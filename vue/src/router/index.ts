import { createRouter, createWebHistory,RouteRecordRaw } from 'vue-router'
import Register from '../loginSystem/Register.vue'
import Login from '../loginSystem/Login.vue'
import Home from '../loginSystemHome.vue'
import Profile from '../loginSystem/Profile.vue'
import UserManage from '../loginSystem/UserManage.vue'
import RoleManage from '../loginSystem/RoleAndPermissionManage.vue'
import PermissionManage from '../loginSystem/PermissionManage.vue'

const routes: Array<RouteRecordRaw> = [
  { path: '/', redirect: '/login' },
  { path: '/register', component: Register },
  { path: '/login', component: Login },
  { 
    path: '/home', 
    component: Home,
    meta: { requiresAuth: true },
    children: [
      { path: '', component: Welcome },
      { path: 'profile', component: Profile },
      { path: 'users', component: UserManage, meta: { requiresAdmin: true } },
      { path: 'role-manage', component: RoleManage, meta: { requiresAdmin: true } },
      { path: 'permission-manage', component: PermissionManage, meta: { requiresAdmin: true } }
    ]
  },
  {
    path: '/403',
    component: {
      template: '<div style="text-align:center;padding:100px 0;font-size:24px;color:#d9534f;">无权限访问</div>'
    }
  }
]

// const router = createRouter({
//   history: createWebHistory(import.meta.env.BASE_URL),
//   routes: [
//     //1、用户管理子系统
//     {
//       //登录页面
//       path: '/login',
//       name: 'login',
//       component: () => import('../loginSystem/Login.vue'),
//     },
//     //2、行业动态管理子系统
//     {
//         //动态详情页面
//       path: '/news-detail',
//       name: 'newsdetail',
//       component: () => import('../industryNews/NewsDetail.vue'),
//     },
//     {
//         //动态表单页面
//       path: '/news-form',
//       name: 'newsform',
//       component: () => import('../industryNews/NewsForm.vue'),
//     },
//     {
//         //动态列表页面
//       path: '/news-list',
//       name: 'newslist',
//       component: () => import('../industryNews/NewsList.vue'),
//     },
//   ],
// })

// 路由守卫
router.beforeEach((to, from, next) => {
  // 检查是否需要登录或管理员权限
    const token = localStorage.getItem('token')
    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
  if (to.matched.some(r => r.meta.requiresAuth || r.meta.requiresAdmin)) {
    if (!token) {
      next('/login')
      return
    }
    if (to.matched.some(r => r.meta.requiresAdmin) && userInfo.userType !== 'admin') {
      next('/403')
      return
    }
  }
  next()
})


export default router
