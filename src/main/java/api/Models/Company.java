package api.Models;

import javax.persistence.*;

@Entity
public class Company {
    @Id
    private String company;

    //FOREIGN KEY FOR DATE
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "date_collected", nullable = false)
    private Date date;

    private int tempPositions;

    private int permanentPositions;

    private int relevantPositions;

    private int relevantTempPositions;

    private int relevantPermanentPositions;


    //CONSTRUCTORS
    public Company(){}

    public Company(String company){
        this.company = company;
    }

    //GETTERS AND SETTERS

    public String getCompanyName(){
        return company;
    }
    public void setCompanyName(String company){
        this.company = company;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTempPositions(){
        return tempPositions;
    }

    public void setTempPositions(int tempPositions){
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
}
