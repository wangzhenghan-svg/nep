<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">网格员登录</h2>
      <!-- 登录表单 -->
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
        <!-- 登录编码输入框 -->
        <el-form-item label="登录编码" prop="logInID">
          <el-input v-model="loginForm.logInID" placeholder="请输入登录编码" />
        </el-form-item>
        <!-- 登录密码输入框 -->
        <el-form-item label="登录密码" prop="logInPwd">
          <el-input
              v-model="loginForm.logInPwd"
              type="password"
              placeholder="请输入登录密码"
              show-password
          />
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" style="width: 100%;">
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
import { gridLogin } from '@/api/grid'
import { useUserStore } from '@/store'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)
const loading = ref(false)

// 登录表单数据
const loginForm = reactive({
  logInID: '',
  logInPwd: ''
})

// 表单验证规则
const rules = {
  logInID: [
    { required: true, message: '请输入登录编码', trigger: 'blur' }
  ],
  logInPwd: [
    { required: true, message: '请输入登录密码', trigger: 'blur' }
  ]
}

// 处理登录
const handleLogin = async () => {
  // 验证表单
  await loginFormRef.value.validate()
  loading.value = true

  try {
    const res = await gridLogin(loginForm.logInID, loginForm.logInPwd)
    // 保存用户信息和token
    userStore.setUserInfo(res.data)
    userStore.setToken('Bearer_' + loginForm.logInID)
    ElMessage.success('登录成功')
    // 跳转到任务列表页面
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