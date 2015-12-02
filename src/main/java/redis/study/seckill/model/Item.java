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
public class Item {
    private int id;
    private String name;
    private long price;
    private int quantity;

    public Item(int id, String name, long price, int quanitty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quanitty;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
