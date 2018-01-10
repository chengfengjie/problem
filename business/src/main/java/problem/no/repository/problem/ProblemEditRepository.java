package problem.no.repository.problem;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import problem.no.dto.problem.ProblemEditResultDto;
import problem.no.model.problem.ProblemEditImageModel;
import problem.no.model.problem.ProblemEditModel;

import java.util.List;

/**
 * Created by chengfj on 2017/10/23.
 * @author chengfj
 */
@Repository
@Mapper
public interface ProblemEditRepository {

    /**
     * 写入问题编辑信息
     * @param model 问题编辑信息model
     */
    void insertProblemEditInfo(ProblemEditModel model);

    /**
     * 写入问题编辑信息图片
     * @param list 图片信息
     */
    void insertProblemEditImage(List<ProblemEditImageModel> list);

    /**
     * 查询问题编辑信息列表
     * @param problemID 问题ID
     * @return 列表
     */
    List<ProblemEditResultDto> queryProblemEditList(Integer problemID);

}
