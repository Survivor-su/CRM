package com.warehousemanagementsystem.service.impl;

import com.warehousemanagementsystem.entity.Warehouse;
import com.warehousemanagementsystem.mapper.WarehouseMapper;
import com.warehousemanagementsystem.service.IWarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-14
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    @Override
    public List<Map<String, Object>> myGroupBy() {
        return getBaseMapper().myGroupBy();
    }
}
