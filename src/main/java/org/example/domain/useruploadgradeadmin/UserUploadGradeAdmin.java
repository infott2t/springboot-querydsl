package org.example.domain.useruploadgradeadmin;

import org.example.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="USER_UPLOAD_GRADE_ADMIN")
public class UserUploadGradeAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_UPLOAD_GRADE_ADMIN_ID")
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

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;



    @Builder
    public UserUploadGradeAdmin(String email
            , String isPermit, String isDel, LocalDateTime modifiedDate, LocalDateTime createdDate, User user) {

        this.email = email;
        this.isPermit = isPermit;
        this.isDel = isDel;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
        this.user = user;
    }

    public UserUploadGradeAdmin updateFile(String originFile, String uuidPath,String certNum, LocalDateTime modifiedDate) {
        this.originFile = originFile;
        this.uuidPath = uuidPath;
        this.certNum = certNum;
        this.modifiedDate = modifiedDate;
        return this;
    }
}