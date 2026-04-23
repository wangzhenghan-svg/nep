<template>
  <div class="page-container">
    <h3>反馈任务详细信息</h3>

    <!-- 任务基本信息卡片 -->
    <el-card class="detail-card">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="任务编号">
          {{ taskDetail.id }}
        </el-descriptions-item>
        <el-descriptions-item label="所在地区">
          {{ taskDetail.area }}
        </el-descriptions-item>
        <el-descriptions-item label="地址">
          {{ taskDetail.address }}
        </el-descriptions-item>
        <el-descriptions-item label="任务日期">
          {{ taskDetail.date }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- AQI数据录入卡片 -->
    <el-card class="aqi-card">
      <h4>AQI数据录入</h4>

      <!-- SO2浓度输入框 -->
      <el-form :model="aqiForm" label-width="120px" style="margin-top: 20px;">
        <el-form-item label="SO2浓度">
          <el-input
            v-model="aqiForm.so2"
            placeholder="请输入SO2浓度"
            @change="calculateAqi"
          >
            <template #append>μg/m³</template>
          </el-input>
          <span class="aqi-level">AQI等级: {{ aqiForm.so2Level || '-' }}</span>
        </el-form-item>

        <!-- CO浓度输入框 -->
        <el-form-item label="CO浓度">
          <el-input
            v-model="aqiForm.co"
            placeholder="请输入CO浓度"
            @change="calculateAqi"
          >
            <template #append>mg/m³</template>
          </el-input>
          <span class="aqi-level">AQI等级: {{ aqiForm.coLevel || '-' }}</span>
        </el-form-item>

        <!-- PM2.5浓度输入框 -->
        <el-form-item label="PM2.5浓度">
          <el-input
            v-model="aqiForm.pm25"
            placeholder="请输入PM2.5浓度"
            @change="calculateAqi"
          >
            <template #append>μg/m³</template>
          </el-input>
          <span class="aqi-level">AQI等级: {{ aqiForm.pm25Level || '-' }}</span>
        </el-form-item>

        <!-- 显示总AQI等级 -->
        <el-form-item label="总AQI等级">
          <el-tag :type="getAqiTagType(totalAqiLevel)" size="large">
            {{ totalAqiLevel || '未计算' }}
          </el-tag>
        </el-form-item>

        <!-- 提交按钮 -->
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">提交实测数据</el-button>
          <el-button @click="handleBack">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getTaskDetail, submitAqiData } from '@/api/grid'

const route = useRoute()
const router = useRouter()

// 任务详情数据
const taskDetail = ref({
  id: route.params.id,
  area: '辽宁省 沈阳市',
  address: '大东区崇泉路456号',
  date: '2022-09-13'
})

// AQI表单数据
const aqiForm = reactive({
  so2: '',
  so2Level: '',
  co: '',
  coLevel: '',
  pm25: '',
  pm25Level: ''
})

// 总AQI等级
const totalAqiLevel = ref('')

// 页面加载时获取任务详情
onMounted(() => {
  fetchData()
})

// 获取任务详情数据
const fetchData = async () => {
  try {
    // TODO: 调用后端API获取任务详情
    // const res = await getTaskDetail(route.params.id)
    // taskDetail.value = res.data
    console.log('获取任务详情', route.params.id)
  } catch (error) {
    console.error('获取任务详情失败', error)
  }
}

// 计算AQI等级
const calculateAqi = () => {
  // 根据SO2浓度计算AQI等级
  if (aqiForm.so2) {
    aqiForm.so2Level = getSo2Level(parseFloat(aqiForm.so2))
  }

  // 根据CO浓度计算AQI等级
  if (aqiForm.co) {
    aqiForm.coLevel = getCoLevel(parseFloat(aqiForm.co))
  }

  // 根据PM2.5浓度计算AQI等级
  if (aqiForm.pm25) {
    aqiForm.pm25Level = getPm25Level(parseFloat(aqiForm.pm25))
  }

  // 计算总AQI等级（取最大值）
  calculateTotalAqi()
}

// SO2浓度对应的AQI等级
const getSo2Level = (value) => {
  if (value <= 50) return '优'
  if (value <= 150) return '良'
  if (value <= 475) return '轻度污染'
  if (value <= 800) return '中度污染'
  if (value <= 1600) return '重度污染'
  return '严重污染'
}

// CO浓度对应的AQI等级
const getCoLevel = (value) => {
  if (value <= 5) return '优'
  if (value <= 10) return '良'
  if (value <= 35) return '轻度污染'
  if (value <= 60) return '中度污染'
  if (value <= 90) return '重度污染'
  return '严重污染'
}

// PM2.5浓度对应的AQI等级
const getPm25Level = (value) => {
  if (value <= 35) return '优'
  if (value <= 75) return '良'
  if (value <= 115) return '轻度污染'
  if (value <= 150) return '中度污染'
  if (value <= 250) return '重度污染'
  return '严重污染'
}

// 计算总AQI等级（取各项中最差的等级）
const calculateTotalAqi = () => {
  const levels = ['优', '良', '轻度污染', '中度污染', '重度污染', '严重污染']
  const allLevels = [aqiForm.so2Level, aqiForm.coLevel, aqiForm.pm25Level].filter(l => l)

  if (allLevels.length === 0) {
    totalAqiLevel.value = ''
    return
  }

  // 找到最差的等级（索引最大的）
  let maxIndex = 0
  allLevels.forEach(level => {
    const index = levels.indexOf(level)
    if (index > maxIndex) maxIndex = index
  })

  totalAqiLevel.value = levels[maxIndex]
}

// 获取AQI标签类型（用于Element Plus的Tag组件）
const getAqiTagType = (level) => {
  const typeMap = {
    '优': 'success',
    '良': '',
    '轻度污染': 'warning',
    '中度污染': 'warning',
    '重度污染': 'danger',
    '严重污染': 'danger'
  }
  return typeMap[level] || 'info'
}

// 提交实测数据
const handleSubmit = async () => {
  // 验证是否填写了数据
  if (!aqiForm.so2 && !aqiForm.co && !aqiForm.pm25) {
    ElMessage.warning('请至少填写一项检测数据')
    return
  }

  try {
    // 准备提交的数据
    const submitData = {
      taskId: route.params.id,
      so2: aqiForm.so2,
      so2Level: aqiForm.so2Level,
      co: aqiForm.co,
      coLevel: aqiForm.coLevel,
      pm25: aqiForm.pm25,
      pm25Level: aqiForm.pm25Level,
      totalAqiLevel: totalAqiLevel.value
    }

    // TODO: 调用后端API提交数据
    // await submitAqiData(submitData)
    console.log('提交数据', submitData)

    ElMessage.success('提交成功')
    router.back()
  } catch (error) {
    console.error('提交失败', error)
  }
}

// 返回列表页
const handleBack = () => {
  router.back()
}
</script>

<style scoped>
.page-container {
  padding: 20px;
}

.detail-card {
  margin-bottom: 20px;
}

.aqi-card {
  margin-top: 20px;
}

.aqi-level {
  margin-left: 10px;
  font-size: 14px;
  color: #409EFF;
}
</style>