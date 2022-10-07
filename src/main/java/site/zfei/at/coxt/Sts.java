package site.zfei.at.coxt;

/**
 * Enumeration of generic status codes base on HTTP status codes.
 *
 * @author fgh
 */
public enum Sts {


    /**
     * {@code 200 OK}.
     */
    OK(200, "OK"),

    // --- 4xx Client Error ---

    /**
     * {@code 400 Bad Request}.
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * {@code 401 Unauthorized}.
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * {@code 402 Payment Required}.
     */
    PAYMENT_REQUIRED(402, "Payment Required"),
    /**
     * {@code 403 Forbidden}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.3">HTTP/1.1: Semantics and Content, section 6.5.3</a>
     */
    FORBIDDEN(403, "Forbidden"),
    /**
     * {@code 404 Not Found}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1: Semantics and Content, section 6.5.4</a>
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * {@code 405 Method Not Allowed}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.5">HTTP/1.1: Semantics and Content, section 6.5.5</a>
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    /**
     * {@code 406 Not Acceptable}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.6">HTTP/1.1: Semantics and Content, section 6.5.6</a>
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    /**
     * {@code 407 Proxy Authentication Required}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.2">HTTP/1.1: Authentication, section 3.2</a>
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    /**
     * {@code 408 Request Timeout}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.7">HTTP/1.1: Semantics and Content, section 6.5.7</a>
     */
    REQUEST_TIMEOUT(408, "Request Timeout"),
    /**
     * {@code 409 Conflict}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.8">HTTP/1.1: Semantics and Content, section 6.5.8</a>
     */
    CONFLICT(409, "Conflict"),
    /**
     * {@code 410 Gone}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.9">HTTP/1.1: Semantics and Content, section 6.5.9</a>
     */
    GONE(410, "Gone"),

    /**
     * {@code 415 Unsupported Media Type}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.13">HTTP/1.1: Semantics and Content, section 6.5.13</a>
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    // --- 5xx Server Error ---

    /**
     * {@code 500 Internal Server Error}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.1">HTTP/1.1: Semantics and Content, section 6.6.1</a>
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * {@code 501 Not Implemented}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.2">HTTP/1.1: Semantics and Content, section 6.6.2</a>
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),
    /**
     * {@code 502 Bad Gateway}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.3">HTTP/1.1: Semantics and Content, section 6.6.3</a>
     */
    BAD_GATEWAY(502, "Bad Gateway"),
    /**
     * {@code 503 Service Unavailable}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.4">HTTP/1.1: Semantics and Content, section 6.6.4</a>
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    /**
     * {@code 504 Gateway Timeout}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.5">HTTP/1.1: Semantics and Content, section 6.6.5</a>
     */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),

    /**
     * {@code 900 Unknown}.
     */
    UNKNOWN(900, "Unknown"), // 未知错误
    /**
     * {@code 901 Unauthenticated}.
     */
    UNAUTHENTICATED(901, "Unauthenticated"), // 未经授权
    /**
     * {@code 902 Permission Denied}.
     */
    PERMISSION_DENIED(902, "Permission Denied"), // 没有许可
    /**
     * {@code 903 Invalid Argument}.
     */
    INVALID_ARGUMENT(903, "Invalid Argument"), // 非法参数
    /**
     * {@code 904 Database Exception}.
     */
    DATABASE_EXCEPTION(904, "Database Exception"), //数据库异常
    /**
     * {@code 905 Cancelled}.
     */
    CANCELLED(905, "Cancelled"), // 取消
    /**
     * {@code 906 Frequently}.
     */
    FREQUENTLY(906, "Frequently"), // 频繁
    /**
     * {@code 907 Not Exist}.
     */
    NOT_EXIST(907, "Not Exist"), // 不存在
    /**
     * {@code 908 Exist}.
     */
    EXIST(908, "Exist"), // 存在
    /**
     * {@code 909 Not Ready}.
     */
    NOT_READY(909, "Not Ready"), // 未就绪
    /**
     * {@code 910 Ready}.
     */
    READY(910, "Ready"), // 就绪
    /**
     * {@code 911 Not Expired}.
     */
    NOT_EXPIRED(911, "Not Expired"), // 没有过期
    /**
     * {@code 912 Expired}.
     */
    EXPIRED(912, "Expired"), // 过期
    /**
     * {@code 913 Not Done}.
     */
    NOT_DONE(913, "Not Done"), // 未完成
    /**
     * {@code 914 Done}.
     */
    DONE(914, "Done"), // 已完成
    /**
     * {@code 915 Not Enough}.
     */
    NOT_ENOUGH(915, "Not Enough"), // 不足
    /**
     * {@code 916 Enough}.
     */
    ENOUGH(916, "Enough"), // 足够
    /**
     * {@code 917 Not Excess}.
     */
    NOT_EXCESS(917, "Not Excess"), // 没有过量
    /**
     * {@code 918 Excess}.
     */
    EXCESS(918, "Excess"), // 过量
    /**
     * {@code 919 Not Duplicate}.
     */
    NOT_DUPLICATE(919, "Not Duplicate"), // 不重复
    /**
     * {@code 920 Duplicate}.
     */
    DUPLICATE(920, "重复操作"), // 重复
    /**
     * {@code 921 Not Busy}.
     */
    NOT_BUSY(921, "Not Busy"), // 不忙
    /**
     * {@code 922 Busy}.
     */
    BUSY(922, "Busy"), // 忙
    /**
     * {@code 923 Unavailable}.
     */
    UNAVAILABLE(923, "Unavailable"), // 不可用
    /**
     * {@code 924 Unknown}.
     */
    AVAILABLE(924, "Available"), // 可用
    /**
     * {@code 925 Unimplemented}.
     */
    UNIMPLEMENTED(925, "Unimplemented"), // 未实现
    /**
     * {@code 925 Implemented}.
     */
    IMPLEMENTED(926, "Implemented"), // 实现

    /**
     * {@code 925 Implemented}.
     */
    FILE_NOT_FOUND(928, "文件不存在"),
    // 不允许
    NOT_ALLOWED(930, "Not Allowed");


    private final int value;

    private final String reasonPhrase;

    Sts(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }



    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
