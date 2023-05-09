package com.evaluation.tenpo.handler;

import com.evaluation.tenpo.exception.DataNotAvailableException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = DataNotAvailableException.class)
  protected Error dataNotFound(DataNotAvailableException ex) {
    return Error.builder()
        .timestamp(new Timestamp(System.currentTimeMillis()))
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(List.of(ex.getMessage()))
        .build();
  }
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(value = RequestNotPermitted.class)
    protected Error rateLimit(RequestNotPermitted ex) {
        return Error.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .message(List.of("exceeded request limit per minutes"))
                .build();
    }

    
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Error handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errorList = new ArrayList<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
                StringBuilder builder = new StringBuilder();
                String fieldName = ((FieldError) error).getField();
               String errorMessage = error.getDefaultMessage();
               builder.append(fieldName).append(" ").append(errorMessage);
               errorList.add(builder.toString());
            });
    
    return Error.builder()
        .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(new Timestamp(System.currentTimeMillis()))
        .message(errorList)
        .build();
  }
}
