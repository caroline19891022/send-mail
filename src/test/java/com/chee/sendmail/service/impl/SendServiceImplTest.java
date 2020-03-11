package com.chee.sendmail.service.impl;

import com.chee.sendmail.Application;
import com.chee.sendmail.entity.TableRow;
import com.chee.sendmail.service.ReadService;
import com.chee.sendmail.service.SendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SendServiceImplTest {

    @Autowired
    private SendService sendService;
    @Autowired
    private ReadService readService;

    @Test
    public void send() {
        String filePath = "F:\\download\\还消组远程工作量统计-3月.xls";
        LocalDate localDate = LocalDate.now();

        List<TableRow> read = readService.read(filePath, localDate);
        System.out.println(read);
        sendService.sendToClient(read, localDate);
        sendService.sendToManager(filePath, localDate);
    }
}