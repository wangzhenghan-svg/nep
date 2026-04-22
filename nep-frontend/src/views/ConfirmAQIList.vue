<template>
  <div class="page-container">
    <h3>确认AQI数据列表</h3>

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
      <el-form-item label="确认日期">
        <el-date-picker
            v-model="queryForm.confirmDate"
            type="date"
            placeholder="请选择日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="handleReset">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据表格 -->
    <el-table :data="tableData" border stripe>
      <el-table-column prop="id" label="编号" width="80" />
      <el-table-column prop="province" label="所在省" />
      <el-table-column prop="city" label="所在市" />
      <el-table-column prop="aqiLevel" label="AQI等级" />
      <el-table-column prop="confirmDate" label="确认日期" />
      <el-table-column prop="confirmTime" label="确认时间" />
      <el-table-column prop="workerName" label="网格员" />
      <el-table-column prop="feedbackContent" label="反馈内容" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button size="small" @click="handleViewDetail(row)">查看详情</el-button>
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
  confirmDate: ''
})

const provinceList = ref([
  { id: '1', name: '辽宁省' },
  { id: '2', name: '北京市' }
])

const cityList = ref([])

const tableData = ref([
  {
    id: 1,
    province: '辽宁省',
    city: '沈阳市',
    aqiLevel: '优',
    confirmDate: '2024-01-01',
    confirmTime: '10:00:00',
    workerName: '网格员A',
    feedbackContent: '空气质量良好'
  },
  {
    id: 2,
    province: '辽宁省',
    city: '大连市',
    aqiLevel: '良',
    confirmDate: '2024-01-02',
    confirmTime: '11:00:00',
    workerName: '网格员B',
    feedbackContent: '轻微污染'
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
  console.log('获取AQI数据', queryForm)
}

const handleProvinceChange = (value) => {
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
    confirmDate: ''
  })
  cityList.value = []
  fetchData()
}

const handleViewDetail = (row) => {
  router.push(`/confirm-aqi/detail/${row.id}`)
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
