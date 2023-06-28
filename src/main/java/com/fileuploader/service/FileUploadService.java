package com.fileuploader.service;

import com.fileuploader.entity.Files;
import com.fileuploader.entity.FilesUrl;
import com.fileuploader.repository.FileUrlRepository;
import com.fileuploader.repository.FilesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
@Log4j2
public class FileUploadService {

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private FileUrlRepository fileUrlRepository;

    public Files uploadFile(MultipartFile file , Long userId ) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        byte[] fileBytes = file.getBytes();

        Files files = new Files();
        files.setName(fileName);
        files.setType(file.getContentType());
        files.setData(fileBytes);
        files.setUserId(userId);

        Files savedFile = filesRepository.save(files);

        String id = savedFile.getId();

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


        fileUrlRepository.save(filesUrl);

        filesRepository.save(files);

        return savedFile;
    }


}
