package api.Pojos;

//Generic object to allow users to return a month and some related value
public class MonthInfo {
    private String month;
    private int value;
    private String name;

    public MonthInfo(){}

    public MonthInfo(String month, int value, String name){
        this.month = month;
        this.value = value;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
