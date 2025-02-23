package com.example.project_module5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickers")
public class Ticker {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticker_id_seq")
    @SequenceGenerator(name = "ticker_id_seq", sequenceName = "ticker_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_price", nullable = false)
    private Double startPrice;

    @Column(name = "end_price", nullable = false)
    private Double endPrice;

    @Column(name = "high_price", nullable = false)
    private Double highPrice;

    @Column(name = "low_price", nullable = false)
    private Double lowPrice;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ticker")
    private List<HistoryRequestTicker> historyRequestTickers;
}
