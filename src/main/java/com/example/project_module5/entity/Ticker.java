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
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticker_id_seq")
    @SequenceGenerator(name = "ticker_id_seq", sequenceName = "ticker_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate date;

    @Column
    private Double startPrice;

    @Column
    private Double endPrice;

    @Column
    private Double highPrice;

    @Column
    private Double lowPrice;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ticker")
    private List<HistoryRequestTicker> historyRequestTickers;
}
