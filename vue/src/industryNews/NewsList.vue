<template>
  <div class="news-list">
    <el-card>
      <template #header>
        <div class="clearfix">
          <span>{{ title }}</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="handleAdd">
            发布动态
          </el-button>
        </div>
      </template>
      <el-input
        v-model="searchKey"
        placeholder="请输入搜索内容"
        @keyup.enter="handleSearch"
        class="search-input"
      >
        <template #append>
          <el-button @click="handleSearch">搜索</el-button>
        </template>
      </el-input>
      <el-table :data="newsList" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" width="200" />
        <el-table-column prop="author" label="作者" width="100" />
        <el-table-column prop="createTime" label="发布时间" width="160" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{
              getStatusText(scope.row.status)
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" right>
          <template #default="scope">
            <el-button
              size="small"
              type="success"
              @click="handleApprove(scope.row)"
              v-if="userInfo.role === 'admin' && scope.row.status === 1"
              >审核</el-button
            >
            <el-button v-else size="small" @click="handleView(scope.row)">查看</el-button>
            <el-button
              size="small"
              type="primary"
              @click="handleEdit(scope.row)"
              v-if="canEdit(scope.row)"
              >编辑</el-button
            >
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
              v-if="canDelete(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="margin-top: 20px"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, defineEmits, watch } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'

const emits = defineEmits(['refresh'])

const title = ref('动态列表')

const router = useRouter()
const route = useRoute()
const listType = computed(() => route.query.listType || 'all')

// 监听路由变化，实时刷新表格
watch(
  () => route.query.listType,
  () => {
    currentPage.value = 1
    fetchNewsList()
  },
)
const searchKey = ref('')
interface NewsItem {
  id: number
  title: string
  author: string
  createTime: string
  status: number
  content: string
  summary: string
  image: string
}
const newsList = ref<NewsItem[]>([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const userInfo = ref({
  username: '管理员',
  role: 'admin',
})

// 模拟登录状态
onMounted(() => {
  // 在每个请求中附加JWT
  const jwt = localStorage.getItem('jwt')
  axios.defaults.headers.common['Authorization'] = 'Bearer ' + jwt

  const user = localStorage.getItem('userInfo')
  if (user) {
    userInfo.value = JSON.parse(user)
  } else {
    router.push('/logon')
  }
  fetchNewsList()
})

const getParams = () => {
  //根据listType判断，my添加作者限制，pending设置状态为1：待审核，为params添加项
  if (listType.value == 'my') {
    const params: any = {
      page: currentPage.value,
      pageSize: pageSize.value,
      keyword: searchKey.value,
      author: userInfo.value.username,
    }
    return params
  } else if (listType.value == 'pending') {
    const params: any = {
      page: currentPage.value,
      pageSize: pageSize.value,
      keyword: searchKey.value,
      status: 1,
    }
    return params
  } else {
    const params: any = {
      page: currentPage.value,
      pageSize: pageSize.value,
      keyword: searchKey.value,
    }
    return params
  }
}

// 获取新闻列表
const fetchNewsList = async () => {
  try {
    // 这里获取数据
    axios.get('http://localhost:8000/news/findPage', { params: getParams() }).then((res) => {
      newsList.value = res.data.list
      total.value = res.data.total
    })
  } catch (error) {
    console.error('获取新闻列表失败:', error)
    // 错误提示
    ElMessage({
      type: 'warning',
      message: '获取新闻列表失败',
    })
  }
}

// 状态相关方法
const getStatusText = (status: number) => {
  const statusMap: { [key: number]: string } = {
    1: '待审核',
    2: '已发布',
    3: '已驳回',
  }
  return statusMap[status] || '未知状态'
}

const getStatusType = (status: number) => {
  const typeMap: { [key: number]: string } = {
    1: 'warning',
    2: 'success',
    3: 'danger',
  }
  return typeMap[status] || 'info'
}

// 权限判断
const canEdit = (news: NewsItem) => {
  if (userInfo.value.role === 'admin') return true
  return userInfo.value.username === news.author
}

const canDelete = (news: NewsItem) => {
  if (userInfo.value.role === 'admin') return true
  return userInfo.value.username === news.author
}

// 事件处理
const handleSizeChange = (newSize: number) => {
  pageSize.value = newSize
  fetchNewsList()
}

const handleCurrentChange = (newPage: number) => {
  currentPage.value = newPage
  fetchNewsList()
}

const handleSearch = () => {
  currentPage.value = 1
  fetchNewsList()
}

const handleAdd = () => {
  router.push({ path: '/news-form', query: { formType: 'add' } })
}

const handleView = (news: NewsItem) => {
  router.push({ path: '/news-detail', query: { id: news.id } })
}

const handleApprove = (news: NewsItem) => {
  router.push({ path: '/news-detail', query: { id: news.id } })
}

const handleEdit = (news: NewsItem) => {
  router.push({ path: '/news-form', query: { formType: 'edit', id: news.id } })
}

const handleDelete = (news: NewsItem) => {
  ElMessageBox.confirm('确定要删除这条动态吗？此操作不可撤销。', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      // 删除
      const response = axios.get('http://localhost:8000/news/delete', {
        params: { id: news.id },
      })
      response.then(() => {
        fetchNewsList()
        emits('refresh')
      })
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '已取消删除',
      })
    })
}
</script>

<style scoped>
.search-input {
  margin: 20px 0;
  width: 300px;
}
</style>
