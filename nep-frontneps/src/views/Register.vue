<template>
  <div class="register-container">
    <div class="header-section">
      <el-icon class="back-icon" @click="goBack"><ArrowLeft /></el-icon>
      <h2 class="page-title">注册</h2>
    </div>

    <div class="register-box">
      <div class="banner-image">
        <p class="slogan">保护环境人人有责</p>
      </div>

      <el-form :model="registerForm" :rules="rules" ref="registerFormRef">
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input
              v-model="registerForm.phoneNumber"
              placeholder="请输入手机号码"
              @blur="checkPhoneExists"
          />
        </el-form-item>

        <el-form-item label="真实姓名" prop="realName">
          <el-input
              v-model="registerForm.realName"
              placeholder="真实姓名便于我们联系您"
          />
        </el-form-item>

        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker
              v-model="registerForm.birthDate"
              type="date"
              placeholder="年 /月/日"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="registerForm.gender">
            <el-radio value="男">男</el-radio>
            <el-radio value="女">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
              show-password
          />
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              @click="handleRegister"
              :loading="loading"
              style="width: 100%;"
          >
            注册
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft } from '@element-plus/icons-vue'
import { register, checkPhone } from '@/api/neps'
import { ElMessage } from 'element-plus'

const router = useRouter()
const registerFormRef = ref(null)
const loading = ref(false)

const registerForm = reactive({
  phoneNumber: '',
  realName: '',
  birthDate: '',
  gender: '男',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  phoneNumber: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' }
  ],
  birthDate: [
    { required: true, message: '请选择出生日期', trigger: 'change' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const checkPhoneExists = async () => {
  if (!registerForm.phoneNumber) return

  try {
    const res = await checkPhone(registerForm.phoneNumber)
    if (res.code === 306) {
      ElMessage.warning('该手机号已被注册')
    }
  } catch (error) {
    console.error('验证手机号失败', error)
    ElMessage.error('验证手机号失败')
  }
}

const handleRegister = async () => {
  const valid = await registerFormRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true

  try {
    const age = new Date().getFullYear() - new Date(registerForm.birthDate).getFullYear()
    const registerData = {
      phoneNumber: registerForm.phoneNumber,
      password: registerForm.password,
      realName: registerForm.realName,
      age: age,
      gender: registerForm.gender
    }

    const res = await register(registerData)

    if (res.code === 200) {
      ElMessage.success('注册成功')
      router.push('/login')
    } else {
      ElMessage.error(res.msg || '注册失败')
    }
  } catch (error) {
    console.error('注册失败', error)
    ElMessage.error(error.response?.data?.msg || '注册失败，请重试')
  } finally {
    loading.value = false
  }
}

const goBack = () => {
  router.push('/login')
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #a8e6cf 0%, #88d8b0 100%);
  padding: 20px;
}

.header-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.back-icon {
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

.register-box {
  background: white;
  border-radius: 12px;
  padding: 30px;
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
</style>
