<template>
    <div id="general">
        <el-row :gutter="10">
            <el-col v-for="item in itemData" :key="item.key" :xs="4" :sm="4" :md="4" :lg="3" style="margin-bottom: 10px">
                <div class="info-item-box" @click="handleClick(item.key)" :style="item.style">
                    <h1>{{item.name}}</h1>
                    <p>{{projectData[item.key]}}</p>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    import configData from '~/config/General.js'
    import projectApi from '~/api/projectApi.js'
    export default {
        name: 'General',
        data() {
            return {
                itemData: configData,
                projectData: {
                    total: '0',
                    open: '0',
                    waitAudit: '0',
                    reject: '0',
                    solve: '0',
                    defer: '0',
                    close: '0',
                    defect: '10',
                    job: '0',
                    need: '0',
                    status: '',
                    member: '0',
                    product: '0',
                    health: '0',
                },
            }
        },
        created() {
            this.fetchData()
        },
        methods: {
            fetchData() {
                projectApi.projectGeneral(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.projectData = res.data.data
                    }
                })
            },
            handleClick(key) {
                console.log(key)
                if (key === 'total') {
                    this.$router.replace({ 'path': '/console/allProblem'})
                }
                if (key === 'open') {
                    this.$router.replace({
                        path: '/console/activityProblem',
                        params: {
                            filterData: {
                                state: 1
                            }
                        }
                    })
                }
            },
        }
    }
</script>

<style scoped>
    #general .info-item-box {
        background-color: #00aeef;
        height: 80px;
        border-radius: 10px;
        padding: 10px;
        cursor: pointer;
    }
    #general .info-item-box h1 {
        text-align: center;
        color: white;
        margin-top: 3px;
        line-height: 40px;
        font-size: 24px;
        font-weight: 200;
    }
    #general .info-item-box p {
        text-align: center;
        color: white;
        font-size: 19px;
    }
</style>
