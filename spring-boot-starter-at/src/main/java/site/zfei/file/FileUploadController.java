package site.zfei.file;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/at/file")
public class FileUploadController {


    @Resource
    private AtFileConfigurationProperties properties;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile uploadFile, HttpServletRequest request) {

        File folder = new File(System.getProperty("user.dir") + properties.getUploadPath());
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        // 对上传的文件重命名，避免文件重名
        String oldName = uploadFile.getOriginalFilename();
        if (oldName == null) {
            oldName = "default";
        }
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            // 文件保存
            uploadFile.transferTo(new File(folder, newName));

            return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + properties.getVisitPath() + "/" + newName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
