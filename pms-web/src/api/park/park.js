import request from '@/utils/request'

// 查询停车场列表
export function listAllPark() {
    return request({
      url: '/park/park/all',
      method: 'get'
    })
}

// 查询停车场列表
export function listPark(query) {
  return request({
    url: '/park/park/list',
    method: 'get',
    params: query
  })
}

// 查询停车场详细
export function getPark(id) {
  return request({
    url: '/park/park/' + id,
    method: 'get'
  })
}

// 新增停车场
export function addPark(data) {
  return request({
    url: '/park/park',
    method: 'post',
    data: data
  })
}

// 修改停车场
export function updatePark(data) {
  return request({
    url: '/park/park/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除停车场
export function delPark(id) {
  return request({
    url: '/park/park/' + id,
    method: 'delete'
  })
}

// 导出停车场
export function exportPark(query) {
  return request({
    url: '/park/park/export',
    method: 'get',
    params: query
  })
}