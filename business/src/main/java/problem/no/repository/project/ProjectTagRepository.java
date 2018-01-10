package problem.no.repository.project;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import problem.no.model.project.ProjectTagModel;

import java.util.List;

@Mapper
@Repository
public interface ProjectTagRepository {

    void insertProjectTag(ProjectTagModel model);

    Boolean tagNameIsExist(ProjectTagModel model);

    List<ProjectTagModel> queryProjectTags(Integer projectID);

    void updateTag(ProjectTagModel model);
}
