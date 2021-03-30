package hello.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Result {

    private int code;

    private String message;

    private Object data;

    private Object meta;

    public Result successRes(Object data) {
        this.code = StatusType.SUCCESS.getCode();
        this.message = StatusType.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public Result successRes(Object data, Object meta) {
        this.code = StatusType.SUCCESS.getCode();
        this.message = StatusType.SUCCESS.getMessage();
        this.data = data;
        this.meta = meta;
        return this;
    }

    public Result failRes(Object data) {
        this.code = StatusType.FAIL.getCode();
        this.message = StatusType.FAIL.getMessage();
        this.data = data;
        return this;
    }

    public void setStatus(StatusType statusType) {
        this.code = statusType.getCode();
        this.message = statusType.getMessage();
    }

    public void setStatus(StatusType statusType, String message) {
        this.code = statusType.getCode();
        this.message = message;
    }
}
