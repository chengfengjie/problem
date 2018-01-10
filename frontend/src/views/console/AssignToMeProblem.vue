<template>
    <div>

        <problem-info-dialog v-on:updateData="fetchData"
                             :visible.sync="problemVisible"
                             :problemID="currentProblemID">
        </problem-info-dialog>

        <problem-search-bar v-on:dataChange="searchDataChange"
                            :projectID="$store.getters.projectID"
                            :hasSolveUser="false"
                            :hasInUser="false" :hasStatus="true" />

        <el-table :row-style="handleRowStyle" @cell-click="handleCellClick" border :data="tableData">
            <el-table-column label="编号" prop="problemID" width="70px"></el-table-column>
            <el-table-column label="类型" width="80px">
                <template scope="scope">
                    <problem-type-tag :problemType="scope.row.problemType" />
                </template>
            </el-table-column>
            <el-table-column label="标题">
                <template scope="scope">
                    <problem-table-title :data="scope.row"></problem-table-title>
                </template>
            </el-table-column>
            <el-table-column label="状态" width="90px">
                <template scope="scope">
                    <problem-status-tag :status="scope.row.status" />
                </template>
            </el-table-column>
            <el-table-column label="优先级" width="80px">
                <template scope="scope">
                    <problem-priority-tag :priority="scope.row.priority" />
                </template>
            </el-table-column>
            <el-table-column label="创建人" prop="inUserName" width="120px"></el-table-column>
            <el-table-column label="创建日期" width="130px">
                <template scope="scope">
                    {{$util.formatDateDay(scope.row.inDate)}}
                </template>
            </el-table-column>
            <el-table-column label="修改日期" width="130px">
                <template scope="scope">
                    {{$util.formatDateDay(scope.row.lastEditDate)}}
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

    </div>
</template>
<script>
    import problemApi from '~/api/problemApi.js'
    export default {
        name: 'AssignToMeProblem',
        data() {
            return {
                tableData: [],
                page: {
                    num: 1,
                    size: 10,
                    total: 0,
                },
                searchForm: {},
                problemVisible: false,
                currentProblemID: '',
            }
        },
        created() {
            this.fetchData()
        },
        methods: {
            fetchData() {
                let data = {
                    projectID: this.$store.getters.projectID,
                    solveUserID: this.$store.getters.userID,
                    pageNum: this.page.num,
                    pageSize: this.page.size,
                    problemType: this.searchForm.problemType,
                    batchStatus: this.searchForm.batchStatus,
                    productID: this.searchForm.productID,
                    moduleID: this.searchForm.moduleID,
                    priority: this.searchForm.priority,
                }
                problemApi.queryList(data).then(res => {
                    if (res.data.data) {
                        this.tableData = res.data.data.list
                        this.page.total = res.data.data.total
                    }
                })
            },
            searchDataChange(val) {
                console.log(val)
                this.searchForm = val
                this.fetchData()
            },
            handleUserCurrentPageChange(val) {
                this.page.num = val
                this.fetchData()
            },
            handleUserPageSizeChange(val) {
                this.page.size = val
                this.fetchData()
            },
            handleCellClick(row, column, cell, event) {
                this.currentProblemID = row.problemID
                this.problemVisible = true
            },
            handleRowStyle(row, index) {
                return {
                    cursor: 'pointer',
                }
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
