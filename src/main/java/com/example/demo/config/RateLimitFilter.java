package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rate limiting simple par IP sur les endpoints de mutation (POST, DELETE, PUT).
 * Max 30 requêtes par fenêtre glissante de 60 secondes.
 *
 * Sécurité X-Forwarded-For :
 *   Le header X-Forwarded-For est lu UNIQUEMENT si la variable d'environnement
 *   TRUST_PROXY=true est définie, ce qui signifie que le service est derrière
 *   un reverse proxy de confiance (nginx, load balancer…).
 *   Sans cette variable, on utilise toujours l'IP de connexion directe pour
 *   éviter le spoofing d'IP par un client malveillant.
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 30;
    private static final long WINDOW_MS    = 60_000L;

    /** true si le service tourne derrière un reverse proxy de confiance. */
    private static final boolean TRUST_PROXY =
            "true".equalsIgnoreCase(System.getenv("TRUST_PROXY"));

    private record RequestCount(AtomicInteger count, long windowStart) {}
    private final ConcurrentHashMap<String, RequestCount> counters = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String method = request.getMethod();
        // Appliquer uniquement aux mutations
        if (!method.equals("POST") && !method.equals("DELETE") && !method.equals("PUT")) {
            chain.doFilter(request, response);
            return;
        }

        String ip = getClientIp(request);
        long now = System.currentTimeMillis();

        counters.compute(ip, (key, existing) -> {
            if (existing == null || (now - existing.windowStart()) > WINDOW_MS) {
                return new RequestCount(new AtomicInteger(1), now);
            }
            existing.count().incrementAndGet();
            return existing;
        });

        RequestCount rc = counters.get(ip);
        if (rc.count().get() > MAX_REQUESTS) {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Too Many Requests\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Retourne l'IP réelle du client.
     * X-Forwarded-For n'est utilisé que si TRUST_PROXY=true pour éviter le spoofing.
     */
    private String getClientIp(HttpServletRequest request) {
        if (TRUST_PROXY) {
            String forwarded = request.getHeader("X-Forwarded-For");
            if (forwarded != null && !forwarded.isBlank()) {
                return forwarded.split(",")[0].trim();
            }
        }
        return request.getRemoteAddr();
    }
}
