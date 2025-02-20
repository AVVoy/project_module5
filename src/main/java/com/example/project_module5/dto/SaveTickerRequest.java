package com.example.project_module5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на сохранение данных акции")
public class SaveTickerRequest {

    @Schema(description = "Название акции", example = "AAPL")
    @Size(max = 50, message = "Название акции должно содержать до 50 символов")
    @NotBlank(message = "Название акции не может быть пустыми")
    private String name;

    @Schema(description = "Дата", example = "2025-02-20")
    @NotBlank(message = "Дата не может быть пустыми")
    private String date;
}
