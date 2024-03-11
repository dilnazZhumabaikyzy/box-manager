package kz.sparklab.restnode.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.mail.EmailBody;
import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.model.SensorReport;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.repository.SensorReportRepository;
import kz.sparklab.restnode.repository.SmartBoxRepository;
import org.springframework.stereotype.Service;

@Service
public class SensorReportService {
    private final SensorReportRepository sensorReportRepository;
    private final SmartBoxRepository smartBoxRepository;

    public SensorReportService(SensorReportRepository sensorReportRepository, SmartBoxRepository smartBoxRepository) {
        this.sensorReportRepository = sensorReportRepository;
        this.smartBoxRepository = smartBoxRepository;
    }

    public void processInboundEmailReport(EmailRequest emailRequest){
        if(emailRequest.getSender().equals("200107114@stu.sdu.edu.kz")){
            try {
                EmailBody emailBody =  new ObjectMapper().readValue(emailRequest.getMailBody()
                        , EmailBody.class);
                SmartBox smartBox = smartBoxRepository.findByName(emailBody.getBox_name()).orElseThrow(BoxNotFoundException::new);
                SensorReport sensorReport = SensorReport.builder().box(smartBox).fullness(Double.parseDouble(emailBody.getFullness())).build();
                sensorReportRepository.save(sensorReport);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("sender: " + emailRequest.getSender());
        }
    }
}
