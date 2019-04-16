package com.sleep.authorize;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesHolder {

    private static PropertiesConfiguration properties;

    public static void initialize() throws ConfigurationException {
        properties = new PropertiesConfiguration("config.properties");
    }

    public static String getProperty(String key){
        return (String) properties.getProperty(key);
    }
}
