package cn.org.atool.fluent.mybatis.generate.entity;

import cn.org.atool.fluent.mybatis.annotation.*;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.customize.IBaseEntity;
import cn.org.atool.fluent.mybatis.customize.MyCustomerInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LongTypeHandler;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author generate code
 */
@Getter
@Setter
@Accessors(chain = true)
@FluentMybatis(
    daoInterface = @DaoInterface(value = MyCustomerInterface.class, args = {ParaType.Entity})
)
public class AddressEntity implements IEntity, IBaseEntity<AddressEntity> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     *
     */
    @TableField(value = "gmt_created", insert = "now()")
    private Date gmtCreated;
    /**
     *
     */
    @TableField(value = "gmt_modified", insert = "now()", update = "now()")
    private Date gmtModified;
    /**
     *
     */
    @TableField(value = "is_deleted", insert = "0")
    private Boolean isDeleted;
    /**
     *
     */
    @TableField(value = "address")
    private String address;
    /**
     * user id
     */
    @TableField(value = "user_id", typeHandler = LongTypeHandler.class, jdbcType = JdbcType.NUMERIC)
    private Long userId;

    @Override
    public Serializable findPk() {
        return id;
    }
}