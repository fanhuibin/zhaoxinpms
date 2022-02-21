<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="任务ID" prop="taskId">
                            <el-input v-model="queryParams.taskId" placeholder="请输入任务ID" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="任务名称" prop="taskName">
                            <el-input v-model="queryParams.taskName" placeholder="任务名称" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="流程编号" prop="businessNo">
                            <el-input v-model="queryParams.businessNo" placeholder="请输入流程编号" clearable size="small" @keyup.enter.native="handleQuery" />
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
                    <el-row :gutter="10" class="mb8"></el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
                    <el-table-column label="流程编号" align="left" prop="businessNo" />
                    <el-table-column label="标题" align="left" prop="instanceTitle" />
                    <el-table-column label="任务ID" align="center" prop="taskId" width="150" />
                    <el-table-column label="任务名称" align="center" prop="taskName" width="150" />
                    <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
                        <template slot-scope="scope">
                            <el-button
                                size="mini"
                                type="text"
                                @click="toAudit(scope.row)"
                                :disabled="scope.row.taskId == '-1' || scope.row.taskId == '' || scope.row.taskId == '-2'"
                            >
                                处理
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>
        <!-- 处理框-->
        <AuditForm v-if="formVisible" @close="colseForm" ref="auditForm" />
    </div>
</template>

<script>
import request from '@/utils/request';
import AuditForm from '../../form/Audit';
export default {
    components: { AuditForm },
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
            // 待办表格数据
            taskList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                taskId: null,
                taskName: null,
                businessNo: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {},
            formVisible: false,
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询待办列表 */
        getList() {
            this.loading = true;

            return request({
                url: '/activiti/process/taskList',
                method: 'get',
                params: this.queryParams,
            })
                .then(response => {
                    this.taskList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                })
                .then(() => {});
        },
        toAudit(item) {
            let data = {
                processDefinitionKey: item.processDefinitionKey,
                instanceId: item.instanceId,
                taskId: item.taskId,
                taskDefKey: item.taskDefKey,
                isSelf: false,
                isAudit: false, //是否可以审核
                hasCancel: false, //可不可以撤销
            };
            if (item.taskId > 0) {
                data.isAudit = true;
                data.hasCancel = true;
            }
            this.$nextTick(() => {
                this.formVisible = true;
                this.$nextTick(() => {
                    this.$refs.auditForm.init(data);
                });
            });
        },
        colseForm(isRefresh) {
            this.formVisible = false;
            if (isRefresh) this.getList();
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
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
            this.title = '添加待办';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getDemo(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改待办';
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
            this.$confirm('是否确认删除待办编号为"' + ids + '"的数据项?', '警告', {
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
            this.$confirm('是否确认导出所有待办数据项?', '警告', {
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
