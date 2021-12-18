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
                <JTable ref="JTable" v-loading="listLoading" :data="list" style="width: 100%" :summary-method="getSummaries" show-summary>
                    <el-table-column prop="resourceName" label="编号" align="center" />
                    <el-table-column prop="total" label="收款金额" align="center" width="100px" />
                    <el-table-column align="center" v-for="(item, index) in head" :key="index" :label="item.name" :property="item.id">
                        <el-table-column
                            v-for="(childItem, index) in item.list"
                            :key="index"
                            :label="childItem.name"
                            :property="childItem.id"
                            align="center"
                            width="100px"
                        >
                            <template slot-scope="scope">
                                {{ scope.row.houseFee[scope.column.property] }}
                                {{ scope.row.tempFee[scope.column.property] }}
                                {{ scope.row.depositFee[scope.column.property] }}
                                {{ scope.row.preFee[scope.column.property] }}
                            </template>
                        </el-table-column>
                    </el-table-column>
                </JTable>
            </div>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
import { mapGetters, mapState } from 'vuex';

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
            head: [
                
            ],
            houseFeeHead: { 
                name: '常规收费项', 
            },
            depositFeeHead: { 
                name: '押金类收费项', 
            },
            tempFeeHead: { 
                name: '临时收费项', 
            },
            preFeeHead: { 
                name: '预存款', 
                list: [
                    { name: '收款', id: 'add' },
                    { name: '支付', id: 'pay' },
                    { name: '找零结转', id: 'payAdd' },
                ] 
            },
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
        this.initData();
    },
    methods: {
        initData() {
            this.listLoading = true;
            if (this.query.beginDate == undefined) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30 * 6);
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
                url: `/statistics/DailyFeeReport`,
                method: 'get',
                data: this.query,
            }).then(res => {
                this.head = [];
                this.list = res.data.list;
                this.houseFeeHead.list = res.data.head.houseFeeHead;
                this.tempFeeHead.list = res.data.head.tempFeeHead;
                this.depositFeeHead.list = res.data.head.depositFeeHead;
                if(this.houseFeeHead.list.length > 0){
                    this.head.push(this.houseFeeHead);
                }
                if(this.tempFeeHead.list.length > 0){
                    this.head.push(this.tempFeeHead);
                }
                if(this.depositFeeHead.list.length > 0){
                    this.head.push(this.depositFeeHead);
                }
                this.head.push(this.preFeeHead);
                this.listLoading = false;
            });
        },
        getSummaries(param) {
            const { columns, data } = param;
            const sums = [];
            columns.forEach((column, index) => {
                if (index === 0) {
                    sums[index] = '总计';
                    return;
                } else if (index === 2) {
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
