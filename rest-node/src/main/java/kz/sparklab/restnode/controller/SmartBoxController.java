package kz.sparklab.restnode.controller;

import jakarta.transaction.Transactional;
import kz.sparklab.restnode.dto.BoxDto;
import kz.sparklab.restnode.dto.SmartBoxDto;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.service.SmartBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    @Transactional
//    @PostMapping("/box")
//    public ResponseEntity<SmartBoxDto> createOnExistBox(@RequestBody SmartBoxDto SmartBoxDto) {
//        SmartBoxDto createdSmartBox = smartBoxService.createOnExistBox(SmartBoxDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdSmartBox);
//    }
}
