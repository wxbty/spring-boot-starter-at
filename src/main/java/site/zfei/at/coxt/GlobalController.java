package site.zfei.at.coxt;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zfei.at.file.AtFileConfigurationProperties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/at/global")
public class GlobalController {

    @GetMapping("/curl/open")
    public String curl(boolean open) {

        Global.printCurl = open;
        return "success set curl open =" + open;
    }

    @GetMapping("/result/open")
    public String result(boolean open) {

        Global.printResult = open;
        return "success set result open =" + open;
    }
}
