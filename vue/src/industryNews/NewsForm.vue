<template>
  <div class="news-form">
    <el-card>
      <template #header>
        <div>{{ formTitle }}</div>
      </template>
      <el-form :model="newsForm" ref="formRef" label-width="120px" size="medium">
        <el-form-item label="标题" prop="title">
          <el-input v-model="newsForm.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="newsForm.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="新闻图片" prop="image">
          <el-upload
            class="upload-demo"
            action="#"
            :multiple="false"
            :limit="1"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-upload="beforeUpload"
            :file-list="fileList"
            list-type="picture-card"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-image
            v-if="newsForm.image"
            :src="newsForm.image"
            style="width: 100px; height: 100px"
          />
        </el-form-item>
        <el-form-item label="新闻简介" prop="summary">
          <el-input
            v-model="newsForm.summary"
            type="textarea"
            :rows="4"
            placeholder="请输入新闻简介"
          />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input
            v-model="newsForm.content"
            type="textarea"
            :rows="8"
            placeholder="请输入新闻内容"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, defineEmits } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, ElImage, ElUpload } from 'element-plus'

const emits = defineEmits(['submit', 'reset'])

const route = useRoute()
const router = useRouter()
const formRef = ref(null)
const fileList = ref<{ url: string }[]>([])
const tempForm = reactive({
  id: null,
  title: '',
  author: '',
  image: '',
  summary: '',
  content: '',
  status: 0,
  createTime: '',
})
const newsForm = reactive({
  id: null,
  title: '',
  author: '',
  image: '',
  summary: '',
  content: '',
  status: 0,
  createTime: '',
})
const formTitle = ref('发布动态')
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
  fetchNewsDetail()

  // 设置表单标题
  if (route.query.formType === 'add') {
    formTitle.value = '发布动态'
    newsForm.author = userInfo.value.username
  } else if (route.query.formType === 'edit') {
    formTitle.value = '编辑动态'
    fetchNewsDetail()
  } else if (route.query.formType === 'approve') {
    formTitle.value = '审核动态'
    fetchNewsDetail()
  }
})

// 获取新闻详情
const fetchNewsDetail = async () => {
  try {
    // 模拟API请求延迟
    await new Promise((resolve) => setTimeout(resolve, 300))

    // 模拟数据
    const mockData = route.query

    // 复制数据到表单
    Object.assign(tempForm, mockData)
    Object.assign(newsForm, mockData)

    // 如果有图片，添加到文件列表
    if (newsForm.image) {
      fileList.value = [{ url: newsForm.image }]
    }
  } catch (error) {
    console.error('获取新闻详情失败:', error)
    ElMessage.error('获取新闻详情失败')
    router.back()
  }
}

// 上传相关方法
import type { UploadFile } from 'element-plus'

const handleRemove = (file: UploadFile, fileList: UploadFile[]) => {
  console.log(file, fileList)
  newsForm.image = ''
}

const handlePreview = (file: UploadFile) => {
  console.log(file)
  // 预览图片逻辑
}

const beforeUpload = (file: File) => {
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
  if (!isJPG) {
    ElMessage.error('上传图片只能是 JPG 或 PNG 格式!')
  }
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isLt2M) {
    ElMessage.error('上传图片大小不能超过 2MB!')
  }

  // 模拟上传成功
  const reader = new FileReader()
  reader.onload = (e) => {
    newsForm.image = (e.target as FileReader).result as string
    fileList.value = [{ url: newsForm.image }]
  }
  reader.readAsDataURL(file)

  return isJPG && isLt2M
}

// 表单操作
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      // 准备提交的数据
      const formData = { ...newsForm }

      formData.status = userInfo.value.role === 'admin' ? 1 : 0 // 管理员直接发布，企业用户需要审核
      formData.createTime = new Date().toISOString().slice(0, 19).replace('T', ' ')

      // 提交表单
      emits('submit', formData)
    } else {
      console.log('error submit!!')
      return false
    }
  })
}

const resetForm = () => {
  Object.assign(newsForm, tempForm)

  if (route.query.formType === 'add') {
    // 重置为初始值
    newsForm.title = ''
    newsForm.author = userInfo.value.username
    newsForm.image = ''
    newsForm.summary = ''
    newsForm.content = ''
    fileList.value = []
  } else {
    // 重新加载数据
    fetchNewsDetail()
  }
  emits('reset')
}
</script>

<style scoped>
.upload-demo {
  display: inline-block;
}
</style>
