package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.types.GatewayIntent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException {
        Dawncord bot = new Dawncord(getProperty("bot.token"));
        bot.setIntents(GatewayIntent.ALL);


        bot.start();
    }

    private static String getProperty(String name) throws IOException {
        InputStream inputStream = null;
        try {
            Properties properties = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            inputStream = loader.getResourceAsStream("bot.properties");
            properties.load(inputStream);

            return properties.getProperty(name);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
