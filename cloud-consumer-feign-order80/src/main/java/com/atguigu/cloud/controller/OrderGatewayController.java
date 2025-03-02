package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderGatewayController {


    @Resource
    private PayFeignApi payFeignApi;


    @GetMapping(value = "/feign/pay/gateway/get/{id}")
    public ResultData getById(@PathVariable("id") String id){
        return payFeignApi.getGatewayById(id);
    }
    @GetMapping(value = "/feign/pay/gateway/info")
    public ResultData<String> getGatewayInfo(){
        return payFeignApi.getGatewayInfo();
    }
}
