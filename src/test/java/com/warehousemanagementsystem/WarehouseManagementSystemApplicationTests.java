package com.warehousemanagementsystem;

import com.warehousemanagementsystem.controller.WarehouseAreaController;
import com.warehousemanagementsystem.controller.WarehouseController;
import com.warehousemanagementsystem.service.IWarehouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WarehouseManagementSystemApplicationTests {
    @Autowired
    private WarehouseController controller;
    @Autowired
    private IWarehouseService warehouseService;
    @Autowired
    private WarehouseAreaController warehouseAreaController;

    @Test
    void contextLoads() {
        // System.out.println(controller.queryAll());
        // System.out.println(warehouseService.list());
        System.out.println(warehouseAreaController.queryByPage(1, 5, ""));
    }

}
