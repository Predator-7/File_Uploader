package com.fileuploader.controller;

import com.fileuploader.dto.UserDto;
import com.fileuploader.entity.Files;
import com.fileuploader.entity.User;
import com.fileuploader.exception.InternalServerException;
import com.fileuploader.exception.InvalidParameterException;
import com.fileuploader.repository.FilesRepository;
import com.fileuploader.service.AuthenticationService;
import com.fileuploader.service.FileUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api")
public class Controller {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @CrossOrigin
    @GetMapping("test")
    public String test(){
        return "test";
    }

    @CrossOrigin("*")
    @PostMapping(value = "signup" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User signup(@RequestBody UserDto userDto) {

        return authenticationService.signup(userDto);
    }

    @CrossOrigin("*")
    @PostMapping(value = "login" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User login(@RequestBody UserDto userDto){

        return authenticationService.login(userDto);
    }

    @CrossOrigin("*")
    @PostMapping("/upload")
    public ResponseEntity<Files> uploadFile(@RequestParam("file") MultipartFile file ,
                                                         @RequestParam("senderId") Long senderId
    ) {

        String message = "";

        try {
          //  storageService.store(file , senderId , receiverIds);

            Files files = fileUploadService.uploadFile(file,senderId);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            log.info(message);
            return ResponseEntity.status(HttpStatus.OK).body(files);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            log.info(message);
           // return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
            throw  new InternalServerException(e.getMessage());
        }
    }

    @CrossOrigin("*")
    @GetMapping("/files/{id}")
    // Api to view the pdf file

    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<Files> files = filesRepository.findById(id);


        if(files.isEmpty()){
            throw new InvalidParameterException("File Not Found!");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set("Content-Disposition", "inline; filename=\"" + files.get().getName() + "\"");
        headers.setContentLength(files.get().getData().length);

        return new ResponseEntity<>(files.get().getData(), headers, HttpStatus.OK);
    }







}
