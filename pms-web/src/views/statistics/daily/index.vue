<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="收费时间">
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
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <JTable v-loading="listLoading" :data="list" :summary-method="getSummaries" show-summary>
                    <el-table-column prop="resourceName" label="编号" align="center" />
                    <el-table-column prop="total" label="收款金额" align="center" />
                    <el-table-column prop="returnTotal" label="退款金额" align="center" />
                    <el-table-column v-for="(item, index) in payTypeOptions" :key="index" :label="item.name" :property="item.code" align="center">
                        <el-table-column label="收款" align="center" :prop="item.code">
                            <template slot-scope="scope">{{ scope.row.typeMoney[scope.column.property] }}</template>
                        </el-table-column>
                        <el-table-column label="退款" align="center" :prop="item.code">
                            <template slot-scope="scope">{{ scope.row.typeReturnMoney[scope.column.property] }}</template>
                        </el-table-column>
                    </el-table-column>
                </JTable>
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
            showAll: false,
            query: {
                beginDate: undefined,
                endDate: undefined,
            },
            list: [],
            listLoading: true,
            payTypeOptions: [],
            pickerVal: [],
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
        };
    },
    computed: {},
    created() {
        this.getPayTypeOptions();
        this.initData();
    },
    methods: {
        initData() {
            this.listLoading = true;
            if (this.query.beginDate == undefined) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                this.pickerVal = [start.getTime(), end.getTime()];
            }

            if (this.pickerVal && this.pickerVal.length) {
                this.query.beginDate = this.pickerVal[0];
                this.query.endDate = this.pickerVal[1];
            } else {
                this.query.beginDate = '';
                this.query.endDate = '';
            }
            request({
                url: `/statistics/DailyReport`,
                method: 'get',
                data: this.query,
            }).then(res => {
                this.list = res.data;
                this.listLoading = false;
            });
        },
        getPayTypeOptions() {
            listPaymentMethod().then(res => {
                this.payTypeOptions = res.data.list;
            });
        },
        getSummaries(param) {
            const { columns, data } = param;
            const sums = [];
            columns.forEach((column, index) => {
                if (index === 0) {
                    sums[index] = '总计';
                    return;
                } else if (index === 2 || index === 3) {
                    const values = data.map(item => Number(item[column.property]));
                    if (!values.every(value => isNaN(value))) {
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);

                            if (!isNaN(value)) {
                                return (parseFloat(prev) + parseFloat(curr)).toFixed(2);
                            } else {
                                return prev;
                            }
                        }, 0);
                        sums[index] += ' 元';
                    } else {
                        sums[index] = '';
                    }
                } else {
                    sums[index] = '';
                }
            });

            return sums;
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
