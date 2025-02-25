package cn.org.atool.fluent.mybatis.method;

import cn.org.atool.fluent.mybatis.generate.entity.StudentEntity;
import cn.org.atool.fluent.mybatis.generate.mapper.StudentMapper;
import cn.org.atool.fluent.mybatis.generate.wrapper.StudentUpdate;
import cn.org.atool.fluent.mybatis.refs.FieldRef;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class UpdateByEntity extends BaseTest {
    @Autowired
    StudentMapper mapper;

    @Test
    void byEntity() {
        StudentEntity student = new StudentEntity()
            .setId(1L)
            .setUserName("test")
            .setAddress("test");
        mapper.updateBy(mapper.updater()
            .set.byEntity(student).end()
            .where.id().eq(1).end()
        );
        db.sqlList().wantFirstSql().eq("" +
            "UPDATE fluent_mybatis.student " +
            "SET `gmt_modified` = now(), `address` = ?, `user_name` = ? " +
            "WHERE `id` = ?");
        db.sqlList().wantFirstPara().eqList("test", "test", 1);
    }

    @DisplayName("按Entity指定字段列表更新")
    @Test
    void byEntity_spec() {
        StudentEntity student = new StudentEntity()
            .setId(1L)
            .setUserName("test")
            .setAddress("test");

        StudentUpdate updater = mapper.updater()
            .set.byEntity(student, FieldRef.Student.userName, FieldRef.Student.grade).end()
            .where.id().eq(1).end();
        mapper.updateBy(updater);

        db.sqlList().wantFirstSql().eq("" +
            "UPDATE fluent_mybatis.student " +
            "SET `gmt_modified` = now(), `user_name` = ?, `grade` = ? " +
            "WHERE `id` = ?");
        db.sqlList().wantFirstPara().eqList("test", null, 1);
    }

    @Test
    void byEntity_Getter() {
        StudentEntity student = new StudentEntity()
            .setId(1L)
            .setUserName("test")
            .setAddress("test");

        StudentUpdate updater = mapper.updater()
            .set.byEntity(student, StudentEntity::getUserName, StudentEntity::getGrade).end()
            .where.id().eq(1).end();
        mapper.updateBy(updater);

        db.sqlList().wantFirstSql().eq("" +
            "UPDATE fluent_mybatis.student " +
            "SET `gmt_modified` = now(), `user_name` = ?, `grade` = ? " +
            "WHERE `id` = ?");
        db.sqlList().wantFirstPara().eqList("test", null, 1);
    }

    @DisplayName("按Entity排除字段更新, 未指定排除")
    @Test
    void byEntity_Exclude() {
        StudentEntity student = new StudentEntity()
            .setId(1L)
            .setUserName("test")
            .setAddress("test")
            .setTenant(122L);

        StudentUpdate updater = mapper.updater()
            .set.byExclude(student).end()
            .where.id().eq(1).end();
        mapper.updateBy(updater);

        db.sqlList().wantFirstSql()
            .contains("`user_name` = ?,")
            .contains("`gmt_modified` = ?,")
            .contains("`age` = ?,")
            .contains("`birthday` = ?,")
            .notContain(", `id` = ?,")
            .notContain("`gmt_modified` = now(),")
            .end("WHERE `id` = ?")
        ;
    }

    @DisplayName("按Entity排除字段更新")
    @Test
    void byEntity_ExcludeSpec() {
        StudentEntity student = new StudentEntity()
            .setId(1L)
            .setUserName("test")
            .setAddress("test");

        StudentUpdate updater = mapper.updater()
            .set.byExclude(student,
                FieldRef.Student.id,
                FieldRef.Student.address,
                FieldRef.Student.tenant,
                FieldRef.Student.birthday).end()
            .where.id().eq(1).end();
        mapper.updateBy(updater);

        db.sqlList().wantFirstSql()
            .contains("`user_name` = ?,")
            .contains("`gmt_modified` = ?,")
            .notContain(", `id` = ?,")
            .notContain("`address` = ?,")
            .notContain("`birthday` = ?,")
            .end("WHERE `id` = ?")
        ;
    }

    @DisplayName("按Entity排除字段更新")
    @Test
    void byEntity_ExcludeByGetter() {
        StudentEntity student = new StudentEntity()
            .setUserName("test")
            .setTenant(124L)
            .setAddress("test");

        StudentUpdate updater = mapper.updater()
            .set.byExclude(student,
                StudentEntity::getAddress,
                StudentEntity::getBirthday,
                StudentEntity::getGmtModified).end()
            .where.id().eq(1).end();
        mapper.updateBy(updater);

        db.sqlList().wantFirstSql()
            .contains("`user_name` = ?,")
            .contains("`gmt_modified` = now(),")
            .notContain(", `id` = ?,")
            .notContain("`address` = ?,")
            .notContain("`birthday` = ?,")
            .end("WHERE `id` = ?")
        ;
    }
}
