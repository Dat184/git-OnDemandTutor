package org.example.ondemandtutor.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    private String oldPass;
    @Size(min = 8, message = "INVALID_PASSWORD")
    private String password;
    @Email(message = "INVALID_EMAIL")
    private String email;
    @Size(min = 2, message = "INVALID_NAME")
    private String name;
    private String grade;

}
