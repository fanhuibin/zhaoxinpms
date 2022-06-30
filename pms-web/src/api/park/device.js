import request from '@/utils/request'

// 查询停车场设备列表
export function listDevice(query) {
  return request({
    url: '/park/device/list',
    method: 'get',
    params: query
  })
}

// 查询停车场设备详细
export function getDevice(id) {
  return request({
    url: '/park/device/' + id,
    method: 'get'
  })
}

// 新增停车场设备
export function addDevice(data) {
  return request({
    url: '/park/device',
    method: 'post',
    data: data
  })
}

// 修改停车场设备
export function updateDevice(data) {
  return request({
    url: '/park/device/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除停车场设备
export function delDevice(id) {
  return request({
    url: '/park/device/' + id,
    method: 'delete'
  })
}

// 导出停车场设备
export function exportDevice(query) {
  return request({
    url: '/park/device/export',
    method: 'get',
    params: query
  })
}