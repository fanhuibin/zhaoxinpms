<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="商铺编号">
                            <el-input v-model="query.resourceName" placeholder="请输入" clearable></el-input>
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
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
                            <el-button icon="el-icon-refresh-right" @click="reset()">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
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
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
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
            query: {
                resource_name: undefined,
                charging_item_name: undefined,
                payState: undefined,
            },
            feeItemList: [],
            list: [],
            listLoading: true,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            },
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
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/statistics/paymentBill/overdue`,
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
