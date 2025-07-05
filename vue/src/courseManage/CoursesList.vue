<script lang="ts">
import {defineComponent, reactive, ref, computed, toRaw} from 'vue'
import { useRouter } from 'vue-router'
import { Plus } from '@element-plus/icons-vue'
import type { UploadFile } from 'element-plus'

export default defineComponent({
  name: "courseList",
  setup() {
    const router = useRouter()
    const search = reactive({
      name: '',
      author: '',
      order: '',
      mine: false
    })
    const onReset = () => {
      search.name = ''
      search.author = ''
      search.order = ''
    }
    // 静态课程数据，审核状态只用三种
    const tableData = ref([
      {
        id: 1,
        name: '软件测试质量管理体系',
        desc: '深入讲解软件测试质量管理体系的建立与实施，包括测试策略制定、质量评估标准、缺陷管理等核心内容',
        cover: { url: 'https://dummyimage.com/200x120/409eff/fff&text=质量管理' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 1,
        author: '张教授',
        createTime: '2024-05-01 10:00',
        status: '通过',
      },
      {
        id: 2,
        name: '信息系统安全评估标准',
        desc: '介绍信息系统安全评估的最新标准和方法，涵盖安全风险评估、漏洞检测、安全防护等关键技术',
        cover: { url: 'https://dummyimage.com/200x120/67c23a/fff&text=安全评估' },
        video: { url: 'https://www.w3schools.com/html/movie.mp4' },
        order: 2,
        author: '李专家',
        createTime: '2024-05-02 11:00',
        status: '未通过',
      },
      {
        id: 3,
        name: '计算机软硬件质量检测技术',
        desc: '系统介绍计算机软硬件质量检测的先进技术和方法，包括自动化测试、性能测试、兼容性测试等',
        cover: { url: 'https://dummyimage.com/200x120/f56c6c/fff&text=质量检测' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 3,
        author: '王工程师',
        createTime: '2024-05-03 12:00',
        status: '待审核',
      },
    ])
    const page = ref(1)
    const pageSize = ref(10)
    const pagedData = computed(() => {
      const start = (page.value - 1) * pageSize.value
      return tableData.value.slice(start, start + pageSize.value)
    })
    const onPageChange = (val: number) => {
      page.value = val
    }
    const onPageSizeChange = (val: number) => {
      pageSize.value = val
      page.value = 1 // 切换每页条数时回到第一页
    }
    const onEdit = (row: any) => {
      editForm.name = row.name
      editForm.desc = row.desc
      editForm.order = row.order || ''
      editForm.cover = row.cover || null
      editForm.video = row.video || null
      editForm.author = row.author
      editCoverFileList.value = row.cover ? [{ ...row.cover, uid: row.cover.uid || Date.now() + '' }] : []
      showEditDialog.value = true
    }
    const onDelete = (row: any) => {
      // 删除逻辑
      console.log('删除', row)
    }
    const goToPending = () => {
      router.push({ name: 'PendingCoursesList' })
    }
    const showAddDialog = ref(false)
    const addFormRef = ref()
    const addForm = reactive({
      name: '',
      desc: '',
      order: '',
      cover: null as UploadFile | null,
      video: null as UploadFile | null,
      author: '当前用户' // 登录功能未开发，暂用静态值
    })
    const addRules = {
      name: [
        { required: true, message: '请输入课程名称', trigger: 'blur' },
        { min: 2, max: 30, message: '课程名称长度为2-30个字符', trigger: 'blur' }
      ],
      desc: [
        { required: true, message: '请输入课程简介', trigger: 'blur' },
        { min: 5, max: 200, message: '课程简介长度为5-200个字符', trigger: 'blur' }
      ],
      order: [ { required: true, message: '请选择排序值', trigger: 'change' } ],
      cover: [ { required: true, message: '请上传课程封面', trigger: 'change' } ],
      video: [ { required: true, message: '请上传课程视频', trigger: 'change' } ],
      author: [ { required: true, message: '请输入作者', trigger: 'blur' } ]
    }
    const coverFileList = ref<UploadFile[]>([])
    const editCoverFileList = ref<UploadFile[]>([])

    const handleCoverChange = (file: UploadFile, fileList: UploadFile[]) => {
      coverFileList.value = fileList
      addForm.cover = fileList.length > 0 ? fileList[0] : null
    }
    const handleCoverRemove = (file: UploadFile, fileList: UploadFile[]) => {
      coverFileList.value = fileList
      addForm.cover = null
    }
    const handleEditCoverChange = (file: UploadFile, fileList: UploadFile[]) => {
      editCoverFileList.value = fileList
      editForm.cover = fileList.length > 0 ? fileList[0] : null
    }
    const handleEditCoverRemove = (file: UploadFile, fileList: UploadFile[]) => {
      editCoverFileList.value = fileList
      editForm.cover = null
    }
    const handleVideoChange = (file: UploadFile) => {
      addForm.video = file
    }
    const resetAddForm = () => {
      addForm.name = ''
      addForm.desc = ''
      addForm.order = ''
      addForm.cover = null
      addForm.video = null
      // addForm.author = '当前用户' // 保持作者不变
      if (addFormRef.value) addFormRef.value.clearValidate()
    }
    const submitAddForm = () => {
      addFormRef.value.validate((valid: boolean) => {
        if (valid) {
          // 提交逻辑
          showAddDialog.value = false
        }
      })
    }
    const showEditDialog = ref(false)
    const editFormRef = ref()
    const editForm = reactive({
      name: '',
      desc: '',
      order: '',
      cover: null as UploadFile | null,
      video: null as UploadFile | null,
      author: ''
    })
    const resetEditForm = () => {
      editForm.name = ''
      editForm.desc = ''
      editForm.order = ''
      editForm.cover = null
      editForm.video = null
      editForm.author = ''
      if (editFormRef.value) editFormRef.value.clearValidate()
    }
    const handleEditVideoChange = (file: UploadFile) => {
      editForm.video = file
    }
    const submitEditForm = () => {
      editFormRef.value.validate((valid: boolean) => {
        if (valid) {
          // 提交编辑逻辑
          showEditDialog.value = false
        }
      })
    }
    // 课程详情弹窗
    const showDetailDialog = ref(false)
    const detailData = ref<any>({})
    const onShowDetail = (row: any) => {
      detailData.value = row
      showDetailDialog.value = true
    }
    
    // 排序相关
    const sortOrder = ref('order')
    const sortDirection = ref('desc')
    const sortOptions = [
      { label: '创建时间', value: 'createTime' },
      { label: '排序值', value: 'order' }
    ]
    
    const onSortChange = () => {
      // 这里可以添加排序逻辑
      console.log('排序方式改变:', sortOrder.value, sortDirection.value)
    }
    
    return { search, onReset, tableData, pagedData, page, pageSize, onPageChange, onPageSizeChange, onEdit, onDelete, goToPending, showAddDialog, addForm, addFormRef, addRules, coverFileList, editCoverFileList, handleCoverChange, handleCoverRemove, handleEditCoverChange, handleEditCoverRemove, handleVideoChange, resetAddForm, submitAddForm, showEditDialog, editForm, editFormRef, resetEditForm, submitEditForm, Plus, showDetailDialog, detailData, onShowDetail, sortOrder, sortDirection, sortOptions, onSortChange }
  }
})
</script>

<template>
  <div class="courses-list-page">
    <div class="header-bar">
      <span>课程列表</span>
      <el-button class="audit-entry" type="primary" size="small" @click="goToPending">进入审核页</el-button>
    </div>
    <div class="operation-bar">
      <span class="search-label">课程搜索</span>
      <el-form :inline="true" class="search-form">
        <el-form-item label="课程名称">
          <el-input v-model="search.name" placeholder="请输入课程名称" size="small" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="search.author" placeholder="请输入作者" size="small" />
        </el-form-item>
        <el-form-item label="排序值">
          <el-select v-model="search.order" placeholder="请选择排序值" size="small" style="width: 160px;">
            <el-option v-for="n in 5" :key="n" :label="n" :value="n" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="small" @click="onReset">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="operation-right">
        <el-checkbox v-model="search.mine">只显示我的课程</el-checkbox>
        <el-button type="primary" size="small" style="margin-left: 16px;" @click="showAddDialog = true">添加课程</el-button>
      </div>
    </div>
    <!-- 课程列表表格 -->
    <div class="table-wrapper fixed-table-height">
      <el-table :data="pagedData" style="width: 100%; margin-top: 0; border-radius: 12px;" border stripe highlight-current-row>
        <el-table-column prop="id" label="课程ID" width="100" align="center" />
        <el-table-column prop="name" label="课程名称" min-width="140" align="center">
          <template #default="{ row }">
            <el-link type="primary" @click="onShowDetail(row)">{{ row.name }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="desc" label="课程描述" min-width="220" show-overflow-tooltip align="center" />
        <el-table-column prop="author" label="作者" min-width="120" align="center" />
        <el-table-column prop="status" label="审核状态" min-width="100" align="center">
          <template #default="{ row }">
            <el-tag v-if="row.status === '通过'" type="success">通过</el-tag>
            <el-tag v-else-if="row.status === '未通过'" type="danger">未通过</el-tag>
            <el-tag v-else type="warning">待审核</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="140" align="center">
          <template #default="scope">
            <el-button size="small" type="primary" @click="onEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="onDelete(scope.row)" style="margin-left: 8px;">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-bar-outer">
      <div class="pagination-container">
        <el-pagination
          background
          layout="sizes, prev, pager, next, jumper, ->, total"
          :current-page="page"
          :page-size="pageSize"
          :page-sizes="[10, 20, 50]"
          :total="tableData.length"
          @current-change="onPageChange"
          @size-change="onPageSizeChange"
        />
        <div class="sort-container">
          <span class="sort-label">排序方式：</span>
          <el-select v-model="sortOrder" placeholder="请选择排序字段" size="default" style="width: 140px; margin-right: 8px;" @change="onSortChange">
            <el-option v-for="option in sortOptions" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
          <el-select v-model="sortDirection" placeholder="排序方向" size="default" style="width: 100px;" @change="onSortChange">
            <el-option label="降序" value="desc" />
            <el-option label="升序" value="asc" />
          </el-select>
        </div>
      </div>
    </div>
    <!-- 添加课程对话框 -->
    <el-dialog v-model="showAddDialog" title="添加课程" width="500px" @close="resetAddForm" class="add-course-dialog" style="margin-top: 50px;">
      <el-form :model="addForm" :rules="addRules" ref="addFormRef" label-width="90px" status-icon>
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="addForm.name" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程简介" prop="desc">
          <el-input v-model="addForm.desc" type="textarea" placeholder="请输入课程简介" :rows="3" />
        </el-form-item>
        <el-form-item label="排序值" prop="order">
          <el-select v-model="addForm.order" placeholder="请选择排序值">
            <el-option v-for="n in 5" :key="n" :label="n" :value="n" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程封面" prop="cover">
          <div style="display: flex; align-items: center;">
            <el-upload
              class="upload-demo"
              action="#"
              :auto-upload="false"
              :show-file-list="true"
              :limit="1"
              :on-change="handleCoverChange"
              :on-remove="handleCoverRemove"
              :file-list="coverFileList"
              accept="image/*"
              list-type="picture-card"
            >
              <template #trigger>
                <el-icon v-if="coverFileList.length === 0"><Plus /></el-icon>
              </template>
              <template #file="{ file }">
                <img v-if="file.url" :src="file.url" class="el-upload-list__item-thumbnail" />
              </template>
              <div class="el-upload__tip" style="color: #999;">只能上传不超过3MB的图片</div>
            </el-upload>
            <el-button
              type="danger"
              size="small"
              style="margin-left: 12px;"
              :disabled="coverFileList.length === 0"
              @click="() => { coverFileList = []; addForm.cover = null }"
            >
              清除图片
            </el-button>
          </div>
        </el-form-item>
        <el-form-item label="课程视频" prop="video">
          <el-upload
            class="upload-demo"
            action="#"
            :auto-upload="false"
            :show-file-list="true"
            :limit="1"
            :on-change="handleVideoChange"
            :file-list="addForm.video ? [addForm.video] : []"
            accept="video/*"
            list-type="text"
          >
            <template #trigger>
              <div style="display: flex; flex-direction: column; align-items: flex-start;">
                <el-button type="primary">上传视频</el-button>
                <div class="el-upload__tip" style="color: #999; margin-top: 4px; pointer-events: none;">
                  只能上传不超过500MB的视频
                </div>
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="addForm.author" disabled />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="submitAddForm">确定</el-button>
      </template>
    </el-dialog>
    <!-- 编辑课程对话框 -->
    <el-dialog v-model="showEditDialog" title="编辑课程" width="500px" @close="resetEditForm" class="edit-course-dialog" style="margin-top: 50px;">
      <el-form :model="editForm" :rules="addRules" ref="editFormRef" label-width="90px" status-icon>
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="editForm.name" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程简介" prop="desc">
          <el-input v-model="editForm.desc" type="textarea" placeholder="请输入课程简介" :rows="3" />
        </el-form-item>
        <el-form-item label="排序值" prop="order">
          <el-select v-model="editForm.order" placeholder="请选择排序值">
            <el-option v-for="n in 5" :key="n" :label="n" :value="n" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程封面" prop="cover" v-if="showEditDialog">
          <div style="display: flex; align-items: center;">
            <el-upload
              class="upload-demo"
              action="#"
              :auto-upload="false"
              :show-file-list="true"
              :limit="1"
              :on-change="handleEditCoverChange"
              :on-remove="handleEditCoverRemove"
              :file-list="editCoverFileList"
              accept="image/*"
              list-type="picture-card"
            >
              <template #trigger>
                <el-icon v-if="editCoverFileList.length === 0"><Plus /></el-icon>
              </template>
              <template #file="{ file }">
                <img v-if="file.url" :src="file.url" class="el-upload-list__item-thumbnail" />
              </template>
              <div class="el-upload__tip" style="color: #999;">只能上传不超过3MB的图片</div>
            </el-upload>
            <el-button
              type="danger"
              size="small"
              style="margin-left: 12px;"
              :disabled="editCoverFileList.length === 0"
              @click="() => { editCoverFileList = []; editForm.cover = null }"
            >
              清除图片
            </el-button>
          </div>
        </el-form-item>
        <el-form-item label="课程视频" prop="video">
          <el-upload
            class="upload-demo"
            action="#"
            :auto-upload="false"
            :show-file-list="true"
            :limit="1"
            :on-change="handleEditVideoChange"
            :file-list="editForm.video ? [editForm.video] : []"
            accept="video/*"
            list-type="text"
          >
            <template #trigger>
              <div style="display: flex; flex-direction: column; align-items: flex-start;">
                <el-button type="primary">上传视频</el-button>
                <div class="el-upload__tip" style="color: #999; margin-top: 4px; pointer-events: none;">
                  只能上传不超过500MB的视频
                </div>
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="editForm.author" disabled />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEditDialog = false">取消</el-button>
        <el-button type="primary" @click="submitEditForm">确定</el-button>
      </template>
    </el-dialog>
    <!-- 课程详情弹窗 -->
    <el-dialog v-model="showDetailDialog" title="课程详情" width="900px" :close-on-click-modal="false" class="detail-dialog" style="margin-top: 50px;">
      <el-descriptions :column="1" border class="detail-desc">
        <el-descriptions-item label="课程ID">{{ detailData.id }}</el-descriptions-item>
        <el-descriptions-item label="课程名称">{{ detailData.name }}</el-descriptions-item>
        <el-descriptions-item label="课程简介">{{ detailData.desc }}</el-descriptions-item>
        <el-descriptions-item label="课程封面">
          <el-image
            v-if="detailData.cover && detailData.cover.url"
            :src="detailData.cover.url"
            style="width: 240px; height: 135px; object-fit: cover; border-radius: 8px;"
            :preview-src-list="[detailData.cover.url]"
          />
        </el-descriptions-item>
        <el-descriptions-item label="课程视频">
          <video v-if="detailData.video && detailData.video.url" :src="detailData.video.url" controls style="width: 240px; height: 135px; border-radius: 8px; background: #000;" />
          <span v-else style="color: #999;">无</span>
        </el-descriptions-item>
        <el-descriptions-item label="排序值">{{ detailData.order }}</el-descriptions-item>
        <el-descriptions-item label="作者">{{ detailData.author }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ detailData.createTime }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <el-tag v-if="detailData.status === '通过'" type="success">通过</el-tag>
          <el-tag v-else-if="detailData.status === '未通过'" type="danger">未通过</el-tag>
          <el-tag v-else type="warning">待审核</el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="showDetailDialog = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.courses-list-page {
  min-height: 100vh;
  background: #f5f6fa;
}

.header-bar {
  width: 100%;
  background: #409eff;
  color: #fff;
  font-size: 24px;
  font-weight: bold;
  padding: 20px 32px;
  box-sizing: border-box;
  letter-spacing: 2px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.audit-entry {
  font-size: 14px;
  font-weight: 400;
}

.operation-bar {
  display: flex;
  align-items: center;
  padding: 18px 40px 14px 40px;
  background: #fff;
  box-sizing: border-box;
  border-bottom: 1px solid #e4e7ed;
}

.search-label {
  font-size: 16px;
  font-weight: 400;
  margin-right: 24px;
  color: #333;
  line-height: 40px;
}

.search-form {
  flex: 1;
  display: flex;
  align-items: center;
  font-size: 18px;
}

.el-form--inline .el-form-item {
  margin-bottom: 0;
  vertical-align: middle;
  font-size: 18px;
}

.operation-right {
  display: flex;
  align-items: center;
  margin-left: 32px;
  font-size: 18px;
}

.table-wrapper {
  margin: 32px 32px 0 32px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.03);
  padding: 0 0 0;
  overflow-x: auto;
}

.fixed-table-height {
  height: 540px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

::v-deep(.el-table__header th) {
  background: #409eff !important;
  color: #fff !important;
  font-weight: bold;
  font-size: 15px;
}

.pagination-bar-outer {
  display: flex;
  justify-content: center;
  margin-top: 24px;
  margin-bottom: 0;
}

.pagination-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 32px;
  width: 100%;
  max-width: 1200px;
  padding: 0 32px;
}

.sort-container {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.sort-label {
  color: #606266;
  margin-right: 8px;
  font-weight: 500;
}

::v-deep(.el-table__body tr) {
  height: 50px !important;
}
::v-deep(.el-table__body td) {
  line-height: 50px !important;
  padding-top: 0 !important;
  padding-bottom: 0 !important;
}

::v-deep(.el-upload-list__item) ~ .el-upload--picture-card {
  display: none;
}

.detail-desc {
  font-size: 15px;
}

/* 限制弹窗高度为一屏并居中显示 */
::v-deep(.detail-dialog .el-dialog) {
  max-height: 90vh;
  margin: 5vh auto !important;
  margin-top: 50px !important; /* 让弹窗在页面中更靠上 */
}

/* 使用更强的选择器确保样式生效 */
::v-deep(.el-dialog-wrapper .detail-dialog .el-dialog) {
  margin-top: 50px !important;
}

::v-deep(.detail-dialog .el-dialog__body) {
  max-height: calc(90vh - 120px);
  overflow-y: auto;
}

/* 添加和编辑课程弹窗样式 */
::v-deep(.add-course-dialog .el-dialog) {
  margin-top: 50px !important;
}

::v-deep(.edit-course-dialog .el-dialog) {
  margin-top: 50px !important;
}
</style>
