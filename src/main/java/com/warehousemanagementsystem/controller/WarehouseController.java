package com.warehousemanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.entity.Warehouse;
import com.warehousemanagementsystem.service.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-14
 */
@RestController
@CrossOrigin
public class WarehouseController {
    @Autowired
    private WarehouseServiceImpl service;

    @RequestMapping(value = "/warehouse", method = RequestMethod.GET)
    public RestResult<Warehouse> queryAll() {
        try {
            return RestResult.success(0, "查询所有成功", service.list());
        } catch (Exception e) {
            return RestResult.error(1, "查询所有失败");
        }
    }

    //模糊分页
    @RequestMapping(value = "/warehousePage", method = RequestMethod.GET)
    public RestResult<Warehouse> queryByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size,String keyword) {
        QueryWrapper<Warehouse> wrapper = new QueryWrapper<>();
        if (keyword != null) {
            wrapper.like("wh_name",keyword);
        }
        try {
            return RestResult.success(0, "分页模糊查询成功", service.page(new Page<>(page, size), wrapper));
        } catch (Exception e) {
            return RestResult.error(1, "分页模糊失败");
        }
    }

    //    添加
    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    public RestResult insert(Warehouse warehouse) {
        boolean t;
        try {
            if (warehouse.getWhId() != null) {
                throw new Exception("id不为空");
            }
            if ((t = service.saveOrUpdate(warehouse))) {
                return RestResult.success(0, "添加成功", t);
            } else {
                return RestResult.success(0, "添加失败", t);
            }

        } catch (Exception e) {
            return RestResult.error(1, "添加异常" + e.getMessage());
        }
    }

    //修改
    @RequestMapping(value = "/warehouse", method = RequestMethod.PUT)
    public RestResult updata(Warehouse warehouse) {
        boolean t;
        try {
            if (warehouse.getWhId() == null) {
                throw new Exception("id为空");
            }
            if ((t = service.saveOrUpdate(warehouse))) {
                return RestResult.success(0, "修改成功", t);
            } else {
                return RestResult.success(0, "修改失败", t);
            }

        } catch (Exception e) {
            return RestResult.error(1, "修改异常" + e.getMessage());
        }
    }

    //    删除
    @RequestMapping(value = "/warehouse/{whId}", method = RequestMethod.DELETE)
    public RestResult delete(@PathVariable("whId") Integer whId) {
        boolean t;
        try {
            if (whId == null) {
                throw new Exception("id为空");
            }
            if ((t = service.removeById(whId))) {
                return RestResult.success(0, "删除成功", t);
            } else {
                return RestResult.error(1, "删除失败", t);
            }

        } catch (Exception e) {
            return RestResult.error(1, "删除异常" + e.getMessage());
        }
    }

    //    根据Id查询单个
    @RequestMapping("/warehouse/{whId}")
    public RestResult findById(@PathVariable(value = "whId") Integer whId) {
        try {
            return RestResult.success(0, "单个查询成功", service.getById(whId));
        } catch (Exception e) {
            return RestResult.error(1, "单个查询失败");
        }
    }
}
