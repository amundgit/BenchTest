package api.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer positionId;

    //FOREIGN KEY FOR DATE
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "date_collected", nullable = false)
    private Date date;

    //FOREIGN KEY FOR COMPANY
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "company_name", nullable = false)
    private Company company;

    private String positionDuration;

    private String positionName;

    private int noOfPositions;

    private String field;

    private String area = "";

    private String customer = "";

    //GETTERS AND SETTERS
    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPositionDuration() {
        return positionDuration;
    }

    public void setPositionDuration(String positionDuration) {
        this.positionDuration = positionDuration;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getNoOfPositions() {
        return noOfPositions;
    }

    public void setNoOfPositions(int noOfPositions) {
        this.noOfPositions = noOfPositions;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
