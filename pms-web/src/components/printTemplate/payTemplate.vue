<template>
    <div>
        <el-button size="mini" type="text" icon="el-icon-edit" @click="print()">
            模板维护
        </el-button>
        <div id="print-header" ref="print-header">
            <table class="header" cellpadding="0" cellspacing="0" style="width: 99%">
                <tr>
                    <td align="center" colspan="3">
                        <div style="width: 100%; height: 110px; position: relative">
                            <span style="display: block; position: absolute; top: 0; left: 0">
                                <!-- 供应商 Logo
                            <img src="{{data.retData.logo}}" width="110" height="110">
                             -->
                            </span>
                            <h2 style="width: 60%; margin: 0 auto; line-height: 80px">{{ data.retData.supplierInfo.companyName }} 送货单</h2>
                            <span style="display: block; position: absolute; top: 0; right: 0">
                                <!-- 二维码 
                            <img src="{{= data.retData.expressQRcode}}" width="110" height="110">
                            --></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td width="40%">
                        <b>地址：</b>
                        {{ data.retData.supplierInfo.address.substring(0, 25) }}
                    </td>
                    <td>
                        <b>电话：</b>
                        {{ data.retData.supplierInfo.mobilePhone }}
                    </td>
                    <td>
                        <b>传真：</b>
                        {{ data.retData.supplierInfo.fax }}
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>客户名称：</b>
                        {{ data.retData.buyerInfo.companyName }}
                    </td>
                    <td>
                        <b>订单编号：</b>
                        {{ data.retData.order.code }}
                    </td>
                    <td>
                        <b>下单时间：</b>
                        {{ data.retData.order.createAt}}
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>收货地址：</b>
                        {{ data.retData.order.address }}
                    </td>
                    <td>
                        <b>发货单号：</b>
                        {{ data.retData.code }}
                    </td>
                    <td>
                        <b>发货日期：</b>
                        {{ data.retData.createAt}}
                    </td>
                </tr>
            </table>
        </div>
        <div id="print-body" ref="print-body">
            <table class="body" cellpadding="0" cellspacing="0" style="width: 99%">
                <thead>
                    <tr>
                        <th width="8%">医院商品编码</th>
                        <th width="18%">医院商品名称</th>
                        <th width="15%">规格型号</th>
                        <th width="17%">生产企业</th>
                        <th width="5%">生产批号</th>
                        <th width="5%">商品有效期</th>
                        <th width="5%">单位</th>
                        <th width="8%">单价（元）</th>
                        <th width="5%">发运数量</th>
                        <th width="8%">总价（元）</th>
                        <th width="5%">收货数量</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, i) in data.retData.waybillDetails" :key="i">
                        <td>{{ item.showCode }}</td>
                        <td>{{ item.showName }}</td>
                        <td>{{ item.sepecification }}</td>
                        <td>{{ item.manufacturer }}</td>
                        <td align="center">{{ item.batch }}</td>
                        <td align="center">{{ item.expireTime }}</td>
                        <td align="center">{{ item.unit }}</td>
                        <td align="right">{{ item.price }}</td>
                        <td align="center">{{ item.sendNum }}</td>
                        <td align="right">{{ item.totalPrice }}</td>
                        <td align="center">{{ item.getNum }}</td>
                    </tr>
                </tbody>

                <tbody></tbody>
                <tfoot>
                    <tr>
                        <td>本页小计</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td align="center" tdata="subSum">###</td>
                        <td align="right" tdata="subSum" format="#,##0.000">######</td>
                        <td align="center" tdata="subSum">###</td>
                    </tr>
                    <tr>
                        <td>合计</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td align="center">{{ data.retData.allNum }}</td>
                        <td align="right">{{ data.retData.allMoney }}</td>
                        <td align="center">{{ data.retData.allgetNum }}</td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <div id="print-footer" ref="print-footer">
            <table class="footer" cellpadding="0" cellspacing="0" style="width: 99%">
                <tr>
                    <td width="40%">
                        <b>金额合计（大写）：</b>
                        {{ data.retData.allMoney }}
                    </td>
                    <td colspan="2"><b>备注：</b></td>
                </tr>
                <tr>
                    <td>
                        <b>制单人：</b>
                        {{ data.retData.printBy }}
                    </td>
                    <td>
                        <b>运输方式：</b>
                        {{ data.retData.expressName }}
                    </td>
                    <td><b>发货人：</b></td>
                </tr>
                <tr>
                    <td>
                        <b>配送人：</b>
                        {{ data.retData.deliveryPerson }}
                    </td>
                    <td><b>发货时温湿度：</b></td>
                    <td><b>收货时温湿度：</b></td>
                </tr>
                <tr>
                    <td>
                        <b>配送人联系电话：</b>
                        {{ data.retData.deliveryContact }}
                    </td>
                    <td><b>收货人：</b></td>
                    <td><b>收货时间：</b></td>
                </tr>
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
            data: {
                status: 200,
                retData: {
                    code: 'FY20161230000014',
                    deliveryPerson: '\u5927\u6c99\u53d1\u65af\u8482\u82ac',
                    deliveryContact: '15922334455',
                    createAt: 'Dec 30, 2016 5:53:44 PM',
                    receiverAt: 'Dec 30, 2016 5:53:51 PM',
                    order: {
                        code: 'DD20161230000009',
                        supplierContactPerson: 'gongyings01',
                        supplierCompanyName: 'gongyings01',
                        checkAt: 'Dec 30, 2016 5:51:44 PM',
                        contactPerson: '\u7f57\u5c0f\u9ed1',
                        contactPhone: '15922334455',
                        address: '\u5317\u4eac \u5317\u4eac \u5317\u4eac\u5e02\u671d\u9633\u533a\u9ad8\u65b0\u533a',
                        companyName: 'yljigou02',
                        goodsTypeNum: 2,
                        goodsNum: 20,
                        totalPrice: 2000,
                        realTotalPrice: 2000,
                        createAt: 'Dec 30, 2016 5:44:16 PM',
                        updateAt: 'Dec 30, 2016 5:53:54 PM',
                        deliveryAt: 'Dec 30, 2016 5:53:51 PM',
                    },
                    waybillDetails: [
                        {
                            sepecification: 'EA36\u3001EA50\u30010G\/EA\uff1a250ml\/\u74f6\u3001500ml\/\u74f6\u62161000ml\/\u74f6',
                            manufacturer: 'xxxxxx\u6709\u9650\u516c\u53f8',
                            unit: '',
                            price: 100,
                            sendNum: 10,
                            batch: '89879',
                            getNum: 10,
                            receiverAt: 'Dec 30, 2016 5:53:51 PM',
                            supplierCompanyName: 'gongyings01',
                            contactPerson: '\u7f57\u5c0f\u9ed1',
                            contactPhone: '15922334455',
                            createAt: 'Dec 30, 2016 5:53:44 PM',
                            expireTime: 'Dec 31, 2016 12:00:00 AM',
                            productionNo: '2016-12-05 00:00:00.0',
                            showCode: '6840120085000114001',
                            showName: '\u5df4\u6c0f\u67d3\u8272\u6db2',
                            goodsNum: 10,
                            totalPrice: 1000,
                        },
                        {
                            sepecification: 'EA36\u3001EA50\u30010G\/EA\uff1a250ml\/\u74f6\u3001500ml\/\u74f6\u62161000ml\/\u74f6',
                            manufacturer: 'xxxxxx\u6709\u9650\u516c\u53f8',
                            unit: '',
                            price: 100,
                            sendNum: 10,
                            batch: '89879',
                            getNum: 10,
                            receiverAt: 'Dec 30, 2016 5:53:51 PM',
                            supplierCompanyName: 'gongyings01',
                            contactPerson: '\u7f57\u5c0f\u9ed1',
                            contactPhone: '15922334455',
                            createAt: 'Dec 30, 2016 5:53:44 PM',
                            expireTime: 'Dec 31, 2016 12:00:00 AM',
                            productionNo: '2016-12-05 00:00:00.0',
                            showCode: '6840120085000114001',
                            showName: '\u5df4\u6c0f\u67d3\u8272\u6db2',
                            goodsNum: 10,
                            totalPrice: 1000,
                        },
                        {
                            sepecification: '500ml',
                            manufacturer: 'xxxxxxxxxx\u6709\u9650\u516c\u53f8',
                            unit: '',
                            price: 100,
                            sendNum: 10,
                            batch: '567',
                            getNum: 10,
                            rejectNum: 0,
                            receiverAt: 'Dec 30, 2016 5:53:51 PM',
                            supplierCompanyName: 'gongyings01',
                            contactPerson: '\u7f57\u5c0f\u9ed1',
                            contactPhone: '15922334455',
                            createAt: 'Dec 30, 2016 5:53:44 PM',
                            expireTime: 'Dec 31, 2016 12:00:00 AM',
                            productionNo: '2016-12-06 00:00:00.0',
                            sterilizationDate: null,
                            invoiceDate: null,
                            waybillIds: null,
                            showCode: '6840140235000036001',
                            showName: '\u5c3f\u6709\u5f62\u6210\u5206\u5206\u6790\u4eea\u5e94\u7528\u8bd5\u5242-\u7a00\u91ca\u6db2',
                            goodsNum: 10,
                            totalPrice: 1000,
                        },
                    ],
                    buyerName: null,
                    supplierName: null,
                    searchValue: null,
                    expressName: '\u81ea\u8425\u7269\u6d41',
                    buyerInfo: { companyName: 'yljigou02' },
                    supplierInfo: {
                        companyName: 'gongyings01',
                        legalPersonName: 'gongyings01',
                        legalPersonId: '540422199209267985',
                        contactPerson: 'gongyings01',
                        mobilePhone: '15812341234',
                        qq: null,
                        email: '123@qq.com',
                        mainPhone: '15812341234',
                        fax: '15812341234',
                        address: '\u5317\u4eac\u5e02\u671d\u9633\u533a',
                        logo: '\/logo\/2016-12-29\/5864bc4195a53.jpg',
                        auditTime: 'Dec 29, 2016 10:43:07 AM',
                        auditReason: 'butongguo',
                        createAt: 'Dec 29, 2016 10:05:10 AM',
                        updateAt: 'Dec 29, 2016 3:33:24 PM',
                    },
                    allNum: 20,
                    allMoney: 2000,
                    allgetNum: 20,
                    logo: '',
                    expressQRcode: '',
                    printBy: 'yljigou02',
                },
            },
        };
    },
    created() {},
    methods: {
        getData() {},
        print() {
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
            console.log();
            // 追加打印头部
            console.log(this.$refs['print-header']);
            LODOP.ADD_PRINT_TABLE(5, 5, '100%', 180, strBodyStyle + this.$refs['print-header'].outerHTML);
            LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1); // 页眉页脚项

            // 追加打印主体：分页、循环表格
            LODOP.ADD_PRINT_TABLE(185, 5, '100%', 370, strBodyStyle + this.$refs['print-body'].outerHTML);
            // LODOP.SET_PRINT_STYLEA(0,"Vorient",3);

            // 追加打印底部
            LODOP.ADD_PRINT_TABLE(570, 5, '100%', 150, strBodyStyle + this.$refs['print-footer'].outerHTML);
            LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1); // 页眉页脚项
            LODOP.SET_PRINT_STYLEA(0, 'LinkedItem', 2);

            LODOP.NewPageA();

            // 追加页码
            LODOP.ADD_PRINT_HTM(745, 5, '100%', 15, this.$refs['print-pages'].outerHTML);
            LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1);
            // LODOP.SET_PRINT_STYLEA(0,"LinkedItem",3);
            LODOP.SET_PRINT_STYLEA(0, 'Horient', 1);

            LODOP.PREVIEW();
        },
    },
};
</script>
<style lang="less" scoped></style>
