package az.example.mv.banking.controllers;

import az.example.mv.banking.exceptions.ServiceException;
import az.example.mv.banking.models.api.Res;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Log4j2
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Catches our custom exception
     *
     * @param se custom banking app exception
     * @return msg object which will be converted to json.
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Res> handleServiceException(ServiceException se) {
        log.error("Got an error", se);
        return new ResponseEntity<>(new Res(se.getMessage()), se.getHttpStatus());
    }

    /**
     * General exception handler for app
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Res> handleException(HttpServletRequest request, Exception e) {
        log.error("Got an unexpected exception", e);
        return new ResponseEntity<>(new Res("Unexpected exception! Incoming request url: " + request.getRequestURL()), INTERNAL_SERVER_ERROR);
    }
}
