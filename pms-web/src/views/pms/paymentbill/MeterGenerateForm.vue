<template>
    <el-dialog :title="'自动生成'" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll width="800px">
        <el-steps :active="active" align-center>
            <el-step title="生成规则"></el-step>
            <el-step title="数据确认"></el-step>
        </el-steps>
        <div class="import-main" v-show="active == 1">
            <el-row :gutter="15" class="">
                <el-form
                    ref="elForm"
                    :model="dataForm"
                    :rules="rules"
                    size="medium"
                    label-width="120px"
                    label-position="right"
                    :disabled="!!isDetail"
                    v-loading="loading"
                >
                    <el-col :span="24">
                        <el-form-item label="费用生效时间" prop="pickerVal">
                            <el-date-picker
                                v-model="dataForm.pickerVal"
                                type="daterange"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                value-format="timestamp"
                                clearable
                                :editable="false"
                                :style="{ width: '40%' }"
                            ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="收费项目" prop="feeItemId">
                            <el-select v-model="dataForm.feeItemId" placeholder="请选择" clearable :style="{ width: '100%' }" :multiple="false">
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
                    <el-col :span="12">
                        <el-form-item label="缴费限期" prop="deadline">
                            <el-date-picker
                                v-model="dataForm.deadline"
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
        </div>
        <div class="import-main" v-show="active == 2">
            <JTable :data="list" height="300" :summary-method="getSummaries" show-summary>
                <el-table-column prop="resourceName" label="编号" align="left" width="100" />
                <el-table-column prop="feeItemName" label="收费项名" align="left" width="150" />
                <el-table-column prop="feeUser" label="客户姓名" align="left" width="100" />
                <el-table-column prop="num" label="数量" align="left" />
                <el-table-column prop="price" label="单价" align="left" />
                <el-table-column prop="total" label="金额" align="left" width="120" />
                <el-table-column prop="beginDate" label="费用开始时间" align="left" width="150" />
                <el-table-column prop="endDate" label="费用结束时间" align="left" width="150" />
            </JTable>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button @click="dataFormSubmit()" type="primary" v-if="active == 1" :loading="btnLoading">生成数据</el-button>
            <el-button type="primary" v-if="active == 2" @click="dataFormSubmit()">确 定</el-button>
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
            btnLoading: false,
            areaOptions: [],
            active: 1,
            list: [],
            dataForm: {
                pickerVal: [],
                feeItemId: undefined,
                chargingItemName: undefined,
                endDate: undefined,
                beginDate: undefined,
                deadline: undefined,
            },
            feeItemList: [],
            rules: {
                pickerVal: [
                    {
                        type: 'array',
                        required: true,
                        message: '请选择',
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
            },
        };
    },
    computed: {},
    watch: {},
    created() {
        this.getFeeItemList();
    },
    mounted() {},
    methods: {
        init(id, isDetail, feeItemList) {
            this.dataForm.id = id || 0;
            this.visible = true;
            this.isDetail = isDetail || false;
            this.active = 1;
            this.list = [];
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
        getFeeItemList() {
            request({
                url: `/baseconfig/ConfigFeeItem/select`,
                method: 'get',
                data: { type: 'house', generateType: 'meter' },
            }).then(res => {
                this.feeItemList = res.data;
            });
        },
        getSummaries(param) {
            const { columns, data } = param;
            const sums = [];
            columns.forEach((column, index) => {
                if (index === 0) {
                    sums[index] = '总计';
                    return;
                } else if (index === 6) {
                    const values = data.map(item => Number(item[column.property]));
                    if (!values.every(value => isNaN(value))) {
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);
                            if (!isNaN(value)) {
                                return (parseFloat(prev) + parseFloat(curr)).toFixed(2);
                            } else {
                                return prev;
                            }
                        }, 0);
                        sums[index] += ' 元';
                    } else {
                        sums[index] = '';
                    }
                } else {
                    sums[index] = '';
                }
            });

            return sums;
        },
        // 表单提交
        dataFormSubmit() {
            if (this.active == 1) {
                this.$refs['elForm'].validate(valid => {
                    if (valid) {
                        this.btnLoading = true;
                        if (this.dataForm.pickerVal && this.dataForm.pickerVal.length) {
                            this.dataForm.beginDate = this.dataForm.pickerVal[0];
                            this.dataForm.endDate = this.dataForm.pickerVal[1];
                        } else {
                            this.dataForm.beginDate = '';
                            this.dataForm.endDate = '';
                        }
                        request({
                            url: `/payment/PaymentBillCreate/getMeterData`,
                            method: 'post',
                            data: this.dataForm,
                        })
                            .then(res => {
                                this.list = res.data.list;
                                this.active++;
                                this.btnLoading = false;
                            })
                            .catch(() => {
                                this.btnLoading = false;
                            });
                    }
                });
            } else {
                this.$confirm('请逐条校验数据是否正确，确认之后将不能修改。', '提示', {
                    type: 'warning',
                })
                    .then(() => {
                        this.$refs['elForm'].validate(valid => {
                            if (valid) {
                                this.btnLoading = true;
                                request({
                                    url: `/payment/PaymentBillCreate/createMeterData`,
                                    method: 'post',
                                    data: this.dataForm,
                                })
                                    .then(res => {
                                        this.$message({
                                            message: res.msg,
                                            type: 'success',
                                            duration: 2000,
                                            onClose: () => {
                                                (this.visible = false), this.$emit('refresh', true);
                                            },
                                        });
                                    })
                                    .catch(() => {
                                        this.btnLoading = false;
                                    });
                            }
                        });
                    })
                    .catch(() => {});
            }
        },
    },
};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog__body {
    padding: 20px 40px 2px !important;
}
.import-main {
    margin-top: 20px;
    overflow: hidden;
    position: relative;
}
</style>
