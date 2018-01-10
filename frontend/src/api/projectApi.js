/**
 * Created by chengfj on 2017/10/12.
 */


const api = require('./common.js')
const relativeUrl = '/api/v1/project'

module.exports = {
    createProject(data) {
        return api.post(`${relativeUrl}/create`, {}, data)
    },
    queryAllProject() {
        return api.get(`${relativeUrl}/queryAllProject`)
    },
    queryProjectById(projectID) {
        return api.get(`${relativeUrl}/queryById?projectID=${projectID}`)
    },
    updateProject(data) {
        return api.post(`${relativeUrl}/update`, {}, data)
    },
    projectGeneral(projectID) {
        return api.get(`${relativeUrl}/general?projectID=${projectID}`)
    },
    projectNotice(projectID) {
        return api.get(`${relativeUrl}/notice?projectID=${projectID}`)
    },
}
