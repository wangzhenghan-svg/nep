<template>
  <div class="statistics-page">
    <h3>其它数据统计</h3>

    <el-card class="stat-card">
      <template #header>
        <div class="card-header">
          <span>检测数据实时统计 - 省和大城市网格覆盖率</span>
        </div>
      </template>
      <div class="coverage-info">
        <el-progress :percentage="coverageRate" :color="customColors" />
        <p>当前覆盖率: {{ coverageRate }}%</p>
      </div>
    </el-card>

    <el-table :data="tableData" border stripe style="margin-top: 20px;">
      <el-table-column prop="province" label="省份" />
      <el-table-column prop="coverage" label="覆盖率" />
      <el-table-column prop="totalCount" label="总网格数" />
      <el-table-column prop="activeCount" label="活跃网格数" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const coverageRate = ref(75)

const customColors = [
  { color: '#f56c6c', percentage: 20 },
  { color: '#e6a23c', percentage: 40 },
  { color: '#5cb87a', percentage: 60 },
  { color: '#1989fa', percentage: 80 },
  { color: '#6f7ad3', percentage: 100 }
]

const tableData = ref([
  {
    province: '辽宁省',
    coverage: '85%',
    totalCount: 1000,
    activeCount: 850
  },
  {
    province: '北京市',
    coverage: '92%',
    totalCount: 800,
    activeCount: 736
  }
])

onMounted(() => {
  fetchData()
})

const fetchData = () => {
  // TODO: 调用后端API获取统计数据
  console.log('获取其它统计数据')
}
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}

.stat-card {
  margin-bottom: 20px;
}

.coverage-info {
  padding: 20px;
}
</style>
