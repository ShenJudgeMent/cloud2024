package com.atguigu.cloud.controller;

import com.atguigu.cloud.service.FlowLimitService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FlowLimitController {

    @Resource
    private FlowLimitService flowLimitService;

    @GetMapping(value = "/testA")
    public String testA(){
        return "------testA";
    }

    @GetMapping(value = "/testB")
    public String testB(){
        return "------testB";
    }

    @GetMapping(value = "/testC")
    public String testC(){
        flowLimitService.common();
        return "------testC";
    }

    @GetMapping(value = "/testD")
    public String testD(){
        flowLimitService.common();
        return "------testD";
    }

    /**
     * 流控效果的排队等待
     * @return
     */
    @GetMapping(value = "/testE")
    public String testE(){
        System.out.println(System.currentTimeMillis()+"\t testE,排队等待。。。");
        return "------testE";
    }

    @GetMapping(value = "/testF")
    public String testF(){
        try {
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("-------测试：慢调用比例------");
        return "------testF";
    }
}
