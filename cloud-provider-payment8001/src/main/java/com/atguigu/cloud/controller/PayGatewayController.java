package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.IdUtil;
import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;

@RestController
public class PayGatewayController {

    @Resource
    private PayService payService;

    @PostMapping(value = "/pay/gateway/add")
    @Operation(summary = "新增", description = "新增支付流水, 参数是JSON字符串")
    public ResultData<String> addPay(@RequestBody Pay pay){
        System.out.println(pay.toString());
        int i = payService.add(pay);
        return ResultData.success("成功插入记录，返回值：" + i);
    }

    @DeleteMapping(value = "/pay/gateway/del/{id}")
    @Operation(summary = "删除", description = "删除支付流水, 参数是Id")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id){
        int i = payService.delete(id);
        return ResultData.success(i);
    }

    @PutMapping(value = "/pay/gateway/update")
    @Operation(summary = "更新", description = "更新支付流水, 参数是JSON字符串, 根据Id更新")
    public ResultData<String> updatePay(@RequestBody PayDto payDto){
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDto,pay);
        int i = payService.update(pay);
        return ResultData.success("成功修改记录，返回值：" + i);
    }

    @GetMapping(value = "/pay/gateway/get/{id}")
    @Operation(summary = "查询单个", description = "查询支付流水, 参数是Id")
    public ResultData<Pay> getById(@PathVariable("id") Integer id){
        if (id < 0) {
            throw new RuntimeException("id不能为负数");
        }
        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getInfoByConsul(@Value("${atguigu.info}") String info){
        return ResultData.success("gateway info test:"+ IdUtil.simpleUUID());
    }

    @GetMapping(value = "/pay/gateway/filter")
    public ResultData<String> getGatewayFilter(HttpServletRequest request){
        String result = "";
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()){
            String headName = headers.nextElement();
            String headValue = request.getHeader(headName);
            System.out.println("请求头名："+headName+"\t\t\t"+"请求头值："+headValue);
            if (headName.equalsIgnoreCase("X-Request-atguigu1")
                || headName.equalsIgnoreCase("X-Request-atguigu2")){
                result  = result + headName + "\t" + headValue + "";
            }
        }
        return ResultData.success("getGatewayFilter 过滤器 test :" + result + "\t" + System.currentTimeMillis());
    }
}
