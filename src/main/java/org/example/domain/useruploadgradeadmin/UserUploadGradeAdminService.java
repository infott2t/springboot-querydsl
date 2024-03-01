package org.example.domain.useruploadgradeadmin;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUploadGradeAdminService {

    private final UserUploadGradeAdminRepository userUploadGradeAdminRepository;
    
    @Transactional(readOnly = true)
    public List<UserUploadGradeAdminApiDto> searchFindAllDesc() {
        return  userUploadGradeAdminRepository.searchFindAllDesc();
    }

    @Transactional(readOnly = true)
    public UserUploadGradeAdmin findById(Long id) {
        return userUploadGradeAdminRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(UserUploadGradeAdmin userUploadGradeAdmin) {
        userUploadGradeAdminRepository.save(userUploadGradeAdmin);
    }

    @Transactional(readOnly = true)
    public Page<UserUploadGradeAdminApiDto> searchAllV2(UserUploadGradeAdminSearchCondition condition, Pageable pageable) {
        return userUploadGradeAdminRepository.searchAllV2(condition, pageable);
    }

    @Transactional
    public void joinFile(User user, String originFile, String certNum, String uuidPath){

        UserUploadGradeAdmin uploadGradeAdmin = userUploadGradeAdminRepository.findByEmail(user.getEmail()).orElseThrow();

        uploadGradeAdmin.updateFile(originFile, uuidPath, certNum, LocalDateTime.now());
        this.save(uploadGradeAdmin);
    }

    @Transactional
    public UserUploadGradeAdmin findByEmail(String email){

        UserUploadGradeAdmin uploadGradeAdmin = userUploadGradeAdminRepository.findByEmail(email).orElseThrow();
        return uploadGradeAdmin;
    }

}