package com.warehousemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-14
 */
@Data
@ApiModel
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库编号
     */
    @TableId(value = "wh_id", type = IdType.AUTO)
    private Integer whId;

    /**
     * 仓库名称
     */
    private String whName;

    /**
     * 利润中心
     */
    @ApiModelProperty(value = "利润中心")
    private String whProfitCenter;

    /**
     * 地址
     */
    private String whAddr;

    /**
     * 容量
     */
    private Integer whCapacity;

    /**
     * 备注
     */
    private String whNotes;


}
