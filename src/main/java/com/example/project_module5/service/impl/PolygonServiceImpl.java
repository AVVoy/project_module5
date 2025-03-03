package com.example.project_module5.service.impl;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.service.PolygonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {

    @Value("${polygon.signing.key}")
    private String polygonSigningKey;
    private final RestClient restClient;


    @Override
    public String findTicker(SaveTickerRequest request) {

        String tickerName = request.getName();
        int year = LocalDate.parse(request.getDate())
                .getYear();
        String startDate = LocalDate.of(year, 1, 1).toString();
        LocalDate now = LocalDate.now();
        LocalDate end = LocalDate.of(year, 12, 31);
        end = now.isBefore(end) ? now : end;
        String endDate = end.toString();

        return restClient.get()
                .uri("https://api.polygon.io/v2/aggs/ticker/{ticker}/range/1/day/{from}/{to}?adjusted=true&sort=asc&apiKey={key}",
                        tickerName, startDate, endDate, polygonSigningKey)
                .retrieve()
                .body(String.class);
    }
}
