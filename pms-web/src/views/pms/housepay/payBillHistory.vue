<template>
    <el-dialog
        :title="'历史数据'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        width="900px"
        height="70%"
        lock-scroll
    >
        <div class="Jflex-main" style="overflow: hidden; height: 100%; padding-bottom: 18px">
            <JTable v-loading="listLoading" :data="list">
                <el-table-column prop="payTime" label="打印" align="left" width="50">
                    <template slot-scope="scope">
                        <el-button type="text" @click="print(scope.row.payLogId, scope.row.resourceName)">打印</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="resourceName" label="编号" align="left" width="100" />
                <el-table-column prop="feeItemName" label="收费项名" align="left" width="150" />
                <el-table-column prop="feeUser" label="客户姓名" align="left" width="100" />
                <el-table-column prop="beginDate" label="费用开始时间" align="left" width="150" />
                <el-table-column prop="endDate" label="费用结束时间" align="left" width="150" />
                <el-table-column prop="total" label="金额" align="left" />
                <el-table-column prop="lateFee" label="滞纳金" align="left" />
                <el-table-column prop="discount" label="优惠金额" align="left" />
                <el-table-column prop="receivable" label="实收金额" align="left" />
                <el-table-column label="付款状态" align="left">
                    <template slot-scope="scope">
                        <el-tag size="small" v-if="scope.row.payLogId !== ''">已付款</el-tag>
                        <el-tag size="small" type="danger" v-if="scope.row.payLogId === ''">未付款</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="payTime" label="缴费时间" align="left" width="150" />
            </JTable>
            <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="search" />
        </div>
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
            feeItemList: [],
            list: [],
            listLoading: true,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: 'pay_time',
            },
            query: {
                resourceName: undefined,
            },
        };
    },
    computed: {},
    watch: {},
    created() {},
    mounted() {},
    methods: {
        init(resourceName) {
            this.visible = true;
            this.query.resourceName = resourceName;
            this.$nextTick(() => {
                if (resourceName) {
                    this.search();
                }
            });
        },
        search() {
            this.listLoading = true;
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/payment/PaymentBill/paied/` + this.query.resourceName,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;
                this.listLoading = false;
            });
        },
        print(payId, resourceName) {
            this.$emit('print', payId, resourceName);
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
</style>
