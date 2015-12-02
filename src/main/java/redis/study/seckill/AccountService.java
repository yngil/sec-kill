/**
 * Copyright(c) 2011-2015 by hexin Inc.
 * All Rights Reserved
 */
package redis.study.seckill;

import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.study.seckill.model.Account;
import redis.study.seckill.model.Item;

/**
 * 
 *
 * @author dns@hexindai.com
 */
public class AccountService {

    private static Jedis redis = null;
    private static String ip = "127.0.0.1";
    private static int port = 6379;
    private static JedisPool pool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(3000);
        jedisPoolConfig.setMaxTotal(400);
        jedisPoolConfig.setMaxIdle(400);
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setLifo(false);
        pool = new JedisPool(jedisPoolConfig, ip, port, 3000);
    }

    public boolean buy(Account account, Item item) throws Exception {
        String key = String.format("%s_%s", item.getName(), account.getName());
        account.setAmount(account.getAmount() - item.getPrice());
        String rdCode = UUID.randomUUID().toString().replaceAll("-", "");
        String result = null;
        redis = pool.getResource();
        result = redis.set(key, "1", "NX", "EX", 2L);
        if (!"OK".equalsIgnoreCase(result)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void finish() {
        pool.destroy();
    }
}
