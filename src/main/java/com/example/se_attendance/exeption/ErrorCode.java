package com.example.se_attendance.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    INVALID_INPUT(HttpStatus.UNAUTHORIZED,"" ),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    INVALID_MEMBER(HttpStatus.UNAUTHORIZED,"" ),
    NOT_FOUND(HttpStatus.NOT_FOUND, "" );

    private HttpStatus httpStatus;
    private String message;
}
