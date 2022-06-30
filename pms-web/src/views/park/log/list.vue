<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="车牌号" prop="numberPlate">
                            <el-input v-model="queryParams.numberPlate" placeholder="请输入车牌号" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="停车场" prop="parkId">
                            <el-select v-model="queryParams.parkId" placeholder="请选择停车场" clearable size="small">
                                <el-option v-for="dict in parkList" :key="dict.id" :label="dict.name" :value="dict.id" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="出场时间" prop="outTimeRange">
                            <el-date-picker
                                v-model="queryParams.outTimeRange"
                                type="datetimerange"
                                :picker-options="pickerOptions"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                value-format="timestamp"
                                align="right"
                            ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <el-row :gutter="10" class="mb8"></el-row>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="logList" @selection-change="handleSelectionChange">
                    <el-table-column label="id" align="center" prop="id" />
                    <el-table-column label="车牌号" align="center" prop="numberPlate" />
                    <el-table-column label="用户id" align="center" prop="uid" />
                    <el-table-column label="停车场" align="center" prop="parkName" />
                    <el-table-column label="进场口" align="center" prop="inCheckPointName" />
                    <el-table-column label="进场时间" align="center" prop="inTime">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.inTime) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="进场放行方式" align="center" prop="inReleaseType">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.park_access_type" :value="scope.row.inReleaseType" />
                        </template>
                    </el-table-column>
                    <el-table-column label="出场口" align="center" prop="outCheckPointName" />
                    <el-table-column label="出场时间" align="center" prop="outTime">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.outTime) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="出场放行方式" align="center" prop="outReleaseType">
                        <template slot-scope="scope">
                            <dict-tag :options="dict.type.park_access_type" :value="scope.row.outReleaseType" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <div></div>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改车辆出入记录对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="车牌号" prop="numberPlate">
                    <el-input v-model="form.numberPlate" placeholder="请输入车牌号" />
                </el-form-item>
                <el-form-item label="用户id(使用场景:申请临时通行时会标记用户)" prop="uid">
                    <el-input v-model="form.uid" placeholder="请输入用户id(使用场景:申请临时通行时会标记用户)" />
                </el-form-item>
                <el-form-item label="进场小图">
                    <imageUpload v-model="form.inSmallImage" />
                </el-form-item>
                <el-form-item label="出场小图">
                    <imageUpload v-model="form.outSmallImage" />
                </el-form-item>
                <el-form-item label="进场大图">
                    <imageUpload v-model="form.inBigImage" />
                </el-form-item>
                <el-form-item label="出场大图">
                    <imageUpload v-model="form.outBigImage" />
                </el-form-item>
                <el-form-item label="停车场id" prop="parkId">
                    <el-input v-model="form.parkId" placeholder="请输入停车场id" />
                </el-form-item>
                <el-form-item label="进场时间" prop="inTime">
                    <el-input v-model="form.inTime" placeholder="请输入进场时间" />
                </el-form-item>
                <el-form-item label="出场时间" prop="outTime">
                    <el-input v-model="form.outTime" placeholder="请输入出场时间" />
                </el-form-item>
                <el-form-item label="入场放行方式(0-未知/1-自动抬杆/2-人工抬杆/3-手机放行，断电断网时)" prop="inReleaseType">
                    <el-select v-model="form.inReleaseType" placeholder="请选择入场放行方式(0-未知/1-自动抬杆/2-人工抬杆/3-手机放行，断电断网时)">
                        <el-option v-for="dict in dict.type.park_access_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="出场放行方式(0-未知/1-自动抬杆/2-人工抬杆/3-手机放行，断电断网时)" prop="outReleaseType">
                    <el-select v-model="form.outReleaseType" placeholder="请选择出场放行方式(0-未知/1-自动抬杆/2-人工抬杆/3-手机放行，断电断网时)">
                        <el-option v-for="dict in dict.type.park_access_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="入口id" prop="inCheckPointId">
                    <el-input v-model="form.inCheckPointId" placeholder="请输入入口id" />
                </el-form-item>
                <el-form-item label="出口id" prop="outCheckPointId">
                    <el-input v-model="form.outCheckPointId" placeholder="请输入出口id" />
                </el-form-item>
                <el-form-item label="当前状态(in 在场内， out已出场)" prop="state">
                    <el-input v-model="form.state" placeholder="请输入当前状态(in 在场内， out已出场)" />
                </el-form-item>
                <el-form-item label="支付时间" prop="payTime">
                    <el-input v-model="form.payTime" placeholder="请输入支付时间" />
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
import { outListLog, getLog, delLog, addLog, updateLog, exportLog } from '@/api/park/log';
import { listAllCheckpoint } from '@/api/park/checkpoint';
import { listAllPark } from '@/api/park/park';
export default {
    dicts: ['park_access_type', 'park_use_state'],
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
            // 停车场列表
            parkList: [],
            // 关卡列表
            checkpointList: [],
            // 总条数
            total: 0,
            // 车辆出入记录表格数据
            logList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                numberPlate: null,
                uid: null,
                parkId: null,
                inTime: null,
                outTime: null,
                outTimeRange: [],
                inReleaseType: null,
                outReleaseType: null,
                inCheckPointId: null,
                outCheckPointId: null,
                state: null,
                payTime: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                numberPlate: [{ required: true, message: '车牌号不能为空', trigger: 'blur' }],
                uid: [{ required: true, message: '用户id(使用场景:申请临时通行时会标记用户)不能为空', trigger: 'blur' }],
                inSmallImage: [{ required: true, message: '进场小图不能为空', trigger: 'blur' }],
                outSmallImage: [{ required: true, message: '出场小图不能为空', trigger: 'blur' }],
                inBigImage: [{ required: true, message: '进场大图不能为空', trigger: 'blur' }],
                outBigImage: [{ required: true, message: '出场大图不能为空', trigger: 'blur' }],
                parkId: [{ required: true, message: '停车场id不能为空', trigger: 'blur' }],
                inTime: [{ required: true, message: '进场时间不能为空', trigger: 'blur' }],
                outTime: [{ required: true, message: '出场时间不能为空', trigger: 'blur' }],
                inReleaseType: [{ required: true, message: '入场放行方式(0-未知/1-自动抬杆/2-人工抬杆/3-手机放行，断电断网时)不能为空', trigger: 'change' }],
                outReleaseType: [{ required: true, message: '出场放行方式(0-未知/1-自动抬杆/2-人工抬杆/3-手机放行，断电断网时)不能为空', trigger: 'change' }],
                inCheckPointId: [{ required: true, message: '入口id不能为空', trigger: 'blur' }],
                outCheckPointId: [{ required: true, message: '出口id不能为空', trigger: 'blur' }],
                state: [{ required: true, message: '当前状态(in 在场内， out已出场)不能为空', trigger: 'blur' }],
                payTime: [{ required: true, message: '支付时间不能为空', trigger: 'blur' }],
            },
            pickerOptions: {
                shortcuts: [
                    {
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                ],
            },
        };
    },
    created() {
        this.getList();
        listAllPark().then(response => {
            this.parkList = response.data;
        });
        listAllCheckpoint().then(response => {
            this.checkpointList = response.data;
        });
    },
    methods: {
        /** 查询车辆出入记录列表 */
        getList() {
            this.loading = true;
            outListLog(this.queryParams).then(response => {
                this.logList = response.data.list;
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
                numberPlate: null,
                uid: null,
                inSmallImage: null,
                outSmallImage: null,
                inBigImage: null,
                outBigImage: null,
                parkId: null,
                inTime: null,
                outTime: null,
                inReleaseType: null,
                outReleaseType: null,
                inCheckPointId: null,
                outCheckPointId: null,
                state: null,
                payTime: null,
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
            this.title = '添加车辆出入记录';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getLog(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改车辆出入记录';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateLog(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addLog(this.form).then(response => {
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
                .confirm('是否确认删除车辆出入记录编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delLog(ids);
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
                .confirm('是否确认导出所有车辆出入记录数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportLog(queryParams);
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
