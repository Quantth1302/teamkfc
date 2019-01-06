package model;

public class InvoiceDetail {
    private String id;
    private String itemId;
    private String itemName;
    private int itemQuantity;
    private Double itemPrice;
    private int sale;
    private String comboId;
    private String comboName;
    private Double comboPrice;
    private int comboQuantity;

    public InvoiceDetail() {
    }

    public InvoiceDetail(String id, String itemId, String itemName, int itemQuantity, Double itemPrice,
                         int sale, String comboId, String comboName, Double comboPrice, int comboQuantity) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.sale = sale;
        this.comboId = comboId;
        this.comboName = comboName;
        this.comboPrice = comboPrice;
        this.comboQuantity = comboQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public Double getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(Double comboPrice) {
        this.comboPrice = comboPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
