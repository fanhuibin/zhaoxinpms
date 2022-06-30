<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="发送方式：" prop="esType">
                            <el-input v-model="queryParams.esType" placeholder="请输入发送方式" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="接收人" prop="esReceiver">
                            <el-input v-model="queryParams.esReceiver" placeholder="请输入接收人" clearable size="small" @keyup.enter.native="handleQuery" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['payment:sms:add']">新增</el-button>
                        </el-col>
                        -->
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="smsList" @selection-change="handleSelectionChange">
                    <el-table-column label="ID" align="center" prop="id" />
                    <el-table-column label="消息标题" align="center" prop="esTitle" />
                    <el-table-column label="发送方式：1短信 2邮件 3微信" align="center" prop="esType" />
                    <el-table-column label="接收人" align="center" prop="esReceiver" />
                    <el-table-column label="发送所需参数Json格式" align="center" prop="esParam" />
                    <el-table-column label="推送内容" align="center" prop="esContent" />
                    <el-table-column label="推送时间" align="center" prop="esSendTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.esSendTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="推送结果" align="center" prop="esSendStatus" />
                    <el-table-column label="发送次数" align="center" prop="esSendNum" />
                    <el-table-column label="推送失败原因" align="center" prop="esResult" />
                    <el-table-column label="备注" align="center" prop="remark" />
                     <!--
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                           
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['payment:sms:edit']">
                                修改
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['payment:sms:remove']">
                                删除
                            </el-button>
                           
                        </template>
                    </el-table-column>
                     -->
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改消息推送记录对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="消息标题" prop="esTitle">
                    <el-input v-model="form.esTitle" placeholder="请输入消息标题" />
                </el-form-item>
                <el-form-item label="发送方式：1短信 2邮件 3微信" prop="esType">
                    <el-select v-model="form.esType" placeholder="请选择发送方式：1短信 2邮件 3微信">
                        <el-option label="请选择字典生成" value="" />
                    </el-select>
                </el-form-item>
                <el-form-item label="接收人" prop="esReceiver">
                    <el-input v-model="form.esReceiver" placeholder="请输入接收人" />
                </el-form-item>
                <el-form-item label="发送所需参数Json格式" prop="esParam">
                    <el-input v-model="form.esParam" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="推送内容">
                    <editor v-model="form.esContent" :min-height="192" />
                </el-form-item>
                <el-form-item label="推送时间" prop="esSendTime">
                    <el-date-picker
                        clearable
                        size="small"
                        v-model="form.esSendTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择推送时间"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送">
                    <el-radio-group v-model="form.esSendStatus">
                        <el-radio label="1">请选择字典生成</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="发送次数 超过5次不再发送" prop="esSendNum">
                    <el-input v-model="form.esSendNum" placeholder="请输入发送次数 超过5次不再发送" />
                </el-form-item>
                <el-form-item label="推送失败原因" prop="esResult">
                    <el-input v-model="form.esResult" placeholder="请输入推送失败原因" />
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listSms, getSms, delSms, addSms, updateSms, exportSms } from '@/api/payment/sms';

export default {
    name: 'Sms',
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
            // 消息推送记录表格数据
            smsList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                esTitle: null,
                esType: null,
                esReceiver: null,
                esParam: null,
                esContent: null,
                esSendTime: null,
                esSendStatus: null,
                esSendNum: null,
                esResult: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {},
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询消息推送记录列表 */
        getList() {
            this.loading = true;
            listSms(this.queryParams).then(response => {
                this.smsList = response.data.list;
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
                esTitle: null,
                esType: null,
                esReceiver: null,
                esParam: null,
                esContent: null,
                esSendTime: null,
                esSendStatus: '0',
                esSendNum: null,
                esResult: null,
                remark: null,
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
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
            this.title = '添加消息推送记录';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getSms(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改消息推送记录';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateSms(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addSms(this.form).then(response => {
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
                .confirm('是否确认删除消息推送记录编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delSms(ids);
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
                .confirm('是否确认导出所有消息推送记录数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportSms(queryParams);
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
