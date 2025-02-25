package cn.org.atool.fluent.mybatis.test.basedao;

import cn.org.atool.fluent.mybatis.customize.StudentExtDao;
import cn.org.atool.fluent.mybatis.generate.ATM;
import cn.org.atool.fluent.mybatis.generate.helper.StudentMapping;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @author darui.wu
 * @create 2019/10/29 9:36 下午
 */
public class DeleteByMapTest extends BaseTest {
    @Autowired
    private StudentExtDao dao;

    @Test
    public void test_deleteByMap() throws Exception {
        ATM.dataMap.student.initTable(10)
            .userName.values("test1", "test12", "test3", "test12", "tess2")
            .env.values("test_env")
            .cleanAndInsert();
        dao.deleteByMap(new HashMap<String, Object>() {
            {
                this.put(StudentMapping.userName.column, "test12");
            }
        });
        db.sqlList().wantFirstSql().eq("" +
            "DELETE FROM fluent_mybatis.student " +
            "WHERE `is_deleted` = ? AND `env` = ? AND `user_name` = ?");
        db.table(ATM.table.student).count().eq(8);
    }

    @Test
    public void test_logicDeleteByMap() throws Exception {
        dao.logicDeleteByMap(new HashMap<String, Object>() {
            {
                this.put(StudentMapping.userName.column, "test12");
            }
        });
        db.sqlList().wantFirstSql().eq("" +
            "UPDATE fluent_mybatis.student SET `is_deleted` = true " +
            "WHERE `is_deleted` = ? AND `env` = ? AND `user_name` = ?");
        db.sqlList().wantFirstPara().eq(new Object[]{false, "test_env", "test12"});
    }
}
