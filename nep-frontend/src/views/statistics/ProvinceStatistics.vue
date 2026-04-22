<template>
  <div class="statistics-page">
    <h3>省分组分项检查统计</h3>

    <el-table :data="tableData" border stripe>
      <el-table-column prop="provinceCode" label="省编号" />
      <el-table-column prop="provinceName" label="省名称" />
      <el-table-column prop="totalAQI" label="总AQI累计统计" />
      <el-table-column prop="SO2" label="SO2超标累计" />
      <el-table-column prop="CO" label="CO超标累计" />
      <el-table-column prop="PM25" label="PM2.5超标累计" />
      <el-table-column prop="AQICount" label="AQI超标统计" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const tableData = ref([
  {
    provinceCode: '21',
    provinceName: '辽宁省',
    totalAQI: 1250,
    SO2: 15,
    CO: 20,
    PM25: 30,
    AQICount: 65
  }
])

onMounted(() => {
  fetchData()
})

const fetchData = () => {
  // TODO: 调用后端API获取统计数据
  console.log('获取省分组统计数据')
}
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}
</style>
