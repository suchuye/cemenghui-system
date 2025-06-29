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
            <el-button size="small" @click="handleView(scope.row)">查看</el-button>
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
            <el-button
              size="small"
              type="success"
              @click="handleApprove(scope.row)"
              v-if="userInfo.role === 'admin' && scope.row.status === 0"
              >审核</el-button
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

<script setup>
import { ref, onMounted, defineProps, defineEmits } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  title: {
    type: String,
    default: '动态列表',
  },
  listType: {
    type: String,
    default: 'my', // all, my, pending
  },
})

const emits = defineEmits(['refresh'])

const router = useRouter()
const searchKey = ref('')
const newsList = [
  {
    id: 1,
    title: '行业新政策解读',
    author: '甲公司',
    createTime: '2023-06-01 10:00',
    status: 1,
    content: '这是政策解读的详细内容...',
    summary: '新政策将带来行业重大变革',
    image: 'https://picsum.photos/200/100?random=1',
  },
  {
    id: 2,
    title: '技术创新大会即将召开',
    author: '乙公司',
    createTime: '2023-06-02 11:30',
    status: 1,
    content: '大会将聚焦AI与行业结合...',
    summary: '年度技术盛宴',
    image: 'https://picsum.photos/200/100?random=2',
  },
  {
    id: 3,
    title: '市场分析报告发布',
    author: '丙公司',
    createTime: '2023-06-03 09:15',
    status: 0,
    content: '本季度市场趋势分析...',
    summary: '市场趋势预测',
    image: 'https://picsum.photos/200/100?random=3',
  },
  {
    id: 4,
    title: '企业合作案例分享',
    author: '丁公司',
    createTime: '2023-06-04 14:20',
    status: 1,
    content: '成功合作案例分析...',
    summary: '合作模式创新',
    image: 'https://picsum.photos/200/100?random=4',
  },
  {
    id: 5,
    title: '人才招聘计划启动',
    author: '戊公司',
    createTime: '2023-06-05 16:45',
    status: 2,
    content: '诚聘各类专业人才...',
    summary: '人才战略升级',
    image: 'https://picsum.photos/200/100?random=5',
  },
]
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const userInfo = ref({
  username: '管理员',
  role: 'admin', // admin 或 enterprise
})

// 模拟登录状态
onMounted(() => {
  const user = localStorage.getItem('userInfo')
  if (user) {
    userInfo.value = JSON.parse(user)
  }
  fetchNewsList()
})

// 获取新闻列表
const fetchNewsList = async () => {
  try {
    // 这里应该调用API获取数据
    // 模拟API请求延迟
    // await new Promise((resolve) => setTimeout(resolve, 300))

    // 模拟数据
    const mockData = [
      {
        id: 1,
        title: '行业新政策解读',
        author: '政策研究部',
        createTime: '2023-06-01 10:00',
        status: 1,
        content: '这是政策解读的详细内容...',
        summary: '新政策将带来行业重大变革',
        image: 'https://picsum.photos/200/100?random=1',
      },
      {
        id: 2,
        title: '技术创新大会即将召开',
        author: '技术部',
        createTime: '2023-06-02 11:30',
        status: 1,
        content: '大会将聚焦AI与行业结合...',
        summary: '年度技术盛宴',
        image: 'https://picsum.photos/200/100?random=2',
      },
      {
        id: 3,
        title: '市场分析报告发布',
        author: '市场部',
        createTime: '2023-06-03 09:15',
        status: 0,
        content: '本季度市场趋势分析...',
        summary: '市场趋势预测',
        image: 'https://picsum.photos/200/100?random=3',
      },
      {
        id: 4,
        title: '企业合作案例分享',
        author: '销售部',
        createTime: '2023-06-04 14:20',
        status: 1,
        content: '成功合作案例分析...',
        summary: '合作模式创新',
        image: 'https://picsum.photos/200/100?random=4',
      },
      {
        id: 5,
        title: '人才招聘计划启动',
        author: '人事部',
        createTime: '2023-06-05 16:45',
        status: 2,
        content: '诚聘各类专业人才...',
        summary: '人才战略升级',
        image: 'https://picsum.photos/200/100?random=5',
      },
    ]

    // 根据用户角色和列表类型过滤数据
    let filteredData = [...mockData]

    if (props.listType === 'my') {
      filteredData = filteredData.filter((item) => item.author === userInfo.value.username)
    } else if (props.listType === 'pending') {
      filteredData = filteredData.filter((item) => item.status === 0)
    }

    // 搜索过滤
    if (searchKey.value) {
      const key = searchKey.value.toLowerCase()
      filteredData = filteredData.filter(
        (item) =>
          item.title.toLowerCase().includes(key) ||
          item.author.toLowerCase().includes(key) ||
          item.summary.toLowerCase().includes(key) ||
          (item.image && item.image.includes(key)),
      )
    }

    // 分页处理
    const start = (currentPage.value - 1) * pageSize.value
    const end = start + pageSize.value
    newsList.value = filteredData.slice(start, end)
    total.value = filteredData.length
  } catch (error) {
    console.error('获取新闻列表失败:', error)
    // 可以添加错误提示
  }
}

// 状态相关方法
const getStatusText = (status) => {
  const statusMap = {
    0: '待审核',
    1: '已发布',
    2: '已驳回',
  }
  return statusMap[status] || '未知状态'
}

const getStatusType = (status) => {
  const typeMap = {
    0: 'warning',
    1: 'success',
    2: 'danger',
  }
  return typeMap[status] || 'info'
}

// 权限判断
const canEdit = (news) => {
  if (userInfo.value.role === 'admin') return true
  return userInfo.value.username === news.author
}

const canDelete = (news) => {
  if (userInfo.value.role === 'admin') return true
  return userInfo.value.username === news.author
}

// 事件处理
const handleSizeChange = (newSize) => {
  pageSize.value = newSize
  fetchNewsList()
}

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage
  fetchNewsList()
}

const handleSearch = () => {
  currentPage.value = 1
  fetchNewsList()
}

const handleAdd = () => {
  router.push('/form')
}

const handleView = (news) => {
  router.push({ name: 'news-detail', params: { id: news.id } })
}

const handleEdit = (news) => {
  router.push({ name: 'news-edit', params: { id: news.id } })
}

const handleDelete = (news) => {
  elMessageBox
    .confirm('确定要删除这条动态吗？此操作不可撤销。', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
    .then(() => {
      // 模拟删除操作
      console.log('删除动态:', news.id)
      // 实际项目中应该调用API删除
      fetchNewsList()
      emits('refresh')
      elMessage({
        type: 'success',
        message: '删除成功!',
      })
    })
    .catch(() => {
      elMessage({
        type: 'info',
        message: '已取消删除',
      })
    })
}

const handleApprove = (news) => {
  router.push({ name: 'news-approve', params: { id: news.id } })
}
</script>

<style scoped>
.search-input {
  margin: 20px 0;
  width: 300px;
}
</style>
