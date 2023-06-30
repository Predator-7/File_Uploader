package com.fileuploader.controller;

import com.fileuploader.dto.GetFilesDto;
import com.fileuploader.dto.UserDto;
import com.fileuploader.entity.Files;
import com.fileuploader.entity.User;
import com.fileuploader.exception.InternalServerException;
import com.fileuploader.exception.InvalidParameterException;
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


import java.util.*;


@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api")
public class Controller {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileUploadService fileUploadService;


    @CrossOrigin
    @GetMapping("test")
    public String test() {
        return "test";
    }

    @CrossOrigin("*")
    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User signup(@RequestBody UserDto userDto) {

        return authenticationService.signup(userDto);
    }

    @CrossOrigin("*")
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User login(@RequestBody UserDto userDto) {

        return authenticationService.login(userDto);
    }

    @CrossOrigin("*")
    @PostMapping("/upload")
    public ResponseEntity<Files> uploadFile(@RequestParam("file") MultipartFile file,
                                            @RequestParam("senderId") Long userId

    ) {


        try {

            fileUploadValidator(file,userId);

            Files files = fileUploadService.uploadFile(file, userId);

            log.info("Uploaded the file successfully: " + file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(files);

        } catch (Exception e) {
            log.info("Could not upload the file: " + file.getOriginalFilename() + "!");
            throw new InternalServerException(e.getMessage());
        }
    }

    @CrossOrigin("*")
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {

        Files files = fileUploadService.getFile(id);

        String contentType = files.getType();

        MediaType mediaType = MediaType.parseMediaType(contentType);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.getName() + "\"")
                .body(files.getData());

    }

    @CrossOrigin("*")
    @DeleteMapping("file")
    public ResponseEntity<Files> deleteFile(@RequestParam String fileId , @RequestParam Long userId) {

        return ResponseEntity.ok(fileUploadService.deleteFile(fileId,userId));

    }

    @CrossOrigin("*")
    @GetMapping("uploadedFiles")
    public List<GetFilesDto> getUploadedFiles(@RequestParam Long userId){

        return fileUploadService.getUploadedFiles(userId);
    }


    // Other private validation methods -

    private void fileUploadValidator(MultipartFile file , Long userId) {


        if(!authenticationService.isUserValid(userId)){
            log.error("Invalid User!");
            throw new InvalidParameterException("Invalid User!");
        }


        // Validate file extension
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png" , "pdf" , "txt");
        if (!allowedExtensions.contains(fileExtension)) {
            log.error("Invalid file extension. Only " + allowedExtensions + " files are allowed.");
            throw new InvalidParameterException("Invalid file extension. Only " + allowedExtensions + " files are allowed.");
        }

        // Validate file size
        long maxSize = 5 * 1024 * 1024;
        log.info(file.getSize());
        if (file.getSize() > maxSize) {
            log.error("File size exceeds the limit of " + maxSize + " bytes.");
            throw new InvalidParameterException("File size exceeds the limit of " + maxSize + " bytes.");
        }

    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }


}
