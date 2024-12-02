package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class OrderController {
//    public static final String PaymentSrv_URL = "http://localhost:8001";
    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping(value = "/consumer/pay/add")
    public ResultData addOrder(PayDto payDto){
        return restTemplate.postForObject(PaymentSrv_URL+"/pay/add",payDto,ResultData.class);
    }

    @PostMapping(value = "/consumer/pay/del/{id}")
    public void deleteOrder(@PathVariable("id") Integer id){
        restTemplate.delete(PaymentSrv_URL + "/pay/del/"+id);
    }

    @PostMapping(value = "/consumer/pay/update")
    public ResultData updateOrder(PayDto payDto){
        return restTemplate.postForObject(PaymentSrv_URL+"/pay/update",payDto,ResultData.class);
    }

    @GetMapping(value = "/consumer/pay/get/{id}")
    public ResultData getOrder(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/get/"+id,ResultData.class,id);
    }
    @GetMapping(value = "/consumer/pay/get/info")
    public  String getInfoByConsul(){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/get/info",String.class);
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping(value = "/consumer/discovery")
    public String getServerInfo(){
        List<String> services = discoveryClient.getServices();
        services.forEach(System.out::println);

        System.out.println("=========================");
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element: instances) {
            System.out.println("=========================");
            System.out.println(element.getServiceId()); //cloud-payment-service
            System.out.println(element.getHost());      //shenchen
            System.out.println(element.getPort());      //8001
            System.out.println(element.getUri());       //http://shenchen:8001
        }
        return instances.get(0).getInstanceId();
    }
}
