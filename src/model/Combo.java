package model;

import javafx.scene.control.Button;

public class Combo {

    private String id;
    private String name;
    private int limit;
    private int percent;
    private int active;
    private double comboPrice;
    private Button edit;
    private Button delete;

    public Combo() {
    }

    public Combo(String id, String name, int limit, int percent, int active, double comboPrice, Button edit, Button delete) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.percent = percent;
        this.active = active;
        this.comboPrice = comboPrice;
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

    public double getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(double comboPrice) {
        this.comboPrice = comboPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
