package api.Models;

import javax.persistence.*;

import api.CompositeIds.CompanyId;

@Entity
public class Company {
    @EmbeddedId
    private CompanyId companyId;

    private int totalPositions;

    private int tempPositions;

    private int permanentPositions;

    private int relevantPositions;

    private int relevantTempPositions;

    private int relevantPermanentPositions;


    //CONSTRUCTORS
    public Company(){}

    public Company(String company, Date date){
        this.companyId = new CompanyId(date,company);
    }

    public Company(String company, Date date, int totalPositions, int tempPositions, int permanentPositions, int relevantPositions, int relevantTempPositions, int relevantPermanentPositions){
        this.companyId = new CompanyId(date,company);
        this.totalPositions = totalPositions;
        this.tempPositions = tempPositions;
        this.permanentPositions = permanentPositions;
        this.relevantPositions = relevantPositions;
        this.relevantTempPositions = relevantTempPositions;
        this.relevantPermanentPositions = relevantPermanentPositions;
    }

    //GETTERS AND SETTERS

    public CompanyId getCompanyId(){
        return companyId;
    }

    public void setCompanyId(CompanyId companyId){
        this.companyId = companyId;
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

    public int getTotalPositions() {
        return totalPositions;
    }

    public void setTotalPositions(int totalPositions) {
        this.totalPositions = totalPositions;
    }
}
