package com.evaluation.tenpo.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Error {
    private Timestamp timestamp;
    private List<String> message;
    private String error;
    private Integer status;
}
