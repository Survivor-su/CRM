package com.warehousemanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.entity.Warehouse;
import com.warehousemanagementsystem.service.impl.WarehouseServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
@Api(tags = "仓库warehouse模块")
public class WarehouseController {
    @Autowired
    private WarehouseServiceImpl service;
    //自动装配,用于操作redis命令的模板工具
    @Autowired(required = false)
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "查询所有", notes = "详细描述")
    @RequestMapping(value = "/warehouse", method = RequestMethod.GET)
    public RestResult<Warehouse> queryAll() {
        try {
            return RestResult.success(0, "查询所有成功", service.list());
        } catch (Exception e) {
            return RestResult.error(1, "查询所有失败");
        }
    }

    @ApiOperation(value = "查询仓库对应的所有库区容量", notes = "详细描述")
    @RequestMapping(value = "/warehouseCapacity", method = RequestMethod.GET)
    public RestResult myGroupBy() {
        try {
            return RestResult.success(0, "查询对象及容量成功", service.myGroupBy());
        } catch (Exception e) {
            return RestResult.error(1, "查询对象及容量失败");
        }
    }

    //模糊分页
    @RequestMapping(value = "/warehousePage", method = RequestMethod.GET)
    public RestResult<Warehouse> queryByPage(@RequestParam(defaultValue = "1") @ApiParam(value = "当前页数默认为1") Integer page, @ApiParam(value = "每页显示记录数") @RequestParam(defaultValue = "5") Integer size, String keyword) {
        QueryWrapper<Warehouse> wrapper = new QueryWrapper<>();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Page<Warehouse> warehousePage = null;
        ObjectMapper jsonTool = new ObjectMapper();
        try {
            if (page == 1 && size == 5 && keyword == "" && ops.get("page") != null) {
                return RestResult.success(0, "分页模糊查询成功1", jsonTool.readValue(ops.get("page"), Page.class));
            } else if (page == 1 && size == 5 && keyword == "") {

                warehousePage = service.page(new Page<>(page, size));
                ops.set("page", jsonTool.writeValueAsString(warehousePage));
                return RestResult.success(0, "分页模糊查询成功2", warehousePage);
            } else {
                wrapper.like("wh_name", keyword);
                warehousePage = service.page(new Page<>(page, size), wrapper);
                return RestResult.success(0, "分页模糊查询成功3", warehousePage);
            }

        } catch (Exception e) {
            return RestResult.error(1, "分页模糊查询失败" + e.getMessage()
            );
        }
    }

    //    添加
    @ApiOperation(value = "添加", notes = "详细描述")
    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    public RestResult insert(@ApiParam(value = "完整的warehouse对象", required = true) Warehouse warehouse) {
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
                redisTemplate.delete("page");
                return RestResult.success(0, "修改成功", t);

            } else {
                return RestResult.success(0, "修改失败", t);
            }

        } catch (Exception e) {
            return RestResult.error(1, "修改异常" + e.getMessage());
        }
    }

    //    删除
    //导入slf4j.参数为当前类
    Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @RequestMapping(value = "/warehouse/{whId}", method = RequestMethod.DELETE)
    public RestResult delete(@PathVariable("whId") Integer whId) {

        boolean t;
        try {
            if (whId == null) {
                throw new Exception("id为空");
            }
            if ((t = service.removeById(whId))) {
                redisTemplate.delete("page");
                logger.info("进行删除编号为" + whId + "的仓库操作");
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
    public RestResult findById(@PathVariable(value = "whId") Integer whId) throws JsonProcessingException {
        /*
         * 假如编号为1000的对象要被经常查询,因为要连数据库,所以将其结果保存到redis中
         * 就能不通过mysql就能就直接访问
         * 第一次访问id为1000时,使用MySQL查询,后保存在redis中再次查询时,就从Redis中获取
         * */

        //SpringBoot中继承了jackson包,用于对象与JSON的转换


/*        ObjectMapper objectMapper = new ObjectMapper();

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        //如果查询的whid为1000且在Redis中,不使用mysql查询
        Warehouse warehouse;

        if (whId == 1000 && ops.get("wh") != null) {
            //读取Redis中的JSON字符串
            String wh = ops.get("wh");
            warehouse = objectMapper.readValue(wh, Warehouse.class);

        } else
        //如果查询的whid为1000且不在Redis中,使用mysql查询
        {
            warehouse = service.getById(whId);
            // 将查询的对象转换为JSON字符串
            String s = objectMapper.writeValueAsString(warehouse);
            //将JSON字符串保存到Redis中
            ops.set("wh", s);
        }
        return RestResult.success(0, "查询单个查询成功", warehouse);
*/


        try {
            return RestResult.success(0, "单个查询成功", service.getById(whId));
        } catch (Exception e) {
            return RestResult.error(1, "单个查询失败");
        }
    }
}
