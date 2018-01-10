/**
 * Created by chengfj on 2017/10/19.
 */

const api = require('./common.js')
const relativeUrl = '/api/v1/project/tag'

module.exports = {
    createTag(data) {
        return api.post(`${relativeUrl}/create`, {}, data)
    },
    queryProjectTagList(projectID) {
        return api.get(`${relativeUrl}/queryList?projectID=${projectID}`)
    },
    deleteTag(tagID) {
        return api.get(`${relativeUrl}/delete?tagID=${tagID}`)
    },
}
