import request from '@/utils/request'

export const statisticsAPI = {
    getProvinceSO2Stats() {
        return request.get('/province/so2')
    },

    getProvincePM25Stats() {
        return request.get('/province/pm25')
    },

    getProvinceCOStats() {
        return request.get('/province/co')
    },

    getProvinceAQIStats() {
        return request.get('/province/aqi')
    },

    getAQILevelDistribution() {
        return request.get('/aqi/level-distribution')
    },

    getAQITrend() {
        return request.get('/aqi/trend')
    },

    getRealtimeStats() {
        return request.get('/realtime')
    },

    getGridCoverage() {
        return request.get('/grid-coverage')
    },

    getDashboardData() {
        return request.get('/dashboard')
    }
}

export default statisticsAPI
