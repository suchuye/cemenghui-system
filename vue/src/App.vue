<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup>
// App.vue 作为路由容器
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: Avenir, Helvetica, Arial, sans-serif;
}
</style>
  




<!-- <template>
  <div id="app">
    <el-container>
      <el-header>
        <div class="logo">测盟汇</div>
        <div class="user-info">
          <span>{{ userInfo.username }}</span>
          <el-dropdown @command="handleCommand">
            <el-icon style="margin-right: 8px; margin-top: 1px; color: white" size="large">
              <setting />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu
            :default-active="activeMenu"
            class="el-menu-vertical"
            @open="handleOpen"
            @close="handleClose"
          >
            <el-sub-menu index="1">
              <template #title>
                <i class="el-icon-document"></i>
                <span>动态管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item
                  index="1-1"
                  v-if="userInfo.role === 'admin'"
                  @click="router.push('/news-list')"
                >
                  全部动态
                </el-menu-item>
                <el-menu-item index="1-2" @click="goToMyList">我的动态</el-menu-item>
                <el-menu-item index="1-3" v-if="userInfo.role === 'admin'" @click="goToPendingList"
                  >待审核动态</el-menu-item
                >
                <el-menu-item
                  index="1-4"
                  @click="router.push({ path: '/news-form', query: { formType: 'add' } })"
                  >发布动态</el-menu-item
                >
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Setting } from '@element-plus/icons-vue'

const router = useRouter()
const activeMenu = ref('1-2')
const userInfo = ref({
  username: '管理员',
  role: 'admin', // admin 或 enterprise
})

// 检查登录状态
onMounted(() => {
  // 这里可以从localStorage获取用户信息
  const user = localStorage.getItem('userInfo')
  if (user) {
    userInfo.value = JSON.parse(user)
  } else {
    router.push('/login')
  }
})

const goToMyList = () => {
  router.push({ path: '/news-list', query: { listType: 'my' } })
}

const goToPendingList = () => {
  router.push({ path: '/news-list', query: { listType: 'pending' } })
}

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}

const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}

const handleCommand = (command) => {
  if (command === 'logout') {
    localStorage.removeItem('userInfo')
    router.push('/login')
  }
}
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 0px;
  height: 100vh;
}

.el-header {
  background-color: #304156;
  color: #fff;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-info span {
  margin-right: 20px;
}

.el-aside {
  background-color: #1f2d3d;
  color: #fff;
}

.el-main {
  background-color: #eaedf1;
  color: #333;
  padding: 20px;
}
</style> -->
