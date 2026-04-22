<template>
  <el-container class="main-layout">
    <el-header class="header">
      <div class="header-left">
        <h3>东软环保公众监督管理系统</h3>
      </div>
      <div class="header-right">
        <span>系统管理员：{{ userStore.userInfo?.memName }}</span>
        <el-button type="primary" size="small" @click="handleLogout">
          OUT
        </el-button>
      </div>
    </el-header>

    <el-container>
      <el-aside width="200px">
        <el-menu
            :default-active="activeMenu"
            router
            background-color="#304156"
            text-color="#bfcbd9"
            active-text-color="#409EFF"
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><Document /></el-icon>
              <span>公众监督数据管理</span>
            </template>
            <el-menu-item index="/public-supervision">公众监督数据列表</el-menu-item>
            <el-menu-item index="/confirm-aqi">确认AQI数据列表</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="2">
            <template #title>
              <el-icon><DataAnalysis /></el-icon>
              <span>统计数据管理</span>
            </template>
            <el-menu-item index="/statistics/province">省分组检查统计</el-menu-item>
            <el-menu-item index="/statistics/aqi-level">AQI指数级数分布</el-menu-item>
            <el-menu-item index="/statistics/aqi-trend">AQI超标趋势统计</el-menu-item>
            <el-menu-item index="/statistics/other">其它数据统计</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

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
import { Document, DataAnalysis } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

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
