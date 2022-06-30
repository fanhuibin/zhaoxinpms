<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="规则名" prop="name">
                            <el-input v-model="queryParams.name" placeholder="请输入规则名" clearable size="small" @keyup.enter.native="handleQuery" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['park:rules:add']">新增</el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="rulesList" @selection-change="handleSelectionChange">
                    <el-table-column label="编号" align="center" prop="id" />
                    <el-table-column label="规则名" align="center" prop="name" />
                    <el-table-column label="免费分钟" align="center" prop="freeMin" />
                    <el-table-column label="首段收费时间" align="center" prop="firstMin" />
                    <el-table-column label="首段收费" align="center" prop="firstMoney" />
                    <el-table-column label="超过首段间隔时间" align="center" prop="afterStepMin" />
                    <el-table-column label="超过首段收费金额" align="center" prop="afterStepMoney" />
                    <el-table-column label="尾段开始时间" align="center" prop="lastMin" />
                    <el-table-column label="尾段间隔时间" align="center" prop="lastStepMin" />
                    <el-table-column label="尾段间隔收费" align="center" prop="lastStepMoney" />
                    <el-table-column label="24小时收费上限" align="center" prop="maxMoney" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['park:rules:edit']">
                                修改
                            </el-button>
                            <!--
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['park:rules:remove']">
                                删除
                            </el-button>-->
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改停车场计费规则对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="180px">
                <el-form-item label="规则名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入规则名" />
                </el-form-item>
                <el-form-item label="免费分钟" prop="freeMin">
                    <el-input v-model="form.freeMin" placeholder="请输入免费分钟">
                        <template slot="append">分钟</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="首段收费时间" prop="firstMin">
                    <el-input v-model="form.firstMin" placeholder="请输入首段收费时间">
                        <template slot="append">分钟</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="首段收费" prop="firstMoney">
                    <el-input v-model="form.firstMoney" placeholder="请输入首段收费">
                        <template slot="append">元</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="超过首段间隔时间" prop="afterStepMin">
                    <el-input v-model="form.afterStepMin" placeholder="请输入超过首段间隔时间">
                        <template slot="append">分钟</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="超过首段收费金额" prop="afterStepMoney">
                    <el-input v-model="form.afterStepMoney" placeholder="请输入超过首段收费金额">
                        <template slot="append">元</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="尾段开始时间" prop="lastMin">
                    <el-input v-model="form.lastMin" placeholder="请输入尾段开始时间">
                        <template slot="append">分钟</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="尾段间隔时间" prop="lastStepMin">
                    <el-input v-model="form.lastStepMin" placeholder="请输入尾段间隔时间">
                        <template slot="append">分钟</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="尾段间隔收费" prop="lastStepMoney">
                    <el-input v-model="form.lastStepMoney" placeholder="请输入尾段间隔收费">
                        <template slot="append">元</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="24小时收费上限" prop="maxMoney">
                    <el-input v-model="form.maxMoney" placeholder="请输入24小时收费上限">
                        <template slot="append">元</template>
                    </el-input>
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
import { listRules, getRules, delRules, addRules, updateRules, exportRules } from '@/api/park/rules';
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
            // 停车场计费规则表格数据
            rulesList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                name: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                name: [
                    { required: true, message: '规则名不能为空', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' },
                ],
                freeMin: [
                    { required: true, message: '免费时间不能为空', trigger: 'blur' },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                firstMin: [
                    { required: true, message: '首段收费时间不能为空', trigger: 'blur' },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                firstMoney: [
                    { required: true, message: '首段时间收费金额不能为空', trigger: 'blur' },
                    {
                        pattern: /^(([1-9]{1}\d{0,5})|(0{1}))(\.\d{0,2})?$/,
                        message: '金额格式不正确，小数点前最多6位数字',
                        trigger: 'blur',
                    },
                ],
                afterStepMin: [
                    { required: true, message: '超过首段间隔时间不能为空', trigger: 'blur' },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                afterStepMoney: [
                    { required: true, message: '超过首段间隔收费不能为空', trigger: 'blur' },
                    {
                        pattern: /^(([1-9]{1}\d{0,5})|(0{1}))(\.\d{0,2})?$/,
                        message: '金额格式不正确，小数点前最多6位数字',
                        trigger: 'blur',
                    },
                ],
                lastMin: [
                    { required: true, message: '尾段开始时间不能为空', trigger: 'blur' },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                lastStepMin: [
                    { required: true, message: '尾段间隔时间不能为空', trigger: 'blur' },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                lastStepMoney: [
                    { required: true, message: '尾段间隔收费不能为空', trigger: 'blur' },
                    {
                        pattern: /^(([1-9]{1}\d{0,5})|(0{1}))(\.\d{0,2})?$/,
                        message: '金额格式不正确，小数点前最多6位数字',
                        trigger: 'blur',
                    },
                ],
                maxMoney: [
                    { required: true, message: '24小时收费上限不能为空', trigger: 'blur' },
                    {
                        pattern: /^(([1-9]{1}\d{0,5})|(0{1}))(\.\d{0,2})?$/,
                        message: '金额格式不正确，小数点前最多6位数字',
                        trigger: 'blur',
                    },
                ],
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询停车场计费规则列表 */
        getList() {
            this.loading = true;
            listRules(this.queryParams).then(response => {
                this.rulesList = response.data.list;
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
                name: null,
                freeMin: null,
                firstMin: null,
                firstMoney: null,
                afterStepMin: null,
                afterStepMoney: null,
                lastMin: null,
                lastStepMin: null,
                lastStepMoney: null,
                maxMoney: null,
                delFlag: null,
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
            this.title = '添加停车场计费规则';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getRules(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改停车场计费规则';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateRules(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addRules(this.form).then(response => {
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
                .confirm('是否确认删除停车场计费规则编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delRules(ids);
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
                .confirm('是否确认导出所有停车场计费规则数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportRules(queryParams);
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
