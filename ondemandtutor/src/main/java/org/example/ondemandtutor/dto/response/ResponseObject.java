package org.example.ondemandtutor.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResponseObject {
    private String status;
    private String message;
    private Object data;

    public ResponseObject(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
