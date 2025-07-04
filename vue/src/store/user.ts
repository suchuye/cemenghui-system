import { defineStore } from 'pinia'
import type { User, LoginUserInfo } from '../types/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}') as LoginUserInfo || null
  }),
  
  getters: {
    // 判断是否为管理员
    isAdmin: (state) => {
      return state.userInfo?.userType === 'admin' || state.userInfo?.role === '管理员'
    },
    
    // 判断是否为普通用户
    isUser: (state) => {
      return state.userInfo?.userType === 'user' || state.userInfo?.role === '普通用户'
    },
    
    // 判断是否已登录
    isLoggedIn: (state) => {
      return !!state.token
    },
    
    // 判断是否拥有某权限
    hasPermission: (state) => (perm: string) => {
      return (state.userInfo?.permissions || []).includes(perm)
    },
    
    // 判断是否拥有某角色
    hasRole: (state) => (role: string) => {
      return state.userInfo?.role === role
    }
  },
  
  actions: {
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },
    setUserInfo(userInfo: LoginUserInfo) {
      this.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    logout() {
      this.token = ''
      this.userInfo = null
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }
}) 