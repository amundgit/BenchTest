package api.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import api.Models.*;

import java.time.LocalDate;

public interface DateRepository extends CrudRepository<Date, LocalDate>{
    @Query("SELECT d FROM Date d WHERE date = :date")
    Date getByLocalDate(@Param("date") LocalDate date);
}
