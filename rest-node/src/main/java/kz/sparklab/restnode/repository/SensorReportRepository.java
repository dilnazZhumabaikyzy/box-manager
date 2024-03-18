package kz.sparklab.restnode.repository;

import kz.sparklab.restnode.model.SensorReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorReportRepository extends JpaRepository<SensorReport,Long> {
    @Query("SELECT sr FROM SensorReport sr WHERE YEAR(sr.createdAt) = :year AND MONTH(sr.createdAt) = :month AND DAY(sr.createdAt) = :day")
    List<SensorReport> findByDate(@Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query("SELECT sr FROM SensorReport sr WHERE sr.createdAt IN (SELECT MAX(sr2.createdAt) FROM SensorReport sr2 GROUP BY sr2.box)")
    List<SensorReport> findLatestReportsForEachBox();
}
