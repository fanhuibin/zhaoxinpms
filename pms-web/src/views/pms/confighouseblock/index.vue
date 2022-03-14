<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="商业区编号">
                            <el-input v-model="query.code" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="商业区名">
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
                    <el-table-column prop="code" label="商业区编号" align="left" />
                    <el-table-column prop="name" label="商业区名" align="left" />
                    <el-table-column prop="address" label="商业区地址" align="left" />
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
import EditForm from './Form';
import ExportBox from './ExportBox';

export default {
    components: { EditForm, ExportBox },
    data() {
        return {
            query: {
                code: undefined,
                name: undefined,
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
                url: `/baseconfig/ConfigHouseBlock`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;

                this.listLoading = false;
            });
        },
        handleDel(id) {
            this.$confirm(
                "此操作将永久删除该商业区与该商业区下的商铺，是否继续?<br/>",
                '提示',
                {
                    type: 'warning',
                    dangerouslyUseHTMLString: true,
                }
            )
                .then(() => {
                    request({
                        url: `/baseconfig/ConfigHouseBlock/${id}`,
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
                url: `/baseconfig/ConfigHouseBlock/Actions/Export`,
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
