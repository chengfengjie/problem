/**
 * Created by chengfj on 2017/10/17.
 */

const state = {
    currentProject: JSON.parse(localStorage.getItem('currentProject'))
}

const mutations = {
    SET_CURRENT_PROJECT(state, currentProject) {
        state.currentProject = currentProject
        localStorage.setItem('currentProject', JSON.stringify(currentProject))
    },
    DEL_CURRENT_PROJECT(state) {
        state.currentProject = null
        localStorage.removeItem('currentProject')
    },
}

const getters = {
    projectID: (state) => {
        if (state.currentProject) {
            return state.currentProject.projectID
        } else {
            return null
        }
    },
    projectName: (state) => {
        if (state.currentProject) {
            return state.currentProject.projectName
        } else {
            return null
        }
    },
    projectDescription: (state) => {
        if (state.currentProject) {
            return state.currentProject.projectDescription
        } else {
            return null
        }
    },
}

export default {
    state: state,
    mutations: mutations,
    getters: getters,
}
