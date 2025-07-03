import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/news-detail',
      name: 'newsdetail',
      component: () => import('../views/NewsDetail.vue'),
    },
    {
      path: '/news-form',
      name: 'newsform',
      component: () => import('../views/NewsForm.vue'),
    },
    {
      path: '/news-list',
      name: 'newslist',
      component: () => import('../views/NewsList.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue'),
    },
  ],
})

export default router
