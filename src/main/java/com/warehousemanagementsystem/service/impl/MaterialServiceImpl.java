package com.warehousemanagementsystem.service.impl;

import com.warehousemanagementsystem.entity.Material;
import com.warehousemanagementsystem.mapper.MaterialMapper;
import com.warehousemanagementsystem.service.IMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-19
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

}
