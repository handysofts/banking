package az.example.mv.banking.controllers;

import az.example.mv.banking.exceptions.ServiceException;
import az.example.mv.banking.models.api.Res;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RunWith(MockitoJUnitRunner.class)
public class ApiExceptionHandlerTest {
    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @Test
    public void handleServiceException() {
        //given
        String msg = "Test service exception";
        ServiceException exception = new ServiceException(msg, FORBIDDEN);

        //when
        ResponseEntity<Res> responseEntity = apiExceptionHandler.handleServiceException(exception);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(FORBIDDEN);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMsg()).isEqualTo(msg);
    }

    @Test
    public void handleException() {
        //given
        Exception exception = new RuntimeException("general test err");

        //when
        ResponseEntity<Res> responseEntity = apiExceptionHandler.handleException(new MockHttpServletRequest(), exception);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMsg()).contains("Unexpected exception");
    }
}
