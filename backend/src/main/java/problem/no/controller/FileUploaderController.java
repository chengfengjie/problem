package problem.no.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import problem.no.dto.ResultDto;
import problem.no.dto.UploadResultDto;
import problem.no.exception.PNException;
import problem.no.service.UploadService;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploaderController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public Callable<ResultDto> handleUpload(@RequestParam(value = "type", required = false, defaultValue = "problem") String type,
                                            @RequestParam("file") MultipartFile[] files) throws PNException, IOException {
        return () -> {
            List<UploadResultDto> list = uploadService.upload(type, files);
            if (list.size() == 1 ) {
                return ResultDto.data(list.get(0));
            } else {
                return ResultDto.data(list);
            }
        };
    }

}
