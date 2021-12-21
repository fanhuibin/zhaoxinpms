<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16" v-if="false">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="状态">
                            <el-select v-model="query.state" placeholder="请选择" clearable>
                                <el-option
                                    v-for="(item, index) in stateOptions"
                                    :key="index"
                                    :label="item.label"
                                    :value="item.value"
                                    :disabled="item.disabled"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
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
                                    :label="item.fullName"
                                    :value="item.id"
                                    :disabled="item.disabled"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
                            <el-button icon="el-icon-refresh-right" @click="reset()">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <div>
                        <el-button type="primary" icon="el-icon-plus" @click="create()">新增</el-button>
                    </div>
                    <div class="Jcommon-head-right">
                        <el-tooltip effect="dark" content="刷新" placement="top">
                            <el-link icon="icon-ym icon-ym-Refresh Jcommon-head-icon" :underline="false" @click="reset()" />
                        </el-tooltip>

                        
                    </div>
                </div>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column prop="feeUser" label="客户姓名" align="left" />
                    <el-table-column label="收费项目" prop="feeItemName" algin="left" />
                    <el-table-column prop="payType" label="付款方式" align="left" />
                    <el-table-column prop="amt" label="收费金额" align="left" />
                    <el-table-column prop="operateUser" label="收款人" align="left" />
                    <el-table-column prop="operateTime" label="收款时间" align="left" />
                    <el-table-column prop="refundUser" label="退款人" align="left" />
                    <el-table-column prop="refundTime" label="退款时间" align="left" />
                    <el-table-column prop="state" label="状态" align="left">
                        <template slot-scope="scope">
                            <el-tag v-if="scope.row.state == 'refunded'">已退款</el-tag>
                            <el-tag type="danger" v-if="scope.row.state == 'paied'">未退款</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="remark" label="备注" align="left" />
                    <el-table-column label="操作" fixed="right" width="110">
                        <template slot-scope="scope">
                            <el-button v-if="scope.row.state == 'paied'" type="text" @click="refund(scope.row.id)">退还</el-button>
                            <el-dropdown>
                                <span class="el-dropdown-link">
                                    <el-button type="text" size="mini">
                                        打印
                                        <i class="el-icon-arrow-down el-icon--right"></i>
                                    </el-button>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item @click.native="handlePrint(scope.row.id)">打印收款单</el-dropdown-item>
                                    <el-dropdown-item v-if="scope.row.state == 'refunded'" @click.native="handleRefundPrint(scope.row.id)">
                                        打印退还单
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </template>
                    </el-table-column>
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
        <CreateForm v-if="formVisible" ref="CreateForm" @refresh="refresh" />
        <RefundForm v-if="refundFormVisible" ref="RefundForm" @refresh="refresh" />
        <Print v-if="printVisible" ref="Print" @refresh="refresh" />
    </div>
</template>

<script>
import request from '@/utils/request';
import { getDictionaryType, getDictionaryDataSelector } from '@/api/systemData/dictionary';
import CreateForm from './payDepositCreate';
import RefundForm from './payDepositRefund';
import Print from '../print';
import { getUsername } from '@/utils/auth';

export default {
    components: { CreateForm, RefundForm, Print },
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
            listLoading: false,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 10,
                sort: 'desc',
                sidx: '',
            },
            formVisible: false,
            refundFormVisible: false,
            printVisible: false,
            blockOptions: [],
            feeItemList: [],
            payTypeOptions: [],
            stateOptions: [
                { value: 'paied', label: '未退款' },
                { value: 'refunded', label: '已退款' },
            ],
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
                data: { type: 'deposit' },
            }).then(res => {
                this.feeItemList = res.data;
            });
        },
        getPayTypeOptions() {
            getDictionaryDataSelector('e14b3a85a37048c8aa39ca97570fb18c').then(res => {
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
                url: `/payment/PaymentDeposit`,
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
        refund(id, isDetail) {
            this.refundFormVisible = true;
            this.$nextTick(() => {
                this.$refs.RefundForm.init(id, this.blockOptions, this.feeItemList, isDetail);
            });
        },
        handlePrint(id) {
            this.printVisible = true;
            var username = getUsername();
            var url = `${this.define.REPORTURL}/view/606409482584907776?id=${id}&opUser=${username}`;
            this.$nextTick(() => {
                this.$refs.Print.init(url);
            });
        },
        handleRefundPrint(id) {
            this.printVisible = true;
            var username = getUsername();
            var url = `${this.define.REPORTURL}/view/606621855862153216?id=${id}&opUser=${username}`;
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
