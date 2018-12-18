package api.Pojos;

//Generic object to allow users to return a month and some related value
public class MonthInfo {
    private String x;
    private int y;

    public MonthInfo(){}

    public MonthInfo(String month, int value){
        this.x = month;
        this.y = value;
    }

    public String getX() {
        return x;
    }

    public void setMonth(String month) {
        this.x = month;
    }

    public int getY() {
        return y;
    }

    public void setValue(int value) {
        this.y = value;
    }
}
