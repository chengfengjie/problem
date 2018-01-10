<template>
    <div>
        <el-dialog title="评论问题" :visible.sync="commentProblemVisible" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="评论内容:">
                    <el-input v-model="commentProblemForm.content" type="textarea" :rows="5" placeholder="请输入您的评论内容，最多500字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadCommentImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleCommentProblemSave" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="指派" :visible.sync="assignVisible" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="指派给:">
                    <el-select v-model="assignForm.userID">
                        <el-option v-for="item in currentProjectMemberList"
                                   :label="item.displayName"
                                   :value="item.userID.toString()"
                                   :key="item.userID"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="assignForm.content" type="textarea" :rows="5" placeholder="最多500个字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadAssignImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleAssignSaveClick" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修复" :visible.sync="repairVisible" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="指派给:">
                    <el-select v-model="repairForm.userID">
                        <el-option v-for="item in currentProjectMemberList"
                                   :label="item.displayName"
                                   :value="item.userID.toString()"
                                   :key="item.userID"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="repairForm.content" type="textarea" :rows="5" placeholder="最多500个字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadRepairImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleRepairSaveClick" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog :title="auditTitle" :visible.sync="auditVisible" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="审核版本:">
                    <el-input v-model="auditForm.auditVersionName" placeholder="最多50个字"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="auditForm.content" type="textarea" :rows="5" placeholder="最多500个字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadAuditPassImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleAuditPassSaveClick" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="再打开" :visible.sync="openProblemVisible" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="发现版本:">
                    <el-input v-model="openProblemForm.versionName" placeholder="最多50个字"></el-input>
                </el-form-item>
                <el-form-item label="指派给:">
                    <el-select v-model="openProblemForm.userID" style="width: 100%">
                        <el-option v-for="item in currentProjectMemberList"
                                   :label="item.displayName"
                                   :value="item.userID.toString()"
                                   :key="item.userID"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="计划日期:">
                    <el-date-picker v-model="openProblemForm.dateRange"
                                    type="daterange"
                                    placeholder="选择日期范围"
                                    style="width: 100%">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="优先级:">
                    <el-select v-model="openProblemForm.priority" style="width: 100%">
                        <el-option label="急" value="1"></el-option>
                        <el-option label="高" value="2"></el-option>
                        <el-option label="中" value="3"></el-option>
                        <el-option label="低" value="4"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="备注:">
                    <el-input v-model="openProblemForm.content"
                              type="textarea"
                              :rows="5"
                              placeholder="最多500个字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadOpenProblemImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleOpenProbleSaveClick" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="拒绝" :visible.sync="rejectProblemVisble" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="拒绝原因:">
                    <el-select v-model="rejectProblemForm.rejectType" style="width: 100%">
                        <el-option label="不是错误" value="1"></el-option>
                        <el-option label="重复问题" value="2"></el-option>
                        <el-option label="延期解决" value="3"></el-option>
                        <el-option label="设计如此" value="4"></el-option>
                        <el-option label="不能复现" value="5"></el-option>
                        <el-option label="不同意意见" value="6"></el-option>
                        <el-option label="忽略" value="7"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="指派给:">
                    <el-select v-model="rejectProblemForm.userID" style="width: 100%">
                        <el-option v-for="item in currentProjectMemberList"
                                   :label="item.displayName"
                                   :value="item.userID.toString()"
                                   :key="item.userID"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="rejectProblemForm.content"
                              type="textarea"
                              :rows="5"
                              placeholder="最多500个字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadRejectProblemImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleRejectProblemSaveClick" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="关闭" :visible.sync="closeProblemVisible" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="备注:">
                    <el-input v-model="closeProblemForm.content"
                              type="textarea"
                              :rows="5"
                              placeholder="最多500个字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadCloseProblemImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="handleCloseProblemSaveClick" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="延期" :visible.sync="deferProblemVisible" top="30px">
            <el-form label-width="80px" label-position="left">
                <el-form-item label="备注:">
                    <el-input v-model="deferProblemForm.content"
                              type="textarea"
                              :rows="5"
                              placeholder="最多500个字"></el-input>
                </el-form-item>
                <el-form-item label="上传图片:">
                    <el-upload action="/api/v1/upload/image"
                               list-type="picture-card"
                               :on-success="handleUploadImageSuccess"
                               :on-remove="handleImageRemove"
                               ref="uploadDeferProblemImageField">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="handleDeferProblemSaveClick" type="primary" style="margin-right: 15px">提交</el-button>
            </span>
        </el-dialog>

        <el-dialog title="图片" :visible.sync="imageVisible" top="30px" :size="imageSize">
            <img :src="currentImageName"
                 @click="changeImageSize"
                 style="display: block;margin: auto;border: 1px dotted #e9e9e9;max-width: 100%;cursor: pointer"
                 :style="imageStyle" />
        </el-dialog>

        <problem-edit-dialog :problemID="problemID" :visible.sync="editVisible" v-on:updateData="fetchData"></problem-edit-dialog>

        <el-dialog @close="handleClose" title="问题信息" :visible.sync="dialogVisible" top="30px">
            <div id="problem-operation" style="margin-top: 0px">

                <el-button v-if="problemData.hasCommentPerssion"
                           type="primary"
                           @click="handleCommentProblem">评论</el-button>

                <el-button v-if="problemData.hasAssignPerssion"
                           type="primary"
                           @click="handleAssignClick">指派</el-button>

                <el-button v-if="problemData.hasRepairPerssion"
                           @click="handleRepairClick"
                           type="primary">修复</el-button>

                <el-button v-if="problemData.hasAuditPassPerssion"
                           type="primary"
                           @click="handleAuditPassClick">通过</el-button>
                <el-button v-if="problemData.hasAuditNotPassPerssion"
                           type="primary"
                           @click="handleAuditNoPassClick">不通过</el-button>

                <el-button v-if="problemData.hasOpenProblemPerssion"
                           @click="handleOpenProblemClick"
                           type="primary">再打开</el-button>

                <el-button v-if="problemData.hasRejectPerssion"
                           type="primary"
                           @click="handleRejectProblemClick">拒绝</el-button>

                <el-button v-if="problemData.hasCloseProblemPerssion"
                           @click="handleCloseProblemClick"
                           type="primary">关闭</el-button>

                <el-button v-if="problemData.hasDeferProblemPerssion"
                           @click="handleDeferProblemClick"
                           type="primary">延期</el-button>

                <el-button @click="handleEditProblemClick">标签</el-button>

            </div>
            <div id="problem-detail-box" :style="contentStyle">

                <h3 style="font-weight: 400">
                    #{{problemData.problemID}}&nbsp;
                    <el-tag type="warning" style="margin-right: 5px">缺陷</el-tag>
                    <span style="color: #20a0ff">{{problemData.productName}}</span>
                    {{problemData.problemTitle}}
                </h3>

                <div v-if="item != null" class="first-edit-box">
                    <edit-problem-item :item="item"></edit-problem-item>
                </div>

                <div v-if="problemData.description && problemData.description.length > 0">
                    <h3 style="margin-top: 15px;font-size: 16px;font-weight: bold">描述:</h3>
                    <p class="description">{{problemData.description}}</p>
                </div>

                <div style="margin-top: 15px;margin-bottom: 15px" v-if="problemData.imageList && problemData.imageList.length>0">
                    <img v-for="item in problemData.imageList"
                         class="problem-image"
                         style=""
                         :src="item.imageName"
                         :key="item.imageName"
                         @click="showBigImage(item.imageName)" />
                </div>

                <div style="clear: both"></div>

                <div style="background-color: antiquewhite;padding: 10px;margin-top: 15px">
                    <el-row :gutter="10" style="margin-bottom: 15px">

                        <el-col :span="3">
                            <div class="item-title">项目</div>
                        </el-col>
                        <el-col :span="9">
                            <div class="item-value">{{problemData.projectName}}</div>
                        </el-col>

                        <el-col style="padding: 0" :span="3">
                            <div class="item-title">产品</div>
                        </el-col>
                        <el-col :span="9" style="padding: 0">
                            <div class="item-value">{{problemData.productName}}</div>
                        </el-col>
                    </el-row>

                    <el-row style="margin-bottom: 15px">
                        <el-col :span="3">
                            <div class="item-title">模块</div>
                        </el-col>
                        <el-col :span="9">
                            <div class="item-value">{{problemData.moduleName}}</div>
                        </el-col>

                        <el-col :span="3">
                            <div class="item-title">环境</div>
                        </el-col>
                        <el-col :span="9">
                            <div class="item-value">{{problemData.environment}}</div>
                        </el-col>
                    </el-row>

                    <el-row style="margin-bottom: 15px">
                        <el-col :span="3">
                            <div class="item-title">状态</div>
                        </el-col>
                        <el-col :span="9">
                            <problem-status-tag :status="problemData.status" />
                        </el-col>

                        <el-col :span="3">
                            <div class="item-title">优先级</div>
                        </el-col>
                        <el-col :span="9">
                            <problem-priority-tag :priority="problemData.priority" />
                        </el-col>
                    </el-row>

                    <el-row style="margin-bottom: 15px">
                        <el-col :span="3">
                            <div class="item-title">分配人</div>
                        </el-col>
                        <el-col :span="9">
                            <div class="item-value">{{problemData.inUserName}}</div>
                        </el-col>

                        <el-col :span="3">
                            <div class="item-title">处理人</div>
                        </el-col>
                        <el-col :span="9">
                            <div class="item-value">{{problemData.solveUserName}}</div>
                        </el-col>
                    </el-row>

                    <el-row style="margin-bottom: 15px">
                        <el-col :span="3">
                            <div class="item-title">计划版本</div>
                        </el-col>
                        <el-col :span="21">
                            <div class="item-value">{{problemData.planVersion}}</div>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="3">
                            <div class="item-title">标签</div>
                        </el-col>
                        <el-col :span="21">
                            <div>
                                <problem-tag v-for="item in problemData.tagList" :key="item.tagName" :color="item.tagColor">{{item.tagName}}</problem-tag>
                            </div>
                        </el-col>
                    </el-row>

                </div>

                <div style="clear: both;"></div>

                <div style="margin-top: 20px">
                    <p style="font-size: 16px;font-weight: bold;margin-bottom: 10px">操作日志:</p>
                    <hr style="background-color: transparent;border-bottom: 1px dashed #a9a9a9;border-top: none;margin-bottom: 10px"/>
                    <el-row v-for="item in editList" :key="item.editInfoID" style="margin-bottom: 10px">
                        <el-col :span="24">
                            <edit-problem-item v-on:imageClick="showBigImage" :item="item" :showImage="true"></edit-problem-item>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>

    import problemApi from '~/api/problemApi.js'
    import projectMemberApi from '~/api/projectMemberApi.js'
    import problemEditItem from '~/components/ProblemEditItem.vue'

    export default {
        props: {
            problemID: [ String, Number ],
            visible: [ Boolean ],
        },
        components: {
            'edit-problem-item': problemEditItem,
        },
        data() {
            return {
                currentImageName: '',
                imageSize: 'big',
                imageStyle: {
                    width: 'auto',
                },
                imageVisible: false,
                dialogVisible: false,
                problemData: {},
                editList: [],
                item: null,
                commentProblemVisible: false,
                assignVisible: false,
                commentProblemForm: {
                    content: '',
                },
                imageList: '',
                currentProjectMemberList: [],
                assignForm: {
                    userID: '',
                    content: '',
                },
                repairVisible: false,
                repairForm: {
                    userID: '',
                    content: '',
                },
                contentStyle: {
                    maxHeight: ''
                },
                auditVisible: false,
                auditForm: {
                    auditVersionName: '',
                    content: '',
                    auditPass: true,
                },
                auditTitle: '审核通过',
                openProblemVisible: false,
                openProblemForm: {
                    versionName: '',
                    userID: '',
                    dateRange: '',
                    priority: '',
                    content: '',
                },
                rejectProblemVisble: false,
                rejectProblemForm: {
                    rejectType: '1',
                    userID: '',
                    content: '',
                },
                closeProblemVisible: false,
                closeProblemForm: {
                    content: '',
                },
                deferProblemVisible: false,
                deferProblemForm: {
                    content: '',
                },
                editVisible: false,
            }
        },
        created() {
            this.contentStyle.maxHeight = (window.innerHeight - 270) + 'px'
        },
        watch: {
            visible: function (newVisible) {
                if (newVisible) {
                    this.fetchData()
                }
            }
        },
        methods: {
            handleClose() {
                this.$emit('update:visible', false)
                this.$emit('updateData')
                this.$store.commit('UPDATE_NOTICE', this.$store.getters.projectID)
            },
            fetchData() {
                problemApi.queryProblemById(this.problemID).then(res => {
                    if (res.data.data) {
                        this.dialogVisible = true
                        this.problemData = res.data.data
                        this.editList = res.data.data.editInfoList
                        if (this.problemData.editInfoList && this.problemData.editInfoList.length > 0) {
                            this.item = this.problemData.editInfoList[0]
                        } else {
                            this.item = null
                        }
                        console.log(res.data.data)
                    }
                })
            },
            handleCommentProblem() {
                this.commentProblemVisible = true
            },
            handleUploadImageSuccess(res, file, list) {
                this.imageList = list
            },
            handleImageRemove(file, list) {
                this.imageList = list
            },
            handleCommentProblemSave() {
                let data = {
                    content: this.commentProblemForm.content,
                    problemID: this.problemID,
                    imageList: this.$util.makeUploadImageList(this.imageList),
                }
                problemApi.commentProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.commentProblemForm.content = ''
                        this.imageList = []
                        this.$refs['uploadCommentImageField'].clearFiles()
                        this.commentProblemVisible = false
                        this.fetchData()
                    }
                })
            },
            handleAssignClick() {
                projectMemberApi.queryProjectMemberList(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.currentProjectMemberList = res.data.data
                        this.assignVisible = true
                    }
                })
            },
            handleAssignSaveClick() {
                let data = {
                    problemID: this.problemID,
                    userID: this.assignForm.userID,
                    content: this.assignForm.content,
                    imageList: this.$util.makeUploadImageList(this.imageList)
                }
                problemApi.assignProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.assignForm.userID = ''
                        this.assignForm.content = ''
                        this.imageList = []
                        this.assignVisible = false
                        this.$refs['uploadAssignImageField'].clearFiles()
                        this.fetchData()
                    }
                })
            },
            handleRepairClick() {
                projectMemberApi.queryProjectMemberList(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.repairForm.userID = this.problemData.inUser.toString()
                        this.currentProjectMemberList = res.data.data
                        this.repairVisible = true
                    }
                })
            },
            handleRepairSaveClick() {
                let data = {
                    problemID: this.problemID,
                    userID: this.repairForm.userID,
                    content: this.repairForm.content,
                    imageList: this.$util.makeUploadImageList(this.imageList),
                }
                problemApi.repairProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.repairForm.userID = ''
                        this.repairForm.content = ''
                        this.imageList = []
                        this.$refs['uploadRepairImageField'].clearFiles()
                        this.repairVisible = false
                        this.fetchData()
                    }
                })
            },
            handleAuditPassClick() {
                this.auditForm.auditPass = true
                this.auditTitle = '审核通过'
                this.auditVisible = true
            },
            handleAuditNoPassClick() {
                this.auditForm.auditPass = false
                this.auditTitle = '审核不通过'
                this.auditVisible = true
            },
            handleAuditPassSaveClick() {
                let data = {
                    auditPass: this.auditForm.auditPass,
                    problemID: this.problemID,
                    content: this.auditForm.content,
                    auditVersionName: this.auditForm.auditVersionName,
                    imageList: this.$util.makeUploadImageList(this.imageList),
                }
                problemApi.auditProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.auditForm.content = ''
                        this.auditForm.auditVersionName = ''
                        this.imageList = []
                        this.$refs['uploadAuditPassImageField'].clearFiles()
                        this.auditVisible = false
                        this.fetchData()
                    }
                })
            },
            handleOpenProblemClick() {
                projectMemberApi.queryProjectMemberList(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.currentProjectMemberList = res.data.data
                        this.openProblemVisible = true
                    }
                })
            },
            handleOpenProbleSaveClick() {
                let dateRange = this.openProblemForm.dateRange
                let data = {
                    problemID: this.problemID,
                    versionName: this.openProblemForm.versionName,
                    solveUserID: this.openProblemForm.userID,
                    priority: this.openProblemForm.priority,
                    content: this.openProblemForm.content,
                    imageList: this.$util.makeUploadImageList(this.imageList)
                }
                data['planBeginDate'] = dateRange[0] ? this.$moment(dateRange[0]).format() : ''
                data['planFinishDate'] = dateRange[1] ? this.$moment(dateRange[1]).format() : ''
                problemApi.openProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.openProblemForm = {
                            versionName: '',
                            userID: '',
                            dateRange: '',
                            priority: '',
                            content: '',
                        }
                        this.imageList = []
                        this.$refs['uploadOpenProblemImageField'].clearFiles()
                        this.openProblemVisible = false
                        this.fetchData()
                    }
                })
            },
            handleRejectProblemClick() {
                projectMemberApi.queryProjectMemberList(this.$store.getters.projectID).then(res => {
                    if (res.data.data) {
                        this.currentProjectMemberList = res.data.data
                        this.rejectProblemVisble = true
                    }
                })
            },
            handleRejectProblemSaveClick() {
                let data = {
                    rejectType: this.rejectProblemForm.rejectType,
                    problemID: this.problemID,
                    content: this.rejectProblemForm.content,
                    userID: this.rejectProblemForm.userID,
                    imageList: this.$util.makeUploadImageList(this.imageList),
                }
                problemApi.rejectProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.rejectProblemForm = {
                            rejectType: '1',
                            userID: '',
                            content: '',
                        }
                        this.$refs['uploadRejectProblemImageField'].clearFiles()
                        this.imageList = []
                        this.rejectProblemVisble = false
                        this.fetchData()
                    }
                })
            },
            handleCloseProblemClick() {
                this.closeProblemVisible = true
            },
            handleCloseProblemSaveClick() {
                console.log(this.closeProblemForm)
                let data = {
                    problemID: this.problemID,
                    content: this.closeProblemForm.content,
                    imageList: this.$util.makeUploadImageList(this.imageList),
                }
                problemApi.closeProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.closeProblemForm.content = ''
                        this.imageList = []
                        this.$refs['uploadCloseProblemImageField'].clearFiles()
                        this.closeProblemVisible = false
                        this.fetchData()
                    }
                })
            },
            handleDeferProblemClick() {
                this.deferProblemVisible = true
            },
            handleDeferProblemSaveClick() {
                let data = {
                    problemID: this.problemID,
                    content: this.deferProblemForm.content,
                    imageList: this.$util.makeUploadImageList(this.imageList),
                }
                problemApi.deferProblem(data).then(res => {
                    if (res.data.code === 0) {
                        this.deferProblemForm.content = ''
                        this.$refs['uploadDeferProblemImageField'].clearFiles()
                        this.imageList = []
                        this.deferProblemVisible = false
                        this.fetchData()
                    }
                })
            },
            showBigImage(imageName) {
                this.imageStyle.width = 'auto'
                this.imageSize = 'big'
                this.imageVisible = true
                this.currentImageName = imageName
            },
            changeImageSize() {
                if (this.imageSize === 'big') {
                    this.imageSize = 'large'
                    this.imageStyle.width = '100%'
                } else {
                    this.imageSize = 'big'
                    this.imageStyle.width = 'auto'
                }
            },
            handleEditProblemClick() {
                this.editVisible = true
            },
        },
    }
</script>

<style scoped>
    #problem-operation {
        background-color: #efefef;
        padding: 10px;
        border-radius: 5px;
    }
    #problem-operation button {
        margin-left: 5px;
    }
    #problem-detail-box {
        margin-top: 10px;
        overflow: scroll;
    }
    #problem-detail-box .tag {
        display: inline-block;
        background-color: #ca5d56;
        color: white;
        font-size: 13px;
        padding: 3px 10px;
    }
    #problem-detail-box .description {
        margin-top: 8px;
    }
    #problem-detail-box .first-edit-box {
        background-color: #efefef;
        margin-top: 15px;
        padding: 10px;
        border: 1px solid #20a0ff;
        border-radius: 5px;
    }
    #problem-detail-box .problem-image {
        max-height: 230px;
        display: block;
        margin-bottom: 10px;
        float: left;
        margin-right: 10px;
        border: 1px dotted #e9e9e9;
        cursor: zoom-in;
    }
    #problem-detail-box .item-title {
        font-weight: bold;
        color: #333333;
        min-width: 10px;
        min-height: 10px;
        padding: 0;
    }
    #problem-detail-box .item-value {
        font-weight: 200;
        color: #666;
        min-width: 10px;
        min-height: 10px;
    }
</style>
