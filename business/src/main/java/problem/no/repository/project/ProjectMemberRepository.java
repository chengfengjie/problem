package problem.no.repository.project;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import problem.no.dto.project.ProjectMemberDto;
import problem.no.model.project.ProjectMemberModel;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProjectMemberRepository {

    void insertProjectMember(ProjectMemberModel model);

    List<ProjectMemberDto> queryProjectMemberList(Integer projectID);

    Boolean memberIsExist(ProjectMemberModel model);

    /**
     * 删除项目成员
     * @param map lastEditUser, projectID, userID
     */
    void deleteProjectMember(Map map);
}
