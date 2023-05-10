<template>
    <el-card style="width: 500px">
        <el-form label-width="70px" size="small">
            <el-upload class="avatar-uploader" ref="upload" action="#" :show-file-list="false" :on-change="handleChange"
                :auto-upload="false" list-type="picture-card">
                <img v-if="form.userphoto" :src="form.userphoto" class="avatar" width="148" height="148">
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
import fileapi from '@/api/page/file'
import router from '@/router'
export default {
    name: "Person",
    data() {
        return {
            file: null,
            form: {},
        }
    },
    computed: {
        ...mapState(['user'])
    },
    created() {
        this.form = this.user
    },
    watch: {
        user: function (o) {
            this.form = JSON.parse(JSON.stringify(o))
        }
    },
    methods: {
        ...mapActions(['getUserInfo']),
        //上传文件让第二次覆盖第一的文件
        handleChange(file, fileList) {
            const isJPG = file.raw.type.indexOf("image") !== -1;
            const isLt2M = file.raw.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
                return
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
                return
            }
            this.form.userphoto = file.url
            this.file = file
        },
        saveUser() {
            // this.form.password = ""
            if (this.file == null) {
                userapi.modifyinfo(this.form).then(res => {
                    if (res.code === '200') {
                        this.$message.success("保存成功")
                        //触发父级更新User的方法
                        this.getUserInfo()
                        router.push({ name: "主页" })
                    } else {
                        this.$message.error("保存失败")
                    }
                })

            } else {
                let fromdata = new FormData()
                fromdata.append("file", this.file.raw)
                fileapi.upload_photo(fromdata).then(res => {
                    if (res.code === '200') {
                        this.form.userphoto = res.data.url
                        userapi.modifyinfo(this.form).then(res => {
                            if (res.code === '200') {
                                this.$message.success("保存成功")
                                //触发父级更新User的方法
                                this.getUserInfo()
                                router.push({ name: "主页" })
                            } else {
                                this.$message.error("保存失败")
                            }
                        })
                    } else {
                        this.$message.error(res.msg)
                        return false
                    }
                })
            }
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
    line-height: 128px;
    text-align: center;
}

.avatar {
    display: block;
}
</style>