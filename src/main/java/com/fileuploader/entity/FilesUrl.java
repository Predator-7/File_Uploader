package com.fileuploader.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "files_url")
public class FilesUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String fileId;

    private String url;

    private Long userId;

    private String fileName;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

}
