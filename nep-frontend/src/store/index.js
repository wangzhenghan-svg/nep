import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
    const userInfoStr = localStorage.getItem('userInfo')
    const userInfo = ref(userInfoStr && userInfoStr !== 'undefined' ? JSON.parse(userInfoStr) : null)
    const token = ref(localStorage.getItem('token') || '')

    function setUserInfo(info) {
        userInfo.value = info
        localStorage.setItem('userInfo', JSON.stringify(info))
    }

    function setToken(t) {
        token.value = t
        localStorage.setItem('token', t)
    }

    function logout() {
        userInfo.value = null
        token.value = ''
        localStorage.removeItem('userInfo')
        localStorage.removeItem('token')
    }

    return {
        userInfo,
        token,
        setUserInfo,
        setToken,
        logout
    }
})
