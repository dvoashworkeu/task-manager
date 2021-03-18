package com.java.taskmanager.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    Properties prop = new Properties();

    public String get(String name) {
        return prop.getProperty(name);
    }

    public void load() throws IOException {
        FileInputStream ip = new FileInputStream("config.properties");
        prop.load(ip);
    }
}
