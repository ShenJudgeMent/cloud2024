package com.atguigu.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateLimitController {

    @GetMapping(value = "/rateLimit/byUrl")
    public String getRateLimit(){
        return "限流测试OK";
    }
    @GetMapping(value = "/rateLimit/byResource")
    @SentinelResource(value = "byResourceSentinelResource",blockHandler = "handlerBlockHandler")
    public String byResource(){
        return "按照资源名称SentinelResource限流测试ok";
    }

    /**
     * 1、必须是public
     * 2、返回类型参数与原方法一致
     * 3、默认和原方法在同一个类中。若希望使用其他类的函数，可配置blockHandlerClass,并指定blockHandlerClass里面的方法
     * @param blockException
     * @return
     */
    public String handlerBlockHandler(BlockException blockException){
        return "服务不可用触发了@SentinelResource启动";
    }

    @GetMapping(value = "/rateLimit/doAction/{id}")
    @SentinelResource(value = "doActionSentinelResource",blockHandler = "doActionBlockHandler",fallback = "doActionFallback")
    public String doAction(@PathVariable("id") Integer id){
        if (id==0) {
            throw new RuntimeException("id等于零直接异常");
        }
        return "doAction";
    }

    public String doActionBlockHandler(@PathVariable("id")Integer id,BlockException e){
        log.error("snetinel配置自定义被限流了：{}"+e);
        return "snetinel配置自定义被限流了";
    }

    /**
     * 1、返回类型与原方法一致
     * 2、参数类型需要和原方法匹配
     * 3、默认和原方法在同一个类中。若希望使用其他类的函数，可配置fallbackClass,并指定fallbackClass里面的方法
     * @param id
     * @param e
     * @return
     */
    public String doActionFallback(@PathVariable("id")Integer id,Throwable e){
        log.error("程序逻辑异常了：{}"+e);
        return "程序逻辑异常了";
    }

    /**
     * 热点限流测试
     * 针对P1参数进行限流，p2不设置
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping(value = "/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "dealHandler_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value = "p2",required = false) String p2){
        return "---------testHotKey";
    }
    public String dealHandler_testHotKey(String p1,String p2,BlockException e){
        return "dealHandler_testHotKey";
    }
}
