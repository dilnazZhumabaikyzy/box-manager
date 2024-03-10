package kz.sparklab.restnode.repository;

import kz.sparklab.restnode.model.SensorReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReportRepository extends JpaRepository<SensorReport,Long> {
}
