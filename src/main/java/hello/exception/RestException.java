package hello.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import hello.common.StatusType;

@Getter
@Setter
@AllArgsConstructor
public class RestException extends RuntimeException {

    private static final long serialVersionUID = -8756242290875950860L;

    private int code;
    private String message;

    public RestException(StatusType stt) {
        this.code = stt.getCode();
        this.message = stt.getMessage();
    }

    public RestException(StatusType stt, String message) {
        this.code = stt.getCode();
        this.message = message;
    }
}