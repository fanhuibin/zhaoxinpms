<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="停车场名" prop="name">
                            <el-input v-model="queryParams.name" placeholder="请输入停车场名" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="手机" prop="phone">
                            <el-input v-model="queryParams.phone" placeholder="请输入手机" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="状态" prop="status">
                            <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
                                <el-option v-for="dict in dict.type.park_state" :key="dict.value" :label="dict.label" :value="dict.value" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['park:park:add']">新增</el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="parkList" @selection-change="handleSelectionChange">
                    <el-table-column label="编号" align="center" prop="id" />
                    <el-table-column label="停车场名" align="center" prop="name" />
                    <el-table-column label="车位数" align="center" prop="parkingSpaceNumber" />
                    <el-table-column label="已用车位数" align="center" prop="usedParkingSpaceNumber" />
                    <el-table-column label="停车场地址" align="center" prop="address" />
                    <el-table-column label="手机" align="center" prop="phone" />
                    <el-table-column label="状态" align="center" prop="status">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.park_state" :value="scope.row.status" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-download" @click="download(scope.row)">
                                下载二维码
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['park:park:edit']">
                                修改
                            </el-button>
                            <!--
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['park:park:remove']">
                                删除
                            </el-button>
                            -->
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改停车场对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="停车场名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入停车场名" />
                </el-form-item>
                <el-form-item label="车位数" prop="parkingSpaceNumber">
                    <el-input v-model="form.parkingSpaceNumber" placeholder="请输入车位数" />
                </el-form-item>
                <el-form-item label="停车场地址" prop="address">
                    <el-input v-model="form.address" placeholder="请输入停车场地址" />
                </el-form-item>
                <el-form-item label="手机" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入手机" />
                </el-form-item>
                <el-form-item label="状态">
                    <el-radio-group v-model="form.status">
                        <el-radio v-for="dict in dict.type.park_state" :key="dict.value" :label="parseInt(dict.value)">{{ dict.label }}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="收费规则" prop="ruleId">
                    <el-select v-model="form.ruleId" placeholder="请选择收费规则" clearable size="small">
                        <el-option v-for="rule in ruleList" :key="rule.id" :label="rule.name" :value="rule.id" />
                    </el-select>
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
import { listPark, getPark, delPark, addPark, updatePark, exportPark } from '@/api/park/park';
import { listAllRules } from '@/api/park/rules';
export default {
    dicts: ['park_state'],
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
            // 停车场表格数据
            parkList: [],
            ruleList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                name: null,
                phone: null,
                status: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                name: [{ required: true, message: '停车场名不能为空', trigger: 'blur' }],
                image: [{ required: true, message: '停车场照片不能为空', trigger: 'blur' }],
                positionX: [{ required: true, message: '经度不能为空', trigger: 'blur' }],
                positionY: [{ required: true, message: '纬度不能为空', trigger: 'blur' }],
                startBusinessHours: [{ required: true, message: '开始营业时间不能为空', trigger: 'blur' }],
                endBusinessHours: [{ required: true, message: '结束营业时间不能为空', trigger: 'blur' }],
                parkingSpaceNumber: [
                    { required: true, message: '车位数不能为空', trigger: 'blur' },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                usedParkingSpaceNumber: [{ required: true, message: '已用车位数不能为空', trigger: 'blur' }],
                address: [{ required: true, message: '停车场地址不能为空', trigger: 'blur' }],
                phone: [
                    { required: true, message: '手机不能为空', trigger: 'blur' },
                    {
                        pattern: /^1[3456789]\d{9}$|^0\d{2,3}-?\d{7,8}$/,
                        message: '请输入正确的联系方式',
                        trigger: 'blur',
                    },
                ],
                landline: [{ required: true, message: '座机不能为空', trigger: 'blur' }],
                status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
                countryId: [{ required: true, message: '国家id不能为空', trigger: 'blur' }],
                provinceId: [{ required: true, message: '省份id不能为空', trigger: 'blur' }],
                stateOrCityId: [{ required: true, message: '州或城市id不能为空', trigger: 'blur' }],
                zoneOrCountyId: [{ required: true, message: '区或县id不能为空', trigger: 'blur' }],
                townshipId: [{ required: true, message: '乡镇id不能为空', trigger: 'blur' }],
                ruleId: [{ required: true, message: '乡镇id不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
        listAllRules().then(response => {
            this.ruleList = response.data;
        });
    },
    methods: {
        /** 查询停车场列表 */
        getList() {
            this.loading = true;
            listPark(this.queryParams).then(response => {
                this.parkList = response.data.list;
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
                image: null,
                positionX: null,
                positionY: null,
                startBusinessHours: null,
                endBusinessHours: null,
                parkingSpaceNumber: null,
                usedParkingSpaceNumber: null,
                address: null,
                phone: null,
                landline: null,
                status: 0,
                countryId: null,
                provinceId: null,
                stateOrCityId: null,
                zoneOrCountyId: null,
                townshipId: null,
                ruleId: null,
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
            this.title = '添加停车场';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getPark(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改停车场';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updatePark(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addPark(this.form).then(response => {
                            this.$modal.msgSuccess('新增成功');
                            this.open = false;
                            this.getList();
                        });
                    }
                }
            });
        },
        download(row) {
            const url = process.env.VUE_APP_BASE_API + '/park/park/qrcode/' + row.id;
            this.$download.customDownload(url);
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            const ids = row.id || this.ids;
            this.$modal
                .confirm('是否确认删除停车场编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delPark(ids);
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
                .confirm('是否确认导出所有停车场数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportPark(queryParams);
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
