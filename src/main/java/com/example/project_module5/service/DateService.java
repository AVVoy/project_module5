package com.example.project_module5.service;

import java.time.LocalDate;
import java.util.List;

public interface DateService {
    List<LocalDate> getRange(LocalDate startDate, LocalDate endDate);
}
