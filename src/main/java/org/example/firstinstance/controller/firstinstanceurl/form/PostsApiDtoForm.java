package org.example.firstinstance.controller.firstinstanceurl.form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostsApiDtoForm {

    private Long id;
    private String title;
    private String context;
    private String author;

    private String isDel;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;

}