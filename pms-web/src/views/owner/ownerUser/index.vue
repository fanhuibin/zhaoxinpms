<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="真实姓名" prop="userName">
                            <el-input v-model="queryParams.userName" placeholder="请输入真实姓名" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="身份证号" prop="idcard">
                            <el-input v-model="queryParams.idcard" placeholder="请输入身份证号" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="手机号码" prop="phonenumber">
                            <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号码" clearable size="small" @keyup.enter.native="handleQuery" />
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
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['owner:ownerUser:add']">
                                新增
                            </el-button>
                        </el-col>
                    </el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="ownerUserList" @selection-change="handleSelectionChange">
                    <el-table-column label="公司名" align="center" prop="company" />
                    <el-table-column label="业主姓名" align="center" prop="userName" />
                    <el-table-column label="身份证号" align="center" prop="idcard" width="150px" />
                    <el-table-column label="手机号码" align="center" prop="phonenumber" />
                    <el-table-column label="性别" align="center" prop="sex">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex" />
                        </template>
                    </el-table-column>
                    <el-table-column label="帐号状态" align="center" prop="status">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
                        </template>
                    </el-table-column>
                    <el-table-column label="租赁的商铺" align="center" prop="rentedCount" />
                    <el-table-column label="拥有的商铺" align="center" prop="ownCount" />
                    <el-table-column label="备注" align="center" prop="remark" />
                    <el-table-column label="操作" align="center" width="120px" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['owner:ownerUser:edit']">修改</el-button>
                            <el-button size="mini" type="text" @click="handleShowDetail(scope.row)" v-hasPermi="['owner:ownerUser:edit']">明细</el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改业主信息对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body class="Jdialog Jdialog_center">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="公司名" prop="company">
                    <el-input v-model="form.company" placeholder="请输入公司名" :maxlength="50" show-word-limit />
                </el-form-item>
                <el-form-item label="业主姓名" prop="userName">
                    <el-input v-model="form.userName" placeholder="请输入业主姓名" :maxlength="30" show-word-limit />
                </el-form-item>
                <el-form-item label="身份证号" prop="idcard">
                    <el-input v-model="form.idcard" placeholder="请输入身份证号" :maxlength="18" show-word-limit />
                </el-form-item>
                <el-form-item label="手机号码" prop="phonenumber">
                    <el-input v-model="form.phonenumber" placeholder="请输入手机号码" :maxlength="11" show-word-limit />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-select v-model="form.sex" placeholder="请选择性别">
                        <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="账号状态">
                    <el-radio-group v-model="form.status">
                        <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
                    </el-radio-group>
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
        <userDetail v-if="showDetail" ref="userDetail" @close="showDetail=false"/>
    </div>
</template>

<script>
import { listOwnerUser, getOwnerUser, delOwnerUser, addOwnerUser, updateOwnerUser, exportOwnerUser } from '@/api/owner/ownerUser';
import userDetail from './detail';
export default {
    dicts: ['sys_user_sex', 'sys_normal_disable'],
    components: {userDetail},
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
            // 业主信息表格数据
            ownerUserList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            //是否显示detail
            showDetail: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                userName: null,
                idcard: null,
                phonenumber: null,
                ownCount: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                company: [
                    {
                        required: true,
                        message: '请输入公司名',
                        trigger: 'blur',
                    },
                ],
                userName: [
                    {
                        required: true,
                        message: '请输入业主姓名',
                        trigger: 'blur',
                    },
                ],
                idcard: [
                    {
                        required: true,
                        message: '请输入身份证号',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                        message: '请输入正确的身份证号',
                        trigger: 'blur',
                    },
                ],
                sex: [
                    {
                        required: true,
                        message: '请输入性别',
                        trigger: 'blur',
                    },
                ],
                phonenumber: [
                    {
                        required: true,
                        message: '请输入手机号',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^1(3|4|5|7|8|9)\d{9}$/,
                        message: '手机号格式错误',
                        trigger: 'blur',
                    },
                ],
                remark: [
                   
                ],
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询业主信息列表 */
        getList() {
            this.loading = true;
            listOwnerUser(this.queryParams).then(response => {
                this.ownerUserList = response.data.list;
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
                userName: null,
                idcard: null,
                phonenumber: null,
                sex: null,
                status: '0',
                rentedCount: null,
                ownCount: null,
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
        /** 打开detail */
        handleShowDetail(ownerUser) {
            this.showDetail = true;
            this.$nextTick(() => {
                this.$refs.userDetail.init(ownerUser);
            });
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = '添加业主信息';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getOwnerUser(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改业主信息';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateOwnerUser(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addOwnerUser(this.form).then(response => {
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
                .confirm('是否确认删除业主信息编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delOwnerUser(ids);
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
