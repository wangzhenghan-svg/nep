<template>
  <div class="statistics-page">
    <h3>AQI超标趋势统计</h3>

    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'

const chartRef = ref(null)
let chartInstance = null

onMounted(() => {
  initChart()
  fetchData()
})

onUnmounted(() => {
  if (chartInstance) {
    chartInstance.dispose()
  }
})

const initChart = () => {
  chartInstance = echarts.init(chartRef.value)

  const option = {
    title: {
      text: 'AQI超标趋势统计'
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '超标次数',
        type: 'line',
        data: [120, 132, 101, 134, 90, 230, 210, 180, 150, 170, 190, 200],
        smooth: true
      }
    ]
  }

  chartInstance.setOption(option)
}

const fetchData = () => {
  // TODO: 调用后端API获取趋势数据
  console.log('获取AQI趋势数据')
}
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}

.chart-container {
  width: 100%;
  height: 500px;
}
</style>
