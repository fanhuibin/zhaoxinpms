import request from '@/utils/request';

// 查询支付方式列表
export function listPaymentMethod(query) {
    return request({
        url: '/payment/paymentMethod/list',
        method: 'get',
        params: query,
    });
}

// 查询支付方式详细
export function getPaymentMethod(id) {
    return request({
        url: '/payment/paymentMethod/' + id,
        method: 'get',
    });
}

// 新增支付方式
export function addPaymentMethod(data) {
    return request({
        url: '/payment/paymentMethod',
        method: 'post',
        data: data,
    });
}

// 修改支付方式
export function updatePaymentMethod(id, data) {
    return request({
        url: '/payment/paymentMethod' + id,
        method: 'put',
        data: data,
    });
}

// 删除支付方式
export function delPaymentMethod(id) {
    return request({
        url: '/payment/paymentMethod/' + id,
        method: 'delete',
    });
}

// 导出支付方式
export function exportPaymentMethod(query) {
    return request({
        url: '/payment/paymentMethod/export',
        method: 'get',
        params: query,
    });
}
