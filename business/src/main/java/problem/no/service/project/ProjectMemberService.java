package problem.no.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.dto.project.ProjectMemberDto;
import problem.no.exception.PNException;
import problem.no.model.project.ProjectMemberModel;
import problem.no.repository.project.ProjectMemberRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class ProjectMemberService {

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    public void createProjectMember(ProjectMemberModel model) {
        if (model.getIsAdmin() == null) {
            model.setIsAdmin(false);
        }
        if (model.getIsOwner() == null) {
            model.setIsOwner(false);
        }
        if (projectMemberRepository.memberIsExist(model)) {
            throw new PNException("成员已经添加了");
        }
        projectMemberRepository.insertProjectMember(model);
    }

    public List<ProjectMemberDto> queryProjectMemberList(Integer projectID) {
        return projectMemberRepository.queryProjectMemberList(projectID);
    }

    public void deleteProjectMember(Integer projectID, Integer userID, Integer currentUserID) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("lastEditUser", currentUserID);
        params.put("projectID", projectID);
        params.put("userID", userID);
        projectMemberRepository.deleteProjectMember(params);
    }
}
