package model;

public class Item {
    private String id;
    private String name;
    private double price;
    private int sale;

    public Item() {
    }

    public Item(String id, String name, double price, int sale) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
