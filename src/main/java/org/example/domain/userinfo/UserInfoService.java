package org.example.domain.userinfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    
    @Transactional(readOnly = true)
    public List<UserInfoApiDto> searchFindAllDesc() {
        return  userInfoRepository.searchFindAllDesc();
    }

    @Transactional(readOnly = true)
    public UserInfo findById(Long id) {
        return userInfoRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Transactional(readOnly = true)
    public Page<UserInfoApiDto> searchAllV2(UserInfoSearchCondition condition, Pageable pageable) {
        return userInfoRepository.searchAllV2(condition, pageable);
    }
}