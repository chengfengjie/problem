/**
 * Created by chengfj on 2017/10/9.
 */

const api = require('./common.js')
const relativeUrl = '/api/v1/user'

module.exports = {
    /**
     * 登录
     * @param data
     * @returns {*|AxiosPromise}
     */
    login(data) {
        return api.post(`${relativeUrl}/login`, {}, data)
    },
    /**
     * 管理员创建用户
     * @param data
     * @returns {*|AxiosPromise}
     */
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
    /**
     * 更细用户信息
     * @param data
     * @returns {*|AxiosPromise}
     */
    updateInfo(data) {
        return api.post(`${relativeUrl}/updateInfo`, {}, data)
    },
    /**
     * 开放用户注册
     * @param data {
     *  avatar: "" --头像
     *  cellphone: "" --手机号码
     *  description: "" --描述
     *  email: "" --邮箱
     *  password: "" --密码
     *  userName: "" --用户名
     * }
     * @returns {*|AxiosPromise}
     */
    registerUser(data) {
        return api.post(`${relativeUrl}/register`, {}, data)
    },
}
