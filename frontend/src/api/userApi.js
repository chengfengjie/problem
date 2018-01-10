/**
 * Created by chengfj on 2017/10/9.
 */

const api = require('./common.js')
const relativeUrl = '/api/v1/user'

module.exports = {
    login(data) {
        return api.post(`${relativeUrl}/login`, {}, data)
    },
    createUser(data) {
        return api.post(`${relativeUrl}/create`, {}, data)
    },
    queryUserList(data) {
        return api.post(`${relativeUrl}/queryList`, {}, data)
    },
    disable(userID) {
        return api.get(`${relativeUrl}/disable?userID=${userID}`)
    },
    enable(userID) {
        return api.get(`${relativeUrl}/enable?userID=${userID}`)
    },
    getAllUserList() {
        return api.get(`${relativeUrl}/allUserList`)
    },
    logout() {
        return api.post(`${relativeUrl}/logout`)
    },
    updatePassword(data) {
        return api.post(`${relativeUrl}/updatePassword`, {}, data)
    },
    userInfo() {
        return api.get(`${relativeUrl}/userInfo`)
    },
    updateInfo(data) {
        return api.post(`${relativeUrl}/updateInfo`, {}, data)
    },
}
