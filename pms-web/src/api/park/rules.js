import request from '@/utils/request'

// 查询规则列表
export function listAllRules() {
    return request({
      url: '/park/rules/all',
      method: 'get'
    })
}
// 查询停车场计费规则列表
export function listRules(query) {
  return request({
    url: '/park/rules/list',
    method: 'get',
    params: query
  })
}

// 查询停车场计费规则详细
export function getRules(id) {
  return request({
    url: '/park/rules/' + id,
    method: 'get'
  })
}

// 新增停车场计费规则
export function addRules(data) {
  return request({
    url: '/park/rules',
    method: 'post',
    data: data
  })
}

// 修改停车场计费规则
export function updateRules(data) {
  return request({
    url: '/park/rules/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除停车场计费规则
export function delRules(id) {
  return request({
    url: '/park/rules/' + id,
    method: 'delete'
  })
}

// 导出停车场计费规则
export function exportRules(query) {
  return request({
    url: '/park/rules/export',
    method: 'get',
    params: query
  })
}