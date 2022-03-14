import request from '@/utils/request'

export function listWxUser(query) {
  return request({
    url: '/owner/wxuser/page',
    method: 'get',
    params: query
  })
}

export function addWxUser(obj) {
  return request({
    url: '/owner/wxuser',
    method: 'post',
    data: obj
  })
}

export function getWxUser(id) {
  return request({
    url: '/owner/wxuser/' + id,
    method: 'get'
  })
}

export function delWxUser(id) {
  return request({
    url: '/owner/wxuser/' + id,
    method: 'delete'
  })
}

export function updateWxUser(obj) {
  return request({
    url: '/owner/wxuser',
    method: 'put',
    data: obj
  })
}
