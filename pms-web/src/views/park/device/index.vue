<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="设备名" prop="name">
                            <el-input v-model="queryParams.name" placeholder="请输入设备名" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <!--<el-col :span="6">
                        <el-form-item label="停车场id" prop="carParkId">
                            <el-input v-model="queryParams.carParkId" placeholder="请输入停车场id" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>-->
                    <el-col :span="6">
                        <el-form-item label="设备条形码" prop="barCode">
                            <el-input v-model="queryParams.barCode" placeholder="请输入设备条形码" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="状态" prop="status">
                            <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
                                <el-option v-for="dict in dict.type.park_device_state" :key="dict.value" :label="dict.label" :value="dict.value" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="类型" prop="type">
                            <el-select v-model="queryParams.type" placeholder="请选择类型" clearable size="small">
                                <el-option v-for="dict in dict.type.park_device_type" :key="dict.value" :label="dict.label" :value="dict.value" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['park:device:add']">新增</el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="deviceList" @selection-change="handleSelectionChange">
                    <el-table-column label="编号" align="center" prop="id" />
                    <el-table-column label="设备名" align="center" prop="name" />
                    <el-table-column label="停车场" align="center" prop="carParkName" />
                    <el-table-column label="道闸" align="center" prop="checkpointName" />
                    <el-table-column label="类型" align="center" prop="type">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.park_device_type" :value="scope.row.type" />
                        </template>
                    </el-table-column>
                    <el-table-column label="方向" align="center" prop="direction">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.park_device_direction" :value="scope.row.direction" />
                        </template>
                    </el-table-column>
                    <el-table-column label="设备条形码" align="center" prop="barCode" />
                    <el-table-column label="品牌" align="center" prop="brand" />
                    <el-table-column label="型号" align="center" prop="model" />
                    <el-table-column label="状态" align="center" prop="status">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.park_device_state" :value="scope.row.status" />
                        </template>
                    </el-table-column>
                    <!--
                    <el-table-column label="最近上线时间" align="center" prop="lastOnlineTime" />
                    -->
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['park:device:edit']">
                                修改
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['park:device:remove']">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改停车场设备对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="120px">
                <el-form-item label="类型" prop="type">
                    <el-select v-model="form.type" placeholder="请选择类型">
                        <el-option v-for="dict in dict.type.park_device_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="道闸" prop="checkpointId">
                    <el-select v-model="form.checkpointId" placeholder="请选择道闸" clearable size="small">
                        <el-option v-for="dict in checkpointList" :key="dict.id" :label="dict.name" :value="dict.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="方向" prop="direction">
                    <el-select v-model="form.direction" placeholder="请选择类型" clearable size="small">
                        <el-option v-for="dict in dict.type.park_device_direction" :key="dict.value" :label="dict.label" :value="dict.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="设备名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入设备名" />
                </el-form-item>
                <el-form-item label="设备条形码" prop="barCode">
                    <el-input v-model="form.barCode" placeholder="请输入设备条形码" />
                </el-form-item>
                <el-form-item label="品牌" prop="brand">
                    <el-input v-model="form.brand" placeholder="请输入品牌" />
                </el-form-item>
                <el-form-item label="型号" prop="model">
                    <el-input v-model="form.model" placeholder="请输入型号" />
                </el-form-item>
                <el-form-item label="对接协议" prop="model">
                    <el-input disabled value="臻识MQTT协议接口" placeholder="臻识MQTT协议接口" />
                </el-form-item>
                <el-form-item label="状态">
                    <el-radio-group v-model="form.status">
                        <el-radio v-for="dict in dict.type.park_device_state" :key="dict.value" :label="parseInt(dict.value)">{{ dict.label }}</el-radio>
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
import { listDevice, getDevice, delDevice, addDevice, updateDevice, exportDevice } from '@/api/park/device';
import { listAllCheckpoint } from '@/api/park/checkpoint';
export default {
    dicts: ['park_device_state', 'park_device_type', 'park_device_direction'],
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
            // 停车场设备表格数据
            deviceList: [],
            checkpointList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                name: null,
                carParkId: null,
                barCode: null,
                brand: null,
                model: null,
                status: null,
                lastOnlineTime: null,
                linkMode: null,
                type: null,
                checkpointId: null,
                direction: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                name: [
                    { required: true, message: '设备名不能为空', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
                ],
                carParkId: [{ required: true, message: '停车场id不能为空', trigger: 'blur' }],
                barCode: [
                    { required: true, message: '设备条形码不能为空', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
                ],
                brand: [
                    { required: true, message: '品牌不能为空', trigger: 'blur' },
                    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' },
                ],
                model: [
                    { required: true, message: '型号不能为空', trigger: 'blur' },
                    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' },
                ],
                status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
                lastOnlineTime: [{ required: true, message: '最近上线时间不能为空', trigger: 'blur' }],
                linkMode: [{ required: true, message: '连接模式(poll-轮询/socket-长连接)不能为空', trigger: 'blur' }],
                type: [{ required: true, message: '类型不能为空', trigger: 'change' }],
                checkpointId: [{ required: true, message: '道闸不能为空', trigger: 'blur' }],
                direction: [{ required: true, message: '方向不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
        listAllCheckpoint().then(response => {
            this.checkpointList = response.data;
        });
    },
    methods: {
        /** 查询停车场设备列表 */
        getList() {
            this.loading = true;
            listDevice(this.queryParams).then(response => {
                this.deviceList = response.data.list;
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
                barCode: null,
                brand: null,
                model: null,
                status: 0,
                lastOnlineTime: null,
                linkMode: null,
                type: null,
                checkpointId: null,
                direction: null,
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
            this.title = '添加停车场设备';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getDevice(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改停车场设备';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateDevice(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addDevice(this.form).then(response => {
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
                .confirm('是否确认删除停车场设备编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delDevice(ids);
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
                .confirm('是否确认导出所有停车场设备数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportDevice(queryParams);
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
