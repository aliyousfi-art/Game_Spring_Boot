package com.example.demo;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST exposant l'API du catalogue des jeux
 */
@RestController
@RequestMapping("/api/games")
public class GameCatalogController {

    @Autowired
    private GameCatalog gameCatalog;

    /**
     * Endpoint GET retournant la liste des identifiants des jeux disponibles
     * @return Collection des identifiants des jeux
     */
    @GetMapping("/catalog")
    public Collection<String> getGameCatalog() {
        return gameCatalog.getAvailableGameIds();
    }
}
