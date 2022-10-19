package com.warehousemanagementsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.warehousemanagementsystem.entity.WarehouseArea;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-19
 */
public interface WarehouseAreaMapper extends BaseMapper<WarehouseArea> {
    /*    //方法一
        @Select("select warehouse_area.*,warehouse.wh_id as warehouse.whId" +
                "warehouse.wh_name as warehouse.whName," +
                "warehouse.wh_profit_center as warehouse.whProfitCenter," +
                "warehouse.wh_whAddr as warehouse.wh_addr," +
                "warehouse.wh_capacity as warehouse.whCapacity," +
                "warehouse.wh_notes as warehouse.whNotes" +
                " from warehouse inner join warehouse_area on warehouse.wh_id=warehouse_area.wh_id ${w.customSqlSegment}")//未使用where关键字
        */
    //方法二
    @Select("select * from warehouse_area where ${ew.sqlSegment}")
    @Results({
            @Result(property = "whId", column = "wh_id"),
            @Result(property = "warehouse", column = "wh_id", one = @One(select = "com.warehousemanagementsystem.mapper.WarehouseMapper.selectById"))
    })

    @Override
    <P extends IPage<WarehouseArea>> P selectPage(P page,@Param("ew") Wrapper<WarehouseArea> queryWrapper);
}
