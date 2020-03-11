package com.chee.sendmail.service;

import com.chee.sendmail.entity.TableRow;

import java.time.LocalDate;
import java.util.List;

public interface SendService {
    void sendToClient(List<TableRow> sendList, LocalDate sendDate);
    void sendToManager(String filePath, LocalDate localDate);
}
