package com.warehousemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 子夕秋寒
 * @since 2023-02-28
 */
@Data
@TableName("input_record")
public class InputRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ir_id", type = IdType.AUTO)
    private Integer irId;

    /**
     * 入库时间
     */
    private LocalDateTime irTime;

    /**
     * 库区编号
     */
    private Integer waId;

    /**
     * 物料编号
     */
    private Integer mId;

    /**
     * 入库数量
     */
    private Integer irCount;

    /**
     * 管理员编号
     */
    private Integer suId;


}
