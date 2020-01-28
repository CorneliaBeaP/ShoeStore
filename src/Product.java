import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Product {
    int id;
    int size;
    int price;
    Brand brand;
    int quantity;
    List<Category> categoryList = new ArrayList();
    List<Colour> colourList = new ArrayList();

    public Product(int id, int size, int price, Brand brand, int quantity) {
        this.id = id;
        this.size = size;
        this.price = price;
        this.brand = brand;
        this.quantity = quantity;
    }

    public void addCategory(Category category) {
        categoryList.add(category);
    }

    public void addColour(Colour colour) {
        colourList.add(colour);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Colour> getColourList() {
        return colourList;
    }

    public void setColourList(List<Colour> colourList) {
        this.colourList = colourList;
    }


    @Override
    public String toString() {
        return "MÄRKE: " + brand + " STORLEK: " + size + " PRIS: " + price + " KATEGORI: " + categoryList + " FÄRG: " + colourList;
    }
}
