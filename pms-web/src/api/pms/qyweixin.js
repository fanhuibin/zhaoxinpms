import request from '@/utils/request';

// 同步用户
export function syncUser() {
    return request({
        url: `/system/qywx/syncUser`,
        method: 'GET',
    });
}

// 同步部门
export function syncDept() {
    return request({
        url: '/system/qywx/syncDept',
        method: 'get',
    });
}

