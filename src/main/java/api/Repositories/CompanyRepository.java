package api.Repositories;

import org.springframework.data.repository.CrudRepository;

import api.Models.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, String> {
    @Query("SELECT c FROM Company c WHERE company = :id")
    List<Company> getByCompany(@Param("id") String id);

    @Query("SELECT c FROM Company c WHERE date_collected = :date")
    List<Company> getByDate(@Param("date") LocalDate date);

    @Query("SELECT c FROM Company c WHERE company = :id AND date_collected = :date")
    Company getByCompanyAndDate(@Param("id") String id, @Param("date") LocalDate date);

    @Query("SELECT c.relevantPositions FROM Company c WHERE company = :id AND date_collected >= :startDate AND date_collected <= :endDate")
    List<Integer> getRelNoByCompanyAndPeriod(@Param("id") String id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Company c WHERE company = :id AND date_collected >= :startDate AND date_collected <= :endDate")
    List<Company> getByCompanyAndPeriod(@Param("id") String id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Company c WHERE date_collected >= :startDate AND date_collected <= :endDate")
    List<Company> getByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
