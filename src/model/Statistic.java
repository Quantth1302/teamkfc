package model;

import java.util.Date;

public class Statistic {
     private String employeeName;
     private Date createdTime;
     private String itemId;
     private int itemQuantity;
     private String itemName;
     private String saleName;
     private double itemTotalPrice;
     private String comboId;
     private int comboQuantity;
     private String comboName;
     private double comboTotalPrice;

    public Statistic() {
    }

    public Statistic(String employeeName, Date createdTime, String itemId, int itemQuantity, String itemName, String saleName,
                     double itemTotalPrice, String comboId, int comboQuantity, String comboName, double comboTotalPrice) {
        this.employeeName = employeeName;
        this.createdTime = createdTime;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemName = itemName;
        this.saleName = saleName;
        this.itemTotalPrice = itemTotalPrice;
        this.comboId = comboId;
        this.comboQuantity = comboQuantity;
        this.comboName = comboName;
        this.comboTotalPrice = comboTotalPrice;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public double getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(double itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public int getComboQuantity() {
        return comboQuantity;
    }

    public void setComboQuantity(int comboQuantity) {
        this.comboQuantity = comboQuantity;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public double getComboTotalPrice() {
        return comboTotalPrice;
    }

    public void setComboTotalPrice(double comboTotalPrice) {
        this.comboTotalPrice = comboTotalPrice;
    }
}
