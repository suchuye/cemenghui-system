<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-header">
        <h2>用户登录</h2>
        <p>欢迎使用测盟汇</p>
      </div>
      
      <el-form :model="form" ref="loginForm" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="account">
          <el-input 
            v-model="form.account" 
            placeholder="请输入账号"
            prefix-icon="User"
          />
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input 
            v-model="form.password" 
            type="password" 
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            @click="onLogin" 
            :loading="loading"
            style="width: 100%"
          >
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <span>没有账号？</span>
        <el-link type="primary" @click="goToRegister">立即注册</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { login } from '../api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const form = ref({
  account: '',
  password: ''
})

const loading = ref(false)

// 表单验证规则
const rules = {
  account: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const loginForm = ref()

// 跳转到注册页面
const goToRegister = () => {
  router.push('/register')
}

const onLogin = async () => {
  try {
    // 表单验证
    await loginForm.value.validate()
    
    loading.value = true
    
    // 使用真实后端API
    const response = await login(form.value)
    // 保存用户信息
    userStore.setToken(response.token)
    userStore.setUserInfo(response.userInfo)
    
    ElMessage.success('登录成功！')
    // 登录成功后统一跳转到/home
    router.push('/home')
    
  } catch (error) {
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 450px;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  color: #303133;
  margin-bottom: 8px;
  font-size: 28px;
  font-weight: 600;
}

.login-header p {
  color: #909399;
  margin: 0;
  font-size: 14px;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
}

.login-footer span {
  color: #909399;
  margin-right: 8px;
}
</style> 