package problem.no.repository.user;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import problem.no.model.user.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import problem.no.model.user.query.UserQueryFilter;

import java.util.List;

/**
 * @author chengfj
 */
@Repository
@Mapper
public interface UserRepository {

    /**
     * 写入用户信息表
     * @param model 用户信息Model
     */
    void insertUser(UserModel model);

    /**
     * 通过邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    UserModel queryUserByEmail(@Param("email") String email);

    /**
     * 通过用户ID查询用户信息
     * @param userID 用户ID
     * @return 用户信息
     */
    UserModel queryUserById(Integer userID);

    /**
     * 查询用户列表
     * @param queryFilter 筛选条件
     * @param rowBounds 分页
     * @return 列表
     */
    List<UserModel> queryUserList(UserQueryFilter queryFilter, RowBounds rowBounds);

    /**
     * 更新用户信息
     * @param userModel 用户模型
     */
    void updateUserInfo(UserModel userModel);

    /**
     * 查询所有用户列表
     * @return 用户列表
     */
    List<UserModel> queryAllUserList();
}
