<template>
  <div class="register-container">
    <el-card class="register-card">
      <div class="register-header">
        <h2>用户注册</h2>
        <p>创建您的企业账户</p>
      </div>
      
      <el-form :model="form" ref="registerForm" :rules="rules" label-width="80px">
        <el-form-item label="企业名称" prop="companyName">
          <el-input 
            v-model="form.companyName" 
            placeholder="请输入企业名称"
            prefix-icon="OfficeBuilding"
          />
        </el-form-item>
        <el-form-item label="企业联系方式" prop="companyContact">
          <el-input 
            v-model="form.companyContact" 
            placeholder="请输入企业联系方式（手机号或座机）"
            prefix-icon="Phone"
          />
        </el-form-item>
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
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input 
            v-model="form.confirmPassword" 
            type="password" 
            placeholder="请再次输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item label="验证码" prop="verifyCode">
          <el-input 
            v-model="form.verifyCode" 
            placeholder="请输入验证码"
            prefix-icon="CircleCheck"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="onRegister" 
            :loading="loading"
            style="width: 100%"
          >
            {{ loading ? '注册中...' : '注册' }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="register-footer">
        <span>已有账号？</span>
        <el-link type="primary" @click="goToLogin">立即登录</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()

const form = ref({
  companyName: '',
  companyContact: '',
  account: '',
  password: '',
  confirmPassword: '',
  verifyCode: ''
})

const loading = ref(false)

// 密码确认验证
const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.value.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

// 表单验证规则
const rules = {
  companyName: [
    { required: true, message: '请输入企业名称', trigger: 'blur' }
  ],
  companyContact: [
    { required: true, message: '请输入企业联系方式', trigger: 'blur' },
    { pattern: /^(1[3-9]\d{9}|0\d{2,3}-?\d{7,8})$/, message: '请输入正确的手机号或座机号', trigger: 'blur' }
  ],
  account: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 20, message: '账号长度在3到20个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' }
  ],
  verifyCode: [
    { required: true, message: '请输入验证码', trigger: 'blur' }
  ]
}

const registerForm = ref()

// 跳转到登录页面
const goToLogin = () => {
  router.push('/login')
}

const onRegister = async () => {
  try {
    // 表单验证
    await registerForm.value.validate()
    loading.value = true
    // 使用真实API调用
    const response = await register(form.value)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (error) {
    ElMessage.error(error.message || '注册失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.register-card {
  width: 100%;
  max-width: 500px;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.register-header {
  text-align: center;
  margin-bottom: 30px;
}

.register-header h2 {
  color: #303133;
  margin-bottom: 8px;
  font-size: 28px;
  font-weight: 600;
}

.register-header p {
  color: #909399;
  margin: 0;
  font-size: 14px;
}

.register-footer {
  text-align: center;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
}

.register-footer span {
  color: #909399;
  margin-right: 8px;
}
</style> 