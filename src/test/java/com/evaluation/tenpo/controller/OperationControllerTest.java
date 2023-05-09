package com.evaluation.tenpo.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.OperationResponseDTO;
import com.evaluation.tenpo.exception.DataNotAvailableException;
import com.evaluation.tenpo.service.impl.AuditRequestServiceImpl;
import com.evaluation.tenpo.service.impl.OperationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(OperationController.class)
@AutoConfigureMockMvc
class OperationControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OperationServiceImpl operationService;
  
  @MockBean
  private AuditRequestServiceImpl auditRequestService;

  @Test
  void calculateWhenAllOK() throws Exception{

    Mockito.when(operationService.calculated(Mockito.any())).thenReturn(OperationResponseDTO.builder().result(56).build());

    var request = OperationRequestDTO.builder().valueA(12).valueB(34).build();
    ResultActions resultActions = mockMvc.perform(post("/operation/calculate")
            .contentType(MediaType.APPLICATION_JSON).content(json(request)));

    resultActions.andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
  }

  @Test 
  void calculateWhenFailForValidation() throws Exception{

    Mockito.when(operationService.calculated(Mockito.any())).thenReturn(OperationResponseDTO.builder().result(56).build());

    var request = OperationRequestDTO.builder().valueA(12).build();
    ResultActions resultActions = mockMvc.perform(post("/operation/calculate")
            .contentType(MediaType.APPLICATION_JSON).content(json(request)));

    resultActions.andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty());
  }

  @Test
  void calculateWhenFailNotFoundPercentage() throws Exception{

    Mockito.when(operationService.calculated(Mockito.any())).thenThrow(new DataNotAvailableException("no found data"));

    var request = OperationRequestDTO.builder().valueA(12).build();
    ResultActions resultActions = mockMvc.perform(post("/operation/calculate")
            .contentType(MediaType.APPLICATION_JSON).content(json(request)));

    resultActions.andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty());
  }

  public String json(Object obj){
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
    }
    return null;
  }
  
}
