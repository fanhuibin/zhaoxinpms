import request from '@/utils/request'

// 查询投诉工单列表
export function listComplaints(query) {
  return request({
    url: '/business/complaints/list',
    method: 'get',
    params: query
  })
}

// 查询投诉工单详细
export function getComplaints(id) {
  return request({
    url: '/business/complaints/' + id,
    method: 'get'
  })
}

// 查询报修工单详细
export function getComplaintsByInstanceId(instanceId) {
    return request({
        url: '/business/complaints/instanceId/' + instanceId,
        method: 'get',
    });
}

// 新增投诉工单
export function addComplaints(data) {
  return request({
    url: '/business/complaints',
    method: 'post',
    data: data
  })
}

// 修改投诉工单
export function updateComplaints(data) {
  return request({
    url: '/business/complaints/' + data.id,
    method: 'put',
    data: data
  })
}

// 删除投诉工单
export function delComplaints(id) {
  return request({
    url: '/business/complaints/' + id,
    method: 'delete'
  })
}

// 导出投诉工单
export function exportComplaints(query) {
  return request({
    url: '/business/complaints/export',
    method: 'get',
    params: query
  })
}