package hello.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 5790983488452699101L;

    private int code;
    private String message;

    public ErrorResponse(StatusType stt) {
        this.code = stt.getCode();
        this.message = stt.getMessage();
    }
}