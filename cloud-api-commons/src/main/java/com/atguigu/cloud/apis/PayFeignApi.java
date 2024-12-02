package com.atguigu.cloud.apis;

import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(value = "cloud-payment-service")
@FeignClient(value = "cloud-gateway")
public interface PayFeignApi {
    /**
     * 新增一条
     * @param payDto
     * @return
     */
    @PostMapping(value = "/pay/add")
    public ResultData addPay(@RequestBody PayDto payDto);

    /**
     * 获取
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/get/{id}")
    public ResultData getById(@PathVariable("id") String id);

    @GetMapping(value = "/pay/get/info")
    public ResultData<String> getInfo();


    @GetMapping(value = "/pay/gateway/get/{id}")
    public ResultData getGatewayById(@PathVariable("id")String id);

    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getGatewayInfo();
}
