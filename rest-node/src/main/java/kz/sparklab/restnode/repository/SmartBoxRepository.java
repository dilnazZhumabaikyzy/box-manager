package kz.sparklab.restnode.repository;

import jakarta.transaction.Transactional;
import kz.sparklab.restnode.model.SmartBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SmartBoxRepository extends JpaRepository<SmartBox, Long> {

//    @Transactional
//    @Modifying
//    @Query("UPDATE SmartBox sb SET sb.isActive = :isActive, sb.sensorHeight = :sensorHeight WHERE sb.id = :existingBoxId")
//    void updateBoxToSmartBox(Long existingBoxId, boolean isActive, int sensorHeight);

//    Optional<SmartBox> findById(Long id);
}
