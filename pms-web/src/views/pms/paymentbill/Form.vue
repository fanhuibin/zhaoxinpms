<template>
    <el-dialog
        :title="!dataForm.id ? '新建' : isDetail ? '详情' : '编辑'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        lock-scroll
        width="800px"
    >
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
                <el-col :span="12" style="height:59px">
                    <el-form-item label="编号" prop="resourceName">
                        
                        <HouseInput  v-model="dataForm.resourceName" :customStyle="{ width: '65%' }"/>
                        <el-button type="primary" @click="searchHouse()">搜索</el-button>
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
                            @input="changePrice"
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
                    <el-form-item label="单价" prop="price">
                        <el-input v-model="dataForm.price" placeholder="请输入" clearable :style="{ width: '100%' }" @input="changeMoney()"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="开始日期" prop="beginDate">
                        <el-date-picker
                            v-model="dataForm.beginDate"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="结束日期" prop="endDate">
                        <el-date-picker
                            v-model="dataForm.endDate"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span="24" >
                    <el-form-item label="缴费限期" prop="deadline">
                        <el-date-picker
                            v-model="dataForm.deadline"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '40%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="上次读数" prop="lastIndex">
                        <el-input v-model="dataForm.lastIndex" placeholder="请输入" clearable :style="{ width: '100%' }" @input="changeIndex()"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="本次读数" prop="currentIndex">
                        <el-input v-model="dataForm.currentIndex" placeholder="请输入" clearable :style="{ width: '100%' }" @input="changeIndex()"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="倍率" prop="multiple">
                        <el-input v-model="dataForm.multiple" placeholder="请输入" clearable :style="{ width: '100%' }" @input="changeIndex()"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="损耗" prop="loss">
                        <el-input v-model="dataForm.loss" placeholder="请输入" clearable :style="{ width: '100%' }" @input="changeIndex()"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="数量" prop="num">
                        <el-input v-model="dataForm.num" placeholder="请输入" clearable :style="{ width: '100%' }" @input="changeMoney()"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12" style="height:59px">
                    <el-form-item label="金额" prop="total">
                        <el-input v-model="dataForm.total" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
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
import HouseInput from '@/components/HouseInput';

export default {
    components: {HouseInput},
    props: [],
    data() {
        return {
            loading: false,
            visible: false,
            isDetail: false,
            dataForm: {
                resourceId: undefined,
                resourceName: undefined,
                feeUser: undefined,
                feeItemId: undefined,
                chargingItemName: undefined,
                endDate: undefined,
                beginDate: undefined,
                deadline: undefined,
                lastIndex: '0',
                currentIndex: '0',
                multiple: '1',
                loss: '0',
                price: '0',
                num: '0',
                lateFee: '0',
                discount: '0',
                receivable: '0',
                total: '0',
            },
            feeItemList: [],
            rules: {
                resourceName: [
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
                resourceId: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                feeItemId: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                chargingItemName: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                endDate: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                beginDate: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                deadline: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                lastIndex: [
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的数字,最多两位小数',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur'
                    },
                ],
                currentIndex: [
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的数字,最多两位小数',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur'
                    },
                ],
                multiple: [
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的数字,最多两位小数',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,1})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多2位数字',
                        trigger: 'blur'
                    },
                ],
                loss: [
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的数字,最多两位小数',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur'
                    },
                ],
                price: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的金额',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,5})|(0{1}))(\.\d{0,4})?$/,
                        message: '小数点前最多6位数字',
                        trigger: 'blur'
                    },
                ],
                num: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur'
                    },
                ],
                lateFee: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的金额',
                        trigger: 'blur',
                    },
                ],
                discount: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的金额',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur'
                    },
                ],
                total: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的金额',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur'
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
        init(id, isDetail, feeItemList) {
            this.dataForm.id = id || 0;
            this.feeItemList = feeItemList;
            this.visible = true;
            this.isDetail = isDetail || false;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
                if (this.dataForm.id) {
                    this.loading = true;
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
        changeIndex() {
            if (this.dataForm.lastIndex != '' && this.dataForm.currentIndex != '') {
                var num = (parseFloat(this.dataForm.currentIndex) - parseFloat(this.dataForm.lastIndex)).toFixed(2);
                if (this.dataForm.multiple != '') {
                    num = (parseFloat(num) * parseFloat(this.dataForm.multiple)).toFixed(2);
                }
                if (this.dataForm.loss != '') {
                    num = (parseFloat(num) + parseFloat(this.dataForm.loss)).toFixed(2);
                }
                this.dataForm.num = num;
                if (this.dataForm.price != '') {
                    this.dataForm.total = (parseFloat(num) * parseFloat(this.dataForm.price)).toFixed(2);
                }
            }
        },
        changeMoney() {
            if (this.dataForm.price != '' && this.dataForm.num != '') {
                this.dataForm.total = (parseFloat(this.dataForm.num) * parseFloat(this.dataForm.price)).toFixed(2);
            }
        },
        changePrice() {
            for (var i = 0; i < this.feeItemList.length; i++) {
                if (this.feeItemList[i].id == this.dataForm.feeItemId) {
                    this.dataForm.price = this.feeItemList[i].price;
                }
            }
        },
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
        // 表单提交
        dataFormSubmit() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    if (!this.dataForm.id) {
                        request({
                            url: `/payment/PaymentBillCreate`,
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
                            url: '/payment/PaymentBill/' + this.dataForm.id,
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
