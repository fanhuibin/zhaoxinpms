<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="名称" prop="name">
                            <el-input v-model="queryParams.name" placeholder="请输入名称" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="状态" prop="status">
                            <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
                                <el-option v-for="dict in dict.type.park_checkpoint_state" :key="dict.value" :label="dict.label" :value="dict.value" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['park:checkpoint:add']">
                                新增
                            </el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="checkpointList" @selection-change="handleSelectionChange">
                    <el-table-column label="编号" align="center" prop="id" />
                    <el-table-column label="名称" align="center" prop="name" />
                    <el-table-column label="停车场id" align="center" prop="carParkId" />
                    <el-table-column label="停车场名" align="center" prop="carParkName" />
                    <el-table-column label="状态" align="center" prop="status">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.park_checkpoint_state" :value="scope.row.status" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['park:checkpoint:edit']">
                                修改
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-download" @click="download(scope.row)">
                                下载二维码
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['park:checkpoint:remove']">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改道闸管理对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入名称" />
                </el-form-item>
                <el-form-item label="停车场" prop="carParkId">
                    <el-select v-model="form.carParkId" placeholder="请选择停车场" clearable size="small">
                        <el-option v-for="park in parkList" :key="park.id" :label="park.name" :value="park.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态">
                    <el-radio-group v-model="form.status">
                        <el-radio v-for="dict in dict.type.park_checkpoint_state" :key="dict.value" :label="parseInt(dict.value)">{{ dict.label }}</el-radio>
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
import { listCheckpoint, getCheckpoint, delCheckpoint, addCheckpoint, updateCheckpoint, exportCheckpoint } from '@/api/park/checkpoint';
import { listAllPark } from '@/api/park/park';
export default {
    dicts: ['park_checkpoint_state'],
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
            // 道闸管理表格数据
            checkpointList: [],
            parkList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                name: null,
                status: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                name: [
                    { required: true, message: '名称不能为空', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' },
                ],
                carParkId: [{ required: true, message: '停车场id不能为空', trigger: 'blur' }],
                positionX: [{ required: true, message: '卡点位置经度不能为空', trigger: 'blur' }],
                positionY: [{ required: true, message: '卡点位置纬度不能为空', trigger: 'blur' }],
                positionDescribe: [{ required: true, message: '位置描述不能为空', trigger: 'blur' }],
                floor: [{ required: true, message: '所在楼层不能为空', trigger: 'blur' }],
                mode: [{ required: true, message: '模式不能为空', trigger: 'blur' }],
                status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
        listAllPark().then(response => {
            this.parkList = response.data;
        });
    },
    methods: {
        /** 查询道闸管理列表 */
        getList() {
            this.loading = true;
            listCheckpoint(this.queryParams).then(response => {
                this.checkpointList = response.data.list;
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
                carParkId: null,
                positionX: null,
                positionY: null,
                positionDescribe: null,
                floor: null,
                mode: null,
                status: 0,
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
            this.title = '添加道闸管理';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getCheckpoint(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改道闸管理';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateCheckpoint(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addCheckpoint(this.form).then(response => {
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
                .confirm('是否确认删除道闸管理编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delCheckpoint(ids);
                })
                .then(() => {
                    this.getList();
                    this.$modal.msgSuccess('删除成功');
                })
                .catch(() => {});
        },
        download(row) {
            const url = process.env.VUE_APP_BASE_API + '/park/checkpoint/qrcode/' + row.id;
            this.$download.customDownload(url);
        },
        /** 导出按钮操作 */
        handleExport() {
            const queryParams = this.queryParams;
            this.$modal
                .confirm('是否确认导出所有道闸管理数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportCheckpoint(queryParams);
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
