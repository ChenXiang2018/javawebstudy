package com.cx.springbootdemo;

import com.cx.springbootdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootdemoApplicationTests {
//
//	@Autowired
//	private MemCachedClient memCachedClient;


//    @Autowired
//    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

	@Test
	public void contextLoads(){
//		// 放入缓存
//		boolean flag = memCachedClient.set("a", 1);
//
//		// 取出缓存
//		Object a = memCachedClient.get("a");
//		System.out.println(a);
//
//
//		// 3s后过期
//		memCachedClient.set("b", "2", new Date(3000));
//		Object b = memCachedClient.get("b");
//		System.out.println(b);
//
//		Thread.sleep(3000);
//		b = memCachedClient.get("b");
//        System.out.println(a);
//		System.out.println(b);

        // 存取字符串
        redisTemplate.opsForValue().set("a", "1");
        Object a = redisTemplate.opsForValue().get("a");
        System.out.println(a);

        // 存取user对象
        redisTemplate.opsForValue().set("user1", new User("张三",1 ));
        User user1 = (User) redisTemplate.opsForValue().get("user1");
        System.out.println(user1);
	}

}
