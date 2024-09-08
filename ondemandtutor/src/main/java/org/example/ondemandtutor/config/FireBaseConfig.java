package org.example.ondemandtutor.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

import java.io.IOException;

@Configuration
public class FireBaseConfig {

    @Bean
    @ConditionalOnMissingBean(FirebaseApp.class)  // Only initialize if FirebaseApp is not already mocked
    public FirebaseApp initFireBase() throws IOException {
        String serviceAccountPath = "ondemandtutor/serviceAccountKey.json";
        FileInputStream serviceAccountStream = new FileInputStream(serviceAccountPath);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .setStorageBucket("ondemandtutor-34f9d.appspot.com")
                .build();
        return FirebaseApp.initializeApp(options);
    }
    @Bean
    public Bucket firebaseBucket(FirebaseApp firebaseApp) {
        return StorageClient.getInstance(firebaseApp).bucket();
    }
}
