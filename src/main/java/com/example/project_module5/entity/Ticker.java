package com.example.project_module5.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickers")
public class Ticker {
    @Getter
    @Setter
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticker_id_seq")
    @SequenceGenerator(name = "ticker_id_seq", sequenceName = "ticker_id_seq", allocationSize = 1)
    private Long id;

    @Getter
    @Setter
    @Column
    private String name;

    @Getter
    @Setter
    @Column
    private LocalDate date;

    @Getter
    @Setter
    @Column
    private Double startPrice;

    @Getter
    @Setter
    @Column
    private Double endPrice;

    @Getter
    @Setter
    @Column
    private Double highPrice;

    @Getter
    @Setter
    @Column
    private Double lowPrice;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ticker")
    private List<HistoryRequestTicker> historyRequestTickers;

    @Override
    public String toString() {
        return "Ticker {" +
                "name=" + name + ", " +
                "date=" + date + ", " +
                "startPrice=" + startPrice + ", " +
                "endPrice=" + endPrice + ", " +
                "highPrice=" + highPrice + ", " +
                "lowPrice=" + lowPrice +
                "}";
    }
}
