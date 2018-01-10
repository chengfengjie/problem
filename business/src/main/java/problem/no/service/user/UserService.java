package problem.no.service.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.dto.user.CreateUserDto;
import problem.no.dto.user.CurrentUserDto;
import problem.no.dto.user.LoginUserDto;
import problem.no.dto.user.UpdatePasswordDto;
import problem.no.enums.user.UserRole;
import problem.no.exception.PNException;
import problem.no.model.PageResultModel;
import problem.no.model.user.UserModel;
import problem.no.model.user.query.UserQueryFilter;
import problem.no.repository.user.UserRepository;
import problem.no.util.BusinessUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author chengfj
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    static private final String DEFAULT_USER_AVATAR = "http://img4.imgtn.bdimg.com/it/u=3150822554,1701663780&fm=27&gp=0.jpg";

    public CurrentUserDto login(LoginUserDto loginUserDto) {
        UserModel model = userRepository.queryUserByEmail(loginUserDto.getEmail());
        if (model == null) {
            throw new PNException("邮箱不存在");
        } else {
            String pwd =  BusinessUtil.encodePassword(loginUserDto.getPassword(), model.getPasswordSalt());
            if (pwd.equals(model.getPassword())) {
                String currentDate = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
                CurrentUserDto currentUserDto = new CurrentUserDto();
                currentUserDto.setUserName(model.getUserName());
                currentUserDto.setUserID(model.getUserID());
                currentUserDto.setRole(model.getRole());
                currentUserDto.setStatus(model.getStatus());
                currentUserDto.setAuthUserID(DigestUtils.sha1Hex(model.getUserID().toString() + currentDate));
                return currentUserDto;
            } else {
                throw new PNException("密码错误");
            }
        }
    }

    public void createUser(CreateUserDto createUserDto) {
        if (userEmailIsExist(createUserDto.getEmail())) {
            throw new PNException("邮箱已经被注册了");
        }
        UserModel model = createUserModel(createUserDto);
        userRepository.insertUser(model);
    }

    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        UserModel model = userRepository.queryUserById(updatePasswordDto.getUserID());
        if (model != null) {
            String pwd = BusinessUtil.encodePassword(updatePasswordDto.getOriginalPassword(), model.getPasswordSalt());
            String updatePassword = BusinessUtil.encodePassword(updatePasswordDto.getPassword(), model.getPasswordSalt());
            if (pwd.equals(model.getPassword())) {
                UserModel updateModel = new UserModel();
                updateModel.setUserID(updatePasswordDto.getUserID());
                updateModel.setPassword(updatePassword);
                userRepository.updateUserInfo(updateModel);
            } else {
                throw new PNException("原始密码错误");
            }
        } else {
            throw new PNException("用户不存在");
        }
    }

    public void updateUserInfo(UserModel model) {
        if (model.getUserID() == null) {
            throw new PNException("用户ID不能为空");
        }
        userRepository.updateUserInfo(model);
    }

    public PageResultModel queryUserList(UserQueryFilter queryFilter) {
        RowBounds rowBounds = new RowBounds(queryFilter.getPageNum(), queryFilter.getPageSize());
        return new PageResultModel<>(userRepository.queryUserList(queryFilter, rowBounds));
    }

    public List<UserModel> queryAllUserList() {
        return userRepository.queryAllUserList();
    }

    public void enableUser(Integer userID) {
        UserModel model = new UserModel();
        model.setStatus(1);
        model.setUserID(userID);
        userRepository.updateUserInfo(model);
    }

    public void disableUser(Integer userID) {
        UserModel model = new UserModel();
        model.setStatus(2);
        model.setUserID(userID);
        userRepository.updateUserInfo(model);
    }

    private UserModel createUserModel(CreateUserDto createUserDto) {
        Date date = new Date();
        String salt = BusinessUtil.createPasswordSalt(date);
        String pwd = BusinessUtil.encodePassword(createUserDto.getEmail(), salt);

        UserModel model = new UserModel(pwd, salt, date);
        model.setEmail(createUserDto.getEmail());
        model.setCellphone(createUserDto.getCellphone());
        model.setPasswordSalt(salt);
        model.setUserName(createUserDto.getUserName());
        model.setStatus(1);
        model.setRole(UserRole.NORMAL_USER.getCode());
        model.setDescription(createUserDto.getDescription());

        model.setAvatar(createUserDto.getAvatar());
        if (model.getAvatar() == null) {
            model.setAvatar(DEFAULT_USER_AVATAR);
        }
        return model;
    }

    private boolean userEmailIsExist(String email) {
        UserModel model = userRepository.queryUserByEmail(email);
        if (model == null) {
            return false;
        } else {
            return true;
        }
    }

    public UserModel queryUserByID(Integer userID) {
        return userRepository.queryUserById(userID);
    }
}
