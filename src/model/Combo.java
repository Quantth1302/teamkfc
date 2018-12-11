package model;

public class Combo {

    private int id;
    private String name;
    private int limit;
    private int percent;
    private int active;

    public Combo() {
    }

    public Combo(int id, String name, int limit, int percent, int active) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.percent = percent;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
