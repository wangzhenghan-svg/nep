import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
    const userInfo = ref(null)
    const token = ref(localStorage.getItem('token') || '')

    function setUserInfo(info) {
        userInfo.value = info
    }

    function setToken(newToken) {
        token.value = newToken
        localStorage.setItem('token', newToken)
    }

    function clearUserInfo() {
        userInfo.value = null
        token.value = ''
        localStorage.removeItem('token')
    }

    return { userInfo, token, setUserInfo, setToken, clearUserInfo }
})