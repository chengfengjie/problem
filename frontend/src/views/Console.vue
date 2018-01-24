<template>
    <div style="width: 100%;height: 100%">
        <HeaderBar :showSearchBar="true" v-on:search="handleHeaderBarClickSearch">
            <div style="font-size: 16px;line-height: 50px;color: white;">
                <router-link to="/project" style="color: white; text-decoration: none;font-weight: 300"><span style="color: white;text-decoration: blink">项目</span></router-link>
                <span style="font-size: 16px;font-weight: 300">&nbsp;&nbsp;&gt;&nbsp;&nbsp;</span>
                <span style="font-size: 15px;font-weight: 300">{{$store.getters.projectName}}</span>
            </div>
        </HeaderBar>

        <problem-info-dialog :visible.sync="problemInfoVisible"
                             :problemID="currentProblemID"
                             v-on:updateData="handleUpdateData">
        </problem-info-dialog>

        <problem-create-dialog v-on:updateData="handleUpdateData"
                             :visible.sync="createProblemVisible">
        </problem-create-dialog>

        <div class="side-menu">
            <el-menu @select="handleSelect" mode="vertical" :default-active="defaultActive">
                <el-menu-item v-for="item in routes" :index="item.path" :key="item.path">
                    <span style="margin-right: 10px">{{item.name}}</span>
                    <span v-if="item.name === '活动问题'"
                          class="proble-notice-badge-box"
                          :style="noticeBackground($store.getters.activityProblemTotal, '#4d9ef8')">{{$store.getters.activityProblemTotal}}</span>
                    <span v-if="item.name === '我的待办'"
                          class="proble-notice-badge-box"
                          :style="noticeBackground($store.getters.myTaskProblemTotal)">{{$store.getters.myTaskProblemTotal}}</span>
                </el-menu-item>
            </el-menu>
        </div>
        <div class="console-main-box">
            <div class="console-top-toolbar">
                <h1>{{title}}</h1>
                <el-button style="float: right;"
                           type="primary"
                           icon="plus"
                           @click="handleCreateProblem">提交问题</el-button>
            </div>
            <div class="console-content">
                <router-view ref="subComponent"></router-view>
            </div>
        </div>
    </div>
</template>
<script>

    import HeaderBar from '~/components/HeaderBar.vue'
    import problemApi from '~/api/problemApi.js'

    export default {
        name: 'Console',
        data() {
            return {
                title: '项目概况',
                createProblemVisible: false,
                defaultActive: '/console/general',
                problemInfoVisible: false,
                currentProblemID: null,
                routes: [
                    {
                        path: '/console/general',
                        name: '项目概况',
                    },
                    {
                        path: '/console/activityProblem',
                        name: '活动问题',
                    },
                    {
                        path: '/console/allProblem',
                        name: '所有问题',
                    },
                    {
                        path: '/console/myActivityProblem',
                        name: '我的待办',
                    },
                    {
                        path: '/console/assignToMeProblem',
                        name: '分配给我',
                    },
                    {
                        path: '/console/myAssignedProblem',
                        name: '我的分配',
                    },
                ],
            }
        },
        components: {
            HeaderBar: HeaderBar
        },
        created() {
            this.defaultActive = this.$router.currentRoute.path
            this.setCurrentName(this.defaultActive)
            this.$store.commit('UPDATE_NOTICE', this.$store.getters.projectID)
        },
        beforeRouteUpdate(to, from, next) {
            this.defaultActive = to.fullPath
            this.setCurrentName(this.defaultActive)
            next()
        },
        methods: {
            handleSelect(index) {
                this.$router.push({path: index})
                this.setCurrentName(index)
            },
            setCurrentName(path) {
                for (let index in this.routes) {
                    if (this.routes[index].path === path) {
                        this.title = this.routes[index].name
                    }
                }
            },
            handleCreateProblem() {
                this.createProblemVisible = true
            },
            handleUpdateData() {
                if (this.$refs['subComponent'] && this.$refs['subComponent'].fetchData) {
                    this.$refs['subComponent'].fetchData()
                }
            },
            noticeBackground(total, color) {
                if (total > 0) {
                    return {
                        'backgroundColor': color === undefined ? 'orange' : color
                    }
                } else {
                    return {
                        'backgroundColor': '#a2a2a2'
                    }
                }
            },
            handleHeaderBarClickSearch(isId, val) {
                if (isId) {
                    problemApi.queryProblemByIdAndProjectId(val, this.$store.getters.projectID).then(res => {
                        if (res.data.data) {
                            this.currentProblemID = val
                            this.problemInfoVisible = true
                        }
                    })
                }
            },
        }
    }
</script>

<style scoped>
    .side-menu {
        width: 140px;
        background-color: #eef1f6;
        position: absolute;
        top: 50px;
        left: 0px;
        bottom: 0px;
    }
    .console-main-box {
        background-color: white;
        left: 140px;
        top: 50px;
        position: absolute;
        right: 0;
        bottom: 0;
        min-width: 860px;
        overflow: scroll;
    }
    .console-top-toolbar {
        top: 10px;
        left: 10px;
        right: 10px;
        position: absolute;
        height: 40px;
    }
    .console-top-toolbar h1 {
        font-size: 20px;
        font-weight: 300;
        color: #333;
        line-height: 40px;
        margin-left: 0px;
        float: left;
    }
    .console-content {
        position: absolute;
        top: 70px;
        bottom: 10px;
        left: 10px;
        right: 10px;
    }
    .proble-notice-badge-box {
        background-color: #a2a2a2;
        color: white;
        font-size: 9px;
        height: 18px;
        display: inline-block;
        line-height: 18px;
        padding-left: 9px;
        padding-right: 9px;
        border-radius: 9px;
        font-weight: normal;
    }
</style>
