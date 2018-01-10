package problem.no.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import problem.no.config.GlobalConfig;
import problem.no.dto.UploadResultDto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chengfj
 */
@Service
public class UploadService {

    private static final int ONE_YEAR_SECOND = 3600 * 24 * 365;
    private static final SimpleDateFormat PATH_SDF = new SimpleDateFormat("yyyy/MM/dd");
    public static final String OSS_FILENAME_PREFIX = "OSS-";

    @Autowired
    private GlobalConfig globalConfig;

    public List<UploadResultDto> upload(String rootDir, MultipartFile[] files) throws IOException {
        OSSClient ossClient = new OSSClient(globalConfig.ossEndPoint, globalConfig.ossAccessKey ,globalConfig.ossAccessSecret);
        List<UploadResultDto> result = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String key = generateKey(rootDir, fileName);

            ObjectMetadata objectMetadata = new ObjectMetadata();

            Calendar expirationTime = Calendar.getInstance();
            expirationTime.add(Calendar.YEAR, 1);
            objectMetadata.setExpirationTime(expirationTime.getTime());
            objectMetadata.setCacheControl("max-age=" + ONE_YEAR_SECOND);

            PutObjectResult putObjectResult = ossClient.putObject("chengfj", key, file.getInputStream(), objectMetadata);

            UploadResultDto dto = new UploadResultDto();
            dto.setPath("/" + key);
            dto.setUrl(globalConfig.aliyunOssDisplayUrl + "/" + key);
            result.add(dto);
        }
        return result;
    }

    private String generateKey(String rootDir, String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        return rootDir + "/" + PATH_SDF.format(new Date()) + "/" + OSS_FILENAME_PREFIX + UUID.randomUUID().toString() + "." + extension;
    }

}
