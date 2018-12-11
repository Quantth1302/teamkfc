package model;

public class Item {
    private String id;
    private String employeeId;
    private double price;
    private int itemTypeId;
    private int saleId;
    private int limit;
    private String name;

    public Item() {
    }

    public Item(String id, String employeeId, double price, int itemTypeId, int saleId, int limit, String name) {
        this.id = id;
        this.employeeId = employeeId;
        this.price = price;
        this.itemTypeId = itemTypeId;
        this.saleId = saleId;
        this.limit = limit;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
