import request from '@/utils/request';

// 查询报修工单列表
export function listRepair(query) {
    return request({
        url: '/business/repair/list',
        method: 'get',
        params: query,
    });
}

// 查询报修工单详细
export function getRepair(id) {
    return request({
        url: '/business/repair/' + id,
        method: 'get',
    });
}

// 查询报修工单详细
export function getRepairByInstanceId(instanceId) {
    return request({
        url: '/business/repair/instanceId/' + instanceId,
        method: 'get',
    });
}

// 新增报修工单
export function addRepair(data) {
    return request({
        url: '/business/repair',
        method: 'post',
        data: data,
    });
}

// 修改报修工单
export function updateRepair(data) {
    return request({
        url: '/business/repair/' + data.id,
        method: 'put',
        data: data,
    });
}

// 删除报修工单
export function delRepair(id) {
    return request({
        url: '/business/repair/' + id,
        method: 'delete',
    });
}

// 导出报修工单
export function exportRepair(query) {
    return request({
        url: '/business/repair/export',
        method: 'get',
        params: query,
    });
}
