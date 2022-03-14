import request from '@/utils/request'

// 查询业主通知公告列表
export function listOwnerNotice(query) {
  return request({
    url: '/owner/ownerNotice/list',
    method: 'get',
    params: query
  })
}

// 查询业主通知公告详细
export function getOwnerNotice(id) {
  return request({
    url: '/owner/ownerNotice/' + id,
    method: 'get'
  })
}

// 新增业主通知公告
export function addOwnerNotice(data) {
  return request({
    url: '/owner/ownerNotice',
    method: 'post',
    data: data
  })
}

// 修改业主通知公告
export function updateOwnerNotice(data) {
  return request({
    url: '/owner/ownerNotice/'+ data.id,
    method: 'put',
    data: data
  })
}

// 删除业主通知公告
export function delOwnerNotice(id) {
  return request({
    url: '/owner/ownerNotice/' + id,
    method: 'delete'
  })
}
