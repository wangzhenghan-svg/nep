<template>
  <div class="page-container">
    <h3>公众监督数据详情</h3>

    <!-- 详情信息 -->
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
        <el-descriptions-item label="预估等级">
          {{ detailData.estimatedLevel }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈日期时间">
          {{ detailData.feedbackDateTime }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 是否异地指派 -->
    <el-form-item label="是否异地指派">
      <el-switch v-model="isRemote" @change="handleSwitchChange" />
    </el-form-item>

    <!-- 本地指派表单 -->
    <div v-if="!isRemote" class="assign-form">
      <el-form :model="localForm">
        <el-form-item label="本地网格员">
          <el-select v-model="localForm.workerId" placeholder="请选择网格员">
            <el-option
                v-for="item in localWorkerList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLocalAssign">本地指派</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 异地指派表单 -->
    <div v-else class="assign-form">
      <el-form :model="remoteForm">
        <el-form-item label="省">
          <el-select v-model="remoteForm.provinceId" placeholder="请选择" @change="handleRemoteProvinceChange">
            <el-option
                v-for="item in provinceList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="市">
          <el-select v-model="remoteForm.cityId" placeholder="请选择" @change="handleRemoteCityChange">
            <el-option
                v-for="item in remoteCityList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="网格员">
          <el-select v-model="remoteForm.workerId" placeholder="请选择网格员">
            <el-option
                v-for="item in remoteWorkerList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRemoteAssign">异地指派</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="button-group">
      <el-button @click="handleBack">返回</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const isRemote = ref(false)
const isReassign = ref(route.query.reassign === 'true')

const detailData = ref({
  id: route.params.id,
  feedbackName: 'XXX 男',
  feedbackSex: '男',
  feedbackPhone: 'xxxxxxxxxxxx',
  address: 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
  phone: 'xxxxxxxxxxxx',
  estimatedLevel: '三级 轻度污染',
  feedbackDateTime: 'xxxx-xx-xx xx:xx:xx'
})

const localForm = reactive({
  workerId: ''
})

const remoteForm = reactive({
  provinceId: '',
  cityId: '',
  workerId: ''
})

const provinceList = ref([
  { id: '1', name: '辽宁省' },
  { id: '2', name: '北京市' }
])

const localWorkerList = ref([
  { id: '1', name: '网格员A - 13800138001' },
  { id: '2', name: '网格员B - 13800138002' }
])

const remoteCityList = ref([])
const remoteWorkerList = ref([])

onMounted(() => {
  fetchData()
})

const fetchData = () => {
  // TODO: 获取详情数据和网格员列表
  console.log('获取详情', route.params.id)
}

const handleSwitchChange = (value) => {
  console.log('是否异地指派:', value)
}

const handleRemoteProvinceChange = (value) => {
  // TODO: 根据省份查询城市
  remoteCityList.value = [
    { id: '1', name: '沈阳市' },
    { id: '2', name: '大连市' }
  ]
  remoteForm.cityId = ''
  remoteForm.workerId = ''
}

const handleRemoteCityChange = (value) => {
  // TODO: 根据城市查询网格员
  remoteWorkerList.value = [
    { id: '1', name: '网格员C - 13800138003' },
    { id: '2', name: '网格员D - 13800138004' }
  ]
  remoteForm.workerId = ''
}

const handleLocalAssign = async () => {
  if (!localForm.workerId) {
    ElMessage.warning('请选择网格员')
    return
  }

  // TODO: 调用后端API进行本地指派
  ElMessage.success('本地指派成功')
  router.back()
}

const handleRemoteAssign = async () => {
  if (!remoteForm.provinceId || !remoteForm.cityId || !remoteForm.workerId) {
    ElMessage.warning('请填写完整信息')
    return
  }

  // TODO: 调用后端API进行异地指派
  ElMessage.success('异地指派成功')
  router.back()
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

.assign-form {
  margin: 20px 0;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 4px;
  max-width: 600px;
}

.button-group {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
