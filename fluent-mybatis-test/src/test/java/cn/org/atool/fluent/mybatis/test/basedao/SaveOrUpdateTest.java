package cn.org.atool.fluent.mybatis.test.basedao;

import cn.org.atool.fluent.mybatis.customize.StudentExtDao;
import cn.org.atool.fluent.mybatis.generate.ATM;
import cn.org.atool.fluent.mybatis.generate.entity.StudentEntity;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.test4j.hamcrest.matcher.string.StringMode;

/**
 * @author darui.wu
 * @create 2019/10/29 9:32 下午
 */
public class SaveOrUpdateTest extends BaseTest {
    @Autowired
    private StudentExtDao dao;

    @Test
    public void test_saveOrUpdate() throws Exception {
        ATM.dataMap.student.initTable(3)
            .cleanAndInsert();
        dao.saveOrUpdate(new StudentEntity().setId(3L).setUserName("test_111").setAge(30));
        db.sqlList().wantFirstSql().eq("SELECT COUNT(*) FROM student WHERE id = ? LIMIT ?, ?", StringMode.SameAsSpace);
        db.sqlList().wantSql(1).eq("UPDATE student SET gmt_modified = now(), age = ?, user_name = ? WHERE id = ?");
        db.table(ATM.table.student).queryWhere("id=3")
            .eqDataMap(ATM.dataMap.student.table(1)
                .userName.values("test_111")
                .age.values(30)
            );
    }

    @Test
    public void test_saveOrUpdate_2() throws Exception {
        ATM.dataMap.student.initTable(3)
            .cleanAndInsert();
        dao.saveOrUpdate(new StudentEntity().setId(4L).setUserName("test_111").setAge(30));
        db.sqlList().wantFirstSql().eq("SELECT COUNT(*) FROM student WHERE id = ? LIMIT ?, ?", StringMode.SameAsSpace);
        db.sqlList().wantSql(1).contains("INSERT INTO student");
        db.table(ATM.table.student).count().eq(4);
        db.table(ATM.table.student).queryWhere("id=4")
            .eqDataMap(ATM.dataMap.student.table(1)
                .userName.values("test_111")
                .age.values(30)
            );
    }
}
