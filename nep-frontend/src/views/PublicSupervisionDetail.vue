<template>
  <div class="page-container">
    <h3>公众监督数据详情</h3>

    <el-card class="detail-card">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="公众监督反馈信息编号">
          {{ detailData.id }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈者信息">
          {{ detailData.feedbackName }} {{ detailData.feedbackSex }} {{ detailData.feedbackPhone }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈者联系地址">
          {{ detailData.address }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈者电话号码">
          {{ detailData.phone }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈信息描述" :span="2">
          {{ detailData.description }}
        </el-descriptions-item>
        <el-descriptions-item label="预估等级">
          {{ detailData.estimatedLevel }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈日期时间">
          {{ detailData.feedbackDateTime }}
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
  feedbackName: 'XXX 男',
  feedbackSex: '男',
  feedbackPhone: 'xxxxxxxxxxxx',
  address: 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
  phone: 'xxxxxxxxxxxx',
  description: 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
  estimatedLevel: '三级 轻度污染',
  feedbackDateTime: 'xxxx-xx-xx xx:xx:xx'
})

onMounted(() => {
  fetchData()
})

const fetchData = () => {
  // TODO: 根据ID获取详情数据
  console.log('获取详情', route.params.id)
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
