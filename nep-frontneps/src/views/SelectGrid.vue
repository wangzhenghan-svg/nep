<template>
  <div class="select-grid-container">
    <div class="header-section">
      <el-icon class="back-icon" @click="goBack"><ArrowLeft /></el-icon>
      <h2 class="page-title">选择网格</h2>
      <el-icon class="menu-icon"><Menu /></el-icon>
    </div>

    <div class="grid-box">
      <div class="banner-section">
        <div class="banner-icon">
          <el-icon :size="60" color="#2d8a4e"><Picture /></el-icon>
        </div>
        <p class="slogan">保护环境人人有责</p>
      </div>

      <el-form :model="gridForm" :rules="rules" ref="gridFormRef">
        <el-form-item label="所在省" prop="province">
          <el-select
            v-model="gridForm.province"
            placeholder="请选择省份"
            @change="handleProvinceChange"
            style="width: 100%;"
          >
            <el-option
              v-for="item in provinceList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="所在市" prop="city">
          <el-select
            v-model="gridForm.city"
            placeholder="请选择城市"
            style="width: 100%;"
          >
            <el-option
              v-for="item in cityList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="地址" prop="specificAddress">
          <el-input
            v-model="gridForm.specificAddress"
            placeholder="具体地址"
            type="textarea"
            :rows="3"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="handleNext"
            style="width: 100%;"
          >
            下一步
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft, Menu, Picture } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const gridFormRef = ref(null)

const gridForm = reactive({
  province: '',
  city: '',
  specificAddress: ''
})

const rules = {
  province: [
    { required: true, message: '请选择省份', trigger: 'change' }
  ],
  city: [
    { required: true, message: '请选择城市', trigger: 'change' }
  ],
  specificAddress: [
    { required: true, message: '请输入具体地址', trigger: 'blur' }
  ]
}

const provinceList = ref([
  { id: '210000', name: '辽宁省' },
  { id: '130000', name: '河北省' }
])

const cityList = ref([])

const cityData = {
  '210000': [
    { id: '210100', name: '沈阳市' },
    { id: '210200', name: '大连市' }
  ],
  '130000': [
    { id: '130100', name: '石家庄市' },
    { id: '130200', name: '唐山市' }
  ]
}

onMounted(() => {
  cityList.value = cityData['210000'] || []
  gridForm.province = '210000'
  gridForm.city = '210100'
})

const handleProvinceChange = (provinceId) => {
  gridForm.city = ''
  cityList.value = cityData[provinceId] || []
}

const handleNext = async () => {
  await gridFormRef.value.validate()

  const selectedProvince = provinceList.value.find(p => p.id === gridForm.province)
  const selectedCity = cityList.value.find(c => c.id === gridForm.city)

  localStorage.setItem('gridInfo', JSON.stringify({
    provinceId: gridForm.province,
    provinceName: selectedProvince?.name,
    cityId: gridForm.city,
    cityName: selectedCity?.name,
    specificAddress: gridForm.specificAddress
  }))

  ElMessage.success('地址选择成功')
  router.push('/submit-aqi')
}

const goBack = () => {
  router.push('/login')
}
</script>

<style scoped>
.select-grid-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #a8e6cf 0%, #88d8b0 100%);
  padding: 20px;
}

.header-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.back-icon, .menu-icon {
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

.grid-box {
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.banner-section {
  text-align: center;
  margin-bottom: 20px;
}

.banner-icon {
  width: 200px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #a8e6cf 0%, #88d8b0 100%);
  border-radius: 12px;
  margin: 0 auto 10px;
}

.slogan {
  color: #2d8a4e;
  font-size: 18px;
  font-weight: bold;
  margin: 10px 0;
}
</style>
