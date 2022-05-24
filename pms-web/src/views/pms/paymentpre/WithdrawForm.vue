<template>
    <el-dialog :title="'退还'" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll width="600px">
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
                        <HouseInput  v-model="dataForm.resourceName"/>
                        <el-button type="primary" @click="searchHouse()">搜索</el-button>
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
                    <el-form-item label="退还方式" prop="payType">
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
                    <el-form-item label="退还时间" prop="operateTime">
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
        <div class="box">
            <el-tabs v-model="activeName">
                <el-tab-pane label="剩余预存款" name="account">
                    <el-table :data="accountList" size="mini">
                        <el-table-column type="index" width="50" label="序号" align="center" />
                        <el-table-column prop="feeItemName" label="收费项" />
                        <el-table-column prop="amt" label="账户金额" />
                        <el-table-column prop="changeMoney" label="退还金额">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.changeMoney" placeholder="请输入" :maxlength="50" clearable :style="{ width: '100%' }"></el-input>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="dataFormSubmit()" v-if="!isDetail">确 定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import request from '@/utils/request';
import { listPaymentMethod } from '@/api/payment/paymentMethod';
import HouseInput from '@/components/HouseInput';

export default {
    components: {HouseInput},
    props: [],
    data() {
        return {
            loading: false,
            visible: false,
            isDetail: false,
            radio: 'house',
            accountList: [],
            activeName: 'account',
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

                    request({
                        url: '/payment/PaymentPreAccount/accounts/' + this.dataForm.resourceId + '/canUse',
                        method: 'get',
                    }).then(res => {
                        for (var i = 0; i < res.data.length; i++) {
                            res.data[i].changeMoney = '0';
                        }
                        this.accountList = res.data;
                    });
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
        init(id, blockOptions, feeItemOptions, isDetail) {
            this.dataForm.id = id || 0;
            this.accountList = [];
            this.visible = true;
            this.isDetail = isDetail || false;
            this.blockOptions = blockOptions;
            this.feeItemIdOptions = feeItemOptions;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
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
                this.dataForm.refundItems = this.accountList;
                if (valid) {
                    request({
                        url: `/payment/PaymentPre/refund`,
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
                }
            });
        },
    },
};
</script>
