package pers.ervinse.shoppingmall.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * 商品实体类
 */
public class Goods implements Serializable {

    private String name, describe,location;
    private int number;
    private double price;
    //TODO 商品图片

    public Goods() {
    }

    public Goods(String name, String describe, String location, int number, double price) {
        this.name = name;
        this.describe = describe;
        this.location = location;
        this.number = number;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
