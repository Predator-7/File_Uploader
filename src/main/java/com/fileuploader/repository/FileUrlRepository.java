package com.fileuploader.repository;

import com.fileuploader.entity.FilesUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUrlRepository extends JpaRepository<FilesUrl, Long> {

    List<FilesUrl> findAllByUserId(Long userId);

    FilesUrl findByFileId(String fileId);

    void deleteByFileId(String id);


}
