package com.fileuploader.service;

import com.fileuploader.dto.GetFilesDto;
import com.fileuploader.entity.Files;
import com.fileuploader.entity.FilesUrl;
import com.fileuploader.entity.User;
import com.fileuploader.exception.InvalidParameterException;
import com.fileuploader.repository.FileUrlRepository;
import com.fileuploader.repository.FilesRepository;
import com.fileuploader.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FileUploadService {

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private FileUrlRepository fileUrlRepository;

    @Autowired
    private UserRepository userRepository;

    private Integer expiryRange = 3;

    public Files uploadFile(MultipartFile file , Long userId) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        LocalDateTime expirationDate = LocalDateTime.now().plusDays(expiryRange);

        byte[] fileBytes = file.getBytes();

        Files files = new Files();
        files.setName(fileName);
        files.setType(file.getContentType());
        files.setData(fileBytes);
        files.setUserId(userId);

        Files savedFile = filesRepository.save(files);

        String id = savedFile.getId();

        String fileExtension = StringUtils.getFilenameExtension(savedFile.getName());
        log.info(fileExtension);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/files/")
                .path(id.toString())
                .toUriString();
        savedFile.setUrl(fileDownloadUri);


        FilesUrl filesUrl = new FilesUrl();
        filesUrl.setUrl(fileDownloadUri);
        filesUrl.setFileId(id);
        filesUrl.setUserId(userId);
        filesUrl.setFileName(fileName);
        filesUrl.setExpirationDate(expirationDate);


        fileUrlRepository.save(filesUrl);

        filesRepository.save(files);


        return savedFile;
    }

    public List<GetFilesDto> getUploadedFiles(Long userId){

        deleteExpiredFiles();

        List<FilesUrl> uploadedFiles = fileUrlRepository.findAllByUserId(userId);

        List<GetFilesDto> dtoList = uploadedFiles.stream()
                .map(file -> {
                    GetFilesDto dto = new GetFilesDto();
                    dto.setFileName(file.getFileName());
                    dto.setFileUrl(file.getUrl());
                    dto.setFileId(file.getFileId());
                    return dto;
                })
                .collect(Collectors.toList());

        return dtoList;
    }

    public Files deleteFile(String fileId , Long userId){

        Optional<Files> files = filesRepository.findById(fileId);
        Optional<User> user = userRepository.findById(userId);
        FilesUrl filesUrl = fileUrlRepository.findByFileId(fileId);


        if (user.isEmpty()) {
            throw new InvalidParameterException("Invalid User!");
        }

        if (files.isEmpty()) {
            throw new InvalidParameterException("File Not Found!");
        }


        filesRepository.deleteById(fileId);

        fileUrlRepository.delete(filesUrl);

        log.info("Deleted Successfully!");

        return files.get();
    }


    public void deleteExpiredFiles() {
        List<FilesUrl> expiredFiles = fileUrlRepository.findAllByExpirationDateBefore(LocalDateTime.now());

        for (FilesUrl expiredFile : expiredFiles) {


            Long userId = expiredFile.getUserId();
            String fileId = expiredFile.getFileId();

            Files files = deleteFile(fileId , userId);
            log.info(expiredFile.getFileName() + " is expired!");
        }
    }

    public Files getFile(String id){
        Optional<Files> files = filesRepository.findById(id);


        if (files.isEmpty()) {
            throw new InvalidParameterException("File Not Found!");
        }
        return files.get();
    }




}
