<template>
    <div class="wrapper">
        <div
            style="margin: auto;position: fixed;right: 0;left: 0;top: 0;bottom: 0; background-color: #fff; width: 450px; height: 400px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登录</b></div>
            <el-form :model="user" :rules="rules" ref="userFrom">
                <el-form-item label="用户名" prop="username">
                    <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-user"
                        v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input prop="password" size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock" show-password
                        v-model="user.password"></el-input>
                </el-form-item>
            </el-form>
            <div style="margin: 10px 0; text-align: center">
                <el-button type="primary" size="medium" autocomplete="off" @click="login">登录</el-button>
                <el-button type="warning" size="medium" autocomplete="off" @click="$router.push('/register')">注册</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
    name: "Login",
    data() {
        return {
            user: {},
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        ...mapActions(['getUserInfo']),
        login() {
            this.$refs['userFrom'].validate((valid) => {
                if (valid) {//表单校验合法
                    this.request.post("/user/login", this.user).then(res => {
                        if (res.code === '200') {
                            localStorage.setItem("user", JSON.stringify(res.data)); //存储用户信息到浏览器
                            this.getUserInfo();
                            this.$router.push({ name: "主页" });
                            this.$message.success("登录成功，欢迎使用本系统！");
                        }
                        else {
                            this.$message.error(res.msg);
                        }
                    })
                }
            });
        }
    }
}
</script>

<style>
.wrapper {
    height: 100vh;
    background: url('/src/assets/login.bmp') !important;
    overflow: hidden;
}
</style>
