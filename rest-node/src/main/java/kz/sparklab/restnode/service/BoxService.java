package kz.sparklab.restnode.service;

import kz.sparklab.restnode.dto.BoxDto;
import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.model.Box;
import kz.sparklab.restnode.model.BoxType;
import kz.sparklab.restnode.repository.BoxRepository;
import org.springframework.stereotype.Service;

@Service
public class BoxService {
    private final BoxRepository boxRepository;

    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public BoxDto create(Box box){
        Box createdBox = boxRepository.save(box);
        return new BoxDto(createdBox);
    }

    public BoxDto update(BoxDto updatedBoxDto) {
        Box existingBox = boxRepository.findByName(updatedBoxDto.getName()).orElseThrow(BoxNotFoundException::new);
        existingBox.setName(updatedBoxDto.getName());
        existingBox.setAddress(updatedBoxDto.getAddress());
        existingBox.setType(BoxType.valueOf(updatedBoxDto.getType()));
        existingBox.setLength(updatedBoxDto.getLength());
        existingBox.setHeight(updatedBoxDto.getHeight());
        existingBox.setWidth(updatedBoxDto.getWidth());

        Box updatedBox = boxRepository.save(existingBox);

        return new BoxDto(updatedBox);
    }

    public void delete(BoxDto box) {
        Box boxToDelete = boxRepository.findByName(box.getName()).orElseThrow(BoxNotFoundException::new);
        boxRepository.delete(boxToDelete);
    }
}
