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

    @Query("SELECT p FROM Position p WHERE company_name = :companyName")
    List<Position> getByCompanyName(@Param("companyName") String companyName);

    @Query("SELECT p FROM Position p WHERE company_name = :companyName AND date_collected = :date")
    List<Position> getByCompanyNameAndDate(@Param("companyName") String companyName, @Param("date") LocalDate date);

    @Query("SELECT p FROM Position p WHERE date_collected = :date AND company_name = :companyName AND position_duration = :positionDuration AND position_name = :positionName AND no_of_positions = :noOfPositions AND field = :field")
    Position exists(@Param("date") LocalDate date, @Param("companyName") String companyName, @Param("positionDuration") String positionDuration,
                     @Param("positionName") String positionName, @Param("noOfPositions") Integer noOfPositions, @Param("field") String field);

    @Query("SELECT p FROM Position p WHERE field = :field")
    List<Position> getByField(@Param("field") String field);

    @Query("SELECT p FROM Position p WHERE field = :field AND company_name = :companyName")
    List<Position> getByFieldAndCompanyName(@Param("field") String field, @Param("companyName") String companyName);

    @Query("SELECT * FROM Position p.no_of_positions WHERE field = :field AND company_name = :companyName")
    List<Integer> countByFieldAndCompanyName(@Param("field") String field, @Param("companyName") String companyName);
}
