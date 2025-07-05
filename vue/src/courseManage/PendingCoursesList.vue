<script lang="ts">
import {defineComponent, ref, computed} from 'vue'
import {ElMessage} from 'element-plus'
import { useRouter } from 'vue-router'

export default defineComponent({
  name: "PendingCoursesList",
  setup() {
    const router = useRouter()
    // 静态数据：只显示待审核课程
    const tableData = ref([
      {
        id: 101,
        name: '软件测试自动化实践',
        desc: '深入讲解软件测试自动化的实施方法和工具应用，包括Selenium、Appium等主流自动化测试框架的使用',
        cover: { url: 'https://dummyimage.com/200x120/409eff/fff&text=自动化测试' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 1,
        author: '张测试专家',
        createTime: '2024-05-10 10:00',
        status: '待审核',
      },
      {
        id: 102,
        name: '信息系统性能测试标准',
        desc: '介绍信息系统性能测试的标准流程和方法，涵盖负载测试、压力测试、容量测试等关键技术',
        cover: { url: 'https://dummyimage.com/200x120/67c23a/fff&text=性能测试' },
        video: { url: 'https://www.w3schools.com/html/movie.mp4' },
        order: 2,
        author: '李性能专家',
        createTime: '2024-05-11 11:00',
        status: '待审核',
      },
      {
        id: 103,
        name: '软件质量度量与评估',
        desc: '系统介绍软件质量度量的指标体系、评估方法和工具，帮助建立科学的软件质量评估体系',
        cover: { url: 'https://dummyimage.com/200x120/f56c6c/fff&text=质量度量' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 3,
        author: '王质量专家',
        createTime: '2024-05-12 09:30',
        status: '待审核',
      },
      {
        id: 104,
        name: '网络安全测试技术',
        desc: '深入讲解网络安全测试的技术方法，包括渗透测试、漏洞扫描、安全代码审计等核心内容',
        cover: { url: 'https://dummyimage.com/200x120/409eff/fff&text=安全测试' },
        video: { url: 'https://www.w3schools.com/html/movie.mp4' },
        order: 4,
        author: '赵安全专家',
        createTime: '2024-05-13 14:00',
        status: '待审核',
      },
      {
        id: 105,
        name: '移动应用质量检测',
        desc: '介绍移动应用质量检测的标准和方法，涵盖兼容性测试、用户体验测试、性能测试等',
        cover: { url: 'https://dummyimage.com/200x120/67c23a/fff&text=移动测试' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 5,
        author: '钱移动专家',
        createTime: '2024-05-14 08:20',
        status: '待审核',
      },
      {
        id: 106,
        name: '云计算平台质量评估',
        desc: '深入讲解云计算平台的质量评估标准和方法，包括可用性、性能、安全性等关键指标',
        cover: { url: 'https://dummyimage.com/200x120/f56c6c/fff&text=云平台评估' },
        video: { url: 'https://www.w3schools.com/html/movie.mp4' },
        order: 6,
        author: '孙云专家',
        createTime: '2024-05-15 13:10',
        status: '待审核',
      },
      {
        id: 107,
        name: '大数据系统测试方法',
        desc: '介绍大数据系统的测试策略和方法，包括数据质量测试、性能测试、功能测试等',
        cover: { url: 'https://dummyimage.com/200x120/409eff/fff&text=大数据测试' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 7,
        author: '周数据专家',
        createTime: '2024-05-16 10:00',
        status: '待审核',
      },
      {
        id: 108,
        name: '人工智能系统质量保证',
        desc: '深入讲解AI系统的质量保证方法和标准，包括算法测试、模型验证、数据质量评估等',
        cover: { url: 'https://dummyimage.com/200x120/67c23a/fff&text=AI质量保证' },
        video: { url: 'https://www.w3schools.com/html/movie.mp4' },
        order: 8,
        author: '吴AI专家',
        createTime: '2024-05-17 15:40',
        status: '待审核',
      },
      {
        id: 109,
        name: '区块链技术质量检测',
        desc: '介绍区块链技术的质量检测标准和方法，包括智能合约测试、共识机制验证等',
        cover: { url: 'https://dummyimage.com/200x120/f56c6c/fff&text=区块链检测' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 9,
        author: '郑区块链专家',
        createTime: '2024-05-18 09:00',
        status: '待审核',
      },
      {
        id: 110,
        name: '物联网设备质量评估',
        desc: '深入讲解物联网设备的质量评估标准和方法，包括硬件测试、软件测试、通信测试等',
        cover: { url: 'https://dummyimage.com/200x120/409eff/fff&text=物联网评估' },
        video: { url: 'https://www.w3schools.com/html/movie.mp4' },
        order: 10,
        author: '冯物联网专家',
        createTime: '2024-05-19 11:30',
        status: '待审核',
      },
      {
        id: 111,
        name: 'DevOps质量保障体系',
        desc: '介绍DevOps环境下的质量保障体系构建，包括持续集成、持续测试、持续部署等',
        cover: { url: 'https://dummyimage.com/200x120/67c23a/fff&text=DevOps质量' },
        video: { url: 'https://www.w3schools.com/html/mov_bbb.mp4' },
        order: 11,
        author: '陈DevOps专家',
        createTime: '2024-05-20 16:00',
        status: '待审核',
      },
      {
        id: 112,
        name: '软件测试工具集成平台',
        desc: '深入讲解软件测试工具的集成方法和平台建设，提高测试效率和工具使用效果',
        cover: { url: 'https://dummyimage.com/200x120/f56c6c/fff&text=工具集成' },
        video: { url: 'https://www.w3schools.com/html/movie.mp4' },
        order: 12,
        author: '褚工具专家',
        createTime: '2024-05-21 14:50',
        status: '待审核',
      },
    ])
    const page = ref(1)
    const pageSize = ref(10)
    const pagedData = computed(() => {
      const start = (page.value - 1) * pageSize.value
      return tableData.value.slice(start, start + pageSize.value)
    })
    const onPageChange = (val: number) => { page.value = val }
    const onPageSizeChange = (val: number) => { pageSize.value = val; page.value = 1 }

    // 详情弹窗
    const showDetailDialog = ref(false)
    const detailData = ref<any>({})
    const onShowDetail = (row: any) => {
      detailData.value = row
      showDetailDialog.value = true
    }
    // 审核操作
    const onApprove = (row: any) => {
      ElMessage.success(`课程"${row.name}"已通过审核`)
      // 这里可加接口调用，成功后移除该行
      tableData.value = tableData.value.filter(item => item.id !== row.id)
    }
    const onReject = (row: any) => {
      ElMessage.error(`课程"${row.name}"未通过审核`)
      // 这里可加接口调用，成功后移除该行
      tableData.value = tableData.value.filter(item => item.id !== row.id)
    }
    const goToCourseList = () => {
      router.push({ name: 'courseList' })
    }
    
    // 排序相关
    const sortOrder = ref('createTime')
    const sortDirection = ref('asc')
    const sortOptions = [
      { label: '创建时间', value: 'createTime' },
      { label: '排序值', value: 'order' }
    ]
    
    const onSortChange = () => {
      // 这里可以添加排序逻辑
      console.log('排序方式改变:', sortOrder.value, sortDirection.value)
    }
    
    return {
      tableData, page, pageSize, pagedData, onPageChange, onPageSizeChange,
      showDetailDialog, detailData, onShowDetail, onApprove, onReject,
      goToCourseList, sortOrder, sortDirection, sortOptions, onSortChange
    }
  }
})
</script>

<template>
  <div class="courses-list-page">
    <div class="header-bar">
      <span>待审核课程列表</span>
      <el-button class="audit-entry" type="primary" size="small" @click="goToCourseList">返回课程列表页</el-button>
    </div>
    <!-- 无 operation-bar -->
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
            <el-tag type="warning">待审核</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="审核操作" min-width="140" align="center">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="onApprove(row)">通过</el-button>
            <el-button type="danger" size="small" @click="onReject(row)" style="margin-left: 8px;">不通过</el-button>
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
          <el-tag type="warning">待审核</el-tag>
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

.table-wrapper {
  margin: 32px 32px 0 32px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.03);
  padding: 0 0 0;
  overflow-x: auto;
}

.fixed-table-height {
  /* 10行高度 + 表头，约56px*10+表头=约616px，可根据实际调整 */
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
</style>
