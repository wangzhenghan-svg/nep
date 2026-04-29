<template>
  <div class="submit-aqi-container">
    <div class="header-section">
      <el-icon class="back-icon" @click="goBack"><ArrowLeft /></el-icon>
      <h2 class="page-title">选择AQI</h2>
      <el-icon class="grid-icon"><Grid /></el-icon>
    </div>

    <div class="aqi-box">
      <div class="aqi-level-table">
        <div class="aqi-row" v-for="item in aqiLevels" :key="item.level">
          <div class="level-num">{{ item.num }}</div>
          <div class="level-name" :style="{ backgroundColor: item.color }">{{ item.name }}</div>
          <div class="level-desc">{{ item.desc }}</div>
        </div>
      </div>

      <div class="form-section">
        <p class="form-label">请预估当前空气质量指数等级：</p>
        <el-radio-group v-model="selectedAQI" class="aqi-radio-group">
          <el-radio
            v-for="item in aqiLevels"
            :key="item.level"
            :label="item.level"
            :style="{ '--radio-color': item.color }"
          >
            {{ item.chineseNum }}
          </el-radio>
        </el-radio-group>

        <p class="form-label">请填写反馈信息：</p>
        <el-input
          v-model="feedbackDesc"
          type="textarea"
          :rows="4"
          placeholder="请填写反馈信息"
        />

        <el-button
          type="primary"
          @click="handleSubmit"
          :loading="loading"
          style="width: 100%; margin-top: 20px;"
        >
          提交
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft, Grid } from '@element-plus/icons-vue'
import { submitFeedback } from '@/api/neps'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const selectedAQI = ref(1)
const feedbackDesc = ref('')

const aqiLevels = [
  { level: 1, num: '一', chineseNum: '一', name: '优', color: '#00e400', desc: '空气质量令人满意，基本无空气污染' },
  { level: 2, num: '二', chineseNum: '二', name: '良', color: '#ffff00', desc: '空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响' },
  { level: 3, num: '三', chineseNum: '三', name: '轻度污染', color: '#ff7e00', desc: '易感人群症状有轻度加剧，健康人群出现刺激症状' },
  { level: 4, num: '四', chineseNum: '四', name: '中度污染', color: '#ff0000', desc: '进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响' },
  { level: 5, num: '五', chineseNum: '五', name: '重度污染', color: '#99004c', desc: '心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状' },
  { level: 6, num: '六', chineseNum: '六', name: '严重污染', color: '#7e0023', desc: '健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病' }
]

const handleSubmit = async () => {
  if (!selectedAQI.value) {
    ElMessage.warning('请选择AQI等级')
    return
  }

  loading.value = true

  try {
    const gridInfo = JSON.parse(localStorage.getItem('gridInfo') || '{}')
    const phoneNumber = localStorage.getItem('phoneNumber')

    const feedbackData = {
      phoneNumber: phoneNumber,
      provinceId: gridInfo.provinceId,
      cityId: gridInfo.cityId,
      specificAddress: gridInfo.specificAddress,
      aqiLevel: selectedAQI.value,
      airQualityDescription: aqiLevels.find(item => item.level === selectedAQI.value)?.name || '',
      remarks: feedbackDesc.value
    }

    await submitFeedback(feedbackData)
    ElMessage.success('提交成功')
    router.push('/history')
  } catch (error) {
    console.error('提交失败', error)
  } finally {
    loading.value = false
  }
}

const goBack = () => {
  router.push('/select-grid')
}
</script>

<style scoped>
.submit-aqi-container {
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

.aqi-box {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.aqi-level-table {
  margin-bottom: 20px;
}

.aqi-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 8px;
  background: #f5f5f5;
  border-radius: 4px;
}

.level-num {
  width: 30px;
  font-weight: bold;
}

.level-name {
  padding: 4px 12px;
  color: white;
  border-radius: 4px;
  margin-right: 10px;
  font-weight: bold;
  min-width: 80px;
  text-align: center;
}

.level-desc {
  flex: 1;
  font-size: 12px;
  color: #666;
}

.form-section {
  margin-top: 20px;
}

.form-label {
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.aqi-radio-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

:deep(.el-radio) {
  margin-right: 0;
}

:deep(.el-radio__input.is-checked .el-radio__inner) {
  background-color: var(--radio-color, #409eff);
  border-color: var(--radio-color, #409eff);
}
</style>