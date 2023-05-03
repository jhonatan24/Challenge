package com.evaluation.tenpo.service;

import com.evaluation.tenpo.client.GeneratorClient;
import com.evaluation.tenpo.dto.FormulaDTO;
import com.evaluation.tenpo.dto.FormulaResultDTO;
import com.evaluation.tenpo.dto.GeneratorDTO;
import com.evaluation.tenpo.model.AuditFormula;
import com.evaluation.tenpo.repository.AuditFormulaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FormulaService {

  @Autowired private AuditFormulaRepository auditFormulaRepository;
  @Autowired private GeneratorClient generatorClient;

  public FormulaResultDTO calculated(FormulaDTO formulaDTO) {
    Optional<GeneratorDTO> infoPercentage =
        generatorClient
            .getPercentage()
            .or(() -> auditFormulaRepository.findLastCreateDate().map(GeneratorDTO::from));

    if (infoPercentage.isEmpty()) {
      throw new RuntimeException("no se pudo efectuar la operacion");
    }

    Integer result =
        formulaDTO.getValueA() + formulaDTO.getValueB() % infoPercentage.get().getPercentage();

    CompletableFuture.runAsync(
        () -> this.saveAuditFormula(infoPercentage.get(), formulaDTO, result));

    return FormulaResultDTO.builder().result(result).build();
  }

  private void saveAuditFormula(GeneratorDTO dto, FormulaDTO request, Integer result) {
    var model =
        AuditFormula.builder()
            .createDate(LocalDateTime.now())
            .value(List.of(request.getValueA(), request.getValueB()))
            .percentage(dto.getPercentage())
            .resul(result)
            .build();
    log.info("save audit objet {}", model);
    auditFormulaRepository.save(model);
  }
}
