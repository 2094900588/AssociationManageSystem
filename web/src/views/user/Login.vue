<template>
    <div class="wrap">
        <!-- 展示效果 -->
        <div class="effect">
            <div class="container">
                <div class="screen">
                    <div class="screen__content">


                        <form class="login" @keydown="getkey">
                            <div class="login_field">
                                <input type="text" class="login_input" v-model="username" required="required">
                                <span>账户</span>
                            </div>
                            <div class="login_field">
                                <input type="password" class="login_input" v-model="password" required="required">
                                <span class="button_text">密码</span>
                            </div>
                            <span style="color: red;">{{ err_msg }}</span>
                            <button type="button" class="button login_submit" @click="login">
                                登录
                            </button>
                        </form>
                        <div class="social-login">
                            <h3>去注册</h3>
                        </div>

                    </div>
                    <div class="screen__background">
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </div>
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
            username: "",
            password: "",
            err_msg: ""
            // rules: {
            //     username: [
            //         { required: true, message: '请输入用户名', trigger: 'blur' },
            //         { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
            //     ],
            //     password: [
            //         { required: true, message: '请输入密码', trigger: 'blur' },
            //         { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
            //     ]
            // }
        }
    },
    methods: {
        ...mapActions(['getUserInfo']),
        getkey(e) {
            if (e.keyCode === 13) this.login();
        },
        login() {
            if (this.username.length > 10 || this.username.length < 3) {
                this.err_msg = "用户名长度在 3 到 10 个字符"
                return false
            }
            if (this.password.length > 18 || this.password.length < 5) {
                this.err_msg = "密码长度在 5 到 18 个字符"
                return false
            }
            let data = {
                username: this.username,
                password: this.password
            }
            this.request.post("/user/login", data).then(res => {
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
    }
}

</script>

<style scoped>
.wrap {
    height: 100vh;
    background: url('/src/assets/login.bmp') !important;
    overflow: hidden;
}

body {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-rendering: optimizeLegibility;
    background: #121212;
    color: white;
    font-family: AlimamaShuHeiTi-Bold, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Ubuntu, "Helvetica Neue", sans-serif;
    font-weight: 400;
    font-style: normal;
    text-transform: initial;
    letter-spacing: initial;
    min-height: 704px
}

button {
    font-family: AlimamaShuHeiTi-Bold
}

* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box
}

h1,
h2,
h3,
h4,
h5,
h6 {
    font-weight: 500
}

a {
    color: inherit;
    text-decoration: none;
    cursor: pointer
}

*::-moz-selection {
    background: rgba(255, 255, 255, .99);
    color: #121212
}

*::selection {
    background: rgba(255, 255, 255, .99);
    color: #121212
}


.wrap {
    align-content: flex-start;
}

img {
    width: 100%;
}

.effect {
    width: 100%;
    /* display: flex;
    flex-wrap: wrap;
    align-content: center;
    justify-content: center; */
    padding: 0px 20px;
    opacity: 0.95;
}

.container {
    /* display: flex;
    align-items: center;
    justify-content: center; */
    border-radius: 5px 40px;
    overflow: hidden;
    position: fixed;
    right: 10vw;
    top: 10vh;
}

.screen {
    background: linear-gradient(90deg, #56d667, #5bd160);
    position: relative;
    height: 600px;
    width: 360px;
}

.screen__content {
    z-index: 1;
    position: relative;
    height: 100%;

}

.screen__background {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 0;
    -webkit-clip-path: inset(0 0 0 0);
    clip-path: inset(0 0 0 0);
}

.screen__background span {
    transform: rotate(45deg);
    position: absolute;
}

.screen__background span:first-child {
    height: 520px;
    width: 520px;
    background: #FFF;
    top: -50px;
    right: 120px;
    border-radius: 0 72px 0 0;
    z-index: 1;
}

.screen__background span:nth-child(2) {
    height: 220px;
    width: 220px;
    background: #56d652;
    top: -172px;
    right: 0;
    border-radius: 32px;
}

.screen__background span:nth-child(3) {
    height: 540px;
    width: 190px;
    background: linear-gradient(240deg, #3de667, #32db65, #6be067, #62e262, #4dd149);
    top: -24px;
    right: 0;
    border-radius: 32px;
}

.screen__background span:last-child {
    height: 400px;
    width: 200px;
    background: #54e273;
    top: 420px;
    right: 50px;
    border-radius: 60px;
}



.login {
    width: 100%;
    padding: 30px;
    padding-top: 40%;
}

.login_field {
    padding: 20px 0px;
    position: relative;
}

.login_input {
    border: none;
    border-bottom: 2px solid #D1D1D4;
    background: none;
    padding: 10px 0px;
    font-weight: 700;
    width: 75%;
    transition: .2s;
    outline: none;
}

.login_field span {
    color: rgb(159, 159, 159);
    position: absolute;
    left: 0;
    padding: 9px 0px;
    text-transform: uppercase;
    text-transform: uppercase;
    letter-spacing: 0.1em;
    transition: .5s;
    pointer-events: none;
    font-size: 1em;
}

.login_input:valid~span,
.login_input:focus~span {
    color: #6be05c;
    transform: translateY(-25px);
    font-size: 0.85em;
}

.login_input:valid,
.login_input:focus {
    border-bottom: 2px solid #5be066;
}

.login_submit {
    background: #fff;
    font-size: 17px;
    margin-top: 30px;
    padding: 5px 20px;
    border-radius: 26px;
    border: none;
    text-transform: uppercase;
    font-weight: 700;
    display: flex;
    align-items: center;
    width: 75%;
    color: #5ee965;
    box-shadow: 0px 0px 20px #51dd51;
    cursor: pointer;
    transition: .2s;
    height: 40px;
    line-height: 40px;
    justify-content: center;
    animation: animate 2s linear infinite;
}


.social-login {
    position: absolute;
    height: 140px;
    width: 160px;
    text-align: center;
    bottom: 0px;
    right: 0px;
    color: #fff;
}

.social-login.login_submit:hover {
    transform: scale(1.2, 1.2);
    /* transition: all 0.6s; */
}
</style>