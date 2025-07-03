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
    axios
      .get('http://localhost:8000/news/find', { params: getParams() })
      .then((res) => {
        newsList.value = res.data.list
        total.value = res.data.total
      })

    // 模拟数据
    // const mockData = [
    //   {
    //     id: 1,
    //     title: '行业新政策解读',
    //     author: '政策研究部',
    //     createTime: '2023-06-01 10:00',
    //     status: 2,
    //     content:
    //       '这是政策解读的详细内容...\n这是政策解读的详细内容...\n这是政策解读的详细内容...这是政策解读的详细内容...这是政策解读的详细内容...这是政策解读的详细内容...这是政策解读的详细内容...\n这是政策解读的详细内容...这是政策解读的详细内容...这是政策解读的详细内容...这是政策解读的详细内容...这是政策解读的详细内容...',
    //     summary: '新政策将带来行业重大变革',
    //     image: 'https://picsum.photos/200/100?random=1',
    //   },
    //   {
    //     id: 2,
    //     title: '技术创新大会即将召开',
    //     author: '技术部',
    //     createTime: '2023-06-02 11:30',
    //     status: 3,
    //     content: '大会将聚焦AI与行业结合...',
    //     summary: '年度技术盛宴',
    //     image: 'https://picsum.photos/200/100?random=2',
    //   },
    //   {
    //     id: 3,
    //     title: '市场分析报告发布',
    //     author: '市场部',
    //     createTime: '2023-06-03 09:15',
    //     status: 1,
    //     content: '本季度市场趋势分析...',
    //     summary: '市场趋势预测',
    //     image: 'https://picsum.photos/200/100?random=3',
    //   },
    //   {
    //     id: 4,
    //     title: '企业合作案例分享',
    //     author: '销售部',
    //     createTime: '2023-06-04 14:20',
    //     status: 2,
    //     content: '成功合作案例分析...',
    //     summary: '合作模式创新',
    //     image: 'https://picsum.photos/200/100?random=4',
    //   },
    //   {
    //     id: 5,
    //     title: '人才招聘计划启动',
    //     author: '人事部',
    //     createTime: '2023-06-05 16:45',
    //     status: 3,
    //     content: '诚聘各类专业人才...',
    //     summary: '人才战略升级',
    //     image: 'https://picsum.photos/200/100?random=5',
    //   },
    //   {
    //     id: 6,
    //     title: '企业社会责任报告',
    //     author: '公关部',
    //     createTime: '2023-06-06 13:10',
    //     status: 1,
    //     content: '企业积极履行社会责任...',
    //     summary: '社会责任新举措',
    //     image: 'https://picsum.photos/200/100?random=6',
    //   },
    //   {
    //     id: 7,
    //     title: '年度财务总结',
    //     author: '财务部',
    //     createTime: '2023-06-07 15:30',
    //     status: 2,
    //     content: '本年度财务状况良好...',
    //     summary: '财务数据公开',
    //     image: 'https://picsum.photos/200/100?random=7',
    //   },
    //   {
    //     id: 8,
    //     title: '新产品发布会',
    //     author: '产品部',
    //     createTime: '2023-06-08 09:00',
    //     status: 3,
    //     content: '发布会介绍新产品亮点...',
    //     summary: '创新产品亮相',
    //     image: 'https://picsum.photos/200/100?random=8',
    //   },
    //   {
    //     id: 9,
    //     title: '环保公益活动圆满结束',
    //     author: '公关部',
    //     createTime: '2023-06-09 17:20',
    //     status: 2,
    //     content: '本次环保活动取得良好社会反响...',
    //     summary: '绿色行动，人人有责',
    //     image: 'https://picsum.photos/200/100?random=9',
    //   },
    //   {
    //     id: 10,
    //     title: '公司荣获行业大奖',
    //     author: '市场部',
    //     createTime: '2023-06-10 10:10',
    //     status: 2,
    //     content: '公司在行业评选中荣获大奖...',
    //     summary: '实力见证，载誉前行',
    //     image: 'https://picsum.photos/200/100?random=10',
    //   },
    //   {
    //     id: 11,
    //     title: '员工培训计划发布',
    //     author: '人事部',
    //     createTime: '2023-06-11 09:30',
    //     status: 1,
    //     content: '新一轮员工培训即将启动...',
    //     summary: '提升员工综合素质',
    //     image: 'https://picsum.photos/200/100?random=11',
    //   },
    //   {
    //     id: 12,
    //     title: '海外市场拓展成果',
    //     author: '销售部',
    //     createTime: '2023-06-12 13:50',
    //     status: 2,
    //     content: '海外市场份额持续增长...',
    //     summary: '国际化步伐加快',
    //     image: 'https://picsum.photos/200/100?random=12',
    //   },
    //   {
    //     id: 13,
    //     title: '安全生产月活动',
    //     author: '行政部',
    //     createTime: '2023-06-13 15:00',
    //     status: 3,
    //     content: '安全生产月系列活动顺利开展...',
    //     summary: '安全责任重于泰山',
    //     image: 'https://picsum.photos/200/100?random=13',
    //   },
    //   {
    //     id: 14,
    //     title: '新办公楼启用',
    //     author: '行政部',
    //     createTime: '2023-06-14 08:30',
    //     status: 2,
    //     content: '新办公楼正式投入使用...',
    //     summary: '办公环境全面升级',
    //     image: 'https://picsum.photos/200/100?random=14',
    //   },
    //   {
    //     id: 15,
    //     title: '客户满意度调查启动',
    //     author: '市场部',
    //     createTime: '2023-06-15 11:15',
    //     status: 1,
    //     content: '诚邀客户参与满意度调查...',
    //     summary: '服务提升，客户至上',
    //     image: 'https://picsum.photos/200/100?random=15',
    //   },
    //   {
    //     id: 16,
    //     title: '年度战略发布会',
    //     author: '战略部',
    //     createTime: '2023-06-16 14:40',
    //     status: 2,
    //     content: '公司发布新年度发展战略...',
    //     summary: '战略引领未来',
    //     image: 'https://picsum.photos/200/100?random=16',
    //   },
    //   {
    //     id: 17,
    //     title: '企业文化建设成果',
    //     author: '人事部',
    //     createTime: '2023-06-17 16:05',
    //     status: 3,
    //     content: '企业文化活动丰富多彩...',
    //     summary: '凝聚力持续提升',
    //     image: 'https://picsum.photos/200/100?random=17',
    //   },
    //   {
    //     id: 18,
    //     title: '智能制造项目进展',
    //     author: '技术部',
    //     createTime: '2023-06-18 09:55',
    //     status: 2,
    //     content: '智能制造项目取得阶段性成果...',
    //     summary: '科技驱动生产升级',
    //     image: 'https://picsum.photos/200/100?random=18',
    //   },
    //   {
    //     id: 19,
    //     title: '智能制造项目进展',
    //     author: '管理员',
    //     createTime: '2023-06-18 09:55',
    //     status: 2,
    //     content: '智能制造项目取得阶段性成果...',
    //     summary: '科技驱动生产升级',
    //     image: 'https://picsum.photos/200/100?random=18',
    //   },
    //   {
    //     id: 20,
    //     title: '智能制造项目进展',
    //     author: '管理员',
    //     createTime: '2023-06-18 09:55',
    //     status: 2,
    //     content: '智能制造项目取得阶段性成果...',
    //     summary: '科技驱动生产升级',
    //     image: 'https://picsum.photos/200/100?random=18',
    //   },
    //   {
    //     id: 21,
    //     title: '智能制造项目进展',
    //     author: '管理员',
    //     createTime: '2023-06-18 09:55',
    //     status: 2,
    //     content: '智能制造项目取得阶段性成果...',
    //     summary: '科技驱动生产升级',
    //     image: 'https://picsum.photos/200/100?random=18',
    //   },
    // ]

    // 根据用户角色和列表类型过滤数据
    // let filteredData = [...mockData]

    // if (listType.value === 'my') {
    //   filteredData = filteredData.filter((item) => item.author === userInfo.value.username)
    // } else if (listType.value === 'pending') {
    //   filteredData = filteredData.filter((item) => item.status === 0)
    // }

    // // 搜索过滤
    // if (searchKey.value) {
    //   const key = searchKey.value.toLowerCase()
    //   filteredData = filteredData.filter(
    //     (item) =>
    //       (item.title ?? '').toLowerCase().includes(key) ||
    //       (item.author ?? '').toLowerCase().includes(key) ||
    //       (item.summary ?? '').toLowerCase().includes(key) ||
    //       (item.image && item.image.includes(key)),
    //   )
    // }

    // // 分页处理
    // const start = (currentPage.value - 1) * pageSize.value
    // const end = start + pageSize.value
    // newsList.value = filteredData.slice(start, end)
    // total.value = filteredData.length
  } catch (error) {
    console.error('获取新闻列表失败:', error)
    // 可以添加错误提示
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
  router.push('/news-form')
}

const handleView = (news: NewsItem) => {
  //router.push({ path: '/news-detail', query: { id: news.id } })
  router.push({ path: '/news-detail', query: news })
}

const handleEdit = (news: NewsItem) => {
  //router.push({ path: '/news-form', query: { id: news.id } })
  router.push({ path: '/news-form', query: news })
}

const handleDelete = (news: NewsItem) => {
  ElMessageBox.confirm('确定要删除这条动态吗？此操作不可撤销。', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      // 模拟删除操作
      console.log('删除动态:', news.id)
      // 实际项目中应该调用API删除
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

const handleApprove = (news: NewsItem) => {
  router.push({ path: '/news-detail', query: news })
}
</script>

<style scoped>
.search-input {
  margin: 20px 0;
  width: 300px;
}
</style>
