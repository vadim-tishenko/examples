package ru.cwl.example.springdataredis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
//@EnableTransactionManagement
public class RedisTxContextConfiguration {

    @Bean
    ListOperations<String, String> getListOps(RedisTemplate<String,String> template){
        return template.opsForList();
    }

//    @Configuration
//    class RedisConfiguration {

        @Bean
        ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
            return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
        }


//    @Bean
//
//
//    @Bean
//    public StringRedisTemplate redisTemplate() {
//        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
//        // explicitly enable transaction support
////        template.setEnableTransactionSupport(true);
//        return template;
//    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory("localhost", 6379);// jedis || Lettuce
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager() throws SQLException {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean
//    public DataSource dataSource() throws SQLException {
//        // ...
//    }
}
