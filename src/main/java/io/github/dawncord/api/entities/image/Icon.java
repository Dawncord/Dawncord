package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.types.ImageFormat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Interface representing an icon associated with a resource.
 */
public interface Icon {

    /**
     * Retrieves the URL of the icon in the specified format.
     *
     * @param format The image format.
     * @return The URL of the icon.
     */
    String getUrl(ImageFormat format);

    /**
     * Downloads the icon in JPEG format with the specified name.
     *
     * @param name The name of the downloaded image.
     */
    default void download(String name) {
        downloadImage(ImageFormat.JPEG, name);
    }

    /**
     * Downloads the icon in the specified format with the specified name.
     *
     * @param name   The name of the downloaded image.
     * @param format The image format.
     */
    default void download(String name, ImageFormat format) {
        downloadImage(format, name);
    }

    private void downloadImage(ImageFormat format, String name) {
        try {
            URL url = new URL(getUrl(format));
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(name + format.getExtension());

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
