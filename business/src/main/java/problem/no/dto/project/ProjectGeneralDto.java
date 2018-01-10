package problem.no.dto.project;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by chengfj on 2017/10/27.
 * @author chengfj
 */
@Getter
@Setter
public class ProjectGeneralDto {
    /**
     * 问题总数
     */
    private Integer total;
    /**
     * 打开的问题总数
     */
    private Integer open;
    /**
     * 待审核问题总数
     */
    private Integer waitAudit;
    /**
     * 拒绝问题总数
     */
    private Integer reject;
    /**
     * 已经解决问题数
     */
    private Integer solve;
    /**
     * 延期问题数
     */
    private Integer defer;
    /**
     * 关闭问题数
     */
    private Integer close;
    /**
     * 缺陷问题数
     */
    private Integer defect;
    /**
     * 任务数
     */
    private Integer job;
    /**
     * 需求数
     */
    private Integer need;
    /**
     * 项目状态
     */
    private String status;
    /**
     * 成员数
     */
    private Integer member;
    /**
     * 产品数
     */
    private Integer product;
    /**
     * 健康状态
     */
    private Integer health;
    /**
     * 打开的缺陷个数
     */
    private Integer openDefect;
    /**
     * 待审核的缺陷个数
     */
    private Integer auditDefect;
}
