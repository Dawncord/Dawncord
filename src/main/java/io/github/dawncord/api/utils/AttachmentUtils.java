package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;

/**
 * Utility class for handling attachments.
 */
public class AttachmentUtils {

    /**
     * Creates a multipart builder for the given JSON object and list of files.
     *
     * @param jsonObject The JSON object containing attachment information.
     * @param files      The list of files to be attached.
     * @return The multipart builder.
     */
    public static MultipartBody.Builder createMultipartBuilder(ObjectNode jsonObject, List<File> files) {
        jsonObject.set("attachments", createAttachmentsArray(files));

        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("payload_json", null, requestBodyJson);

        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i).getName().toLowerCase();
            String extension = getExtension(fileName);
            String mediaType;

            switch (extension) {
                case "jpg", "jpeg" -> mediaType = "image/jpeg";
                case "png" -> mediaType = "image/png";
                case "webp" -> mediaType = "image/webp";
                case "gif" -> mediaType = "image/gif";
                default -> mediaType = "unknown";
            }

            multipartBuilder.addFormDataPart("files[" + i + "]", fileName, RequestBody.create(MediaType.parse(mediaType), files.get(i)));
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
