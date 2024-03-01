package org.example.domain.posts;
import lombok.Data;

@Data
public class PostsSearchCondition {

    private String field;
    private String s;

    private String sdate;
    private String edate;
}
