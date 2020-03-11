package com.chee.sendmail.task;

import com.chee.sendmail.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendTask {
    @Autowired
    private ReadService readService;

//    @Scheduled(fixedDelay = 1000)
//    public void task() {
//        readService.read();
//    }
}
