import request from '@/utils/request'

// 查询物业费支付订单列表
export function listPaymentOrder(query) {
  return request({
    url: '/payment/paymentOrder/list',
    method: 'get',
    params: query
  })
}

// 查询物业费支付订单详细
export function getPaymentOrder(id) {
  return request({
    url: '/payment/paymentOrder/' + id,
    method: 'get'
  })
}

