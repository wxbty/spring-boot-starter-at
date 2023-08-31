package site.zfei.at.coxt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.zfei.at.util.Now;

import java.io.Serializable;

@Data
public class PageAble implements Serializable {

    @ApiModelProperty(value = "开始时间")
    private Long minTime = 0L;

    @ApiModelProperty(value = "结束时间")
    private Long maxTime;

    private Integer orderType;
    private String orderCol;

    @ApiModelProperty("第几页")
    private Integer pageNo = 1;

    @ApiModelProperty("分页条数，默认20")
    private Integer pageSize = 20;

    public PageAble() {
        this.maxTime = Now.millis();
    }


}
