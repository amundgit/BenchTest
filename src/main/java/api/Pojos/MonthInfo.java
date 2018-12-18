package api.Pojos;

//Generic object to allow users to return a month and some related value
public class MonthInfo {
    private String month;
    private int value;

    public MonthInfo(){}

    public MonthInfo(String month, int value){
        this.month = month;
        this.value = value;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
