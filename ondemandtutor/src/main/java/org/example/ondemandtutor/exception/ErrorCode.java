package org.example.ondemandtutor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY (1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED (1001, "User already existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID (1001, "Username must be have least 3 character", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD (1001, "Password must be have least 8 character", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD (1001, "Wrong password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED (1001, "User not existed", HttpStatus.NOT_FOUND),
    ADMIN_NOT_EXISTED (1001, "Admin not existed", HttpStatus.BAD_REQUEST),
    INVALID_ROLE (1001, "Invalid role", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED (1001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED (1001, "You do not have permisssion", HttpStatus.FORBIDDEN),

    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
