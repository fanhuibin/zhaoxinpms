<template>
    <el-dialog
        :title="!dataForm.id ? '新建' : isDetail ? '详情' : '编辑'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        lock-scroll
        width="600px"
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
                <el-col :span="24">
                    <el-form-item label="类型" prop="type">
                        <el-select v-model="dataForm.type" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false">
                            <el-option
                                v-for="(item, index) in typeOptions"
                                :key="index"
                                :label="item.fullName"
                                :value="item.id"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="收费项目名" prop="name">
                        <el-input v-model="dataForm.name" placeholder="请输入" :maxlength="50" clearable :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house'">
                    <el-form-item label="单价" prop="price">
                        <el-input v-model="dataForm.price" placeholder="没有固定单价的填写1" :maxlength="7" clearable :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house'">
                    <el-form-item label="数量" prop="numType">
                        <el-select v-model="dataForm.numType" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false">
                            <el-option
                                v-for="(item, index) in numTypeOptions"
                                :key="index"
                                :label="item.fullName"
                                :value="item.id"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house'">
                    <el-form-item label="计算公式" prop="formula">
                        <el-select v-model="dataForm.formula" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false">
                            <el-option
                                v-for="(item, index) in formulaOptions"
                                :key="index"
                                :label="item.fullName"
                                :value="item.id"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house' && dataForm.formula === 'expression'">
                    <el-form-item label="自定义" prop="formulaExpression">
                        <el-input
                            v-model="dataForm.formulaExpression"
                            placeholder="请参考使用文档"
                            :style="{ width: '100%' }"
                            true
                            type="textarea"
                            :autosize="{ minRows: 4, maxRows: 4 }"
                        ></el-input>
                        <el-popover placement="top" width="300" trigger="click">
                            <p>公式使用说明</p>
                            <div>
                                <p>
                                    <strong>公式变量：</strong>
                                    单价 数量
                                </p>
                                <p>
                                    <strong>语法逻辑处理函数：</strong>
                                    if elsif else return
                                </p>
                                <p>
                                    <strong>以设置梯度电价为例：</strong>
                                    230度以内0.5283元/度，231-400度 0.5783元/度，400度以上 0.8783元/度
                                </p>
                                <p><strong>示例公式如下：</strong></p>
                                <p>
                                    if(数量&lt;=230){
                                    <br />
                                    &nbsp; &nbsp; &nbsp; &nbsp; return 0.5283*数量;
                                    <br />
                                    }elsif(数量&lt;=400){
                                    <br />
                                    &nbsp; &nbsp; &nbsp; &nbsp; return 0.5783*数量;
                                    <br />
                                    }else{
                                    <br />
                                    &nbsp; &nbsp; &nbsp; &nbsp; return 0.8783*数量;
                                    <br />
                                    }
                                </p>
                            </div>
                            <el-link type="primary" slot="reference">点击查看使用说明</el-link>
                        </el-popover>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house'">
                    <el-form-item label="计算周期" prop="period">
                        <el-select v-model="dataForm.period" placeholder="几个月收费一次" clearable :style="{ width: '100%' }" :multiple="false">
                            <el-option
                                v-for="(item, index) in periodOptions"
                                :key="index"
                                :label="item.fullName"
                                :value="item.id"
                                :disabled="item.disabled"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house'">
                    <el-form-item label="滞纳金" prop="lateFeeEnable">
                        <el-radio-group v-model="dataForm.lateFeeEnable" size="medium">
                            <el-radio-button v-for="(item, index) in lateFeeEnableOptions" :key="index" :label="item.id" :disabled="item.disabled">
                                {{ item.fullName }}
                            </el-radio-button>
                        </el-radio-group>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house' && dataForm.lateFeeEnable === '1'">
                    <el-form-item label="滞纳金比例" prop="lateFeeRate">
                        <el-input v-model="dataForm.lateFeeRate" placeholder="请输入滞纳金百分比" clearable :style="{ width: '100%' }">
                            <template slot="append">%</template>
                        </el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24" v-if="dataForm.type === 'house' && dataForm.lateFeeEnable === '1'">
                    <el-form-item label="滞纳金天数" prop="lateFeeDays">
                        <el-input v-model="dataForm.lateFeeDays" placeholder="请输入滞纳金天数" clearable :style="{ width: '100%' }"></el-input>
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

export default {
    components: {},
    props: [],
    data() {
        return {
            loading: false,
            visible: false,
            isDetail: false,
            dataForm: {
                type: '',
                name: undefined,
                price: undefined,
                numType: '',
                formula: '',
                formulaExpression: undefined,
                period: '',
                lateFeeEnable: '0',
                lateFeeRate: 0.6,
                lateFeeDays: 60,
            },
            rules: {
                type: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                name: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                price: [
                    {
                        required: true,
                        message: '没有固定单价的填写0',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0|[1-9]\d*$/,
                        message: '请输入正确的单价',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,5})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多6位数字',
                        trigger: 'blur'
                    },
                ],
                numType: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                formula: [
                    {
                        required: true,
                        message: '请选择',
                        trigger: 'blur',
                    },
                ],
                formulaExpression: [
                    {
                        required: true,
                        message: '请输入自定义公式',
                        trigger: 'blur',
                    },
                ],
                period: [
                    {
                        required: true,
                        message: '几个月收费一次',
                        trigger: 'blur',
                    },
                ],
                lateFeeEnable: [
                    {
                        required: true,
                        message: '请至少选择一个',
                        trigger: 'blur',
                    },
                ],
                lateFeeRate: [
                    {
                        required: true,
                        message: '请输入滞纳金百分比',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                lateFeeDays: [
                    {
                        required: true,
                        message: '请至少选择一个',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[0-9]+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
            },
            typeOptions: [
                { fullName: '常规收费项（商铺）', id: 'house' },
                { fullName: '临时收费项', id: 'temp' },
                { fullName: '押金类收费项', id: 'deposit' },
            ],
            numTypeOptions: [
                { fullName: '按户数收费', id: 'flat' },
                { fullName: '按人口数收费', id: 'people' },
                { fullName: '按楼层收费', id: 'floor' },
                { fullName: '按占地面积收费', id: 'building_area' },
                { fullName: '按使用面积收费', id: 'use_area' },
                { fullName: '按走表数量', id: 'meter' },
                { fullName: '按租金收费', id: 'rent_fee' },
            ],
            formulaOptions: [
                { fullName: '单价*数量', id: 'base' },
                { fullName: '自定义', id: 'expression' },
            ],
            periodOptions: [
                { fullName: '1个月', id: '1' },
                { fullName: '2个月', id: '2' },
                { fullName: '3个月', id: '3' },
                { fullName: '4个月', id: '4' },
                { fullName: '6个月', id: '6' },
                { fullName: '12个月', id: '12' },
            ],
            lateFeeEnableOptions: [
                { fullName: '不适用', id: '0' },
                { fullName: '适用', id: '1' },
            ],
        };
    },
    computed: {},
    watch: {},
    created() {},
    mounted() {},
    methods: {
        init(id, isDetail) {
            this.dataForm.id = id || 0;
            this.visible = true;
            this.isDetail = isDetail || false;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
                if (this.dataForm.id) {
                    this.loading = true;
                    request({
                        url: '/baseconfig/ConfigFeeItem/' + this.dataForm.id,
                        method: 'get',
                    }).then(res => {
                        this.dataForm = res.data;
                        this.loading = false;
                    });
                }else{
                    this.dataForm = {
                        type: '',
                        name: undefined,
                        price: undefined,
                        numType: '',
                        formula: '',
                        formulaExpression: undefined,
                        period: '',
                        lateFeeEnable: '0',
                        lateFeeRate: 0.6,
                        lateFeeDays: 60,
                    }
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    if (!this.dataForm.id) {
                        request({
                            url: `/baseconfig/ConfigFeeItem`,
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
                            url: '/baseconfig/ConfigFeeItem/' + this.dataForm.id,
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
