/**
 * Created by chengfj on 2017/10/19.
 */

const api = require('./common.js')
const relativeUrl = '/api/v1/problem'

module.exports = {
    /**
     * 创建问题API
     * @param data {
     *      projectID: 项目ID,
     *      productID: 产品ID,
     *      problemType: 问题类型 1 缺陷 2 任务 3 需求,
     *      moduleID: 模块ID,
     *      problemTitle: 问题标题,
     *      versionName: 版本名称,
     *      versionID: 版本管理,
     *      environment: 环境,
     *      description: 描述,
     *      solveUserID: 解决问题用户ID,
     *      priority: 优先级,
     *      beginDate: 计划开始时间,
     *      finishDate: 计划结束时间,
     *      planVersion: 计划版本,
     *      status: 状态 0 已关闭 1 打开 2 延期,
     *      imageList: [String] 图片名称数组,
     *      tagList: [Integer] 标签数组
     * }
     * @returns {*|AxiosPromise}
     */
    createProblem(data) {
        return api.post(`${relativeUrl}/create`, {}, data)
    },
    /**
     * 更新问题信息
     * @param data {
     *  tagList: [Integer]   //问题标签
     * }
     * @returns {AxiosPromise|*}
     */
    updateProblem(data) {
        return api.post(`${relativeUrl}/update`, {}, data)
    },
    /**
     *
     * @param data {
     *   "pageNum": 0,
     *   "pageSize": 0,
     *   "priority": 0,
     *   "problemType": 0,
     *   "productID": 0,
     *   "solveUserID": 0,
     *   "status": 0
     * }
     * @returns {*|AxiosPromise}
     */
    queryList(data) {
        return api.post(`${relativeUrl}/queryList`, {}, data)
    },
    /**
     * 通过problemID查询详细信息
     * @param problemID 问题ID
     * @returns {*} 包含图片列表和问题动态列表
     */
    queryProblemById(problemID) {
        return api.get(`${relativeUrl}/query?problemID=${problemID}`)
    },
    /**
     * 评论问题
     * @param data {
     *  content: 内容,
     *  problemID: 问题ID,
     *  imageList: [String] 图片列表
     * }
     * @returns {*|AxiosPromise}
     */
    commentProblem(data) {
        return api.post(`${relativeUrl}/comment`, {}, data)
    },
    /**
     * 指派问题给用户
     * @param data {
     *  userID: 用户id,
     *  problemID: 问题ID,
     *  content: 备注,
     *  imageList: 图片
     * }
     * @returns {AxiosPromise|*}
     */
    assignProblem(data) {
        return api.post(`${relativeUrl}/assign`, {}, data)
    },
    /**
     * 修复bug，指派给审核人员
     * @param data {
     *  userID: 用户id,
     *  problemID: 问题ID,
     *  content: 备注,
     *  imageList: 图片
     * }
     * @returns {*|AxiosPromise}
     */
    repairProblem(data) {
        return api.post(`${relativeUrl}/repair`, {}, data)
    },
    /**
     * 审核问题
     * @param data {
     *  problemID: 问题ID,
     *  content: 备注,
     *  imageList: 图片,
     *  auditPass: 是否通过
     *  auditVersionName: 审核版本
     * }
     * @returns {*|AxiosPromise}
     */
    auditProblem(data) {
        return api.post(`${relativeUrl}/audit`, {}, data)
    },
    /**
     * 重新打开问题
     * @param data {
     *    "content": "备注",
          "imageList": [
            "图片名称"
          ],
          "planBeginDate": "2017-10-25T03:24:21.123Z",
          "planFinishDate": "2017-10-25T03:24:21.123Z",
          "priority": 0,
          "problemID": 0,
          "solveUserID": 0,
          "versionName": "版本"
     * }
     * @returns {*|AxiosPromise}
     */
    openProblem(data) {
        return api.post(`${relativeUrl}/open`, {}, data)
    },
    /**
     * 拒绝问题
     * @param data {
     *  problemID : 问题ID,
     *  imageList : 图片,
     *  content : 内容,
     *  userID : 指派给,
     *  rejectType : 拒绝理由
     * }
     * @returns {*|AxiosPromise}
     */
    rejectProblem(data) {
        return api.post(`${relativeUrl}/reject`, {}, data)
    },
    /**
     * 关闭问题
     * @param data {
     *  problemID: 问题ID，
     *  content: 备注,
     *  imageList: [String]
     * }
     * @returns {*|AxiosPromise}
     */
    closeProblem(data) {
        return api.post(`${relativeUrl}/close`, {}, data)
    },
    /**
     * 延期问题
     * @param data {
     *  problemID: 问题ID，
     *  content: 备注,
     *  imageList: [String] 图片
     * }
     * @returns {*|AxiosPromise}
     */
    deferProblem(data) {
        return api.post(`${relativeUrl}/defer`, {}, data)
    },
}
