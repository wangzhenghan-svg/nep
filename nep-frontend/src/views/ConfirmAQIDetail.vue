<template>
  <div class="page-container">
    <h3>公众监督数据详情</h3>

    <el-card class="detail-card">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="确认AQI数据编号">
          {{ detailData.id }}
        </el-descriptions-item>
        <el-descriptions-item label="确认信息存在地区">
          {{ detailData.area }}
        </el-descriptions-item>
        <el-descriptions-item label="确认AQI等级">
          {{ detailData.aqiLevel }}
        </el-descriptions-item>
        <el-descriptions-item label="确认日期时间">
          {{ detailData.confirmDateTime }}
        </el-descriptions-item>
        <el-descriptions-item label="网格员信息">
          {{ detailData.workerInfo }}
        </el-descriptions-item>
        <el-descriptions-item label="公众监督员反馈信息概述" :span="2">
          {{ detailData.feedbackOverview }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <div class="button-group">
      <el-button type="primary" @click="handleBack">返回</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const detailData = ref({
  id: route.params.id,
  area: '辽宁省 沈阳市 XXXXXX',
  aqiLevel: '三级 轻度污染',
  confirmDateTime: 'xxxx-xx-xx xx:xx:xx',
  workerInfo: '张三 xxxxxxxxxxx',
  feedbackOverview: 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'
})

onMounted(() => {
  fetchData()
})

const fetchData = () => {
  // TODO: 根据ID获取详情数据
  console.log('获取AQI详情', route.params.id)
}

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

.button-group {
  display: flex;
  justify-content: center;
}
</style>
