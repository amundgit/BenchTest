package api.Repositories;

import org.springframework.data.repository.CrudRepository;

import api.Models.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface PositionRepository extends CrudRepository<Position, Integer>{
    @Query("SELECT p FROM Position p WHERE position_id = :id")
    Position getById(@Param("id") Integer id);

    @Query("SELECT p FROM Position p WHERE date_collected = :date")
    List<Position> getByDate(@Param("date") LocalDate date);

    @Query("SELECT EXISTS(SELECT p FROM Position p WHERE date_collected = :date AND companyName = :companyName AND positionDuration = :positionDuration AND positionName = :positionName AND noOfPositions = :noOfPositions AND field = :field)")
    boolean exists(@Param("date") LocalDate date, @Param("companyName") String companyName, @Param("positionDuration") String positionDuration,
                     @Param("positionName") String positionName, @Param("noOfPositions") Integer noOfPositions, @Param("field") String field);
}
