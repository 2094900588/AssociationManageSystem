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
      { path: 'modifypwd', name: '修改密码', component: () => import('../views/user/ModifyPassword') },
      { path: 'role', name: '角色管理', component: () => import('../views/user/RoleManage') },
      { path: 'log', name: '操作日志', component: () => import('../views/log/log') },
      { path: 'file', name: '文件管理', component: () => import('../views/File') },
      { path: 'activemanage', name: '社团活动管理', component: () => import('../views/club/ActiveManage') },
      { path: 'activeapply', name: '社团活动申请', component: () => import('../views/club/ActiveApply') },
      { path: 'clubmanage', name: '社团管理', component: () => import('../views/club/ClubManage') },
      { path: 'clubperson', name: '社团人员管理', component: () => import('../views/club/ClubPerson') },
      { path: 'uppowerperson', name: '社团人员升降职', component: () => import('../views/club/UpPowerManage') },
      { path: 'addcourst', name: '添加无课课表', component: () => import('../views/course/AddCourse') },
      { path: 'listcourse', name: '显示无课课表', component: () => import('../views/course/ListCourse') },
      { path: 'showcourse', name: '查看无课课表', component: () => import('../views/course/ShowCourse') },
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
    path: '/login1',
    name: 'Login1',
    component: () => import('../views/user/Login1.vue')
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
