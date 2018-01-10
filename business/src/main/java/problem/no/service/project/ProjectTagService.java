package problem.no.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.exception.PNException;
import problem.no.model.project.ProjectTagModel;
import problem.no.repository.project.ProjectTagRepository;

import java.util.List;

@Service
public class ProjectTagService {

    @Autowired
    private ProjectTagRepository projectTagRepository;

    public void createProjectTag(ProjectTagModel model) {
        if (projectTagRepository.tagNameIsExist(model)) {
            throw new PNException("标签名称已经存在项目中");
        }
        projectTagRepository.insertProjectTag(model);
    }

    public List<ProjectTagModel> queryProjectTagList(Integer projectID) {
        return projectTagRepository.queryProjectTags(projectID);
    }

    public void deleteTag(Integer tagID, Integer editUserID) {
        ProjectTagModel model = new ProjectTagModel();
        model.setTagID(tagID);
        model.setIsActive(false);
        model.setLastEditUser(editUserID);
        projectTagRepository.updateTag(model);
    }
}
