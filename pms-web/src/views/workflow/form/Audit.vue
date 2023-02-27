<template>
    <transition name="el-zoom-in-center">
        <div class="Jpreview-main flow-form-main nohead">
            <div class="btns">
                <template>
                    <el-button type="primary" @click="transfer()">转 办</el-button>
                    <el-button type="primary" @click="approval('audit')">提 交</el-button>
                    <el-button type="danger" @click="jumpTo()" v-if="setting.showReject">驳 回</el-button>
                    <el-button type="danger" @click="cancel()" v-if="setting.hasCancel">终 止</el-button>
                </template>
                <el-button @click="goBack()">返 回</el-button>
            </div>
            <el-tabs class="Jel_tabs" v-model="activeTab" style="padding: 0 10px">
                <el-tab-pane label="工单信息">
                    <component v-if="formShow" :is="currentView" :conf="setting" @close="goBack" ref="form" @approval="handleApproval" />
                </el-tab-pane>
                <el-tab-pane label="流程信息">
                    <preview v-if="previewShow" :conf="designerImg" ref="processPreview" />
                </el-tab-pane>
                <el-tab-pane label="流转记录">
                    <recordList :list="historyList" v-if="recordShow" />
                </el-tab-pane>
            </el-tabs>
            <!--用户列表对话框-->
            <el-dialog title="选择用户" :visible.sync="showUserTable" width="900px" append-to-body>
                <!--用户列表表格-->
                <div>
                    <el-form :model="queryUserParams" ref="queryUserForm" :inline="true" v-show="showUserSearch" label-width="68px">
                        <el-form-item label="用户名称" prop="userName">
                            <el-input
                                v-model="queryUserParams.userName"
                                placeholder="请输入用户名称"
                                clearable
                                size="small"
                                style="width: 240px"
                                @keyup.enter.native="handleUserQuery"
                            />
                        </el-form-item>
                        <el-form-item label="手机号码" prop="phonenumber">
                            <el-input
                                v-model="queryUserParams.phonenumber"
                                placeholder="请输入手机号码"
                                clearable
                                size="small"
                                style="width: 240px"
                                @keyup.enter.native="handleUserQuery"
                            />
                        </el-form-item>
                        <el-form-item>
                            <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleUserQuery">搜索</el-button>
                            <el-button icon="el-icon-refresh" size="mini" @click="resetUserQuery">重置</el-button>
                        </el-form-item>
                    </el-form>

                    <el-row :gutter="10" class="mb8">
                        <right-toolbar :showSearch.sync="showUserSearch" @queryTable="getUserList"></right-toolbar>
                    </el-row>

                    <el-table v-loading="userLoading" :data="userList" @selection-change="handleUserSelectionChange">
                        <el-table-column type="selection" width="50" align="center" />
                        <el-table-column label="用户编号" align="center" prop="userId" />
                        <el-table-column label="用户名称" align="center" prop="userName" :show-overflow-tooltip="true" />
                        <el-table-column label="用户昵称" align="center" prop="nickName" :show-overflow-tooltip="true" />
                        <el-table-column label="部门" align="center" prop="dept.deptName" :show-overflow-tooltip="true" />
                        <el-table-column label="手机号码" align="center" prop="phonenumber" width="120" />
                        <el-table-column label="创建时间" align="center" prop="createTime" width="160">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.createTime) }}</span>
                            </template>
                        </el-table-column>
                    </el-table>

                    <pagination
                        v-show="userTotal > 0"
                        :total="userTotal"
                        :page.sync="queryUserParams.pageNum"
                        :limit.sync="queryUserParams.pageSize"
                        @pagination="getUserList"
                    />
                </div>
                <div slot="footer" class="dialog-footer" style="text-align: right">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="showUserTable = false">关闭</el-button>
                </div>
            </el-dialog>
        </div>
    </transition>
</template>

<script>
import recordList from './RecordList';
import request from '@/utils/request';
import preview from '@/components/workflow/Process/preview.vue';
import { listUser } from '@/api/system/user';
import { format, formatTotalDateSub } from '@/utils/activiti/myUtil.js';
export default {
    components: { recordList, preview },
    data() {
        return {
            currentView: '',
            setting: {
                showReject:true ,
            },
            visible: false,
            treeData: [],
            fullName: '',
            activeTab: '0',
            designerImg: '',
            previewShow: false,
            historyList: [],
            formShow: false,
            recordShow: false,
            showUserTable: false,
            showUserSearch: true,
            // 遮罩层
            userLoading: false,
            // 查询参数
            queryUserParams: {
                pageNum: 1,
                pageSize: 10,
                userName: undefined,
                phonenumber: undefined,
                status: undefined,
            },
            // 总条数
            userTotal: 0,
            userList: [],
            historyQueryParams: {
                pageNum: 1,
                pageSize: 999,
                processInstanceId: null,
                activityName: null,
                assignee: null,
            },
        };
    },
    methods: {
        goBack(isRefresh) {
            this.$emit('close', isRefresh);
        },
        init(data) {
            this.activeTab = '0';
            this.currentView = resolve => require([`@/views/business/${data.processDefinitionKey}/audit`], resolve);
            this.setting = Object.assign(this.setting, data);
            this.formShow = true;
            this.getInfo(data);
        },
        getInfo(data) {
            //查询流程图
            request({
                url: `/activiti/process/designer/` + data.instanceId,
                method: 'get',
            }).then(res => {
                this.designerImg = res.data.model.processData;
                const completeTasks = res.data.completeTasks;
                const tasks = res.data.tasks;

                const loop = (data, taskDefKey, className) => {
                    if (Array.isArray(data)) data.forEach(d => loop(d, taskDefKey, className));
                    if (data.nodeId === taskDefKey) {
                        data.state = className;
                        return;
                    }
                    if (data.conditionNodes && Array.isArray(data.conditionNodes)) loop(data.conditionNodes, taskDefKey, className);
                    if (data.childNode) loop(data.childNode, taskDefKey, className);
                };

                completeTasks.forEach(task => {
                    loop(this.designerImg, task, 'complete');
                });

                tasks.forEach(task => {
                    loop(this.designerImg, task, 'active');
                });

                this.previewShow = true;
            });

            //查询历史记录
            this.historyQueryParams.processInstanceId = data.instanceId;
            request({
                url: '/activiti/process/listHistory',
                method: 'post',
                data: this.historyQueryParams,
            })
                .then(response => {
                    this.historyList = response.rows;
                    this.historyList.forEach(row => {
                        row.startTime = format(row.startTime, 'yyyy-MM-dd HH:mm:ss');
                        row.endTime = format(row.endTime, 'yyyy-MM-dd HH:mm:ss');
                        row.durationInMillis = formatTotalDateSub(row.durationInMillis / 1000);
                    });
                    this.recordShow = true;
                })
                .then(() => {});
        },
        approval(eventType) {
            this.$refs.form && this.$refs.form.dataFormSubmit(eventType);
        },
        handleApproval(formData, eventType) {
            this.formData = formData;
            this[eventType]();
        },
        revoke() {
            this.$prompt('', '撤回流程', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputPlaceholder: '请输入撤回原因（必填）',
                inputType: 'textarea',
                inputErrorMessage: '原因不能为空',
                inputValue: '',
                inputValidator: val => {
                    if (!val) return false;
                },
                closeOnClickModal: false,
            })
                .then(({ value }) => {
                    Revoke(this.setting.id, { handleOpinion: value }).then(res => {
                        this.$message({
                            type: 'success',
                            message: res.msg,
                            duration: 1000,
                            onClose: () => {
                                this.$emit('close', true);
                            },
                        });
                    });
                })
                .catch(() => {});
        },
        cancel() {
            this.$prompt('', '终止审核不可恢复', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputPlaceholder: '请输入终止原因（必填）',
                inputType: 'textarea',
                inputErrorMessage: '原因不能为空',
                inputValue: '',
                inputValidator: val => {
                    if (!val) return false;
                },
                closeOnClickModal: false,
            })
                .then(({ value }) => {
                    const data = { instanceId: this.setting.instanceId };
                    request({
                        url: '/activiti/process/cancelApply',
                        method: 'post',
                        params: data,
                    })
                        .then(response => {
                            this.$emit('close', true);
                            this.showSuccess('操作成功');
                        })
                        .catch(function () {});
                })
                .catch(() => {});
        },
        transfer() {
            this.showUserTable = true;
            this.getUserList();
        },
        /** 查询用户列表 */
        getUserList() {
            this.loading = true;
            listUser(this.queryUserParams).then(response => {
                this.userList = response.rows;
                this.userTotal = response.total;
                this.userLoading = false;
            });
        },
        /** 搜索按钮操作 */
        handleUserQuery() {
            this.queryUserParams.page = 1;
            this.getUserList();
        },
        /** 重置按钮操作 */
        resetUserQuery() {
            this.resetForm('queryUserForm');
            this.handleUserQuery();
        },
        // 多选框选中数据
        handleUserSelectionChange(selection) {
            this.selectedRow = selection[0];
        },
        //流程回退
        jumpTo() {
            const taskId = this.setting.taskId;
            const processInstanceId = this.setting.instanceId;
            const newTaskName = '指派工单';
            request({
                url: '/activiti/process/jumpTo',
                method: 'post',
                data: {processInstanceId: processInstanceId, taskId: taskId, comment: ' 为什么回退，给个理由,你们可以实现一个弹窗输入' },
            }).then(response => {
                this.$emit('close', true);
                this.showSuccess('操作成功');
                this.showUserTable = false;
            });
        },
        /** 提交按钮 */
        submitForm() {
            const taskId = this.setting.taskId;
            const instanceId = this.setting.instanceId;
            const selectedRow = this.selectedRow;
            if (!selectedRow) {
                this.showError('请先选择要转办的用户');
                return;
            }
            if (selectedRow.userName === this.$store.state.user.name) {
                this.showError('不能转办给自己');
                return;
            }
            this.$confirm('是否确认转办给' + selectedRow.nickName + '?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            })
                .then(function () {
                    return request({
                        url: '/activiti/process/delegate',
                        method: 'post',
                        params: { taskId: taskId, instanceId: instanceId , delegateToUser: selectedRow.userName },
                    });
                })
                .then(() => {
                    this.$emit('close', true);
                    this.showSuccess('操作成功');
                    this.showUserTable = false;
                });
        },
        showError(msg) {
            this.$message({ showClose: true, message: msg, type: 'error' });
        },
        showSuccess(msg) {
            this.$message({ showClose: true, message: msg, type: 'success' });
        },
    },
};
</script>
<style lang="scss" scoped>
.radio-audit {
    display: block;
    margin-bottom: 20px;
}
::v-deep .el-select--small {
    height: 33.403px;
}
</style>
