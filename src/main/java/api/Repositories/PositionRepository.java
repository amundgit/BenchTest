package api.Repositories;

import org.springframework.data.repository.CrudRepository;

import api.Models.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface PositionRepository extends CrudRepository<Position, Integer>{
    //BASIC QUERIES
    @Query("SELECT p FROM Position p WHERE position_id = :id")
    Position getById(@Param("id") Integer id);

    @Query("SELECT p FROM Position p WHERE date_collected = :date")
    List<Position> getByDate(@Param("date") LocalDate date);

    @Query("SELECT p FROM Position p WHERE company_name = :companyName")
    List<Position> getByCompanyName(@Param("companyName") String companyName);

    @Query("SELECT p FROM Position p WHERE field = :field")
    List<Position> getByField(@Param("field") String field);

    @Query("SELECT p FROM Position p WHERE position_duration = :positionDuration")
    List<Position> getByDuration(@Param("positionDuration") String positionDuration);

    @Query("SELECT p FROM Position p WHERE field = :field AND company_name = :companyName")
    List<Position> getByFieldAndCompanyName(@Param("field") String field, @Param("companyName") String companyName);

    @Query("SELECT p FROM Position p WHERE field = :field AND position_duration = :positionDuration")
    List<Position> getByFieldAndDuration(@Param("field") String field, @Param("positionDuration") String positionDuration);

    @Query("SELECT p FROM Position p WHERE position_duration = :positionDuration AND company_name = :companyName")
    List<Position> getByDurationAndCompanyName(@Param("positionDuration") String positionDuration, @Param("companyName") String companyName);

    //QUERIES WITH DATE
    @Query("SELECT p FROM Position p WHERE company_name = :companyName AND date_collected = :date")
    List<Position> getByCompanyNameAndDate(@Param("companyName") String companyName, @Param("date") LocalDate date);

    @Query("SELECT p FROM Position p WHERE field = :field AND date_collected = :date")
    List<Position> getByFieldAndDate(@Param("field") String field, @Param("date") LocalDate date);

    @Query("SELECT p FROM Position p WHERE position_duration = :positionDuration AND date_collected = :date")
    List<Position> getByDurationAndDate(@Param("positionDuration") String positionDuration, @Param("date") LocalDate date);

    @Query("SELECT p FROM Position p WHERE field = :field AND company_name = :companyName AND date_collected = :date")
    List<Position> getByFieldAndCompanyNameAndDate(@Param("field") String field, @Param("companyName") String companyName, @Param("date") LocalDate date);

    @Query("SELECT p FROM Position p WHERE field = :field AND position_duration = :positionDuration AND date_collected = :date")
    List<Position> getByFieldAndDurationAndDate(@Param("field") String field, @Param("positionDuration") String positionDuration, @Param("date") LocalDate date);

    @Query("SELECT p FROM Position p WHERE position_duration = :positionDuration AND company_name = :companyName AND date_collected = :date")
    List<Position> getByDurationAndCompanyNameAndDate(@Param("positionDuration") String positionDuration, @Param("companyName") String companyName, @Param("date") LocalDate date);

    //QUERIES WITH PERIOD
    @Query("SELECT p FROM Position p WHERE date_collected >= :startDate AND date_collected <= :endDate")
    List<Position> getByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Position p WHERE company_name = :companyName AND date_collected >= :startDate AND date_collected <= :endDate")
    List<Position> getByCompanyNameAndPeriod(@Param("companyName") String companyName, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Position p WHERE field = :field AND date_collected >= :startDate AND date_collected <= :endDate")
    List<Position> getByFieldAndPeriod(@Param("field") String field, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Position p WHERE position_duration = :positionDuration AND date_collected >= :startDate AND date_collected <= :endDate")
    List<Position> getByDurationAndPeriod(@Param("positionDuration") String positionDuration, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //BASIC COUNT QUERIES
    @Query("SELECT p.noOfPositions FROM Position p WHERE company_name = :companyName")
    List<Integer> countByCompanyName(@Param("companyName") String companyName);

    @Query("SELECT p.noOfPositions FROM Position p WHERE field = :field")
    List<Integer> countByField(@Param("field") String field);

    @Query("SELECT p.noOfPositions FROM Position p WHERE position_duration = :positionDuration")
    List<Integer> countByDuration(@Param("positionDuration") String positionDuration);

    @Query("SELECT p.noOfPositions FROM Position p WHERE field = :field AND company_name = :companyName")
    List<Integer> countByFieldAndCompanyName(@Param("field") String field, @Param("companyName") String companyName);

    @Query("SELECT p.noOfPositions FROM Position p WHERE field = :field AND position_duration = :positionDuration")
    List<Integer> countByFieldAndDuration(@Param("field") String field, @Param("positionDuration") String positionDuration);

    @Query("SELECT p.noOfPositions FROM Position p WHERE position_duration = :positionDuration AND company_name = :companyName")
    List<Integer> countByDurationAndCompanyName(@Param("positionDuration") String positionDuration, @Param("companyName") String companyName);

    //COUNT QUERIES WITH DATE
    @Query("SELECT p.noOfPositions FROM Position p WHERE date_collected = :date AND company_name = :companyName")
    List<Integer> countByCompanyNameAndDate(@Param("companyName") String companyName,@Param("date") LocalDate date);

    @Query("SELECT p.noOfPositions FROM Position p WHERE field = :field AND date_collected = :date")
    List<Integer> countByFieldAndDate(@Param("field") String field, @Param("date") LocalDate date);

    @Query("SELECT p.noOfPositions FROM Position p WHERE position_duration = :positionDuration AND date_collected = :date")
    List<Integer> countByDurationAndDate(@Param("positionDuration") String positionDuration, @Param("date") LocalDate date);

    @Query("SELECT p.noOfPositions FROM Position p WHERE field = :field AND company_name = :companyName AND date_collected = :date")
    List<Integer> countByFieldAndCompanyNameAndDate(@Param("field") String field, @Param("companyName") String companyName, @Param("date") LocalDate date);

    @Query("SELECT p.noOfPositions FROM Position p WHERE field = :field AND position_duration = :positionDuration AND date_collected = :date")
    List<Integer> countByFieldAndDurationAndDate(@Param("field") String field, @Param("positionDuration") String positionDuration, @Param("date") LocalDate date);

    @Query("SELECT p.noOfPositions FROM Position p WHERE position_duration = :positionDuration AND company_name = :companyName AND date_collected = :date")
    List<Integer> countByDurationAndCompanyNameAndDate(@Param("positionDuration") String positionDuration, @Param("companyName") String companyName, @Param("date") LocalDate date);

    //OTHER QUERIES
    @Query("SELECT p FROM Position p WHERE date_collected = :date AND company_name = :companyName AND position_duration = :positionDuration AND position_name = :positionName AND no_of_positions = :noOfPositions AND field = :field")
    Position exists(@Param("date") LocalDate date, @Param("companyName") String companyName, @Param("positionDuration") String positionDuration,
                    @Param("positionName") String positionName, @Param("noOfPositions") Integer noOfPositions, @Param("field") String field);
}
