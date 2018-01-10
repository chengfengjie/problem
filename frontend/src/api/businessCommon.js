/**
 * Created by chengfj on 2018/1/10.
 */

const api = require('./common.js')
const relativeUrl = '/api/v1/businessCommon'

module.exports = {
    getAppConfig() {
        return api.get(`${relativeUrl}/getConfig`)
    },
}
