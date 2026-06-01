package com.example.demo.plugins;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TicTacToePlugin implements GamePlugin {

    private final MessageSource messageSource;

    @Value("${game.tictactoe.default-player-count:2}")
    private int defaultPlayerCount;

    @Value("${game.tictactoe.default-board-size:3}")
    private int defaultBoardSize;

    public TicTacToePlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getType() {
        return "tictactoe";
    }

    @Override
    public String getName(Locale locale) {
        return messageSource.getMessage("game.tictactoe.name", null, "Tic Tac Toe", locale);
    }

    @Override
    public int getDefaultPlayerCount() {
        return defaultPlayerCount;
    }

    @Override
    public int getDefaultBoardSize() {
        return defaultBoardSize;
    }
}
