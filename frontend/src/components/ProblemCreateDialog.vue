<template>
    <div>
        <el-dialog @close="handleDialogClose" title="新建问题" :visible.sync="createProblemVisible" top="10px">
            <el-tabs @tab-click="handleTabClick">
                <el-tab-pane label="信息与描述">
                    <el-form style="margin-left: 15px;margin-right: 15px"
                             label-position="left"
                             label-width="80px"
                             :model="createProblemForm"
                             ref="createProblemForm"
                             :rules="createProblemFormRules">

                        <el-form-item prop="problemTitle" label="问题标题">
                            <el-input v-model="createProblemForm.problemTitle" placeholder="请输入问题标题, 最多50个字"></el-input>
                        </el-form-item>

                        <el-form-item label="项目产品">
                            <el-select @change="handleProductIdChange"
                                       v-model="createProblemForm.productID"
                                       style="width: 100%">
                                <el-option v-for="item in currentProductData"
                                           :label="item.productName"
                                           :value="item.productID.toString()"
                                           :key="item.productID"></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="产品模块">
                            <el-select v-model="createProblemForm.moduleID">
                                <el-option v-for="item in currentModuleData"
                                           :label="item.moduleName"
                                           :value="item.moduleID.toString()"
                                           :key="item.moduleID"></el-option>
                            </el-select>
                            <el-button v-if="createProblemForm.productID !== ''" type="primary" icon="plus">添加新模块</el-button>
                        </el-form-item>

                        <el-form-item label="问题类型">
                            <el-select v-model="createProblemForm.problemType" style="width: 100%">
                                <el-option label="缺陷" value="1"></el-option>
                                <el-option label="任务" value="2"></el-option>
                                <el-option label="需求" value="3"></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="发现环境">
                            <el-input v-model="createProblemForm.environment" placeholder="最多100个字"></el-input>
                        </el-form-item>

                        <el-form-item label="问题描述" prop="description">
                            <el-input type="textarea" placeholder="最多2000字" v-model="createProblemForm.description"></el-input>
                        </el-form-item>

                        <el-form-item label="上传图片" v-show="showUploadImage">
                            <el-upload action="/api/v1/upload/image"
                                       list-type="picture-card"
                                       :on-success="handleUploadImageSuccess"
                                       :on-remove="handleImageRemove">
                                <i class="el-icon-plus"></i>
                            </el-upload>
                        </el-form-item>

                        <el-form-item label=" ">
                            <el-button v-if="showUploadImage===false" @click="showUploadImage = true" type="text" size="mini">图片<i class="el-icon-arrow-down el-icon--right"></i></el-button>
                            <el-button v-else @click="showUploadImage = false" type="text" size="mini">图片<i class="el-icon-arrow-up el-icon--right"></i></el-button>
                        </el-form-item>

                    </el-form>
                </el-tab-pane>
                <el-tab-pane label="人员与计划">
                    <el-form style="margin-left: 15px; margin-right: 15px;"
                             label-position="left"
                             label-width="80px">
                        <el-form-item label="指派人员">
                            <el-select v-model="createProblemForm.solveUserID" style="width: 100%">
                                <el-option v-for="item in currentMemberData"
                                           :label="item.displayName"
                                           :value="item.userID.toString()"
                                           :key="item.userID"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="优先级">
                            <el-select v-model="createProblemForm.priority" style="width: 100%">
                                <el-option label="低" value="4"></el-option>
                                <el-option label="中" value="3"></el-option>
                                <el-option label="高" value="2"></el-option>
                                <el-option label="急" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="计划日期">
                            <el-date-picker v-model="createProblemForm.dateRange"
                                            type="daterange"
                                            placeholder="选择日期范围"
                                            style="width: 100%">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="计划版本">
                            <el-input placeholder="请输入计划修复版本" v-model="createProblemForm.planVersion"></el-input>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>

                <el-tab-pane label="标签" name="tagSetting">
                    <div v-if="currentTagData.length > 0" style="margin-left: 15px;margin-right: 15px">
                        <problem-tag v-for="item in currentTagData"
                                     :isActive="true"
                                     :color="item.tagColor"
                                     v-on:change="handleTagChange"
                                     :value="item.tagID"
                                     :key="item.tagName">{{item.tagName}}</problem-tag>
                    </div>
                    <div v-else style="margin-left: 15px;margin-right: 15px">
                        <span style="color: #999999">暂无标签,可以去项目设置添加标签</span>
                    </div>
                </el-tab-pane>

            </el-tabs>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleSaveProblem" type="primary" style="margin-right: 15px">提交问题</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    import projectProductApi from '~/api/projectProductApi.js'
    import projectApi from '~/api/projectApi.js'
    import projectTagApi from '~/api/projectTagApi.js'
    import problemApi from '~/api/problemApi.js'
    export default {
        props: {
            visible: {
                type: [Boolean],
                default: false,
            },
        },
        data() {
            return {
                createProblemVisible: false,
                createProblemForm: this.createProblemFormData(),
                showUploadImage: false,
                createProblemFormRules: {
                    problemTitle: [
                        { required: true, message: '问题标题必须填写', trigger: 'blur' },
                    ],
                },
                currentProductData: [],
                currentModuleData: [],
                currentMemberData: [],
                currentTagData: [],
                uploadImageList: [],
            }
        },
        watch: {
            visible: function (newValue) {
                if (newValue) {
                    this.handleCreateProblem()
                }
            }
        },
        methods: {
            createProblemFormData() {
                return {
                    problemTitle: '',
                    productID: '',
                    moduleID: '',
                    problemType: '1',
                    solveUserID: '',
                    priority: '3',
                    dateRange: '',
                    environment: '',
                    description: '',
                    planVersion: '',
                    tagList: [],
                }
            },
            handleCreateProblem() {
                projectApi.queryProjectById(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.currentProductData = res.data.data.productData
                        this.currentMemberData = res.data.data.memberData
                        this.createProblemVisible = true
                    }
                })
            },
            handleProductIdChange(productID) {
                projectProductApi.queryModuleList(productID).then(res => {
                    if (res.data.data) {
                        this.createProblemForm.moduleID = ''
                        this.currentModuleData = res.data.data
                    }
                })
            },
            handleTagChange(value, isSelect) {
                if (isSelect) {
                    this.createProblemForm.tagList.push(value)
                } else {
                    this.$util.removeArrayValue(this.createProblemForm.tagList, value)
                }
            },
            handleSaveProblem() {
                this.$refs['createProblemForm'].validate((valid) => {
                    if (valid) {
                        let data = this.createProblemForm
                        data['projectID'] = this.$store.getters.projectID
                        data['imageList'] = this.$util.makeUploadImageList(this.uploadImageList)
                        data['beginDate'] = data.dateRange[0] ? this.$moment(data.dateRange[0]).format() : ''
                        data['finishDate'] = data.dateRange[1] ? this.$moment(data.dateRange[1]).format() : ''
                        problemApi.createProblem(data).then(res => {
                            if (res.data.code === 0) {
                                this.createProblemForm = this.createProblemFormData()
                                this.createProblemVisible = false
                                this.$message('创建问题成功')
                                this.$emit('updateData')
                            }
                        })
                    } else {
                        return false
                    }
                })
            },
            handleTabClick(val) {
                if (val.name === 'tagSetting') {
                    this.fetchCurrentTagData()
                }
            },
            fetchCurrentTagData() {
                projectTagApi.queryProjectTagList(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.currentTagData = res.data.data
                    }
                })
            },
            handleUploadImageSuccess(res, file, fileList) {
                this.uploadImageList = fileList
            },
            handleImageRemove(file, fileList) {
                this.uploadImageList = fileList
            },
            handleDialogClose() {
                this.$store.commit('UPDATE_NOTICE', this.$store.getters.projectID)
                this.$emit('update:visible', false)
            },
        },
    }
</script>
