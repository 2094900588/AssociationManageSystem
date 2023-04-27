<template>
    <el-menu :default-openeds="['1']" style="height: 100%;" background-color="rgb(48,65,86)" text-color="#fff"
        overflow-x:hidden active-text-color="#ffd04b" :collapse-transition="false" :collapse="isCollapse" router>
        <div style="height: 60px;line-height: 60px;text-align: center">
            <img src="../assets/logo.png" alt=""
                style="width: 50px;position: relative;vertical-align: middle;margin-right:5px">
            <b style="color: azure;vertical-align: middle;" v-show="logoTextShow">社团管理系统</b>
        </div>
        <el-menu-item index="1" :route="{ name: '主页' }">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-submenu index="2" v-if="user.sysroleid !== 3">
            <template slot="title">
                <i class="el-icon-setting"></i>
                <span slot="title">系统管理</span>
            </template>
            <el-menu-item index="2-1" :route="{ name: '用户管理' }">
                <template slot="title">
                    <i class="el-icon-user"></i>
                    <span slot="title">用户管理</span>
                </template>
            </el-menu-item>
            <!-- <el-menu-item index="2-2" :route="{ name: '文件管理' }">
                <template slot="title">
                    <i class="el-icon-folder-opened"></i>
                    <span slot="title">文件管理</span>
                </template>
            </el-menu-item> -->
            <el-menu-item index="2-3" :route="{ name: '角色管理' }" v-if="user.sysroleid === 0 || user.sysroleid === 1">
                <template slot="title">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">角色管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="2-4" :route="{ name: '操作日志' }">
                <template slot="title">
                    <i class="el-icon-edit"></i>
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
                    <i class="el-icon-postcard"></i>
                    <span slot="title">社团管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="3-2" :route="{ name: '社团人员管理' }">
                <template slot="title">
                    <i class="el-icon-user-solid"></i>
                    <span slot="title">社员管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="3-3" :route="{ name: '社团活动申请' }">
                <template slot="title">
                    <i class="el-icon-star-off"></i>
                    <span slot="title">活动申请</span>
                </template>
            </el-menu-item>
            <el-menu-item index="3-4" :route="{ name: '社团活动管理' }">
                <template slot="title">
                    <i class="el-icon-star-off"></i>
                    <span slot="title">活动管理</span>
                </template>
            </el-menu-item>
            <el-menu-item index="3-5" :route="{ name: '社团人员升降职' }">
                <template slot="title">
                    <i class="el-icon-star-off"></i>
                    <span slot="title">社团人员升降职</span>
                </template>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="4">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">课表管理</span>
            </template>
            <el-menu-item index="4-1" :route="{ name: '添加无课课表' }">
                <template slot="title">
                    <i class="el-icon-postcard"></i>
                    <span slot="title">添加无课课表</span>
                </template>
            </el-menu-item>
            <el-menu-item index="4-2" :route="{ name: '显示无课课表' }">
                <template slot="title">
                    <i class="el-icon-postcard"></i>
                    <span slot="title">显示无课课表</span>
                </template>
            </el-menu-item>
            <el-menu-item index="4-3" :route="{ name: '查看无课课表' }">
                <template slot="title">
                    <i class="el-icon-postcard"></i>
                    <span slot="title">查看无课课表</span>
                </template>
            </el-menu-item>


        </el-submenu>
    </el-menu>
</template>

<script>

import { mapState } from 'vuex'
export default {
    name: "Aside",
    computed: {
        ...mapState(['user'])
    },
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean
    },
}
</script>

<style scoped></style>