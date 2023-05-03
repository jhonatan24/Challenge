package com.evaluation.tenpo.service;

import com.evaluation.tenpo.client.GeneratorClient;
import com.evaluation.tenpo.dto.FormulaDTO;
import com.evaluation.tenpo.dto.GeneratorDTO;
import com.evaluation.tenpo.model.AuditFormula;
import com.evaluation.tenpo.repository.AuditFormulaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FormulaServiceTest {

  @InjectMocks private FormulaService formulaService;

  @Mock private AuditFormulaRepository auditFormulaRepository;

  @Mock private GeneratorClient generatorClient;

  @Test
  void calculatedWhenAllOk() throws InterruptedException {
    GeneratorDTO generatorDTO =
        GeneratorDTO.builder().createDate(LocalDateTime.now()).percentage(34).build();
    Mockito.when(generatorClient.getPercentage()).thenReturn(Optional.of(generatorDTO));
    var resul = formulaService.calculated(FormulaDTO.builder().valueA(23).valueB(43).build());
    Mockito.verify(auditFormulaRepository, Mockito.never()).findLastCreateDate();
    Thread.sleep(2000);
    Mockito.verify(auditFormulaRepository, Mockito.times(1)).save(Mockito.any());
    Assertions.assertNotNull(resul);
    Assertions.assertNotNull(resul.getResult());
  }

  @Test
  void calculatedWhenGeneratorServiceReturnEmptyButExitsRegisterInDataBase()
      throws InterruptedException {
    Mockito.when(generatorClient.getPercentage()).thenReturn(Optional.empty());
    var resulAuditForm = Optional.of(AuditFormula.builder().percentage(30).build());
    Mockito.when(auditFormulaRepository.findLastCreateDate()).thenReturn(resulAuditForm);

    var resul = formulaService.calculated(FormulaDTO.builder().valueA(23).valueB(43).build());

    Mockito.verify(auditFormulaRepository, Mockito.times(1)).findLastCreateDate();
    Thread.sleep(2000);
    Mockito.verify(auditFormulaRepository, Mockito.times(1)).save(Mockito.any());

    Assertions.assertNotNull(resul);
    Assertions.assertNotNull(resul.getResult());
  }

  @Test()
  void calculatedWhenGeneratorServiceReturnEmptyAndNotExitsRegisterInDataBase()
      throws InterruptedException {
    Mockito.when(generatorClient.getPercentage()).thenReturn(Optional.empty());
    Mockito.when(auditFormulaRepository.findLastCreateDate()).thenReturn(Optional.empty());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          formulaService.calculated(FormulaDTO.builder().valueA(23).valueB(43).build());
        });

    Mockito.verify(auditFormulaRepository, Mockito.times(1)).findLastCreateDate();
    Thread.sleep(2000);
    Mockito.verify(auditFormulaRepository, Mockito.never()).save(Mockito.any());
  }
}
