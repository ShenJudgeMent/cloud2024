package com.atguigu.cloud.apis;

import com.atguigu.cloud.enumerate.ReturnCodeEnum;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.stereotype.Component;

@Component
public class PayFeignSentinelApiFallBack implements PayFeignSentinelApi{
    @Override
    public ResultData getPayByOrderNo(String orderNo) {
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), "对方服务宕机或不可用，FallBack服务降级");
    }
}
