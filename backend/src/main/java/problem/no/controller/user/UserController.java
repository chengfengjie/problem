package problem.no.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problem.no.config.GlobalConfig;
import problem.no.dto.ResultDto;
import problem.no.dto.user.CreateUserDto;
import problem.no.dto.user.CurrentUserDto;
import problem.no.dto.user.LoginUserDto;
import problem.no.dto.user.UpdatePasswordDto;
import problem.no.exception.PNException;
import problem.no.interpretation.AdminPermission;
import problem.no.interpretation.LoginUserPermission;
import problem.no.manager.UserCacheManager;
import problem.no.model.user.UserModel;
import problem.no.model.user.query.UserQueryFilter;
import problem.no.service.user.UserService;
import problem.no.util.WebUtil;

import javax.validation.Valid;

/**
 * @author chengfj
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCacheManager userCacheManager;
    @Autowired
    private GlobalConfig globalConfig;

    @PostMapping("/login")
    public ResultDto login(@RequestBody LoginUserDto loginUserDto) {
        CurrentUserDto currentUserDto = userService.login(loginUserDto);
        WebUtil.setCookieAuthUserID(currentUserDto.getAuthUserID());
        userCacheManager.setCurrentUserDto(currentUserDto);
        return ResultDto.data(currentUserDto);
    }

    @LoginUserPermission
    @PostMapping("/logout")
    public ResultDto logout() {
        userCacheManager.removeCurrentUserDto(WebUtil.getCurrentUserDto().getAuthUserID());
        return ResultDto.msg("退出登录成功");
    }

    @LoginUserPermission
    @PostMapping("/updatePassword")
    public ResultDto updatePassword(@RequestBody @Valid UpdatePasswordDto updatePasswordDto) {
        updatePasswordDto.setUserID(WebUtil.getCurrentUserID());
        userService.updatePassword(updatePasswordDto);
        return ResultDto.msg("修改密码成功");
    }

    @LoginUserPermission
    @PostMapping("/updateInfo")
    public ResultDto updateInfo(@RequestBody UserModel userModel) {
        userModel.setUserID(WebUtil.getCurrentUserID());
        userModel.setStatus(null);
        userService.updateUserInfo(userModel);
        CurrentUserDto currentUserDto = WebUtil.getCurrentUserDto();
        currentUserDto.setUserName(userModel.getUserName());
        return ResultDto.data(currentUserDto);
    }

    @LoginUserPermission
    @GetMapping("/currentUser")
    public ResultDto currentUser() {
        return ResultDto.data(WebUtil.getCurrentUserDto());
    }

    @AdminPermission
    @PostMapping("/create")
    public ResultDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResultDto.msg("注册成功");
    }

    @PostMapping("/register")
    public ResultDto registerUser(@RequestBody @Valid CreateUserDto createUserDto) {
        if (globalConfig.openRegister) {
            userService.createUser(createUserDto);
            return ResultDto.msg("注册成功");
        } else {
            return ResultDto.warning(new PNException("系统不开放用户注册，请联系管理员"));
        }
    }

    @AdminPermission
    @PostMapping("/queryList")
    public ResultDto queryList(@RequestBody UserQueryFilter queryFilter) {
        return ResultDto.data(userService.queryUserList(queryFilter));
    }

    @AdminPermission
    @GetMapping("/allUserList")
    public ResultDto queryAllUser() {
        return ResultDto.data(userService.queryAllUserList());
    }

    @AdminPermission
    @GetMapping("/enable")
    public ResultDto enableUser(@RequestParam("userID") Integer userID) {
        userService.enableUser(userID);
        return ResultDto.msg("启用成功");
    }

    @AdminPermission
    @GetMapping("/disable")
    public ResultDto disableUser(@RequestParam("userID") Integer userID) {
        userService.disableUser(userID);
        return ResultDto.msg("禁用成功");
    }

    @LoginUserPermission
    @GetMapping("/userInfo")
    public ResultDto getUserInfo() {
        return ResultDto.data(userService.queryUserByID(WebUtil.getCurrentUserID()));
    }
}
