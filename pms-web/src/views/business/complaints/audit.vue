<template>
    <div class="flow-form">
        <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="120px" :disabled="false">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="流程标题" prop="flowTitle">
                        <el-input v-model="dataForm.flowTitle" placeholder="流程标题" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="分类" prop="applyCategory">
                        <el-select v-model="dataForm.applyCategory" placeholder="请选择回访结果" clearable size="small" :disabled="true">
                            <el-option v-for="dict in dict.type.complaints_category" :key="dict.value" :label="dict.label" :value="dict.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="投诉人姓名" prop="applyName">
                        <el-input v-model="dataForm.applyName" placeholder="投诉人姓名" readonly :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="投诉人电话" prop="applyPhone">
                        <el-input v-model="dataForm.applyPhone" placeholder="投诉人电话" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="投诉时间" prop="applyTime">
                        <el-date-picker
                            v-model="dataForm.applyTime"
                            type="date"
                            placeholder="投诉时间"
                            format="yyyy-MM-dd HH:mm"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            :editable="false"
                            readonly
                            :disabled="true"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="投诉图片" prop="applyImg">
                        <imageUpload v-model="dataForm.applyImg" :disabled="true" :isShowTip="false" />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="投诉内容" prop="applyContent">
                        <el-input
                            v-model="dataForm.applyContent"
                            placeholder="投诉内容说明"
                            type="textarea"
                            :rows="3"
                            :disabled="true"
                            style="width: 45%"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="投诉人要求" prop="applyRequirements">
                        <el-input
                            v-model="dataForm.applyRequirements"
                            placeholder="投诉人要求"
                            type="textarea"
                            :rows="3"
                            :disabled="true"
                            style="width: 45%"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <!--投诉确认功能表单-->
                <el-col :span="12" v-if="canShow('apply')">
                    <el-form-item label="选择处理人" prop="assigneeUser">
                        <org-select
                            ref="userSelect"
                            buttonType="button"
                            v-model="selectedUser"
                            title="选择用户"
                            type="user"
                            @change="onUserChange"
                            class="mb-10"
                            v-if="dataForm.state == 'apply' && this.conf.isAudit"
                        />
                        <el-input
                            v-model="dataForm.assigneeUserName"
                            placeholder="处理人姓名"
                            readonly
                            :disabled="true"
                            v-if="canShow('processing')"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <!--维修处理表单-->
                <el-col :span="24" v-if="canShow('processing')">
                    <el-form-item label="处理备注" prop="assigneeContent">
                        <el-input
                            v-model="dataForm.assigneeContent"
                            placeholder="处理备注"
                            type="textarea"
                            :rows="3"
                            :disabled="isDisabled('processing')"
                            style="width: 45%"
                        ></el-input>
                    </el-form-item>
                </el-col>

                <!--结果确认、回访表单-->
                <el-col :span="12" v-if="canShow('score')">
                    <el-form-item label="回访结果" prop="returnResult">
                        <el-select v-model="dataForm.returnResult" placeholder="请选择回访结果" clearable size="small" :disabled="isDisabled('score')">
                            <el-option v-for="dict in dict.type.return_result" :key="dict.value" :label="dict.label" :value="dict.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="24" v-if="canShow('score')">
                    <el-form-item label="回访意见" prop="returnRemark">
                        <el-input
                            type="textarea"
                            :rows="3"
                            v-model="dataForm.returnRemark"
                            placeholder="请输入回访意见"
                            clearable
                            size="small"
                            :disabled="isDisabled('score')"
                            style="width: 45%"
                        />
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
import { getComplaintsByInstanceId, updateComplaints } from '@/api/business/complaints';
import OrgSelect from '@/components/workflow/FormControls/OrgSelect';
export default {
    dicts: ['complaints_category', 'return_state', 'return_result', 'complaints_state', 'flow_client'],
    name: 'PaymentApply',
    components: { OrgSelect },
    props: ['conf'],
    data() {
        return {
            dataForm: {},
            selectedUser: [],
            dataRule: {},
        };
    },
    created() {
        this.initData();
    },
    computed: {
        canShow: function () {
            return function (state) {
                const currentState = this.dataForm.state;
                if (state == 'apply') {
                    return true;
                } else if (state === 'complete') {
                    if (['apply', 'complete', 'unconfirmed', 'processing', 'score'].indexOf(currentState) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (state === 'processing') {
                    if (['complete', 'processing', 'score'].indexOf(currentState) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (state === 'score') {
                    if (['complete', 'score'].indexOf(currentState) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            };
        },
        isDisabled() {
            return function (state) {
                if (!this.conf.isAudit) {
                    return true;
                } else if (this.dataForm.state === state) {
                    return false;
                } else {
                    return true;
                }
            };
        },
    },
    methods: {
        initData() {
            getComplaintsByInstanceId(this.conf.instanceId).then(response => {
                if (response.data.state == 'unconfirmed' && this.conf.isAudit) {
                    response.data.repairState = 'processing';
                }
                this.dataForm = response.data;
            });
        },
        beforeInit() {
            this.getPaymentMethodOptions();
        },
        onUserChange() {},
        dataFormSubmit() {
            this.$refs['dataForm'].validate(valid => {
                if (valid) {
                    this.dataForm.taskId = this.conf.taskId;
                    this.dataForm.taskDefKey = this.conf.taskDefKey;
                    this.dataForm.taskName = this.conf.taskName;
                    if (this.dataForm.id != null) {
                        if (this.selectedUser.length > 0) {
                            this.dataForm.assigneeUser = this.selectedUser.map(t => t.id).join(',');
                            this.dataForm.assigneeUserName = this.selectedUser.map(t => t.name).join(',');
                        }
                        if (this.dataForm.assigneeUser == null) {
                            this.$modal.msgError('请选择处理人员');
                            return;
                        }

                        updateComplaints(this.dataForm).then(response => {
                            this.$modal.msgSuccess('提交成功');
                            this.$emit('close', true); //返回刷新列表数据
                        });
                    }
                }
            });
        },
    },
};
</script>
