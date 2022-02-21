<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center" v-show="!formVisible">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="流程名">
                            <el-input v-model="query.fullName" placeholder="请输入" clearable></el-input>
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
                <el-alert
                    title="”报修流程“与”投诉流程“表单字段权限实现逻辑不同，一个基于流程设计器，一个自定义实现"
                    type="success"
                    description="">
                </el-alert>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column label="流程图标" width="100">
                        <template slot-scope="scope">
                            <div :style="{backgroundColor: scope.row.iconBackground}" style="width:44px;height:44px;margin-left:30px;border-radius:5px;">
                                <i :class="scope.row.icon" style="font-size: 24px;color: white;padding-left:10px;padding-top:10px;" />
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="fullName" label="流程名" align="left" />
                    <el-table-column prop="enCode" label="流程key" align="left" />
                    <el-table-column label="操作" fixed="right" width="200">
                        <template slot-scope="scope">
                            <el-button type="text" @click="addOrUpdateHandle(scope.row.id)">编辑</el-button>
                            <el-button type="text" @click="deploy(scope.row.id)">部署</el-button>
                        </template>
                    </el-table-column>
                </JTable>
            </div>
        </div>
        <Designer ref="designer"  v-if="formVisible" @refresh="refresh"/>
    </div>
    
</template>

<script>
import request from '@/utils/request';
import JTable from '@/components/JTable';
import Designer from '../engine/index.vue';

export default {
    components: {JTable, Designer},
    data() {
        return {
            showAll: false,
            query: {
                fullName: undefined,
            },
            defaultProps: {
                children: 'children',
                label: 'fullName',
            },
            list: [],
            listLoading: true,
            listQuery: {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            },
            formVisible: false,
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
                url: `/workflow/designer`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.listLoading = false;
            });
        },
        addOrUpdateHandle(id, isDetail) {
            this.formVisible = true;
            this.$nextTick(() => {
                this.$refs.designer.init(id);
            });
        },
        deploy(id) {
            request({
                url: '/workflow/designer/deploy/' + id,
                method: 'get',
            }).then(res => {
                this.$message({
                    message: res.msg,
                    type: 'success',
                    duration: 1000,
                    onClose: () => {
                        (this.visible = false), this.$emit('refresh', true);
                    },
                });
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
