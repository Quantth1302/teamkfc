package model;

import java.util.Date;

public class Invoice {
    private String id;
    private String customerId;
    private String employeeId;
    private String createdTime;
    private Double totalPrice;
    private Double payPrice;

    public Invoice() {
    }

    public Invoice(String id, String customerId, String employeeId, String createdTime, Double totalPrice, Double payPrice) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.createdTime = createdTime;
        this.totalPrice = totalPrice;
        this.payPrice = payPrice;
    }

    public String getId() {
        return id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
