package com.chee.sendmail.config;

import com.chee.sendmail.entity.TableRow;
import com.chee.sendmail.service.ReadService;
import com.chee.sendmail.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class StartSend {
    //  sendToClient: true
    //  sendToManager: true
    //  filePath: F:\download\还消组远程工作量统计-3月.xls
    //  sendDateEarly: 0
    @Value("${sendset.sendToClient}")
    private String sendToClient;
    @Value("${sendset.sendToManager}")
    private String sendToManager;
    @Value("${sendset.filePath}")
    private String filePath;
    @Value("${sendset.sendDateEarly}")
    private String sendDateEarly;

    @Autowired
    private ReadService readService;
    @Autowired
    private SendService sendService;

    @PostConstruct
    public void run() {
        long sendDateEarly = Long.parseLong(this.sendDateEarly);
        LocalDate sendDate = LocalDate.now().minusDays(sendDateEarly);
        boolean isSendToC = Boolean.parseBoolean(sendToClient);
        boolean isSendToM = Boolean.parseBoolean(sendToManager);
        List<TableRow> read = readService.read(filePath, sendDate);
        System.out.println(read);
        if (isSendToC) {
            sendService.sendToClient(read, sendDate);
        }
        if (isSendToM) {
            sendService.sendToManager(filePath, sendDate);
        }
        System.out.println("发送结束");
    }
}
