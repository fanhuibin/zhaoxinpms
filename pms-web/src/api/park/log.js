import request from '@/utils/request'

// 查询场内车辆
export function inListLog(query) {
  return request({
    url: '/park/log/inList',
    method: 'get',
    params: query
  })
}

// 查询已出车辆列表
export function outListLog(query) {
    return request({
      url: '/park/log/outList',
      method: 'get',
      params: query
    })
  }

// 查询车辆出入记录详细
export function getLog(id) {
  return request({
    url: '/park/log/' + id,
    method: 'get'
  })
}

// 新增车辆出入记录
export function addLog(data) {
  return request({
    url: '/park/log',
    method: 'post',
    data: data
  })
}

// 新增离场记录
export function exitLog(data) {
  return request({
    url: '/park/log/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除车辆出入记录
export function delLog(id) {
  return request({
    url: '/park/log/' + id,
    method: 'delete'
  })
}

// 导出车辆出入记录
export function exportLog(query) {
  return request({
    url: '/park/log/export',
    method: 'get',
    params: query
  })
}