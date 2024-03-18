package kz.sparklab.restnode.service;

import kz.sparklab.restnode.dto.SensorReportDto;
import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.model.SensorReport;

import java.util.List;
import java.util.Map;

public interface SensorReportService {
    void processInboundEmailReport(EmailRequest emailRequest);
    Map<String, Integer> getSensorReport();

    List<SensorReportDto> getAll();

    void deleteAll();

    void delete(String reportId);

    SensorReportDto getSensorReportById(String reportId);
}
