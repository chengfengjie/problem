package problem.no.repository.problem;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import problem.no.dto.problem.ProblemFormDto;
import problem.no.dto.problem.ProblemDetailDto;
import problem.no.dto.problem.ProblemListDto;
import problem.no.model.problem.ProblemImageModel;
import problem.no.model.problem.ProblemModel;
import problem.no.model.problem.ProblemTagModel;
import problem.no.model.problem.query.ProblemQueryFilter;

import java.util.List;

/**
 * @author chengfj
 */
@Repository
@Mapper
public interface ProblemRepository {

    /**
     * 写入问题数据表
     * @param problemFormDto 问题数据
     */
    void insertProblem(ProblemFormDto problemFormDto);

    /**
     * 写入问题图片
     * @param list 问题图片
     */
    void insertProblemImage(List<ProblemImageModel> list);

    /**
     * 分页查询问题
     * @param queryFilter 查询条件
     * @return 问题列表
     */
    List<ProblemListDto> queryProblemList(ProblemQueryFilter queryFilter);

    /**
     * 查询问题条件的条数
     * @param queryFilter 查询条件
     * @return 总条数
     */
    Long queryProblemTotal(ProblemQueryFilter queryFilter);

    /**
     * 查询问题详细信息
     * @param problemID 问题ID
     * @return 问题详细信息DTO
     */
    ProblemDetailDto queryProblemById(Integer problemID);

    /**
     * 更新问题信息
     * @param model 问题信息model
     */
    void updateProblem(ProblemModel model);

    /**
     * 获取问题的图片
     * @param problemID 问题id
     * @return result
     */
    List<ProblemImageModel> queryProblemImageList(Integer problemID);

    /**
     * 写入问题标签
     * @param list 标签列表
     */
    void insertProblemTag(List<ProblemTagModel> list);

    /**
     * 删除问题标签
     * @param problemID 问题ID
     */
    void deleteProblemTag(Integer problemID);
}
