<template>
  <div class="dashboard-container">
    <header class="dashboard-header">
      <h1 class="header-title">东软空气质量环保公众监督平台</h1>
    </header>

    <div class="dashboard-content">
      <div class="dashboard-row top-row">
        <div class="dashboard-col left-col">
          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">全国省网格覆盖率(%)</span>
            </div>
            <div ref="provinceCoverageChart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">全国大城市网格覆盖率(%)</span>
            </div>
            <div ref="cityCoverageChart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">空气质量指数级别分布</span>
            </div>
            <div ref="aqiLevelChart" class="chart-container"></div>
          </div>
        </div>

        <div class="dashboard-col center-col">
          <div class="chart-panel map-panel">
            <div class="panel-header">
              <span class="panel-title">中国地图</span>
            </div>
            <div ref="mapChart" class="map-container"></div>
          </div>

          <div class="chart-panel realtime-panel">
            <div class="panel-header">
              <span class="panel-title">空气质量检测实时统计</span>
            </div>
            <div class="realtime-stats">
              <div class="stat-item">
                <div class="stat-value">{{ realtimeStats.totalCount || 0 }}</div>
                <div class="stat-label">检测总数量</div>
              </div>
              <div class="stat-item">
                <div class="stat-value success">{{ realtimeStats.passCount || 0 }}</div>
                <div class="stat-label">合格数量</div>
              </div>
              <div class="stat-item">
                <div class="stat-value danger">{{ realtimeStats.exceedCount || 0 }}</div>
                <div class="stat-label">污染数量</div>
              </div>
            </div>
          </div>
        </div>

        <div class="dashboard-col right-col">
          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">PM2.5 浓度超标累计</span>
            </div>
            <div ref="pm25Chart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">SO2 浓度超标累计</span>
            </div>
            <div ref="so2Chart" class="chart-container"></div>
          </div>

          <div class="chart-panel">
            <div class="panel-header">
              <span class="panel-title">CO 浓度超标累计</span>
            </div>
            <div ref="coChart" class="chart-container"></div>
          </div>
        </div>
      </div>

      <div class="dashboard-row bottom-row">
        <div class="chart-panel full-width">
          <div class="panel-header">
            <span class="panel-title">12个月内空气质量超标趋势</span>
          </div>
          <div ref="trendChart" class="chart-container trend-chart"></div>
        </div>
      </div>
    </div>

    <div class="dashboard-footer">
      <span class="footer-text">数据更新时间: {{ updateTime }}</span>
      <span class="footer-text">刷新间隔: 3秒</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import { statisticsAPI } from '@/api/statistics'

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

const updateTime = ref(new Date().toLocaleString('zh-CN'))

let refreshTimer = null
const chartMap = new Map()

onMounted(() => {
  initCharts()
  fetchAllData()
  refreshTimer = setInterval(() => {
    fetchAllData()
  }, 3000)

  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  if (refreshTimer) {
    clearInterval(refreshTimer)
  }
  chartMap.forEach(chart => {
    if (chart) chart.dispose()
  })
  chartMap.clear()
  window.removeEventListener('resize', handleResize)
})

const handleResize = () => {
  chartMap.forEach(chart => {
    if (chart) chart.resize()
  })
}

const initCharts = () => {
  const charts = [
    { name: 'provinceCoverage', ref: provinceCoverageChart },
    { name: 'cityCoverage', ref: cityCoverageChart },
    { name: 'aqiLevel', ref: aqiLevelChart },
    { name: 'map', ref: mapChart },
    { name: 'pm25', ref: pm25Chart },
    { name: 'so2', ref: so2Chart },
    { name: 'co', ref: coChart },
    { name: 'trend', ref: trendChart }
  ]

  charts.forEach(({ name, ref }) => {
    if (ref.value) {
      const chart = echarts.init(ref.value)
      chartMap.set(name, chart)
    }
  })
}

const fetchAllData = async () => {
  try {
    console.log('[Dashboard] 开始获取数据...')

    const [
      provinceSO2Res,
      provincePM25Res,
      provinceCORes,
      provinceAQIRes,
      aqiLevelRes,
      aqiTrendRes,
      realtimeRes,
      gridCoverageRes
    ] = await Promise.all([
      statisticsAPI.getProvinceSO2Stats(),
      statisticsAPI.getProvincePM25Stats(),
      statisticsAPI.getProvinceCOStats(),
      statisticsAPI.getProvinceAQIStats(),
      statisticsAPI.getAQILevelDistribution(),
      statisticsAPI.getAQITrend(),
      statisticsAPI.getRealtimeStats(),
      statisticsAPI.getGridCoverage()
    ])

    console.log('[Dashboard] 数据获取成功')
    console.log('[ProvinceSO2]', provinceSO2Res)
    console.log('[GridCoverage]', gridCoverageRes)
    console.log('[Realtime]', realtimeRes)

    if (realtimeRes && realtimeRes.totalCount !== undefined) {
      realtimeStats.value = realtimeRes
    }

    updateTime.value = new Date().toLocaleString('zh-CN')

    const gridData = gridCoverageRes || []
    const provinceData = provinceAQIRes || []
    const aqiLevelData = aqiLevelRes || []
    const trendData = aqiTrendRes || []

    console.log('[渲染数据] gridData:', gridData)
    console.log('[渲染数据] provinceData:', provinceData)

    renderProvinceCoverageChart(gridData)
    renderCityCoverageChart(gridData)
    renderAQILevelChart(aqiLevelData)
    renderPM25Chart(provincePM25Res || [])
    renderSO2Chart(provinceSO2Res || [])
    renderCOChart(provinceCORes || [])
    renderTrendChart(trendData)

    setTimeout(() => {
      renderMapChart(provinceData)
    }, 100)

  } catch (error) {
    console.error('[Dashboard] 获取数据失败，加载模拟数据:', error)
    loadMockData()
  }
}

const loadMockData = () => {
  updateTime.value = new Date().toLocaleString('zh-CN')

  const mockGridCoverage = [
    { regionType: 'province', regionName: '辽宁省', coverageRate: 85.5 },
    { regionType: 'province', regionName: '吉林省', coverageRate: 78.2 },
    { regionType: 'province', regionName: '黑龙江省', coverageRate: 82.1 },
    { regionType: 'province', regionName: '河北省', coverageRate: 76.8 },
    { regionType: 'province', regionName: '山东省', coverageRate: 80.3 },
    { regionType: 'city', regionName: '沈阳市', coverageRate: 92.3 },
    { regionType: 'city', regionName: '大连市', coverageRate: 88.7 },
    { regionType: 'city', regionName: '长春市', coverageRate: 85.4 },
    { regionType: 'city', regionName: '哈尔滨市', coverageRate: 83.2 },
    { regionType: 'city', regionName: '济南市', coverageRate: 87.5 }
  ]

  const mockProvinceData = [
    { provinceCode: '21', provinceName: '辽宁省', so2ExceedCount: 15, pm25ExceedCount: 30, coExceedCount: 20, aqiExceedCount: 65 },
    { provinceCode: '22', provinceName: '吉林省', so2ExceedCount: 12, pm25ExceedCount: 25, coExceedCount: 18, aqiExceedCount: 55 },
    { provinceCode: '23', provinceName: '黑龙江省', so2ExceedCount: 18, pm25ExceedCount: 35, coExceedCount: 22, aqiExceedCount: 70 },
    { provinceCode: '13', provinceName: '河北省', so2ExceedCount: 22, pm25ExceedCount: 42, coExceedCount: 28, aqiExceedCount: 85 },
    { provinceCode: '37', provinceName: '山东省', so2ExceedCount: 16, pm25ExceedCount: 32, coExceedCount: 24, aqiExceedCount: 68 }
  ]

  const mockAQILevel = [
    { level: 1, levelName: '优', count: 120, percentage: 26.3 },
    { level: 2, levelName: '良', count: 180, percentage: 39.5 },
    { level: 3, levelName: '轻度污染', count: 80, percentage: 17.5 },
    { level: 4, levelName: '中度污染', count: 45, percentage: 9.9 },
    { level: 5, levelName: '重度污染', count: 25, percentage: 5.5 },
    { level: 6, levelName: '严重污染', count: 6, percentage: 1.3 }
  ]

  const mockTrend = [
    { month: '1月', exceedCount: 45 },
    { month: '2月', exceedCount: 52 },
    { month: '3月', exceedCount: 38 },
    { month: '4月', exceedCount: 42 },
    { month: '5月', exceedCount: 35 },
    { month: '6月', exceedCount: 28 },
    { month: '7月', exceedCount: 22 },
    { month: '8月', exceedCount: 25 },
    { month: '9月', exceedCount: 32 },
    { month: '10月', exceedCount: 40 },
    { month: '11月', exceedCount: 48 },
    { month: '12月', exceedCount: 55 }
  ]

  realtimeStats.value = {
    totalCount: 456,
    passCount: 400,
    exceedCount: 56
  }

  renderProvinceCoverageChart(mockGridCoverage)
  renderCityCoverageChart(mockGridCoverage)
  renderAQILevelChart(mockAQILevel)
  renderPM25Chart(mockProvinceData)
  renderSO2Chart(mockProvinceData)
  renderCOChart(mockProvinceData)
  renderTrendChart(mockTrend)

  setTimeout(() => {
    renderMapChart(mockProvinceData)
  }, 100)
}

const renderProvinceCoverageChart = (data) => {
  const chart = chartMap.get('provinceCoverage')
  if (!chart) return

  const provinceData = data.filter(item => item.regionType === 'province')
  if (provinceData.length === 0) {
    console.warn('[ProvinceCoverage] 数据为空')
    return
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      textStyle: { color: '#333' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: provinceData.map(item => item.regionName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 11
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '覆盖率(%)',
      max: 100,
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0' } }
    },
    series: [{
      data: provinceData.map(item => item.coverageRate),
      type: 'bar',
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#667eea' },
          { offset: 1, color: '#764ba2' }
        ]),
        borderRadius: [4, 4, 0, 0]
      }
    }]
  }
  chart.setOption(option)
}

const renderCityCoverageChart = (data) => {
  const chart = chartMap.get('cityCoverage')
  if (!chart) return

  const cityData = data.filter(item => item.regionType === 'city')
  if (cityData.length === 0) {
    console.warn('[CityCoverage] 数据为空')
    return
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      textStyle: { color: '#333' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: cityData.map(item => item.regionName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 11
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '覆盖率(%)',
      max: 100,
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0' } }
    },
    series: [{
      data: cityData.map(item => item.coverageRate),
      type: 'bar',
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#667eea' },
          { offset: 1, color: '#764ba2' }
        ]),
        borderRadius: [4, 4, 0, 0]
      }
    }]
  }
  chart.setOption(option)
}

const renderAQILevelChart = (data) => {
  const chart = chartMap.get('aqiLevel')
  if (!chart || data.length === 0) {
    console.warn('[AQILevel] 数据为空')
    return
  }

  const colors = ['#667eea', '#764ba2', '#f093fb', '#f5576c', '#4facfe', '#00f2fe']

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)',
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      textStyle: { color: '#333' }
    },
    legend: {
      orient: 'vertical',
      right: '2%',
      top: 'center',
      textStyle: {
        color: '#666',
        fontSize: 11
      },
      itemWidth: 12,
      itemHeight: 12
    },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      center: ['40%', '50%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 6,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        formatter: '{b}\n{d}%',
        color: '#666',
        fontSize: 10
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
      data: data.map((item, index) => ({
        value: item.count,
        name: item.levelName,
        itemStyle: {
          color: colors[index % colors.length]
        }
      }))
    }]
  }
  chart.setOption(option)
}

const renderMapChart = (data) => {
  const chart = chartMap.get('map')
  if (!chart) return

  if (!data || data.length === 0) {
    console.warn('[MapChart] 数据为空，跳过渲染')
    return
  }

  fetch('https://geo.datav.aliyun.com/areas_v3/bound/100000_full.json')
      .then(response => response.json())
      .then(chinaJson => {
        echarts.registerMap('china', chinaJson)

        const option = {
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c}',
            backgroundColor: 'rgba(255, 255, 255, 0.95)',
            borderColor: '#e0e0e0',
            textStyle: { color: '#333' }
          },
          visualMap: {
            min: 0,
            max: 100,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],
            calculable: true,
            textStyle: { color: '#666' },
            inRange: {
              color: ['#e3f2fd', '#1976d2']
            }
          },
          series: [{
            type: 'map',
            map: 'china',
            roam: false,
            label: {
              show: true,
              fontSize: 10,
              color: '#666'
            },
            itemStyle: {
              areaColor: '#f5f5f5',
              borderColor: '#ddd',
              borderWidth: 1
            },
            emphasis: {
              itemStyle: {
                areaColor: '#bbdefb'
              },
              label: {
                color: '#333'
              }
            },
            data: data.map(item => ({
              name: item.provinceName,
              value: item.aqiExceedCount
            }))
          }]
        }
        chart.setOption(option)
      })
      .catch(error => {
        console.error('[MapChart] 加载地图数据失败:', error)
      })
}

const renderPM25Chart = (data) => {
  const chart = chartMap.get('pm25')
  if (!chart || data.length === 0) {
    console.warn('[PM25] 数据为空')
    return
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      textStyle: { color: '#333' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: data.map(item => item.provinceName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 11
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标数量',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0' } }
    },
    series: [{
      data: data.map(item => item.pm25ExceedCount),
      type: 'bar',
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#fa709a' },
          { offset: 1, color: '#fee140' }
        ]),
        borderRadius: [4, 4, 0, 0]
      }
    }]
  }
  chart.setOption(option)
}

const renderSO2Chart = (data) => {
  const chart = chartMap.get('so2')
  if (!chart || data.length === 0) {
    console.warn('[SO2] 数据为空')
    return
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      textStyle: { color: '#333' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: data.map(item => item.provinceName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 11
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标数量',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0' } }
    },
    series: [{
      data: data.map(item => item.so2ExceedCount),
      type: 'bar',
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#43e97b' },
          { offset: 1, color: '#38f9d7' }
        ]),
        borderRadius: [4, 4, 0, 0]
      }
    }]
  }
  chart.setOption(option)
}

const renderCOChart = (data) => {
  const chart = chartMap.get('co')
  if (!chart || data.length === 0) {
    console.warn('[CO] 数据为空')
    return
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      textStyle: { color: '#333' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: data.map(item => item.provinceName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        color: '#666',
        fontSize: 11
      },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标数量',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0' } }
    },
    series: [{
      data: data.map(item => item.coExceedCount),
      type: 'bar',
      barWidth: '50%',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#fa709a' },
          { offset: 1, color: '#fee140' }
        ]),
        borderRadius: [4, 4, 0, 0]
      }
    }]
  }
  chart.setOption(option)
}

const renderTrendChart = (data) => {
  const chart = chartMap.get('trend')
  if (!chart || data.length === 0) {
    console.warn('[Trend] 数据为空')
    return
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e0e0e0',
      textStyle: { color: '#333' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: data.map(item => item.month),
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } }
    },
    yAxis: {
      type: 'value',
      name: '超标数量',
      axisLabel: { color: '#666' },
      axisLine: { lineStyle: { color: '#ddd' } },
      splitLine: { lineStyle: { color: '#f0f0f0' } }
    },
    series: [{
      data: data.map(item => item.exceedCount),
      type: 'line',
      smooth: true,
      symbol: 'circle',
      symbolSize: 6,
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(102, 126, 234, 0.3)' },
          { offset: 1, color: 'rgba(102, 126, 234, 0.05)' }
        ])
      },
      lineStyle: {
        color: '#667eea',
        width: 3
      },
      itemStyle: {
        color: '#667eea'
      }
    }]
  }
  chart.setOption(option)
}
</script>

<style scoped lang="scss">
.dashboard-container {
  width: 100vw;
  height: 100vh;
  background: #f5f7fa;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.dashboard-header {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #ffffff;
  border-bottom: 1px solid #e0e0e0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);

  .header-title {
    color: #333;
    font-size: 22px;
    font-weight: 600;
    letter-spacing: 4px;
  }
}

.dashboard-content {
  flex: 1;
  padding: 16px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 16px;
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
    flex: 0 0 300px;
  }

  &.center-col {
    flex: 1;
  }
}

.chart-panel {
  background: #ffffff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  }

  &.map-panel {
    flex: 1;
  }

  &.realtime-panel {
    height: 140px;
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
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 12px;

  .panel-title {
    color: #333;
    font-size: 14px;
    font-weight: 600;
  }
}

.chart-container {
  width: 100%;
  height: 170px;
}

.map-container {
  width: 100%;
  height: calc(100% - 44px);
}

.trend-chart {
  height: 210px;
}

.realtime-stats {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 100%;
  padding: 0 20px;
}

.stat-item {
  text-align: center;
  padding: 12px 20px;
  border-radius: 8px;
  background: #f8f9fa;
  border: 1px solid #e9ecef;
  transition: all 0.3s ease;

  &:hover {
    background: #e9ecef;
    transform: translateY(-2px);
  }

  .stat-value {
    font-size: 32px;
    font-weight: 600;
    margin-bottom: 8px;
    color: #667eea;

    &.success {
      color: #43e97b;
    }

    &.danger {
      color: #f5576c;
    }
  }

  .stat-label {
    color: #666;
    font-size: 12px;
  }
}

.dashboard-footer {
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  background: #ffffff;
  border-top: 1px solid #e0e0e0;

  .footer-text {
    color: #666;
    font-size: 12px;
  }
}
</style>
