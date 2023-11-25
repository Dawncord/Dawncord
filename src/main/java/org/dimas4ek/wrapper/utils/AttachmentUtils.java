package org.dimas4ek.wrapper.utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class AttachmentUtils {
    public static MultipartBody.Builder creteMultipartBuilder(JSONObject jsonObject, List<File> files) {
        jsonObject.put("attachments", AttachmentUtils.createAttachmentsArray(files));

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

    private static JSONArray createAttachmentsArray(List<File> files) {
        JSONArray attachments = new JSONArray();

        for (int i = 0; i < files.size(); i++) {
            JSONObject file = new JSONObject();
            file.put("id", i);
            file.put("filename", files.get(i).getName());
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
