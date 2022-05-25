package pers.ervinse.shoppingmall.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * 商品实体类
 */
public class Goods implements Serializable {

    private String name, description,location;
    private int number;
    private double price;
    public Boolean isSelected = false;
    //TODO 商品图片

    public Goods() {
    }

    public Goods(String name, String description, String location, int number, double price, Boolean isSelected) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.number = number;
        this.price = price;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return number == goods.number && Double.compare(goods.price, price) == 0 && Objects.equals(name, goods.name) && Objects.equals(description, goods.description) && Objects.equals(location, goods.location) && Objects.equals(isSelected, goods.isSelected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, location, number, price, isSelected);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", isSelected=" + isSelected +
                '}';
    }
}
