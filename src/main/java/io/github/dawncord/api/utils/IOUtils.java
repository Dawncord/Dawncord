package io.github.dawncord.api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Utility class for handling input and output operations.
 */
public class IOUtils {

    /**
     * Sets image data from the specified path.
     *
     * @param path The path to the image file.
     * @return The image data in base64 format with the appropriate data URI.
     * @throws RuntimeException if there's an error reading the file or if the file format is unsupported.
     */
    public static String setImageData(String path) {
        String format = path.substring(path.lastIndexOf(".") + 1);
        if (format.matches("gif|png|jpg|jpeg")) {
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(path));
                String base64 = Base64.getEncoder().encodeToString(imageBytes);
                return "data:image/" + format + ";base64," + base64;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Unsupported file format: " + format);
        }
    }
}
