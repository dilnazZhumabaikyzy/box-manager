package kz.sparklab.restnode.service;

import kz.sparklab.restnode.dto.SmartBoxDto;

import java.util.List;

public interface SmartBoxService {
    SmartBoxDto create(SmartBoxDto smartBoxDto);

    SmartBoxDto update(SmartBoxDto smartBoxDto);

    List<SmartBoxDto> getAll();

    SmartBoxDto getBoxById(String boxId);
    void deleteAll();
    void delete(String smartBoxId);

    SmartBoxDto getBoxByName(String boxName);
}
