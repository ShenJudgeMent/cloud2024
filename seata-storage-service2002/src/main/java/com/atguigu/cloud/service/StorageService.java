package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Storage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestParam;

public interface StorageService {


    /**
     * 扣减库存
     * @param productId
     * @param count
     */
    public void decrease(Long productId, Integer count);

}
