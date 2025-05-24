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

    @Column
    private Long userId;

    @Column
    private Long tickerId;

    @Override
    public int hashCode() {
        int result = (int) (31*userId+ tickerId);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UserTickerId other = (UserTickerId) obj;
        if (userId != other.userId) return false;
        return tickerId == other.tickerId;
    }
}
