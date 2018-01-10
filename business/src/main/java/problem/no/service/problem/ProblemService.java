package problem.no.service.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.config.GlobalConfig;
import problem.no.dto.problem.ProblemFormDto;
import problem.no.dto.problem.ProblemDetailDto;
import problem.no.dto.problem.ProblemEditResultDto;
import problem.no.dto.problem.ProblemListDto;
import problem.no.exception.PNException;
import problem.no.model.PageResultModel;
import problem.no.model.problem.ProblemEditImageModel;
import problem.no.model.problem.ProblemEditModel;
import problem.no.model.problem.ProblemImageModel;
import problem.no.model.problem.ProblemTagModel;
import problem.no.model.problem.query.ProblemQueryFilter;
import problem.no.repository.problem.ProblemEditRepository;
import problem.no.repository.problem.ProblemRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chengfengjie
 */
@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ProblemEditRepository problemEditRepository;
    @Autowired
    private GlobalConfig globalConfig;

    public void createProblem(ProblemFormDto problemFormDto) {
        if (problemFormDto.getProblemType() == null) {
            problemFormDto.setProblemType(1);
        }
        if (problemFormDto.getPriority() == null) {
            problemFormDto.setPriority(3);
        }
        if (problemFormDto.getStatus() == null) {
            problemFormDto.setStatus(1);
        }
        problemRepository.insertProblem(problemFormDto);
        // 写入问题图片
        if (problemFormDto.getImageList() != null && !problemFormDto.getImageList().isEmpty()) {
            List<ProblemImageModel> list = new ArrayList<>();
            for (String imageName : problemFormDto.getImageList()) {
                ProblemImageModel model = new ProblemImageModel();
                model.setProblemID(problemFormDto.getProblemID());
                model.setImageName(imageName);
                model.setInUser(problemFormDto.getInUser());
                model.setLastEditUser(problemFormDto.getInUser());
                list.add(model);
            }
            problemRepository.insertProblemImage(list);
        }
        {
            // 写入问题编辑信息
            ProblemEditModel editModel = new ProblemEditModel();
            editModel.setEditType(0);
            editModel.setProblemID(problemFormDto.getProblemID());
            editModel.setSolveUserID(problemFormDto.getSolveUserID());
            editModel.setInUser(problemFormDto.getInUser());
            editModel.setLastEditUser(problemFormDto.getInUser());
            problemEditRepository.insertProblemEditInfo(editModel);
        }
        {
            // 写入标签
            List<ProblemTagModel> list = new ArrayList<>();
            for (Integer tagID : problemFormDto.getTagList()) {
                ProblemTagModel model = new ProblemTagModel();
                model.setProblemID(problemFormDto.getProblemID());
                model.setTagID(tagID);
                model.setInDate(new Date());
                model.setInUser(problemFormDto.getInUser());
                model.setLastEditUser(problemFormDto.getInUser());
                model.setLastEditDate(new Date());
                list.add(model);
            }
            if (!list.isEmpty()) {
                problemRepository.insertProblemTag(list);
            }
        }
    }

    public PageResultModel<ProblemListDto> queryProblemList(ProblemQueryFilter filter) {
        filter.makeCustomPage();
        Long total = problemRepository.queryProblemTotal(filter);
        if (total != null && total > 0) {
            List<ProblemListDto> list = problemRepository.queryProblemList(filter);
            return new PageResultModel<>(list, filter, total);
        } else {
            return new PageResultModel<>(null, filter, 0L);
        }
    }

    public ProblemDetailDto queryProblemById(Integer problemID, Integer currentUserID) {
        ProblemDetailDto detailDto = problemRepository.queryProblemById(problemID);

        List<ProblemEditResultDto> list = problemEditRepository.queryProblemEditList(problemID);
        list.forEach((ProblemEditResultDto dto) -> {
            if (dto.getImageList() != null) {
                dto.getImageList().forEach((ProblemEditImageModel imageModel) -> {
                    imageModel.setImageName(globalConfig.aliyunOssDisplayUrl + imageModel.getImageName());
                });
            }
        });
        detailDto.setEditInfoList(list);

        List<ProblemImageModel> imageList = problemRepository.queryProblemImageList(problemID);
        imageList.forEach((ProblemImageModel model) -> {
            if (model.getImageName() != null) {
                model.setImageName(globalConfig.aliyunOssDisplayUrl + model.getImageName());
            }
        });
        detailDto.setImageList(imageList);

        {
            Integer status = detailDto.getStatus();
            /**
             * 所有问题无论什么状态都可以评论
             */
            detailDto.setHasCommentPerssion(true);

            /**
             * 判断指派功能，打开，待审核，拒绝的问题可以指派给某人
             */
            if (status.equals(1) || status.equals(3) || status > 10) {
                detailDto.setHasAssignPerssion(true);
            } else {
                detailDto.setHasAssignPerssion(false);
            }

            /**
             * 修复权限, 问题的指派者可以修复状态未1的问题
             */
            if (status.equals(1) && detailDto.getSolveUserID() != null && currentUserID.equals(detailDto.getSolveUserID())) {
                detailDto.setHasRepairPerssion(true);
            } else {
                detailDto.setHasRepairPerssion(false);
            }

            /**
             * 修复审核权限，当前问题的处理者对待审核的问题具有审核权限, 拒绝的问题问题处理者具有不通过的权限
             */
            if (detailDto.getSolveUserID() != null && currentUserID.equals(detailDto.getSolveUserID())) {
                if (status.equals(3)) {
                    detailDto.setHasAuditPassPerssion(true);
                    detailDto.setHasAuditNotPassPerssion(true);
                } else if (status > 10){
                    detailDto.setHasAuditPassPerssion(false);
                    detailDto.setHasAuditNotPassPerssion(true);
                } else {
                    detailDto.setHasAuditNotPassPerssion(false);
                    detailDto.setHasAuditPassPerssion(false);
                }
            } else {
                detailDto.setHasAuditNotPassPerssion(false);
                detailDto.setHasAuditPassPerssion(false);
            }

            /**
             * 关闭，解决，延期的问题可以再次打开
             */
            if (status.equals(0) || status.equals(4) || status.equals(2)) {
                detailDto.setHasOpenProblemPerssion(true);
            } else {
                detailDto.setHasOpenProblemPerssion(false);
            }
            /**
             * 拒绝权限
             */
            if (status.equals(1) && currentUserID.equals(detailDto.getSolveUserID())) {
                detailDto.setHasRejectPerssion(true);
            } else {
                detailDto.setHasRejectPerssion(false);
            }
            /**
             * 关闭问题
             */
            if (!status.equals(0) && !status.equals(4)) {
                detailDto.setHasCloseProblemPerssion(true);
            } else {
                detailDto.setHasCloseProblemPerssion(false);
            }

            /**
             * 延期问题
             */
            if (status.equals(1)) {
                detailDto.setHasDeferProblemPerssion(true);
            } else {
                detailDto.setHasDeferProblemPerssion(false);
            }

        }
        return detailDto;
    }

    public void updateProblem(ProblemFormDto problemFormDto) {
        if (problemFormDto.getProblemID() == null) {
            throw new PNException("问题ID不能为空");
        }
        if (problemFormDto.getTagList() != null) {
            problemRepository.deleteProblemTag(problemFormDto.getProblemID());
            List<ProblemTagModel> list = new ArrayList<>();
            for (Integer tagID : problemFormDto.getTagList()) {
                ProblemTagModel model = new ProblemTagModel();
                model.setTagID(tagID);
                model.setProblemID(problemFormDto.getProblemID());
                model.setInUser(problemFormDto.getInUser());
                model.setLastEditUser(problemFormDto.getInUser());
                model.setInDate(new Date());
                model.setLastEditDate(new Date());
                list.add(model);
            }
            if (!list.isEmpty()) {
                problemRepository.insertProblemTag(list);
            }
        }
    }
}
