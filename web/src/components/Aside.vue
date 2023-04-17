<template>
    <el-menu :default-openeds="['1']" style="height: 100%;" background-color="rgb(48,65,86)" text-color="#fff"
        overflow-x:hidden active-text-color="#ffd04b" :collapse-transition="false" :collapse="isCollapse" router>
        <div style="height: 60px;line-height: 60px;text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top:5px;margin-right:5px">
            <b style="color: azure;" v-show="logoTextShow">后台管理系统</b>
        </div>
        <el-menu-item index="1" :route="{ name: '主页' }">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">系统管理</span>
            </template>
            <el-menu-item index="2-1" :route="{ name: '用户管理' }">
                <template slot="title">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">用户管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="2-2" :route="{ name: '文件管理' }">
                <template slot="title">
                    <i class="el-icon-folder-opened"></i>
                    <span slot="title">文件管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="2-3" :route="{ name: '角色管理' }">
                <template slot="title">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">角色管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="2-4" :route="{ name: '操作日志' }">
                <template slot="title">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">操作日志</span>
                </template>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="3">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">社团管理</span>
            </template>
            <el-menu-item index="3-1" :route="{ name: '社团管理' }">
                <template slot="title">
                    <i class="el-icon-folder-opened"></i>
                    <span slot="title">社团管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="3-2" :route="{ name: '社团人员管理' }">
                <template slot="title">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">社员管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="3-3" :route="{ name: '社团活动管理' }">
                <template slot="title">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">活动管理</span>
                </template>
            </el-menu-item>
        </el-submenu>

    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean
    },
}
</script>

<style scoped></style>