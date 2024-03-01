package org.example.domain.useruploadgradeadmin;
import org.example.domain.user.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUploadGradeAdminApiDto {
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
    private User user;



    @QueryProjection
    public UserUploadGradeAdminApiDto( Long id, String email, String certNum, String originFile, String uuidPath, Long whoPermit, String isPermit, String isDel, LocalDateTime modifiedDate, LocalDateTime createdDate, User user) {
        this.id = id;
        this.email = email;
        this.certNum = certNum;
        this.originFile = originFile;
        this.uuidPath = uuidPath;
        this.whoPermit = whoPermit;
        this.isPermit = isPermit;
        this.isDel = isDel;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
        this.user = user;
    }

 

}