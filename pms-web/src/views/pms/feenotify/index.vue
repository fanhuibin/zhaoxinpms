<template>
    <div style="box-sizing: border-box; overflow-x: hidden; height: 100%">
        <el-row :gutter="20" style="height: 100%; display: flex">
            <!--楼栋楼层数据-->
            <el-col :span="5" :xs="24" style="background-color: white">
                <div class="head-container" style="padding: 20px">
                    <el-tree
                        :data="buildings"
                        :props="defaultProps"
                        :expand-on-click-node="false"
                        ref="tree"
                        default-expand-all
                        @node-click="handleNodeClick"
                    />
                </div>
            </el-col>
            <el-col :span="19" :xs="24">
                <div class="Jcommon-layout">
                    <div class="Jcommon-layout-center">
                        <el-row class="Jcommon-search-box" :gutter="16">
                            <el-form @submit.native.prevent>
                                <el-col :span="6">
                                    <el-form-item label="编号">
                                        <HouseInput v-model="query.resourceName" />
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-form-item label="收费项目">
                                        <el-select v-model="query.feeItemId" placeholder="请选择" clearable>
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
                                    <el-form-item>
                                        <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
                                        <el-button type="primary" icon="el-icon-download" @click="download()">下载</el-button>
                                        <el-button type="primary" icon="el-icon-printer" @click="print()">打印</el-button>
                                        <el-button icon="el-icon-refresh-right" @click="reset()">重置</el-button>
                                    </el-form-item>
                                </el-col>
                            </el-form>
                        </el-row>
                        <div class="Jcommon-layout-main Jflex-main">
                            <JTable v-loading="listLoading" :data="list">
                                <el-table-column prop="resourceName" label="编号" align="left" width="100" />
                                <el-table-column prop="feeItemName" label="收费项名" align="left" width="150" />
                                <el-table-column prop="beginDate" label="开始计费日期" align="left" width="150" />
                                <el-table-column prop="endDate" label="结束计费日期" align="left" width="150" />
                                <el-table-column prop="deadline" label="缴费限期" align="left" />
                                <el-table-column prop="payStateName" label="状态" align="left" />
                                <el-table-column prop="total" label="金额(元)" align="right" header-align="center" />
                                <el-table-column prop="lateFee" label="滞纳金(元)" align="right" header-align="center" />
                            </JTable>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
        <billNotifyPrint ref="Print" />
    </div>
</template>

<script>
import request from '@/utils/request';
import HouseInput from '@/components/HouseInput';
import { selectBuilding } from '@/api/pms/building';
import billNotifyPrint from '@/components/printTemplate/billNotifyPrint';

export default {
    components: { HouseInput, billNotifyPrint },
    data() {
        return {
            showAll: false,
            query: {
                feeItemId: undefined,
                resourceName: undefined,
            },
            list: [],
            listLoading: true,
            blockOptions: [],
            feeItemList: [],
            buildings: [],
            defaultProps: {
                children: 'children',
                label: 'label',
            },
        };
    },
    computed: {},
    created() {
        //初始化building
        selectBuilding().then(res => {
            this.buildings = res.data;
        });
        this.initData();
        this.getFeeItemList();
    },
    methods: {
        getFeeItemList() {
            request({
                url: `/baseconfig/ConfigFeeItem/select`,
                method: 'get',
                data: { type: 'house' },
            }).then(res => {
                this.feeItemList = res.data;
            });
        },
        // 节点单击事件
        handleNodeClick(data) {
            this.query.resourceName = data.fullCode;
            this.initData();
        },
        initData() {
            this.listLoading = true;
            let query = {
                ...this.query,
            };
            request({
                url: `/payment/PaymentBillNotify/unpaiedAndPayingList`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data;
                this.listLoading = false;
            });
        },
        search() {
            this.initData();
        },
        reset() {
            for (let key in this.query) {
                this.query[key] = undefined;
            }
            this.initData();
        },
        download() {
            var resourceName = this.query.resourceName ? this.query.resourceName : '';
            var feeItemId = this.query.feeItemId ? this.query.feeItemId : '';
            const url = process.env.VUE_APP_BASE_API + '/payment/PaymentBillNotify/download?resourceName=' + resourceName + '&feeItemId=' + feeItemId;
            this.$download.customDownload(url);
        },
        print() {
            this.$nextTick(() => {
                var resourceName = this.query.resourceName ? this.query.resourceName : '';
                var feeItemId = this.query.feeItemId ? this.query.feeItemId : '';
                this.$refs.Print.print(resourceName, feeItemId);
            });
        },
    },
};
</script>
