package org.example.firstinstance.controller.firstinstanceurl.form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUploadGradeAdminApiDtoForm {

    private Long id;
    private String email;
    private String certNum;
    private String originFile;
    private String uuidPath;
    private Long whoPermit;
    private String isPermit;

    private String isDel;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
    private Long userId;
}