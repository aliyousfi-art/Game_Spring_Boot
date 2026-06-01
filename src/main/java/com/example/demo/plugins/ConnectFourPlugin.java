package com.example.demo.plugins;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ConnectFourPlugin implements GamePlugin {

    private final MessageSource messageSource;

    @Value("${game.connectfour.default-player-count:2}")
    private int defaultPlayerCount;

    @Value("${game.connectfour.default-board-size:7}")
    private int defaultBoardSize;

    public ConnectFourPlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getType() {
        return "connectfour";
    }

    @Override
    public String getName(Locale locale) {
        return messageSource.getMessage("game.connectfour.name", null, "Connect Four", locale);
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
