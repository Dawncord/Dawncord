package org.dimas4ek.wrapper.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class IOUtils {
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
