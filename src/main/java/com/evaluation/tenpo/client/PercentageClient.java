package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;

public interface PercentageClient {
    public PercentageDTO getPercentage() throws RemoteServiceNotAvailableException;
}
