<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="编号">
                            <HouseInput  v-model="query.resourceName"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="客户姓名">
                            <el-input v-model="query.feeUser" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="状态">
                            <el-select v-model="query.type" placeholder="请选择" clearable>
                                <el-option
                                    v-for="(item, index) in typeOptions"
                                    :key="index"
                                    :label="item.label"
                                    :value="item.value"
                                    :disabled="item.disabled"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <template v-if="showAll">
                        <el-col :span="6">
                            <el-form-item label="收费项目">
                                <el-select v-model="query.feeItemId" placeholder="请选择" clearable>
                                    <el-option
                                        v-for="(item, index) in feeItemList"
                                        :key="index"
                                        :label="item.name"
                                        :value="item.id"
                                        :disabled="item.disabled"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="付款方式">
                                <el-select v-model="query.payType" placeholder="请选择" clearable>
                                    <el-option
                                        v-for="(item, index) in payTypeOptions"
                                        :key="index"
                                        :label="item.name"
                                        :value="item.code"
                                        :disabled="item.disabled"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </template>
                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
                            <el-button icon="el-icon-refresh-right" @click="reset()">重置</el-button>
                            <el-button type="text" icon="el-icon-arrow-down" @click="showAll = true" v-if="!showAll">展开</el-button>
                            <el-button type="text" icon="el-icon-arrow-up" @click="showAll = false" v-else>收起</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
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
                    <el-table-column prop="resourceName" label="编号" align="left" />
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
                    <el-table-column label="操作" fixed="right" width="200">
                        <template slot-scope="scope">
                            <el-button :disabled="scope.row.type !== 'add'" type="text" @click="handlePrint(scope.row.payNo)">打印收款单</el-button>
                            <el-button :disabled="scope.row.type !== 'refund'" type="text" @click="handleRefundPrint(scope.row.payNo)">打印退还单</el-button>
                        </template>
                    </el-table-column>
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
        <EditForm v-if="formVisible" ref="EditForm" @refresh="refresh" />
        <WithdrawForm v-if="withdrawFormVisible" ref="WithdrawForm" @refresh="refresh" />
        <PrePrint ref="Print" />
        <PreRefundPrint ref="RefundPrint" />
    </div>
</template>

<script>
import request from '@/utils/request';
import { listPaymentMethod } from '@/api/payment/paymentMethod';
import EditForm from './Form';
import WithdrawForm from './WithdrawForm';
import { getUsername } from '@/utils/auth';
import HouseInput from '@/components/HouseInput';
import PrePrint from '@/components/printTemplate/prePrint';
import PreRefundPrint from '@/components/printTemplate/preRefundPrint';

export default {
    components: { EditForm, WithdrawForm, HouseInput,  PreRefundPrint, PrePrint },
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
            defaultProps: {
                children: 'children',
                label: 'fullName',
            },
            list: [],
            listLoading: true,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            },
            formVisible: false,
            withdrawFormVisible: false,
            columnList: [
                { prop: 'block', label: '商业区' },
                { prop: 'resourceName', label: '编号' },
                { prop: 'feeUser', label: '客户姓名' },
                { prop: 'feeItemId', label: '收费项目' },
                { prop: 'payType', label: '付款方式' },
                { prop: 'amt', label: '收费金额' },
                { prop: 'remark', label: '备注' },
            ],
            blockOptions: [],
            feeItemList: [],
            payTypeOptions: [],
            typeOptions: [
                { value: 'add', label: '预存' },
                { value: 'refund', label: '退还' },
                { value: 'pay', label: '支付' },
                { value: 'payAdd', label: '找零结转' },
            ],
        };
    },
    computed: {},
    created() {
        this.initData();
        this.getPayTypeOptions();
        this.getBlockOptions();
        this.getFeeItemList();
    },
    methods: {
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
            this.formVisible = true;
            this.$nextTick(() => {
                this.$refs.EditForm.init(id, this.blockOptions, this.feeItemList, isDetail);
            });
        },
        withdraw(id, isDetail) {
            this.withdrawFormVisible = true;
            this.$nextTick(() => {
                this.$refs.WithdrawForm.init(id, this.blockOptions, this.feeItemList, isDetail);
            });
        },
        handlePrint(payNo) {
            this.$nextTick(() => {
                this.$refs.Print.print(payNo);
            });
        },
        handleRefundPrint(payNo) {
            this.$nextTick(() => {
                this.$refs.RefundPrint.print(payNo);
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
                this.query[key] = undefined;
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
