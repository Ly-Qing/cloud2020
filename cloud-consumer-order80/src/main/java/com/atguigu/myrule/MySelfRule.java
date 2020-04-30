package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LQ on 2020/4/4.
 */

@Configuration
public class MySelfRule {

    @Bean
    public IRule getIRule(){
        return new RandomRule();
    }
}
