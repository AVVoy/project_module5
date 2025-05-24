package com.example.project_module5.service.impl;

import com.example.project_module5.dto.DailyOpenCloseTicker;
import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.exception.TickerNotFoundException;
import com.example.project_module5.service.PolygonService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private final MapperImpl mapper;


    @Override
    @SneakyThrows
    public Ticker findTicker(SaveTickerRequest request) throws HttpClientErrorException.NotFound {

        String tickerName = request.getName();
        LocalDate date = LocalDate.parse(request.getDate());
        DailyOpenCloseTicker tickerDto;

        try {
            tickerDto = restClient.get()
                    .uri("https://api.polygon.io/v1/open-close/{ticker}/{startDate}?adjusted=true&apiKey={key}",
                            tickerName, date, polygonSigningKey)
                    .retrieve()
                    .body(DailyOpenCloseTicker.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new TickerNotFoundException(
                    String.format(
                            "Данных по акциям %s за дату: %s не найдено! Проверьте имя акций, если оно верно, то в данный день биржа не работала!",
                            tickerName,
                            date
                    )
                    );
        }

        return mapper.map(tickerDto, Ticker.class);
    }
}
