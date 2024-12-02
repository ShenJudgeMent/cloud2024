package com.atguigu.cloud.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.enumerate.ReturnCodeEnum;
import com.atguigu.cloud.resp.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
public class PayAlibabaController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/pay/nacos/{id}")
    public String getPayInfo(@PathVariable("id") String id){
        return "nacos registry,server port:"+serverPort + "\t id:" + id;
    }

    @GetMapping(value = "/pay/nacos/get/{orderNo}")
    @SentinelResource(value = "getPayByOrderNo",blockHandler = "handlerBlockHandler")
    public ResultData getPayByOrderNo(@PathVariable("orderNo") String orderNo){
        PayDto payDto = new PayDto();
        payDto.setId(1024);
        payDto.setOrderNo(orderNo);
        payDto.setAmount(BigDecimal.valueOf(9.9));
        payDto.setPayNo("pay:"+ IdUtil.fastUUID());
        payDto.setUserId(1);
        return ResultData.success("查询返回值："+payDto);
    }

    public ResultData handlerBlockHandler(@PathVariable("orderNo") String orderNo, BlockException blockException){
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), "getPayByOrderNo服务不可用，" +
                "触发sentinel流量配置规则 \t  o( __ )o");
    }
}
