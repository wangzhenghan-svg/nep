<template>
  <div class="statistics-page">
    <h3>AQI空气质量指数级数分布</h3>

    <el-table :data="tableData" border stripe>
      <el-table-column prop="level" label="AQI指数等级" />
      <el-table-column prop="count" label="AQI数据记录统计" />
      <el-table-column prop="percentage" label="AQI超标概率百分比统计" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const tableData = ref([
  { level: 1, count: 100, percentage: '10%' },
  { level: 2, count: 200, percentage: '20%' },
  { level: 3, count: 150, percentage: '15%' },
  { level: 4, count: 80, percentage: '8%' },
  { level: 5, count: 50, percentage: '5%' },
  { level: 6, count: 20, percentage: '2%' }
])

onMounted(() => {
  fetchData()
})

const fetchData = () => {
  // TODO: 调用后端API获取统计数据
  console.log('获取AQI级数分布数据')
}
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}
</style>
