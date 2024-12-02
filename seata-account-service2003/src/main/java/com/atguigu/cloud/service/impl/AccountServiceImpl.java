package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.mapper.AccountMapper;
import com.atguigu.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;
    @Override
    public void descrease(Long userId, Long money) {
        log.info("------> 账户微服务扣减余额开始");
        accountMapper.decrease(userId,money);

//        myTimeout();
//        int age = 10/0;
        log.info("------> 账户微服务扣减余额结束");
    }

    private static void myTimeout(){
        try{
            TimeUnit.SECONDS.sleep(65);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
