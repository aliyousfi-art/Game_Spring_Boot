package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collection;

@Service
public class GameCatalogImpl implements GameCatalog {

    @Override
    public Collection<String> getAvailableGameIds() {
        return List.of(
                "tictactoe",
                "connectfour",
                "taquin"
        );
    }
}