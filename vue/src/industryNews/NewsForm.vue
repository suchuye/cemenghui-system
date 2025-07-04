<template>
  <div class="news-form">
    <el-card>
      <template #header>
        <div>
          {{ formTitle }}
          <el-button style="float: right; padding: 3px 0" type="text" @click="goBack">
            返回列表
          </el-button>
        </div>
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
            :file-list="uploadFileList"
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
import { ref, reactive, onMounted, defineEmits, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElImage, ElUpload } from 'element-plus'
import axios from 'axios'
import type { UploadUserFile, FormInstance } from 'element-plus'

const emits = defineEmits(['submit', 'reset'])

const route = useRoute()
const router = useRouter()
const formRef = ref<FormInstance | null>(null)
const fileList = ref<{ url: string }[]>([])
const uploadFileList = computed<UploadUserFile[]>(() =>
  fileList.value.map((file) => ({
    url: file.url,
    name: '', // 可选，UploadUserFile 需要 name 字段
    status: 'success', // 可选，UploadUserFile 需要 status 字段
  })),
)
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
    axios
      .get('http://localhost:8000/news/findById', {
        params: {
          id: route.query.id,
        },
      })
      .then((res) => {
        newsForm.id = res.data.id
        newsForm.title = res.data.title
        newsForm.author = res.data.author
        newsForm.image = res.data.image
        newsForm.summary = res.data.summary
        newsForm.content = res.data.content
        Object.assign(tempForm, newsForm)
        // 如果有图片，添加到文件列表
        if (newsForm.image) {
          fileList.value = [{ url: newsForm.image }]
        }
      })
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
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      newsForm.status = userInfo.value.role === 'admin' ? 2 : 1 // 管理员直接发布，企业用户需要审核
      newsForm.createTime = new Date().toISOString().slice(0, 19).replace('T', ' ')

      // 提交表单
      if (route.query.formType === 'add') {
        addNews()
      } else if (route.query.formType === 'edit') {
        updateNews()
      }
    } else {
      console.log('error submit!!')
    }
  })
}

const getParams = () => {
  const params: any = { ...newsForm }
  // 删除空值或空字符串的参数
  Object.keys(params).forEach(
    (key) =>
      (params[key] === undefined || params[key] === null || params[key] === '') &&
      delete params[key],
  )
  return params
}

const addNews = () => {
  axios
    .get('http://localhost:8000/news/add', {
      params: getParams(),
    })
    .then(() => {
      ElMessage({
        type: 'success',
        message: '添加成功',
      })
      router.back()
    })
}

const updateNews = () => {
  axios
    .get('http://localhost:8000/news/update', {
      params: getParams(),
    })
    .then(() => {
      ElMessage({
        type: 'success',
        message: '修改成功',
      })
      router.back()
    })
}

const resetForm = () => {
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
    Object.assign(newsForm, tempForm)
    // 如果有图片，添加到文件列表
    if (newsForm.image) {
      fileList.value = [{ url: newsForm.image }]
    }
  }
  emits('reset')
}

const goBack = () => {
  router.back()
}
</script>

<style scoped>
.upload-demo {
  display: inline-block;
}
</style>
