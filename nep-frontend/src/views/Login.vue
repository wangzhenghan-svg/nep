<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">登录</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
        <el-form-item label="登录编码" prop="logInID">
          <el-input v-model="loginForm.logInID" placeholder="请输入登录编码" />
        </el-form-item>
        <el-form-item label="登录密码" prop="logInPwd">
          <el-input
              v-model="loginForm.logInPwd"
              type="password"
              placeholder="请输入登录密码"
              show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading">
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
import { login } from '@/api/member'
import { useUserStore } from '@/store'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  logInID: '',
  logInPwd: ''
})

const rules = {
  logInID: [
    { required: true, message: '请输入登录编码', trigger: 'blur' }
  ],
  logInPwd: [
    { required: true, message: '请输入登录密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  await loginFormRef.value.validate()
  loading.value = true

  try {
    const res = await login(loginForm.logInID, loginForm.logInPwd)
    userStore.setUserInfo(res.data)
    userStore.setToken('Bearer_' + loginForm.logInID)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error('登录失败', error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
</style>
