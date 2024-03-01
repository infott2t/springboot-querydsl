package org.example.domain.posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsRepositoryCustom {

    Page<PostsApiDto> searchAllV2(PostsSearchCondition condition, Pageable pageable);

    Page<PostsApiDto> searchAllV3(PostsSearchCondition2 condition, Pageable pageable);

  List<PostsApiDto> searchFindAllDesc();


}