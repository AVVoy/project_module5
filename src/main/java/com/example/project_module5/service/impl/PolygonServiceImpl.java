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


    @Override
    public String findTicker(SaveTickerRequest request) {

        String tickerName = request.getName();
        int year = Integer.parseInt(request.getDate().substring(0, 4));
        String startDate = LocalDate.of(year, 1, 1).toString();
        LocalDate now = LocalDate.now();
        LocalDate end = LocalDate.of(year, 12, 31);
        end = now.isBefore(end) ? now : end;
        String endDate = end.toString();

        var restClient = RestClient.create();


        String uri = String.format(
                "https://api.polygon.io/v2/aggs/ticker/%s/range/1/day/%s/%s?adjusted=true&sort=asc&apiKey=%s",
                tickerName, startDate, endDate, polygonSigningKey);

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
}
