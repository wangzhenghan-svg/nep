import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/Register.vue')
    },
    {
        path: '/select-grid',
        name: 'SelectGrid',
        component: () => import('@/views/SelectGrid.vue')
    },
    {
        path: '/submit-aqi',
        name: 'SubmitAQI',
        component: () => import('@/views/SubmitAQI.vue')
    },
    {
        path: '/history',
        name: 'History',
        component: () => import('@/views/History.vue')
    },
    {
        path: '/',
        redirect: '/login'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router