<template>
    <el-dialog
        :title="'合同撤销'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        lock-scroll
        width="800px"
    >
        <div class="box">
            <el-tabs v-model="activeName">
                <el-tab-pane label="未完成的缴费单" name="unpaiedList">
                    <el-table :data="dataForm.unpaiedList" height="250" size="mini">
                        <el-table-column type="index" width="50" label="序号" align="center" />
                        <el-table-column prop="resourceName" label="商铺名" width="150px" />
                        <el-table-column prop="chargingItemName" label="收费项名" />
                        <el-table-column prop="beginDate" label="费用开始时间" width="150px" />
                        <el-table-column prop="endDate" label="费用结束时间" width="150px" />
                        <el-table-column prop="receivable" label="应收金额">
                            <template slot-scope="scope">{{ scope.row.receivable }}元</template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div class="table-actions">
            <el-link type="danger" style="font-size: 16px">删除客户信息会删除上面的所有未完成的缴费单，请确认后再执行。</el-link>
        </div>

        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="danger" @click="dataFormSubmit()" v-if="!isDetail">确定收回</el-button>
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
            setting: {
                readonly: false,
            },
            activeName: 'unpaiedList',
            loading: false,
            visible: false,
            isDetail: false,
            dataForm: {
                unpaiedList: [],
            },
        };
    },
    computed: {},
    watch: {},
    created() {},
    mounted() {},
    methods: {
        init(id) {
            this.dataForm.resourceId = id || 0;
            this.dataForm.unpaiedList = [];
            this.visible = true;
            this.isDetail = false;
            this.$nextTick(() => {
                if (this.dataForm.resourceId) {
                    this.loading = true;
                    request({
                        url: '/payment/PaymentBill/unpaied/' + this.dataForm.resourceId,
                        method: 'get',
                    }).then(res => {
                        this.dataForm.unpaiedList = res.data.list;
                        this.loading = false;
                    });
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            request({
                url: `/payment/PaymentContract/` + this.dataForm.resourceId,
                method: 'DELETE',
            }).then(res => {
                this.$message({
                    type: 'success',
                    message: res.msg,
                    onClose: () => {
                        (this.visible = false), this.$emit('refresh', true);
                    },
                });
            });
        },
    },
};
</script>
