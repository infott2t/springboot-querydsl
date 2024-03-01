package org.example.domain.useruploadgradeadmin;
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

import static org.example.domain.useruploadgradeadmin.QUserUploadGradeAdmin.userUploadGradeAdmin;
import static org.example.domain.user.QUser.user;
import static org.springframework.util.StringUtils.hasText;



public class UserUploadGradeAdminRepositoryImpl implements UserUploadGradeAdminRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserUploadGradeAdminRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


   @Override
    public Page<UserUploadGradeAdminApiDto> searchAllV3(UserUploadGradeAdminSearchCondition2 condition2, Pageable pageable) {

        List<UserUploadGradeAdminApiDto> content = queryFactory.
                select(Projections.constructor(UserUploadGradeAdminApiDto.class,
                        userUploadGradeAdmin.id,
                        userUploadGradeAdmin.email,
                        userUploadGradeAdmin.certNum,
                        userUploadGradeAdmin.originFile,
                        userUploadGradeAdmin.uuidPath,
                        userUploadGradeAdmin.whoPermit,
                        userUploadGradeAdmin.isPermit,
                        userUploadGradeAdmin.isDel,
                        userUploadGradeAdmin.modifiedDate,
                        userUploadGradeAdmin.createdDate,
                        userUploadGradeAdmin.user              )).from(userUploadGradeAdmin)
                        .leftJoin(userUploadGradeAdmin.user, user)
                .where(
                        searchAllV3Predicate(condition2)
                ).where(userUploadGradeAdmin.isDel.eq("N"))
                .orderBy(userUploadGradeAdmin.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(userUploadGradeAdmin.count())
                .from(userUploadGradeAdmin)
                .where(
                        searchAllV3Predicate(condition2)
                ).where(userUploadGradeAdmin.isDel.eq("N"))
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate searchAllV3Predicate(UserUploadGradeAdminSearchCondition2 condition2) {
        return new BooleanBuilder()
                        .and(condId(condition2.getId()))
                        .and(condEmail(condition2.getEmail()))
                        .and(condCertNum(condition2.getCertNum()))
                        .and(condOriginFile(condition2.getOriginFile()))
                        .and(condUuidPath(condition2.getUuidPath()))
                        .and(condWhoPermit(condition2.getWhoPermit()))
                        .and(condIsPermit(condition2.getIsPermit()))
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
            builder.or(userUploadGradeAdmin.id.eq(Long.valueOf(id)));
        }
        return builder;
    }

    private Predicate condEmail(String email) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(email)){
            builder.or(userUploadGradeAdmin.email.eq(email));
        }
        return builder;
    }

    private Predicate condCertNum(String certNum) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(certNum)){
            builder.or(userUploadGradeAdmin.certNum.eq(certNum));
        }
        return builder;
    }

    private Predicate condOriginFile(String originFile) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(originFile)){
            builder.or(userUploadGradeAdmin.originFile.eq(originFile));
        }
        return builder;
    }

    private Predicate condUuidPath(String uuidPath) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(uuidPath)){
            builder.or(userUploadGradeAdmin.uuidPath.eq(uuidPath));
        }
        return builder;
    }

    private Predicate condWhoPermit(String whoPermit) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(whoPermit)){
            builder.or(userUploadGradeAdmin.whoPermit.eq(Long.valueOf(whoPermit)));
        }
        return builder;
    }

    private Predicate condIsPermit(String isPermit) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(isPermit)){
            builder.or(userUploadGradeAdmin.isPermit.eq(isPermit));
        }
        return builder;
    }

    private Predicate condIsDel(String isDel) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(isDel)){
            builder.or(userUploadGradeAdmin.isDel.eq(isDel));
        }
        return builder;
    }

    private Predicate condModifiedDate(String modifiedDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(modifiedDate)){
            builder.or(userUploadGradeAdmin.modifiedDate.eq(LocalDateTime.parse(modifiedDate)));
        }
        return builder;
    }

    private Predicate condCreatedDate(String createdDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(createdDate)){
            builder.or(userUploadGradeAdmin.createdDate.eq(LocalDateTime.parse(createdDate)));
        }
        return builder;
    }

    private Predicate condUserId (String userId) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(userId)){
            builder.or(userUploadGradeAdmin.user.id.eq(Long.valueOf(userId)));
        }
        return builder;
    }


    private Predicate condS2(String field, String s) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(field) && hasText(s)){
            if(field.equals("id")){
                builder.or(userUploadGradeAdmin.id.eq(Long.valueOf(s)));
            }
            if(field.equals("email")){
                builder.or(userUploadGradeAdmin.email.like("%"+s+"%"));
            }
            if(field.equals("certNum")){
                builder.or(userUploadGradeAdmin.certNum.like("%"+s+"%"));
            }
            if(field.equals("originFile")){
                builder.or(userUploadGradeAdmin.originFile.like("%"+s+"%"));
            }
            if(field.equals("uuidPath")){
                builder.or(userUploadGradeAdmin.uuidPath.like("%"+s+"%"));
            }
            if(field.equals("whoPermit")){
                builder.or(userUploadGradeAdmin.whoPermit.eq(Long.valueOf(s)));
            }
            if(field.equals("isPermit")){
                builder.or(userUploadGradeAdmin.isPermit.like("%"+s+"%"));
            }
            if(field.equals("isDel")){
                builder.or(userUploadGradeAdmin.isDel.like("%"+s+"%"));
            }
            if(field.equals("modifiedDate")){
                builder.or(userUploadGradeAdmin.modifiedDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("createdDate")){
                builder.or(userUploadGradeAdmin.createdDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("userId")){
                builder.or(userUploadGradeAdmin.user.id.eq(Long.valueOf(s)));
            }
        }
        return builder;
    }

    private Predicate condSdate2(String sdate) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(sdate)){
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(sdate + "T00:00:00");
                builder.or(userUploadGradeAdmin.modifiedDate.goe(localDateTime)); // isrtDate >= sdate

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
                builder.or(userUploadGradeAdmin.modifiedDate.loe(localDateTime)); // isrtDate <= edate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }


    @Override
    public Page<UserUploadGradeAdminApiDto> searchAllV2(UserUploadGradeAdminSearchCondition condition, Pageable pageable) {

        List<UserUploadGradeAdminApiDto> content = queryFactory.
                select(Projections.constructor(UserUploadGradeAdminApiDto.class,
                        userUploadGradeAdmin.id,
                        userUploadGradeAdmin.email,
                        userUploadGradeAdmin.certNum,
                        userUploadGradeAdmin.originFile,
                        userUploadGradeAdmin.uuidPath,
                        userUploadGradeAdmin.whoPermit,
                        userUploadGradeAdmin.isPermit,
                        userUploadGradeAdmin.isDel,
                        userUploadGradeAdmin.modifiedDate,
                        userUploadGradeAdmin.createdDate,
                        userUploadGradeAdmin.user              )).from(userUploadGradeAdmin)
                        .leftJoin(userUploadGradeAdmin.user, user)
                .where(
                        searchAllV2Predicate(condition)
                ).where(userUploadGradeAdmin.isDel.eq("N"))
                .orderBy(userUploadGradeAdmin.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(userUploadGradeAdmin.count())
                .from(userUploadGradeAdmin)
                .where(
                        searchAllV2Predicate(condition)
                ).where(userUploadGradeAdmin.isDel.eq("N"))
                .fetch().get(0);

        return new PageImpl<>(content, pageable, total);
    }





    private BooleanBuilder searchAllV2Predicate(UserUploadGradeAdminSearchCondition condition){
        return new BooleanBuilder()
                .and(condS(condition.getField(), condition.getS()))
                .and(condSdate(condition.getSdate()))
                .and(condEdate(condition.getEdate()));

    }

    private Predicate condS(String field, String s) {
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(field) && hasText(s)){
            if(field.equals("id")){
                builder.or(userUploadGradeAdmin.id.eq(Long.valueOf(s)));
            }
            if(field.equals("email")){
                builder.or(userUploadGradeAdmin.email.like("%"+s+"%"));
            }
            if(field.equals("certNum")){
                builder.or(userUploadGradeAdmin.certNum.like("%"+s+"%"));
            }
            if(field.equals("originFile")){
                builder.or(userUploadGradeAdmin.originFile.like("%"+s+"%"));
            }
            if(field.equals("uuidPath")){
                builder.or(userUploadGradeAdmin.uuidPath.like("%"+s+"%"));
            }
            if(field.equals("whoPermit")){
                builder.or(userUploadGradeAdmin.whoPermit.eq(Long.valueOf(s)));
            }
            if(field.equals("isPermit")){
                builder.or(userUploadGradeAdmin.isPermit.like("%"+s+"%"));
            }
            if(field.equals("isDel")){
                builder.or(userUploadGradeAdmin.isDel.like("%"+s+"%"));
            }
            if(field.equals("modifiedDate")){
                builder.or(userUploadGradeAdmin.modifiedDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("createdDate")){
                builder.or(userUploadGradeAdmin.createdDate.eq(LocalDateTime.parse(s)));
            }
            if(field.equals("userId")){
                builder.or(userUploadGradeAdmin.user.id.eq(Long.valueOf(s)));
            }
        }
        return builder;
    }

    private Predicate condSdate( String sdate){
        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(sdate)){
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(sdate + "T00:00:00");
                builder.or(userUploadGradeAdmin.modifiedDate.goe(localDateTime)); // isrtDate >= sdate

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
                builder.or(userUploadGradeAdmin.modifiedDate.loe(localDateTime)); // isrtDate <= edate

            } catch (DateTimeParseException e) {
            }
        }
        return builder;
    }



    @Override
    public List<UserUploadGradeAdminApiDto> searchFindAllDesc() {
        List<UserUploadGradeAdminApiDto> content = queryFactory.
                select(Projections.constructor(UserUploadGradeAdminApiDto.class,
                        userUploadGradeAdmin.id,
                        userUploadGradeAdmin.email,
                        userUploadGradeAdmin.certNum,
                        userUploadGradeAdmin.originFile,
                        userUploadGradeAdmin.uuidPath,
                        userUploadGradeAdmin.whoPermit,
                        userUploadGradeAdmin.isPermit,
                        userUploadGradeAdmin.isDel,
                        userUploadGradeAdmin.modifiedDate,
                        userUploadGradeAdmin.createdDate,
                        userUploadGradeAdmin.user              )).from(userUploadGradeAdmin).where(userUploadGradeAdmin.isDel.eq("N"))
                        .leftJoin(userUploadGradeAdmin.user, user)
                .orderBy(userUploadGradeAdmin.id.asc())
                .fetch();


        return content;
    }
}