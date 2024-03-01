package org.example.domain.useruploadgradeadmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserUploadGradeAdminRepositoryCustom {

    Page<UserUploadGradeAdminApiDto> searchAllV2(UserUploadGradeAdminSearchCondition condition, Pageable pageable);

    Page<UserUploadGradeAdminApiDto> searchAllV3(UserUploadGradeAdminSearchCondition2 condition, Pageable pageable);

  List<UserUploadGradeAdminApiDto> searchFindAllDesc();


}