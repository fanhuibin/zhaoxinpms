<template>
    <el-dialog
        :title="'常规收费'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        width="900px"
        height="80%"
        lock-scroll
    >
        <div class="Jflex-main" style="overflow: hidden; height: 100%; padding-bottom: 18px">
            <JTable v-loading="listLoading" :data="fee.paymentBills" :hasNO="false" :hasC="false">
                <el-table-column prop="feeItemName" label="收费项名" align="left" width="150" />
                <el-table-column prop="beginDate" label="费用开始时间" align="left" width="100" />
                <el-table-column prop="endDate" label="费用结束时间" align="left" width="100" />
                <el-table-column prop="total" label="金额" align="left" />
                <el-table-column prop="discount" label="优惠金额" align="left" width="150">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.discount" placeholder="请输入" :maxlength="50" clearable :style="{ width: '100%' }" @blur="calc()" />
                    </template>
                </el-table-column>
                <el-table-column prop="lateFee" label="滞纳金" align="left" />
                <el-table-column prop="receivable" label="应收金额" align="left" />
            </JTable>
            <div class="dataBoard">
                <el-form @submit.native.prevent size="medium" :style="'width:100%'" label-width="70px" label-position="left">
                    <el-row :gutter="16" :style="'width:100%'">
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        滞纳金：
                                        <span style="color: #409eff">{{ fee.lateFeeMoney }}</span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        优惠金额：
                                        <span style="color: #409eff">{{ fee.discountMoney }}</span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        实际应收：
                                        <span style="color: #f56c6c">{{ fee.receivableMoney }}</span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="16" :style="'width:100%'">
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        可用预存款：
                                        <span style="color: #409eff">{{ this.fee.canUsePrePayMoney }}</span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        预存款付款：
                                        <span style="color: #409eff">{{ this.fee.prePayMoney }}</span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        找零结存：
                                        <span style="color: #409eff">{{ this.fee.preSaveMoney }}</span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        找零金额：
                                        <span style="color: #f56c6c">{{ this.fee.changeMoney }}</span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="16" :style="'width:100%;'">
                        <el-col :span="8">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        收款金额：
                                        <span style="color: #f56c6c">
                                            <el-input placeholder="请输入" clearable :style="{ width: '50%' }" v-model="fee.payMoney" @blur="calc()"></el-input>
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="16">
                            <div class="dataBoard-body">
                                <div class="text">
                                    <p class="num">
                                        付款方式：
                                        <span>
                                            <el-select
                                                v-model="fee.payMethod"
                                                placeholder="请选择付款方式"
                                                clearable
                                                :style="{ width: '50%' }"
                                                :multiple="false"
                                            >
                                                <el-option
                                                    v-for="(item, index) in payMethod"
                                                    :key="index"
                                                    :label="item.name"
                                                    :value="item.code"
                                                    :disabled="item.disabled"
                                                ></el-option>
                                            </el-select>
                                        </span>
                                    </p>
                                </div>
                                <el-checkbox v-model="fee.usePrePay" style="padding-left: 20px" @input="calc()">使用预付款</el-checkbox>
                                <el-checkbox v-model="fee.usePreSave" @input="calc()">找零结存</el-checkbox>
                            </div>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="handleSubmit()">确 定</el-button>
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
            fee: {
                itemTotalMoney: '0',
                lateFeeMoney: '0',
                discountMoney: '0',
                receivableMoney: '0',
                payMoney: '0',
                usePrePay: false,
                useProSave: false,
                prePayMoney: '0',
                changeMoney: '0',
                preSaveMoney: '0',
                canUsePrePayMoney: '0',
                paymentBills: [],
                payMethod: undefined,
            },
            payMethod: [],
            loading: false,
            visible: false,
            listLoading: true,
        };
    },
    computed: {},
    watch: {},
    created() {
        this.getpayMethod();
    },
    mounted() {},
    methods: {
        init(payList) {
            this.visible = true;
            (this.fee = {
                itemTotalMoney: '0',
                lateFeeMoney: '0',
                discountMoney: '0',
                receivableMoney: '0',
                payMoney: '0',
                usePrePay: false,
                useProSave: false,
                prePayMoney: '0',
                changeMoney: '0',
                preSaveMoney: '0',
                canUsePrePayMoney: '0',
                paymentBills: [],
                payMethod: undefined,
            }),
                (this.fee.paymentBills = payList);
            this.$nextTick(() => {
                if (payList.length > 0) {
                    request({
                        url: `/payment/PaymentBillPay/payCalc`,
                        method: 'post',
                        data: this.fee,
                    }).then(res => {
                        this.fee = res.data;
                        this.listLoading = false;
                    });
                }
            });
        },
        calc() {
            if (this.fee.paymentBills.length > 0) {
                this.listLoading = true;
                request({
                    url: `/payment/PaymentBillPay/payCalc`,
                    method: 'post',
                    data: this.fee,
                }).then(res => {
                    this.fee = res.data;
                    this.listLoading = false;
                });
            }
        },
        handleSubmit() {
            this.$confirm('确认完成付款操作吗?', '提示', {
                type: 'warning',
            })
                .then(() => {
                    request({
                        url: `/payment/PaymentBillPay/payBill`,
                        method: 'post',
                        data: this.fee,
                    }).then(res => {
                        this.$message({
                            type: 'success',
                            message: '支付成功',
                            onClose: () => {
                                this.visible = false;
                                this.$emit('refresh', res.data.id, this.fee.paymentBills[0].resourceName);
                            },
                        });
                    });
                })
                .catch(() => {});
        },
        getpayMethod() {
            listPaymentMethod({client:1}).then(res => {
                this.payMethod = res.data.list;
            });
        },
    },
};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
    height: 70%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    .el-dialog__body {
        height: 100%;
        flex: 1;
        overflow: hidden !important;
    }
}

.dataBoard {
    overflow: hidden;
    padding-top: 10px;
    border: 1px solid #ebeef5;
    .dataBoard-body {
        padding-left: 10px;
        height: 100%;
        display: flex;
        align-items: center;
        border-right: 1px solid #ebeef5;
        .text {
            display: inline-block;
            .num {
                font-size: 20px;
                line-height: 36px;
                width: 238px;
                margin: 0;
            }
            .name {
                font-size: 14px;
                color: #666;
            }
        }
    }
}
</style>
