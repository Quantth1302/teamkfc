package model;

import controller.ItemController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Item {
    private String id;
    private String employeeId;
    private double price;
    private int itemTypeId;
    private int saleId;
    private int limit;
    private String name;
    private int percent;
    private String typeName;
    private double salePrice;
    private Button edit;
    private Button delete;

    public Item(String id, String employeeId, double price, int itemTypeId,
                int saleId, int limit, String name, int percent, String typeName, double salePrice, Button edit, Button delete) {
        this.id = id;
        this.employeeId = employeeId;
        this.price = price;
        this.itemTypeId = itemTypeId;
        this.saleId = saleId;
        this.limit = limit;
        this.name = name;
        this.percent = percent;
        this.typeName = typeName;
        this.salePrice = salePrice;
        this.edit = edit;
        this.delete = delete;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
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
