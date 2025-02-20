package com.example.project_module5.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTickerId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ticker_id")
    private Long tickerId;
}
