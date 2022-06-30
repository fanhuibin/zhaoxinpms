import request from '@/utils/request';

// 查询商业区信息
export function listBlock(query) {
    return request({
        url: `/baseconfig/ConfigHouseBlock/selectList`,
        method: 'GET',
    });
}

// 查询楼栋管理列表
export function selectBuilding() {
    return request({
        url: '/pms/building/select',
        method: 'get',
    });
}

// 查询楼栋管理列表
export function listBuilding(query) {
    return request({
        url: '/pms/building/list',
        method: 'get',
        params: query,
    });
}

// 查询楼栋管理详细
export function getBuilding(id) {
    return request({
        url: '/pms/building/' + id,
        method: 'get',
    });
}

// 新增楼栋管理
export function addBuilding(data) {
    return request({
        url: '/pms/building',
        method: 'post',
        data: data,
    });
}

// 修改楼栋管理
export function updateBuilding(data) {
    return request({
        url: '/pms/building/' + data.id,
        method: 'put',
        data: data,
    });
}

// 删除楼栋管理
export function delBuilding(id) {
    return request({
        url: '/pms/building/' + id,
        method: 'delete',
    });
}

// 导出楼栋管理
export function exportBuilding(query) {
    return request({
        url: '/pms/building/export',
        method: 'get',
        params: query,
    });
}
