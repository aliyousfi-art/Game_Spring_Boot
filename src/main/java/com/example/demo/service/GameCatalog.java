package com.example.demo.service;

import java.util.Collection;

public interface GameCatalog {

    /**
     * Retourne les IDs de tous les jeux disponibles
     */
    Collection<String> getAvailableGameIds();
}
