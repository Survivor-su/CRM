package com.warehousemanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.entity.WarehouseArea;
import com.warehousemanagementsystem.service.impl.WarehouseAreaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-19
 */
@RestController
@CrossOrigin
public class WarehouseAreaController {
    @Autowired
    private WarehouseAreaServiceImpl service;

    @GetMapping("warehouseAreaPage")
    public RestResult<WarehouseArea> queryByPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "") String keyword) {
        try {
            return RestResult.success(0, "模糊查询分页成功", service.page(new Page<>(1, 5), new QueryWrapper<WarehouseArea>().like("wa_name", keyword)));
        } catch (Exception e) {
            return RestResult.error(1, "分页模糊查询失败" + e.getMessage());
        }
    }

    @PutMapping("warehouseArea")
    public RestResult updata(WarehouseArea warehouseArea) {
        try {
            boolean t = service.saveOrUpdate(warehouseArea);
            if (t) {
                return RestResult.success(0, "修改成功", t);
            } else {
                return RestResult.success(1, "修改失败", t);
            }
        } catch (Exception e) {
            return RestResult.error(1, "修改异常" + e.getMessage());
        }
    }

    @PostMapping("warehouseArea")
    public RestResult insert(WarehouseArea warehouseArea) {
        try {
            boolean t = service.saveOrUpdate(warehouseArea);
            if (t) {
                return RestResult.success(0, "添加成功", t);
            } else {
                return RestResult.success(1, "添加失败", t);
            }
        } catch (Exception e) {
            return RestResult.error(1, "添加异常" + e.getMessage());
        }
    }

    @DeleteMapping("warehouseArea/{path}")
    public RestResult delete(@PathVariable("path") Integer waId) {
        try {
            boolean t = service.removeById(waId);
            if (t) {
                return RestResult.success(0, "删除成功", t);
            } else {
                return RestResult.success(1, "删除失败", t);
            }
        } catch (Exception e) {
            return RestResult.error(1, "删除异常" + e.getMessage());
        }
    }
}
