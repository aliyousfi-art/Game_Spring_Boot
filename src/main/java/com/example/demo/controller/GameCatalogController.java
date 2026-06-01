package com.example.demo.controller;

import com.example.demo.dto.GameInfoDTO;
import com.example.demo.plugins.GamePlugin;
import com.example.demo.service.GameCatalogImpl;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@RestController
public class GameCatalogController {

    private final GameCatalogImpl gameCatalog;

    public GameCatalogController(GameCatalogImpl gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    /**
     * GET /games/catalog
     * Retourne la liste des IDs de jeux disponibles
     */
    @GetMapping("/games/catalog")
    public Collection<String> getCatalog() {
        return gameCatalog.getAvailableGameIds();
    }

    /**
     * GET /games/catalog/detailed
     * Retourne les informations détaillées des jeux
     * Support Accept-Language pour l'internationalisation
     */
    @GetMapping("/games/catalog/detailed")
    public List<GameInfoDTO> getDetailedCatalog(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {
        
        // Déterminer la locale depuis le header Accept-Language ou utiliser la locale par défaut
        Locale locale = parseLocale(acceptLanguage);
        
        return gameCatalog.getGamePlugins().stream()
                .map(plugin -> new GameInfoDTO(
                        plugin.getType(),
                        plugin.getName(locale),
                        plugin.getDefaultPlayerCount(),
                        plugin.getDefaultBoardSize()
                ))
                .toList();
    }

    /**
     * Parse le header Accept-Language pour créer une Locale
     * Format: "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7"
     */
    private Locale parseLocale(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isEmpty()) {
            return Locale.getDefault();
        }

        String[] locales = acceptLanguage.split(",");
        String[] parts = locales[0].split(";")[0].split("-");
        
        if (parts.length >= 2) {
            return new Locale(parts[0].toLowerCase(), parts[1].toUpperCase());
        } else if (parts.length == 1) {
            return new Locale(parts[0].toLowerCase());
        }
        
        return Locale.getDefault();
    }
}
