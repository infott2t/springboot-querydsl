package org.example.domain.posts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    
    @Transactional(readOnly = true)
    public List<PostsApiDto> searchFindAllDesc() {
        return  postsRepository.searchFindAllDesc();
    }

    @Transactional(readOnly = true)
    public Posts findById(Long id) {
        return postsRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(Posts posts) {
        postsRepository.save(posts);
    }

    @Transactional(readOnly = true)
    public Page<PostsApiDto> searchAllV2(PostsSearchCondition condition, Pageable pageable) {
        return postsRepository.searchAllV2(condition, pageable);
    }
}