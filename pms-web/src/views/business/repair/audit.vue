<template>
    <div class="flow-form">
        <el-form v-if="formShow" ref="dataForm" :model="dataForm" :rules="dataRule" label-width="120px" :disabled="false">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="流程标题" prop="flowTitle">
                        <el-input v-model="dataForm.flowTitle" placeholder="流程标题" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="分类" prop="applyCategory">
                        <el-select v-model="dataForm.applyCategory" placeholder="请选择回访结果" clearable size="small" :disabled="true">
                            <el-option v-for="dict in dict.type.repair_category" :key="dict.value" :label="dict.label" :value="dict.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="报修人员" prop="applyName">
                        <el-input v-model="dataForm.applyName" placeholder="报修人员姓名" readonly :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="报修电话" prop="applyPhone">
                        <el-input v-model="dataForm.applyPhone" placeholder="报修电话" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="报修时间" prop="applyTime">
                        <el-date-picker
                            v-model="dataForm.applyTime"
                            type="date"
                            placeholder="报修时间"
                            format="yyyy-MM-dd HH:mm"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            :editable="false"
                            readonly
                            :disabled="true"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item label="预约时间" prop="appointmenTime">
                        <el-date-picker
                            v-model="dataForm.applyTime"
                            type="date"
                            placeholder="预约时间"
                            format="yyyy-MM-dd HH:mm"
                            :editable="false"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            readonly
                            :disabled="true"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="报修图片" prop="applyImg">
                        <imageUpload v-model="dataForm.applyImg" :disabled="true" :isShowTip="false" />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="报修内容说明" prop="applyContent">
                        <el-input
                            v-model="dataForm.applyContent"
                            placeholder="报修内容说明"
                            type="textarea"
                            :rows="3"
                            :disabled="true"
                            style="width: 45%"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <!--报修确认功能表单-->
                <el-col :span="12" v-if="!isDisabled('repairUser')">
                    <el-form-item label="维修人员" prop="repairUser">
                        <org-select
                            ref="userSelect"
                            buttonType="button"
                            v-model="selectedUser"
                            title="选择用户"
                            type="user"
                            @change="onUserChange"
                            class="mb-10"
                            v-if="!isDisabled('repairUser')"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="canShow('repairUserName')">
                    <el-form-item label="维修人员" prop="repairUserName">
                        <el-input v-model="dataForm.repairUserName" placeholder="维修人员姓名" readonly :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12" v-if="canShow('repairState')">
                    <el-form-item label="维修状态" prop="repairState">
                        <el-radio-group v-model="dataForm.repairState" size="medium" :disabled="isDisabled('repairState')">
                            <el-radio label="repairing">问题已确认，等待维修</el-radio>
                            <el-radio label="repaired">问题已确认、已处理</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
                <!--
                <el-col :span="24" v-if="canShow('unconfirmed')">
                    <el-form-item label="维修备注" prop="confirmContent">
                        <el-input
                            v-model="dataForm.confirmContent"
                            placeholder="维修备注"
                            type="textarea"
                            :rows="3"
                            :disabled="isDisabled('unconfirmed')"
                        ></el-input>
                    </el-form-item>
                </el-col>
                -->
            </el-row>
            <el-row>
                <!--维修处理表单-->
                <el-col :span="12" v-if="canShow('repairMaterialsFee')">
                    <el-form-item label="维修材料费" prop="repairMaterialsFee">
                        <el-input
                            v-model="dataForm.repairMaterialsFee"
                            placeholder="维修材料费"
                            type="number"
                            :disabled="isDisabled('repairMaterialsFee')"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="canShow('repairServiceFee')">
                    <el-form-item label="维修服务费" prop="repairServiceFee">
                        <el-input
                            v-model="dataForm.repairServiceFee"
                            placeholder="维修服务费"
                            type="number"
                            :disabled="isDisabled('repairServiceFee')"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24" v-if="canShow('repairContent')">
                    <el-form-item label="维修备注" prop="repairContent">
                        <el-input
                            v-model="dataForm.repairContent"
                            placeholder="维修备注"
                            type="textarea"
                            :rows="3"
                            :disabled="isDisabled('repairContent')"
                            style="width: 45%"
                        ></el-input>
                    </el-form-item>
                </el-col>

                <!--结果确认、回访表单-->
                <el-col :span="12" v-if="canShow('returnResult')">
                    <el-form-item label="回访结果" prop="returnResult">
                        <el-select v-model="dataForm.returnResult" placeholder="请选择回访结果" clearable size="small" :disabled="isDisabled('returnResult')">
                            <el-option v-for="dict in dict.type.return_result" :key="dict.value" :label="dict.label" :value="dict.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="24" v-if="canShow('returnRemark')">
                    <el-form-item label="回访意见" prop="returnRemark">
                        <el-input
                            type="textarea"
                            :rows="3"
                            v-model="dataForm.returnRemark"
                            placeholder="请输入回访意见"
                            clearable
                            size="small"
                            :disabled="isDisabled('returnRemark')"
                            style="width: 45%"
                        />
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
import { getRepairByInstanceId, updateRepair } from '@/api/business/repair';
import { getFormOperates, getRead, getWrite } from '@/api/business/formOperate';
import OrgSelect from '@/components/workflow/FormControls/OrgSelect';
import request from '@/utils/request';
export default {
    dicts: ['repair_category', 'return_state', 'return_result', 'repair_state', 'flow_client'],
    name: 'PaymentApply',
    components: { OrgSelect },
    props: ['conf'],
    data() {
        return {
            dataForm: {},
            selectedUser: [],
            dataRule: {},
            formOperates: [],
            formShow: false,
        };
    },
    created() {
        this.initData();
    },
    computed: {
        canShow: function () {
            return function (state) {
                if (this.conf.isSelf) {
                    return true;
                }

                return getRead(this.formOperates, state);
            };
        },
        isDisabled() {
            return function (state) {
                //非审核状态全部禁用
                if (!this.conf.isAudit) {
                    return true;
                }

                if (state === 'repairContent' || state === 'repairMaterialsFee' || state === 'repairServiceFee') {
                    if (this.dataForm.repairState === 'repaired' && getWrite(this.formOperates, 'repairState') && getWrite(this.formOperates, state)) {
                        return false;
                    } else if ( !getWrite(this.formOperates, 'repairState') && getWrite(this.formOperates, state)){
                        return false;
                    } else {
                        return true;
                    }
                }

                return !getWrite(this.formOperates, state);
            };
        },
    },
    methods: {
        initData() {
            let p1 = getRepairByInstanceId(this.conf.instanceId);

            let p2 = new Promise((resolve, reject) => {
                resolve({ data: { formOperates: [] } });
            });
            if (!this.conf.isSelf) {
                p2 = getFormOperates(this.conf.instanceId, this.conf.taskId);
            }

            Promise.all([p1, p2]).then(values => {
                let res1 = values[0];
                if (res1.data.state == 'unconfirmed' && this.conf.isAudit) {
                    res1.data.repairState = 'repairing';
                }
                this.dataForm = res1.data;

                let res2 = values[1];
                this.formOperates = res2.data.formOperates;
                this.formShow = true;
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
                            this.dataForm.repairUser = this.selectedUser.map(t => t.id).join(',');
                            this.dataForm.repairUserName = this.selectedUser.map(t => t.name).join(',');
                        }
                        if (this.dataForm.repairUser == null) {
                            this.$modal.msgError('请选择维修人员');
                            return;
                        }

                        updateRepair(this.dataForm).then(response => {
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
