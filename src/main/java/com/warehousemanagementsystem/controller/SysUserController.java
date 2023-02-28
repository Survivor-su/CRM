package com.warehousemanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.entity.SysUser;
import com.warehousemanagementsystem.service.impl.SysUserServiceImpl;
import com.warehousemanagementsystem.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 子夕秋寒
 * @since 2022-10-25
 */
@RestController
@CrossOrigin
public class SysUserController {
    @Autowired
    private SysUserServiceImpl service;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("sys_user")
    public RestResult queryByUser(SysUser sysUser) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("su_name", sysUser.getSuName()).eq("su_password", sysUser.getSuPassword());
        try {
            SysUser one = service.getOne(wrapper);
            if (one != null) {
                if(one.getSuStatus()==0){
                    return RestResult.success(1, "登录失败,账户未激活");
                }
                return RestResult.success(0, "登录成功", jwtUtil.createToken(one));
            } else {
                return RestResult.success(1, "登录失败，请检查账号或密码");
            }
        } catch (Exception e) {
            return RestResult.success(1, "登录异常" + e.getMessage());
        }
    }

    @RequestMapping(value = "register_sys_user",method = RequestMethod.POST)
    public RestResult registerUser(SysUser sysUser){
        try {
            boolean b = service.saveOrUpdate(sysUser);
            if(b){
                return RestResult.success(0,"注册成功,请查看邮件激活!");
            }else {
                return RestResult.success(1,"注册失败!");
            }
        } catch (Exception e){
            return RestResult.error(1,"注册异常!");
        }
    }
}
