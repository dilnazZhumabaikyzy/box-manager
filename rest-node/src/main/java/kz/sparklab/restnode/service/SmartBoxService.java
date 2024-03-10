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
    private final BoxService boxService;
    private final BoxRepository boxRepository;
    private final SmartBoxRepository smartBoxRepository;

    public SmartBoxService(BoxService boxService, BoxRepository boxRepository, SmartBoxRepository smartBoxRepository) {
        this.boxService = boxService;
        this.boxRepository = boxRepository;
        this.smartBoxRepository = smartBoxRepository;
    }

    public SmartBoxDto create(SmartBoxDto smartBoxDto) {
        SmartBox smartBox = new SmartBox();

//        smartBox.setName(smartBoxDto.getName());
//        smartBox.setAddress(smartBoxDto.getAddress());
//        smartBox.setHeight(smartBoxDto.getHeight());
//        smartBox.setLength(smartBoxDto.getLength());
//        smartBox.setWidth(smartBoxDto.getWidth());
//        smartBox.setType(BoxType.valueOf(smartBoxDto.getType()));
//
//        smartBox.setAddress(smartBoxDto.getAddress());
//        smartBox.setSensorHeight(smartBoxDto.getSensorHeight());

        SmartBox createdSmartBox = smartBoxRepository.save(smartBox);

        return new SmartBoxDto(createdSmartBox);
    }

//    public SmartBoxDto createOnExistBox(SmartBoxDto smartBoxDto) {
//        Box existingBox = boxRepository.findByName(smartBoxDto.getName()).orElseThrow(BoxNotFoundException::new);
//
//
//        smartBoxRepository.updateBoxToSmartBox(existingBox.getId(), smartBoxDto.isActive(),smartBoxDto.getSensorHeight());
//
//        return smartBoxDto;
//    }


}
