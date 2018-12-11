package model;

public class ComboDetail {
    private String itemId;
    private int comboId;
    private int itemQuantity;

    public ComboDetail() {
    }

    public ComboDetail(String itemId, int comboId, int itemQuantity) {
        this.itemId = itemId;
        this.comboId = comboId;
        this.itemQuantity = itemQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getComboId() {
        return comboId;
    }

    public void setComboId(int comboId) {
        this.comboId = comboId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
