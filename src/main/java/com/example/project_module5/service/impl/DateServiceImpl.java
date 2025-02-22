package com.example.project_module5.service.impl;

import com.example.project_module5.service.DateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DateServiceImpl implements DateService {
    @Override
    public List<LocalDate> getRange(LocalDate startDate, LocalDate endDate) {

        return startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());
    }
}
