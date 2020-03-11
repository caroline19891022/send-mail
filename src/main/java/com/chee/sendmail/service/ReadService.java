package com.chee.sendmail.service;

import com.chee.sendmail.entity.TableRow;

import java.time.LocalDate;
import java.util.List;

public interface ReadService {

    List<TableRow> read(String filePath, LocalDate localDate);
}
