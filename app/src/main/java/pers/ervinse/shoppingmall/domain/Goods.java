package pers.ervinse.shoppingmall.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * 商品实体类
 */
public class Goods implements Serializable {

    private String name, description,location,image;
    private int number;
    private double price;
    public Boolean isSelected = false;

    public Goods() {
    }

    public Goods(String name, String description, String location, String image, int number, double price, Boolean isSelected) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", isSelected=" + isSelected +
                '}';
    }
}
