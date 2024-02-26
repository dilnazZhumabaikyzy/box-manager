package kz.sparklab.dao;

import kz.sparklab.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
