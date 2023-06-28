package com.fileuploader.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Setter
@Getter
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ErrorResponseDto {
    private int status;
    private String message;
    private long timestamp;
}
