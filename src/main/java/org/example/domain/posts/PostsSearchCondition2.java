package org.example.domain.posts;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostsSearchCondition2 {

    private String id;
    private String title;
    private String context;
    private String author;
    private String isDel;
    private String modifiedDate;
    private String createdDate;
   private String field;
   private String s;
   private String sdate;
   private String edate;
}