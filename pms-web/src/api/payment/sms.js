import request from '@/utils/request'

// 查询消息推送记录列表
export function listSms(query) {
  return request({
    url: '/payment/sms/list',
    method: 'get',
    params: query
  })
}

// 查询消息推送记录详细
export function getSms(id) {
  return request({
    url: '/payment/sms/' + id,
    method: 'get'
  })
}

// 新增消息推送记录
export function addSms(data) {
  return request({
    url: '/payment/sms',
    method: 'post',
    data: data
  })
}

// 修改消息推送记录
export function updateSms(data) {
  return request({
    url: '/payment/sms/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除消息推送记录
export function delSms(id) {
  return request({
    url: '/payment/sms/' + id,
    method: 'delete'
  })
}

// 导出消息推送记录
export function exportSms(query) {
  return request({
    url: '/payment/sms/export',
    method: 'get',
    params: query
  })
}