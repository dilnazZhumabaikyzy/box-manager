package kz.sparklab.restnode.controller;

import kz.sparklab.restnode.dto.SmartBoxDto;
import kz.sparklab.restnode.service.SmartBoxService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("smart")
public class SmartBoxController {
    private final SmartBoxService smartBoxService;
    
    public SmartBoxController(SmartBoxService smartBoxService ) {
        this.smartBoxService = smartBoxService;
    }


    @PostMapping
    public ResponseEntity<SmartBoxDto> create(@RequestBody SmartBoxDto smartBoxDto) {
        SmartBoxDto createdSmartBox = smartBoxService.create(smartBoxDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSmartBox);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        smartBoxService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String boxId) {
        smartBoxService.delete(boxId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    public ResponseEntity<SmartBoxDto> update(@RequestBody SmartBoxDto SmartBoxDto) {
        SmartBoxDto updatedBox = smartBoxService.update(SmartBoxDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBox);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<SmartBoxDto>> getAll(){
        List<SmartBoxDto> smartBoxDtoList = smartBoxService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(smartBoxDtoList);
    }
    @GetMapping("/id")
    public ResponseEntity<SmartBoxDto> getBoxById(@RequestParam String boxId){
        SmartBoxDto smartBoxDto = smartBoxService.getBoxById(boxId);
        return ResponseEntity.status(HttpStatus.OK).body(smartBoxDto);
    }
    @GetMapping("/name")
    public ResponseEntity<SmartBoxDto> getBoxByName(@RequestParam String boxName){
        SmartBoxDto smartBoxDto = smartBoxService.getBoxByName(boxName);
        return ResponseEntity.status(HttpStatus.OK).body(smartBoxDto);
    }


}
