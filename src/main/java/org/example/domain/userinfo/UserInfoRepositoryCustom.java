package org.example.domain.userinfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserInfoRepositoryCustom {

    Page<UserInfoApiDto> searchAllV2(UserInfoSearchCondition condition, Pageable pageable);

    Page<UserInfoApiDto> searchAllV3(UserInfoSearchCondition2 condition, Pageable pageable);

  List<UserInfoApiDto> searchFindAllDesc();


}