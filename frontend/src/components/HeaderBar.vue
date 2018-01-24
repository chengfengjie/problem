<template>
    <div class="nav-bar">

        <div class="nav-logo">
            <img src="http://chengfj.oss-cn-hangzhou.aliyuncs.com/problem/grape.png" alt="">
            <span>NOProblem</span>
        </div>
        <div class="nav-content" style="float: left">
            <slot></slot>
        </div>
        <div v-if="showSearchBar" style="width: 300px;height: 28px;float: left;margin-left: 50px;margin-top: 11px;border: 1px solid #efefef;border-radius: 15px;">
            <input v-model="searchText" class="search-input-box" type="text" placeholder="编号、标题、模块、描述">
            <i style="color: white;font-size: 18px;display: inline-block;margin-top: 5px;cursor: pointer" class="el-icon-search" @click="handleClickSearch"></i>
        </div>
        <div class="nav-info-box">
            <el-dropdown @command="handleLogout">
                <span style="color: white;
                width: 100px;display: block;cursor: pointer;
                text-align: right;height: 50px;overflow: hidden;
                text-overflow: ellipsis;white-space: nowrap" class="el-dropdown-link;">
                    {{$store.getters.userName}}
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
                    <el-dropdown-item command="updateInfo">修改资料</el-dropdown-item>
                    <el-dropdown-item command="logout">注销登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>

        <el-dialog title="修改密码" :visible.sync="updatePasswordVisible" top="30px">
            <el-form :rules="updatePwdFormRules" ref="updatePwdForm" :model="updatePwdForm" label-width="100px" label-position="left">
                <el-form-item prop="originalPassword" label="原始密码">
                    <el-input type="password" placeholder="请输入原始密码" v-model="updatePwdForm.originalPassword"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="新密码">
                    <el-input type="password" placeholder="请输入新密码" v-model="updatePwdForm.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" label="确认密码">
                    <el-input type="password" placeholder="确认新密码" v-model="updatePwdForm.rePassword"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleSaveUpdatePassword" type="primary">保存</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修改资料" :visible.sync="updateInfoVisible" top="30px">
            <el-form label-width="80px" label-position="left" ref="updateInfoForm" :model="updateInfoForm" :rules="updateInfoFormRules">
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="updateInfoForm.email" disabled></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="userName">
                    <el-input v-model="updateInfoForm.userName" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                    <el-input v-model="updateInfoForm.phone" placeholder="请输入您的手机号码"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="updateInfoForm.description" placeholder="请输入描述" :rows="5" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleSaveUserInfo">保存</el-button>
            </span>
        </el-dialog>

    </div>
</template>
<script>
    import userApi from '~/api/userApi.js'
    export default {
        props: {
            showSearchBar: {
                type: Boolean,
                default: false
            }
        },
        name: 'HeaderBar',
        data() {
            return {
                updatePasswordVisible: false,
                updatePwdForm: this.updatePwdFormCreate(),
                updatePwdFormRules: {
                    originalPassword: [
                        { required: true, message: '原始密码必填', trigger: 'blur'},
                    ],
                    password: [
                        { required: true, message: '密码必填', trigger: 'blur'},
                    ],
                    rePassword: [
                        { required: true, message: '确认密码必填', trigger: 'blur'},
                    ],
                },
                updateInfoVisible: false,
                updateInfoForm: {
                    userName: '',
                    phone: '',
                    description: '',
                    email: '',
                },
                updateInfoFormRules: {
                    userName: [
                        { required: true, message: '用户名称必填', trigger: 'change blur'}
                    ]
                },
                searchText: '',
            }
        },
        methods: {
            updatePwdFormCreate() {
                return {
                    originalPassword: '',
                    passowrd: '',
                    rePassword: '',
                }
            },
            handleLogout(val) {
                if (val === 'logout') {
                    this.logout()
                } else if (val === 'updatePassword') {
                    this.updatePasswordVisible = true
                } else if (val === 'updateInfo') {
                    userApi.userInfo().then(res => {
                        if (res.data.code === 0) {
                            this.updateInfoForm.userName = res.data.data.userName
                            this.updateInfoForm.phone = res.data.data.cellphone
                            this.updateInfoForm.description = res.data.data.description
                            this.updateInfoForm.email = res.data.data.email
                            this.updateInfoVisible = true
                        }
                    })
                }
            },
            logout() {
                userApi.logout().then(res => {
                    if (res.data.code === 0) {
                        this.$store.commit('DEL_CURRENT_USER')
                        this.$store.commit('DEL_CURRENT_PROJECT')
                        this.$router.replace({ path: '/login'} )
                    }
                })
            },
            handleSaveUpdatePassword() {
                this.$refs['updatePwdForm'].validate(valid => {
                    if (valid) {
                        if (this.updatePwdForm.password === this.updatePwdForm.rePassword) {
                            let data = {
                                originalPassword: this.updatePwdForm.originalPassword,
                                password: this.updatePwdForm.password,
                            }
                            userApi.updatePassword(data).then(res => {
                                if (res.data.code === 0) {
                                    this.updatePwdForm = this.updatePwdFormCreate()
                                    this.updatePasswordVisible = false
                                    this.logout()
                                }
                            })
                        } else {
                            this.$message({
                                message: '两次密码不一致',
                                type: 'warning',
                            })
                        }
                    } else {
                        return false
                    }
                })
            },
            handleSaveUserInfo() {
                this.$refs['updateInfoForm'].validate(valid => {
                    if (valid) {
                        let data = {
                            userName: this.updateInfoForm.userName,
                            cellphone: this.updateInfoForm.phone,
                            description: this.updateInfoForm.description,
                        }
                        userApi.updateInfo(data).then(res => {
                            if (res.data.code === 0) {
                                this.$store.commit('SET_CURRENT_USER', res.data.data)
                                this.$refs['updateInfoForm'].resetFields()
                                this.updateInfoVisible = false
                            }
                        })
                    } else {
                        return false
                    }
                })
            },
            handleClickSearch() {
                if (this.searchText.includes('.') || this.searchText == '') {
                    this.$emit('search', false, this.searchText)
                } else if (isNaN(this.searchText)) {
                    this.$emit('search', false, this.searchText)
                } else {
                    this.$emit('search', true, this.searchText)
                }
            },
        },
    }
</script>
<style scoped>
    .nav-bar {
        width: 100%;
        min-width: 1000px;
        background-color: #4d9ef8;
        height: 50px;
    }
    .nav-logo {
        width: 120px;
        height: 50px;
        margin-left: 20px;
        line-height: 50px;
        text-align: center;
        color: white;
        float: left;
    }
    .nav-logo img {
        display: block;
        height: 24px;
        margin-top: 13px;
        float: left;
    }
    .nav-logo span {
        font-size: 20px;
    }
    .nav-content {
        height: 50px;
        margin-left: 90px;
        max-width: 600px;
        float: left;
    }
    .nav-info-box {
        background-color: transparent;
        height: 50px;
        float: right;
        margin-right: 10px;
        line-height: 50px;
        color: white;
    }
    .search-input-box {
        background-color: transparent;
        border: none;
        display: block;
        width: 260px;
        height: 24px;
        margin-top: 2px;
        margin-left: 11px;
        color: white;
        font-size: 14px;
        font-weight: 100;
        line-height: 24px;
        float: left;
    }
    :-moz-placeholder {
        color: white;
        opacity: 0.7;
        font-size: 14px;
        font-weight: 100;
    }
    ::-moz-placeholder {
        color: white;
        opacity: 0.7;
        font-size: 14px;
        font-weight: 100;
    }
    input:-ms-input-placeholder {
        color: white;
        opacity: 0.7;
        font-size: 14px;
        font-weight: 100;
    }
    input::-webkit-input-placeholder {
        color: white;
        opacity: 0.6;
        font-size: 14px;
        font-weight: 100;
    }
</style>
