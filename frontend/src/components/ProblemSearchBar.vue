<template>
    <div style="background-color: #efefef;padding-left: 10px;padding-right: 10px;padding-top: 10px;margin-bottom: 15px">
        <el-row :gutter="10">

            <el-col :xs="layout.xs" :sm="layout.sm" :md="layout.md" :lg="layout.lg">
                <el-select v-model="searchForm.problemType" @change="handleSelectChange">
                    <el-option label="问题类型:全部" value=""></el-option>
                    <el-option label="问题类型:缺陷" value="1"></el-option>
                    <el-option label="问题类型:任务" value="2"></el-option>
                    <el-option label="问题类型:需求" value="3"></el-option>
                </el-select>
            </el-col>

            <el-col v-if="hasStatus" :xs="layout.xs" :sm="layout.sm" :md="layout.md" :lg="layout.lg">
                <el-select v-model="searchForm.batchStatus"
                           @change="handleSelectChange">
                    <el-option label="状态:全部" value=""></el-option>
                    <el-option label="状态:未解决" value="1"></el-option>
                    <el-option label="状态:待审核" value="3"></el-option>
                    <el-option label="状态:已拒绝" value="11,12,13,14,15,16,17"></el-option>
                    <el-option label="状态:已解决" value="4"></el-option>
                    <el-option label="状态:已关闭" value="0"></el-option>
                    <el-option label="状态:已延期" value="2"></el-option>
                </el-select>
            </el-col>

            <el-col :xs="layout.xs" :sm="layout.sm" :md="layout.md" :lg="layout.lg">
                <el-select v-model="searchForm.productID" @change="handleSelectProductChange">
                    <el-option label="产品:全部" value=""></el-option>
                    <el-option v-for="item in currentProductList"
                               :label="'产品:'+item.productName"
                               :value="item.productID"
                               :key="item.productID"></el-option>
                </el-select>
            </el-col>

            <el-col :xs="layout.xs" :sm="layout.sm" :md="layout.md" :lg="layout.lg">
                <el-select v-model="searchForm.moduleID"
                           @change="handleSelectChange">
                    <el-option label="模块:全部" value=""></el-option>
                    <el-option v-for="item in currentModuleList"
                               :label="'模块:'+item.moduleName"
                               :value="item.moduleID"
                               :key="item.moduleID"></el-option>
                </el-select>
            </el-col>

            <el-col :xs="layout.xs" :sm="layout.sm" :md="layout.md" :lg="layout.lg">
                <el-select v-model="searchForm.priority"
                           @change="handleSelectChange">
                    <el-option label="优先级:全部" value=""></el-option>
                    <el-option label="优先级:急" value="1"></el-option>
                    <el-option label="优先级:高" value="2"></el-option>
                    <el-option label="优先级:中" value="3"></el-option>
                    <el-option label="优先级:低" value="4"></el-option>
                </el-select>
            </el-col>

            <el-col v-if="hasInUser" :xs="layout.xs" :sm="layout.sm" :md="layout.md" :lg="layout.lg">
                <el-select v-model="searchForm.inUserID" @change="handleSelectChange">
                    <el-option label="创建人:全部" value=""></el-option>
                    <el-option v-for="item in currentMemberList"
                               :label="'创建人:'+item.displayName"
                               :value="item.userID"
                               :key="item.userID"></el-option>
                </el-select>
            </el-col>

            <el-col v-if="hasSolveUser" :xs="layout.xs" :sm="layout.sm" :md="layout.md" :lg="layout.lg">
                <el-select v-model="searchForm.solveUserID" @change="handleSelectChange">
                    <el-option label="指派给:全部" value=""></el-option>
                    <el-option v-for="item in currentMemberList"
                               :label="'指派给:'+item.displayName"
                               :value="item.userID"
                               :key="item.userID"></el-option>
                </el-select>
            </el-col>

        </el-row>
    </div>
</template>
<script>

    import projectProductApi from '~/api/projectProductApi.js'
    import projectMemberApi from '~/api/projectMemberApi.js'
    import projectApi from '~/api/projectApi.js'

    export default {
        props: {
            projectID: [String, Number],
            hasStatus: {
                type: Boolean,
                default: false,
            },
            hasSolveUser: {
                type: Boolean,
                default: true
            },
            hasInUser: {
                type: Boolean,
                default: true,
            },
        },
        data() {
            return {
                searchForm: {
                    problemType: '',
                    productID: '',
                    moduleID: '',
                    priority: '',
                    solveUserID: '',
                    inUserID: '',
                    batchStatus: '',
                },
                currentProductList: [],
                currentModuleList: [],
                currentMemberList: [],
                layout: {
                    xs: 6,
                    sm: 6,
                    md: 4,
                    lg: 4,
                },
            }
        },
        created() {
            this.fetchData()
        },
        methods: {
            handleSelectChange(val) {
                this.$emit('dataChange', this.searchForm)
            },
            handleSelectProductChange(val) {
                this.searchForm.moduleID = ''
                projectProductApi.queryModuleList(val).then(res => {
                    if (res.data.data) {
                        this.currentModuleList = res.data.data
                    }
                })
                this.$emit('dataChange', this.searchForm)
            },
            fetchData() {
                projectApi.queryProjectById(this.projectID).then(res => {
                    if (res.data.data) {
                        this.currentProductList = res.data.data.productData
                        this.currentMemberList = res.data.data.memberData
                    }
                })
            },
        },
    }
</script>

<style scoped>
    .el-select {
        margin-bottom: 10px;
        width: 100%;
    }
</style>
