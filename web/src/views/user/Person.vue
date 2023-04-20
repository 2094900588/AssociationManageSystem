<template>
    <el-card style="width: 500px">
        <el-form label-width="70px" size="small">
            <el-upload class="avatar-uploader" action="http://localhost:9090/file/upload" :show-file-list="false"
                :on-success="handleAvatarSuccess">
                <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>

            <el-form-item label="用户名">
                <el-input v-model="form.username" disabled type="text" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="form.nickname" type="text" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱地址">
                <el-input v-model="form.email" type="text" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="电话">
                <el-input v-model="form.phone" type="text" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址">
                <el-input v-model="form.address" type="textarea" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
                <el-input v-model="form.name" type="text" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="学号">
                <el-input v-model="form.studentid" type="text" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveUser">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import userapi from '@/api/page/user'
export default {
    name: "Person",
    data() {
        return {
            // form: {},
        }
    },
    computed: {
        ...mapState({ form: 'user' })
    },
    created() {
        // this.form = this.user
    },
    methods: {
        ...mapActions(['getUserInfo']),
        saveUser() {
            // this.form.password = ""
            userapi.modifyinfo(this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success("保存成功")
                    //触发父级更新User的方法
                    this.getUserInfo()
                } else {
                    this.$message.error("保存失败")
                }
            })
            // this.request.post("/user/", this.form).then(res => {
            //     if (res.code === '200') {
            //         this.$message.success("保存成功")
            //         //触发父级更新User的方法
            //         this.getUserInfo()
            //     } else {
            //         this.$message.error("保存失败")
            //     }
            // })
        },
        handleAvatarSuccess(res) {
            this.form.avatarUrl = res
        }
    }
}
</script>

<style>
.avatar-uploader {
    text-align: center;
    padding-bottom: 10px;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 128px;
    height: 128px;
    line-height: 128px;
    text-align: center;
}

.avatar {
    width: 128px;
    height: 128px;
    display: block;
}
</style>