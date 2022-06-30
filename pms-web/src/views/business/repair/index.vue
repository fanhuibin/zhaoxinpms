<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="报修单号" prop="no">
                            <el-input v-model="queryParams.no" placeholder="请输入报修单号" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="报修电话" prop="applyPhone">
                            <el-input v-model="queryParams.applyPhone" placeholder="请输入报修电话" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="分类" prop="applyCategory">
                            <el-select v-model="queryParams.applyCategory" placeholder="请选择分类" clearable size="small">
                                <el-option v-for="dict in dict.type.repair_category" :key="dict.value" :label="dict.label" :value="dict.value" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="商户编号" prop="applyHouse">
                            <el-input
                                v-model="queryParams.applyHouse"
                                placeholder="请输入报修商户编号"
                                clearable
                                size="small"
                                @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="流程状态" prop="state">
                            <el-select v-model="queryParams.state" placeholder="请选择流程状态" clearable size="small">
                                <el-option v-for="dict in dict.type.repair_state" :key="dict.value" :label="dict.label" :value="dict.value" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="数据来源" prop="client">
                            <el-select v-model="queryParams.client" placeholder="请选择数据来源" clearable size="small">
                                <el-option v-for="dict in dict.type.flow_client" :key="dict.value" :label="dict.label" :value="dict.value" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['business:repair:add']">
                                新增
                            </el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>
                <JTable v-loading="loading" :data="repairList" @selection-change="handleSelectionChange">
                    <el-table-column label="报修单号" align="center" prop="no" />
                    <el-table-column label="报修标题" align="center" prop="title" />
                    <el-table-column label="分类" align="center" prop="applyCategory">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.repair_category" :value="scope.row.applyCategory" />
                        </template>
                    </el-table-column>
                    <el-table-column label="报修电话" align="center" prop="applyPhone" />
                    <el-table-column label="报修者姓名" align="center" prop="applyName" />
                    <el-table-column label="报修时间" align="center" prop="applyTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="流程状态" align="center" prop="state">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.repair_state" :value="scope.row.state" />
                        </template>
                    </el-table-column>
                    <el-table-column label="数据来源" align="center" prop="client">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.flow_client" :value="scope.row.client" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150" fixed="right">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" @click="toDetail(scope.row)">详情</el-button>
                        </template>
                    </el-table-column>
                </JTable>
                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改报事工单对话框 -->
        <el-dialog class="Jdialog Jdialog_center" :title="title" :visible.sync="open" width="600px" append-to-body lock-scroll>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="数据来源" prop="client">
                    <el-radio-group v-model="form.client" size="medium">
                        <el-radio-button v-for="dict in dict.type.flow_client" :key="dict.id" :label="dict.value">{{ dict.label }}</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="报修分类" prop="applyCategory">
                    <el-select v-model="form.applyCategory" placeholder="请选择报修分类">
                        <el-option v-for="dict in dict.type.repair_category" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="商铺编号" prop="applyHouse" v-if="form.client != 'web_company'">
                    <el-input v-model="form.applyHouse" placeholder="商铺编号" clearable :style="{ width: '70%' }"></el-input>
                    <el-button type="primary" @click="searchHouse()">搜索</el-button>
                </el-form-item>
                <el-form-item label="报修人员" prop="applyName" :maxlength="20">
                    <el-input v-model="form.applyName" placeholder="请输入报修人员姓名" />
                </el-form-item>
                <el-form-item label="报修电话" prop="applyPhone" :maxlength="20">
                    <el-input v-model="form.applyPhone" placeholder="请输入报修电话" />
                </el-form-item>
                <el-form-item label="预约时间" prop="appointmentTime" v-if="form.client != 'web_company'">
                    <el-date-picker
                        clearable
                        size="small"
                        v-model="form.appointmentTime"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择预约时间"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="报修内容" prop="applyContent">
                    <el-input
                        v-model="form.applyContent"
                        placeholder="请输入报修的内容说明"
                        :maxlength="200"
                        :style="{ width: '100%' }"
                        true
                        type="textarea"
                        :rows="3"
                    />
                </el-form-item>
                <el-form-item label="报修图片" prop="applyImg">
                    <imageUpload v-model="form.applyImg" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>

        <!-- 处理框-->
        <component :is="currentView" v-if="formVisible" @close="colseForm" ref="auditForm" />
    </div>
</template>

<script>
import { listRepair, getRepair, delRepair, addRepair, updateRepair } from '@/api/business/repair';
import request from '@/utils/request';
import AuditForm from '../../workflow/form/Audit';
export default {
    dicts: ['repair_category', 'return_state', 'return_result', 'repair_state', 'flow_client'],
    components: { AuditForm },
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
            // 报事工单表格数据
            repairList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                instanceId: null,
                no: null,
                title: null,
                applyPhone: null,
                applyName: null,
                applyCategory: null,
                applyContent: null,
                applyHouse: null,
                applyTime: null,
                applyImg: null,
                appContent: null,
                appointmentTime: null,
                appTime: null,
                repairUser: null,
                repairMaterialsFee: null,
                repairServiceFee: null,
                repairContent: null,
                returnState: null,
                returnResult: null,
                returnRemark: null,
                endTime: null,
                state: null,
                client: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                client: [{ required: true, message: '请选择报修数据来源', trigger: 'blur' }],
                applyHouse: [{ required: true, message: '请选择商铺编号', trigger: 'blur' }],
                applyPhone: [
                    { required: true, message: '报修电话不能为空', trigger: 'blur' },
                    { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' },
                ],
                applyName: [{ required: true, message: '报修者姓名不能为空', trigger: 'blur' }],
                applyCategory: [{ required: true, message: '报修分类不能为空', trigger: 'change' }],
                applyContent: [{ required: true, message: '报修的内容不能为空', trigger: 'blur' }],
            },
            formVisible: false,
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询报事工单列表 */
        getList() {
            this.loading = true;
            listRepair(this.queryParams).then(response => {
                this.repairList = response.data.list;
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
                no: null,
                title: null,
                applyPhone: null,
                applyName: null,
                applyCategory: null,
                applyContent: null,
                applyHouse: null,
                applyTime: null,
                applyImg: null,
                state: null,
                client: 'web_company',
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
            this.title = '创建报修工单';
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    addRepair(this.form).then(response => {
                        this.$modal.msgSuccess('新增成功');
                        this.open = false;
                        this.getList();
                    });
                }
            });
        },
        toDetail(item) {
            let data = {
                processDefinitionKey: 'repair',
                instanceId: item.instanceId,
                delegateId: item.delegateId,
                taskId: item.taskId,
                taskDefKey: item.taskDefKey,
                id: item.id,
                isSelf: true,
                isAudit: false, //是否可以审核
                hasCancel: false, //可不可以撤销
            };
            this.currentView = 'auditForm';
            this.$nextTick(() => {
                this.formVisible = true;
                this.$nextTick(() => {
                    this.$refs.auditForm.init(data);
                });
            });
        },
        colseForm(isRefresh) {
            this.formVisible = false;
            if (isRefresh) this.refresh();
        },
        searchHouse() {
            request({
                url: '/payment/PaymentContract/resourceName/' + this.form.applyHouse,
                method: 'get',
            })
                .then(res => {
                    this.form.applyName = res.data.contract.userName;
                    this.form.applyPhone = res.data.contract.userPhone;
                })
                .catch(err => {
                    this.form.applyHouse = '';
                    this.form.applyUser = '';
                });
        },
    },
};
</script>
