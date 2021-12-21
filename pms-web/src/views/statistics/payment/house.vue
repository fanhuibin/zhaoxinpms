<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
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
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main" style="overflow-y: scroll" v-if="loaded">
                <div class="Jcommon-head">
                    <el-form @submit.native.prevent size="mini" :style="'width:100%'">
                        <el-row :gutter="16" :style="'width:100%'">
                            <el-col :span="4">
                                <el-form-item label="实收金额" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">
                                        {{ data.paySum.receivable }}
                                    </label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="滞纳金" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">
                                        {{ data.paySum.lateFeeMoney }}
                                    </label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="优惠金额" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ data.paySum.discount }}</label>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="16" :style="'width:100%'">
                            <el-col :span="4">
                                <el-form-item label="收款金额" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ data.paySum.payMoney }}</label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="预存款支付" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">
                                        {{ data.paySum.prePayMoney }}
                                    </label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="找零结存" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">
                                        {{ data.paySum.preSaveMoney }}
                                    </label>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <div>
                    <div style="padding-left: 10px">
                        <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 100%">
                            <el-tab-pane label="常规收费合计" name="first"></el-tab-pane>
                        </el-tabs>
                    </div>
                    <div>
                        <el-table :data="data.billSum" max-height="500px" style="width: 600px">
                            <el-table-column prop="resourceName" label="商铺编号" align="left" width="150px" />
                            <el-table-column prop="lateFee" label="滞纳金" align="left" width="150px" />
                            <el-table-column prop="discount" label="优惠金额" align="left" width="150px" />
                            <el-table-column prop="total" label="实收金额" align="left" width="150px" />
                        </el-table>
                    </div>
                </div>
                <div>
                    <div style="padding-left: 10px">
                        <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 100%">
                            <el-tab-pane label="押金收费合计" name="first"></el-tab-pane>
                        </el-tabs>
                    </div>
                    <div>
                        <el-table :data="data.depositSum">
                            <el-table-column prop="resourceName" label="商铺编号" align="left" width="150px" />
                            <el-table-column prop="total" label="实收金额" align="left" width="150px" />
                        </el-table>
                    </div>
                </div>
                <div>
                    <div style="padding-left: 10px">
                        <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 100%">
                            <el-tab-pane label="临时收费合计" name="first"></el-tab-pane>
                        </el-tabs>
                    </div>
                    <div>
                        <el-table :data="data.tempSum">
                            <el-table-column prop="resourceName" label="商铺编号" align="left" width="150px" />
                            <el-table-column prop="total" label="实收金额" align="left" width="150px" />
                        </el-table>
                    </div>
                </div>

                <h1 style="margin-top: 50px; padding-left: 10px">退费合计</h1>
                <div class="Jcommon-head">
                    <el-form @submit.native.prevent size="mini" :style="'width:100%'">
                        <el-row :gutter="16" :style="'width:100%'">
                            <el-col :span="4">
                                <el-form-item label="退还金额" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">
                                        {{ data.refundSum.receivable }}
                                    </label>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <div>
                    <div style="padding-left: 10px">
                        <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 100%">
                            <el-tab-pane label="押金退还合计" name="first"></el-tab-pane>
                        </el-tabs>
                    </div>
                    <div>
                        <el-table :data="data.depositRefundSum">
                            <el-table-column prop="resourceName" label="商铺编号" align="left" width="150px" />
                            <el-table-column prop="total" label="退还金额" align="left" width="150px" />
                        </el-table>
                    </div>
                </div>
                <div>
                    <div style="padding-left: 10px">
                        <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 100%">
                            <el-tab-pane label="临时收费退还合计" name="first"></el-tab-pane>
                        </el-tabs>
                    </div>
                    <div>
                        <el-table :data="data.tempRefundSum">
                            <el-table-column prop="resourceName" label="商铺编号" align="left" width="150px" />
                            <el-table-column prop="total" label="退还金额" align="left" width="150px" />
                        </el-table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';

export default {
    components: {},
    data() {
        return {
            showAll: false,
            activeName: 'first',
            loaded: false,
            data: {},
            query: {
                beginDate: undefined,
                endDate: undefined,
            },
            list: [],
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
                url: `/statistics/PaymentStatistics/groupByHouse`,
                method: 'get',
                data: this.query,
            }).then(res => {
                this.data = res.data;
                this.loaded = true;
            });
        },
        search() {
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
        handleClick(tab, event) {},
    },
};
</script>
