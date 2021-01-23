package cn.org.atool.fluent.mybatis.test.segment2;

import cn.org.atool.fluent.mybatis.base.model.SqlOp;
import cn.org.atool.fluent.mybatis.generate.mapper.StudentMapper;
import cn.org.atool.fluent.mybatis.generate.wrapper.StudentQuery;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WhereApplyTest2 extends BaseTest {
    @Autowired
    private StudentMapper mapper;

    @Test
    void apply() {
        mapper.listEntity(new StudentQuery()
            .where.userName().applyFunc(SqlOp.EQ, "user_name+1").end()
        );
        db.sqlList().wantFirstSql()
            .end("WHERE user_name = user_name+1");
    }
}
