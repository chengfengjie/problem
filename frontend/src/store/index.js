/**
 * Created by chengfj on 2017/10/17.
 */

import Vue from 'vue'
import vuex from 'vuex'
import currentUser from './modules/currentUser'
import currentProject from './modules/currentProject'

Vue.use(vuex)

export default new vuex.Store({
    modules: {
        currentUser,
        currentProject,
    }
})
