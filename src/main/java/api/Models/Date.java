package api.Models;

import javax.persistence.*;
import java.time.LocalDate;

//THIS CLASS CREATES A TABLE OF DATES, TO BE USED BY THE COMPANY AND POSITION TABLES
@Entity // This tells Hibernate to make a table out of this class
public class Date {
    @Id
    private LocalDate date;

    //CONSTRUCTOR - any entry in this table should always be the date when the function is called
    public Date(){
        this.date = LocalDate.now();
    }

    //GETTERS AND SETTERS - for hibernate + convenience
    public LocalDate getDate(){
        return this.date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }
}
