package model;

public class Customer {

    private String id;
    private String fullname;
    private String address;
    private String phone;
    private int userId;

    public Customer(String id, String fullname, String address, String phone, int userId) {
        this.id = id;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.userId = userId;
    }

    public Customer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
