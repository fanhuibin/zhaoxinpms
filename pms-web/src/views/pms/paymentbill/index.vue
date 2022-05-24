<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="商铺编号">
                            <HouseInput  v-model="query.resourceName"/>
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
                        <el-form-item label="缴费单号">
                            <el-input v-model="query.payLogNo" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="付款状态">
                            <el-select v-model="query.payState" placeholder="请选择">
                                <el-option v-for="item in payStateOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="截止时间">
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
                        <el-form-item label="缴费时间">
                            <el-date-picker
                                v-model="pickerPayVal"
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
                <div class="Jcommon-head">
                    <div>
                        <el-button type="primary" icon="el-icon-plus" @click="addOrUpdateHandle()">新增</el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="batchUpdateHandle()">批量新增</el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="batchGenerateHandle()">生成数据（非抄表）</el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="meterGenerateHandle()">生成抄表数据</el-button>
                    </div>
                </div>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column prop="resourceName" label="编号" align="left" width="100" />
                    <el-table-column prop="feeItemName" label="收费项名" align="left" width="150" />
                    <el-table-column prop="feeUser" label="客户姓名" align="left" width="100" />
                    <el-table-column prop="beginDate" label="费用开始时间" align="left" width="150" />
                    <el-table-column prop="endDate" label="费用结束时间" align="left" width="150" />
                    <el-table-column prop="lastIndex" label="上次读数" align="left" />
                    <el-table-column prop="currentIndex" label="本次读数" align="left" />
                    <el-table-column prop="multiple" label="倍率" align="left" />
                    <el-table-column prop="loss" label="损耗" align="left" />
                    <el-table-column prop="num" label="数量" align="left" />
                    <el-table-column prop="price" label="单价" align="left" />
                    <el-table-column prop="total" label="金额" align="left" />
                    <el-table-column prop="receivable" label="实收金额" align="left" />
                    <el-table-column label="付款状态" align="left">
                        <template slot-scope="scope">
                            <el-tag size="small" v-if="scope.row.payLogId !== ''">已付款</el-tag>
                            <el-tag size="small" type="danger" v-if="scope.row.payLogId === ''">未付款</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="退款状态" align="left">
                        <template slot-scope="scope">
                            <el-tag size="small" v-if="scope.row.refundState === 0">未退款</el-tag>
                            <el-tag size="small" type="danger" v-if="scope.row.refundState === 1">部分退款</el-tag>
                            <el-tag size="small" type="danger" v-if="scope.row.refundState === 2">全部退款</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="refundAmount" label="退款总金额" align="left" />
                    <el-table-column prop="payLogNo" label="缴费单号" align="left" width="150" />
                    <el-table-column prop="payTime" label="缴费时间" align="left" width="150" />
                    <el-table-column label="操作" fixed="right" width="150">
                        <template slot-scope="scope">
                            <el-button type="text" @click="addOrUpdateHandle(scope.row.id)" :disabled="scope.row.payLogId !== ''">编辑</el-button>
                            <el-button type="text" class="JTable-delBtn" @click="handleDel(scope.row.id)" :disabled="scope.row.payLogId !== ''">删除</el-button>
                            <el-button type="text" @click="refundHandle(scope.row.id)" :disabled="scope.row.refundState ==2 || scope.row.payLogId === ''">退款</el-button>
                        </template>
                    </el-table-column>
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
        <BatchAddForm v-if="batchAddFormVisible" ref="BatchAddForm" @refresh="refresh" />
        <BatchGenerateForm v-if="batchGenerateFormVisible" ref="BatchGenerateForm" @refresh="refresh" />
        <EditForm v-if="formVisible" ref="EditForm" @refresh="refresh" />
        <RefundForm v-if="refundFormVisible" ref="RefundForm" @refresh="refresh" />
        <MeterGenerateForm v-if="meterGenerateFormVisible" ref="MeterGenerateForm" @refresh="refresh" />
    </div>
</template>

<script>
import request from '@/utils/request';
import EditForm from './Form';
import RefundForm from './RefundForm';
import BatchAddForm from './BatchAddForm';
import BatchGenerateForm from './BatchGenerateForm';
import MeterGenerateForm from '../paymentbill/MeterGenerateForm.vue';
import ExportBox from './ExportBox';
import ImportForm from './ImportForm';
import HouseInput from '@/components/HouseInput';

export default {
    components: { EditForm, RefundForm, ExportBox, ImportForm, BatchAddForm, BatchGenerateForm, MeterGenerateForm, HouseInput },
    data() {
        return {
            query: {
                resource_name: undefined,
                charging_item_name: undefined,
                payState: undefined,
            },
            pickerVal: [],
            pickerPayVal: [],
            feeItemList: [],
            payStateOptions: [
                { label: '已付款', value: '1' },
                { label: '未付款', value: '0' },
            ],
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
            batchAddFormVisible: false,
            batchGenerateFormVisible: false,
            meterGenerateFormVisible: false,
            importFormVisible: false,
            exportBoxVisible: false,
            refundFormVisible: false,
            columnList: [
                { prop: 'resourceName', label: '资源名' },
                { prop: 'chargingItemName', label: '收费项名' },
                { prop: 'beginDate', label: '账单对应的周期' },
                { prop: 'endDate', label: '账单对应的周期' },
                { prop: 'period', label: '付费周期（月）' },
                { prop: 'receivable', label: '应收' },
                { prop: 'receipts', label: '实收' },
            ],
        };
    },
    computed: {},
    created() {
        this.initData();
        this.getFeeItemList();
    },
    methods: {
        initData() {
            this.listLoading = true;
            if (this.pickerVal && this.pickerVal.length) {
                this.query.beginTime = this.pickerVal[0];
                this.query.endTime = this.pickerVal[1];
            } else {
                this.query.beginTime = '';
                this.query.endTime = '';
            }
            if (this.pickerPayVal && this.pickerPayVal.length) {
                this.query.payBeginTime = this.pickerPayVal[0];
                this.query.payEndTime = this.pickerPayVal[1];
            } else {
                this.query.payBeginTime = '';
                this.query.payEndTime = '';
            }
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/payment/PaymentBill`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;
                this.listLoading = false;
            });
        },
        meterGenerateHandle(id, isDetail) {
            this.meterGenerateFormVisible = true;
            this.$nextTick(() => {
                this.$refs.MeterGenerateForm.init(id, isDetail);
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
        handleDel(id) {
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                type: 'warning',
            })
                .then(() => {
                    request({
                        url: `/payment/PaymentBill/${id}`,
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
        refundHandle(id) {
            this.refundFormVisible = true;
            this.$nextTick(() => {
                this.$refs.RefundForm.init(id, this.feeItemList);
            });
        },
        addOrUpdateHandle(id, isDetail) {
            this.formVisible = true;
            this.$nextTick(() => {
                this.$refs.EditForm.init(id, isDetail, this.feeItemList);
            });
        },
        batchUpdateHandle(id, isDetail) {
            this.batchAddFormVisible = true;
            this.$nextTick(() => {
                this.$refs.BatchAddForm.init(id, isDetail, this.feeItemList);
            });
        },
        batchGenerateHandle(id, isDetail) {
            this.batchGenerateFormVisible = true;
            this.$nextTick(() => {
                this.$refs.BatchGenerateForm.init(id, isDetail);
            });
        },
        exportData() {
            this.exportBoxVisible = true;
            this.$nextTick(() => {
                this.$refs.ExportBox.init(this.columnList);
            });
        },
        uploadForm() {
            this.importFormVisible = true;
            this.$nextTick(() => {
                this.$refs.importForm.init();
            });
        },
        download(data) {
            let query = { ...data, ...this.listQuery, ...this.query };
            request({
                url: `/payment/PaymentBill/Actions/Export`,
                method: 'GET',
                data: query,
            }).then(res => {
                if (!res.data.url) return;
                window.location.href = process.env.VUE_APP_BASE_API + res.data.url;
                this.$refs.ExportBox.visible = false;
                this.exportBoxVisible = false;
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
            this.pickerVal = [];
            this.pickerPayVal = [];
            this.query = {
                resource_name: undefined,
                charging_item_name: undefined,
                payState: undefined,
            };
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
