<template>
  <div class="history-container">
    <div class="header-section">
      <el-icon class="back-icon" @click="goBack"><ArrowLeft /></el-icon>
      <h2 class="page-title">历史反馈信息列表</h2>
      <el-icon class="grid-icon"><Grid /></el-icon>
    </div>

    <div class="history-box">
      <div class="banner-image">
        <img src="/images/header-bg.png" alt="保护环境人人有责" />
        <p class="slogan">保护环境人人有责</p>
      </div>

      <div class="history-list">
        <div
          v-for="item in historyList"
          :key="item.feedbackId"
          class="history-item"
        >
          <div class="aqi-badge" :style="{ backgroundColor: getAQIColor(item.aqiLevel) }">
            {{ getAQIChinese(item.aqiLevel) }}
          </div>
          <div class="history-info">
            <div class="info-row">
              <span class="time">{{ formatTime(item.feedbackTime) }}</span>
              <span class="location">{{ getProvinceName(item.provinceId) }}</span>
              <span class="location">{{ getCityName(item.cityId) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft, Grid } from '@element-plus/icons-vue'
import { getHistoryFeedback } from '@/api/neps'

const router = useRouter()
const historyList = ref([])

const aqiLevelMap = {
  1: { chinese: '一', color: '#00e400' },
  2: { chinese: '二', color: '#ffff00' },
  3: { chinese: '三', color: '#ff7e00' },
  4: { chinese: '四', color: '#ff0000' },
  5: { chinese: '五', color: '#99004c' },
  6: { chinese: '六', color: '#7e0023' }
}

const provinceMap = {
  '210000': '辽宁省',
  '130000': '河北省'
}

const cityMap = {
  '210100': '沈阳市',
  '210200': '大连市',
  '130100': '石家庄市',
  '130200': '唐山市'
}

onMounted(async () => {
  const phoneNumber = localStorage.getItem('phoneNumber')
  if (phoneNumber) {
    await loadHistoryFeedback(phoneNumber)
  }
})

const loadHistoryFeedback = async (phoneNumber) => {
  try {
    const res = await getHistoryFeedback(phoneNumber)
    historyList.value = res.data || []
  } catch (error) {
    console.error('加载历史记录失败', error)
  }
}

const getAQIColor = (level) => {
  return aqiLevelMap[level]?.color || '#999'
}

const getAQIChinese = (level) => {
  return aqiLevelMap[level]?.chinese || ''
}

const getProvinceName = (provinceId) => {
  return provinceMap[provinceId] || ''
}

const getCityName = (cityId) => {
  return cityMap[cityId] || ''
}

const formatTime = (time) => {
  if (!time) return ''
  return time.replace('T', ' ').substring(0, 19)
}

const goBack = () => {
  router.push('/select-grid')
}
</script>

<style scoped>
.history-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #a8e6cf 0%, #88d8b0 100%);
  padding: 20px;
}

.header-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.back-icon, .grid-icon {
  font-size: 24px;
  cursor: pointer;
  color: #333;
}

.page-title {
  flex: 1;
  text-align: center;
  font-size: 20px;
  color: #333;
  margin: 0;
}

.history-box {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.banner-image {
  text-align: center;
  margin-bottom: 20px;
}

.banner-image img {
  width: 100%;
  max-width: 300px;
  height: auto;
}

.slogan {
  color: #2d8a4e;
  font-size: 18px;
  font-weight: bold;
  margin: 10px 0;
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.history-item {
  display: flex;
  align-items: center;
  padding: 12px;
  background: #f0f8ff;
  border-radius: 8px;
}

.aqi-badge {
  width: 40px;
  height: 40px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: bold;
  margin-right: 12px;
}

.history-info {
  flex: 1;
}

.info-row {
  display: flex;
  gap: 15px;
  align-items: center;
  font-size: 14px;
  color: #333;
}

.time {
  color: #666;
  font-size: 12px;
}

.location {
  color: #333;
}
</style>