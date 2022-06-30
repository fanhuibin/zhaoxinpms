<template>
    <el-dialog
        :title="'收费数据退费'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        lock-scroll
        width="800px"
    >
        <el-row :gutter="15" class="">
            <el-form
                ref="elForm"
                :model="refundForm"
                :rules="rules"
                size="medium"
                label-width="100px"
                label-position="right"
                v-loading="loading"
            >
                <el-col :span="12" style="height:59px">
                    <el-form-item label="商铺编号" prop="resourceName">
                        <el-input :disabled="true" v-model="dataForm.resourceName" placeholder="商铺编号" clearable></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="客户姓名" prop="feeUser">
                        <el-input
                            v-model="dataForm.feeUser"
                            placeholder="请输入"
                            :maxlength="50"
                            clearable
                            :style="{ width: '100%' }"
                            :disabled="true"
                        ></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="收费项目" prop="feeItemId">
                        <el-select
                            v-model="dataForm.feeItemId"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            :multiple="false"
                            :disabled="true"
                        >
                            <el-option
                                v-for="(item, index) in feeItemList"
                                :key="index"
                                :label="item.name"
                                :value="item.id"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" style="height:59px">
                    <el-form-item label="收款时间" prop="payTime">
                        <el-input v-model="dataForm.payTime" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" style="height:59px">
                    <el-form-item label="收款金额" prop="receivable">
                        <el-input v-model="dataForm.receivable" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" style="height:59px">
                    <el-form-item label="已退款金额" prop="refundAmount">
                        <el-input v-model="dataForm.refundAmount" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="退款方式" prop="payMethod" :style="{ height: '33px' }">
                        <el-select v-model="refundForm.payMethod" placeholder="请选择退款方式" clearable :style="{ width: '100%' }" :multiple="false">
                            <el-option
                                v-for="(item, index) in payMethod"
                                :key="index"
                                :label="item.name"
                                :value="item.code"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" style="height:59px">
                    <el-form-item label="退款金额" prop="currentRefundAmount"> 
                        <el-input v-model="refundForm.currentRefundAmount" placeholder="请输入" clearable :style="{ width: '100%' }" maxlength="10" show-word-limit></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="退款说明" prop="refundComment">
                        <el-input  type="textarea"
                            :autosize="{ minRows: 4, maxRows: 4 }" v-model="refundForm.refundComment" placeholder="请输入" clearable :style="{ width: '100%' }" maxlength="100" show-word-limit></el-input>
                    </el-form-item>
                </el-col>
            </el-form>
        </el-row>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确 定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import request from '@/utils/request';
import { listPaymentMethod } from '@/api/payment/paymentMethod';

export default {
    components: {},
    props: [],
    data() {
        return {
            loading: false,
            visible: false,
            payMethod: [],
            dataForm: {
                resourceId: undefined,
                resourceName: undefined,
                feeUser: undefined,
                feeItemId: undefined,
                chargingItemName: undefined,
                endDate: undefined,
                beginDate: undefined,
                deadline: undefined,
                payTime: '',
                refundAmount: undefined,
                receivable: undefined,
            },
            refundForm: {
                billId: undefined,
                currentRefundAmount: 0,
                payMethod:'',
                refundComment: '',
            },
            feeItemList: [],
            rules: {
                currentRefundAmount: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d*)|(0{1}))(\.\d{1,2})?$/,
                        message: '请输入正确的数字,最多两位小数',
                        trigger: 'blur',
                    },
                ],
                refundComment: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                payMethod: [
                    {
                        required: true,
                        message: '请选择退款方式',
                        trigger: 'blur',
                    },
                ],
            },
        };
    },
    computed: {},
    watch: {},
    created() {},
    mounted() {},
    methods: {
        getpayMethod() {
            listPaymentMethod({client:1}).then(res => {
                this.payMethod = res.data.list;
            });
        },
        init(id, feeItemList) {
            this.dataForm.id = id || 0;
            this.refundForm.billId = id;
            this.feeItemList = feeItemList;
            this.visible = true;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
                if (this.dataForm.id) {
                    this.loading = true;
                    this.getpayMethod();
                    request({
                        url: '/payment/PaymentBill/' + this.dataForm.id,
                        method: 'get',
                    }).then(res => {
                        this.dataForm = res.data;
                        this.loading = false;
                    });
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    request({
                        url: '/payment/PaymentBillPay/refundBill',
                        method: 'POST',
                        data: this.refundForm,
                    }).then(res => {
                        this.$message({
                            message: res.msg,
                            type: 'success',
                            duration: 1000,
                            onClose: () => {
                                this.visible = false;
                                this.$emit('refresh', true);
                            },
                        });
                    });
                }
            });
        },
    },
};
</script>
