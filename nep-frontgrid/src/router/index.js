import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/login',
        name: 'GridLogin',
        component: () => import('@/views/GridLogin.vue'),
        meta: { title: '网格员登录' }
    },
    {
        path: '/',
        component: () => import('@/views/GridMainLayout.vue'),
        redirect: '/task-list',
        children: [
            {
                path: 'task-list',
                name: 'GridTaskList',
                component: () => import('@/views/GridTaskList.vue'),
                meta: { title: '反馈任务列表' }
            },
            {
                path: 'task-detail/:id',
                name: 'GridTaskDetail',
                component: () => import('@/views/GridTaskDetail.vue'),
                meta: { title: '反馈任务详细信息' }
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router