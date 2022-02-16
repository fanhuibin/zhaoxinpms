<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="关键字" prop="procInstName">
                            <el-input v-model="queryParams.procInstName" placeholder="请输入关键字" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                   
                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                     <div>
                        <el-button type="primary" icon="el-icon-plus" @click="create()">发起流程</el-button>
                    </div>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>
                <JTable v-loading="loading" :data="proInstList" @selection-change="handleSelectionChange">
                    <el-table-column label="标题" align="left" prop="name" />
                    <el-table-column label="流程类型" prop="processDefinitionName" width="150"/>
                    <el-table-column label="发起时间" align="center" prop="startTime" width="150">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="结束时间" align="center" prop="endTime" width="150" >
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" align="center" prop="state" width="100">
                        <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.endTime">已结束</el-tag>
                            <el-tag v-else>进行中</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" @click="toDetail(scope.row)">查看</el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>
        <AuditForm v-if="formVisible" ref="auditForm"  @close="colseForm"/>
        <CreateForm v-if="showCreate" ref="createForm"/>
    </div>
</template>

<script>
import request from '@/utils/request';
import AuditForm from '../form/Audit';
import CreateForm from './create';
export default {
    components: { AuditForm, CreateForm },
    data() {
        return {
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 我发起的任务列表
            proInstList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                procInstName: null,
                startTimeRange: [],
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {},
            formVisible: false,
            showCreate: false,
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询已办列表 */
        getList() {
            this.loading = true;
            return request({
                url: '/activiti/process/taskApplyList',
                method: 'get',
                params: this.queryParams,
            })
                .then(response => {
                    this.proInstList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                })
                .then(() => {});
        },
        create() {
            this.showCreate = true;
            this.$nextTick(() => {
                this.$refs.createForm.init();
            });
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        colseForm(isRefresh) {
            this.formVisible = false;
            if (isRefresh) this.getList();
        },
        toDetail(item) {
            let data = {
                processDefinitionKey: item.processDefinitionKey,
                instanceId: item.processInstanceId,
                taskId: '',
                taskDefKey: '',
                isSelf: false,
                isAudit: false, //是否可以审核
                hasCancel: false, //可不可以撤销
            };
            this.$nextTick(() => {
                this.formVisible = true;
                this.$nextTick(() => {
                    this.$refs.auditForm.init(data);
                });
            });
        },
        // 表单重置
        reset() {
            this.form = {
                id: null,
                taskId: null,
                taskName: null,
            };
            this.resetForm('form');
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.resetForm('queryForm');
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.id);
            this.single = selection.length !== 1;
            this.multiple = !selection.length;
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = '添加已办';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getDemo(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改已办';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateDemo(this.form).then(response => {
                            this.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addDemo(this.form).then(response => {
                            this.msgSuccess('新增成功');
                            this.open = false;
                            this.getList();
                        });
                    }
                }
            });
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            const ids = row.id || this.ids;
            this.$confirm('是否确认删除已办编号为"' + ids + '"的数据项?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            })
                .then(function () {
                    return delDemo(ids);
                })
                .then(() => {
                    this.getList();
                    this.msgSuccess('删除成功');
                });
        },
        /** 导出按钮操作 */
        handleExport() {
            const queryParams = this.queryParams;
            this.$confirm('是否确认导出所有已办数据项?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            })
                .then(function () {
                    return exportDemo(queryParams);
                })
                .then(response => {
                    this.download(response.msg);
                });
        },
    },
};
</script>
