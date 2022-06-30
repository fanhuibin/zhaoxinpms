<template>
    <el-dialog title="押金退还" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll width="600px">
        <el-row :gutter="15" class="">
            <el-form
                ref="elForm"
                :model="dataForm"
                :rules="rules"
                size="medium"
                label-width="100px"
                label-position="right"
                :disabled="!!isDetail"
                v-loading="loading"
            >
                <el-col :span="24">
                    <el-form-item label="商铺编号" prop="resourceName">
                        <el-input v-model="dataForm.resourceName" placeholder="商铺编号" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
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

                <el-col :span="24">
                    <el-form-item label="收费项目" prop="feeItemId">
                        <el-select v-model="dataForm.feeItemId" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false" :disabled="true">
                            <el-option
                                v-for="(item, index) in feeItemIdOptions"
                                :key="index"
                                :label="item.name"
                                :value="item.id"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="付款方式" prop="payType">
                        <el-select v-model="dataForm.payType" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false" :disabled="true">
                            <el-option
                                v-for="(item, index) in payTypeOptions"
                                :key="index"
                                :label="item.name"
                                :value="item.code"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="收费金额" prop="amt">
                        <el-input v-model="dataForm.amt" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="收款时间" prop="operateTime">
                        <el-date-picker
                            v-model="dataForm.operateTime"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                            :disabled="true"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="退款时间" prop="refundTime">
                        <el-date-picker
                            v-model="dataForm.refundTime"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="退款方式" prop="refundType">
                        <el-select v-model="dataForm.refundType" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false">
                            <el-option
                                v-for="(item, index) in payTypeOptions"
                                :key="index"
                                :label="item.name"
                                :value="item.id"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="备注" prop="remark">
                        <el-input
                            v-model="dataForm.remark"
                            placeholder="请输入"
                            :maxlength="200"
                            :style="{ width: '100%' }"
                            true
                            type="textarea"
                            :autosize="{ minRows: 4, maxRows: 4 }"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-form>
        </el-row>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="dataFormSubmit()" v-if="!isDetail">确 定</el-button>
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
            dataForm: {
                block: '',
                resourceId: undefined,
                resourceName: undefined,
                feeUser: undefined,
                feeItemId: '',
                payType: '',
                refundType: '',
                amt: undefined,
                remark: undefined,
                operateTime: undefined,
                refundTime: undefined,
                remark: undefined,
            },
            rules: {
                block: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                feeUser: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                feeItemId: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                payType: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                amt: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/,
                        message: '请输入正确的金额',
                        trigger: 'blur',
                    },
                ],
                operateTime: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                refundTime: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                refundType: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                remark: [
                    {
                        min: 0,
                        max: 50,
                        message: '备注最大能输入50个字符',
                        trigger: 'blur',
                    },
                ],
            },
            blockOptions: [],
            feeItemIdOptions: [],
            payTypeOptions: [],
        };
    },
    computed: {},
    watch: {},
    created() {
        this.getpayTypeOptions();
    },
    mounted() {},
    methods: {
        searchHouse() {
            request({
                url: '/payment/PaymentContract/resourceName/' + this.dataForm.resourceName,
                method: 'get',
            }).then(res => {
                this.dataForm.feeUser = res.data.userName;
                this.dataForm.resourceId = res.data.resourceId;
                this.dataForm.block = res.data.blockCode;
            });
        },
        getpayTypeOptions() {
            listPaymentMethod({client:1}).then(res => {
                this.payTypeOptions = res.data.list;
            });
        },
        init(id, blockOptions, feeItemOptions, isDetail) {
            this.dataForm.id = id || 0;
            this.visible = true;
            this.isDetail = isDetail || false;
            this.blockOptions = blockOptions;
            this.feeItemIdOptions = feeItemOptions;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
                if (this.dataForm.id) {
                    this.loading = true;
                    request({
                        url: '/payment/PaymentDeposit/' + this.dataForm.id,
                        method: 'get',
                    }).then(res => {
                        res.data.refundTime = new Date().valueOf();
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
                    if (!this.dataForm.id) {
                        request({
                            url: `/payment/PaymentDeposit`,
                            method: 'post',
                            data: this.dataForm,
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
                    } else {
                        request({
                            url: '/payment/PaymentDeposit/' + this.dataForm.id,
                            method: 'PUT',
                            data: this.dataForm,
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
                }
            });
        },
    },
};
</script>
