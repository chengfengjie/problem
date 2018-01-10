<template>
    <div>
        <div style="margin-left: auto;margin-right: auto;margin-top: 120px;width: 260px;position: relative">
            <el-form>
                <el-form-item>
                    <el-input v-model="email" placeholder="请输入账号">
                        <template slot="prepend">账号:</template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-input type="password" v-model="password" placeholder="请输入密码">
                        <template slot="prepend">密码:</template>
                    </el-input>
                </el-form-item>
                <el-button @click="handleLogin" type="primary" style="width: 100%">登录</el-button>
                <el-button v-if="openRegister" @click="handleRegister" style="width: 100%;margin-top: 10px;margin-left: 0">注册</el-button>
            </el-form>
        </div>

        <el-dialog v-model="registerVisible" title="欢迎注册" top="60px">

            <el-form label-width="100px" :model="registerForm" ref="registerForm" label-position="left" :rules="registerUserRules">
                <el-form-item label="邮箱:" required prop="email">
                    <el-input v-model="registerForm.email" placeholder="请输入您的邮箱"></el-input>
                </el-form-item>
                <el-form-item label="名称:" required prop="name">
                    <el-input v-model="registerForm.name" placeholder="请输入您的名称"></el-input>
                </el-form-item>
                <el-form-item label="密码:" required prop="password">
                    <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="重复密码:" required prop="repassword">
                    <el-input type="password" v-model="registerForm.repassword" placeholder="请再次输入密码"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phonNumber">
                    <el-input v-model="registerForm.phoneNumber" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="description">
                    <el-input v-model="registerForm.description" type="textarea" placeholder="请输入您的备注"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="registerVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSaveRegister">注册</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    import background from '~/assets/demo.jpg'
    import userApi from '~/api/userApi.js'
    import businessCommon from '~/api/businessCommon.js'

    export default {
        name: 'Login',
        data() {
            return {
                background: background,
                email: '',
                password: '',
                openRegister: false,
                registerVisible: false,
                registerForm: {
                    email: '',
                    name: '',
                    password: '',
                    repassword: '',
                    phoneNumber: '',
                    description: '',
                },
                registerUserRules: {
                    email: [
                        { required: true, message: '用户邮箱必须填写', trigger: 'blur' },
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' },
                    ],
                    name: [
                        { required: true, message: '用户名称必须填写', trigger: 'blur'},
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur'},
                    ],
                    repassword: [
                        { required: true, message: '重复密码必填', trigger: 'blur'},
                    ],
                },
            }
        },
        created() {
            this.fetchConfig()
        },
        methods: {
            fetchConfig() {
                businessCommon.getAppConfig().then(res => {
                    if (res.data) {
                        this.openRegister = res.data.openRegister
                    }
                })
            },
            handleLogin() {
                let data = {
                    email: this.email,
                    password: this.password,
                }
                userApi.login(data).then(res => {
                    console.log(res)
                    if (res.data.code === 0) {
                        this.$store.commit('SET_CURRENT_USER', res.data.data)
                        if (this.$store.getters.projectID) {
                            this.$router.replace({'path': '/console'})
                        } else {
                            this.$router.replace({'path': '/project'})
                        }
                    }
                })
            },
            handleRegister() {
                if (this.$refs['registerForm']) {
                    this.$refs['registerForm'].resetFields()
                }
                this.registerVisible = true
            },
            handleSaveRegister() {
                console.log(this.registerForm)
                this.$refs['registerForm'].validate(valid => {
                    if (valid) {
                        let data = {
                            email: this.registerForm.email,
                            userName: this.registerForm.name,
                            password: this.registerForm.password,
                            avatar: '',
                            cellphone: this.registerForm.phoneNumber,
                            description: this.registerForm.description,
                        }
                        userApi.registerUser(data).then(res => {
                            if (res.data.code === 0) {
                                this.registerVisible = false
                            }
                        })
                    } else {
                        return false
                    }
                })
            },
        },
    }
</script>
