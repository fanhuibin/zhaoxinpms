<template>
    <el-dialog title="缴费单支付" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll width="800px">
        <el-form ref="elForm" :model="dataForm" :rules="rules" size="medium" label-width="100px" label-position="right" v-loading="loading">
            <el-row :gutter="15" class="">
                <el-col :span="12">
                    <el-form-item label="资源名" prop="resourceName" :style="{ height: '33px' }">
                        <el-input v-model="dataForm.resourceName" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="!!isDetail"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item label="收费项名" prop="chargingItemName" :style="{ height: '33px' }">
                        <el-input
                            v-model="dataForm.chargingItemName"
                            placeholder="请输入"
                            clearable
                            :style="{ width: '100%' }"
                            :disabled="!!isDetail"
                        ></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item label="费用开始时间" prop="beginDate" :style="{ height: '33px' }">
                        <el-input v-model="dataForm.beginDate" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="!!isDetail"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item label="缴费周期" prop="period" :style="{ height: '33px' }">
                        <el-input v-model="dataForm.period" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="!!isDetail">
                            <template slot="append">月</template>
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="应收" prop="receivable" :style="{ height: '33px' }">
                        <el-input
                            v-model="dataForm.receivable"
                            placeholder="请输入"
                            clearable
                            :style="{ width: '100%' }"
                            class="red-input"
                            :disabled="!!isDetail"
                        >
                            <template slot="append">元</template>
                        </el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="15" class="">
                <el-col :span="12">
                    <el-form-item label="实收" prop="receipts" :style="{ height: '33px' }">
                        <el-input v-model="dataForm.receipts" placeholder="请输入" clearable :style="{ width: '100%' }">
                            <template slot="append">元</template>
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="支付方式" prop="payMethod" :style="{ height: '33px' }">
                        <el-select v-model="dataForm.payMethod" placeholder="请选择支付方式" clearable :style="{ width: '100%' }" :multiple="false">
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
                <el-col :span="24">
                    <el-form-item label="备注" prop="comment">
                        <el-input
                            v-model="dataForm.comment"
                            placeholder="请输入备注"
                            :style="{ width: '100%' }"
                            true
                            type="textarea"
                            :autosize="{ minRows: 4, maxRows: 4 }"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定收费</el-button>
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
            isDetail: false,
            payMethod: [],
            dataForm: {
                resourceName: undefined,
                chargingItemName: undefined,
                beginDate: undefined,
                endDate: undefined,
                period: undefined,
                receivable: undefined,
                receipts: undefined,
                comment: '',
            },
            rules: {
                payMethod: [
                    {
                        required: true,
                        message: '请选择支付方式',
                        trigger: 'blur',
                    },
                ],
                receipts: [
                    {
                        required: true,
                        message: '请输入实收',
                        trigger: 'blur',
                    },
                ],
                comment: [
                    {
                        max: 100,
                        message: '最多能输入100个字符',
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
        init(id, isDetail) {
            this.dataForm.id = id || 0;
            this.visible = true;
            this.isDetail = isDetail || false;
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
                        this.dataForm.receipts = this.dataForm.receivable;
                        this.loading = false;
                    });
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    var postData = {
                        payMethod: this.dataForm.payMethod,
                        comment: this.dataForm.comment,
                        paymentBills: [this.dataForm],
                    };
                    request({
                        url: `/payment/PaymentBill/payBill`,
                        method: 'post',
                        data: postData,
                    }).then(res => {
                        this.$message({
                            message: res.msg,
                            type: 'success',
                            duration: 1000,
                            onClose: () => {
                                (this.visible = false), this.$emit('refresh', true);
                            },
                        });
                    });
                }
            });
        },
    },
};
</script>
<style lang="scss" scoped>
.red-input {
    color: red;
    >>> .el-input__inner {
        color: red !important;
    }
}
</style>
