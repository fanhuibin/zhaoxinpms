import request from '@/utils/request'

// 查询流程定义列表
export function listDefinition(query) {
  return request({
    url: '/activiti/definition/list',
    method: 'get',
    params: query
  })
}

// 激活挂起流程定义
export function suspendOrActiveDefinition(data) {
  return request({
    url: '/activiti/definition/suspendOrActiveDefinition',
    method: 'post',
    params: data
  })
}

// 删除流程定义
export function delDefinition(deploymentId) {
  return request({
    url: '/activiti/definition/remove/' + deploymentId,
    method: 'delete',
  })
}

// 流程定义转成模型
export function convert2Model(data) {
  return request({
    url: '/activiti/definition/convert2Model',
    method: 'post',
    params: data
  })
}
