package com.guan.entiity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HRError {
    String type;

    @Override
    public String toString() {
        return "HRError{" +
                "type='" + type + '\'' +
                '}';
    }
}
