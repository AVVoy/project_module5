package com.example.project_module5.service.impl;

import com.example.project_module5.dto.DailyOpenCloseTicker;
import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.service.PolygonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {

    @Value("${polygon.signing.key}")
    private String polygonSigningKey;
    private final RestClient restClient;


    @Override
    public DailyOpenCloseTicker findTicker(SaveTickerRequest request) throws HttpClientErrorException.NotFound {

        String tickerName = request.getName();
        LocalDate startDate = LocalDate.parse(request.getDate());
        DailyOpenCloseTicker ticker;
        ticker = restClient.get()

                .uri("https://api.polygon.io/v1/open-close/{ticker}/{startDate}?adjusted=true&apiKey={key}",
                        tickerName, startDate, polygonSigningKey)
                .retrieve()
                .body(DailyOpenCloseTicker.class);
        return ticker;
    }
}
