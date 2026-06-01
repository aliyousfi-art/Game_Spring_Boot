package com.example.demo.plugins;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TaquinPlugin implements GamePlugin {

    @Override
    public String getType() {
        return "taquin";
    }

    @Override
    public String getName(Locale locale) {
        return "Taquin";
    }
}