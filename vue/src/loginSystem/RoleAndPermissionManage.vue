<template>
  <div class="role-permission-manage-container">
    <!-- 角色管理部分 -->
    <el-card style="margin-bottom: 32px;">
      <div style="margin-bottom: 16px; display: flex; justify-content: space-between; align-items: center;">
        <span style="font-size: 20px; font-weight: bold;">角色管理</span>
        <el-button type="primary" @click="openAddDialog">新建角色</el-button>
      </div>
      <el-table :data="roleList" style="width: 100%" v-loading="loading">
        <el-table-column prop="name" label="角色名称" width="180" />
        <el-table-column prop="description" label="角色描述" />
        <el-table-column label="操作" width="260">
          <template #default="scope">
            <el-button size="small" @click="openEditDialog(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteRole(scope.row)">删除</el-button>
            <el-button size="small" type="success" @click="openPermissionDialog(scope.row)">分配权限</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新建/编辑角色弹窗 -->
    <el-dialog :title="isEdit ? '编辑角色' : '新建角色'" v-model="addEditDialogVisible" width="400px">
      <el-form :model="roleForm" :rules="roleRules" ref="roleFormRef" label-width="80px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="roleForm.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input v-model="roleForm.description" placeholder="请输入角色描述" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addEditDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitRole">保存</el-button>
      </template>
    </el-dialog>

    <!-- 分配权限弹窗 -->
    <el-dialog title="分配权限" v-model="permissionDialogVisible" width="400px">
      <el-tree
        :data="permissionList"
        show-checkbox
        node-key="id"
        ref="permissionTreeRef"
        :default-checked-keys="checkedPermissionIds"
        :props="{
          label: 'name',
          children: 'children'
        }"
        style="max-height: 300px; overflow: auto;"
      />
      <template #footer>
        <el-button @click="permissionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPermissions">保存</el-button>
      </template>
    </el-dialog>

    <!-- 权限管理部分 -->
    <el-card>
      <div style="margin-bottom: 16px; display: flex; justify-content: space-between; align-items: center;">
        <span style="font-size: 20px; font-weight: bold;">权限管理</span>
        <el-button type="primary" @click="openAddPermissionDialog">新建权限</el-button>
      </div>
      <el-table :data="permissionTableData" style="width: 100%" v-loading="permissionLoading">
        <el-table-column prop="name" label="权限名称" width="180" />
        <el-table-column prop="code" label="权限标识" width="180" />
        <el-table-column prop="description" label="权限描述" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="openEditPermissionDialog(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deletePermission(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新建/编辑权限弹窗 -->
    <el-dialog :title="permissionIsEdit ? '编辑权限' : '新建权限'" v-model="addEditPermissionDialogVisible" width="400px">
      <el-form :model="permissionForm" :rules="permissionRules" ref="permissionFormRef" label-width="80px">
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="permissionForm.name" placeholder="请输入权限名称" />
        </el-form-item>
        <el-form-item label="权限标识" prop="code">
          <el-input v-model="permissionForm.code" placeholder="请输入权限标识（如 user:manage）" />
        </el-form-item>
        <el-form-item label="权限描述" prop="description">
          <el-input v-model="permissionForm.description" placeholder="请输入权限描述" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addEditPermissionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPermissionForm">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRoleList, createRole, updateRole, deleteRole, assignRolePermissions } from '@/api/role'
import { getPermissionList } from '@/api/permission'

// 角色管理相关
const roleList = ref([])
const loading = ref(false)
const addEditDialogVisible = ref(false)
const isEdit = ref(false)
const roleForm = reactive({ id: '', name: '', description: '' })
const roleFormRef = ref()
const roleRules = {
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }]
}
const permissionDialogVisible = ref(false)
const permissionList = ref([])
const checkedPermissionIds = ref([])
const currentRoleId = ref(null)
const permissionTreeRef = ref()

const fetchRoles = async () => {
  loading.value = true
  try {
    const res = await getRoleList()
    roleList.value = res.roleList
  } finally {
    loading.value = false
  }
}

const openAddDialog = () => {
  isEdit.value = false
  Object.assign(roleForm, { id: '', name: '', description: '' })
  addEditDialogVisible.value = true
}
const openEditDialog = (row) => {
  isEdit.value = true
  Object.assign(roleForm, row)
  addEditDialogVisible.value = true
}
const submitRole = () => {
  roleFormRef.value.validate(async (valid) => {
    if (!valid) return
    if (isEdit.value) {
      await updateRole(roleForm)
      ElMessage.success('角色修改成功')
    } else {
      await createRole(roleForm)
      ElMessage.success('角色创建成功')
    }
    addEditDialogVisible.value = false
    fetchRoles()
  })
}
const deleteRole = async (row) => {
  await deleteRole(row.id)
  ElMessage.success('角色删除成功')
  fetchRoles()
}
const openPermissionDialog = async (row) => {
  currentRoleId.value = row.id
  permissionDialogVisible.value = true
  // 获取当前角色已分配权限（如有API可补充）
  checkedPermissionIds.value = []
}
const submitPermissions = async () => {
  await assignRolePermissions(currentRoleId.value, checkedPermissionIds.value)
  ElMessage.success('权限分配成功')
  permissionDialogVisible.value = false
  fetchRoles()
}

// 权限管理相关
const permissionTableData = ref([])
const permissionLoading = ref(false)
const addEditPermissionDialogVisible = ref(false)
const permissionIsEdit = ref(false)
const permissionForm = reactive({ id: '', name: '', code: '', description: '' })
const permissionFormRef = ref()
const permissionRules = {
  name: [{ required: true, message: '请输入权限名称', trigger: 'blur' }],
  code: [{ required: true, message: '请输入权限标识', trigger: 'blur' }]
}
const fetchPermissions = async () => {
  permissionLoading.value = true
  try {
    const res = await getPermissionList()
    permissionList.value = res.permissionList
    permissionTableData.value = res.permissionList
  } finally {
    permissionLoading.value = false
  }
}
const openAddPermissionDialog = () => {
  permissionIsEdit.value = false
  Object.assign(permissionForm, { id: '', name: '', code: '', description: '' })
  addEditPermissionDialogVisible.value = true
}
const openEditPermissionDialog = (row) => {
  permissionIsEdit.value = true
  Object.assign(permissionForm, row)
  addEditPermissionDialogVisible.value = true
}
const submitPermissionForm = () => {
  permissionFormRef.value.validate(async (valid) => {
    if (!valid) return
    if (permissionIsEdit.value) {
      ElMessage.success('权限修改成功（测试）')
    } else {
      ElMessage.success('权限创建成功（测试）')
    }
    addEditPermissionDialogVisible.value = false
    // 测试数据不刷新
  })
}
const deletePermission = (row) => {
  ElMessageBox.confirm('确定要删除该权限吗？', '提示', { type: 'warning' })
    .then(() => {
      ElMessage.success('权限删除成功（测试）')
    })
    .catch(() => {})
}
onMounted(() => {
  fetchRoles()
  fetchPermissions()
})
</script>

<style scoped>
.role-permission-manage-container {
  padding: 24px;
}
</style> 