import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue'),
        meta: { title: '登录' }
    },
    {
        path: '/',
        component: () => import('@/views/MainLayout.vue'),
        redirect: '/public-supervision',
        children: [
            {
                path: 'public-supervision',
                name: 'PublicSupervisionList',
                component: () => import('@/views/PublicSupervisionList.vue'),
                meta: { title: '公众监督数据列表' }
            },
            {
                path: 'public-supervision/detail/:id',
                name: 'PublicSupervisionDetail',
                component: () => import('@/views/PublicSupervisionDetail.vue'),
                meta: { title: '公众监督数据详情' }
            },
            {
                path: 'assign-worker/:id',
                name: 'AssignGridWorker',
                component: () => import('@/views/AssignGridWorker.vue'),
                meta: { title: '指派网格员' }
            },
            {
                path: 'confirm-aqi',
                name: 'ConfirmAQIList',
                component: () => import('@/views/ConfirmAQIList.vue'),
                meta: { title: '确认AQI数据列表' }
            },
            {
                path: 'confirm-aqi/detail/:id',
                name: 'ConfirmAQIDetail',
                component: () => import('@/views/ConfirmAQIDetail.vue'),
                meta: { title: '确认AQI数据详情' }
            },
            {
                path: 'statistics',
                name: 'Statistics',
                component: () => import('@/views/Statistics.vue'),
                meta: { title: '统计数据' },
                children: [
                    {
                        path: 'province',
                        name: 'ProvinceStatistics',
                        component: () => import('@/views/statistics/ProvinceStatistics.vue'),
                        meta: { title: '省分组检查统计' }
                    },
                    {
                        path: 'aqi-level',
                        name: 'AQILevelStatistics',
                        component: () => import('@/views/statistics/AQILevelStatistics.vue'),
                        meta: { title: 'AQI指数级数分布' }
                    },
                    {
                        path: 'aqi-trend',
                        name: 'AQITrendStatistics',
                        component: () => import('@/views/statistics/AQITrendStatistics.vue'),
                        meta: { title: 'AQI超标趋势统计' }
                    },
                    {
                        path: 'other',
                        name: 'OtherStatistics',
                        component: () => import('@/views/statistics/OtherStatistics.vue'),
                        meta: { title: '其它数据统计' }
                    }
                ]
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    if (to.path !== '/login' && !token) {
        next('/login')
    } else {
        next()
    }
})

export default router
