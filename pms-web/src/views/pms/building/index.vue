<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="商铺区域" prop="block">
                            <el-select v-model="queryParams.block" placeholder="请选择商业区" clearable :style="{ width: '100%' }" :multiple="false">
                                <el-option
                                    v-for="(item, index) in areaOptions"
                                    :key="index"
                                    :label="item.name"
                                    :value="item.code"
                                    :disabled="item.disabled"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="楼栋名" prop="name">
                            <el-input v-model="queryParams.name" placeholder="请输入楼栋名" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="编号" prop="number">
                            <el-input v-model="queryParams.number" placeholder="请输入编号" clearable size="small" @keyup.enter.native="handleQuery" />
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
                            <el-button type="primary" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['pms:building:add']">新增</el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="buildingList" @selection-change="handleSelectionChange">
                    <el-table-column label="编号" align="center" prop="id" />
                    <el-table-column label="商铺区域" align="center" prop="blockName" />
                    <el-table-column label="楼栋名" align="center" prop="name" />
                    <el-table-column label="编号" align="center" prop="number" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['pms:building:edit']">
                                修改
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['pms:building:remove']">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改楼栋管理对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="商业区" prop="block">
                    <el-select :disabled="form.id" v-model="form.block" placeholder="请选择商业区" clearable :style="{ width: '100%' }" :multiple="false">
                        <el-option
                            v-for="(item, index) in areaOptions"
                            :key="index"
                            :label="item.name"
                            :value="item.code"
                            :disabled="item.disabled"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="编号" prop="number">
                    <el-input :disabled="form.id" v-model="form.number" placeholder="请输入编号" />
                </el-form-item>
                <el-form-item label="楼栋名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入楼栋名" />
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
import { listBuilding, getBuilding, delBuilding, addBuilding, updateBuilding, exportBuilding, listBlock } from '@/api/pms/building';

export default {
    data() {
        return {
            areaOptions: [],
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
            // 楼栋管理表格数据
            buildingList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                block: null,
                name: null,
                number: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                block: [{ required: true, message: '商铺区域不能为空', trigger: 'blur' }],
                name: [{ required: true, message: '楼栋名不能为空', trigger: 'blur' }],
                number: [{ required: true, message: '编号不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
        this.getareaOptions();
    },
    methods: {
        getareaOptions() {
            listBlock().then(res => {
                this.areaOptions = res.data;
            });
        },
        /** 查询楼栋管理列表 */
        getList() {
            this.loading = true;
            listBuilding(this.queryParams).then(response => {
                this.buildingList = response.data.list;
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
                block: null,
                name: null,
                number: null,
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
            this.title = '添加楼栋管理';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getBuilding(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改楼栋管理';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateBuilding(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addBuilding(this.form).then(response => {
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
                .confirm('是否确认删除楼栋管理编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delBuilding(ids);
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
                .confirm('是否确认导出所有楼栋管理数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportBuilding(queryParams);
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
