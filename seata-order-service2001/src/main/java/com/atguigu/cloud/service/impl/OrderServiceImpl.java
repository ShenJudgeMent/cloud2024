package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.apis.StorageFeignApi;
import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.mapper.OrderMapper;
import com.atguigu.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AccountFeignApi accountFeignApi;

    @Resource
    private StorageFeignApi storageFeignApi;
    @Override
    @GlobalTransactional(name = "sc-create-order",rollbackFor = Exception.class)
    public void createOrder(Order order) {
        //xid全局事务id的检查，重要
        String xid = RootContext.getXID();

        //1、 新建订单
        log.info("------------------------开始新建订单：" + "\t xid:" + xid );
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);
        Order orderFromDB = null;

        if (result > 0) {
            //2、查询数据库新增记录
            orderFromDB = orderMapper.selectOne(order);
            log.info("------> 新建订单成功,orderFromDB info:"+orderFromDB);

            //3、扣减库存
            log.info("------> 订单微服务开始调用Storage库存，做扣减count");
            storageFeignApi.decrease(orderFromDB.getProductId(),orderFromDB.getCount());
            log.info("------> 订单微服务结束调用Storage库存，做扣减完成");

            //4、扣减账户余额
            log.info("------> 订单微服务开始调用Account账户，做扣减money");
            accountFeignApi.decrease(orderFromDB.getUserId(),orderFromDB.getMoney());
            log.info("------> 订单微服务结束调用Account账户，做扣减完成");

            //5、修改订单状态
            //将订单状态从0 修改为 1，表示已经完成
            log.info("------> 修改订单状态");
            orderFromDB.setStatus(1);
            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId",orderFromDB.getUserId());
            criteria.andEqualTo("status",0);
            int updateResult = orderMapper.updateByExampleSelective(orderFromDB, whereCondition);
            log.info("------> 修改订单状态完成：\t"+updateResult);
            log.info("------> orderFromDB info:"+orderFromDB);
        }
        log.info("------------------------结束新建订单：" + "\t xid:" + xid );
    }
}
