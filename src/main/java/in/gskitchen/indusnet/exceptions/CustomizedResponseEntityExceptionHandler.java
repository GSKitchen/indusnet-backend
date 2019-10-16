package in.gskitchen.indusnet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExeceptions(Exception ex, WebRequest request){
        ExepceptionResponse exepceptionResponse = new ExepceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exepceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExeceptions(UserNotFoundException ex, WebRequest request){
        ExepceptionResponse exepceptionResponse = new ExepceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exepceptionResponse, HttpStatus.NOT_FOUND);
    }
}
