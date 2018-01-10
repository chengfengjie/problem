/**
 * Created by chengfj on 2017/10/16.
 */
const api = require('./common.js')
const relativeUrl = '/api/v1/project/product'

module.exports = {
    createProduct(data) {
        return api.post(`${relativeUrl}/create`, {}, data)
    },
    queryProductList(projectID) {
        return api.get(`${relativeUrl}/queryList?projectID=${projectID}`)
    },
    createModule(data) {
        return api.post(`${relativeUrl}/createModule`, {}, data)
    },
    queryModuleList(productID) {
        return api.get(`${relativeUrl}/queryModuleList?productID=${productID}`)
    },
    deleteModule(moduleID) {
        return api.get(`${relativeUrl}/deleteModule?moduleID=${moduleID}`)
    },
    updateModule(data) {
        return api.post(`${relativeUrl}/updateModule`, {}, data)
    },
    deleteProduct(productID) {
        return api.get(`${relativeUrl}/delete?productID=${productID}`)
    },
}