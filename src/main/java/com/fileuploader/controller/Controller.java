package com.fileuploader.controller;

import com.fileuploader.dto.UserDto;
import com.fileuploader.entity.User;
import com.fileuploader.service.SignupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api")
public class Controller {

    @Autowired
    private SignupService signupService;

    @CrossOrigin
    @GetMapping("test")
    public String test(){
        return "test";
    }

    @CrossOrigin("*")
    @PostMapping(value = "signup" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User signup(@RequestBody UserDto userDto) {

        return signupService.signup(userDto);
    }


}
