package model;

public class ComboDetail {
    private String itemId;
    private String comboId;
    private int itemQuantity;

    public ComboDetail() {
    }

    public ComboDetail(String itemId, String comboId, int itemQuantity) {
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

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
