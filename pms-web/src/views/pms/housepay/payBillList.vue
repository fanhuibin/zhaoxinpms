<template>
    <div class="Jflex-main" style="height: 100%">
        <div class="Jcommon-head">
            <div>
                <el-button type="primary" icon="el-icon-plus" @click="pay()">收费</el-button>
                <el-button type="primary" icon="el-icon-plus" @click="openHistory()">历史信息</el-button>
            </div>
        </div>
        <JTable v-loading="listLoading" :data="list" @selection-change="handleSelectionChange" :hasNO="false" :hasC="true" ref="showTable">
            <el-table-column prop="feeItemName" label="收费项名称" align="left"></el-table-column>
            <el-table-column prop="beginDate" label="起收日期" align="left" />
            <el-table-column prop="endDate" label="到期日期" align="left" />
            <el-table-column prop="deadline" label="缴费限期" align="left" />
            <el-table-column prop="price" label="单价" align="left" />
            <el-table-column label="数量" prop="num" algin="left" />
            <el-table-column label="金额" prop="total" algin="left" />
        </JTable>
        <PayBillHistory v-if="historyVisible" ref="PayBillHistory" @print="handlePrint" />
        <PayBill v-if="payBillVisible" ref="PayBill" @refresh="handlePrint" />
        <billPrint ref="Print" />
    </div>
</template>

<script>
import request from '@/utils/request';
import PayBillHistory from './payBillHistory';
import PayBill from './payBill';
import billPrint from '@/components/printTemplate/billPrint';

export default {
    components: { PayBillHistory, PayBill, billPrint },
    data() {
        return {
            listLoading: false,
            total: 0,
            multipleSelection: [],
            list: [],
            resourceName: undefined,
            historyVisible: false,
            payBillVisible: false,
            printId: '',
        };
    },
    computed: {},
    created() {},
    methods: {
        setResourceName(resourceName) {
            this.resourceName = resourceName;
        },
        pay() {
            if (this.multipleSelection.length > 0) {
                this.payBillVisible = true;
                this.$nextTick(() => {
                    this.$refs.PayBill.init(this.multipleSelection);
                });
            } else {
                this.$message({
                    type: 'warning',
                    message: '请选择要缴费的数据',
                    duration: 1000,
                });
            }
        },
        search() {
            this.listLoading = true;
            request({
                url: `/payment/PaymentBill/unpaied/` + this.resourceName,
                method: 'get',
            }).then(res => {
                this.list = res.data.list;
                this.listLoading = false;
            });
        },
        openHistory() {
            if (this.resourceName) {
                this.historyVisible = true;
                this.$nextTick(() => {
                    this.$refs.PayBillHistory.init(this.resourceName);
                });
            } else {
                this.$message({
                    type: 'warning',
                    message: '编号不能为空',
                    duration: 1000,
                });
            }
        },
        updateParent(){
            this.$parent.search();
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handlePrint(id, resourceName) {
            this.$nextTick(() => {
                //刷新数据
                this.search();
                this.$refs.Print.print(id);
            });
        },
    },
};
</script>
