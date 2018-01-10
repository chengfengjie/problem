/**
 * Created by chengfj on 2017/10/14.
 */
const api = require('./common.js')
const relativeUrl = '/api/v1/project/member'

module.exports = {
    addMember(data) {
        return api.post(`${relativeUrl}/add`, {}, data)
    },
    queryProjectMemberList(projectID) {
        return api.get(`${relativeUrl}/queryProjectMemberList?projectID=${projectID}`)
    },
    deleteMember(projectID, userID) {
        return api.get(`${relativeUrl}/delete?projectID=${projectID}&userID=${userID}`)
    },
}
