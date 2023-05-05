package com.evaluation.tenpo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
public class AuditOperation {

    @Id
    @GeneratedValue
    private Long id;
    private List<Integer> value;
    private Integer resul;
    private Integer percentage;
    private LocalDateTime createDate;
}
