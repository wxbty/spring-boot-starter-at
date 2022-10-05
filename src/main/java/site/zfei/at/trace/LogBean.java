package site.zfei.at.trace;

/**
 * @author fgh
 */
public interface LogBean {

    /**
     * add prop
     */
    LogBean addProp(String key, Object value);

    /**
     * del prop
     */
    Object delProp(String key);


    /**
     * get prop
     */
    Object getProp(String key);

    /**
     * print log
     */
    void log();
}
