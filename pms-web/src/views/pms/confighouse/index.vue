<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="编号">
                            <HouseInput  v-model="query.name" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="使用状态">
                            <el-select v-model="query.state" placeholder="请选择" clearable>
                                <el-option
                                    v-for="(item, index) in stateOptions"
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
                        <el-button type="primary" icon="el-icon-plus" @click="addOrUpdateHandle()">新增</el-button>
                        <el-button type="primary" icon="el-icon-upload2" @click="uploadForm()">导入</el-button>
                    </div>
                    <div class="Jcommon-head-right">
                        <el-tooltip effect="dark" content="刷新" placement="top">
                            <el-link icon="icon-ym icon-ym-Refresh Jcommon-head-icon" :underline="false" @click="reset()" />
                        </el-tooltip>
                        
                    </div>
                </div>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column prop="name" label="完整编号" align="left" />
                    <el-table-column prop="block" label="商业区" align="left">
                        <template slot-scope="scope">{{ scope.row.blockName }}</template>
                    </el-table-column>
                    <el-table-column prop="building" label="楼栋" align="left">
                        <template slot-scope="scope">{{ scope.row.buildingName }}</template>
                    </el-table-column>
                    <el-table-column prop="floor" label="楼层" align="left" />
                    <el-table-column prop="code" label="房号" align="left"/>
                    <el-table-column prop="buildingsquare" label="占地面积" align="left" />
                    <el-table-column prop="usesquare" label="使用面积" align="left" />
                    <el-table-column prop="rentFee" label="租金" align="left" />
                    <el-table-column label="使用状态" prop="state" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.state | dynamicText(stateOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" fixed="right" width="200">
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
        <ImportForm v-if="importFormVisible" ref="importForm" @refresh="refresh" />
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
                name: undefined,
                area: undefined,
                code: undefined,
                state: undefined,
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
            importFormVisible: false,
            exportBoxVisible: false,
            ContractVisible: false,
            cancelContractFormVisible: false,
            areaOptions: [],
            stateOptions: [{ fullName: '空置', id: 'empty' }],
        };
    },
    computed: {},
    created() {
        this.initData();
        this.getareaOptions();
        this.getstateOptions();
    },
    methods: {
        getareaOptions() {
            request({
                url: `/baseconfig/ConfigHouseBlock/selectList`,
                method: 'GET',
            }).then(res => {
                this.areaOptions = res.data;
            });
        },
        getstateOptions() {
            request({
                url: `/baseconfig/House/stateOptions`,
                method: 'GET',
            }).then(res => {
                this.stateOptions = res.data;
            });
        },

        initData() {
            this.listLoading = true;
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/baseconfig/House`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;

                this.listLoading = false;
            });
        },
        uploadForm() {
            this.importFormVisible = true;
            this.$nextTick(() => {
                this.$refs.importForm.init();
            });
        },
        handleDel(id) {
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                type: 'warning',
            })
                .then(() => {
                    request({
                        url: `/baseconfig/House/${id}`,
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
