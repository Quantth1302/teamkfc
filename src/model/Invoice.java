package model;

import javafx.scene.control.Button;

import java.util.Date;

public class Invoice {
    private String id;
    private String customerId;
    private String customerName;
    private String employeeName;
    private String employeeId;
    private Date createdTime;
    private Double totalPrice;
    private Double payPrice;
    private Button detail;

    public Invoice() {
    }

    public Invoice(String customerName, String employeeName, String id, String customerId, String employeeId,
                   Date createdTime, Double totalPrice, Double payPrice, Button detail) {
        this.customerName = customerName;
        this.employeeName = employeeName;
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.createdTime = createdTime;
        this.totalPrice = totalPrice;
        this.payPrice = payPrice;
        this.detail = detail;
    }

    public Button getDetail() {
        return detail;
    }

    public void setDetail(Button detail) {
        this.detail = detail;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
