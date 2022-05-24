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
                        <el-form-item label="请选择收费项">
                            <el-select v-model="query.feeItemId" placeholder="请选择">
                                <el-option v-for="item in feeItemList" :key="item.id" :label="item.name" :value="item.id"></el-option>
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
                        <el-button type="primary" icon="el-icon-upload2" @click="importHandle()">导入</el-button>
                    </div>
                </div>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column prop="resourceName" label="编号" align="left" />
                    <el-table-column prop="feeItemName" label="费用类型" align="left" />
                    <el-table-column prop="lastIndex" label="上期读数" align="left" />
                    <el-table-column prop="lastIndexDate" label="上期读表时间" align="left" />
                    <el-table-column prop="currentIndex" label="本期读数" align="left" />
                    <el-table-column prop="currentIndexDate" label="本期读表时间" align="left" />
                    <el-table-column prop="multiple" label="倍率" align="left" />
                    <el-table-column prop="loss" label="损耗" align="left" />
                    <el-table-column prop="result" label="最终数量" align="left" />
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
        <ImportForm v-if="importFormVisible" ref="ImportForm" @refresh="refresh" />
    </div>
</template>

<script>
import request from '@/utils/request';
import EditForm from './Form';
import ImportForm from './ImportForm';
import HouseInput from '@/components/HouseInput';

export default {
    components: { EditForm, ImportForm, HouseInput },
    data() {
        return {
            query: {
                feeItemId: undefined,
                resourceName: undefined,
            },
            defaultProps: {
                children: 'children',
                label: 'fullName',
            },
            list: [],
            feeItemList: [],
            listLoading: true,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            },
            formVisible: false,
            importFormVisible: false,
            generateFormVisible: false,
        };
    },
    computed: {},
    created() {
        this.initData();
        this.getFeeItemList();
    },
    methods: {
        getFeeItemList() {
            request({
                url: `/baseconfig/ConfigFeeItem/select`,
                method: 'get',
                data: { numType: 'meter', type: 'house' },
            }).then(res => {
                this.feeItemList = res.data;
            });
        },
        initData() {
            this.listLoading = true;
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/payment/PaymentMeter`,
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
                        url: `/payment/PaymentMeter/${id}`,
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
        importHandle(id, isDetail) {
            this.importFormVisible = true;
            this.$nextTick(() => {
                this.$refs.ImportForm.init(this.feeItemList);
            });
        },
        addOrUpdateHandle(id, isDetail) {
            this.formVisible = true;
            this.$nextTick(() => {
                this.$refs.EditForm.init(id, isDetail);
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
