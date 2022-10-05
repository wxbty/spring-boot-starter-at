package site.zfei.at.trace;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.zfei.at.coxt.Context;
import site.zfei.at.util.Now;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author fgh
 */
public class WebLogBean implements LogBean {

    private static final Logger logger = LoggerFactory.getLogger(WebLogBean.class);

    public static final String KEY_LOG_BEAN = "X-WebLogBean";

    public static WebLogBean getLogBean() {
        return (WebLogBean) Context.current().getAttachments().computeIfAbsent(KEY_LOG_BEAN, k -> new WebLogBean());
    }

    public static void removeLogBean() {
        Context.current().removeAttachment(KEY_LOG_BEAN);
    }

    public static WebLogBean start() {
        WebLogBean logBean = getLogBean();
        logBean.setBeginTime(Now.millis());
        return logBean;
    }

    public static void stop() {
        getLogBean().print();
        removeLogBean();
    }

    private String trace = "";

    private String uid = "";

    private String path = "";

    private String clientIp = "";

    private int code = 200;

    private long beginTime = 0;

    private long spend = 0;

    private Object headers;

    private Object params;

    private Object payload;

    private Object result;

    private String error;

    private Map<String, Object> props = new LinkedHashMap<>();

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getSpend() {
        return spend;
    }

    public void setSpend(long spend) {
        this.spend = spend;
    }

    public Object getHeaders() {
        return headers;
    }

    public void setHeaders(Object headers) {
        this.headers = headers;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, Object> getProps() {
        return props;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
    }


    @Override
    public LogBean addProp(String key, Object value) {
        if (this.getProps() != null) {
            this.getProps().put(key, (value == null ? "null" : value));
        }
        return this;
    }

    @Override
    public Object delProp(String key) {
        if (this.getProps() != null) {
            return this.getProps().remove(key);
        }
        return null;
    }

    @Override
    public Object getProp(String key) {
        if (this.getProps() != null) {
            return this.getProps().get(key);
        }
        return null;
    }

    @Override
    public void log() {
        logger.info(JSONUtil.toJsonStr(this));
    }

    public void print() {
        this.print(Now.millis());
    }

    public void print(long now) {
        this.setSpend(now - this.getBeginTime());
        this.log();
    }
}
