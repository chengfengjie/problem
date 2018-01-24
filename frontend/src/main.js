import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import App from './App.vue'
import axios from 'axios'
import './resource/css/reset.css'
import router from './router'
import VueCookies from 'vue-cookies'
import moment from 'moment'
import store from './store'
import problemTag from './components/ProblemTag.vue'
import problemInfoDialog from './components/ProblemInfoDialog.vue'
import util from './util/index'

import problemStatusTag from '~/components/ProblemStatusTag.vue'
import problemTypeTag from '~/components/ProblemTypeTag.vue'
import searchBar from '~/components/ProblemSearchBar.vue'
import priorityTag from '~/components/ProblemPriorityTag.vue'
import problemTableTitle from '~/components/ProblemTableTitle.vue'
import problemCreateDialog from '~/components/ProblemCreateDialog.vue'
import imagePreviewDialog from '~/components/ImagePreviewDialog.vue'
import problemEditDialog from '~/components/ProblemEditDialog.vue'

Vue.use(ElementUI)
Vue.use(VueCookies)
Vue.component('problem-tag', problemTag)
Vue.component('problem-info-dialog', problemInfoDialog)
Vue.component('problem-status-tag', problemStatusTag)
Vue.component('problem-type-tag', problemTypeTag)
Vue.component('problem-search-bar', searchBar)
Vue.component('problem-priority-tag', priorityTag)
Vue.component('problem-table-title', problemTableTitle)
Vue.component('problem-create-dialog', problemCreateDialog)
Vue.component('image-preview-dialog', imagePreviewDialog)
Vue.component('problem-edit-dialog', problemEditDialog)


Vue.prototype.$moment=moment
Vue.prototype.$util = util

const app = new Vue({
  el:'#app',
  router,
  store,
  render: h => h(App)
})

let loadingInstance
axios.interceptors.request.use((config) => {
  let isSourceMap = config && config.url && config.url.indexOf('.js.map') !== -1
  if (!isSourceMap) {
    loadingInstance = ElementUI.Loading.service({
      fullscreen: true,
      text: '拼命加载中...'
    })
  }
  return config
})

axios.interceptors.response.use((response) => {
  let isSourceMap = response.config && response.config.url && response.config.url.indexOf('.js.map') !== -1
  if (isSourceMap) {
    loadingInstance.close()
    return response;
  } else {
    loadingInstance.close()
    switch (response.data.code) {
      case 0:
        if (response.data.data === null && response.data.message && response.data.message !== '') {
          app.$message(response.data.message)
        }
        return response
      case 1:
        app.$message.error(response.data.errorMessage)
        return response
      case 2:
        app.$message({
          message: response.data.errorMessage,
          type: 'warning',
        })
        return response
      case 3:
        app.$message({
          message: '请登录',
          type: 'warning',
        })
        app.$router.replace({ path: '/login' })
        return response
      default:
        return response
    }
  }
}, (error) => {
  app.$message({
    'message': '服务器错误',
    'type': 'error',
  })
  loadingInstance.close()
  return Promise.reject(error.response)
})
