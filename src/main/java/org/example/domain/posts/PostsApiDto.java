package org.example.domain.posts;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostsApiDto {
    private Long id;
    private String title;
    private String context;
    private String author;
    private String isDel;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;



    @QueryProjection
    public PostsApiDto( Long id, String title, String context, String author, String isDel, LocalDateTime modifiedDate, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.author = author;
        this.isDel = isDel;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

 

}