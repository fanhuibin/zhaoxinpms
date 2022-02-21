import request from '@/utils/request';

// 查询表单的操作权限设置
export function getFormOperates(instanceId, taskId) {
    return request({
        url: `/activiti/process/designer/formOperates/` + instanceId + '/' + taskId,
        method: 'get',
    });
}

export function getRead(formOperates, field) {
    if (!formOperates || !formOperates.length) return true;
    let arr = formOperates.filter(o => o.vModel === field) || [];
    if (!arr.length) return true;
    let item = arr[0];
    return item.read;
}

export function getWrite(formOperates, field) {
    if (!formOperates || !formOperates.length) return false;
    let arr = formOperates.filter(o => o.vModel === field) || [];
    if (!arr.length) return false;
    let item = arr[0];
    return item.write;
}
