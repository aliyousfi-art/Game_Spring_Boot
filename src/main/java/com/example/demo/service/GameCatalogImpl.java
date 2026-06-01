package com.example.demo.service;

import com.example.demo.plugins.GamePlugin;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog {

    private final List<GamePlugin> gamePlugins;

    // Injection de tous les GamePlugin disponibles
    public GameCatalogImpl(List<GamePlugin> gamePlugins) {
        this.gamePlugins = gamePlugins;
    }

    @Override
    public Collection<String> getAvailableGameIds() {
        return gamePlugins.stream()
                .map(GamePlugin::getType)
                .toList();
    }

    public List<GamePlugin> getGamePlugins() {
        return gamePlugins;
    }

    public GamePlugin getGamePlugin(String gameId) {
        return gamePlugins.stream()
                .filter(plugin -> plugin.getType().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game not found: " + gameId));
    }
}
