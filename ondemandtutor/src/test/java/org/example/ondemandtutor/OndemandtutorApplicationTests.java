package org.example.ondemandtutor;

import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class OndemandtutorApplicationTests {
    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;  // Correct type


    @Test
    void contextLoads() {
    }

}
