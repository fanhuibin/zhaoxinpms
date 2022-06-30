<template>
    <div v-if="loaded" style="display: none">
        <div id="print-header" ref="print-header">
            <table class="header" cellpadding="0" cellspacing="0" style="width: 99%">
                <tr>
                    <td align="center" colspan="3">
                        <div style="width: 100%; height: 110px; position: relative">
                            <span style="display: block; position: absolute; top: 0; left: 0">
                                <!--  Logo
                            <img src="{{data.retData.logo}}" width="110" height="110">
                             -->
                            </span>
                            <h2 style="width: 60%; margin: 0 auto; line-height: 80px">{{ data.company }} 预收款收款收据</h2>
                            <span style="display: block; position: absolute; top: 0; right: 0">
                                <!-- 扫码支付二维码 
                            <img src="{{= data.retData.expressQRcode}}" width="110" height="110">
                            --></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td width="40%">
                        <b>商铺号：</b>
                        {{ data.paymentPre.resourceName }}
                    </td>
                    <td width="40%">
                        <b>收款人：</b>
                        {{ data.creator.nickName }}
                    </td>
                    <td>
                        <b>收款时间：</b>
                        {{ parseTime(data.paymentPre.operateTime) }}
                    </td>
                </tr>
            </table>
        </div>
        <div id="print-body" ref="print-body">
            <table class="body" cellpadding="0" cellspacing="0" style="width: 99%">
                <thead>
                    <tr>
                        <th width="40%">收费项名</th>
                        <th width="10%">姓名</th>
                        <th width="15%">单价</th>
                        <th width="15%">数量</th>
                        <th width="20%">总价</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{{ data.paymentPre.feeItemName }}</td>
                        <td align="center">{{ data.paymentPre.feeUser }}</td>
                        <td align="right">{{ data.paymentPre.amt }}</td>
                        <td align="center">1</td>
                        <td align="right">{{ data.paymentPre.amt }}</td>
                    </tr>
                </tbody>

                <tbody></tbody>
                <tfoot>
                    <tr>
                        <td>本页小计</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td align="right" tdata="subSum" format="#,##0.00">###</td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <div id="print-pages" ref="print-pages">
            <p style="text-align: center; font-size: 12px">
                页码：
                <span tdata="pageNO">第 ## 页</span>
                /
                <span tdata="pageCount">共 ## 页</span>
            </p>
        </div>
    </div>
</template>
<script>
import { getLodop } from '@/utils/lodop/LodopFuncs';
import request from '@/utils/request';
export default {
    data() {
        return {
            data: {},
            loaded: false,
        };
    },
    created() {},
    methods: {
        getData(payNo) {
            return request({
                url: '/print/printData/prePayPrint?payNo=' + payNo,
                method: 'get',
            });
        },
        print(payNo) {
            this.getData(payNo).then(res => {
                this.loaded = true;
                this.data = res.data;
                this.$nextTick(() => {
                    var strBodyStyle =
                        '<style>body { margin: 0; padding: 0; border: 0;} table tr { font-size: 12px; line-height: 20px; }' +
                        'table.body  { border-bottom: 1px solid #666; border-right: 1px solid #666; } table.body th,table.body ' +
                        'td { border-left: 1px solid #666; border-top: 1px solid #666; font-weight: normal; padding: 1px; }' +
                        'table.body td { padding: 0 5px; } table.header tr, table.footer tr { font-size: 14px; } </style>';
                    LODOP = getLodop();
                    if (!LODOP) {
                        return false;
                    }
                    LODOP.PRINT_INIT('');
                    LODOP.SET_PRINT_PAGESIZE(2, 0, 0, 'A4'); // 0 操作者自行决定或打印机缺省设置 1 纵向打印，固定纸张；2 横向打印，固定纸张
                    // LODOP.SET_PREVIEW_WINDOW(1,0,0,1000,600,""); // 初始预览窗口大小
                    LODOP.SET_SHOW_MODE('LANDSCAPE_DEFROTATED', 1); // 横向打印时正向显示
                    LODOP.SET_SHOW_MODE('HIDE_PAPER_BOARD', 1); // 去除背景滚动线
                    LODOP.SET_PRINT_MODE('AUTO_CLOSE_PREWINDOW', 1); // 打印后自动关闭预览
                    LODOP.SET_PRINT_MODE('CUSTOM_TASK_NAME', '发运单打印'); // 打印队列中的文档名
                    // 追加打印头部
                    LODOP.ADD_PRINT_TABLE(5, 5, '100%', 150, strBodyStyle + this.$refs['print-header'].outerHTML);
                    LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1); // 页眉页脚项

                    // 追加打印主体：分页、循环表格
                    LODOP.ADD_PRINT_TABLE(155, 5, '100%', 400, strBodyStyle + this.$refs['print-body'].outerHTML);
                    // LODOP.SET_PRINT_STYLEA(0,"Vorient",3);

                    // 追加打印底部
                    // LODOP.ADD_PRINT_TABLE(570, 5, '100%', 150, strBodyStyle + this.$refs['print-footer'].outerHTML);
                    // LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1); // 页眉页脚项
                    // LODOP.SET_PRINT_STYLEA(0, 'LinkedItem', 2);

                    LODOP.NewPageA();

                    // 追加页码
                    LODOP.ADD_PRINT_HTM(745, 5, '100%', 15, this.$refs['print-pages'].outerHTML);
                    LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1);
                    // LODOP.SET_PRINT_STYLEA(0,"LinkedItem",3);
                    LODOP.SET_PRINT_STYLEA(0, 'Horient', 1);

                    LODOP.PREVIEW();
                });
            });
        },
    },
};
</script>
<style lang="less" scoped></style>
