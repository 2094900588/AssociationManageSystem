import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/Manage'),
    redirect: "/home",
    children:[
      {path: '/home', name: '主页', component: () => import('../views/Home')},
      {path: '/user', name: '用户管理', component: () => import('../views/User')},
      {path: '/person', name: '个人信息', component: () => import('../views/Person')},
      {path: '/file', name: '文件管理', component: () => import('../views/File')}
    ]
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to,from,next) =>{
  localStorage.setItem("currentPathName",to.name)
  store.commit("setPath")
  next()
})

export default router
