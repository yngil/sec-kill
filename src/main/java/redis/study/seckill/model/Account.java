/**
 * Copyright(c) 2011-2015 by hexin Inc.
 * All Rights Reserved
 */
package redis.study.seckill.model;

/**
 * 
 *
 * @author dns@hexindai.com
 */
public class Account {
    private int id;
    private String name;
    private long amount;

    public Account(int id, String name, long amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
