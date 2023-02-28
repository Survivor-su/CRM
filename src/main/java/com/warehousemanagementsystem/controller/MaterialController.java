package com.warehousemanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehousemanagementsystem.entity.Material;
import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.service.impl.MaterialServiceImpl;
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
public class MaterialController {
    @Autowired
    private MaterialServiceImpl service;

    @RequestMapping(value = "material", method = RequestMethod.GET)
    public RestResult<Material> queryAll() {
        try {
            return RestResult.success(0, "查询所有成功", service.list());
        } catch (Exception e) {
            return RestResult.error(1, "查询所有失败" + e.getMessage());
        }
    }

    @RequestMapping(value = "materialPage", method = RequestMethod.GET)
    public RestResult<Material> queryByPage(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "5") Integer size,
                                            @RequestParam(defaultValue = "") String keyword) {
        try {
            return RestResult.success(0, "分页模糊查询成功", service.page(new Page<>(page, size), new QueryWrapper<Material>().like("name", keyword)));
        } catch (Exception e) {
            return RestResult.error(1, "分页查询失败" + e.getMessage());
        }
    }

    @PostMapping("material")
    public RestResult insert(Material material) {
        boolean t;
        try {
            if (t = service.saveOrUpdate(material)) {
                return RestResult.success(0, "添加成功", t);
            } else {
                return RestResult.success(1, "添加失败", t);
            }
        } catch (Exception e) {
            return RestResult.success(1, "添加失败" + e.getMessage());
        }
    }

    @PutMapping("material")
    public RestResult updata(Material material) {
        try {
            return RestResult.success(0, "修改物料成功", service.saveOrUpdate(material));
        } catch (Exception e) {
            return RestResult.success(0, "修改物料失败" + e.getMessage());
        }
    }

    //    删除
    @RequestMapping(value = "/material/{whId}", method = RequestMethod.DELETE)
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
}
