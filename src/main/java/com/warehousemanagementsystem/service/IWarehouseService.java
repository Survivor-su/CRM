package com.warehousemanagementsystem.service;

import com.warehousemanagementsystem.entity.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-14
 */
public interface IWarehouseService extends IService<Warehouse> {
    List<Map<String, Object>> myGroupBy();
}
