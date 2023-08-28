package site.zfei.at.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fgh
 */
public class Beans {

    public static <T, K> List<T> copys(List<K> sources, Class<T> clazz) {
        assert sources != null;
        return sources.stream().map(source -> copy(source, clazz)).collect(Collectors.toList());
    }

    public static <T> T copy(Object source, Class<T> clazz) {
        if (source != null) {
            try {
                return BeanUtil.toBean(source, clazz, CopyOptions.create().setIgnoreNullValue(true));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
