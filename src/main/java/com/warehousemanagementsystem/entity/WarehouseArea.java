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
 * @since 2022-10-19
 */
@Data
@TableName("warehouse_area")
public class WarehouseArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库区编号
     */
    @TableId(value = "wa_id", type = IdType.AUTO)
    private Integer waId;

    /**
     * 所属仓库
     */
    private Integer whId;

    /**
     * 库区名称
     */
    private String waName;

    /**
     * 库区地址
     */
    private String waAddr;

    /**
     * 库区总容量
     */
    private Integer waCapacity;

    /**
     * 可用容量
     */
    private Integer waAvailableCapacity;

    /**
     * 库区备注
     */
    private String waNote;

    /**
     * 库区状态（0-未使用 1已使用）
     */
    private Integer waStatus;

}
