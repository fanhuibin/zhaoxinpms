<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <div>
                        <el-button type="primary" icon="el-icon-plus" @click="create()">预存</el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="withdraw()">退还</el-button>
                    </div>
                    <div class="Jcommon-head-right">
                        <el-tooltip effect="dark" content="刷新" placement="top">
                            <el-link icon="icon-ym icon-ym-Refresh Jcommon-head-icon" :underline="false" @click="reset()" />
                        </el-tooltip>

                        
                    </div>
                </div>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column prop="feeUser" label="客户姓名" align="left" />
                    <el-table-column prop="type" label="状态" align="left">
                        <template slot-scope="scope">
                            <el-tag type="danger" v-if="scope.row.type == 'refund'">退还</el-tag>
                            <el-tag v-if="scope.row.type == 'add'">预存</el-tag>
                            <el-tag v-if="scope.row.type == 'pay'">支付</el-tag>
                            <el-tag v-if="scope.row.type == 'payAdd'">找零结转</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="收费项目" prop="feeItemName" algin="left" />
                    <el-table-column prop="payType" label="付款方式" align="left" />
                    <el-table-column prop="amt" label="金额" align="left" />
                    <el-table-column prop="operateUser" label="收款人" align="left" />
                    <el-table-column prop="operateTime" label="收款时间" align="left" />
                    <el-table-column prop="payNo" label="单号" align="left" />
                    <el-table-column label="操作" fixed="right" width="110">
                        <template slot-scope="scope">
                            <el-button v-if="scope.row.type == 'refund'" type="text" @click="handleRefundPrint(scope.row.payNo)">打印退还单</el-button>
                            <el-button v-if="scope.row.type == 'add'" type="text" @click="handlePrint(scope.row.payNo)">打印收款单</el-button>
                        </template>
                    </el-table-column>
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
        <CreateForm v-if="formVisible" ref="CreateForm" @refresh="refresh" />
        <WithdrawForm v-if="withdrawFormVisible" ref="WithdrawForm" @refresh="refresh" />
        <Print v-if="printVisible" ref="Print" @refresh="refresh" />
    </div>
</template>

<script>
import request from '@/utils/request';
import { listPaymentMethod } from '@/api/payment/paymentMethod';
import CreateForm from './payPreCreate';
import WithdrawForm from './payPreWithdraw';
import Print from '../print';
import { getUsername } from '@/utils/auth';

export default {
    components: { CreateForm, Print, WithdrawForm },
    data() {
        return {
            showAll: false,
            query: {
                state: undefined,
                block: undefined,
                resourceName: undefined,
                feeUser: undefined,
                feeItemId: undefined,
                payType: undefined,
            },
            list: [],
            listLoading: false,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 10,
                sort: 'desc',
                sidx: '',
            },
            formVisible: false,
            withdrawFormVisible: false,
            printVisible: false,
            blockOptions: [],
            feeItemList: [],
            payTypeOptions: [],
        };
    },
    computed: {},
    created() {
        this.getPayTypeOptions();
        this.getBlockOptions();
        this.getFeeItemList();
    },
    methods: {
        setResourceName(resourceName) {
            this.query.resourceName = resourceName;
            if (!resourceName) {
                this.list = [];
            }
        },
        getBlockOptions() {
            request({
                url: `/baseconfig/ConfigHouseBlock/selectList`,
                method: 'GET',
            }).then(res => {
                this.blockOptions = res.data;
            });
        },
        getFeeItemList() {
            request({
                url: `/baseconfig/ConfigFeeItem/select`,
                method: 'get',
                data: { type: 'house' },
            }).then(res => {
                this.feeItemList = res.data;
            });
        },
        getPayTypeOptions() {
            listPaymentMethod({client:1}).then(res => {
                this.payTypeOptions = res.data.list;
            });
        },

        initData() {
            if (!this.query.resourceName) {
                this.$message({
                    type: 'warning',
                    message: '请先输入编号进行搜索',
                    duration: 1000,
                });
                return;
            }
            this.listLoading = true;
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/payment/PaymentPre`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;
                this.listLoading = false;
            });
        },
        create(id, isDetail) {
            if (!this.query.resourceName) {
                this.$message({
                    type: 'warning',
                    message: '请先输入编号进行搜索',
                    duration: 1000,
                });
                return;
            }
            this.formVisible = true;
            this.$nextTick(() => {
                this.$refs.CreateForm.init(id, this.blockOptions, this.feeItemList, this.query.resourceName);
            });
        },
        withdraw(id, isDetail) {
            if (!this.query.resourceName) {
                this.$message({
                    type: 'warning',
                    message: '请先输入编号进行搜索',
                    duration: 1000,
                });
                return;
            }
            this.withdrawFormVisible = true;
            this.$nextTick(() => {
                this.$refs.WithdrawForm.init(id, this.blockOptions, this.feeItemList, this.query.resourceName);
            });
        },
        handlePrint(payNo) {
            this.printVisible = true;
            var url = `${this.define.REPORTURL}/view/609560442559717376?payNo=${payNo}&opUser=${getUsername()}`;
            this.$nextTick(() => {
                this.$refs.Print.init(url);
            });
        },
        handleRefundPrint(payNo) {
            this.printVisible = true;
            var url = `${this.define.REPORTURL}/view/609633772184723456?payNo=${payNo}&opUser=${getUsername()}`;
            this.$nextTick(() => {
                this.$refs.Print.init(url);
            });
        },
        search() {
            this.listQuery = {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            };
            this.initData();
        },
        refresh(isrRefresh) {
            this.formVisible = false;
            if (isrRefresh) this.reset();
        },
        reset() {
            for (let key in this.query) {
                if (key != 'resourceName') {
                    this.query[key] = undefined;
                }
            }
            this.listQuery = {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            };
            this.initData();
        },
    },
};
</script>
