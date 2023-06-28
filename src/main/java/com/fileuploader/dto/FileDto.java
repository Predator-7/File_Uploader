package com.fileuploader.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@Setter
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileDto {

    private Long senderId;

    private String url;

    private String fileType;

    private String filename;

}
