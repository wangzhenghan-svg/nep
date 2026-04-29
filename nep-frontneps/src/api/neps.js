import request from '@/utils/request'

export function register(data) {
    return request.post('/neps/register', data)
}

export function checkPhone(phoneNumber) {
    return request.get('/neps/checkPhone', { params: { phoneNumber } })
}

export function login(data) {
    return request.post('/neps/login', data)
}

export function submitFeedback(data) {
    return request.post('/neps/submitFeedback', data)
}

export function getHistoryFeedback(phoneNumber) {
    return request.get('/neps/historyFeedback', { params: { phoneNumber } })
}