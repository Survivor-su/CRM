package com.warehousemanagementsystem.controller;

import com.warehousemanagementsystem.annotation.Auth;
import com.warehousemanagementsystem.entity.CapacityOrWh;
import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.entity.WaCount;
import com.warehousemanagementsystem.entity.Warehouse;
import com.warehousemanagementsystem.mapper.WaCountOfWhMapper;
import com.warehousemanagementsystem.service.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Auth(role = "ADMIN,USER")
public class WaCountOfmapper {
    @Autowired
    private WaCountOfWhMapper mapper;
    @Autowired
    private WarehouseServiceImpl service;
    //当前只允许role为ADMIN或USER的用户访问

    @RequestMapping(value = "wa_count_of_mapper", method = RequestMethod.GET)
    public RestResult mySelect() {
        List<WaCount> waCounts = mapper.mySelect();

        Map<String, List> map = new HashMap<>();
        ArrayList<String> xAxis = new ArrayList<>();
        ArrayList<Integer> series = new ArrayList<>();
        for (WaCount waCount : waCounts) {
            xAxis.add(waCount.getWhName());
            series.add(waCount.getWaCount());
        }
        map.put("xAxis", xAxis);
        map.put("series", series);
        return RestResult.success(0, "柱状图查询成功", map);
    }

    @RequestMapping("capacity_oh_wh")
    public RestResult capacityOfWh() {
        List<Warehouse> list = service.list();
        List<CapacityOrWh> whList = new ArrayList<>();
        for (Warehouse warehouse : list) {
            whList.add(new CapacityOrWh(warehouse.getWhName(), warehouse.getWhCapacity()));
        }
        return RestResult.success(0, "饼状图查询成功", whList);
    }
}
