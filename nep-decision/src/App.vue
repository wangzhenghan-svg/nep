
<template>
  <router-view />
</template>

<script setup>
import { onMounted } from 'vue'

onMounted(() => {
  console.log('%c 决策者端应用已启动 ', 'background: #1e90ff; color: white; padding: 4px 8px; border-radius: 4px;')
})
</script>

<style>
:root {
  --primary-color: #1e90ff;
  --primary-light: #83bff6;
  --bg-dark: #0a1929;
  --bg-panel: rgba(13, 33, 55, 0.7);
  --text-primary: #ffffff;
  --text-secondary: #a0c4ff;
  --border-color: rgba(30, 144, 255, 0.4);
  --success-color: #91cc75;
  --warning-color: #fac858;
  --danger-color: #ee6666;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Microsoft YaHei', 'PingFang SC', Arial, sans-serif;
  background: var(--bg-dark);
  color: var(--text-primary);
  overflow: hidden;
}

::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background: rgba(30, 144, 255, 0.1);
}

::-webkit-scrollbar-thumb {
  background: rgba(30, 144, 255, 0.3);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(30, 144, 255, 0.5);
}
</style>
