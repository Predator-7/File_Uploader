package com.fileuploader.repository;

import com.fileuploader.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesRepository extends JpaRepository<Files, String> {

    List<Files> findAllByUserId(Long userId);

    void deleteById(String id);

}
