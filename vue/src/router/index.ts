import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    //1、用户管理子系统
    {
      //登录页面
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue'),
    },
    //2、行业动态管理子系统
    {
        //动态详情页面
      path: '/news-detail',
      name: 'newsdetail',
      component: () => import('../views/NewsDetail.vue'),
    },
    {
        //动态表单页面
      path: '/news-form',
      name: 'newsform',
      component: () => import('../views/NewsForm.vue'),
    },
    {
        //动态列表页面
      path: '/news-list',
      name: 'newslist',
      component: () => import('../views/NewsList.vue'),
    },
  ],
})

export default router
