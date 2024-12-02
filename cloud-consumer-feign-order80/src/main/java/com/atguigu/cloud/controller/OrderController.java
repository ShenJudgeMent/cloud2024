package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class OrderController {



    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping(value = "/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDto payDto){
        ResultData resultData = payFeignApi.addPay(payDto);
        return resultData;
    }

    @GetMapping(value = "/feign/pay/get/{id}")
    public ResultData getById(@PathVariable("id") String id){
        return payFeignApi.getById(id);
    }

    @GetMapping(value = "/feign/pay/mylb")
    public ResultData MyLb(){
        return payFeignApi.getInfo();
    }



}
