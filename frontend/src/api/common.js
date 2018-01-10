/**
 * Created by chengfj on 2017/10/9.
 */

const axios = require('axios')

exports.ajax = function (type, url, params, data) {
    return axios({
        method: type,
        url,
        params,
        data,
    })
}

exports.get = function (url) {
    let rand = ''
    if (!url.includes('?')) {
        rand = '?r=' + Math.random()
    } else {
        rand = '&r=' + Math.random()
    }
    return axios.get(url + rand)
}

exports.post = function (url, params, data) {
    return axios({
        method: 'post',
        url,
        params,
        data,
    })
}
