package com.warehousemanagementsystem.mapper;

import com.warehousemanagementsystem.entity.WaCount;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaCountOfWhMapper {
    @Select("select wh_name,count(wa_id) as wa_count from warehouse left join warehouse_area on warehouse.wh_id=warehouse_area.wh_id group by warehouse.wh_id")
    List<WaCount> mySelect();
}
