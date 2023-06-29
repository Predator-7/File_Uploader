package com.fileuploader.repository;

import com.fileuploader.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FilesRepository extends JpaRepository<Files, String> {

    List<Files> findAllByUserId(Long userId);

    // filesRepository.findByExpirationDateBefore


    void deleteById(String id);

}
