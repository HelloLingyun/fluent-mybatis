package cn.org.atool.fluent.mybatis.base;

import cn.org.atool.fluent.mybatis.base.mapper.IRichMapper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * IBaseDao Dao基本操作方法
 *
 * @param <E> 实体类
 * @Author darui.wu
 * @Date 2019-06-25 12:00
 */
public interface IBaseDao<E extends IEntity> {
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @param <PK>   主键类型
     * @return 返回记录主键
     */
    default <PK extends Serializable> PK save(E entity) {
        PK pk = (PK) this.mapper().save(entity);
        return pk;
    }

    /**
     * 批量插入
     * 列表实例的主键必须全赋值，或者全不赋值
     *
     * @param list 实体对象列表
     * @return 插入记录数
     */
    default int save(Collection<E> list) {
        int count = this.mapper().save(list);
        return count;
    }

    /**
     * <p>
     * 根据主键判断记录是否已经存在
     * o 是：更新记录
     * o 否：插入记录
     * </p>
     *
     * @param entity 实体对象
     * @return 更新或者插入成功
     */
    default boolean saveOrUpdate(E entity) {
        return this.mapper().saveOrUpdate(entity);
    }

    /**
     * 根据id查询
     *
     * @param id 主键值
     * @return 结果对象
     */
    default E selectById(Serializable id) {
        Object obj = this.mapper().findById(id);
        return (E) obj;
    }

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    default List<E> selectByIds(Serializable... ids) {
        List<E> list = this.mapper().listByIds(Arrays.asList(ids));
        return list;
    }

    /**
     * 根据id列表查询
     *
     * @param ids 主键列表
     * @return 结果列表
     */
    default List<E> selectByIds(Collection<? extends Serializable> ids) {
        List<E> list = this.mapper().listByIds(ids);
        return list;
    }

    /**
     * 根据where key值构造条件查询
     *
     * @param where 条件，忽略null值
     * @return 结果列表
     */
    default List<E> selectByMap(Map<String, Object> where) {
        List<E> list = this.mapper().listByMapAndDefault(where);
        return list;
    }

    /**
     * 判断主键id记录是否已经存在
     *
     * @param id 主键值
     * @return true: 记录存在; false: 记录不存在
     */
    default boolean existPk(Serializable id) {
        return this.mapper().existPk(id);
    }

    /**
     * 根据entity的主键批量修改entity中非null属性
     *
     * @param entities 实体对象列表
     * @return 是否更新成功
     */
    boolean updateEntityByIds(E... entities);

    /**
     * 根据entity的主键批量修改entity中非null属性
     *
     * @param entities 实体对象列表
     * @return 是否更新成功
     */
    default boolean updateEntityByIds(Collection<E> entities) {
        return this.updateEntityByIds((E[]) entities.toArray());
    }

    /**
     * 根据whereNoN非空属性作为相等条件, 更新updateNoN非空属性字段
     *
     * @param updateNoN
     * @param whereNoN
     * @return
     */
    int updateBy(E updateNoN, E whereNoN);

    /**
     * 根据entities中的id值，批量删除记录
     *
     * @param entities
     * @return 被执行的记录数
     */
    default int deleteByEntityIds(Collection<E> entities) {
        int count = this.mapper().deleteByEntityIds(entities);
        return count;
    }

    /**
     * 根据entities中的id值，批量删除记录
     *
     * @param entities
     * @return 被执行的记录数
     */
    default int deleteByEntityIds(E... entities) {
        int count = this.mapper().deleteByEntityIds(entities);
        return count;
    }

    /**
     * 根据ids列表批量删除记录
     *
     * @param ids 主键列表
     * @return 被执行的记录数
     */
    default int deleteByIds(Collection<? extends Serializable> ids) {
        int count = this.mapper().deleteByIds(ids);
        return count;
    }

    /**
     * 根据id删除记录
     *
     * @param ids 主键值
     * @return 是否删除成功
     */
    default boolean deleteById(Serializable... ids) {
        int count;
        if (ids.length == 1) {
            count = this.mapper().deleteById(ids[0]);
        } else {
            List list = Arrays.asList(ids);
            count = this.mapper().deleteByIds(list);
        }
        return count > 0;
    }

    /**
     * 根据map构造条件删除记录
     *
     * @param map 条件, 忽略null值
     * @return 被执行的记录数
     */
    default int deleteByMap(Map<String, Object> map) {
        int count = this.mapper().deleteByMapAndDefault(map);
        return count;
    }

    /**
     * 获取对应entity的BaseMapper
     *
     * @return
     */
    IRichMapper mapper();
}