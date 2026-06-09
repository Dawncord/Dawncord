package io.github.dawncord.api.entities.guild.widget;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public class GuildWidgetImage {
    private final String url;

    public GuildWidgetImage(String url) {
        this.url = url;
    }

    public void downloadImage(String name) {
        try {
            URL url = URI.create(this.url).toURL();
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(name + ".png");

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
            System.out.println("Image downloaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
