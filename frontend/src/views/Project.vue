<template>
    <div style="background-color: #efefef">
        <HeaderBar></HeaderBar>

        <el-dialog title="修改模块名称" :visible.sync="editModuleVisible" top="50px">
            <el-input v-model="currentModuleName"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="saveModuleName" type="primary">保存模块名称</el-button>
            </span>
        </el-dialog>

        <el-dialog title="产品模块管理" :visible.sync="productModuleDialogVisible" top="50px">
            <p>产品 : <span style="color: deepskyblue">{{currentProductName}}</span></p>
            <div class="module-bar">
                <el-form ref="moduleForm" :rules="moduleFormRules" :model="moduleForm">
                    <el-form-item label="添加模块" prop="moduleName">
                        <el-input v-model="moduleForm.moduleName" placeholder="模块名称" style="width: 200px"></el-input>
                        <el-button @click="handleAddModule" style="margin-left: 15px" type="primary" icon="plus">添加模块</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <el-table border :data="moduleData" style="margin-top: 20px">
                <el-table-column prop="moduleName" label="模块"></el-table-column>
                <el-table-column label="操作" width="140px">
                    <template scope="scope">
                        <el-button type="danger"
                                   @click="handleDeleteModule(scope.row.moduleID)"
                                   size="small">删除</el-button>
                        <el-button @click="handleEditModule(scope.row.moduleID, scope.row.moduleName)"
                                   type="primary"
                                   size="small">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog @open="handleProjectDialogOpen"
                   @close="handleProjectDialogClose"
                   :title="projectDialogTitle"
                   top="50px"
                   :visible.sync="createProjectDialogVisible">

            <el-tabs @tab-click="handleProjectTabChange" v-model="createProjectTab">

                <el-tab-pane label="项目信息" name="info">

                    <el-form label-width="80px"
                             label-position="left"
                             style="margin-left: 15px;margin-right: 15px"
                             :rules="createProjectRules"
                             ref="createProjectForm"
                             :model="createProjectForm">

                        <el-form-item label="项目名称:" prop="projectName">
                            <el-input v-model="createProjectForm.projectName" placeholder="最多输入50个字符"></el-input>
                        </el-form-item>
                        <el-form-item label="项目描述:" prop="projectDescription">
                            <el-input v-model="createProjectForm.projectDescription" size="large" type="textarea" placeholder="最多输入200个字符"></el-input>
                        </el-form-item>
                        <el-form-item label="计划日期:" prop="dateRange">
                            <el-date-picker v-model="createProjectForm.dateRange" style="width: 100%" type="daterange" placeholder="选择日期范围"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="项目状态:" prop="status">
                            <el-select v-model="createProjectForm.status" style="width: 100%">
                                <el-option label="未开始" value="0" :key="0"></el-option>
                                <el-option label="已开始" value="1" :key="1"></el-option>
                                <el-option label="已竣工" value="2" :key="2"></el-option>
                                <el-option label="已终止" value="3" :key="3"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-button @click="handleSaveProject" type="primary" style="float: right">保存项目</el-button>
                    </el-form>

                </el-tab-pane>

                <el-tab-pane label="成员管理" :disabled="currentProjectID === ''" name="member">

                    <el-form style="margin-left: 15px;margin-right: 15px;margin-top: 15px;">
                        <el-form-item label="添加成员:">
                            <el-select v-model="addMemberID">
                                <el-option v-for="item in allUserList" :label="item.userName" :value="item.userID" :key="item.userID"></el-option>
                            </el-select>
                            <el-button type="primary" @click="handleAddMember" icon="plus" style="margin-left: 10px">添加</el-button>
                            <el-button type="primary">新建用户</el-button>
                        </el-form-item>
                    </el-form>

                    <div style="padding-left: 15px;padding-right: 15px">
                        <el-table border stripe :data="projectMemberList">
                            <el-table-column label="用户名">
                                <template scope="scope">
                                    <p>{{scope.row.displayName}}</p>
                                </template>
                            </el-table-column>
                            <el-table-column label="身份" width="100px">
                                <template scope="scope">
                                    <el-tag type="success" v-if="scope.row.isOwner">拥有者</el-tag>
                                    <el-tag type="warning" v-else-if="scope.row.isAdmin">管理员</el-tag>
                                    <el-tag type="primary" v-else>普通成员</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column width="180px" label="操作">
                                <template scope="scope">
                                    <div v-if="scope.row.isOwner === false">
                                        <el-button type="danger" @click="handleDeleteProjectMember(scope.row.userID)" size="small">删除</el-button>
                                        <el-button type="primary" size="small">设为管理员</el-button>
                                    </div>
                                    <div v-else>
                                        <p>拥有者不能操作</p>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>

                </el-tab-pane>

                <el-tab-pane label="产品管理" :disabled="currentProjectID === ''" name="product">
                    <el-form :rules="createProductFormRules"
                             ref="createProductForm"
                             :model="createProductForm"
                             style="margin-left: 15px;margin-right: 15px;margin-top: 15px;">
                        <el-form-item label="添加产品:" prop="productName">
                            <el-input v-model="createProductForm.productName" placeholder="请输入产品名称" style="width: 200px"></el-input>
                            <el-button @click="handleAddProduct" style="margin-left: 15px" icon="plus" type="primary">添加产品</el-button>
                        </el-form-item>
                    </el-form>

                    <el-table border :data="currentProductData">
                        <el-table-column label="产品名称" prop="productName"></el-table-column>
                        <el-table-column label="操作" width="170px">
                            <template scope="scope">
                                <el-button type="primary"
                                           size="small"
                                           @click="handleManageProductModule(scope.row.productID, scope.row.productName)">管理模块</el-button>
                                <el-button @click="handleDeleteProduct(scope.row.productID)"
                                           type="danger"
                                           size="small">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                </el-tab-pane>

                <el-tab-pane label="标签管理" :disabled="currentProjectID === ''" name="tag">
                    <div style="background-color: #efefef;padding-top: 20px;padding-bottom: 1px;border-radius: 5px">
                        <el-form style="margin-right: 15px;margin-left: 15px" label-width="80px" label-position="left">
                            <el-form-item label="添加标签">
                                <el-input v-model="tagForm.tagName" placeholder="标签名称" style="width: 140px;margin-right: 10px;float: left"></el-input>
                                <el-color-picker v-model="tagForm.tagColor" style="float: left;margin-right: 15px"></el-color-picker>
                                <el-button @click="handleCreateTag" icon="plus" type="primary">添加</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                    <el-table border :data="currentTagData" style="margin-top: 15px">
                        <el-table-column label="标签">
                            <template scope="scope">
                                <problem-tag :isActive="false" :color="scope.row.tagColor">{{scope.row.tagName}}</problem-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80px">
                            <template scope="scope">
                                <el-button @click="handleDeleteTag(scope.row.tagID)" type="danger" size="small">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </el-dialog>

        <el-dialog title="用户管理" :visible.sync="userManageDialogVisible" size="large" top="30px">
            <el-button type="primary" icon="plus" @click="handleAddUser">添加新用户</el-button>
            <div style="margin-top: 20px;background-color: #efefef;padding-top: 20px;padding-left: 10px;padding-right: 10px">
                <el-form :inline="true">
                    <el-form-item label="邮箱:">
                        <el-input v-model="searchUserForm.email"></el-input>
                    </el-form-item>
                    <el-form-item label="名称:">
                        <el-input v-model="searchUserForm.userName"></el-input>
                    </el-form-item>
                    <el-form-item label="电话:">
                        <el-input v-model="searchUserForm.cellphone"></el-input>
                    </el-form-item>
                    <el-button @click="fetchUserData" icon="search" type="primary">搜索</el-button>
                </el-form>
            </div>
            <div style="margin-top: 20px">
                <el-table border :data="userTableData" height="280">
                    <el-table-column label="ID" prop="userID"></el-table-column>
                    <el-table-column label="邮箱" prop="email"></el-table-column>
                    <el-table-column label="名称" prop="userName" ></el-table-column>
                    <el-table-column label="电话" prop="cellphone"></el-table-column>
                    <el-table-column label="描述" prop="description"></el-table-column>
                    <el-table-column label="状态" width="80px">
                        <template scope="scope">
                            <el-tag type="success" v-if="scope.row.status===1">启用</el-tag>
                            <el-tag type="danger" v-if="scope.row.status===2">禁用</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="90px">
                        <template scope="scope">
                            <el-button v-if="scope.row.status===1 && scope.row.role===2" size="small" @click="disableUser(scope.row.userID)" type="warning">禁用</el-button>
                            <el-button v-if="scope.row.status===2" type="success" size="small" @click="enableUser(scope.row.userID)">启用</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <el-pagination
                    layout="total, sizes, prev, pager, next"
                    class="page-box"
                    @current-change="handleUserCurrentPageChange"
                    @size-change="handleUserPageSizeChange"
                    :page-size="userPage.size"
                    :total="userPage.total">
            </el-pagination>
            <div style="clear: both"></div>
        </el-dialog>

        <el-dialog title="添加新用户" @close="createUserClose" :visible.sync="addUserDialogVisible">
            <el-form ref="createUserForm" :model="createUserForm" :rules="createUserRules" label-width="70px" label-position="left">
                <el-form-item label="邮箱:" prop="email">
                    <el-input v-model="createUserForm.email" placeholder="请输入用户邮箱"></el-input>
                </el-form-item>
                <el-form-item label="名称:" prop="userName">
                    <el-input v-model="createUserForm.userName" placeholder="请输入用户名称"></el-input>
                </el-form-item>
                <el-form-item label="电话:" prop="cellphone">
                    <el-input v-model="createUserForm.cellphone" placeholder="请输入用户手机号码"></el-input>
                </el-form-item>
                <el-form-item label="描述:" prop="description">
                    <el-input v-model="createUserForm.description" type="textarea" placeholder="可填写用户备注"></el-input>
                </el-form-item>
            </el-form>
            <p style="color: red">注意:创建的新用户登录账号为邮箱，初始密码也是邮箱,初始状态为启用状态</p>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleCreateUser" type="primary">保存用户</el-button>
            </span>
        </el-dialog>

        <div class="content">
            <div v-if="$store.getters.userRole === 1" style="min-width: 1000px;">
                <el-button type="primary"
                           icon="plus"
                           @click="handleAddProject" style="margin-top: 10px;margin-left: 10px;">
                    创建项目
                </el-button>
                <el-button type="primary" @click="handleUserManage" style="margin-top: 10px;margin-left: 10px">用户管理</el-button>
                <div style="clear: both"></div>
            </div>
            <div v-else>
                <el-button type="primary"
                           icon="plus"
                           @click="handleAddProject" style="margin-top: 10px;margin-left: 10px;">
                    创建项目
                </el-button>
                <div style="clear: both"></div>
            </div>

            <div class="main-box">
                <el-table stripe :row-style="projectTableRowStyle" @row-click="projectTableClick" class="project-main-box" border :data="projectData">
                    <el-table-column label="编号" prop="projectID" width="80px"></el-table-column>
                    <el-table-column label="项目名称" prop="projectName" width="160px"></el-table-column>
                    <el-table-column label="项目描述" prop="projectDescription"></el-table-column>
                    <el-table-column label="项目状态" width="100px">
                        <template scope="scope">
                            <el-tag v-if="scope.row.status===0" type="warning">未开始</el-tag>
                            <el-tag v-if="scope.row.status===1" type="success">已开始</el-tag>
                            <el-tag v-if="scope.row.status===2" type="primary">已竣工</el-tag>
                            <el-tag v-if="scope.row.status===3" type="danger">已终止</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="问题总数" width="100px">
                        <template scope="scope">
                            0
                        </template>
                    </el-table-column>
                    <el-table-column label="活跃问题" width="100px">
                        <template scope="scope">
                            0
                        </template>
                    </el-table-column>
                    <el-table-column v-if="$store.getters.userRole === 1 && $store.getters.openRegister === false" label="操作" width="90px">
                        <template scope="scope">
                            <el-button @click="handleEditProject(scope.row.projectID)" type="info" size="small">编辑</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="$store.getters.openRegister === true" label="操作" width="180px">
                        <template scope="scope">
                            <el-button v-if="$store.getters.userID === scope.row.inUser" @click="handleEditProject(scope.row.projectID)" type="info" size="small">编辑</el-button>
                            <el-button type="primary" size="small">进入控制台</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>

<script>

    import HeaderBar from '~/components/HeaderBar.vue'
    import LogoImage from '~/assets/logo.png'
    import userApi from '~/api/userApi.js'
    import projectApi from '~/api/projectApi.js'
    import projectMemberApi from '~/api/projectMemberApi.js'
    import projectProductApi from '~/api/projectProductApi.js'
    import projectTagApi from '~/api/projectTagApi.js'

    export default {
        name: 'Project',
        data() {
            return {
                logo: LogoImage,
                createProjectDialogVisible: false,
                createProjectTab: 'info',
                userManageDialogVisible: false,
                addUserDialogVisible: false,
                userTableData: [],
                createUserForm: {
                    email: '',
                    cellphone: '',
                    userName: '',
                    description: '',
                },
                searchUserForm: {
                    email: '',
                    userName: '',
                    cellphone: '',
                },
                userPage: {
                    num: 1,
                    size: 10,
                    total: 0,
                },
                createUserRules: {
                    email: [
                        { required: true, message: '用户邮箱必须填写', trigger: 'blur' },
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' },
                    ],
                    userName: [
                        { required: true, message: '用户名称必须填写', trigger: 'blur'}
                    ],
                },
                createProjectForm: {
                    projectName: '',
                    projectDescription: '',
                    dateRange: '',
                    status: '',
                    beginDate: '',
                    finishDate: '',
                },
                createProjectRules: {
                    projectName: [
                        { required: true, message: '项目名称必填', trigger: 'blur' }
                    ],
                    status: [
                        { required: true, message: '项目状态必填', trigger: 'blur, change' }
                    ],
                },
                projectData: [],
                allUserList: [],
                addMemberID: '',
                currentProjectID: '',
                projectMemberList: [],
                projectDialogTitle: '创建项目',
                createProductForm: {
                    productName: ''
                },
                createProductFormRules: {
                    productName: [
                        { required: true, message: '请填写产品名称', trigger: 'blur' },
                    ],
                },
                currentProductData: [],
                productModuleDialogVisible: false,
                currentProductID: '',
                currentProductName: '',
                moduleForm: {
                    moduleName: '',
                },
                moduleFormRules: {
                    moduleName: [
                        { required: true, message: '请填写模块名称', trigger: 'blur,change' },
                    ],
                },
                moduleData: [],
                editModuleVisible: false,
                currentModuleID: '',
                currentModuleName: '',
                tagForm: {
                    tagName: '',
                    tagColor: '#20a0ff',
                },
                currentTagData: [],
            }
        },
        components: {
            HeaderBar: HeaderBar
        },
        created() {
            this.fetchProjectData()
        },
        methods: {
            projectTableClick(row, event, column) {
                if (column.label !== '操作') {
                    this.$store.commit('SET_CURRENT_PROJECT', row)
                    this.$router.replace({ path: '/console' })
                }
            },
            projectTableRowStyle(object, b, c, d) {
                return {
                    cursor: 'pointer',
                }
            },
            projectForm() {
                return {
                    projectName: '',
                    projectDescription: '',
                    dateRange: '',
                    status: '',
                    beginDate: '',
                    finishDate: '',
                }
            },
            fetchProjectData() {
                projectApi.queryAllProject().then(res => {
                    if (res.data.data) {
                        console.log(this.$store.getters.userID)
                        console.log(res)
                        console.log(this.$store.getters.openRegister)
                        this.projectData = res.data.data
                    }
                })
            },
            handleConsole() {
                this.$router.replace({ path: 'console' })
            },
            handleAddProject() {
                if (this.$refs['createProjectForm']) {
                    this.$refs['createProjectForm'].resetFields()
                }
                this.createProjectForm = this.projectForm()
                this.currentProjectID = ''
                this.createProjectTab = 'info'
                this.createProjectDialogVisible = true
                this.projectDialogTitle = '创建项目'
            },
            handleUserManage() {
                this.fetchUserData()
            },
            fetchUserData() {
                let data = {
                    email: this.searchUserForm.email,
                    cellphone: this.searchUserForm.cellphone,
                    userName: this.searchUserForm.userName,
                    pageNum: this.userPage.num,
                    pageSize: this.userPage.size,
                }
                userApi.queryUserList(data).then(res => {
                    if (res.data.data) {
                        this.userTableData = res.data.data.list
                        this.userPage.total = res.data.data.total
                        if (this.userManageDialogVisible === false) {
                            this.userManageDialogVisible = true
                        }
                    }
                })
            },
            handleAddUser() {
                this.addUserDialogVisible = true
            },
            handleCreateUser() {
                this.$refs['createUserForm'].validate((valid) => {
                    if (valid) {
                        let data = this.createUserForm
                        userApi.createUser(data).then(res => {
                            if (res.data.code === 0) {
                                this.addUserDialogVisible = false
                                this.fetchUserData()
                            }
                        })
                    } else {
                        return false
                    }
                })

            },
            createUserClose() {
                this.$refs['createUserForm'].resetFields()
            },
            handleUserPageSizeChange(val) {
                this.userPage.size = val
                this.fetchUserData()
            },
            handleUserCurrentPageChange(val) {
                this.userPage.num = val
                this.fetchUserData()
            },
            disableUser(userID) {
                userApi.disable(userID).then(res => {
                    if (res.data.code === 0) {
                        this.fetchUserData()
                    }
                })
            },
            enableUser(userID) {
                userApi.enable(userID).then(res => {
                    if (res.data.code === 0) {
                        this.fetchUserData()
                    }
                })
            },
            handleSaveProject() {
                this.$refs['createProjectForm'].validate((valid) => {
                    if (valid) {
                        let form = this.createProjectForm
                        let dateRange = form.dateRange
                        let beginDate = dateRange[0] ? this.$moment(dateRange[0]).format() : ''
                        let finishDate = dateRange[1] ? this.$moment(dateRange[1]).format() : ''
                        let data = {
                            projectName: form.projectName,
                            projectDescription: form.projectDescription,
                            status: form.status,
                            beginDate: beginDate,
                            finishDate: finishDate
                        }
                        if (this.currentProjectID === '') {
                            projectApi.createProject(data).then(res => {
                                if (res.data.code === 0) {
                                    this.currentProjectID = res.data.data.projectID
                                    this.createProjectTab = 'member'
                                }
                            })
                        } else {
                            data['projectID'] = this.currentProjectID
                            projectApi.updateProject(data).then(res => {
                                if (res.data.code === 0) {
                                    this.createProjectTab = 'member'
                                }
                            })
                        }
                    } else {
                        return false
                    }
                })
            },
            handleProjectDialogOpen() {
                userApi.getAllUserList().then(res => {
                    if (res.data.data) {
                        this.allUserList = res.data.data
                    }
                })
            },
            handleAddMember() {
                if (this.addMemberID !== null && this.addMemberID !== '') {
                    for (let index in this.allUserList) {
                        if (this.allUserList[index].userID === this.addMemberID) {
                            let userName = this.allUserList[index].userName
                            let data = {
                                displayName: userName,
                                isAdmin: false,
                                isOwner: false,
                                projectID: this.currentProjectID,
                                userID: this.addMemberID,
                            }
                            projectMemberApi.addMember(data).then(res => {
                                if (res.data.code === 0) {
                                    this.fetchCurrentMemberData()
                                    this.addMemberID = ''
                                }
                            })
                        }
                    }
                } else {
                    this.$message.error('请选择用户')
                }
            },
            handleEditProject(projectID) {
                projectApi.queryProjectById(projectID).then(res => {
                    if (res.data.data) {
                        this.createProjectTab = 'info'
                        if (this.$refs['createProjectForm']) {
                            this.$refs['createProjectForm'].resetFields()
                            this.createProjectForm.status = ''
                        }
                        this.currentProjectID = res.data.data.projectID
                        this.createProjectForm.status = res.data.data.status.toString()
                        this.createProjectForm.projectName = res.data.data.projectName
                        this.createProjectForm.projectDescription = res.data.data.projectDescription
                        this.createProjectForm.dateRange = [
                                new Date(res.data.data.beginDate),
                                new Date(res.data.data.finishDate),
                        ]
                        this.createProjectForm.beginDate = res.data.data.beginDate
                        this.createProjectForm.finishDate = res.data.data.finishDate
                        this.projectDialogTitle = '编辑项目'
                        this.createProjectDialogVisible = true
                    }
                })
            },
            handleProjectDialogClose() {

            },
            handleProjectTabChange(val) {
                if (val.name === 'member') {
                    this.fetchCurrentMemberData()
                }
                if (val.name === 'product') {
                    this.$refs['createProductForm'].resetFields()
                    this.fetchCurrentProductData()
                }
                if (val.name === 'tag') {
                    this.fetchCurrentTagData()
                }
            },
            fetchCurrentMemberData() {
                projectMemberApi.queryProjectMemberList(this.currentProjectID).then(res => {
                    if (res.data.data) {
                        this.projectMemberList = res.data.data
                    }
                })
            },
            handleDeleteProjectMember(userID) {
                projectMemberApi.deleteMember(this.currentProjectID, userID).then(res => {
                    if (res.data.code === 0) {
                        this.fetchCurrentMemberData()
                    }
                })
            },
            handleAddProduct() {
                this.$refs['createProductForm'].validate((valid) => {
                    if (valid) {
                        let data = {
                            productName: this.createProductForm.productName,
                            projectID: this.currentProjectID
                        }
                        projectProductApi.createProduct(data).then(res => {
                            if (res.data.code === 0) {
                                this.createProductForm.productName = ''
                                this.$refs['createProductForm'].resetFields()
                                this.fetchCurrentProductData()
                            }
                        })
                    } else {
                        return false
                    }
                })
            },
            fetchCurrentProductData() {
                projectProductApi.queryProductList(this.currentProjectID).then(res => {
                    if (res.data.data) {
                        this.currentProductData = res.data.data
                    }
                })
            },
            handleManageProductModule(productID, productName) {
                if (this.$refs['moduleForm']) {
                    this.$refs['moduleForm'].resetFields()
                }
                this.currentProductID = productID
                this.currentProductName = productName
                this.productModuleDialogVisible = true
                this.fetchModuleData()
            },
            handleAddModule() {
                this.$refs['moduleForm'].validate((valid) => {
                    if (valid) {
                        let data = {
                            moduleName: this.moduleForm.moduleName,
                            productID: this.currentProductID
                        }
                        projectProductApi.createModule(data).then(res => {
                            if (res.data.code === 0) {
                                this.$refs['moduleForm'].resetFields()
                                this.fetchModuleData()
                            }
                        })
                    } else {
                        return false
                    }
                })
            },
            fetchModuleData() {
                projectProductApi.queryModuleList(this.currentProductID).then(res => {
                    if (res.data.data) {
                        this.moduleData = res.data.data
                    }
                })
            },
            handleDeleteModule(moduleID) {
                projectProductApi.deleteModule(moduleID).then(res => {
                    if (res.data.code === 0) {
                        this.fetchModuleData()
                    }
                })
            },
            handleEditModule(moduleID, moduleName) {
                this.currentModuleID = moduleID
                this.currentModuleName = moduleName
                this.editModuleVisible = true
            },
            saveModuleName() {
                let data = {
                    moduleName: this.currentModuleName,
                    moduleID: this.currentModuleID,
                    productID: this.currentProductID,
                }
                projectProductApi.updateModule(data).then(res => {
                    if (res.data.code === 0) {
                        this.fetchModuleData()
                        this.editModuleVisible = false
                    }
                })
            },
            handleDeleteProduct(productID) {
                projectProductApi.deleteProduct(productID).then(res => {
                    if (res.data.code === 0) {
                        this.fetchCurrentProductData()
                    }
                })
            },
            handleCreateTag() {
                if (this.tagForm.tagName.length === 0) {
                    this.$message({
                        message: '请填写tag名称',
                        type: 'warning',
                    })
                    return
                }
                let data = {
                    projectID: this.currentProjectID,
                    tagName: this.tagForm.tagName,
                    tagColor: this.tagForm.tagColor,
                }
                projectTagApi.createTag(data).then(res => {
                    if (res.data.code === 0) {
                        this.tagForm.tagName = ''
                        this.tagForm.tagColor = '#20a0ff'
                        this.fetchCurrentTagData()
                    }
                })
            },
            fetchCurrentTagData() {
                projectTagApi.queryProjectTagList(this.currentProjectID).then(res => {
                    if (res.data.data) {
                        this.currentTagData = res.data.data
                    }
                })
            },
            handleDeleteTag(tagID) {
                projectTagApi.deleteTag(tagID).then(res => {
                    if (res.data.code === 0) {
                        this.fetchCurrentTagData()
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .content {
        width: 100%;
        min-width: 1000px;
    }
    .main-box {
        min-width: 940px;
        padding: 10px;
    }
    .page-box {
        float: right;
        margin-top: 10px;
        margin-right: -5px;
    }
    .project-main-box {
    }
    .module-bar {
        background-color: #efefef;
        margin-top: 15px;
        padding-top: 18px;
        padding-bottom: 1px;
        padding-left: 10px;
        border-radius: 5px;
    }
</style>
