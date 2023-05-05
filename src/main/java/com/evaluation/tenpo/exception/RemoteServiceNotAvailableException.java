package com.evaluation.tenpo.exception;

public class RemoteServiceNotAvailableException extends Exception{


    public RemoteServiceNotAvailableException(){
        super();
    }
    public RemoteServiceNotAvailableException(String errorMessage){
        super(errorMessage);
    }
    public RemoteServiceNotAvailableException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
