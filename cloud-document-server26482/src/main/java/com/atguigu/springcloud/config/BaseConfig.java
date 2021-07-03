package com.atguigu.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LQ on 2020/11/22.
 */
@Configuration
public class BaseConfig {

    /**
     * 线程池配置
     *
     */
    @Bean
    public Executor executor(){
        return new ThreadPoolExecutor(
                4,
                4,
                2,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
    }
}
