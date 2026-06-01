package com.example.demo.plugins;

import java.util.Locale;

public interface GamePlugin {

    String getType();

    String getName(Locale locale);

}
