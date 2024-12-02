package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Storage;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.StorageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    @PostMapping(value = "/storage/decrease")
    public ResultData decrease(Long productId, Integer count){
        storageService.decrease(productId,count);
        return ResultData.success("扣减库存成功");
    }
}
