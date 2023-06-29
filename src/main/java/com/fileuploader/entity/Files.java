package com.fileuploader.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Getter
@Setter
@Table(name = "files")
public class Files {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "url")
    private String url;

    @Column(name = "uploaded_by")
    private Long userId;


}
