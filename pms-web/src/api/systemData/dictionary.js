import request from '@/utils/request'

// 获取数据字典分类
export function getDictionaryType() {
  return request({
    url: '/Base/DictionaryType',
    method: 'GET'
  })
}

// 获取字典分类下拉框列表
export function getDictionaryTypeSelector() {
  return request({
    url: '/Base/DictionaryType/Selector',
    method: 'GET'
  })
}

// 添加数据字典分类
export function createDictionaryType(data) {
  return request({
    url: `/Base/DictionaryType`,
    method: 'POST',
    data
  })
}

// 修改数据字典分类
export function updateDictionaryType(data) {
  return request({
    url: `/Base/DictionaryType/${data.id}`,
    method: 'PUT',
    data
  })
}

// 获取数据字典分类信息
export function getDictionaryTypeInfo(id) {
  return request({
    url: `/Base/DictionaryType/${id}`,
    method: 'GET'
  })
}

// 删除数据字典分类
export function delDictionaryType(id) {
  return request({
    url: `/Base/DictionaryType/${id}`,
    method: 'DELETE'
  })
}

// 获取数据字典列表
export function getDictionaryDataList(typeId, data) {
  return request({
    url: `/Base/DictionaryData/${typeId}`,
    method: 'GET',
    data
  })
}

// 获取数据字典列表(分类+内容)
export function getDictionaryAll() {
  return request({
    url: `/Base/DictionaryData/All`,
    method: 'GET'
  })
}

// 获取字典分类下拉框(项目上级)
export function getDictionaryDataTypeSelector(dictionaryTypeId, isTree) {
  return request({
    url: `/Base/DictionaryData/${dictionaryTypeId}/Selector`,
    method: 'GET',
    data: { isTree }
  })
}

// 获取字典数据下拉框列表
export function getDictionaryDataSelector(dictionaryTypeId) {
  return request({
    url: `/Base/DictionaryData/${dictionaryTypeId}/Data/Selector`,
    method: 'GET'
  })
}

// 添加数据字典
export function createDictionaryData(data) {
  return request({
    url: '/Base/DictionaryData',
    method: 'POST',
    data
  })
}

// 修改数据字典
export function updateDictionaryData(data) {
  return request({
    url: `/Base/DictionaryData/${data.id}`,
    method: 'PUT',
    data
  })
}

// 获取数据字典信息
export function getDictionaryDataInfo(id) {
  return request({
    url: `/Base/DictionaryData/${id}/Info`,
    method: 'GET'
  })
}

// 删除数据字典信息
export function delDictionaryData(id) {
  return request({
    url: `/Base/DictionaryData/${id}`,
    method: 'DELETE'
  })
}

// 更新字典状态
export function updateDictionaryState(id) {
  return request({
    url: `/Base/DictionaryData/${id}/Actions/State`,
    method: 'PUT'
  })
}
