package com.warehousemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-25
 */
@TableName("sys_user")
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表主键
     */
    @TableId(value = "su_id", type = IdType.AUTO)
    private Integer suId;

    private String suName;

    private String suPassword;

    private String suRole;

    private String suPhone;

    private String suEmail;

    private Integer suStatus;

}
