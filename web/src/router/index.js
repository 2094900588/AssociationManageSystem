import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin',
    component: () => import('../views/Manage'),
    // redirect: "/home",
    children: [
      { path: '', name: '主页', component: () => import('../views/Home') },
      { path: 'user', name: '用户管理', component: () => import('../views/user/User') },
      { path: 'person', name: '个人信息', component: () => import('../views/user/Person') },
      { path: 'role', name: '角色管理', component: () => import('../views/user/RoleManage') },
      { path: 'log', name: '操作日志', component: () => import('../views/log/log') },
      { path: 'file', name: '文件管理', component: () => import('../views/File') },
      { path: 'activemanage', name: '社团活动管理', component: () => import('../views/club/ActiveManage') },
      { path: 'clubmanage', name: '社团管理', component: () => import('../views/club/ClubManage') },
      { path: 'clubperson', name: '社团人员管理', component: () => import('../views/club/ClubPerson') },
    ]
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/',
    name: 'Login',
    component: () => import('../views/user/Login.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/user/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/user/Register')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)
  store.commit("setPath")
  next()
})

export default router
