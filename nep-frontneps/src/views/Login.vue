<template>
  <div class="login-container">
    <div class="header-image">
      <div class="logo-placeholder">
        <el-icon :size="80" color="#2d8a4e"><Picture /></el-icon>
      </div>
      <h1 class="platform-title">东软环保公众监督平台</h1>
      <p class="subtitle">公众监督员端</p>
    </div>

    <div class="login-box">
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
        <el-form-item prop="phoneNumber">
          <el-input
            v-model="loginForm.phoneNumber"
            placeholder="输入手机号"
            :prefix-icon="User"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="输入登录密码"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <div class="form-footer">
          <el-link type="primary" @click="goToRegister">注册</el-link>
          <el-link type="primary">忘记密码？</el-link>
        </div>

        <el-form-item>
          <el-button
            type="primary"
            @click="handleLogin"
            :loading="loading"
            style="width: 100%;"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, Picture } from '@element-plus/icons-vue'
import { login } from '@/api/neps'
import { useUserStore } from '@/store'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  phoneNumber: '',
  password: ''
})

const rules = {
  phoneNumber: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  await loginFormRef.value.validate()
  loading.value = true

  try {
    const res = await login(loginForm)
    userStore.setUserInfo(res.data)
    userStore.setToken('Bearer_' + loginForm.phoneNumber)
    localStorage.setItem('phoneNumber', loginForm.phoneNumber)
    ElMessage.success('登录成功')
    router.push('/select-grid')
  } catch (error) {
    console.error('登录失败', error)
  } finally {
    loading.value = false
  }
}

const goToRegister = () => {
  router.push('/register')
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #a8e6cf 0%, #88d8b0 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
}

.header-image {
  text-align: center;
  margin-bottom: 30px;
}

.logo-placeholder {
  width: 200px;
  height: 120px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
  border-radius: 12px;
  margin: 0 auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.platform-title {
  font-size: 28px;
  color: #2d8a4e;
  margin: 20px 0 10px;
}

.subtitle {
  font-size: 16px;
  color: #5a9e6f;
  margin: 0;
}

.login-box {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.form-footer {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>
