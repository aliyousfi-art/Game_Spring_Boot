package com.example.demo;

import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implémentation du catalogue des jeux disponibles
 * Utilise le moteur de jeux du Campus Numérique
 */
@Service
public class GameCatalogImpl implements GameCatalog {

    /**
     * Retourne la liste des identifiants des jeux disponibles
     * @return Collection des identifiants des jeux
     */
    @Override
    public Collection<String> getAvailableGameIds() {
        // Pour le moment, retourne les jeux disponibles via TicTacToeGameFactory
        // Ce catalogue peut être étendu avec d'autres jeux du moteur
        return List.of(
            "tictactoe"
        );
    }
}
