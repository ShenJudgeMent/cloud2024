package com.atguigu.cloud.controller;

import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping(value = "/account/decrease")
    public ResultData decrease(Long userId,Long money){
        accountService.descrease(userId,money);
        return ResultData.success("账户余额扣减完成");
    }
}
