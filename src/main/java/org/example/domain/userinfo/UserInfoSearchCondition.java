package org.example.domain.userinfo;
import lombok.Data;

@Data
public class UserInfoSearchCondition {

    private String field;
    private String s;

    private String sdate;
    private String edate;
}
