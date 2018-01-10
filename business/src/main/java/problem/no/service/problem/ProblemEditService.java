package problem.no.service.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.dto.problem.OpenProblemDto;
import problem.no.dto.problem.ProblemEditFormDto;
import problem.no.dto.problem.CommentProblemDto;
import problem.no.model.problem.ProblemEditImageModel;
import problem.no.model.problem.ProblemEditModel;
import problem.no.model.problem.ProblemModel;
import problem.no.repository.problem.ProblemEditRepository;
import problem.no.repository.problem.ProblemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengfj on 2017/10/23.
 * @author chengfj
 */
@Service
public class ProblemEditService {

    @Autowired
    private ProblemEditRepository problemEditRepository;
    @Autowired
    private ProblemRepository problemRepository;

    public void commentProblem(CommentProblemDto commentProblemDto) {
        ProblemEditModel model = new ProblemEditModel();

        model.setProblemID(commentProblemDto.getProblemID());
        model.setContent(commentProblemDto.getContent());
        model.setEditType(1);
        model.setInUser(commentProblemDto.getCreateUserID());
        model.setLastEditUser(commentProblemDto.getCreateUserID());
        problemEditRepository.insertProblemEditInfo(model);

        insertProblemEditImage(model, commentProblemDto.getImageList());
    }

    public void assignProblem(ProblemEditFormDto editFormDto) {

        {
            ProblemModel problemModel = new ProblemModel();
            problemModel.setProblemID(editFormDto.getProblemID());
            problemModel.setLastEditUser(editFormDto.getEditUserID());
            problemModel.setSolveUserID(editFormDto.getUserID());
            problemModel.setStatus(null);
            problemRepository.updateProblem(problemModel);
        }
        {
            ProblemEditModel model = new ProblemEditModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setEditType(2);
            model.setContent(editFormDto.getContent());
            model.setSolveUserID(editFormDto.getUserID());
            model.setInUser(editFormDto.getEditUserID());
            model.setLastEditUser(editFormDto.getEditUserID());
            problemEditRepository.insertProblemEditInfo(model);

            insertProblemEditImage(model, editFormDto.getImageList());
        }

    }

    public void repairProblem(ProblemEditFormDto editFormDto) {
        {
            ProblemModel model = new ProblemModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setStatus(3);
            model.setLastEditUser(editFormDto.getEditUserID());
            model.setSolveUserID(editFormDto.getUserID());
            problemRepository.updateProblem(model);
        }
        {
            ProblemEditModel model = new ProblemEditModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setEditType(3);
            model.setContent(editFormDto.getContent());
            model.setInUser(editFormDto.getEditUserID());
            model.setLastEditUser(editFormDto.getEditUserID());
            model.setSolveUserID(editFormDto.getUserID());
            problemEditRepository.insertProblemEditInfo(model);

            insertProblemEditImage(model, editFormDto.getImageList());
        }
    }

    public void auditProblem(ProblemEditFormDto editFormDto) {

        {
            ProblemModel model = new ProblemModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setLastEditUser(editFormDto.getEditUserID());

            if (editFormDto.getAuditPass()) {
                model.setStatus(4);
            } else {
                model.setStatus(1);
            }

            problemRepository.updateProblem(model);
        }
        {
            ProblemEditModel model = new ProblemEditModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setContent(editFormDto.getContent());
            model.setAuditVersionName(editFormDto.getAuditVersionName());
            model.setInUser(editFormDto.getEditUserID());
            model.setLastEditUser(editFormDto.getEditUserID());

            if (editFormDto.getAuditPass()) {
                model.setEditType(7);
            } else {
                model.setEditType(8);
            }

            problemEditRepository.insertProblemEditInfo(model);

            insertProblemEditImage(model, editFormDto.getImageList());
        }

    }

    public void openProblem(OpenProblemDto openProblemDto) {
        {
            ProblemModel model = new ProblemModel();
            model.setProblemID(openProblemDto.getProblemID());
            model.setSolveUserID(openProblemDto.getSolveUserID());
            model.setStatus(1);
            model.setPriority(openProblemDto.getPriority());
            model.setBeginDate(openProblemDto.getPlanBeginDate());
            model.setFinishDate(openProblemDto.getPlanFinishDate());
            model.setLastEditUser(openProblemDto.getEditUserID());
            problemRepository.updateProblem(model);
        }
        {
            ProblemEditModel model = new ProblemEditModel();
            model.setProblemID(openProblemDto.getProblemID());
            model.setEditType(9);
            model.setContent(openProblemDto.getContent());
            model.setAuditVersionName(openProblemDto.getVersionName());
            model.setSolveUserID(openProblemDto.getSolveUserID());
            model.setLastEditUser(openProblemDto.getEditUserID());
            model.setInUser(openProblemDto.getEditUserID());
            problemEditRepository.insertProblemEditInfo(model);
            insertProblemEditImage(model, openProblemDto.getImageList());
        }
    }

    public void rejectProblem(ProblemEditFormDto editFormDto) {
        {
            ProblemModel model = new ProblemModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setStatus(editFormDto.getRejectType() + 10);
            model.setSolveUserID(editFormDto.getUserID());
            model.setLastEditUser(editFormDto.getEditUserID());
            problemRepository.updateProblem(model);
        }
        {
            ProblemEditModel model = new ProblemEditModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setEditType(4);
            model.setContent(editFormDto.getContent());
            model.setSolveUserID(editFormDto.getUserID());
            model.setRejectType(editFormDto.getRejectType());
            model.setLastEditUser(editFormDto.getEditUserID());
            model.setInUser(editFormDto.getEditUserID());
            problemEditRepository.insertProblemEditInfo(model);
            insertProblemEditImage(model, editFormDto.getImageList());
        }
    }

    public void closeProblem(ProblemEditFormDto editFormDto) {
        {
            ProblemModel model = new ProblemModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setStatus(0);
            model.setLastEditUser(editFormDto.getEditUserID());
            problemRepository.updateProblem(model);
        }
        {
            ProblemEditModel model = new ProblemEditModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setEditType(5);
            model.setContent(editFormDto.getContent());
            model.setLastEditUser(editFormDto.getEditUserID());
            model.setInUser(editFormDto.getEditUserID());
            problemEditRepository.insertProblemEditInfo(model);
            insertProblemEditImage(model, editFormDto.getImageList());
        }
    }

    public void deferProblem(ProblemEditFormDto editFormDto) {
        {
            ProblemModel model = new ProblemModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setStatus(2);
            model.setLastEditUser(editFormDto.getEditUserID());
            problemRepository.updateProblem(model);
        }
        {
            ProblemEditModel model = new ProblemEditModel();
            model.setProblemID(editFormDto.getProblemID());
            model.setEditType(6);
            model.setContent(editFormDto.getContent());
            model.setInUser(editFormDto.getEditUserID());
            model.setLastEditUser(editFormDto.getEditUserID());
            problemEditRepository.insertProblemEditInfo(model);
            insertProblemEditImage(model, editFormDto.getImageList());
        }
    }

    private void insertProblemEditImage(ProblemEditModel model, List<String> imageList) {
        if (imageList != null && !imageList.isEmpty()) {
            List<ProblemEditImageModel> list = new ArrayList<>();
            for (String imageName : imageList) {
                ProblemEditImageModel imageModel = new ProblemEditImageModel();
                imageModel.setEditInfoID(model.getEditInfoID());
                imageModel.setImageName(imageName);
                imageModel.setInUser(model.getInUser());
                imageModel.setLastEditUser(model.getInUser());
                list.add(imageModel);
            }
            problemEditRepository.insertProblemEditImage(list);
        }
    }
}
