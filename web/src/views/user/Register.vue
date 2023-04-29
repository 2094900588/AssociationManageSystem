<template>
    <div class="wrapper">
        <div style="margin: 100px auto; background-color: #fff; width: 30%; height: 70%; padding: 20px; border-radius: 10px; margin-top: 10%;">
            <div style="margin: 5px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
            <el-form :model="user" :rules="rules" ref="userFrom">
                <el-form-item prop="username">
                    <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input  placeholder="请输入密码" prop="password" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input  placeholder="请确认密码" prop="password" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item prop="nickname">
                    <el-input  placeholder="请输入昵称" prop="nickname" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-s-custom"  v-model="user.nickname"></el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input  placeholder="请输入邮箱" prop="email" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-message"  v-model="user.email"></el-input>
                </el-form-item>
                <el-form-item prop="phone">
                    <el-input  placeholder="请输入电话" prop="phone" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-phone"  v-model="user.phone"></el-input>
                </el-form-item>
                <el-form-item prop="address">
                    <el-input  placeholder="请输入地址" prop="address" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-s-flag"  v-model="user.address"></el-input>
                </el-form-item>
                <el-form-item prop="name">
                    <el-input  placeholder="请输入姓名" prop="name" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-s-flag"  v-model="user.name"></el-input>
                </el-form-item>
                <el-form-item prop="studentid">
                    <el-input  placeholder="请输入学号" prop="studentid" size="medium" style="margin: 5px 0;" prefix-icon="el-icon-s-flag"  v-model="user.studentid"></el-input>
                </el-form-item>
            </el-form>
            <div style="margin: 10px 0; text-align: center">
                <el-button type="primary" size="medium" autocomplete="off" @click="login">注册</el-button>
                <el-button type="warning" size="medium" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
    name: "Register",
    data(){
        return{
        user: {},
        rolesys: [],
        roles: [],
        clubs: [],
        rules:{
            username:[
                { required: true, message: '请输入用户名', trigger: 'blur' },
                { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
            ],
            password:[
                { required: true, message: '请输入密码', trigger: 'blur' },
                { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
            ],
            confirmPassword:[
                { required: true, message: '请输入密码', trigger: 'blur' },
                { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
            ]
        }
        }
    },
    methods:{
        login(){
                this.$refs['userFrom'].validate((valid) => {
                    if (valid) {//表单校验合法
                        if (this.user.password !== this.user.confirmPassword){
                            this.$message.error("两次输入密码不一致");
                            return false
                        }
                        this.request.post("/user/register",this.user).then(res =>{
                            if (res.code === '200'){
                                this.$router.push("/login");
                                this.$message.success("注册成功，请登录！");
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
.wrapper{
    height: 100vh;
    background: url('/src/assets/login.bmp') !important;
    overflow: hidden;
}
</style>
