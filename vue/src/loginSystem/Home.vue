<template>
  <div class="home-layout">
    <!-- 顶部导航栏 -->
    <div class="navbar">
      <div class="navbar-title">测盟汇</div>
      <div class="navbar-user" @click="goToProfile">
        <el-avatar :size="32" :src="userInfo.avatar" icon="UserFilled" style="margin-right: 8px;" />
        <span class="navbar-nickname">{{ userInfo.nickname }}</span>
      </div>
    </div>
    <div class="home-main">
      <!-- 侧边栏 -->
      <div class="sidebar">
        <el-menu :default-active="activeMenu" class="el-menu-vertical-demo" router>
          <el-menu-item v-if="!isAdmin" index="1">侧边栏1</el-menu-item>
          <el-menu-item v-if="!isAdmin" index="2">侧边栏2</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/home/users">用户管理</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/home/role-manage">权限管理</el-menu-item>
        </el-menu>
      </div>
      <!-- 主要内容区 -->
      <div class="main-content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../store/user'
import { getUserList } from '@/api/user'

const router = useRouter()
const userStore = useUserStore()
const route = useRoute()

const userInfo = computed(() => userStore.userInfo)
const isAdmin = computed(() => userInfo.value.userType === 'admin')
const activeMenu = computed(() => route.path)

const stats = ref({ totalUsers: 0, activeUsers: 0, newUsers: 0 })
const recentUsers = ref([])

onMounted(async () => {
  const res = await getUserList({ page: 1, pageSize: 5 })
  stats.value.totalUsers = res.total
  stats.value.activeUsers = res.userList.filter(u => u.status === 'active').length
  stats.value.newUsers = res.userList.length // 可根据后端实际返回调整
  recentUsers.value = res.userList.slice(0, 5)
})

// 跳转到个人信息页面
const goToProfile = () => {
  router.push('/home/profile')
}

</script>

<style scoped>
.home-layout {
  min-height: 100vh;
  background-color: #f5f7fa;
}
.navbar {
  height: 56px;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}
.navbar-title {
  font-size: 20px;
  font-weight: bold;
  color: #333;
}
.navbar-user {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.navbar-nickname {
  color: #333;
  font-size: 16px;
}
.home-main {
  display: flex;
  height: calc(100vh - 56px);
}
.sidebar {
  width: 180px;
  background: #fff;
  border-right: 1px solid #ebeef5;
  padding-top: 24px;
}
.main-content {
  flex: 1;
  padding: 32px 24px;
  overflow-y: auto;
}
.home-container {
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

.home-content {
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

.user-info .email {
  margin: 0 0 20px 0;
  color: #909399;
  font-size: 12px;
}

.user-stats {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 4px;
}

.stat-label {
  color: #909399;
  font-size: 12px;
}

.main-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.quick-actions-card, .recent-card {
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.card-header {
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

.quick-actions {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.recent-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.recent-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 6px;
  background-color: #f8f9fa;
}

.recent-item .user-info {
  flex: 1;
}

.recent-item .user-name {
  font-weight: 500;
  color: #303133;
  margin-bottom: 4px;
}

.recent-item .user-time {
  font-size: 12px;
  color: #909399;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .home-container {
    padding: 16px;
  }
  
  .home-content {
    flex-direction: column;
  }
  
  .user-card {
    width: 100%;
  }
  
  .quick-actions {
    grid-template-columns: 1fr;
  }
}
</style> 