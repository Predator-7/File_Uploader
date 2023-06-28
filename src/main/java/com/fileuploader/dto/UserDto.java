package com.fileuploader.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserDto {


    private String userName;

    private String email;

    private String password;

}
