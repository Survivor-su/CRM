package com.warehousemanagementsystem.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehousemanagementsystem.annotation.Auth;
import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

//自定义拦截器类
/*
 * 需要实现HandlerInterceptor接口，重写preHandle方法
 * */
public class Authinterceptor implements HandlerInterceptor {
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    /*
     * 该方法会在拦截前执行
     * 如果该方法的返回值为false,则不继续执行后续操作
     * */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(request.getRequestURI());
        ObjectMapper jsonUtil = new ObjectMapper();
        /*
         * 前端页面在登录成功后的后续操作，都要在header中携带token请求到后端
         * 获取请求中携带的token
         * 判断token是否存在，是否合理
         * */
        if (handler instanceof HandlerMethod) {
//    拦截
            String token = request.getHeader("token");
            //判断token是否存在
            if (token == null || "null".equals(token)) {
                //    构造一个token不存在时的返回值(RestResult对象的JSON字符串)
                String restJson = jsonUtil.writeValueAsString(RestResult.success(1, "未登录,token不存在"));
                //将JSON字符串写入响应对象response,要将编码格式设置为UTF8
                response.getOutputStream().write(restJson.getBytes(StandardCharsets.UTF_8));
                response.setContentType("application/json;charset=utf-8");
                return false;
            }
            //验证token
            RestResult restResult = jwtUtil.validataToken(token);
            //如果code不为0说明token有误
            if (restResult.getCode() != 0) {
                //将有误的结果转换为JSON字符串后,写入到response中
                String string = jsonUtil.writeValueAsString(restResult);
                response.getOutputStream().write(string.getBytes(StandardCharsets.UTF_8));
                response.setContentType("application/json;charset=utf-8");
                return false;
            }
            //验证用户权限
            //获取当前登录的用户信息
            Claims claims = (Claims) restResult.getObj();
            //获取用户的角色信息
            String suRole = (String) claims.get("suRole");
            //获取当前请求的所在类
            Class<?> beanType = ((HandlerMethod) handler).getBeanType();
            //检验该类上是否有某个注解
            if (beanType.isAnnotationPresent(Auth.class)) {
                //获取该类上是否有某个注解
                Auth auth = beanType.getAnnotation(Auth.class);
                //判断该注解中的属性值是否与该角色信息匹配
                if (!auth.role().contains(suRole)) {
                    String string = jsonUtil.writeValueAsString(RestResult.error(1,"无权限"));
                    response.getOutputStream().write(string.getBytes(StandardCharsets.UTF_8));
                    response.setContentType("application/json;charset=utf-8");
                    return false;
                }
            }
        }

        return true;
    }
}
