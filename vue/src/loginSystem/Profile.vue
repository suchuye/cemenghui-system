<template>
  <div class="profile-container">
    <!-- 页面标题 -->
    <div class="page-title">
      <h2>个人信息</h2>
      <p>管理您的账户信息和安全设置</p>
    </div>
    
    <div class="profile-content">
      <!-- 左侧用户信息卡片 -->
      <div class="user-card">
        <div class="user-avatar">
          <el-avatar :size="100" :src="userInfo.avatar" icon="UserFilled" />
          <div class="user-badge">
            <el-tag :type="userInfo.userType === 'admin' ? 'danger' : 'primary'" size="small">
              {{ userInfo.userType === 'admin' ? '管理员' : '普通用户' }}
            </el-tag>
          </div>
        </div>
        
        <div class="user-info">
          <h3>{{ userInfo.nickname }}</h3>
          <p class="account">@{{ userInfo.account }}</p>
          <p class="user-id">ID: {{ userInfo.id }}</p>
          <p class="company-name">企业名称：{{ userInfo.companyName || '未填写' }}</p>
          <p class="company-contact">企业联系方式：{{ userInfo.companyContact || '未填写' }}</p>
          <p class="register-date">注册时间：{{ userInfo.createdAt }}</p>
        </div>
        
        <div class="user-actions">
          <el-button type="primary" @click="showChangePassword" icon="Lock">
            修改密码
          </el-button>
        </div>
      </div>
      
      <!-- 右侧信息表单 -->
      <div class="info-section">
        <!-- 基本信息表单 -->
        <div class="form-card">
          <div class="card-header">
            <h3>基本信息</h3>
            <el-button type="primary" @click="startEdit" :icon="isEditing ? 'Close' : 'Edit'">
              {{ isEditing ? '取消' : '编辑' }}
            </el-button>
          </div>
          
          <el-form 
            :model="form" 
            :rules="rules" 
            ref="profileForm" 
            label-width="80px"
            :disabled="!isEditing"
          >
            <div class="form-row">
              <el-form-item label="昵称" prop="nickname">
                <el-input v-model="form.nickname" placeholder="请输入昵称" />
              </el-form-item>
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入手机号" />
              </el-form-item>
            </div>
            <div class="form-row">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" placeholder="请输入邮箱" />
              </el-form-item>
              <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="form.gender">
                  <el-radio label="male">男</el-radio>
                  <el-radio label="female">女</el-radio>
                  <el-radio label="secret">保密</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>
            
            <div v-if="isEditing" class="form-actions">
              <el-button type="primary" @click="saveProfile" :loading="saving">
                {{ saving ? '保存中...' : '保存' }}
              </el-button>
              <el-button @click="cancelEdit">取消</el-button>
            </div>
          </el-form>
        </div>
        
        <!-- 账户信息 -->
        <div class="account-card">
          <div class="card-header">
            <h3>账户信息</h3>
          </div>
          
          <div class="account-info">
            <div class="info-item">
              <span class="label">注册时间：</span>
              <span class="value">{{ userInfo.createdAt }}</span>
            </div>
            <div class="info-item">
              <span class="label">最后登录：</span>
              <span class="value">{{ userInfo.lastLogin }}</span>
            </div>
            <div class="info-item">
              <span class="label">账户状态：</span>
              <el-tag :type="userInfo.status === 0 ? 'success' : 'danger'" size="small">
                {{ userInfo.status === 0 ? '启用' : '禁用' }}
              </el-tag>
            </div>
            <div class="info-item">
              <span class="label">登录次数：</span>
              <span class="value">{{ loginCount }} 次</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 修改密码对话框 -->
    <el-dialog 
      v-model="passwordDialogVisible" 
      title="修改密码" 
      width="400px"
      :close-on-click-modal="false"
    >
      <el-alert
        title="密码安全提示"
        type="info"
        :closable="false"
        show-icon
        style="margin-bottom: 20px;"
      >
        <template #default>
          <p>新密码必须包含字母和数字，长度至少6位</p>
        </template>
      </el-alert>
      
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px">
        <el-form-item label="当前密码" prop="oldPassword">
          <el-input 
            v-model="passwordForm.oldPassword" 
            type="password" 
            show-password
            placeholder="请输入当前密码"
          />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input 
            v-model="passwordForm.newPassword" 
            type="password" 
            show-password
            placeholder="请输入新密码"
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input 
            v-model="passwordForm.confirmPassword" 
            type="password" 
            show-password
            placeholder="请再次输入新密码"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="passwordDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="changePassword" :loading="changingPassword">
            {{ changingPassword ? '修改中...' : '确认修改' }}
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted } from 'vue'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus'
import { getProfile, updateProfile, changePassword } from '@/api/user'

const userStore = useUserStore()

const userInfo = computed(() => userStore.userInfo)

// 响应式数据
const isEditing = ref(false)
const saving = ref(false)
const passwordDialogVisible = ref(false)
const changingPassword = ref(false)

// 表单数据
const form = reactive({
  nickname: userInfo.value.nickname,
  phone: userInfo.value.phone,
  email: userInfo.value.email,
  gender: userInfo.value.gender,
  remark: userInfo.value.remark
})

// 密码表单
const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 表单验证规则
const rules = {
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 20, message: '昵称长度在2到20个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ]
}

// 密码确认验证
const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入当前密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' },
    { 
      pattern: /^(?=.*[a-zA-Z])(?=.*\d)/, 
      message: '密码必须包含字母和数字', 
      trigger: 'blur' 
    }
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const profileForm = ref()
const passwordFormRef = ref()

// 模拟登录次数
const loginCount = ref(42)

// 获取用户信息
onMounted(async () => {
  const res = await getProfile()
  Object.assign(form, res.userInfo)
})

// 开始编辑
const startEdit = () => {
  isEditing.value = true
  // 重置表单数据
  Object.assign(form, {
    nickname: userInfo.value.nickname,
    phone: userInfo.value.phone,
    email: userInfo.value.email,
    gender: userInfo.value.gender,
    remark: userInfo.value.remark
  })
}

// 取消编辑
const cancelEdit = () => {
  isEditing.value = false
}

// 保存个人信息
const saveProfile = async () => {
  saving.value = true
  try {
    await updateProfile(form)
    ElMessage.success('保存成功')
    isEditing.value = false
  } catch (e) {
    ElMessage.error('保存失败')
  } finally {
    saving.value = false
  }
}

// 修改密码
const changePassword = async () => {
  changingPassword.value = true
  try {
    await changePassword({
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    })
    ElMessage.success('密码修改成功')
    passwordDialogVisible.value = false
  } catch (e) {
    ElMessage.error('密码修改失败')
  } finally {
    changingPassword.value = false
  }
}

// 显示修改密码对话框
const showChangePassword = () => {
  passwordDialogVisible.value = true
  // 清空密码表单
  Object.assign(passwordForm, {
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  })
}
</script>

<style scoped>
.profile-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.page-title {
  text-align: center;
  margin-bottom: 30px;
}

.page-title h2 {
  color: #303133;
  font-size: 24px;
  font-weight: 600;
  margin: 0 0 8px 0;
}

.page-title p {
  color: #909399;
  font-size: 14px;
  margin: 0;
}

.profile-content {
  max-width: 1000px;
  margin: 0 auto;
  display: flex;
  gap: 24px;
}

.user-card {
  width: 280px;
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: fit-content;
}

.user-avatar {
  position: relative;
  display: inline-block;
  margin-bottom: 16px;
}

.user-badge {
  position: absolute;
  bottom: 0;
  right: 0;
  transform: translate(25%, 25%);
}

.user-info h3 {
  margin: 0 0 8px 0;
  color: #303133;
  font-size: 18px;
  font-weight: 600;
}

.user-info .account {
  margin: 0 0 4px 0;
  color: #409eff;
  font-size: 14px;
}

.user-info .user-id {
  margin: 0 0 20px 0;
  color: #909399;
  font-size: 12px;
}

.user-actions {
  margin-top: 20px;
}

.info-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-card, .account-card {
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid #ebeef5;
}

.card-header h3 {
  margin: 0;
  color: #303133;
  font-size: 16px;
  font-weight: 600;
}

.form-row {
  display: flex;
  gap: 20px;
  margin-bottom: 16px;
}

.form-row .el-form-item {
  flex: 1;
  margin-bottom: 0;
}

.form-actions {
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #ebeef5;
  display: flex;
  gap: 12px;
}

.account-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.info-item .label {
  color: #606266;
  font-weight: 500;
  min-width: 80px;
}

.info-item .value {
  color: #303133;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .profile-container {
    padding: 16px;
  }
  
  .profile-content {
    flex-direction: column;
  }
  
  .user-card {
    width: 100%;
  }
  
  .form-row {
    flex-direction: column;
    gap: 0;
  }
}
</style> 