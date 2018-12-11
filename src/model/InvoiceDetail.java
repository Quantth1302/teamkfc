package model;

public class InvoiceDetail {
    private String id;
    private String itemId;
    private int itemQuantity;
    private int comboId;
    private int comboQuantity;

    public InvoiceDetail() {
    }

    public InvoiceDetail(String id, String itemId, int itemQuantity, int comboId, int comboQuantity) {
        this.id = id;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.comboId = comboId;
        this.comboQuantity = comboQuantity;
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

    public int getComboId() {
        return comboId;
    }

    public void setComboId(int comboId) {
        this.comboId = comboId;
    }

    public int getComboQuantity() {
        return comboQuantity;
    }

    public void setComboQuantity(int comboQuantity) {
        this.comboQuantity = comboQuantity;
    }
}
