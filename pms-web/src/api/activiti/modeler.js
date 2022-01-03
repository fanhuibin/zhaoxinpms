import request from '@/utils/request'

// 查询模型列表
export function listModeler(query) {
  return request({
    url: '/activiti/modeler/list',
    method: 'get',
    params: query
  })
}

// 新增模型
export function addModeler(data) {
  return request({
    url: '/activiti/modeler/create',
    method: 'post',
    params: data
  })
}

// 删除模型
export function delModeler(modelId) {
  return request({
    url: '/activiti/modeler/remove/' + modelId,
    method: 'delete',
  })
}

// 导出模型
export function exportModeler(modelId) {
  return request({
    url: '/activiti/modeler/export/' + modelId,
    method: 'get',
  })
}

// 部署模型
export function deployModeler(modelId) {
  return request({
    url: '/activiti/modeler/deploy/' + modelId,
    method: 'get',
  })
}
