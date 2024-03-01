package org.example.firstinstance.controller.firstinstanceurl.form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoApiDtoForm {

    private Long id;
    private String addr;
    private String tel;
    private String email;
    private String password;

    private String isDel;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
    private Long userId;
}