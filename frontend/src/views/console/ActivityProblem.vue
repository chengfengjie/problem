<template>
    <div id="activityProblem">

        <problem-info-dialog v-on:updateData="fetchData"
                             :visible.sync="problemVisible"
                             :problemID="currentProblemID">
        </problem-info-dialog>

        <problem-search-bar :projectID="$store.getters.projectID"
                    v-on:dataChange="handleDataChange"></problem-search-bar>

        <el-table @cell-click="handleCellClick" :row-style="handleRowStyle" border :data="problemData" stripe>
            <el-table-column  label="编号" prop="problemID" width="70px"></el-table-column>
            <el-table-column label="类型" width="80px">
                <template scope="scope">
                    <problem-type-tag :problemType="scope.row.problemType"></problem-type-tag>
                </template>
            </el-table-column>
            <el-table-column label="标题">
                <template scope="scope">
                    <problem-table-title :data="scope.row"></problem-table-title>
                </template>
            </el-table-column>
            <el-table-column label="状态" width="90px">
                <template scope="scope">
                    <problem-status-tag :status="scope.row.status"></problem-status-tag>
                </template>
            </el-table-column >
            <el-table-column label="优先级" width="80px">
                <template scope="scope">
                    <problem-priority-tag :priority="scope.row.priority"></problem-priority-tag>
                </template>
            </el-table-column>
            <el-table-column label="创建人" prop="inUserName" width="110px"></el-table-column>
            <el-table-column label="指派给" prop="solveUserName" width="110px"></el-table-column>
            <el-table-column label="创建日期" width="130px">
                <template scope="scope">
                    <span>{{$util.formatDateDay(scope.row.inDate)}}</span>
                </template>
            </el-table-column>
            <el-table-column label="修改日期" width="130px">
                <template scope="scope">
                    <span>{{$util.formatDateDay(scope.row.lastEditDate)}}</span>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination layout="total, sizes, prev, pager, next"
                class="page-box"
                @current-change="handleUserCurrentPageChange"
                @size-change="handleUserPageSizeChange"
                :page-size="page.size"
                :total="page.total">
        </el-pagination>
        <div style="clear: both"></div>
    </div>
</template>
<script>

    import problemApi from '~/api/problemApi.js'

    export default {
        name: 'ActivityProblem',
        props: {
            filterData: {
                type: Object,
                default: () => {
                    return {}
                },
            }
        },
        data() {
            return {
                problemData: [],
                page: {
                    size: 10,
                    num: 1,
                    total: 0,
                },
                problemVisible: false,
                currentProblemID: '',
                searchForm: {
                    problemType: '',
                    productID: '',
                    moduleID: '',
                    priority: '',
                    solveUserID: '',
                    inUserID: '',
                },
            }
        },
        created() {
            console.log(this)
            this.fetchData()
        },
        methods: {
            handleRowStyle(row, index) {
                return {
                    cursor: 'pointer',
                }
            },
            handleCellClick(row, column, cell, event) {
                this.currentProblemID = row.problemID
                this.problemVisible = true
            },
            fetchData() {
                let data = {
                    projectID: this.$store.getters.projectID,
                    batchStatus: '1,3,11,12,13,14,15,16,17',
                    problemType: this.searchForm.problemType,
                    productID: this.searchForm.productID,
                    moduleID: this.searchForm.moduleID,
                    priority: this.searchForm.priority,
                    solveUserID: this.searchForm.solveUserID,
                    inUserID: this.searchForm.inUserID,
                    pageNum: this.page.num,
                    pageSize: this.page.size,
                }
                problemApi.queryList(data).then(res => {
                    if (res.data.data) {
                        console.log(res.data.data)
                        this.problemData = res.data.data.list
                        this.page.total = res.data.data.total
                    }
                })
            },
            handleUserCurrentPageChange(val) {
                this.page.num = val
                this.fetchData()
            },
            handleUserPageSizeChange(val) {
                this.page.size = val
                this.fetchData()
            },
            handleDataChange(val) {
                this.searchForm = val
                this.fetchData()
            },
        },
    }
</script>

<style scoped>
    .page-box {
        float: right;
        margin-top: 15px;
        margin-right: -5px;
        margin-bottom: 15px;
    }
</style>
