package cn.org.atool.fluent.mybatis.base.crud;

import cn.org.atool.fluent.mybatis.base.splice.FreeQuery;
import cn.org.atool.fluent.mybatis.functions.QFunction;
import cn.org.atool.fluent.mybatis.segment.JoinQuery;

/**
 * 连接查询构造
 *
 * @author wudarui
 */
@SuppressWarnings({"unchecked", "unused", "rawtypes"})
public interface JoinBuilder<QL extends BaseQuery<?, QL>> {

    /**
     * 关联查询构造方式一: 使用直接传入设置好别名和参数的Query
     *
     * @param query 查询器
     * @param <QL>  左查询类型
     * @return ignore
     */
    static <QL extends BaseQuery<?, QL>> JoinBuilder1<QL> from(QL query) {
        return new JoinQuery<>(query);
    }

    /**
     * ... from (select query) alias
     *
     * @param query 查询器
     * @param alias 表别名
     * @param <QL>  左查询类型
     * @return ignore
     */
    static <QL extends BaseQuery<?, QL>> JoinBuilder1<QL> from(QL query, String alias) {
        return new JoinQuery<>((QL) new FreeQuery(query, alias));
    }

    /**
     * 关联查询构造方式二: 使用lambda表达式,由框架自动设置query别名和关联参数
     * <p>
     * 注: 在有些场景下, IDE对lambda表达式的代码提示不够智能
     * <p>
     *
     * @param clazz 查询器类型
     * @param query 查询器
     * @param <QL>  左查询类型
     * @return ignore
     */
    static <QL extends BaseQuery<?, QL>> JoinBuilder2<QL> from(Class<QL> clazz, QFunction<QL> query) {
        return new JoinQuery<>(clazz, query);
    }

    /**
     * 显式指定查询字段
     *
     * @param columns 字段列表
     * @return ignore
     */
    JoinBuilder<QL> select(String... columns);

    /**
     * distinct
     *
     * @return ignore
     */
    JoinBuilder<QL> distinct();

    /**
     * limit 0, limit
     *
     * @param limit limit
     * @return ignore
     */
    JoinBuilder<QL> limit(int limit);

    /**
     * limit start, limit
     *
     * @param start from start
     * @param limit limit size
     * @return ignore
     */
    JoinBuilder<QL> limit(int start, int limit);

    /**
     * 追加在sql语句的末尾
     * !!!慎用!!!
     * 有sql注入风险
     *
     * @param lastSql 追加的SQL
     * @return ignore
     */
    JoinBuilder<QL> last(String lastSql);

    /**
     * 返回IQuery对象
     *
     * @return ignore
     */
    IQuery build();

    String[] getAlias();
}