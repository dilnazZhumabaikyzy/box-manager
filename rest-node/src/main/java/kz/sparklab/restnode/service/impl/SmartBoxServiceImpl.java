package kz.sparklab.restnode.service.impl;


import jakarta.persistence.PersistenceException;
import kz.sparklab.restnode.dto.SmartBoxDto;
import kz.sparklab.restnode.exception.BoxCreationError;
import kz.sparklab.restnode.exception.BoxNotFoundException;

import kz.sparklab.restnode.exception.DeletionFailedException;

import kz.sparklab.restnode.exception.InvalidBoxTypeException;
import kz.sparklab.restnode.model.BoxType;
import kz.sparklab.restnode.model.SmartBox;

import kz.sparklab.restnode.repository.SmartBoxRepository;
import kz.sparklab.restnode.service.SmartBoxService;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Service
public class SmartBoxServiceImpl implements SmartBoxService {

    private final SmartBoxRepository smartBoxRepository;

    public SmartBoxServiceImpl(SmartBoxRepository smartBoxRepository) {
        this.smartBoxRepository = smartBoxRepository;
    }

    @Override
    public SmartBoxDto create(SmartBoxDto smartBoxDto) {
        SmartBox smartBox = convertDtoToEntity(smartBoxDto);
        SmartBox savedSmartBox;
        try {
            savedSmartBox = smartBoxRepository.save(smartBox);
            log.info("NEW BOX CREATED. ID: " + savedSmartBox.getId());
        }
        catch (Exception e){
                throw new BoxCreationError(e.getMessage());
        }
        return convertEntityToDto(savedSmartBox);
    }

    @Override
    public void deleteAll() {
        try {
            smartBoxRepository.deleteAll();
            log.info("DELETION OF ALL SMART BOXES SUCCESSFUL");
        }
        catch (DeletionFailedException e){
            throw new DeletionFailedException(e.getMessage());
        }
    }

    @Override
    public void delete(String smartBoxId) {
        SmartBox existingSmartBox = smartBoxRepository.findById(Long.valueOf(smartBoxId)).orElseThrow(BoxNotFoundException::new);

        try {
            smartBoxRepository.delete(existingSmartBox);
            log.info("SMART BOX DELETED SUCCESSFULLY. ID: " + smartBoxId);

        }
        catch (DeletionFailedException e){
            throw new DeletionFailedException(e.getMessage());
        }
    }

    @Override
    public SmartBoxDto update(SmartBoxDto smartBoxDto) {
        SmartBox existingSmartBox = smartBoxRepository.findByName(smartBoxDto.getName()).orElseThrow(BoxNotFoundException::new);

        existingSmartBox.setName(smartBoxDto.getName());
        existingSmartBox.setAddress(smartBoxDto.getAddress());
        existingSmartBox.setHeight(smartBoxDto.getHeight());
        existingSmartBox.setLength(smartBoxDto.getLength());
        existingSmartBox.setWidth(smartBoxDto.getWidth());

        existingSmartBox.setType(getBoxType(smartBoxDto));

        existingSmartBox.setActive(smartBoxDto.isActive());
        existingSmartBox.setSensorHeight(smartBoxDto.getSensorHeight());

        SmartBox updatedBox = smartBoxRepository.save(existingSmartBox);

        log.info("SMART BOX UPDATED SUCCESSFULLY.");
        return convertEntityToDto(updatedBox);
    }

    @Override
    public List<SmartBoxDto> getAll() {
        List<SmartBox> smartBoxList  = smartBoxRepository.findAll();
        return  convertEntitiesToDtos(smartBoxList);
    }

    @Override
    public SmartBoxDto getBoxById(String smartBoxId) {
        System.out.println(smartBoxId);
        SmartBox smartBox  = smartBoxRepository.findById(Long.valueOf(smartBoxId)).orElseThrow(BoxNotFoundException::new);
        return  convertEntityToDto(smartBox);
    }

    // Helper method to convert List of entity to List of Dto
    private List<SmartBoxDto> convertEntitiesToDtos(List<SmartBox> smartBoxList) {
        List<SmartBoxDto> smartBoxDtoList = new ArrayList<>();
        smartBoxList.forEach(smartBox -> {
            smartBoxDtoList.add(convertEntityToDto(smartBox));
        });
        return  smartBoxDtoList;
    }

    // Helper method to convert DTO to entity
    private SmartBox convertDtoToEntity(SmartBoxDto smartBoxDto) {
        BoxType boxType = getBoxType(smartBoxDto);

        SmartBox smartBox = SmartBox.builder()
                .name(smartBoxDto.getName())
                .address(smartBoxDto.getAddress())
                .type(boxType)
                .width(smartBoxDto.getWidth())
                .length(smartBoxDto.getLength())
                .height(smartBoxDto.getHeight())
                .sensorHeight(smartBoxDto.getSensorHeight())
                .isActive(smartBoxDto.isActive())
                .build();
        return  smartBox;
    }

    private static BoxType getBoxType(SmartBoxDto smartBoxDto) {
        BoxType  boxType;
        try{
            boxType = BoxType.valueOf(smartBoxDto.getType());
        }
        catch (InvalidBoxTypeException exception){
            throw new InvalidBoxTypeException();
        }
        return boxType;
    }

    // Helper method to convert entity to DTO
    private SmartBoxDto convertEntityToDto(SmartBox smartBox) {
        return new SmartBoxDto(smartBox);
    }
}
