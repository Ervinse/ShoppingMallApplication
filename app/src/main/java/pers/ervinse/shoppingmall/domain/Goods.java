package pers.ervinse.shoppingmall.domain;

import java.util.Objects;

/**
 * 商品实体类
 */
public class Goods {

    private String name, describe;
    private int number;
    private double price;
    //TODO 商品图片

    public Goods() {
    }

    public Goods(String name, String describe, double price) {
        this.name = name;
        this.describe = describe;
        this.price = price;
    }

    public Goods(String name, String describe, int number, double price) {
        this.name = name;
        this.describe = describe;
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

    @Override
    public String toString() {
        return "goods{" +
                "name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Double.compare(goods.price, price) == 0 && name.equals(goods.name) && Objects.equals(describe, goods.describe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, describe, price);
    }
}
