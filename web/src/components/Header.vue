<template>
    <div style="line-height:60px;display:flex">
        <div style="flex: 1; ">
            <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapses"></span>
            <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
                <el-breadcrumb-item :to="{ name: '主页' }">主页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style=" cursor:pointer; text-align: right">
            <div style="display:inline-block;">
                <img :src="user.avatarUrl" alt=""
                    style="width: 30px; border-radius: 50%; position: relative; top: 10px; left: 60px">
                <span style="margin-left: 80px">{{ user.nickname }}</span><i class="el-icon-arrow-down"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <router-link :to="{
                        name: '个人信息'
                    }">个人信息</router-link>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <span style="text-decoration: none" @click="logout">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
    name: "Header",
    props: {
        collapseBtnClass: String,
        collapse: Boolean,
        user: Object
    },
    computed: {
        currentPathName() {
            return this.$store.state.currentPathName;
        }
    },
    watch: {
        currentPathName(newVal, oldVal) {
            console.log(newVal)
        }
    },
    data() {
        return {

        }
    },
    methods: {
        ...mapActions(['exit']),
        collapses() {
            this.$emit("asideCollapse")
        },
        logout() {
            this.exit()
            localStorage.removeItem("user")
            this.$message.success("退出成功！")
            this.$router.push("/login")
        }
    }
}
</script>

<style scoped></style>