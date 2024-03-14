package kz.sparklab.restnode.service;

import kz.sparklab.restnode.mail.EmailRequest;

import java.util.Map;

public interface SensorReportService {
    void processInboundEmailReport(EmailRequest emailRequest);
    Map<String, Integer> getSensorReport();
}
