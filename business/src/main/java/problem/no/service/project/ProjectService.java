package problem.no.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.dto.project.ProjectGeneralDto;
import problem.no.dto.project.ProjectNoticeDto;
import problem.no.exception.PNException;
import problem.no.model.project.ProjectMemberModel;
import problem.no.model.project.ProjectModel;
import problem.no.repository.project.ProjectMemberRepository;
import problem.no.repository.project.ProjectProductRepository;
import problem.no.repository.project.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectMemberRepository projectMemberRepository;
    @Autowired
    private ProjectProductRepository projectProductRepository;

    public void createProject(ProjectModel model, String currentUserName) {
        if (projectRepository.projectNameIsExist(model.getProjectName())) {
            throw new PNException("项目名称已经存在");
        }
        projectRepository.insertProject(model);

        ProjectMemberModel memberModel = new ProjectMemberModel();
        memberModel.setIsOwner(true);
        memberModel.setIsAdmin(false);
        memberModel.setUserID(model.getInUser());
        memberModel.setProjectID(model.getProjectID());
        memberModel.setLastEditUser(model.getInUser());
        memberModel.setInUser(model.getInUser());
        memberModel.setDisplayName(currentUserName);
        projectMemberRepository.insertProjectMember(memberModel);

    }

    public void updateProject(ProjectModel model) {
        projectRepository.updateProject(model);
    }

    public List<ProjectModel> queryProjectList(Integer userID) {
        return projectRepository.queryProjectList(userID);
    }

    public ProjectModel queryProjectById(Integer projectID) {
        ProjectModel model = projectRepository.queryProjectById(projectID);
        model.setMemberData(projectMemberRepository.queryProjectMemberList(projectID));
        model.setProductData(projectProductRepository.queryProductList(projectID));
        return model;
    }

    public ProjectGeneralDto queryProjectGeneral(Integer projectID) {
        ProjectGeneralDto dto = projectRepository.queryProjectGeneral(projectID);
        Integer health = 100 - dto.getOpenDefect() * 2 - dto.getAuditDefect();
        if (health < 0) {
            health = 0;
        }
        dto.setHealth(health);
        return dto;
    }

    public ProjectNoticeDto selectNoticeInfo(Integer projectID, Integer userID) {
        return projectRepository.selectNoticeInfo(projectID, userID);
    }
}

