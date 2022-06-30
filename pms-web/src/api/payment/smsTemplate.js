import request from '@/utils/request'

// 查询短信模板列表
export function listSmsTemplate(query) {
  return request({
    url: '/payment/smsTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询短信模板详细
export function getSmsTemplate(id) {
  return request({
    url: '/payment/smsTemplate/' + id,
    method: 'get'
  })
}

// 新增短信模板
export function addSmsTemplate(data) {
  return request({
    url: '/payment/smsTemplate',
    method: 'post',
    data: data
  })
}

// 修改短信模板
export function updateSmsTemplate(data) {
  return request({
    url: '/payment/smsTemplate/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除短信模板
export function delSmsTemplate(id) {
  return request({
    url: '/payment/smsTemplate/' + id,
    method: 'delete'
  })
}

// 导出短信模板
export function exportSmsTemplate(query) {
  return request({
    url: '/payment/smsTemplate/export',
    method: 'get',
    params: query
  })
}