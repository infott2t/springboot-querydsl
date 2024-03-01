package org.example.domain.userinfo;

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
@Table(name="USER_INFO")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_INFO_ID")
    private Long id;

    private String addr;
    private String tel;
    private String email;
    private String password;
    private String isDel;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;



    @Builder
    public UserInfo(Long id,String isDel, String email,LocalDateTime createdDate, User user) {

        this.isDel = isDel;
        this.email = email;
        this.createdDate = createdDate;
        this.user = user;
    }


}