package org.dimas4ek.wrapper.utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class AttachmentUtils {
    public static MultipartBody.Builder creteMultipartBuilder(JSONObject jsonObject, File[] files) {
        jsonObject.put("attachments", AttachmentUtils.createAttachmentsArray(files));

        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("payload_json", null, requestBodyJson);

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName().toLowerCase();
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
                    //TODO add logger error
                    break;
            }

            if (mediaType != null) {
                multipartBuilder.addFormDataPart("files[" + i + "]", fileName, RequestBody.create(MediaType.parse(mediaType), files[i]));
            }
        }
        return multipartBuilder;
    }

    public static JSONArray createAttachmentsArray(File[] files) {
        JSONArray attachments = new JSONArray();

        for (int i = 0; i < files.length; i++) {
            JSONObject file = new JSONObject();
            file.put("id", i);
            file.put("filename", files[i].getName());
            attachments.put(file);
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
