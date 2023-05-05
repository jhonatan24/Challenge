package com.evaluation.tenpo.model;


import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AuditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date timestamp;
    private String remoteAddress;
    private String requestMethod;
    private String requestUri;
    private int responseStatus;
    @Column(columnDefinition = "TEXT")
    private String requestBody;
    @Column(columnDefinition = "TEXT")
    private String responseBody;
}
