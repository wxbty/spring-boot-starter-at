package site.zfei.at.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.zfei.at.coxt.IRequest;
import site.zfei.at.coxt.Sts;
import site.zfei.at.coxt.StsException;


/**
 * @author fgh
 */
@Data
public class OnlyIdRequest implements IRequest {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @Override
    public void checkParam() {
        if (id == null) {
            throw new StsException(Sts.INVALID_ARGUMENT);
        }
    }
}

