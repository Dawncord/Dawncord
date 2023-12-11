package org.dimas4ek.wrapper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;

public class AttachmentUtils {
    public static MultipartBody.Builder createMultipartBuilder(ObjectNode jsonObject, List<File> files) {
        jsonObject.set("attachments", createAttachmentsArray(files));

        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("payload_json", null, requestBodyJson);

        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i).getName().toLowerCase();
            String extension = getExtension(fileName);
            String mediaType = null;

            switch (extension) {
                case "jpg":
                case "jpeg":
                    mediaType = "image/jpeg";
                    break;
                case "png":
                    mediaType = "image/png";
                    break;
                case "webp":
                    mediaType = "image/webp";
                    break;
                case "gif":
                    mediaType = "image/gif";
                    break;
                default:
                    break;
            }

            if (mediaType != null) {
                multipartBuilder.addFormDataPart("files[" + i + "]", fileName, RequestBody.create(MediaType.parse(mediaType), files.get(i)));
            }
        }
        return multipartBuilder;
    }

    private static ArrayNode createAttachmentsArray(List<File> files) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode attachments = mapper.createArrayNode();

        for (int i = 0; i < files.size(); i++) {
            ObjectNode file = mapper.createObjectNode();
            file.put("id", i);
            file.put("filename", files.get(i).getName());
            attachments.add(file);
        }

        return attachments;
    }

    private static String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
}
