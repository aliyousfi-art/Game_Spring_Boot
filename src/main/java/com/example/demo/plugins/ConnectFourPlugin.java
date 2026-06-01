package com.example.demo.plugins;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ConnectFourPlugin implements GamePlugin {

    @Override
    public String getType() {
        return "connectfour";
    }

    @Override
    public String getName(Locale locale) {
        return "Connect Four";
    }
}
