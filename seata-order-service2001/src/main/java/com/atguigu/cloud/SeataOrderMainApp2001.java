package com.atguigu.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient//该注解用于向使用consul为注册中心时注册服务
@EnableFeignClients//启用openFeign客户端，定义服务+绑定接口，以声明式的方法优雅而简单的实现服务调用
@MapperScan("com.atguigu.cloud.mapper")
public class SeataOrderMainApp2001 {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderMainApp2001.class);
    }
}