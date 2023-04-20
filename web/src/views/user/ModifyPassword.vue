<template>
    <div>
        <div style="width:30vw;margin: auto;">
            <el-form :model="pwd" :rules="rules" size="small" ref="pwdFrom">
                <!-- <el-form-item label="原密码">
                    <el-input v-model="pwd.oldpassword" type="text" autocomplete="off"></el-input>
                </el-form-item> -->
                <el-form-item label="密码" prop="oldpassword">
                    <el-input prop="oldpassword" size="medium" prefix-icon="el-icon-lock" show-password
                        v-model="pwd.oldpassword"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newpassword">
                    <el-input prop="newpassword" size="medium" prefix-icon="el-icon-lock" show-password
                        v-model="pwd.newpassword"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="secondpassword">
                    <el-input prop="secondpassword" size="medium" prefix-icon="el-icon-lock" show-password
                        v-model="pwd.secondpassword"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="save">修 改</el-button>
            </div>

        </div>
    </div>
</template>


<script>

import userapi from '@/api/page/user'
export default {
    data() {
        return {
            pwd: {
                id: "",
                oldpassword: "",
                newpassword: "",
                secondpassword: "",
            },
            rules: {
                oldpassword: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
                ],
                newpassword: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
                ],
                secondpassword: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        save() {
            this.$refs['pwdFrom'].validate((valid) => {
                if (valid) {//表单校验合法
                    if (this.pwd.newpassword !== this.pwd.secondpassword) {
                        this.$message.success("两次密码不一致")
                    }
                    userapi.modifypassword(this.pwd).then(res => {
                        if (res.code === '200') {
                            this.$router.push("/login")
                            this.$message.success("修改成功")
                            //触发父级更新User的方法
                            this.getUserInfo()
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            });

        }
    }
}
</script>


<style scoped></style>