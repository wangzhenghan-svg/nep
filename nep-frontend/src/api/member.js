import request from '@/utils/request'

// 登录
export function login(logInID, logInPwd) {
    return request({
        url: '/member/login',
        method: 'post',
        params: { logInID, logInPwd }
    })
}

// 获取验证码
export function getCode(tel) {
    return request({
        url: `/member/login/code/get/${tel}`,
        method: 'get'
    })
}

// 验证码登录
export function loginCode(data) {
    return request({
        url: '/member/login/code',
        method: 'post',
        data
    })
}

// 注册
export function register(data) {
    return request({
        url: '/member/register',
        method: 'post',
        data
    })
}

// 获取所有用户
export function selectAll() {
    return request({
        url: '/member/select/all',
        method: 'get'
    })
}
