package api.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import api.Models.*;

import java.time.LocalDate;

public interface DateRepository extends CrudRepository<Date, LocalDate>{
}
