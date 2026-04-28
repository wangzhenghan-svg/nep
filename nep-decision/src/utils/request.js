import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
    baseURL: '/api/nepv/statistics',
    timeout: 15000,
    headers: {
        'Content-Type': 'application/json'
    }
})

request.interceptors.request.use(
    config => {
        console.log(`[Request] ${config.method.toUpperCase()} ${config.url}`)
        return config
    },
    error => {
        console.error('[Request Error]', error)
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    response => {
        const res = response.data
        console.log('[Response Data]', res)

        if (res.code === 200 || res.code === 1 || res.success === true) {
            return res.result
        }

        ElMessage.error(res.msg || res.message || '请求失败')
        return Promise.reject(new Error(res.msg || res.message || '请求失败'))
    },
    error => {
        console.error('[Response Error]', error)

        if (error.response) {
            const status = error.response.status
            switch (status) {
                case 404:
                    ElMessage.error('请求的资源不存在')
                    break
                case 500:
                    ElMessage.error('服务器内部错误')
                    break
                default:
                    ElMessage.error(`请求失败: ${status}`)
            }
        } else if (error.request) {
            ElMessage.error('网络错误，请检查后端服务是否启动')
        } else {
            ElMessage.error(error.message || '未知错误')
        }

        return Promise.reject(error)
    }
)

export default request
