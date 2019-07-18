package az.example.mv.banking.exceptions;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {
    private HttpStatus httpStatus;

    public ServiceException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public ServiceException(String message, Throwable cause, HttpStatus httpStatus){
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
