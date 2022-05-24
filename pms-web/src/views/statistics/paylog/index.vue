<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="付款方式">
                            <el-select v-model="query.payMethod" placeholder="请选择" clearable>
                                <el-option
                                    v-for="(item, index) in payMethodOptions"
                                    :key="index"
                                    :label="item.name"
                                    :value="item.code"
                                    :disabled="item.disabled"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="类型">
                            <el-select v-model="query.type" placeholder="请选择">
                                <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="缴费时间">
                            <el-date-picker
                                v-model="pickerVal"
                                type="daterange"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                :picker-options="pickerOptions"
                                value-format="timestamp"
                                clearable
                                :editable="false"
                            ></el-date-picker>
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
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column prop="payNo" label="缴费单号" align="left" width="150px" />
                    <el-table-column prop="name" label="缴费内容" align="left" width="150px" />
                    <el-table-column prop="payerName" label="付款人" align="left" />
                    <el-table-column prop="type" label="类型" align="left">
                        <template slot-scope="scope">
                            <el-tag size="small" v-if="scope.row.type == '支付'">付款</el-tag>
                            <el-tag size="small" type="danger" v-if="scope.row.type == '退款'">退款</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="payTime" label="付款时间" align="left" width="150px" />
                    <el-table-column prop="payMethod" label="付款方式" align="left" />
                    <el-table-column prop="lateFeeMoney" label="滞纳金（元）" align="left" />
                    <el-table-column prop="discountMoney" label="优惠金额（元）" align="left" />
                    <el-table-column prop="receivableMoney" label="应收金额（元）" align="left" />
                    <el-table-column prop="payMoney" label="收款金额（元）" align="left" />
                    <el-table-column prop="prePayMoney" label="预存款支付（元）" align="left" />
                    <el-table-column prop="changeMoney" label="找零金额（元）" align="left" />
                    <el-table-column prop="preSaveMoney" label="找零结转金额（元）" align="left" />
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
import { listPaymentMethod } from '@/api/payment/paymentMethod';

export default {
    components: {},
    data() {
        return {
            query: {
                payMethod: undefined,
                type: undefined,
                name: undefined,
            },
            defaultProps: {
                children: 'children',
                label: 'fullName',
            },
            pickerOptions: {
                shortcuts: [
                    {
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                ],
            },
            pickerVal: [],
            typeOptions: [
                { label: '付款', value: 'pay' },
                { label: '退款', value: 'refund' },
            ],
            payMethodOptions: [],
            list: [],
            listLoading: true,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: 'pay_time',
            },
            columnList: [
                { prop: 'payTime', label: '付款时间' },
                { prop: 'payMethod', label: '支付方式' },
                { prop: 'money', label: '单行输入' },
                { prop: 'type', label: '支付和退款' },
                { prop: 'describe', label: '描述' },
                { prop: 'certificate', label: '支付凭证' },
            ],
        };
    },
    computed: {},
    created() {
        this.initData();
        this.getPayMethodOptions();
    },
    methods: {
        getPayMethodOptions() {
            listPaymentMethod().then(res => {
                this.payMethodOptions = res.data.list;
            });
        },
        initData() {
            this.listLoading = true;
            if (this.pickerVal && this.pickerVal.length) {
                this.query.payBeginDate = this.pickerVal[0];
                this.query.payEndDate = this.pickerVal[1];
            } else {
                this.query.payBeginDate = '';
                this.query.payEndDate = '';
            }
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/statistics/PaymentPayLog`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;

                this.listLoading = false;
            });
        },
        handleDel(id) {
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                type: 'warning',
            })
                .then(() => {
                    request({
                        url: `/wuye/PaymentPayLog/${id}`,
                        method: 'DELETE',
                    }).then(res => {
                        this.$message({
                            type: 'success',
                            message: res.msg,
                            onClose: () => {
                                this.initData();
                            },
                        });
                    });
                })
                .catch(() => {});
        },
        search() {
            this.listQuery = {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: 'pay_time',
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
            this.pickerVal = [];
            this.listQuery = {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: 'pay_time',
            };
            this.initData();
        },
    },
};
</script>
