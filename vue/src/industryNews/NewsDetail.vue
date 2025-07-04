<template>
  <div class="news-detail">
    <el-card>
      <template #header>
        <div class="clearfix">
          <span>{{ news.title }}</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="goBack">
            返回列表
          </el-button>
        </div>
      </template>
      <div class="detail-info">
        <span>作者: {{ news.author }}</span>
        <span>发布时间: {{ news.createTime }}</span>
        <span v-if="news.status == 1" class="status-tag status-pending">待审核</span>
        <span v-else-if="news.status == 2" class="status-tag status-published">已发布</span>
        <span v-else-if="news.status == 3" class="status-tag status-rejected">已驳回</span>
        <span v-else class="status-tag status-unknown">未知状态</span>
      </div>
      <el-image
        v-if="news.image"
        :src="news.image"
        style="width: 100%; max-width: 600px; margin: 20px 0"
      />
      <div class="detail-content">
        {{ news.content }}
      </div>
      <el-affix v-if="news.status == 1 && userInfo.role == 'admin'" position="bottom" :offset="40">
        <div style="display: flex; justify-content: center">
          <el-button
            @click="drawerExam = true"
            type="success"
            size="large"
            style="font-size: large"
          >
            审核
          </el-button>
        </div>
      </el-affix>
      <el-drawer v-model="drawerExam" direction="btt" size="10%">
        <template #header
          ><div
            style="
              display: flex;
              flex-direction: row;
              justify-content: center;
              align-items: center;
              gap: 20px;
              width: 100%;
            "
          >
            <h2 style="margin: 0">审核结果：</h2>
            <el-button @click="clickPass" type="success" plain>通过</el-button>
            <el-button @click="clickReject" type="danger" plain>驳回</el-button>
            <el-button @click="drawerExam = false" type="primary" plain>取消</el-button>
          </div>
        </template>
        <template #default> </template>
        <template #footer> </template>
      </el-drawer>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios'

//抽屉：开关
const drawerExam = ref(false)

const route = useRoute()
const router = useRouter()
const userInfo = ref({
  username: '管理员',
  role: 'admin', // admin 或 enterprise
})
const news = ref({
  id: null,
  title: '',
  author: '',
  createTime: '',
  status: 0,
  content: '',
  summary: '',
  image: '',
})
onMounted(() => {
  fetchNewsDetail()
})

// 获取新闻详情
const fetchNewsDetail = async () => {
  try {
    // // 模拟API请求延迟
    // await new Promise((resolve) => setTimeout(resolve, 300))

    // // 模拟数据
    // const mockData = route.query

    // // 复制数据到详情
    // Object.assign(news.value, mockData)
    axios
      .get('http://localhost:8000/news/findById', {
        params: {
          id: route.query.id,
        },
      })
      .then((res) => {
        news.value = res.data
      })
  } catch (error) {
    console.error('获取新闻详情失败:', error)
    ElMessage.error('获取新闻详情失败')
    router.back()
  }
}

const goBack = () => {
  router.back()
}

const updateNews = (num: number) => {
  axios
    .get('http://localhost:8000/news/update', {
      params: { id: news.value.id, status: num },
    })
    .then(() => {
      ElMessage({
        type: 'success',
        message: '审核成功',
      })
    })
}

const clickPass = () => {
  //上传……
  updateNews(2)
  router.back()
}

const clickReject = () => {
  //上传……
  updateNews(3)
  router.back()
}
</script>

<style scoped>
.detail-info {
  margin: 10px 0;
  color: #606266;
}

.detail-info span {
  margin-right: 20px;
}

.status-tag {
  padding: 1px 5px;
  border-radius: 3px;
  color: white;
}

.status-tag.status-pending {
  background-color: #e6a23c;
}

.status-tag.status-published {
  background-color: #67c23a;
}

.status-tag.status-rejected {
  background-color: #f56c6c;
}

.status-tag.status-unknown {
  background-color: #868484;
}

.detail-content {
  text-align: left;
  line-height: 1.8;
  margin-top: 20px;
  white-space: pre-wrap;
}
</style>
