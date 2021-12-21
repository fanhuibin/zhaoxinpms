<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="类型">
                            <el-select v-model="query.type" placeholder="请选择" clearable>
                                <el-option
                                    v-for="(item, index) in typeOptions"
                                    :key="index"
                                    :label="item.fullName"
                                    :value="item.id"
                                    :disabled="item.disabled"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="收费项目名">
                            <el-input v-model="query.name" placeholder="请输入" clearable></el-input>
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
                    </div>
                    <div class="Jcommon-head-right">
                        <el-tooltip effect="dark" content="刷新" placement="top">
                            <el-link icon="icon-ym icon-ym-Refresh Jcommon-head-icon" :underline="false" @click="reset()" />
                        </el-tooltip>

                        
                    </div>
                </div>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column label="类型" prop="type" algin="left" width="150px;">
                        <template slot-scope="scope">
                            {{ scope.row.type | dynamicText(typeOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="收费项目名" align="left" width="150px;" />
                    <el-table-column prop="price" label="单价" align="left" />
                    <el-table-column label="数量" prop="numType" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.numType | dynamicText(numTypeOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="计算公式" prop="formula" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.formula | dynamicText(formulaOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="计算周期" prop="period" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.period | dynamicText(periodOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="滞纳金" prop="lateFeeEnable" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.lateFeeEnable | dynamicText(lateFeeEnableOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="lateFeeRate" label="滞纳金比例" align="left" />
                    <el-table-column prop="lateFeeDays" label="滞纳金天数" align="left" />
                    <el-table-column label="操作" fixed="right" width="100">
                        <template slot-scope="scope">
                            <el-button type="text" @click="addOrUpdateHandle(scope.row.id)">编辑</el-button>
                            <el-button type="text" class="JTable-delBtn" @click="handleDel(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
        <EditForm v-if="formVisible" ref="EditForm" @refresh="refresh" />
        <ExportBox v-if="exportBoxVisible" ref="ExportBox" @download="download" />
    </div>
</template>

<script>
import request from '@/utils/request';

import JTable from '@/components/JTable';
import EditForm from './Form';
import ExportBox from './ExportBox';

export default {
    components: { EditForm, ExportBox, JTable },
    data() {
        return {
            showAll: false,
            query: {
                type: 'house',
                name: undefined,
                formula: undefined,
                late_fee_enable: undefined,
                late_fee_rate: undefined,
                late_fee_days: undefined,
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
            exportBoxVisible: false,
            treeData: [],
            treeActiveId: '',
            typeOptions: [
                { fullName: '常规收费项（商铺）', id: 'house' },
                { fullName: '临时收费项', id: 'temp' },
                { fullName: '押金类收费项', id: 'deposit' },
            ],
            formulaOptions: [
                { fullName: '单价*数量', id: 'base' },
                { fullName: '自定义', id: 'expression' },
            ],
            numTypeOptions: [
                { fullName: '按户数收费', id: 'flat' },
                { fullName: '按人口数收费', id: 'people' },
                { fullName: '按楼层收费', id: 'floor' },
                { fullName: '按占地面积收费', id: 'building_area' },
                { fullName: '按使用面积收费', id: 'use_area' },
                { fullName: '按走表数量', id: 'meter' },
                { fullName: '按租金收费', id: 'rent_fee' },
            ],
            lateFeeEnableOptions: [
                { fullName: '不适用', id: '0' },
                { fullName: '适用', id: '1' },
            ],
            periodOptions: [
                { fullName: '1个月', id: '1' },
                { fullName: '2个月', id: '2' },
                { fullName: '3个月', id: '3' },
                { fullName: '4个月', id: '4' },
                { fullName: '6个月', id: '6' },
                { fullName: '12个月', id: '12' },
            ],
        };
    },
    computed: {},
    created() {
        this.initData();
    },
    methods: {
        initData() {
            this.listLoading = true;
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/baseconfig/ConfigFeeItem`,
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
                        url: `/baseconfig/ConfigFeeItem/${id}`,
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
        addOrUpdateHandle(id, isDetail) {
            this.formVisible = true;
            this.$nextTick(() => {
                this.$refs.EditForm.init(id, isDetail);
            });
        },
        exportData() {
            this.exportBoxVisible = true;
            this.$nextTick(() => {
                this.$refs.ExportBox.init(this.columnList);
            });
        },
        download(data) {
            let query = { ...data, ...this.listQuery, ...this.query };
            request({
                url: `/baseconfig/ConfigFeeItem/Actions/Export`,
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
