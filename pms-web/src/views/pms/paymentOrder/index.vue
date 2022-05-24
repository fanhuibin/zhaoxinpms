<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="支付方式代码" prop="wayCode">
                            <el-input v-model="queryParams.wayCode" placeholder="请输入支付方式代码" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    
                    <el-col :span="6">
                        <el-form-item label="支付状态" prop="state">
                            <el-input
                                v-model="queryParams.state"
                                placeholder="请输入支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭"
                                clearable
                                size="small"
                                @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="用户id" prop="userId">
                            <el-input v-model="queryParams.userId" placeholder="请输入缴费单订单号" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="退款状态" prop="refundState">
                            <el-input
                                v-model="queryParams.refundState"
                                placeholder="请输入退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款"
                                clearable
                                size="small"
                                @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </div>

                <JTable v-loading="loading" :data="paymentOrderList" @selection-change="handleSelectionChange">
                    <el-table-column label="订单号" align="center" prop="id" />
                    <el-table-column label="支付方式" align="center" prop="wayCode" />
                    <el-table-column label="支付金额" align="center" prop="amount" />
                    <el-table-column
                        label="支付状态"
                        align="center"
                        prop="state"
                    />
                    <el-table-column label="缴费内容" align="center" prop="subject" />
                    <el-table-column label="openId" align="center" prop="openId" />
                    <el-table-column label="退款状态" align="center" prop="refundState" />
                    <el-table-column label="退款金额" align="center" prop="refundAmount" />
                    <el-table-column label="支付成功时间" align="center" prop="successTime" width="160">
                        <template slot-scope="scope">
                            <span>{{ scope.row.successTime }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="来源" align="center" prop="client" />
                    <el-table-column label="创建时间" align="center" prop="createTime" width="160">
                        <template slot-scope="scope">
                            <span>{{ scope.row.createTime }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['payment:paymentOrder:edit']">
                                修改
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.currentPage" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>
    </div>
</template>

<script>
import { listPaymentOrder, getPaymentOrder, delPaymentOrder, addPaymentOrder, updatePaymentOrder, exportPaymentOrder } from '@/api/payment/paymentOrder';

export default {
    name: 'PaymentOrder',
    data() {
        return {
            // 遮罩层
            loading: true,
            // 导出遮罩层
            exportLoading: false,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 物业费支付订单表格数据
            paymentOrderList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                currentPage: 1,
                pageSize: 10,
                wayCode: null,
                state: null,
                openId: null,
                userId: null,
                refundState: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
               
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询物业费支付订单列表 */
        getList() {
            this.loading = true;
            listPaymentOrder(this.queryParams).then(response => {
                this.paymentOrderList = response.data.list;
                this.total = response.data.pagination.total;
                this.loading = false;
            });
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.form = {
                id: null,
                appId: null,
                wayCode: null,
                amount: null,
                state: null,
                subject: null,
                body: null,
                openId: null,
                userId: null,
                refundState: null,
                refundTimes: null,
                refundAmount: null,
                errCode: null,
                errMsg: null,
                expiredTime: null,
                successTime: null,
                client: null,
                logId: null,
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
            };
            this.resetForm('form');
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.currentPage = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.resetForm('queryForm');
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.id);
            this.single = selection.length !== 1;
            this.multiple = !selection.length;
        },
    },
};
</script>
