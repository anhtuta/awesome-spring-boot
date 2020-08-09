package hello.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hello.common.ErrorResponse;

@ControllerAdvice
public class DefaultExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ RestException.class })
    public ResponseEntity<Object> handleRestEx(RestException ex) {
        // ex.printStackTrace();
        System.out.println(ex.getMessage());

        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(getHttpStatus(ex.getCode())).body(error);
    }

    /**
     * return http code
     * 
     * @param code >= 100000 && <= 900000
     * @return
     */
    public int getHttpStatus(int code) {
        return code / 1000;
    }

}
