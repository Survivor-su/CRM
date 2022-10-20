package com.warehousemanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/*
* 通过定义配置类的形式设置在线接口文档的相关信息
* 一个类如果需要定义配置类,需要加入@Configuration注解,在项目启动时自动执行
* */
@Configuration
@EnableOpenApi
public class Swagger3Config {
    @Bean
    public Docket docket(){
        //该构造方法参数是一个"文档类型"的枚举类型,OAS_30表示OpenApI3.0
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(//参数是apiInfo对象
                 new ApiInfoBuilder()//用于创建ApiInfo对象
                .title("ERP项目接口文档")//设置接口文档标题
                .description("Api测试")//文档的详细描述
                .version("v8.8")//文档版本
                .build()        //创建ApiInofo对象
                );
    }
}
