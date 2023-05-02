package com.evaluation.tenpo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/formula")
public class FormulaController {
    @GetMapping
    public String getHola(){
        return "hola mundo";
    }
}
