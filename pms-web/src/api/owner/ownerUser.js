import request from '@/utils/request'

// 查询业主信息列表
export function listOwnerUser(query) {
  return request({
    url: '/owner/ownerUser/list',
    method: 'get',
    params: query
  })
}

// 查询业主信息详细
export function getOwnerUser(id) {
  return request({
    url: '/owner/ownerUser/' + id,
    method: 'get'
  })
}

// 查询业主信息详细（包含商铺和费用信息）
export function getOwnerUserDetail(id) {
    return request({
      url: '/owner/ownerUser/detail/' + id,
      method: 'get'
    })
  }

// 新增业主信息
export function addOwnerUser(data) {
  return request({
    url: '/owner/ownerUser',
    method: 'post',
    data: data
  })
}

// 修改业主信息
export function updateOwnerUser(data) {
  return request({
    url: '/owner/ownerUser/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除业主信息
export function delOwnerUser(id) {
  return request({
    url: '/owner/ownerUser/' + id,
    method: 'delete'
  })
}

// 导出业主信息
export function exportOwnerUser(query) {
  return request({
    url: '/owner/ownerUser/export',
    method: 'get',
    params: query
  })
}