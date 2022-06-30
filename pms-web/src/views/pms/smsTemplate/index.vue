<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="模板标题" prop="templateName">
                            <el-input
                                v-model="queryParams.templateName"
                                placeholder="请输入模板标题"
                                clearable
                                size="small"
                                @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="模板CODE" prop="templateCode">
                            <el-input
                                v-model="queryParams.templateCode"
                                placeholder="请输入模板CODE"
                                clearable
                                size="small"
                                @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="模板类型：1短信 2邮件 3微信" prop="templateType">
                            <el-select v-model="queryParams.templateType" placeholder="请选择模板类型：1短信 2邮件 3微信" clearable size="small">
                                <el-option label="请选择字典生成" value="" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <el-row :gutter="10" class="mb8">
                        <!--
                        <el-col :span="1.5">
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['payment:smsTemplate:add']">
                                新增
                            </el-button>
                        </el-col>
                        -->
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="smsTemplateList" @selection-change="handleSelectionChange">
                    <el-table-column label="主键" align="center" prop="id" />
                    <el-table-column label="模板标题" align="center" prop="templateName" />
                    <el-table-column label="模板CODE" align="center" prop="templateCode" />
                    <el-table-column label="模板类型：1短信 2邮件 3微信" align="center" prop="templateType" />
                    <el-table-column label="模板内容" align="center" prop="templateContent" width="500"/>
                     <!--
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                           
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['payment:smsTemplate:edit']">
                                修改
                            </el-button>
                            <el-button
                                size="mini"
                                type="text"
                                icon="el-icon-delete"
                                @click="handleDelete(scope.row)"
                                v-hasPermi="['payment:smsTemplate:remove']"
                            >
                                删除
                            </el-button>
                           
                        </template>
                    </el-table-column>
                     -->
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改短信模板对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="模板标题" prop="templateName">
                    <el-input v-model="form.templateName" placeholder="请输入模板标题" />
                </el-form-item>
                <el-form-item label="模板CODE" prop="templateCode">
                    <el-input v-model="form.templateCode" placeholder="请输入模板CODE" />
                </el-form-item>
                <el-form-item label="模板类型：1短信 2邮件 3微信" prop="templateType">
                    <el-select v-model="form.templateType" placeholder="请选择模板类型：1短信 2邮件 3微信">
                        <el-option label="请选择字典生成" value="" />
                    </el-select>
                </el-form-item>
                <el-form-item label="模板内容">
                    <editor v-model="form.templateContent" :min-height="192" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { listSmsTemplate, getSmsTemplate, delSmsTemplate, addSmsTemplate, updateSmsTemplate, exportSmsTemplate } from '@/api/payment/smsTemplate';

export default {
    name: 'SmsTemplate',
    data() {
        return {
            // 遮罩层
            loading: true,
            // 导出遮罩层
            exportLoading: false,
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
            // 短信模板表格数据
            smsTemplateList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                templateName: null,
                templateCode: null,
                templateType: null,
                templateContent: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                templateCode: [{ required: true, message: '模板CODE不能为空', trigger: 'blur' }],
                templateType: [{ required: true, message: '模板类型：1短信 2邮件 3微信不能为空', trigger: 'change' }],
                templateContent: [{ required: true, message: '模板内容不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询短信模板列表 */
        getList() {
            this.loading = true;
            listSmsTemplate(this.queryParams).then(response => {
                this.smsTemplateList = response.data.list;
                this.total = response.data.pagination.total;
                this.loading = false;
            });
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
                templateName: null,
                templateCode: null,
                templateType: null,
                templateContent: null,
                createTime: null,
                createBy: null,
                updateTime: null,
                updateBy: null,
            };
            this.resetForm('form');
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.currentPage = 1;
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
            this.title = '添加短信模板';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getSmsTemplate(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改短信模板';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateSmsTemplate(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addSmsTemplate(this.form).then(response => {
                            this.$modal.msgSuccess('新增成功');
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
            this.$modal
                .confirm('是否确认删除短信模板编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delSmsTemplate(ids);
                })
                .then(() => {
                    this.getList();
                    this.$modal.msgSuccess('删除成功');
                })
                .catch(() => {});
        },
        /** 导出按钮操作 */
        handleExport() {
            const queryParams = this.queryParams;
            this.$modal
                .confirm('是否确认导出所有短信模板数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportSmsTemplate(queryParams);
                })
                .then(response => {
                    this.$download.name(response.msg);
                    this.exportLoading = false;
                })
                .catch(() => {});
        },
    },
};
</script>
