<template>
    <el-dialog title="合同信息" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll width="600px">
        <el-steps :active="stepActive" align-center>
            <el-step title="步骤 1" description="确认商铺信息"></el-step>
            <el-step title="步骤 2" description="选择业主"></el-step>
            <el-step title="步骤 3" description="设置费用"></el-step>
        </el-steps>
        <el-row :gutter="15" class="stepRow">
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
                <template>
                    <el-col :span="24" v-show="stepActive == 0">
                        <el-form-item label="商业区" prop="area">
                            <el-select
                                v-model="dataForm.blockCode"
                                placeholder="请选择商铺的商业区"
                                clearable
                                :style="{ width: '100%' }"
                                :multiple="false"
                                :disabled="true"
                            >
                                <el-option v-for="(item, index) in areaOptions" :key="index" :label="item.name" :value="item.code" :disabled="true"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-show="stepActive == 0">
                        <el-form-item label="编号" prop="name">
                            <el-input
                                v-model="dataForm.name"
                                placeholder="请输入编号"
                                :maxlength="49"
                                clearable
                                :style="{ width: '100%' }"
                                :disabled="true"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-show="stepActive == 0">
                        <el-form-item label="类别" prop="contractType">
                            <el-select
                                v-model="dataForm.contractType"
                                placeholder="请选择"
                                clearable
                                :style="{ width: '100%' }"
                                :multiple="false"
                                :disabled="this.dataForm.contractId?true:false"
                                @input="getConfigFeeSetting"
                            >
                                <el-option v-for="(item, index) in contractTypeOptions" :key="index" :label="item.fullName" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 0">
                        <el-form-item label="开始时间" prop="beginDate" :style="{ height: '33px' }">
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
                    <el-col :span="24" v-if="this.dataForm.contractType == 'rented'" style="height: 59px" v-show="stepActive == 0">
                        <el-form-item label="出租时间" prop="period" :style="{ height: '33px' }">
                            <el-input v-model="dataForm.period" placeholder="出租时间" clearable :style="{ width: '100%' }">
                                <template slot="append">月</template>
                            </el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24" v-if="this.dataForm.contractType == 'rented'" style="height: 59px" v-show="stepActive == 0">
                        <el-form-item label="租金" prop="rentFee" :style="{ height: '33px' }">
                            <el-input v-model="dataForm.rentFee" placeholder="租金" clearable :style="{ width: '100%' }">
                                <template slot="append">元</template>
                            </el-input>
                        </el-form-item>
                    </el-col>
                </template>
                <template v-if="stepActive >= 1">
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 1">
                        <el-form-item label="客户公司" prop="company">
                            <el-row>
                                <el-col :span="16">
                                    <el-input
                                        v-model="dataForm.company"
                                        placeholder="请输入"
                                        :maxlength="20"
                                        clearable
                                        :style="{ width: '100%' }"
                                        :disabled="true"
                                    ></el-input>
                                </el-col>
                                <el-col :span="8">
                                    <el-button type="primary" @click="selectOwner">选择业主公司</el-button>
                                </el-col>
                            </el-row>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 1">
                        <el-form-item label="联系人姓名" prop="userName">
                            <el-row>
                                <el-col :span="24">
                                    <el-input
                                        v-model="dataForm.userName"
                                        placeholder="请输入"
                                        :maxlength="20"
                                        clearable
                                        :style="{ width: '100%' }"
                                        :disabled="true"
                                    ></el-input>
                                </el-col>
                            </el-row>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 1">
                        <el-form-item label="身份证" prop="userIdcard">
                            <el-input
                                v-model="dataForm.userIdcard"
                                placeholder="请输入"
                                :maxlength="18"
                                clearable
                                :style="{ width: '100%' }"
                                :disabled="true"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 1">
                        <el-form-item label="性别" prop="userGender">
                            <el-select v-model="dataForm.userGender" placeholder="请选择性别" :disabled="true">
                                <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 1">
                        <el-form-item label="联系方式" prop="userPhone">
                            <el-input
                                v-model="dataForm.userPhone"
                                placeholder="请输入"
                                :maxlength="13"
                                clearable
                                :style="{ width: '100%' }"
                                :disabled="true"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 1">
                        <el-form-item label="从事的行业" prop="userTrade">
                           <el-input
                                v-model="dataForm.userTrade"
                                placeholder="请输入"
                                :maxlength="13"
                                clearable
                                :style="{ width: '100%' }"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 59px" v-show="stepActive == 1">
                        <el-form-item label="详细说明" prop="userTradeDetail">
                            <el-input v-model="dataForm.userTradeDetail" :maxlength="150" placeholder="请输入" clearable :style="{ width: '100%' }"></el-input>
                        </el-form-item>
                    </el-col>
                </template>
            </el-form>
        </el-row>
        <div class="box" v-show="stepActive == 2" style="margin: 0 20px">
            <el-tabs v-model="activeName">
                <el-tab-pane label="设置收费项目" name="contractFees">
                    <el-table :data="dataForm.contractFees" size="mini">
                        <el-table-column type="index" width="50" label="序号" align="center" />
                        <el-table-column prop="name" label="收费项名" />
                        <el-table-column prop="price" label="单价" />
                        <!-- 功能未开放 
                        <el-table-column prop="beginDate" label="计费开始时间" width="220px">
                            <template slot-scope="scope">
                                <el-date-picker
                                    v-model="scope.row.beginDate"
                                    placeholder="不选择随合约时间生效"
                                    clearable
                                    :style="{ width: '100%' }"
                                    type="date"
                                    format="yyyy-MM-dd"
                                    value-format="timestamp"
                                ></el-date-picker>
                            </template>
                        </el-table-column>
                        <el-table-column prop="endDate" label="计费结束时间" width="220px">
                            <template slot-scope="scope">
                                <el-date-picker
                                    v-model="scope.row.endDate"
                                    placeholder="不选择随合约生效"
                                    clearable
                                    :style="{ width: '100%' }"
                                    type="date"
                                    format="yyyy-MM-dd"
                                    value-format="timestamp"
                                ></el-date-picker>
                            </template>
                        </el-table-column>-->
                        <el-table-column label="操作" width="50" v-if="!setting.readonly">
                            <template slot-scope="scope">
                                <el-button size="mini" type="text" class="JTable-delBtn" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="table-actions" @click="choice" v-if="!setting.readonly">
                        <el-button type="text" icon="el-icon-plus">新增收费项</el-button>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button style="margin-top: 12px" @click="prev" v-if="stepActive !== 0">上一步</el-button>
            <el-button style="margin-top: 12px" @click="next" v-if="stepActive !== 2">下一步</el-button>
            <el-button type="primary" @click="dataFormSubmit()" v-if="!isDetail && stepActive == 2">确 定</el-button>
        </span>
        <Box v-if="boxVisible" ref="form" @refreshDataList="initData" />
        <SelectOwnerBox v-if="selectOwnerBoxVisible" ref="selectOwner" @select="ownerSelected" />
    </el-dialog>
</template>
<script>
import request from '@/utils/request';
import SelectOwnerBox from '../../owner/ownerUser/SelectBox.vue';
import Box from '../configfeeitem/SelectBox';
export default {
    components: { Box, SelectOwnerBox },
    dicts: ['sys_user_sex'],
    props: [],
    data() {
        return {
            setting: {
                readonly: false,
            },
            stepActive: 0,
            ownerType: '1',
            activeName: 'contractFees',
            loading: false,
            visible: false,
            isDetail: false,
            boxVisible: false,
            selectOwnerBoxVisible: false,
            dataForm: {
                resourceId: '',
                ownerId: '',
                contractFees: [],
                resourceType: 'house',
                blockCode: undefined,
                period: undefined,
                name: undefined,
                contractId: undefined,
                contractType: undefined,
                userName: undefined,
                userIdcard: undefined,
                userGender: '',
                userPhone: undefined,
                beginDate: undefined,
                endDate: undefined,
                userTrade: '',
                userTradeDetail: undefined,
                description: undefined,
                rentFee: undefined,
            },
            areaOptions: [],
            rules: {
                contractType: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                userName: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                userIdcard: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                        message: '请输入正确的身份证号码',
                        trigger: 'blur',
                    },
                ],
                userGender: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                userPhone: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^1[3456789]\d{9}$|^0\d{2,3}-?\d{7,8}$/,
                        message: '请输入正确的联系方式',
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
                period: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                rentFee: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d*)|(0{1}))(\.\d{0,2})?$/,
                        message: '请输入正确的数字，最多两位小数',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur',
                    },
                ],
                userTrade: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
            },
            usersexOptions: [],
            usertradeOptions: [],
            contractTypeOptions: [
                { id: 'rented', fullName: '出租' },
                { id: 'selled', fullName: '出售' },
            ],
        };
    },
    computed: {},
    watch: {},
    created() {
        this.getareaOptions();
    },
    mounted() {},
    methods: {
        choice() {
            this.boxVisible = true;
            this.$nextTick(() => {
                this.$refs.form.init();
            });
        },
        selectOwner() {
            this.selectOwnerBoxVisible = true;
            this.$nextTick(() => {
                this.$refs.selectOwner.init();
            });
        },
        ownerSelected(data) {
            this.dataForm.company = data.company;
            this.dataForm.userName = data.userName;
            this.dataForm.userPhone = data.phonenumber;
            this.dataForm.userIdcard = data.idcard;
            this.dataForm.userGender = data.sex;
            this.dataForm.ownerId = data.id;
        },
        next() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    this.stepActive++;
                }
            });
        },
        prev() {
            this.stepActive--;
        },
        handleDel(index, row) {
            this.dataForm.contractFees.splice(index, 1);
        },
        initData(list) {
            for (let i = 0; i < list.length; i++) {
                const e = list[i];
                let item = {
                    feeItemId: e.id,
                    name: e.name,
                    period: e.period,
                    beginDate: this.dataForm.beginDate,
                    endDate: null,
                    price: e.price,
                    formula: e.formula,
                };
                this.dataForm.contractFees.push(item);
            }
        },
        getareaOptions() {
            request({
                url: `/baseconfig/ConfigHouseBlock/selectList`,
                method: 'GET',
            }).then(res => {
                this.areaOptions = res.data;
            });
        },
        getConfigFeeSetting() {
            request({
                url: `/baseconfig/ConfigFeeSetting`,
                method: 'get',
                data: { type: this.dataForm.contractType },
            }).then(res => {
                this.dataForm.contractFees = [];
                for (let i = 0; i < res.data.length; i++) {
                    const e = res.data[i];
                    let item = {
                        id: '',
                        feeItemId: e.feeItemId,
                        name: e.feeItemName,
                        beginDate: this.dataForm.beginDate,
                        endDate: null,
                        price: e.price,
                    };
                    this.dataForm.contractFees.push(item);
                }
            });
        },
        init(company, resourceId, block, name, contractId, rentFee, state, isDetail) {
            this.stepActive = 0;
            this.visible = true;
            this.isDetail = isDetail || false;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
                this.dataForm.resourceId = resourceId || 0;
                this.dataForm.blockCode = block;
                this.dataForm.name = name;
                this.dataForm.contractId = contractId;
                this.dataForm.contractType = state;
                this.dataForm.rentFee = rentFee;
                this.dataForm.contractFees = [];
                this.dataForm.userTrade = '';
                this.dataForm.company = '';
                this.dataForm.userPhone = '';
                this.dataForm.userGender = '';
                this.dataForm.userName = '';
                this.dataForm.userIdcard = '';
                this.dataForm.beginDate = '';
                this.dataForm.period = '';
                this.dataForm.userTradeDetail = '';
                if (this.dataForm.contractId) {
                    this.loading = true;
                    request({
                        url: '/payment/PaymentContract/' + this.dataForm.contractId,
                        method: 'get',
                    }).then(res => {
                        this.dataForm.company = res.data.company;
                        this.dataForm.userTrade = res.data.userTrade;
                        this.dataForm.userPhone = res.data.userPhone;
                        this.dataForm.userGender = res.data.userGender;
                        this.dataForm.userName = res.data.userName;
                        this.dataForm.userIdcard = res.data.userIdcard;
                        this.dataForm.contractType = res.data.contractType;
                        this.dataForm.beginDate = res.data.beginDate;
                        this.dataForm.period = res.data.period;
                        this.dataForm.userTradeDetail = res.data.userTradeDetail;
                        this.dataForm.contractFees = res.data.contractFees;
                        this.dataForm.ownerId = res.data.ownerId;
                        this.loading = false;
                    });
                } else {
                    this.getConfigFeeSetting();
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    if (!this.dataForm.contractId) {
                        request({
                            url: `/payment/PaymentContract`,
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
                            url: '/payment/PaymentContract/' + this.dataForm.contractId,
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
<style lang="scss" scoped>
.stepRow {
    margin-top: 20px;
    margin-right: 60px !important;
}
</style>
