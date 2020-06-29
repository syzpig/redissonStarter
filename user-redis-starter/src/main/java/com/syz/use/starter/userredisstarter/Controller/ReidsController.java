package com.syz.use.starter.userredisstarter.Controller;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@description ${description}
 *@date 2020/6/29 10:42
 *@author syz
 */
@RestController
public class ReidsController {

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/say")
    public String say(){
        RBucket bucket=redissonClient.getBucket("name");
        if(bucket.get()==null){
            bucket.set("syz.com");
        }
        return bucket.get().toString();
    }
}
