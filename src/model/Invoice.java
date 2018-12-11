package model;

import java.util.Date;

public class Invoice {
    private String id;
    private String customerId;
    private String employeeId;
    private Date createdTime;

    public Invoice() {
    }

    public Invoice(String id, String customerId, String employeeId, Date createdTime) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
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
}
