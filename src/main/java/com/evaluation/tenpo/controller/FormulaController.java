package com.evaluation.tenpo.controller;

import com.evaluation.tenpo.dto.FormulaDTO;
import com.evaluation.tenpo.dto.FormulaResultDTO;
import com.evaluation.tenpo.service.FormulaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/formula")
@Slf4j
public class FormulaController {

    private FormulaService formulaService;

    @PostMapping("/calculate")
    public FormulaResultDTO calculate(@RequestBody FormulaDTO formulaDTO){
      return  formulaService.calculated(formulaDTO);
    }
}
