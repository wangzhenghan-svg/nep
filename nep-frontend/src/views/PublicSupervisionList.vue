<template>
  <div class="page-container">
    <h3>公众监督数据列表</h3>

    <!-- 查询表单 -->
    <el-form :model="queryForm" inline class="query-form">
      <el-form-item label="省区域">
        <el-select v-model="queryForm.provinceId" placeholder="请选择" @change="handleProvinceChange">
          <el-option
              v-for="item in provinceList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="市区域">
        <el-select v-model="queryForm.cityId" placeholder="请选择">
          <el-option
              v-for="item in cityList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预估等级">
        <el-select v-model="queryForm.level" placeholder="请选择">
          <el-option label="一级" value="1" />
          <el-option label="二级" value="2" />
          <el-option label="三级" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="反馈日期">
        <el-date-picker
            v-model="queryForm.feedbackDate"
            type="date"
            placeholder="请选择日期"
        />
      </el-form-item>
      <el-form-item label="是否指派">
        <el-radio-group v-model="queryForm.isAssigned">
          <el-radio :label="null">全部</el-radio>
          <el-radio :label="0">未指派</el-radio>
          <el-radio :label="1">已指派</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="handleReset">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据表格 -->
    <el-table :data="tableData" border stripe>
      <el-table-column prop="id" label="编号" width="80" />
      <el-table-column prop="feedbackName" label="反馈者姓名" />
      <el-table-column prop="province" label="所在省" />
      <el-table-column prop="city" label="所在市" />
      <el-table-column prop="estimatedLevel" label="预估污染等级" />
      <el-table-column prop="feedbackDate" label="反馈日期" />
      <el-table-column prop="feedbackTime" label="反馈时间" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button size="small" @click="handleViewDetail(row)">查看详情</el-button>
          <el-button
              v-if="!row.isAssigned"
              size="small"
              type="primary"
              @click="handleAssign(row)"
          >
            指派网格员
          </el-button>
          <el-button
              v-else
              size="small"
              type="warning"
              @click="handleReassign(row)"
          >
            重新指派
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="prev, pager, next, total"
        @current-change="handlePageChange"
        class="pagination"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const queryForm = reactive({
  provinceId: '',
  cityId: '',
  level: '',
  feedbackDate: '',
  isAssigned: null
})

const provinceList = ref([
  { id: '1', name: '辽宁省' },
  { id: '2', name: '北京市' }
])

const cityList = ref([])

const tableData = ref([
  {
    id: 1,
    feedbackName: '张三',
    province: '辽宁省',
    city: '沈阳市',
    estimatedLevel: '一级污染',
    feedbackDate: '2024-01-01',
    feedbackTime: '10:00:00',
    isAssigned: false
  },
  {
    id: 2,
    feedbackName: '李四',
    province: '辽宁省',
    city: '大连市',
    estimatedLevel: '轻度污染',
    feedbackDate: '2024-01-02',
    feedbackTime: '11:00:00',
    isAssigned: true
  }
])

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

onMounted(() => {
  fetchData()
})

const fetchData = () => {
  // TODO: 调用后端API获取数据
  console.log('获取数据', queryForm)
}

const handleProvinceChange = (value) => {
  // TODO: 根据省份查询城市列表
  cityList.value = [
    { id: '1', name: '沈阳市' },
    { id: '2', name: '大连市' }
  ]
  queryForm.cityId = ''
}

const handleQuery = () => {
  currentPage.value = 1
  fetchData()
}

const handleReset = () => {
  Object.assign(queryForm, {
    provinceId: '',
    cityId: '',
    level: '',
    feedbackDate: '',
    isAssigned: null
  })
  cityList.value = []
  fetchData()
}

const handleViewDetail = (row) => {
  router.push(`/public-supervision/detail/${row.id}`)
}

const handleAssign = (row) => {
  router.push(`/assign-worker/${row.id}`)
}

const handleReassign = (row) => {
  router.push(`/assign-worker/${row.id}?reassign=true`)
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchData()
}
</script>

<style scoped>
.page-container {
  padding: 20px;
}

.query-form {
  margin-bottom: 20px;
  background: #f5f7fa;
  padding: 20px;
  border-radius: 4px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
