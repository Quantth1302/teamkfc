package model;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

import java.util.Date;

public class Sale {
    private String id;
    private int percent;
    private Date startedTime;
    private Date endTime;
    private String name;
    private Button edit;
    private Button delete;

    public Sale() {
    }

    public Sale(String id, int percent, Date startedTime, Date endTime, String name, Button edit, Button delete) {
        this.id = id;
        this.percent = percent;
        this.startedTime = startedTime;
        this.endTime = endTime;
        this.name= name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
