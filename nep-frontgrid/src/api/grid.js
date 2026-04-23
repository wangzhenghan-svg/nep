import request from '@/utils/request'

// 网格员登录
export function gridLogin(logInID, logInPwd) {
    return request({
        url: '/member/login',
        method: 'post',
        params: { logInID, logInPwd }
    })
}

// 获取指派给当前网格员的任务列表
export function getTaskList(gridWorkerId) {
    return request({
        url: '/grid/task/list',
        method: 'get',
        params: { gridWorkerId }
    })
}

// 获取任务详情
export function getTaskDetail(taskId) {
    return request({
        url: `/grid/task/detail/${taskId}`,
        method: 'get'
    })
}

// 提交实测数据
export function submitAqiData(data) {
    return request({
        url: '/grid/aqi/submit',
        method: 'post',
        data
    })
}