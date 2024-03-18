package kz.sparklab.restnode.repository;

import jakarta.transaction.Transactional;

import kz.sparklab.restnode.model.SmartBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SmartBoxRepository extends JpaRepository<SmartBox, Long> {
    Optional<SmartBox> findByName(String name);
    @Query("SELECT sb.id FROM SmartBox sb")
    List<Long> getAllBoxIds();
}
