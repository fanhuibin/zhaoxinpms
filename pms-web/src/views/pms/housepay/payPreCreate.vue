<template>
    <el-dialog :title="'预存'" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll width="600px">
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
                    <el-form-item label="编号" prop="resourceName">
                        <el-input v-model="dataForm.resourceName" placeholder="请输入" clearable :style="{ width: '100%' }" :disalbed="true"></el-input>
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
                    <el-form-item label="可用收费项" prop="useFeeItem">
                        <el-radio v-model="dataForm.useFeeItem" label="1">指定收费项</el-radio>
                        <el-radio v-model="dataForm.useFeeItem" label="0">所有收费项</el-radio>
                    </el-form-item>
                </el-col>
                <el-col :span="24" v-if="dataForm.useFeeItem == '1'">
                    <el-form-item label="收费项目" prop="feeItemId">
                        <el-select v-model="dataForm.feeItemId" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false">
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
                        <el-select v-model="dataForm.payType" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false">
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
                        <el-input v-model="dataForm.amt" placeholder="请输入" clearable :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="收费时间" prop="operateTime">
                        <el-date-picker
                            v-model="dataForm.operateTime"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                        ></el-date-picker>
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
            radio: 'house',
            dataForm: {
                type: '',
                useFeeItem: '',
                block: '',
                resourceId: undefined,
                resourceName: undefined,
                feeUser: undefined,
                feeItemId: '',
                payType: '',
                amt: undefined,
                remark: undefined,
                operateTime: new Date().valueOf(),
            },
            rules: {
                resourceName: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                useFeeItem: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
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
                        message: '请输入编号并点击搜索',
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
            })
                .then(res => {
                    this.dataForm.feeUser = res.data.contract.userName;
                    this.dataForm.resourceId = res.data.contract.resourceId;
                    this.dataForm.block = res.data.contract.blockCode;
                })
                .catch(err => {
                    this.dataForm.resourceId = '';
                    this.dataForm.block = '';
                    this.dataForm.feeUser = '';
                });
        },
        getpayTypeOptions() {
            listPaymentMethod({client:1}).then(res => {
                this.payTypeOptions = res.data.list;
            });
        },
        init(id, blockOptions, feeItemOptions, resourceName) {
            this.dataForm.id = id || 0;
            this.visible = true;
            this.dataForm.resourceName = resourceName;
            this.blockOptions = blockOptions;
            this.feeItemIdOptions = feeItemOptions;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
                this.searchHouse();
                if (this.dataForm.id) {
                    this.loading = true;
                    request({
                        url: '/payment/PaymentPre/' + this.dataForm.id,
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
                    if (!this.dataForm.id) {
                        request({
                            url: `/payment/PaymentPre/pay`,
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
                            url: '/payment/PaymentPre/' + this.dataForm.id,
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
