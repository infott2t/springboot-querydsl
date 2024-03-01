package org.example.domain.posts;
import org.example.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long>,
        QuerydslPredicateExecutor<Posts>, PostsRepositoryCustom {


}