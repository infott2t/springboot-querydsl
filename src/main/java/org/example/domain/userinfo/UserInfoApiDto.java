package org.example.domain.userinfo;
import org.example.domain.user.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoApiDto {
    private Long id;
    private String addr;
    private String tel;
    private String email;
    private String password;
    private String isDel;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
    private User user;



    @QueryProjection
    public UserInfoApiDto( Long id, String addr, String tel, String email, String password, String isDel, LocalDateTime modifiedDate, LocalDateTime createdDate, User user) {
        this.id = id;
        this.addr = addr;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.isDel = isDel;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
        this.user = user;
    }

 

}