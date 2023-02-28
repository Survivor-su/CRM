package com.warehousemanagementsystem.util;

import com.warehousemanagementsystem.entity.RestResult;
import com.warehousemanagementsystem.entity.SysUser;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JWTUtil {
    /*
     * 生成JWT(token)
     * 参数是登录成功后的用户对象
     * 返回一个字符串
     * */
    public String createToken(SysUser sysUser) {
        //    定义有效时长,先记录生成时间,以及计算到期时间
        long timeMillis = System.currentTimeMillis();
        //计算7天到期
        long l = timeMillis + 7 * 24 * 3600 * 1000;
        //    定义负载信息,可能有很多,通过map去存储
        HashMap<String, Object> map = new HashMap<>();
        map.put("suId", sysUser.getSuId());
        map.put("suName", sysUser.getSuName());
        map.put("suRole", sysUser.getSuRole());
        //创建一个用于构造token的对象
        JwtBuilder builder = Jwts.builder();
        //设置负载信息
        builder.setClaims(map);
        //设置签发的者(无需求)
        builder.setIssuer("子夕秋寒");
        //设置签发时间
        builder.setIssuedAt(new Date(timeMillis));
        //设置到期时间
        builder.setExpiration(new Date(l));
        //设置签名算法和数字签名(至少4个字符)
        builder.signWith(SignatureAlgorithm.HS256, "Survivor");
        //返回签订好的字符串
        return builder.compact();
    }

    //    验证token
    /*
     * 通过异常区分不同验证结果
     * */
    public RestResult validataToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("Survivor")//设置当时签发时的秘钥
                    .parseClaimsJws(token)//解析token
                    .getBody();//得到token负载
            return RestResult.success(0, "token验证通过", claims);
        } catch (SignatureException e) {
            return RestResult.success(1, "token签名异常");
        } catch (ExpiredJwtException e) {
            return RestResult.success(1, "token失效");
        } catch (Exception e) {
            return RestResult.success(1, "token无效");
        }

    }
}
