package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.types.ImageFormat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface Icon {
    String getUrl(ImageFormat format);

    default void download(String name) {
        downloadImage(ImageFormat.JPEG, name);
    }

    default void download(String name, ImageFormat format) {
        downloadImage(format, name);
    }

    private void downloadImage(ImageFormat format, String name) {
        try {
            URL url = new URL(getUrl(format));
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(name + format.getFormat());

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
            System.out.println("Изображение успешно скачано.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
