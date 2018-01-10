/**
 * Created by chengfj on 2017/10/23.
 */

const moment = require('moment')


module.exports = {
    /**
     * 从上传的图片列表中解析出图片路径数组提交给后台处理
     * @param imageList
     * @returns {Array}
     */
    makeUploadImageList(imageList) {
        let imageNameList = []
        for (let index in imageList) {
            let imageObject = imageList[index]
            let imageName = imageObject.response.data.path
            imageNameList.push(imageName)
        }
        return imageNameList
    },
    /**
     * 格式化时间到天
     * @param timestamp
     * @returns {*}
     */
    formatDateDay(timestamp) {
        if (timestamp !== null && timestamp !== undefined) {
            return moment(timestamp).format('YYYY-MM-DD')
        } else {
            return ''
        }
    },
    /**
     * 移除数组元素
     * @param array 数组
     * @param val 元素
     */
    removeArrayValue(array, val) {
        if (array instanceof Array) {
            for (let i = 0; i < array.length; i++) {
                if (array[i] === val) {
                    array.splice(i, 1)
                    break
                }
            }
        }
    },
}
