package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.entities.Storage;
import com.atguigu.cloud.mapper.StorageMapper;
import com.atguigu.cloud.service.StorageService;
import io.seata.core.context.RootContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageMapper storageMapper;
    @Override
    public void decrease(Long productId, Integer count) {
        log.info("------> 库存微服务扣减库存开始");
        storageMapper.decrease(productId,count);
        log.info("------> 库存微服务扣减库存结束");
    }
}
