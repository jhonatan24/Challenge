package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.GeneratorDTO;

import java.util.Optional;

public interface GeneratorClient {
    public Optional<GeneratorDTO> getPercentage();
}
