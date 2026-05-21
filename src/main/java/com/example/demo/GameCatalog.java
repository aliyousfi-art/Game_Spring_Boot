package com.example.demo;

import java.util.Collection;

/**
 * Interface pour accéder au catalogue des jeux disponibles
 */
public interface GameCatalog {
    
    /**
     * Retourne la liste des identifiants des jeux disponibles
     * @return Collection des identifiants des jeux
     */
    Collection<String> getAvailableGameIds();
}
