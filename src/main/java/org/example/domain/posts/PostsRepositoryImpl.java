package org.example.domain.posts;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.example.domain.posts.QPosts.posts;
import static org.springframework.util.StringUtils.hasText;



public class PostsRepositoryImpl implements PostsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


   @Override
    public Page<PostsApiDto> searchAllV3(PostsSearchCondition2 condition2, Pageable pageable) {

        List<PostsApiDto> content = queryFactory.
                select(Projections.constructor(PostsApiDto.class,
                        posts.id,
                        posts.title,
                        posts.context,
                        posts.author,
                        posts.isDel,
                        posts.modifiedDate,
                        posts.createdDate              )).from(posts)
                .where(
                        searchAllV3Predicate(condition2)
                ).where(posts.isDel.eq("N"))
                .orderBy(posts.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(posts.count())
                .from(posts)
                .where(
                        searchAllV3Predicate(condition2)
                ).where(posts.isDel.eq("N"))
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate searchAllV3Predicate(PostsSearchCondition2 condition2) {
        return new BooleanBuilder()
                        .and(condId(condition2.getId()))
                        .and(condTitle(condition2.getTitle()))
                        .and(condContext(condition2.getContext()))
                        .and(condAuthor(condition2.getAuthor()))
                        .and(condIsDel(condition2.getIsDel()))
                        .and(condModifiedDate(condition2.getModifiedDate()))
                        .and(condCreatedDate(condition2.getCreatedDate()))
                .and(condS2(condition2.getField(), condition2.getS()))
                .and(condSdate2(condition2.getSdate()))
                .and(condEdate2(condition2.getEdate()));
    }




    private Predicate condId(String id) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(id)){
            builder.or(posts.id.eq(Long.valueOf(id)));
        }
        return builder;
    }

    private Predicate condTitle(String title) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(title)){
            builder.or(posts.title.eq(title));
        }
        return builder;
    }

    private Predicate condContext(String context) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(context)){
            builder.or(posts.context.eq(context));
        }
        return builder;
    }

    private Predicate condAuthor(String author) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(author)){
            builder.or(posts.author.eq(author));
        }
        return builder;
    }

    private Predicate condIsDel(String isDel) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(isDel)){
            builder.or(posts.isDel.eq(isDel));
        }
        return builder;
    }

    private Predicate condModifiedDate(String modifiedDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(modifiedDate)){
            builder.or(posts.modifiedDate.eq(LocalDateTime.parse(modifiedDate)));
        }
        return builder;
    }

    private Predicate condCreatedDate(String createdDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(createdDate)){
            builder.or(posts.createdDate.eq(LocalDateTime.parse(createdDate)));
        }
        return builder;
    }


    private Predicate condS2(String field, String s) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(field) && hasText(s)){
            if(field.equals("id")){
                builder.or(posts.id.eq(Long.valueOf(s)));
            }
            if(field.equals("title")){
                builder.or(posts.title.like("%"+s+"%"));
            }
            if(field.equals("context")){
                builder.or(posts.context.like("%"+s+"%"));
            }
            if(field.equals("author")){
                builder.or(posts.author.like("%"+s+"%"));
            }
            if(field.equals("isDel")){
                builder.or(posts.isDel.like("%"+s+"%"));
            }
            if(field.equals("modifiedDate")){
                builder.or(posts.modifiedDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("createdDate")){
                builder.or(posts.createdDate.eq(LocalDateTime.parse(s)));
            }
        }
        return builder;
    }

    private Predicate condSdate2(String sdate) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(sdate)){
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(sdate + "T00:00:00");
                builder.or(posts.modifiedDate.goe(localDateTime)); // isrtDate >= sdate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }
    private Predicate condEdate2(String edate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(edate)) {
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(edate + "T00:00:00");
                builder.or(posts.modifiedDate.loe(localDateTime)); // isrtDate <= edate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }


    @Override
    public Page<PostsApiDto> searchAllV2(PostsSearchCondition condition, Pageable pageable) {

        List<PostsApiDto> content = queryFactory.
                select(Projections.constructor(PostsApiDto.class,
                        posts.id,
                        posts.title,
                        posts.context,
                        posts.author,
                        posts.isDel,
                        posts.modifiedDate,
                        posts.createdDate              )).from(posts)
                .where(
                        searchAllV2Predicate(condition)
                ).where(posts.isDel.eq("N"))
                .orderBy(posts.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(posts.count())
                .from(posts)
                .where(
                        searchAllV2Predicate(condition)
                ).where(posts.isDel.eq("N"))
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }





    private BooleanBuilder searchAllV2Predicate(PostsSearchCondition condition){
        return new BooleanBuilder()
                .and(condS(condition.getField(), condition.getS()))
                .and(condSdate(condition.getSdate()))
                .and(condEdate(condition.getEdate()));

    }

    private Predicate condS(String field, String s) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(field) && hasText(s)){
            if(field.equals("id")){
                builder.or(posts.id.eq(Long.valueOf(s)));
            }
            if(field.equals("title")){
                builder.or(posts.title.like("%"+s+"%"));
            }
            if(field.equals("context")){
                builder.or(posts.context.like("%"+s+"%"));
            }
            if(field.equals("author")){
                builder.or(posts.author.like("%"+s+"%"));
            }
            if(field.equals("isDel")){
                builder.or(posts.isDel.like("%"+s+"%"));
            }
            if(field.equals("modifiedDate")){
                builder.or(posts.modifiedDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("createdDate")){
                builder.or(posts.createdDate.eq(LocalDateTime.parse(s)));
            }
        }
        return builder;
    }

    private Predicate condSdate( String sdate){
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(sdate)){
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(sdate + "T00:00:00");
                builder.or(posts.modifiedDate.goe(localDateTime)); // isrtDate >= sdate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }

    private Predicate condEdate( String edate){
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(edate)) {
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(edate + "T00:00:00");
                builder.or(posts.modifiedDate.loe(localDateTime)); // isrtDate <= edate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }



    @Override
    public List<PostsApiDto> searchFindAllDesc() {
        List<PostsApiDto> content = queryFactory.
                select(Projections.constructor(PostsApiDto.class,
                        posts.id,
                        posts.title,
                        posts.context,
                        posts.author,
                        posts.isDel,
                        posts.modifiedDate,
                        posts.createdDate              )).from(posts).where(posts.isDel.eq("N"))
                .orderBy(posts.id.asc())
                .fetch();


        return content;
    }
}