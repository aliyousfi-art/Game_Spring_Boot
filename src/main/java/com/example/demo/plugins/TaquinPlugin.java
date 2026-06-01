package com.example.demo.plugins;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TaquinPlugin implements GamePlugin {

    private final MessageSource messageSource;

    @Value("${game.taquin.default-player-count:1}")
    private int defaultPlayerCount;

    @Value("${game.taquin.default-board-size:4}")
    private int defaultBoardSize;

    public TaquinPlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getType() {
        return "taquin";
    }

    @Override
    public String getName(Locale locale) {
        return messageSource.getMessage("game.taquin.name", null, "Taquin", locale);
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
