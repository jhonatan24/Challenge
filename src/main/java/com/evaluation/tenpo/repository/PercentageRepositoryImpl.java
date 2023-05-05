package com.evaluation.tenpo.repository;

import com.evaluation.tenpo.client.PercentageClient;
import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import com.evaluation.tenpo.model.AuditOperation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PercentageRepositoryImpl implements PercentageRepository {

    private PercentageClient percentageClient;
    private AuditOperationRepository auditOperationRepository;

    public PercentageRepositoryImpl(PercentageClient percentageClient, AuditOperationRepository auditOperationRepository) {
        this.percentageClient = percentageClient;
        this.auditOperationRepository = auditOperationRepository;
    }

    @Override
    @Retryable(
            value = RemoteServiceNotAvailableException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000))
    public Optional<PercentageDTO> getPercentage() throws RemoteServiceNotAvailableException {
       return Optional.ofNullable(percentageClient.getPercentage()).or(this::findLastPercentage);
    }

    @Recover
    private Optional<PercentageDTO> failGetPercentage(RemoteServiceNotAvailableException ex){
        log.error("fails call getPercentage",ex);
        return findLastPercentage();
    }
    
    private Optional<PercentageDTO> findLastPercentage(){
        try{
            return auditOperationRepository.findLastCreateDate().map(PercentageDTO::from);
        }catch (Exception error){
            log.error("fails getpercentage of database",error);
            return Optional.empty();
        }
    }

    @Override
    public void saveHistoryPercentage(PercentageDTO dto, OperationRequestDTO request, Integer result) {
        CompletableFuture.runAsync(
                () -> {
                    var model =
                            AuditOperation.builder()
                                    .createDate(LocalDateTime.now())
                                    .value(List.of(request.getValueA(), request.getValueB()))
                                    .percentage(dto.getPercentage())
                                    .resul(result)
                                    .build();
                    log.info("save audit objet {}", model);
                    auditOperationRepository.save(model);
                });
    }
    
}
