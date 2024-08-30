package org.example.ondemandtutor.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
    private String address;
    private String imgUrl;
}
