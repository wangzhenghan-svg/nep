<template>
  <el-container class="main-layout">
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <div class="header-left">
        <h3>网格员端管理系统</h3>
      </div>
      <div class="header-right">
        <span>欢迎，{{ userStore.userInfo?.memName || '网格员' }}</span>
        <el-button type="text" @click="handleLogout" style="color: white;">退出登录</el-button>
      </div>
    </el-header>

    <el-container>
      <!-- 侧边栏菜单 -->
      <el-aside width="200px">
        <el-menu
            :default-active="activeMenu"
            background-color="#304156"
            text-color="#bfcbd9"
            active-text-color="#409EFF"
            router
        >
          <el-menu-item index="/task-list">
            <el-icon><Document /></el-icon>
            <span>反馈任务列表</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 主内容区 -->
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store'
import { Document } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

// 当前激活的菜单项
const activeMenu = computed(() => route.path)

// 退出登录
const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.main-layout {
  height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #304156;
  color: white;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.el-aside {
  background: #304156;
}
</style>