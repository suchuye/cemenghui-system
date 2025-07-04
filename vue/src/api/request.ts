import axios, { AxiosRequestConfig, AxiosResponse } from 'axios'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: '/',
  timeout: 10000
})

// 请求拦截器
service.interceptors.request.use(config => {
  const userStore = useUserStore()
  if (userStore.token) {
    config.headers.Authorization = 'Bearer ' + userStore.token
  }
  return config
})

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    if (response.data && response.data.code && response.data.code !== 0) {
      ElMessage.error(response.data.msg || '请求失败')
      return Promise.reject(response.data)
    }
    return response.data
  },
  err => {
    if (err.response && err.response.status === 401) {
      ElMessage.error('请先登录')
      window.location.href = '/login'
    } else {
      ElMessage.error(err.message || '网络错误')
    }
    return Promise.reject(err)
  }
)

export default service 