package server;

public class User {
    private String id;
    private boolean isPicker = false;
    private int points;
    private Boolean bid = null;
    private Integer draw;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPicker() {
        return isPicker;
    }

    public void setPicker(boolean picker) {
        isPicker = picker;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Boolean isBid() {
        return bid;
    }

    public void setBid(Boolean bid) {
        this.bid = bid;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }
}
