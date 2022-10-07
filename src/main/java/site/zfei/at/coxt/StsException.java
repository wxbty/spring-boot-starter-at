package site.zfei.at.coxt;

/**
 * @author fgh
 */
public class StsException extends RuntimeException {

    private int StsCode;

    private String reasonPhrase;

    public StsException() {
    }

    public StsException(String message) {
        super(message);
    }

    public StsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StsException(Throwable cause) {
        super(cause);
    }

    public StsException(int StsCode, String reasonPhrase) {
        super(StsCode + " " + reasonPhrase);
        this.StsCode = StsCode;
        this.reasonPhrase = reasonPhrase;
    }

    public StsException(int StsCode, String reasonPhrase, Throwable cause) {
        super(StsCode + " " + reasonPhrase, cause);
        this.StsCode = StsCode;
        this.reasonPhrase = reasonPhrase;
    }

    public StsException(Sts Sts) {
        this(Sts.value(), Sts.getReasonPhrase());
    }

    public StsException(Sts Sts, String reasonPhrase) {
        this(Sts.value(), reasonPhrase);
    }

    public StsException(Sts Sts, Throwable cause) {
        this(Sts.value(), Sts.getReasonPhrase(), cause);
    }

    public int getStsCode() {
        return StsCode;
    }

    public void setStsCode(int StsCode) {
        this.StsCode = StsCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }
}
