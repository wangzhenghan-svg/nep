<template>
  <div class="dashboard-container">
    <div v-if="loading" class="global-loading">
      <div class="loading-spinner"></div>
      <span>数据加载中...</span>
    </div>

    <header class="dashboard-header">
      <h1 class="header-title">🌍 东软空气质量环保公众监督平台</h1>
      <div class="header-time">
        <span class="time-text">数据更新时间: {{ updateTime }}</span>
        <span class="auto-refresh">自动刷新: 30秒</span>
      </div>
    </header>

    <div class="dashboard-content">
      <div class="dashboard-row top-row">
        <div class="dashboard-col left-col">
          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">📊 全国省网格覆盖率(%)</span>
            </div>
            <div ref="provinceCoverageChart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">️ 全国大城市网格覆盖率(%)</span>
            </div>
            <div ref="cityCoverageChart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">🎯 空气质量指数级别分布</span>
            </div>
            <div ref="aqiLevelChart" class="chart-container"></div>
          </div>
        </div>

        <div class="dashboard-col center-col">
          <div class="chart-panel map-panel">
            <div class="panel-header">
              <span class="panel-title">🗺️ 中国地图 - 空气质量超标分布</span>
            </div>
            <div ref="mapChart" class="map-container"></div>
          </div>

          <div class="chart-panel realtime-panel">
            <div class="panel-header">
              <span class="panel-title">⚡ 空气质量检测实时统计</span>
            </div>
            <div class="realtime-stats">
              <div class="stat-item">
                <div class="stat-icon">📈</div>
                <div class="stat-value">{{ realtimeStats.totalCount || 0 }}</div>
                <div class="stat-label">检测总数量</div>
              </div>
              <div class="stat-item">
                <div class="stat-icon">✅</div>
                <div class="stat-value success">{{ realtimeStats.passCount || 0 }}</div>
                <div class="stat-label">合格数量</div>
              </div>
              <div class="stat-item">
                <div class="stat-icon">⚠️</div>
                <div class="stat-value danger">{{ realtimeStats.exceedCount || 0 }}</div>
                <div class="stat-label">超标数量</div>
              </div>
              <div class="stat-item">
                <div class="stat-icon">📊</div>
                <div class="stat-value warning">{{ passRate }}%</div>
                <div class="stat-label">合格率</div>
              </div>
            </div>
          </div>
        </div>

        <div class="dashboard-col right-col">
          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">💨 PM2.5 浓度超标统计</span>
            </div>
            <div ref="pm25Chart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">🏭 SO2 浓度超标统计</span>
            </div>
            <div ref="so2Chart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">️ CO 浓度超标统计</span>
            </div>
            <div ref="coChart" class="chart-container"></div>
          </div>
        </div>
      </div>

      <div class="dashboard-row bottom-row">
        <div class="chart-panel full-width">
          <div class="panel-header">
            <span class="panel-title">📈 12个月内空气质量超标趋势</span>
          </div>
          <div ref="trendChart" class="chart-container trend-chart"></div>
        </div>
      </div>
    </div>

    <div class="dashboard-footer">
      <span class="footer-text">© 2024 东软集团 - 空气质量环保公众监督平台</span>
      <span class="footer-text">技术支持: Neusoft</span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount, nextTick } from 'vue'
import * as echarts from 'echarts'
import { statisticsAPI } from '@/api/statistics'

const loading = ref(false)

const provinceCoverageChart = ref(null)
const cityCoverageChart = ref(null)
const aqiLevelChart = ref(null)
const mapChart = ref(null)
const pm25Chart = ref(null)
const so2Chart = ref(null)
const coChart = ref(null)
const trendChart = ref(null)

const realtimeStats = ref({
  totalCount: 0,
  passCount: 0,
  exceedCount: 0
})
const updateTime = ref('')

let refreshTimer = null
const chartMap = new Map()

const passRate = computed(() => {
  if (!realtimeStats.value.totalCount) return '0.00'
  return ((realtimeStats.value.passCount / realtimeStats.value.totalCount) * 100).toFixed(2)
})

const MOCK_DATA = {
  gridCoverage: [
    { regionType: 'province', regionName: '北京市', coverageRate: 88.5 },
    { regionType: 'province', regionName: '天津市', coverageRate: 92.3 },
    { regionType: 'province', regionName: '河北省', coverageRate: 76.8 },
    { regionType: 'province', regionName: '辽宁省', coverageRate: 85.5 },
    { regionType: 'province', regionName: '吉林省', coverageRate: 78.2 },
    { regionType: 'province', regionName: '黑龙江省', coverageRate: 82.1 },
    { regionType: 'province', regionName: '上海市', coverageRate: 95.1 },
    { regionType: 'province', regionName: '江苏省', coverageRate: 89.7 },
    { regionType: 'province', regionName: '浙江省', coverageRate: 91.2 },
    { regionType: 'province', regionName: '广东省', coverageRate: 93.6 },
    { regionType: 'city', regionName: '沈阳市', coverageRate: 92.3 },
    { regionType: 'city', regionName: '大连市', coverageRate: 88.7 },
    { regionType: 'city', regionName: '鞍山市', coverageRate: 85.4 },
    { regionType: 'city', regionName: '抚顺市', coverageRate: 83.2 },
    { regionType: 'city', regionName: '本溪市', coverageRate: 87.5 }
  ],
  provinceStats: [
    { provinceCode: '11', provinceName: '北京市', so2ExceedCount: 12, pm25ExceedCount: 28, coExceedCount: 8, aqiExceedCount: 35 },
    { provinceCode: '12', provinceName: '天津市', so2ExceedCount: 15, pm25ExceedCount: 32, coExceedCount: 10, aqiExceedCount: 42 },
    { provinceCode: '13', provinceName: '河北省', so2ExceedCount: 25, pm25ExceedCount: 45, coExceedCount: 18, aqiExceedCount: 58 },
    { provinceCode: '21', provinceName: '辽宁省', so2ExceedCount: 18, pm25ExceedCount: 38, coExceedCount: 12, aqiExceedCount: 48 },
    { provinceCode: '22', provinceName: '吉林省', so2ExceedCount: 14, pm25ExceedCount: 30, coExceedCount: 9, aqiExceedCount: 38 },
    { provinceCode: '23', provinceName: '黑龙江省', so2ExceedCount: 16, pm25ExceedCount: 35, coExceedCount: 11, aqiExceedCount: 44 },
    { provinceCode: '31', provinceName: '上海市', so2ExceedCount: 8, pm25ExceedCount: 22, coExceedCount: 6, aqiExceedCount: 28 },
    { provinceCode: '32', provinceName: '江苏省', so2ExceedCount: 13, pm25ExceedCount: 29, coExceedCount: 9, aqiExceedCount: 36 },
    { provinceCode: '33', provinceName: '浙江省', so2ExceedCount: 10, pm25ExceedCount: 25, coExceedCount: 7, aqiExceedCount: 32 },
    { provinceCode: '44', provinceName: '广东省', so2ExceedCount: 20, pm25ExceedCount: 42, coExceedCount: 15, aqiExceedCount: 55 }
  ],
  aqiLevel: [
    { level: 1, levelName: '一级(优)', count: 125, percentage: 27.41 },
    { level: 2, levelName: '二级(良)', count: 186, percentage: 40.79 },
    { level: 3, levelName: '三级(轻度污染)', count: 68, percentage: 14.92 },
    { level: 4, levelName: '四级(中度污染)', count: 32, percentage: 7.02 },
    { level: 5, levelName: '五级(重度污染)', count: 28, percentage: 6.14 },
    { level: 6, levelName: '六级(严重污染)', count: 17, percentage: 3.72 }
  ],
  trend: [
    { month: '2024-01', exceedCount: 65 },
    { month: '2024-02', exceedCount: 72 },
    { month: '2024-03', exceedCount: 58 },
    { month: '2024-04', exceedCount: 48 },
    { month: '2024-05', exceedCount: 42 },
    { month: '2024-06', exceedCount: 35 },
    { month: '2024-07', exceedCount: 28 },
    { month: '2024-08', exceedCount: 32 },
    { month: '2024-09', exceedCount: 45 },
    { month: '2024-10', exceedCount: 58 },
    { month: '2024-11', exceedCount: 68 },
    { month: '2024-12', exceedCount: 75 }
  ],
  realtime: { totalCount: 1256, passCount: 1098, exceedCount: 158 }
}

onMounted(async () => {
  await nextTick()
  initCharts()
  await fetchAllData()

  refreshTimer = setInterval(() => {
    fetchAllData()
  }, 30000)

  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  if (refreshTimer) clearInterval(refreshTimer)
  chartMap.forEach(c => c?.dispose())
  window.removeEventListener('resize', handleResize)
})

function initCharts() {
  const list = [
    { name: 'provinceCoverage', ref: provinceCoverageChart },
    { name: 'cityCoverage', ref: cityCoverageChart },
    { name: 'aqiLevel', ref: aqiLevelChart },
    { name: 'map', ref: mapChart },
    { name: 'pm25', ref: pm25Chart },
    { name: 'so2', ref: so2Chart },
    { name: 'co', ref: coChart },
    { name: 'trend', ref: trendChart }
  ]
  list.forEach(({ name, ref }) => {
    if (ref.value) {
      const chart = echarts.init(ref.value)
      chartMap.set(name, chart)
    }
  })
}

async function fetchAllData() {
  loading.value = true
  try {
    const [so2, pm25, co, aqi, level, trend, realtime, grid] = await Promise.all([
      statisticsAPI.getProvinceSO2Stats(),
      statisticsAPI.getProvincePM25Stats(),
      statisticsAPI.getProvinceCOStats(),
      statisticsAPI.getProvinceAQIStats(),
      statisticsAPI.getAQILevelDistribution(),
      statisticsAPI.getAQITrend(),
      statisticsAPI.getRealtimeStats(),
      statisticsAPI.getGridCoverage()
    ])

    realtimeStats.value = realtime || MOCK_DATA.realtime
    updateTime.value = new Date().toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    })

    renderAllCharts({ so2, pm25, co, aqi, level, trend, grid })
  } catch (err) {
    console.error('接口异常，使用模拟数据', err)
    realtimeStats.value = MOCK_DATA.realtime
    updateTime.value = new Date().toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    })
    renderAllCharts({
      so2: MOCK_DATA.provinceStats,
      pm25: MOCK_DATA.provinceStats,
      co: MOCK_DATA.provinceStats,
      aqi: MOCK_DATA.provinceStats,
      level: MOCK_DATA.aqiLevel,
      trend: MOCK_DATA.trend,
      grid: MOCK_DATA.gridCoverage
    })
  } finally {
    loading.value = false
  }
}

function renderAllCharts(data) {
  renderProvinceCoverageChart(data.grid)
  renderCityCoverageChart(data.grid)
  renderAQILevelChart(data.level)
  renderPM25Chart(data.pm25)
  renderSO2Chart(data.so2)
  renderCOChart(data.co)
  renderTrendChart(data.trend)
  setTimeout(() => renderMapChart(data.aqi), 200)
}

const handleResize = () => {
  chartMap.forEach(c => c?.resize())
}

function renderProvinceCoverageChart(data) {
  const chart = chartMap.get('provinceCoverage')
  if (!chart) return

  let provinceData = (data || []).filter(i => i.regionType === 'province')
  if (!provinceData.length) {
    provinceData = MOCK_DATA.gridCoverage.filter(i => i.regionType === 'province')
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      borderWidth: 1,
      textStyle: { color: '#333' },
      formatter: '{b}<br/>覆盖率: <b>{c}%</b>'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '18%',
      top: '8%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: provinceData.map(i => i.regionName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 10
      },
      axisLine: { lineStyle: { color: '#ddd' } },
      axisTick: { alignWithLabel: true }
    },
    yAxis: {
      type: 'value',
      name: '覆盖率(%)',
      min: 0,
      max: 100,
      interval: 20,
      axisLabel: {
        color: '#666',
        formatter: '{value}%'
      },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } }
    },
    series: [{
      type: 'bar',
      data: provinceData.map(i => i.coverageRate),
      barWidth: '50%',
      label: {
        show: true,
        position: 'top',
        formatter: '{c}%',
        color: '#667eea',
        fontSize: 10,
        fontWeight: 'bold'
      },
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#667eea' },
          { offset: 1, color: '#764ba2' }
        ]),
        borderRadius: [6, 6, 0, 0]
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(102, 126, 234, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option, true)
}

function renderCityCoverageChart(data) {
  const chart = chartMap.get('cityCoverage')
  if (!chart) return

  let cityData = (data || []).filter(i => i.regionType === 'city')
  if (!cityData.length) {
    cityData = MOCK_DATA.gridCoverage.filter(i => i.regionType === 'city')
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      borderWidth: 1,
      textStyle: { color: '#333' },
      formatter: '{b}<br/>覆盖率: <b>{c}%</b>'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '18%',
      top: '8%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: cityData.map(i => i.regionName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 10
      },
      axisLine: { lineStyle: { color: '#ddd' } },
      axisTick: { alignWithLabel: true }
    },
    yAxis: {
      type: 'value',
      name: '覆盖率(%)',
      min: 0,
      max: 100,
      interval: 20,
      axisLabel: {
        color: '#666',
        formatter: '{value}%'
      },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } }
    },
    series: [{
      type: 'bar',
      data: cityData.map(i => i.coverageRate),
      barWidth: '50%',
      label: {
        show: true,
        position: 'top',
        formatter: '{c}%',
        color: '#43e97b',
        fontSize: 10,
        fontWeight: 'bold'
      },
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#43e97b' },
          { offset: 1, color: '#38f9d7' }
        ]),
        borderRadius: [6, 6, 0, 0]
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(67, 233, 123, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option, true)
}

function renderAQILevelChart(data) {
  const chart = chartMap.get('aqiLevel')
  if (!chart) return

  let levelData = data || MOCK_DATA.aqiLevel
  if (!levelData.length) {
    levelData = MOCK_DATA.aqiLevel
  }

  const colors = ['#67C23A', '#E6A23C', '#F56C6C', '#909399', '#C0C4CC', '#409EFF']

  const option = {
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      borderWidth: 1,
      textStyle: { color: '#333' },
      formatter: '{b}<br/>数量: {c}<br/>占比: {d}%'
    },
    legend: {
      orient: 'vertical',
      right: '2%',
      top: 'center',
      textStyle: { color: '#666', fontSize: 10 },
      itemWidth: 12,
      itemHeight: 12
    },
    series: [{
      type: 'pie',
      radius: ['35%', '65%'],
      center: ['40%', '50%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 8,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        formatter: '{b}\n{d}%',
        color: '#666',
        fontSize: 10
      },
      labelLine: {
        length: 15,
        length2: 10
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 12,
          fontWeight: 'bold'
        },
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.2)'
        }
      },
      data: levelData.map((item, index) => ({
        value: item.count,
        name: item.levelName,
        itemStyle: { color: colors[index % colors.length] }
      }))
    }]
  }
  chart.setOption(option, true)
}

function renderPM25Chart(data) {
  const chart = chartMap.get('pm25')
  if (!chart) return

  let pm25Data = data || MOCK_DATA.provinceStats
  if (!pm25Data.length) {
    pm25Data = MOCK_DATA.provinceStats
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      borderWidth: 1,
      textStyle: { color: '#333' },
      formatter: '{b}<br/>超标次数: <b>{c}次</b>'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '8%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: pm25Data.map(i => i.provinceName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 9
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标次数',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } }
    },
    series: [{
      type: 'bar',
      data: pm25Data.map(i => i.pm25ExceedCount),
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#fa709a' },
          { offset: 1, color: '#fee140' }
        ]),
        borderRadius: [6, 6, 0, 0]
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(250, 112, 154, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option, true)
}

function renderSO2Chart(data) {
  const chart = chartMap.get('so2')
  if (!chart) return

  let so2Data = data || MOCK_DATA.provinceStats
  if (!so2Data.length) {
    so2Data = MOCK_DATA.provinceStats
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      borderWidth: 1,
      textStyle: { color: '#333' },
      formatter: '{b}<br/>超标次数: <b>{c}次</b>'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '8%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: so2Data.map(i => i.provinceName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 9
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标次数',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } }
    },
    series: [{
      type: 'bar',
      data: so2Data.map(i => i.so2ExceedCount),
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#43e97b' },
          { offset: 1, color: '#38f9d7' }
        ]),
        borderRadius: [6, 6, 0, 0]
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(67, 233, 123, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option, true)
}

function renderCOChart(data) {
  const chart = chartMap.get('co')
  if (!chart) return

  let coData = data || MOCK_DATA.provinceStats
  if (!coData.length) {
    coData = MOCK_DATA.provinceStats
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      borderWidth: 1,
      textStyle: { color: '#333' },
      formatter: '{b}<br/>超标次数: <b>{c}次</b>'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '8%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: coData.map(i => i.provinceName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 9
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标次数',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } }
    },
    series: [{
      type: 'bar',
      data: coData.map(i => i.coExceedCount),
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#667eea' },
          { offset: 1, color: '#764ba2' }
        ]),
        borderRadius: [6, 6, 0, 0]
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(102, 126, 234, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option, true)
}

function renderTrendChart(data) {
  const chart = chartMap.get('trend')
  if (!chart) return

  let trendData = data || MOCK_DATA.trend
  if (!trendData.length) {
    trendData = MOCK_DATA.trend
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      borderWidth: 1,
      textStyle: { color: '#333' },
      formatter: '{b}<br/>超标次数: <b>{c}次</b>'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '15%',
      top: '8%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: trendData.map(i => i.month),
      axisLabel: {
        color: '#666',
        fontSize: 11,
        rotate: 30
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标次数',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } }
    },
    series: [{
      name: '超标次数',
      type: 'line',
      smooth: true,
      symbol: 'circle',
      symbolSize: 8,
      data: trendData.map(i => i.exceedCount),
      lineStyle: {
        width: 3,
        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          { offset: 0, color: '#667eea' },
          { offset: 1, color: '#764ba2' }
        ])
      },
      itemStyle: {
        color: '#667eea',
        borderWidth: 2,
        borderColor: '#fff'
      },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(102, 126, 234, 0.3)' },
          { offset: 0.5, color: 'rgba(118, 75, 162, 0.15)' },
          { offset: 1, color: 'rgba(102, 126, 234, 0.05)' }
        ])
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(102, 126, 234, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option, true)
}

function renderMapChart(data) {
  const chart = chartMap.get('map')
  if (!chart) return

  let mapData = data || MOCK_DATA.provinceStats
  if (!mapData.length) {
    mapData = MOCK_DATA.provinceStats
  }

  fetch('https://geo.datav.aliyun.com/areas_v3/bound/100000_full.json')
      .then(res => res.json())
      .then(map => {
        echarts.registerMap('china', map)

        const option = {
          tooltip: {
            trigger: 'item',
            backgroundColor: 'rgba(255, 255, 255, 0.95)',
            borderColor: '#e0e0e0',
            borderWidth: 1,
            textStyle: { color: '#333' },
            formatter: '{b}<br/>AQI超标次数: <b>{c}次</b>'
          },
          visualMap: {
            min: 0,
            max: 80,
            left: 'left',
            bottom: 'bottom',
            text: ['高', '低'],
            calculable: true,
            inRange: {
              color: ['#e3f2fd', '#90caf9', '#42a5f5', '#1e88e5', '#1565c0', '#0d47a1']
            },
            textStyle: { color: '#666' }
          },
          series: [{
            type: 'map',
            map: 'china',
            roam: true,
            zoom: 1.2,
            center: [104.114129, 35.863638],
            label: {
              show: true,
              fontSize: 10,
              color: '#333'
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1,
              areaColor: '#f0f0f0'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 12,
                fontWeight: 'bold',
                color: '#333'
              },
              itemStyle: {
                areaColor: '#ffd700',
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.3)'
              }
            },
            data: mapData.map(i => ({
              name: i.provinceName,
              value: i.aqiExceedCount
            }))
          }]
        }
        chart.setOption(option, true)
      })
      .catch(err => {
        console.error('地图数据加载失败', err)
        chart.setOption({
          graphic: {
            elements: [{
              type: 'text',
              style: {
                text: '地图数据加载失败\n请检查网络连接',
                x: 'center',
                y: 'center',
                fill: '#999',
                fontSize: 14
              }
            }]
          }
        })
      })
}
</script>

<style scoped lang="scss">
.dashboard-container {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4edf5 100%);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  position: relative;
}

.global-loading {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.9);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  color: #667eea;
  font-size: 14px;

  .loading-spinner {
    width: 40px;
    height: 40px;
    border: 4px solid #e3e8ff;
    border-top-color: #667eea;
    border-radius: 50%;
    animation: rotate 0.8s linear infinite;
    margin-bottom: 12px;
  }
}

@keyframes rotate {
  to { transform: rotate(360deg) }
}

.dashboard-header {
  height: 65px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 2px 12px rgba(102, 126, 234, 0.3);

  .header-title {
    font-size: 24px;
    font-weight: 700;
    color: #fff;
    letter-spacing: 2px;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .header-time {
    display: flex;
    gap: 20px;

    .time-text,
    .auto-refresh {
      color: rgba(255, 255, 255, 0.9);
      font-size: 12px;
      padding: 4px 12px;
      background: rgba(255, 255, 255, 0.15);
      border-radius: 12px;
    }
  }
}

.dashboard-content {
  flex: 1;
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  overflow: hidden;
}

.dashboard-row {
  display: flex;
  gap: 16px;

  &.top-row {
    flex: 1;
  }

  &.bottom-row {
    height: 280px;
  }
}

.dashboard-col {
  display: flex;
  flex-direction: column;
  gap: 16px;

  &.left-col,
  &.right-col {
    flex: 0 0 320px;
  }

  &.center-col {
    flex: 1;
  }
}

.chart-panel {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  border: 1px solid #e8ecf1;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px rgba(102, 126, 234, 0.15);
    border-color: #667eea;
  }

  &.map-panel {
    flex: 1;
  }

  &.realtime-panel {
    height: 150px;
  }

  &.full-width {
    height: 100%;
  }
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 12px;
  border-bottom: 2px solid #f0f2f5;
  margin-bottom: 12px;

  .panel-title {
    font-size: 15px;
    font-weight: 600;
    color: #333;
  }
}

.chart-container {
  width: 100%;
  height: 180px;
}

.map-container {
  width: 100%;
  height: calc(100% - 44px);
}

.trend-chart {
  height: 220px;
}

.realtime-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
  height: 100%;
  align-items: center;
}

.stat-item {
  text-align: center;
  padding: 12px 8px;
  border-radius: 10px;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border: 1px solid #e9ecef;
  transition: all 0.3s ease;

  &:hover {
    background: linear-gradient(135deg, #e9ecef 0%, #dee2e6 100%);
    transform: translateY(-3px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .stat-icon {
    font-size: 20px;
    margin-bottom: 6px;
  }

  .stat-value {
    font-size: 28px;
    font-weight: 700;
    margin-bottom: 6px;
    color: #667eea;

    &.success {
      color: #67c23a;
    }

    &.danger {
      color: #f56c6c;
    }

    &.warning {
      color: #e6a23c;
    }
  }

  .stat-label {
    color: #666;
    font-size: 11px;
    font-weight: 500;
  }
}

.dashboard-footer {
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  background: #fff;
  border-top: 1px solid #e8ecf1;

  .footer-text {
    color: #999;
    font-size: 12px;
  }
}
</style>
