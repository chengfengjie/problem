<template>
    <div>
        <el-dialog title="编辑问题" @close="handleClose" :visible.sync="dialogVisible" top="30px">
            <el-tabs>
                <el-tab-pane label="标签">
                    <div v-if="currentTagData.length > 0" style="margin-left: 15px;margin-right: 15px">
                        <problem-tag v-for="item in currentTagData"
                                     :isActive="true"
                                     :color="item.tagColor"
                                     v-on:change="handleTagChange"
                                     :value="item.tagID"
                                     :key="item.tagName"
                                     :selected="item.selected">{{item.tagName}}</problem-tag>
                    </div>
                    <div v-else style="margin-left: 15px;margin-right: 15px">
                        <span style="color: #999999">暂无标签,可以去项目设置添加标签</span>
                    </div>
                </el-tab-pane>
            </el-tabs>

            <span slot="footer" class="dialog-footer">
                <el-button @click="handleSaveProblem" type="primary" style="margin-right: 15px">保存问题</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    import problemApi from '~/api/problemApi.js'
    import projectTagApi from '~/api/projectTagApi.js'
    export default {
        props: {
            problemID: [String, Number],
            visible: [Boolean]
        },
        data() {
            return {
                dialogVisible: false,
                data: {},
                currentTagData: [],
                selectTagList: [],
            }
        },
        watch: {
            visible: function (val) {
                if (val) {
                    this.fetchData()
                }
            }
        },
        methods: {
            fetchData() {
                projectTagApi.queryProjectTagList(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.currentTagData = res.data.data
                        problemApi.queryProblemById(this.problemID).then(res => {
                            if (res.data.code === 0) {
                                this.data = res.data.data
                                for (let i = 0; i < this.currentTagData.length; i++) {
                                    this.currentTagData[i].selected = false
                                    for (let j = 0; j < this.data.tagList.length; j++) {
                                        if (this.currentTagData[i].tagID === this.data.tagList[j].tagID) {
                                            this.currentTagData[i].selected = true
                                            this.selectTagList.push(this.currentTagData[i].tagID)
                                        }
                                    }
                                }
                                this.dialogVisible = true
                            } else {
                                this.$emit('update:visible', false)
                            }
                        })
                    }
                })
            },
            handleClose() {
                this.$emit('update:visible', false)
            },
            handleTagChange(value, isSelect) {
                if (isSelect) {
                    this.selectTagList.push(value)
                } else {
                    this.$util.removeArrayValue(this.selectTagList, value)
                }
                console.log(this.selectTagList)
            },
            handleSaveProblem() {
                let data = {
                    tagList: this.selectTagList,
                    problemID: this.problemID,
                }
                problemApi.updateProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.dialogVisible = false
                        this.$emit('updateData')
                    }
                })
            },
        }
    }
</script>
