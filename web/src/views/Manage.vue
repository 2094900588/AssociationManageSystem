<template>
    <el-container style="min-height: 100vh">
        <el-aside :width="side_With + 'px'" style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px #888888">
            <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
        </el-aside>
        <el-container>
            <el-header style=" border-bottom:1px solid #ccc;">
                <Header :collapseBtnClass="collapseBtnClass" :collapse="isCollapse" :user="user"
                    @asideCollapse="collapse" />
            </el-header>
            <el-main>
                <!--  表示当前页面的子路由会在router-view中显示  -->
                <router-view />
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
// @ is an alias to /src
import Aside from '@/components/Aside'
import Header from '@/components/Header'
import { mapState } from 'vuex'
export default {
    name: 'Home',
    data() {
        return {
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            side_With: 200,
            logoTextShow: true,
            // user: {}
        }
    },
    components: {
        //注册Aside组件，将侧边栏整体打包，以便后续使用
        Aside,
        Header
    },
    created() {
    },
    computed: {
        ...mapState(['user'])
    },
    methods: {
        collapse() {//点击收缩按钮触发
            this.isCollapse = !this.isCollapse;
            if (this.isCollapse) {
                this.side_With = 64
                this.collapseBtnClass = 'el-icon-s-unfold'
                this.logoTextShow = false
            } else {
                this.side_With = 200
                this.collapseBtnClass = 'el-icon-s-fold'
                this.logoTextShow = true
            }
        }
    }
}
</script>
