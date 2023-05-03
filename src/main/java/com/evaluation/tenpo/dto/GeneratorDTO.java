package com.evaluation.tenpo.dto;

import com.evaluation.tenpo.model.AuditFormula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorDTO {
    private LocalDateTime createDate;
    private Integer percentage;

    public static GeneratorDTO from(AuditFormula auditFormula){
        return GeneratorDTO.builder()
                .createDate(auditFormula.getCreateDate())
                .percentage(auditFormula.getPercentage()).build();
    }
}
