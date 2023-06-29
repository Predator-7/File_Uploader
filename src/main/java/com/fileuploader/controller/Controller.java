package com.fileuploader.controller;

import com.fileuploader.dto.GetFilesDto;
import com.fileuploader.dto.UserDto;
import com.fileuploader.entity.Files;
import com.fileuploader.entity.FilesUrl;
import com.fileuploader.entity.User;
import com.fileuploader.exception.InternalServerException;
import com.fileuploader.exception.InvalidParameterException;
import com.fileuploader.repository.FileUrlRepository;
import com.fileuploader.repository.FilesRepository;
import com.fileuploader.repository.UserRepository;
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

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private UserRepository userRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FileUrlRepository fileUrlRepository;

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
                                            @RequestParam("senderId") Long userId ,
                                            @RequestParam("fileName") String fileName
    ) {

        // TODO: To add authentication user check.

        String message = "";

        try {

            validateFile(file , fileName);

            Files files = fileUploadService.uploadFile(file, userId , fileName);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            log.info(message);
            return ResponseEntity.status(HttpStatus.OK).body(files);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            log.info(message);
            throw new InternalServerException(e.getMessage());
        }
    }

    @CrossOrigin("*")
    @GetMapping("/files/{id}")
    // Api to view the pdf file

    public ResponseEntity<byte[]> getFile(@PathVariable String id) {

        Optional<Files> files = filesRepository.findById(id);


        if (files.isEmpty()) {
            throw new InvalidParameterException("File Not Found!");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.get().getName() + "\"")
                .body(files.get().getData());

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

    private void validateFile(MultipartFile file , String fileName) {
        // Validate file extension
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png" , "pdf"); // Define your allowed file extensions
        if (!allowedExtensions.contains(fileExtension)) {
            log.error("Invalid file extension. Only " + allowedExtensions + " files are allowed.");
            throw new IllegalArgumentException("Invalid file extension. Only " + allowedExtensions + " files are allowed.");
        }

        // Validate file size
        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            log.error("File size exceeds the limit of " + maxSize + " bytes.");
            throw new IllegalArgumentException("File size exceeds the limit of " + maxSize + " bytes.");
        }


        // Perform file name sanitization to check the name of file is correct or not.
        String sanitizedFileName = sanitizeFileName(originalFilename);
        if (!originalFilename.equals(sanitizedFileName)) {
            log.error("Invalid file name. Please remove any special characters.");
            throw new IllegalArgumentException("Invalid file name. Please remove any special characters.");
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private String sanitizeFileName(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }


}
