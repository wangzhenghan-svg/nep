<template>
  <div class="page-container">
    <h3>反馈任务列表</h3>

    <!-- 任务列表 -->
    <el-card class="task-card">
      <div class="task-list">
        <!-- 遍历任务列表 -->
        <div
          v-for="task in taskList"
          :key="task.id"
          class="task-item"
        >
          <div class="task-info">
            <!-- 任务编号标识 -->
            <div class="task-badge" :style="{ backgroundColor: getBadgeColor(task.id) }">
              {{ getBadgeText(task.id) }}
            </div>
            <div class="task-content">
              <!-- 显示地区、日期和地址 -->
              <div class="task-location">{{ task.province }} {{ task.city }} {{ task.date }}</div>
              <div class="task-address">{{ task.address }}</div>
            </div>
          </div>
          <!-- 去检测按钮，点击跳转到任务详情页 -->
          <el-button type="primary" link @click="handleGoDetect(task)">
            去检测
            <el-icon><Right /></el-icon>
          </el-button>
        </div>
      </div>

      <!-- 如果没有数据 -->
      <el-empty v-if="taskList.length === 0" description="暂无任务" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store'
import { getTaskList } from '@/api/grid'
import { Right } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

// 任务列表数据
const taskList = ref([
  {
    id: 1,
    province: '辽宁省',
    city: '沈阳市',
    date: '2022-09-13',
    address: '大东区崇泉路456号'
  },
  {
    id: 2,
    province: '辽宁省',
    city: '沈阳市',
    date: '2022-09-13',
    address: '杏花岭区南八路4-67号'
  },
  {
    id: 3,
    province: '辽宁省',
    city: '大连市',
    date: '2022-09-13',
    address: '河东区南二路孝义里1-232-6号'
  },
  {
    id: 4,
    province: '辽宁省',
    city: '沈阳市',
    date: '2022-09-14',
    address: '井陉矿区正定街西五路'
  }
])

// 页面加载时获取数据
onMounted(() => {
  fetchData()
})

// 获取任务列表数据
const fetchData = async () => {
  try {
    // TODO: 调用后端API获取指派给当前网格员的任务列表
    // const res = await getTaskList(userStore.userInfo?.id)
    // taskList.value = res.data
    console.log('获取任务列表', userStore.userInfo)
  } catch (error) {
    console.error('获取任务列表失败', error)
  }
}

// 获取徽章颜色
const getBadgeColor = (id) => {
  const colors = ['#F56C6C', '#E6A23C', '#9B59B6', '#67C23A']
  return colors[(id - 1) % colors.length]
}

// 获取徽章文字（中文数字）
const getBadgeText = (id) => {
  const texts = ['四', '三', '五', '二', '一', '六', '七', '八', '九', '十']
  return texts[(id - 1) % texts.length]
}

// 点击"去检测"按钮，跳转到任务详情页
const handleGoDetect = (task) => {
  router.push(`/task-detail/${task.id}`)
}
</script>

<style scoped>
.page-container {
  padding: 20px;
}

.task-card {
  margin-top: 20px;
}

.task-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.task-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: 1px dashed #e4e7ed;
}

.task-item:last-child {
  border-bottom: none;
}

.task-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.task-badge {
  width: 40px;
  height: 40px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: bold;
  font-size: 18px;
}

.task-content {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.task-location {
  font-size: 14px;
  color: #333;
}

.task-address {
  font-size: 12px;
  color: #909399;
}
</style>