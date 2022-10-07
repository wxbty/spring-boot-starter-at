package site.zfei.at.coxt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fgh
 */
@Data
public class Result implements Serializable {

    private Object data;

    private int code;

    private String msg;

    public Result(Object data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg) {
        this(null, code, msg);
    }

    public Result(Object data, Sts status) {
        this(data, status.value(), status.getReasonPhrase());
    }

    public Result(StsException e) {
        this(e.getStsCode(), e.getReasonPhrase());
    }

    public static Result of(Object data) {
        return new Result(data, Sts.OK);
    }

    public static Result ofNull() {
        return new Result(null, Sts.OK);
    }
}
