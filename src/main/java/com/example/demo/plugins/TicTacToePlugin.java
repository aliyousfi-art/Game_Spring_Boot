package com.example.demo.plugins;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TicTacToePlugin implements GamePlugin {

    @Override
    public String getType() {
        return "tictactoe";
    }

    @Override
    public String getName(Locale locale) {
        return "Tic Tac Toe";
    }
}