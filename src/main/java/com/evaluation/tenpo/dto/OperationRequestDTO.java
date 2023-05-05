package com.evaluation.tenpo.dto;

import static com.evaluation.tenpo.config.Const.REQUIRED;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequestDTO {
    @NotNull(message=REQUIRED)
    @Min(value = 1,message = REQUIRED)
    private Integer valueA;
    @NotNull(message = REQUIRED)
    @Min(value = 1,message = REQUIRED)
    private Integer valueB;
}

