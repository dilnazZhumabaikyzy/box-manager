package kz.sparklab.restnode.controller;



import kz.sparklab.restnode.dto.BoxDto;
import kz.sparklab.restnode.model.Box;
import kz.sparklab.restnode.service.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("box")
public class BoxController {
    private final BoxService boxService;
    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @PostMapping
    public ResponseEntity<BoxDto> create(@RequestBody Box box){
        BoxDto createdBox = boxService.create(box);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBox);
    }
    @PutMapping
    public ResponseEntity<BoxDto> update(@RequestBody BoxDto box){
        BoxDto updatedBox = boxService.update(box);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBox);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody BoxDto box){
        boxService.delete(box);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
