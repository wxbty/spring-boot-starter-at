package site.zfei.at.coxt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author fgh
 */
public class Context {

    private static final Logger logger = LoggerFactory.getLogger(Context.class);

    public static final Context ROOT = new Context(null);

    private static final ThreadLocal<Context> LOCAL = ThreadLocal.withInitial(() -> ROOT);

    public static Context current() {
        Context current = LOCAL.get();
        return (current == null ? ROOT : current);
    }

    public static Context getContext() {
        return LOCAL.get();
    }

    public static void removeContext() {
        LOCAL.remove();
    }

    private final Context parent;

    private final Map<String, Object> attachments;

    private Context(Context parent) {
        this.parent = parent;
        this.attachments = new LinkedHashMap<>();
    }

    public Context fork() {
        return new Context(this);
    }

    public void attach() {
        LOCAL.set(this);
    }


    public Context getParent() {
        return parent;
    }

    public Object getAttachment(String key) {
        return attachments.get(key);
    }

    public Context setAttachment(String key, Object value) {
        if (value == null) {
            attachments.remove(key);
        } else {
            attachments.put(key, value);
        }
        return this;
    }

    public Context removeAttachment(String key) {
        attachments.remove(key);
        return this;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }


    public Context setGlobalAttachment(String key, Object value) {
        if (this != ROOT) {
            if (this.getParent() == ROOT) {
                this.setAttachment(key, value);
            } else {
                this.getParent().setGlobalAttachment(key, value);
            }
        }
        return this;
    }


}
