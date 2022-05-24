<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="公告标题" prop="noticeTitle">
                            <el-input v-model="queryParams.noticeTitle" placeholder="请输入公告标题" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="公告类型" prop="noticeType">
                            <el-select v-model="queryParams.noticeType" placeholder="请选择公告类型" clearable size="small">
                                <el-option label="请选择字典生成" value="" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="公告状态" prop="status">
                            <el-select v-model="queryParams.status" placeholder="请选择公告状态" clearable size="small">
                                <el-option v-for="dict in dict.type.owner_notice_state" :key="dict.value" :label="dict.label" :value="dict.value" />
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
                        <el-col :span="1.5">
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['owner:ownerNotice:add']">
                                新增
                            </el-button>
                        </el-col>
                        <el-col :span="1.5">
                            <el-button
                                type="success"
                                plain
                                icon="el-icon-edit"
                                size="mini"
                                :disabled="single"
                                @click="handleUpdate"
                                v-hasPermi="['owner:ownerNotice:edit']"
                            >
                                修改
                            </el-button>
                        </el-col>
                        <el-col :span="1.5">
                            <el-button
                                type="danger"
                                plain
                                icon="el-icon-delete"
                                size="mini"
                                :disabled="multiple"
                                @click="handleDelete"
                                v-hasPermi="['owner:ownerNotice:remove']"
                            >
                                删除
                            </el-button>
                        </el-col>
                        <el-col :span="1.5">
                            <el-button
                                type="warning"
                                plain
                                icon="el-icon-download"
                                size="mini"
                                :loading="exportLoading"
                                @click="handleExport"
                                v-hasPermi="['owner:ownerNotice:export']"
                            >
                                导出
                            </el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="ownerNoticeList" @selection-change="handleSelectionChange">
                    <el-table-column label="公告标题" align="center" prop="noticeTitle" width="300px" />
                    <el-table-column label="公告图片" align="center" prop="noticeImg">
                        <template slot-scope="scope">
                            <img :src="baseUrl + scope.row.noticeImg" width="50px" />
                        </template>
                    </el-table-column>
                    <el-table-column label="公告类型" align="center" prop="noticeType">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.owner_notice_type" :value="scope.row.noticeType" />
                        </template>
                    </el-table-column>
                    <el-table-column label="公告状态" align="center" prop="status">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.owner_notice_state" :value="scope.row.status" />
                        </template>
                    </el-table-column>
                    <el-table-column label="发布时间" align="center" prop="publishTime"/>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['owner:ownerNotice:edit']">
                                修改
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['owner:ownerNotice:remove']">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改业主公告对话框 -->
        <el-dialog class="Jdialog Jdialog_center" :title="title" :visible.sync="open" width="500px" append-to-body @open="doFocus">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="公告标题" prop="noticeTitle">
                    <el-input v-model="form.noticeTitle" ref="mark" placeholder="请输入公告标题" maxlength="30" show-word-limit />
                </el-form-item>
                <el-form-item label="公告类型" prop="noticeType">
                    <el-select v-model="form.noticeType" placeholder="请选择公告类型">
                        <el-option v-for="dict in dict.type.owner_notice_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="公告图片" prop="noticeImg">
                    <imageUpload v-model="form.noticeImg" :limit="1" />
                    <span>为了最佳的显示效果，请上传正方形的图片</span>
                </el-form-item>
                <el-form-item label="公告描述" prop="noticeSubTitle">
                    <el-input
                        type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4 }"
                        v-model="form.noticeSubTitle"
                        placeholder="请输入公告描述"
                        maxlength="200"
                        show-word-limit
                    />
                </el-form-item>
                <el-form-item label="公告内容" prop="noticeContent">
                    <editor v-model="form.noticeContent" :min-height="192" />
                </el-form-item>
                <el-form-item label="公告状态">
                    <el-radio-group v-model="form.status">
                        <el-radio v-for="dict in dict.type.owner_notice_state" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
                    </el-radio-group>
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
import { listOwnerNotice, getOwnerNotice, delOwnerNotice, addOwnerNotice, updateOwnerNotice, exportOwnerNotice } from '@/api/owner/ownerNotice';

export default {
    dicts: ['owner_notice_state', 'owner_notice_type'],
    data() {
        return {
            baseUrl: process.env.VUE_APP_BASE_API,
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
            // 业主公告表格数据
            ownerNoticeList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                noticeTitle: null,
                noticeType: null,
                status: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                noticeTitle: [{ required: true, message: '公告标题不能为空', trigger: 'blur' }],
                noticeSubTitle: [{ required: true, message: '公告描述不能为空', trigger: 'blur' }],
                noticeType: [{ required: true, message: '公告类型不能为空', trigger: 'blur' }],
                noticeImg: [{ required: true, message: '公告图片不能为空', trigger: 'blur' }],
                noticeContent: [{ required: true, message: '公告内容不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询业主公告列表 */
        getList() {
            this.loading = true;
            listOwnerNotice(this.queryParams).then(response => {
                this.ownerNoticeList = response.data.list;
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
                noticeTitle: null,
                noticeType: null,
                noticeContent: null,
                status: '0',
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
                remark: null,
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
            this.title = '添加业主公告';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getOwnerNotice(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改业主公告';
            });
        },
        doFocus() {
            this.$nextTick(function () {
                this.$refs.mark.focus();
            });
            
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateOwnerNotice(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addOwnerNotice(this.form).then(response => {
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
                .confirm('是否确认删除业主公告编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delOwnerNotice(ids);
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
                .confirm('是否确认导出所有业主公告数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportOwnerNotice(queryParams);
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
