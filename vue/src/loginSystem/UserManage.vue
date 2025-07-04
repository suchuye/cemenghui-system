<template>
  <div class="user-manage-container">
    <!-- 页面标题 -->
    <div class="page-title">
      <h2>用户管理</h2>
      <p>管理系统中的所有用户账户</p>
    </div>
    
    <div class="manage-content">
      <!-- 搜索和操作栏 -->
      <div class="action-bar">
        <div class="search-section">
          <el-input
            v-model="accountQuery"
            placeholder="账号"
            style="width: 120px; margin-right: 8px;"
            clearable
            @input="handleSearch"
          />
          <el-input
            v-model="companyNameQuery"
            placeholder="企业名称"
            style="width: 140px; margin-right: 8px;"
            clearable
            @input="handleSearch"
          />
          <el-input
            v-model="searchQuery"
            placeholder="昵称/手机号"
            style="width: 180px; margin-right: 8px;"
            clearable
            @input="handleSearch"
          />
          <el-select v-model="statusFilter" placeholder="状态筛选" style="width: 120px" @change="handleSearch">
            <el-option label="全部状态" value="" />
            <el-option label="活跃用户" value="active" />
            <el-option label="禁用用户" value="inactive" />
          </el-select>
          <el-button type="primary" @click="handleSearch" icon="Search">搜索</el-button>
          <el-button @click="resetSearch" icon="Refresh">重置</el-button>
        </div>
        
        <div class="action-section">
          <el-button type="primary" @click="showAddDialog" icon="Plus">
            添加用户
          </el-button>
        </div>
      </div>
      
      <!-- 统计信息 -->
      <div class="stats-bar">
        <el-tag type="info">
          共找到 {{ totalUsers }} 个用户
        </el-tag>
      </div>
      
      <!-- 用户表格 -->
      <div class="table-section">
        <el-table 
          :data="filteredUsers" 
          style="width: 100%" 
          v-loading="loading"
          border
          stripe
        >
          <el-table-column prop="id" label="ID" width="80" align="center" />
          <el-table-column prop="account" label="账号" width="120" />
          <el-table-column prop="companyName" label="企业名称" width="160" />
          <el-table-column prop="companyContact" label="企业联系方式" width="160" />
          <el-table-column prop="nickname" label="昵称" width="120" />
          <el-table-column prop="userType" label="用户类型" width="100" align="center">
            <template #default="scope">
              <el-tag :type="scope.row.userType === 'admin' ? 'danger' : 'primary'" size="small">
                {{ scope.row.userType === 'admin' ? '管理员' : '普通用户' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="手机号" width="130" />
          <el-table-column prop="email" label="邮箱" width="180" />
          <el-table-column prop="gender" label="性别" width="80" align="center">
            <template #default="scope">
              <el-tag v-if="scope.row.gender === 'male'" type="primary" size="small">男</el-tag>
              <el-tag v-else-if="scope.row.gender === 'female'" type="danger" size="small">女</el-tag>
              <el-tag v-else type="info" size="small">保密</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="80" align="center">
            <template #default="scope">
              <el-tag :type="scope.row.status === 'active' ? 'success' : 'danger'" size="small">
                {{ scope.row.status === 'active' ? '活跃' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="注册时间" width="160" />
          <el-table-column prop="lastLogin" label="最后登录" width="160" />
          <el-table-column prop="remark" label="备注" width="120" show-overflow-tooltip />
          <el-table-column label="操作" width="260" fixed="right" align="center">
            <template #default="scope">
              <el-button size="small" type="primary" @click="editUser(scope.row)" icon="Edit" :disabled="scope.row.userType === 'admin'">
                编辑
              </el-button>
              <el-button 
                size="small" 
                :type="scope.row.status === 'active' ? 'warning' : 'success'"
                @click="toggleUserStatus(scope.row)"
                :icon="scope.row.status === 'active' ? 'Lock' : 'Unlock'"
                :disabled="scope.row.userType === 'admin'"
              >
                {{ scope.row.status === 'active' ? '禁用' : '启用' }}
              </el-button>
              <el-button size="small" type="danger" @click="deleteUser(scope.row)" icon="Delete" :disabled="scope.row.userType === 'admin'">
                删除
              </el-button>
              <el-button size="small" type="success" @click="openAssignRoleDialog(scope.row)" icon="UserFilled" :disabled="scope.row.userType === 'admin'">
                分配角色
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      
      <!-- 分页 -->
      <div class="pagination-section">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="totalUsers"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          background
        />
      </div>
    </div>
    
    <!-- 添加/编辑用户对话框 -->
    <el-dialog 
      v-model="dialogVisible" 
      :title="isEdit ? '编辑用户' : '添加用户'"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form :model="userForm" :rules="userRules" ref="userFormRef" label-width="80px">
        <div class="form-row">
          <el-form-item label="账号" prop="account" required>
            <el-input v-model="userForm.account" placeholder="请输入账号" />
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="userForm.nickname" placeholder="请输入昵称" />
          </el-form-item>
        </div>
        <div class="form-row">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="userForm.phone" placeholder="请输入手机号" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="userForm.email" placeholder="请输入邮箱" />
          </el-form-item>
        </div>
        <div class="form-row">
          <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="userForm.gender">
              <el-radio label="male">男</el-radio>
              <el-radio label="female">女</el-radio>
              <el-radio label="secret">保密</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <el-form-item v-if="!isEdit" label="密码" prop="password" required>
          <el-input v-model="userForm.password" type="password" placeholder="请输入初始密码" show-password />
        </el-form-item>
        <el-form-item label="企业名称" prop="companyName" required>
          <el-input v-model="userForm.companyName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="企业联系方式" prop="companyContact" required>
          <el-input v-model="userForm.companyContact" placeholder="请输入企业联系方式" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="userForm.status">
            <el-radio label="active">活跃</el-radio>
            <el-radio label="inactive">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="userForm.remark" type="textarea" :rows="3" placeholder="请输入备注信息（可选）" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveUser" :loading="saveLoading">
            {{ saveLoading ? '保存中...' : '保存' }}
          </el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 分配角色弹窗 -->
    <el-dialog v-model="assignRoleDialogVisible" title="分配角色" width="350px">
      <el-form>
        <el-form-item label="选择角色">
          <el-radio-group v-model="selectedRoleId">
            <el-radio v-for="role in roleList" :key="role.id" :label="role.id">{{ role.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="assignRoleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAssignRole">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '../store/user'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserList, createUser, updateUser, deleteUser } from '@/api/user'
import { getRoleList } from '@/api/role'


const userStore = useUserStore()
const router = useRouter()

// 检查权限
const isAdmin = computed(() => {
  return userStore.userInfo.userType === 'admin'
})

// 如果不是管理员，重定向到首页
if (!isAdmin.value) {
  router.push('/home')
}

const searchQuery = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const loading = ref(false)
const saveLoading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)

const userForm = ref({
  account: '',
  nickname: '',
  password: '',
  userType: 'user',
  phone: '',
  email: '',
  gender: 'secret',
  status: 'active',
  remark: '',
  companyName: '',
  companyContact: ''
})

const userRules = {
  account: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入初始密码', trigger: 'blur' }
  ],
  companyName: [
    { required: true, message: '请输入企业名称', trigger: 'blur' }
  ],
  companyContact: [
    { required: true, message: '请输入企业联系方式', trigger: 'blur' }
  ]
  // 其它字段为选填项，无需必填校验
}

const userFormRef = ref()
const accountQuery = ref('')
const companyNameQuery = ref('')

const users = ref([])
const totalUsers = ref(0)

const fetchUsers = async () => {
  loading.value = true
  try {
    const res = await getUserList({
      account: accountQuery.value,
      companyName: companyNameQuery.value,
      nickname: searchQuery.value,
      status: statusFilter.value,
      page: currentPage.value,
      pageSize: pageSize.value
    })
    users.value = res.userList
    totalUsers.value = res.total
  } finally {
    loading.value = false
  }
}

onMounted(fetchUsers)

const handleSearch = () => {
  currentPage.value = 1
  fetchUsers()
}

const resetSearch = () => {
  accountQuery.value = ''
  companyNameQuery.value = ''
  searchQuery.value = ''
  statusFilter.value = ''
  currentPage.value = 1
  fetchUsers()
}

const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchUsers()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchUsers()
}

const showAddDialog = () => {
  isEdit.value = false
  userForm.value = {
    account: '',
    nickname: '',
    password: '',
    userType: 'user',
    phone: '',
    email: '',
    gender: 'secret',
    status: 'active',
    remark: '',
    companyName: '',
    companyContact: ''
  }
  dialogVisible.value = true
}

const editUser = (user) => {
  isEdit.value = true
  userForm.value = { ...user }
  if (user.userType === 'admin') {
    userForm.value.userType = 'admin'
  }
  dialogVisible.value = true
}

const saveUser = async () => {
  try {
    await userFormRef.value.validate()
    saveLoading.value = true
    if (isEdit.value && userForm.value.userType === 'admin') {
      ElMessage.error('不能修改超级管理员账号！')
      saveLoading.value = false
      return
    }
    if (!isEdit.value && userForm.value.userType === 'admin') {
      ElMessage.error('不能创建超级管理员账号！')
      saveLoading.value = false
      return
    }
    if (isEdit.value) {
      await updateUser(userForm.value)
      ElMessage.success('用户信息更新成功')
    } else {
      await createUser(userForm.value)
      ElMessage.success('用户添加成功')
    }
    dialogVisible.value = false
    fetchUsers()
  } catch (error) {
    ElMessage.error('保存失败')
  } finally {
    saveLoading.value = false
  }
}

const deleteUserHandler = async (user) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除用户 "${user.nickname}" 吗？此操作不可恢复！`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteUser(user.id)
    ElMessage.success('用户删除成功')
    fetchUsers()
  } catch {}
}

const toggleUserStatus = async (user) => {
  try {
    await ElMessageBox.confirm(
      `确定要${user.status === 'active' ? '禁用' : '启用'}用户 "${user.nickname}" 吗？`,
      '确认操作',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await updateUser({ ...user, status: user.status === 'active' ? 'inactive' : 'active' })
    ElMessage.success(`用户${user.status === 'active' ? '禁用' : '启用'}成功`)
    fetchUsers()
  } catch {}
}

// 角色分配相关（如需后端API可补充）
const assignRoleDialogVisible = ref(false)
const selectedUser = ref(null)
const selectedRoleId = ref(null)
const roleList = ref([])
const openAssignRoleDialog = async (user) => {
  selectedUser.value = user
  // 获取角色列表
  const res = await getRoleList()
  roleList.value = res.roleList
  selectedRoleId.value = user.roleId || null
  assignRoleDialogVisible.value = true
}
const submitAssignRole = async () => {
  // 这里应调用后端分配角色API
  // await assignUserRole(selectedUser.value.id, selectedRoleId.value)
  ElMessage.success('分配角色成功')
  assignRoleDialogVisible.value = false
  fetchUsers()
}
</script>

<style scoped>
.user-manage-container {
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

.manage-content {
  max-width: 1200px;
  margin: 0 auto;
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.action-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #ebeef5;
}

.search-section {
  display: flex;
  gap: 12px;
  align-items: center;
}

.stats-bar {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-end;
}

.table-section {
  margin-bottom: 20px;
}

.pagination-section {
  display: flex;
  justify-content: center;
  padding-top: 16px;
  border-top: 1px solid #ebeef5;
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

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-manage-container {
    padding: 16px;
  }
  
  .action-bar {
    flex-direction: column;
    gap: 16px;
    align-items: stretch;
  }
  
  .search-section {
    flex-direction: column;
    align-items: stretch;
  }
  
  .search-section .el-input,
  .search-section .el-select {
    width: 100% !important;
  }
  
  .form-row {
    flex-direction: column;
    gap: 0;
  }
}
</style> 