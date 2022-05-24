<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="编码" prop="code">
                            <el-input v-model="queryParams.code" placeholder="请输入编码" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="名称" prop="name">
                            <el-input v-model="queryParams.name" placeholder="请输入名称" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="数据端" prop="client">
                            <el-input v-model="queryParams.client" placeholder="请输入数据端" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="排序" prop="sortField">
                            <el-input v-model="queryParams.sortField" placeholder="请输入排序" clearable size="small" @keyup.enter.native="handleQuery" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['payment:paymentMethod:add']">
                                新增
                            </el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="paymentMethodList" @selection-change="handleSelectionChange">
                    <el-table-column label="id" align="center" prop="id" />
                    <el-table-column label="编码" align="center" prop="code" />
                    <el-table-column label="名称" align="center" prop="name" />
                    <el-table-column label="数据端" align="center" prop="client" />
                    <el-table-column label="排序" align="center" prop="sortField" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <!--
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['payment:paymentMethod:edit']">
                                修改
                            </el-button>
                            <el-button
                                size="mini"
                                type="text"
                                icon="el-icon-delete"
                                @click="handleDelete(scope.row)"
                                v-hasPermi="['payment:paymentMethod:remove']"
                            >
                                删除
                            </el-button>
                            -->
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改支付方式对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="编码" prop="code">
                    <el-input v-model="form.code" placeholder="请输入编码" />
                </el-form-item>
                <el-form-item label="名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入名称" />
                </el-form-item>
                <el-form-item label="数据端" prop="client">
                    <el-input v-model="form.client" placeholder="请输入数据端" />
                </el-form-item>
                <el-form-item label="排序" prop="sortField">
                    <el-input v-model="form.sortField" placeholder="请输入排序" />
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
import { listPaymentMethod, getPaymentMethod, delPaymentMethod, addPaymentMethod, updatePaymentMethod, exportPaymentMethod } from '@/api/payment/paymentMethod';

export default {
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
            // 支付方式表格数据
            paymentMethodList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                code: null,
                name: null,
                client: null,
                sortField: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                code: [{ required: true, message: '编码不能为空', trigger: 'blur' }],
                name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
                client: [{ required: true, message: '数据端不能为空', trigger: 'blur' }],
                sortField: [{ required: true, message: '排序不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询支付方式列表 */
        getList() {
            this.loading = true;
            listPaymentMethod(this.queryParams).then(response => {
                this.paymentMethodList = response.data.list;
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
                code: null,
                name: null,
                client: null,
                sortField: null,
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
            this.title = '添加支付方式';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getPaymentMethod(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改支付方式';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updatePaymentMethod(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addPaymentMethod(this.form).then(response => {
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
                .confirm('是否确认删除支付方式编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delPaymentMethod(ids);
                })
                .then(() => {
                    this.getList();
                    this.$modal.msgSuccess('删除成功');
                })
                .catch(() => {});
        },
    },
};
</script>
