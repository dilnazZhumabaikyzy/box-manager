package kz.sparklab.restnode.service;

import kz.sparklab.restnode.dto.BoxDto;
import kz.sparklab.restnode.dto.SmartBoxDto;
import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.exception.SmartBoxNotFoundException;
import kz.sparklab.restnode.model.Box;
import kz.sparklab.restnode.model.BoxType;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.repository.BoxRepository;
import kz.sparklab.restnode.repository.SmartBoxRepository;
import org.springframework.stereotype.Service;

@Service
public class SmartBoxService {
    private final BoxRepository boxRepository;
    private final SmartBoxRepository smartBoxRepository;

    public SmartBoxService(BoxRepository boxRepository, SmartBoxRepository smartBoxRepository) {
        this.boxRepository = boxRepository;
        this.smartBoxRepository = smartBoxRepository;
    }

    public SmartBoxDto create(SmartBoxDto smartBoxDto) {
        SmartBox smartBox = new SmartBox();

        smartBox.setName(smartBoxDto.getName());
        smartBox.setAddress(smartBoxDto.getAddress());
        smartBox.setHeight(smartBoxDto.getHeight());
        smartBox.setLength(smartBoxDto.getLength());
        smartBox.setWidth(smartBoxDto.getWidth());
        smartBox.setType(BoxType.valueOf(smartBoxDto.getType()));

        smartBox.setAddress(smartBoxDto.getAddress());
        smartBox.setSensorHeight(smartBoxDto.getSensorHeight());

        SmartBox createdSmartBox = smartBoxRepository.save(smartBox);

        return new SmartBoxDto(createdSmartBox);
    }

    public SmartBoxDto createOnExistBox(SmartBoxDto smartBoxDto) {
        Box existingBox = boxRepository.findByName(smartBoxDto.getName()).orElseThrow(BoxNotFoundException::new);

        SmartBox smartBox = new SmartBox();
        smartBox.setId(existingBox.getId());
        smartBox.setName(smartBoxDto.getName());
        smartBox.setAddress(smartBoxDto.getAddress());
        smartBox.setHeight(smartBoxDto.getHeight());
        smartBox.setLength(smartBoxDto.getLength());
        smartBox.setWidth(smartBoxDto.getWidth());
        smartBox.setType(BoxType.valueOf(smartBoxDto.getType()));

        smartBox.setAddress(smartBoxDto.getAddress());
        smartBox.setSensorHeight(smartBoxDto.getSensorHeight());

        boxRepository.delete(existingBox);
        SmartBox newBox = smartBoxRepository.save(smartBox);

        return new SmartBoxDto(newBox);
    }

    public void delete(SmartBoxDto smartBoxDto) {
        SmartBox existingSmartBox = smartBoxRepository.findByName(smartBoxDto.getName()).orElseThrow(BoxNotFoundException::new);
        Box newBox = new Box();
        newBox.setName(existingSmartBox.getName());
        newBox.setAddress(existingSmartBox.getAddress());
        newBox.setHeight(existingSmartBox.getHeight());
        newBox.setLength(existingSmartBox.getLength());
        newBox.setWidth(existingSmartBox.getWidth());
        newBox.setType(existingSmartBox.getType());

        smartBoxRepository.delete(existingSmartBox);
        boxRepository.save(newBox);
    }

    public SmartBoxDto update(SmartBoxDto smartBoxDto) {
        SmartBox existingSmartBox = smartBoxRepository.findByName(smartBoxDto.getName()).orElseThrow(BoxNotFoundException::new);

        existingSmartBox.setName(smartBoxDto.getName());
        existingSmartBox.setAddress(smartBoxDto.getAddress());
        existingSmartBox.setHeight(smartBoxDto.getHeight());
        existingSmartBox.setLength(smartBoxDto.getLength());
        existingSmartBox.setWidth(smartBoxDto.getWidth());
        existingSmartBox.setType(BoxType.valueOf(smartBoxDto.getType()));

        existingSmartBox.setAddress(smartBoxDto.getAddress());
        existingSmartBox.setSensorHeight(smartBoxDto.getSensorHeight());

       SmartBox updatedBox = smartBoxRepository.save(existingSmartBox);
       return new SmartBoxDto(updatedBox);
    }
}
