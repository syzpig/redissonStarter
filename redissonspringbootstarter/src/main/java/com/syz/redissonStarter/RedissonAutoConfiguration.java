package com.syz.redissonStarter;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@ConditionalOnClass(Redisson.class)//条件注解 项目中存在此类才会加载此starter
//使使用 @ConfigurationProperties 注解的类生效。
@EnableConfigurationProperties(RedissonProperties.class)  //如果一个配置类只配置@ConfigurationProperties注解，而没有使用@Component，那么在IOC容器中是获取不到properties 配置文件转化的bean。说白了 @EnableConfigurationProperties 相当于把使用 @ConfigurationProperties 的类进行了一次注入
@Configuration //相当于xml文件的配置类
public class RedissonAutoConfiguration {

    @Bean//相当于 xml 中<bean>
    RedissonClient redissonClient(RedissonProperties redissonProperties){
        //todo 这里线上要做做集群，负载等等处理
        Config config=new Config();
        String prefix="redis://";
        if(redissonProperties.isSsl()){
            prefix="rediss://";
        }
        config.useSingleServer().
                setAddress(prefix+redissonProperties.getHost()+":"+redissonProperties.getPort()).
                setConnectTimeout(redissonProperties.getTimeout());
        return Redisson.create(config);
    }
}
