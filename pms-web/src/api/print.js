// 支付成功打印
export function getPayPrintUrl(baseUrl, payLogId, resourceName, account) {
    return `${baseUrl}/view/615427513994633216?payId=${payLogId}&houseCode=${resourceName}&opUser=${account}`;
}

export function getDepositPayPrint(){

}