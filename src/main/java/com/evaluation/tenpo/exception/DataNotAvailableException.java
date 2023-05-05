package com.evaluation.tenpo.exception;

public class DataNotAvailableException extends Exception{
    
    public DataNotAvailableException(String errorMessage){
        super(errorMessage);
    }
    public DataNotAvailableException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
