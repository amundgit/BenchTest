package api.Pojos;

//Object to easily allow the user to get all peak values for a given company, in a given month. Can easily be expanded if more values are needed
public class PeakInfo {
    private String month;
    private String companyName;
    private int totalPositions;
    private int tempPositions;
    private int permanentPositions;
    private int relevantPositions;
    private int relevantTempPositions;
    private int relevantPermanentPositions;
    private int ITPositions;
    private int financePositions;
    private int executivePositions;
    private int engineeringPositions;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getTotalPositions() {
        return totalPositions;
    }

    public void setTotalPositions(int totalPositions) {
        this.totalPositions = totalPositions;
    }

    public int getTempPositions() {
        return tempPositions;
    }

    public void setTempPositions(int tempPositions) {
        this.tempPositions = tempPositions;
    }

    public int getPermanentPositions() {
        return permanentPositions;
    }

    public void setPermanentPositions(int permanentPositions) {
        this.permanentPositions = permanentPositions;
    }

    public int getRelevantPositions() {
        return relevantPositions;
    }

    public void setRelevantPositions(int relevantPositions) {
        this.relevantPositions = relevantPositions;
    }

    public int getRelevantTempPositions() {
        return relevantTempPositions;
    }

    public void setRelevantTempPositions(int relevantTempPositions) {
        this.relevantTempPositions = relevantTempPositions;
    }

    public int getRelevantPermanentPositions() {
        return relevantPermanentPositions;
    }

    public void setRelevantPermanentPositions(int relevantPermanentPositions) {
        this.relevantPermanentPositions = relevantPermanentPositions;
    }

    public int getITPositions() {
        return ITPositions;
    }

    public void setITPositions(int ITPositions) {
        this.ITPositions = ITPositions;
    }

    public int getFinancePositions() {
        return financePositions;
    }

    public void setFinancePositions(int financePositions) {
        this.financePositions = financePositions;
    }

    public int getExecutivePositions() {
        return executivePositions;
    }

    public void setExecutivePositions(int executivePositions) {
        this.executivePositions = executivePositions;
    }

    public int getEngineeringPositions() {
        return engineeringPositions;
    }

    public void setEngineeringPositions(int engineeringPositions) {
        this.engineeringPositions = engineeringPositions;
    }
}
