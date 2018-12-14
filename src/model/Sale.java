package model;

import javafx.scene.chart.PieChart;

import java.util.Date;

public class Sale {
    private int id;
    private int percent;
    private Date startedTime;
    private Date endTime;
    private String name;

    public Sale() {
    }

    public Sale(int id, int percent, Date startedTime, Date endTime, String name) {
        this.id = id;
        this.percent = percent;
        this.startedTime = startedTime;
        this.endTime = endTime;
        this.name= name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
