/**
 * Copyright(c) 2011-2015 by hexin Inc.
 * All Rights Reserved
 */
package redis.study.seckill;

import java.util.UUID;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.study.seckill.model.Account;
import redis.study.seckill.model.Item;

/**
 * 
 *
 * @author dns@hexindai.com
 */
public class Main {

    private static String ip = "127.0.0.1";
    private static int port = 6379;

    /**
     * @param args
     */
    public static void main(String[] args) {
        int nThread = 5;
        final Item aItem = new Item(1, "iphone6s", 480000l, 10);
        final AccountService service = new AccountService();
        final ExecutorService executor = Executors.newFixedThreadPool(nThread);
        final CyclicBarrier barrier = new CyclicBarrier(nThread, new Runnable() {

            @Override
            public void run() {
                executor.shutdownNow();
                service.finish();
            }
        });
        final Account aAccount = new Account(1, "yngil", 10000000l);
        for (int i = 0; i < nThread; i++) {
            executor.submit(new Runnable() {

                @Override
                public void run() {
                    boolean bool;
                    try {
                        bool = service.buy(aAccount, aItem);
                        if (!bool) {
                            System.out.printf("用户->%s抢购商品->%s，失败\r\n", aAccount.getName(), aItem.getName());
                        } else {
                            System.out.printf("用户->%s抢购商品->%s，成功\r\n", aAccount.getName(), aItem.getName());
                        }
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static String randomShortCode() {
        String shortCode = null, code = UUID.randomUUID().toString().replaceAll("-", "");
        String hex = code;
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"

        };
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl[0];
    }
}
