package org.example.domain.userinfo;
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

import static org.example.domain.userinfo.QUserInfo.userInfo;
import static org.example.domain.user.QUser.user;
import static org.springframework.util.StringUtils.hasText;



public class UserInfoRepositoryImpl implements UserInfoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserInfoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


   @Override
    public Page<UserInfoApiDto> searchAllV3(UserInfoSearchCondition2 condition2, Pageable pageable) {

        List<UserInfoApiDto> content = queryFactory.
                select(Projections.constructor(UserInfoApiDto.class,
                        userInfo.id,
                        userInfo.addr,
                        userInfo.tel,
                        userInfo.email,
                        userInfo.password,
                        userInfo.isDel,
                        userInfo.modifiedDate,
                        userInfo.createdDate,
                        userInfo.user              )).from(userInfo)
                        .leftJoin(userInfo.user, user)
                .where(
                        searchAllV3Predicate(condition2)
                ).where(userInfo.isDel.eq("N"))
                .orderBy(userInfo.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(userInfo.count())
                .from(userInfo)
                .where(
                        searchAllV3Predicate(condition2)
                ).where(userInfo.isDel.eq("N"))
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate searchAllV3Predicate(UserInfoSearchCondition2 condition2) {
        return new BooleanBuilder()
                        .and(condId(condition2.getId()))
                        .and(condAddr(condition2.getAddr()))
                        .and(condTel(condition2.getTel()))
                        .and(condEmail(condition2.getEmail()))
                        .and(condPassword(condition2.getPassword()))
                        .and(condIsDel(condition2.getIsDel()))
                        .and(condModifiedDate(condition2.getModifiedDate()))
                        .and(condCreatedDate(condition2.getCreatedDate()))
                        .and(condUserId(condition2.getUserId()))
                .and(condS2(condition2.getField(), condition2.getS()))
                .and(condSdate2(condition2.getSdate()))
                .and(condEdate2(condition2.getEdate()));
    }




    private Predicate condId(String id) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(id)){
            builder.or(userInfo.id.eq(Long.valueOf(id)));
        }
        return builder;
    }

    private Predicate condAddr(String addr) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(addr)){
            builder.or(userInfo.addr.eq(addr));
        }
        return builder;
    }

    private Predicate condTel(String tel) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(tel)){
            builder.or(userInfo.tel.eq(tel));
        }
        return builder;
    }

    private Predicate condEmail(String email) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(email)){
            builder.or(userInfo.email.eq(email));
        }
        return builder;
    }

    private Predicate condPassword(String password) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(password)){
            builder.or(userInfo.password.eq(password));
        }
        return builder;
    }

    private Predicate condIsDel(String isDel) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(isDel)){
            builder.or(userInfo.isDel.eq(isDel));
        }
        return builder;
    }

    private Predicate condModifiedDate(String modifiedDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(modifiedDate)){
            builder.or(userInfo.modifiedDate.eq(LocalDateTime.parse(modifiedDate)));
        }
        return builder;
    }

    private Predicate condCreatedDate(String createdDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(createdDate)){
            builder.or(userInfo.createdDate.eq(LocalDateTime.parse(createdDate)));
        }
        return builder;
    }

    private Predicate condUserId (String userId) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(userId)){
            builder.or(userInfo.user.id.eq(Long.valueOf(userId)));
        }
        return builder;
    }


    private Predicate condS2(String field, String s) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(field) && hasText(s)){
            if(field.equals("id")){
                builder.or(userInfo.id.eq(Long.valueOf(s)));
            }
            if(field.equals("addr")){
                builder.or(userInfo.addr.like("%"+s+"%"));
            }
            if(field.equals("tel")){
                builder.or(userInfo.tel.like("%"+s+"%"));
            }
            if(field.equals("email")){
                builder.or(userInfo.email.like("%"+s+"%"));
            }
            if(field.equals("password")){
                builder.or(userInfo.password.like("%"+s+"%"));
            }
            if(field.equals("isDel")){
                builder.or(userInfo.isDel.like("%"+s+"%"));
            }
            if(field.equals("modifiedDate")){
                builder.or(userInfo.modifiedDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("createdDate")){
                builder.or(userInfo.createdDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("userId")){
                builder.or(userInfo.user.id.eq(Long.valueOf(s)));
            }
        }
        return builder;
    }

    private Predicate condSdate2(String sdate) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(sdate)){
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(sdate + "T00:00:00");
                builder.or(userInfo.modifiedDate.goe(localDateTime)); // isrtDate >= sdate

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
                builder.or(userInfo.modifiedDate.loe(localDateTime)); // isrtDate <= edate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }


    @Override
    public Page<UserInfoApiDto> searchAllV2(UserInfoSearchCondition condition, Pageable pageable) {

        List<UserInfoApiDto> content = queryFactory.
                select(Projections.constructor(UserInfoApiDto.class,
                        userInfo.id,
                        userInfo.addr,
                        userInfo.tel,
                        userInfo.email,
                        userInfo.password,
                        userInfo.isDel,
                        userInfo.modifiedDate,
                        userInfo.createdDate,
                        userInfo.user              )).from(userInfo)
                        .leftJoin(userInfo.user, user)
                .where(
                        searchAllV2Predicate(condition)
                ).where(userInfo.isDel.eq("N"))
                .orderBy(userInfo.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(userInfo.count())
                .from(userInfo)
                .where(
                        searchAllV2Predicate(condition)
                ).where(userInfo.isDel.eq("N"))
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }





    private BooleanBuilder searchAllV2Predicate(UserInfoSearchCondition condition){
        return new BooleanBuilder()
                .and(condS(condition.getField(), condition.getS()))
                .and(condSdate(condition.getSdate()))
                .and(condEdate(condition.getEdate()));

    }

    private Predicate condS(String field, String s) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(field) && hasText(s)){
            if(field.equals("id")){
                builder.or(userInfo.id.eq(Long.valueOf(s)));
            }
            if(field.equals("addr")){
                builder.or(userInfo.addr.like("%"+s+"%"));
            }
            if(field.equals("tel")){
                builder.or(userInfo.tel.like("%"+s+"%"));
            }
            if(field.equals("email")){
                builder.or(userInfo.email.like("%"+s+"%"));
            }
            if(field.equals("password")){
                builder.or(userInfo.password.like("%"+s+"%"));
            }
            if(field.equals("isDel")){
                builder.or(userInfo.isDel.like("%"+s+"%"));
            }
            if(field.equals("modifiedDate")){
                builder.or(userInfo.modifiedDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("createdDate")){
                builder.or(userInfo.createdDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("userId")){
                builder.or(userInfo.user.id.eq(Long.valueOf(s)));
            }
        }
        return builder;
    }

    private Predicate condSdate( String sdate){
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(sdate)){
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(sdate + "T00:00:00");
                builder.or(userInfo.modifiedDate.goe(localDateTime)); // isrtDate >= sdate

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
                builder.or(userInfo.modifiedDate.loe(localDateTime)); // isrtDate <= edate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }



    @Override
    public List<UserInfoApiDto> searchFindAllDesc() {
        List<UserInfoApiDto> content = queryFactory.
                select(Projections.constructor(UserInfoApiDto.class,
                        userInfo.id,
                        userInfo.addr,
                        userInfo.tel,
                        userInfo.email,
                        userInfo.password,
                        userInfo.isDel,
                        userInfo.modifiedDate,
                        userInfo.createdDate,
                        userInfo.user              )).from(userInfo).where(userInfo.isDel.eq("N"))
                        .leftJoin(userInfo.user, user)
                .orderBy(userInfo.id.asc())
                .fetch();


        return content;
    }
}