package kz.sparklab.restnode.controller;

import jakarta.transaction.Transactional;
import kz.sparklab.restnode.dto.BoxDto;
import kz.sparklab.restnode.dto.SmartBoxDto;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.service.SmartBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("smart")
public class SmartBoxController {
    private final SmartBoxService smartBoxService;

    public SmartBoxController(SmartBoxService smartBoxService) {
        this.smartBoxService = smartBoxService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<SmartBoxDto> create(@RequestBody SmartBoxDto smartBoxDto) {
        SmartBoxDto createdSmartBox = smartBoxService.create(smartBoxDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSmartBox);
    }

    @PostMapping("/box")
    public ResponseEntity<SmartBoxDto> createOnExistBox(@RequestBody SmartBoxDto SmartBoxDto) {
        SmartBoxDto createdSmartBox = smartBoxService.createOnExistBox(SmartBoxDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSmartBox);
    }
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody SmartBoxDto SmartBoxDto) {
        smartBoxService.delete(SmartBoxDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping
    public ResponseEntity<SmartBoxDto> update(@RequestBody SmartBoxDto SmartBoxDto) {
        SmartBoxDto updatedBox = smartBoxService.update(SmartBoxDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBox);
    }
}
