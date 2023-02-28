package com.warehousemanagementsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehousemanagementsystem.entity.Warehouse;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-14
 */
public interface WarehouseMapper extends BaseMapper<Warehouse> {
    @Select("select wh.wh_id as whId,wh_name as whName,wh_capacity as whCapacity,sum(wa.wa_capacity) as waCapacity from warehouse wh inner join warehouse_area wa on wh.wh_id=wa.wh_id group by wh.wh_id")
    List<Map<String, Object>> myGroupBy();

}
