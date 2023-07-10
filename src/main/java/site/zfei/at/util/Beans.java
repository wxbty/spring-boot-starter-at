package site.zfei.at.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fgh
 */
public class Beans {

    private static final Logger logger = LoggerFactory.getLogger(Beans.class);

    public static <T> T copy(Object source, Class<T> clazz) {
        return copy(source, clazz, (String[]) null);
    }

    public static <T, K> List<T> copys(List<K> sources, Class<T> clazz) {
        assert sources != null;
        return sources.stream().map(source -> copy(source, clazz)).collect(Collectors.toList());
    }

    public static <T> T copy(Object source, Class<T> clazz, String... ignoreProperties) {
        if (source != null) {
            try {
                T target = clazz.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, target, ignoreProperties);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
