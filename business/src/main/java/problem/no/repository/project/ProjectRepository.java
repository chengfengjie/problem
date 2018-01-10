package problem.no.repository.project;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import problem.no.dto.project.ProjectGeneralDto;
import problem.no.dto.project.ProjectNoticeDto;
import problem.no.model.project.ProjectModel;

import java.util.List;

/**
 * @author chengfj
 */
@Mapper
@Repository
public interface ProjectRepository {
    /**
     * 写入项目表
     * @param model 数据model
     */
    void insertProject(ProjectModel model);

    /**
     * 检测项目名称是否存在
     * @param projectName 项目名称
     * @return 是否存在
     */
    boolean projectNameIsExist(String projectName);

    /**
     * 查询所有项目列表
     * @return 项目列表
     */
    List<ProjectModel> queryProjectList(Integer userID);

    /**
     * 查询单个项目信息
     * @param projectID 项目ID
     * @return 项目信息
     */
    ProjectModel queryProjectById(Integer projectID);

    /**
     * 更新项目信息
     * @param model 项目模型
     */
    void updateProject(ProjectModel model);

    /**
     * 查询项目概况
     * @param projectID 项目ID
     * @return 项目概况
     */
    ProjectGeneralDto queryProjectGeneral(Integer projectID);

    /**
     * 查询用户的通知显示个数
     * @param projectID 项目ID
     * @param userID 用户ID
     * @return
     */
    ProjectNoticeDto selectNoticeInfo(@Param("projectID") Integer projectID,
                                      @Param("userID") Integer userID);

}
