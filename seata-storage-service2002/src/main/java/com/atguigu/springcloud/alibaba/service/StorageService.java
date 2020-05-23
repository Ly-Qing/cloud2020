package com.atguigu.springcloud.alibaba.service;

/**
 * Created by LQ on 2020/5/17.
 */
public interface StorageService {

    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);
}
