package org.example.domain.useruploadgradeadmin;
import org.example.domain.user.User;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserUploadGradeAdminRepository extends JpaRepository<UserUploadGradeAdmin, Long>,
        QuerydslPredicateExecutor<UserUploadGradeAdmin>, UserUploadGradeAdminRepositoryCustom {

    Optional<UserUploadGradeAdmin> findByEmail(String email);
}