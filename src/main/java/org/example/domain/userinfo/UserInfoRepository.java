package org.example.domain.userinfo;
import org.example.domain.user.User;
import org.example.domain.userinfo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>,
        QuerydslPredicateExecutor<UserInfo>, UserInfoRepositoryCustom {
    Optional<UserInfo> findByEmail(String email);

}