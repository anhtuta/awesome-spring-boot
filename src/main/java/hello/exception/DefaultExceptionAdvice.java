package hello.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import hello.common.ErrorResponse;
import hello.common.StatusType;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Dùng AOP để xử lý exception: mọi exception của app sẽ đi qua class này, và việc
 * return response nếu có exception cũng do class này thực hiện, chứ ko phải controller nữa
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RestException.class})
    public ResponseEntity<Object> handleRestEx(RestException ex) {
        // ex.printStackTrace();
        log.error(ex.getMessage());

        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(getHttpStatus(ex.getCode())).body(error);
    }

    @ExceptionHandler({InvalidGrantException.class})
    public ResponseEntity<Object> handleInvalidGrantEx(InvalidGrantException ex) {
        // ex.printStackTrace();
        log.error(ex.getMessage());

        Map<String, String> error = new HashMap<>(2);
        error.put("error", "invalid_grant");
        error.put("error_description", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage());

        ErrorResponse error = new ErrorResponse(StatusType.FORBIDDEN.getCode(), StatusType.FORBIDDEN.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle500(Exception ex) {
        log.error(StatusType.INTERNAL_SERVER_ERROR.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(StatusType.INTERNAL_SERVER_ERROR.getCode(), StatusType.INTERNAL_SERVER_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
