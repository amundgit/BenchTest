package api.CompositeIds;

import javax.persistence.*;

import java.util.Objects;
import java.io.Serializable;

import api.Models.*;

@Embeddable
public class CompanyId implements Serializable {
    //FOREIGN KEY
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "date_collected", nullable = false)
    private Date date;

    private String company;

    //CONSTRUCTORS
    public CompanyId(){}

    public CompanyId(Date date, String company){
        this.date = date;
        this.company = company;
    }

    //GETTERS AND SETTERS
    public Date getDate(){
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    //OVERRIDES
    @Override
    public boolean equals(Object o){
        if(this == o){return true;}
        if(!(o instanceof CompanyId)){return false;}
        CompanyId that = (CompanyId) o;
        return Objects.equals(getDate(), that.getDate()) && Objects.equals(getCompany(), that.getCompany());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getCompany());
    }
}
