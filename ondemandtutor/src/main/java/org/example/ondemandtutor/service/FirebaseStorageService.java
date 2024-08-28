package org.example.ondemandtutor.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FirebaseStorageService {
    Bucket bucket;
    // Upload file
    public String uploadFile(String fileName, InputStream inputStream, String contentType) throws IOException {
        try {
            bucket.create(fileName, inputStream, contentType);
            String baseUrl = "https://firebasestorage.googleapis.com/v0/b/ondemandtutor-34f9d.appspot.com/o/";
            return baseUrl + URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()) + "?alt=media";
        } catch (IOException e) {
            throw new IOException("Failed to upload file to Firebase Storage", e);
        }
    }

    // Delete file
    public void deleteFile(String url) {
        try {
            String fileName = url.substring(url.lastIndexOf('/') + 1, url.indexOf("?alt=media"));
            Blob blob = bucket.get(fileName);
            if (blob != null) {
                blob.delete();
            } else {
                System.out.println("File not found: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Failed to delete file from Firebase Storage: " + e.getMessage());
        }
    }
}
