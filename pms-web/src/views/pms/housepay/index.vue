<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="商铺编号">
                            <HouseInput  v-model="query.resourceName" type="contract"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <el-form @submit.native.prevent size="mini" :style="'width:100%'">
                        <el-row :gutter="16" :style="'width:100%'">
                            <el-col :span="4">
                                <el-form-item label="编号" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ house.name }}</label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="商铺状态" style="margin-bottom: 0px">
                                    <label style="color: #1890ff" v-if="contract.contractType == 'rented'">出租</label>
                                    <label style="color: #1890ff" v-if="contract.contractType == 'selled'">出售</label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="到期日期" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ contract.endDate }}</label>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="16" :style="'width:100%'">
                            <el-col :span="4">
                                <el-form-item label="占地面积" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ house.buildingSquare }}</label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="使用面积" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ house.useSquare }}</label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="行业" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ contract.userTrade }}</label>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="16" :style="'width:100%'">
                            <el-col :span="4">
                                <el-form-item label="客户姓名" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ contract.userName }}</label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="联系方式" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ contract.userPhone }}</label>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="入住时间" style="margin-bottom: 0px">
                                    <label style="color: #1890ff">{{ contract.beginDate }}</label>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <div class="Jflex-main Jcommon-layout-main">
                    <div style="padding-left: 10px">
                        <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 100%">
                            <el-tab-pane label="常规收费" name="first"></el-tab-pane>
                            <el-tab-pane label="临时收费" name="second"></el-tab-pane>
                            <el-tab-pane label="收取押金" name="third"></el-tab-pane>
                            <el-tab-pane label="预存款" name="four"></el-tab-pane>
                        </el-tabs>
                    </div>
                    <div style="flex: 1; overflow: hidden">
                        <PayBillList v-show="activeName == 'first'" ref="PayBillList" @refresh="refresh" />
                        <PayTempList v-show="activeName == 'second'" ref="PayTempList" @refresh="refresh" />
                        <PayDepositList v-show="activeName == 'third'" ref="PayDepositList" @refresh="refresh" />
                        <PayPreList v-show="activeName == 'four'" ref="PayPreList" @refresh="refresh" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';

import Print from '../print';
import PayBillList from './payBillList';
import PayDepositList from './payDepositList';
import PayPreList from './payPreList';
import PayTempList from './payTempList';
import { mapGetters, mapState } from 'vuex';
import HouseInput from '@/components/HouseInput';

export default {
    components: { Print, PayBillList, PayDepositList, PayPreList, PayTempList, HouseInput },
    data() {
        return {
            showAll: false,
            activeName: 'first',
            query: {
                resourceName: undefined,
            },
            house: {
                name: undefined,
                buildingSquare: undefined,
                useSquare: undefined,
            },
            contract: {
                state: undefined,
                contractType: undefined,
                beginDate: undefined,
                endDate: undefined,
                userName: undefined,
                userPhone: undefined,
                userTrade: undefined,
            },
            defaultProps: {
                children: 'children',
                label: 'fullName',
            },
        };
    },
    computed: {
        ...mapGetters(['device', 'userInfo']),
    },
    created() {
        this.initData();
    },
    methods: {
        initData() {},
        search() {
            if (this.query.resourceName != undefined && this.query.resourceName != '') {
                request({
                    url: `/payment/PaymentContract/resourceName/` + this.query.resourceName,
                    method: 'get',
                })
                    .then(res => {
                        this.house = res.data.house;
                        this.contract = res.data.contract;

                        this.$nextTick(() => {
                            this.updateList(this.query.resourceName);
                        });
                    })
                    .catch(() => {
                        this.house = {
                            name: undefined,
                            buildingSquare: undefined,
                            useSquare: undefined,
                        };
                        this.contract = {
                            state: undefined,
                            contractType: undefined,
                            beginDate: undefined,
                            endDate: undefined,
                            userName: undefined,
                            userPhone: undefined,
                            userTrade: undefined,
                        };
                    });
            } else {
                (this.house = {
                    name: undefined,
                    buildingSquare: undefined,
                    useSquare: undefined,
                }),
                    (this.contract = {
                        state: undefined,
                        contractType: undefined,
                        beginDate: undefined,
                        endDate: undefined,
                        userName: undefined,
                        userPhone: undefined,
                        userTrade: undefined,
                    }),
                    this.$nextTick(() => {
                        this.$refs.PayBillList.setResourceName(this.query.resourceName);
                        this.$refs.PayDepositList.setResourceName(this.query.resourceName);
                        this.$refs.PayPreList.setResourceName(this.query.resourceName);
                        this.$refs.PayTempList.setResourceName(this.query.resourceName);
                    });
            }
        },
        updateList(resourceName) {
            this.$refs.PayBillList.setResourceName(resourceName);
            this.$refs.PayBillList.search();
            this.$refs.PayDepositList.setResourceName(resourceName);
            this.$refs.PayDepositList.initData();
            this.$refs.PayPreList.setResourceName(resourceName);
            this.$refs.PayPreList.initData();
            this.$refs.PayTempList.setResourceName(resourceName);
            this.$refs.PayTempList.initData();
        },
        refresh(isrRefresh) {
            this.formVisible = false;
            if (isrRefresh) this.reset();
        },
        reset() {
            for (let key in this.query) {
                this.query[key] = undefined;
            }
            this.listQuery = {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            };
            this.initData();
        },
        handleClick(tab, event) {},
    },
};
</script>
