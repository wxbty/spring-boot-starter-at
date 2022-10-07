package site.zfei.at.coxt;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PageResult<T> {


    private int code = Sts.OK.value();

    private String msg = Sts.OK.getReasonPhrase();

    private PageData<T> data;


    public static <T> PageResult<T> build(List<T> list, long total) {
        PageData<T> pageVo = new PageData<>();
        pageVo.setList(list);
        pageVo.setTotal(total);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setData(pageVo);
        return pageResult;
    }

    public static <T> PageResult<T> empty() {
        PageData<T> pageVo = new PageData<>();
        pageVo.setList(Collections.emptyList());
        pageVo.setTotal(0);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setData(pageVo);
        return pageResult;
    }

    @Data
    private static class PageData<T> {
        private long total;

        private List<T> list;
    }

}
