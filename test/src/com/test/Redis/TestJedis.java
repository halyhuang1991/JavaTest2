package com.test.Redis;

// import java.util.HashMap;
import java.util.List;

import redis.clients.jedis.Jedis;
// import redis.clients.jedis.JedisPool;

public class TestJedis {
    public static void testConnRedis() {
        // 连接到Redis数据库(如果连接超时，可能是网络的问题，也可能是端口没有开)
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // jedis.auth("redis123");
        jedis.set("username", "test");
        String username = jedis.get("username");
        System.out.println(username);
        jedis.hset("myhash", "username", "crs");
        // 删除某一个键值对
        // jedis.hdel("myhash", "username");
        String result = jedis.hget("myhash", "username");
        System.out.println(result);
        List<String> lrange = jedis.lrange("array", 0, -1);
        for (String item : lrange) {
            System.out.println(item);
        }
        // ----lock
        Boolean flag = true;
        long ii = jedis.setnx("lockKey", "requestId");
        if (ii == 1) {
            jedis.expire("lockKey",2000);
            jedis.del("lockKey");
        }

    }

}
