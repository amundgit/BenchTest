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

    public String getMonth() {
        return x;
    }

    public void setMonth(String month) {
        this.x = month;
    }

    public int getValue() {
        return y;
    }

    public void setValue(int value) {
        this.y = value;
    }
}
