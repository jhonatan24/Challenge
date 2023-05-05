package com.evaluation.tenpo.dto;

import com.evaluation.tenpo.model.AuditOperation;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PercentageDTO  implements Serializable {
    private LocalDateTime createDate;
    private Integer percentage;

    public static PercentageDTO from(AuditOperation auditOperation){
        return PercentageDTO.builder()
                .createDate(auditOperation.getCreateDate())
                .percentage(auditOperation.getPercentage()).build();
    }
}
