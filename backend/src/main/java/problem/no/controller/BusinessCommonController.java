package problem.no.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import problem.no.config.GlobalConfig;
import problem.no.dto.GlobalConfgDto;
import problem.no.dto.ResultDto;

/**
 * @author chengfj
 */
@RestController
@RequestMapping("/api/v1/businessCommon")
public class BusinessCommonController {

    @Autowired
    private GlobalConfig globalConfig;

    @GetMapping("/getConfig")
    public ResultDto getConfig() {
        GlobalConfgDto globalConfgDto = new GlobalConfgDto();
        globalConfgDto.setOpenRegister(globalConfig.openRegister);
        return ResultDto.data(globalConfgDto);
    }
}
