<template>
    <transition name="el-zoom-in-center">
        <div class="Jpreview-main flow-form-main nohead">
            <div class="btns">
                <el-button @click="goBack()">返 回</el-button>
            </div>
            <el-tabs class="Jel_tabs" style="padding: 0 10px">
                <el-tab-pane label="业主信息">
                    <div class="app-container">
                        <el-row :gutter="20">
                            <el-col :span="6" :xs="24">
                                <el-card class="box-card">
                                    <div slot="header" class="clearfix">
                                        <span>业主信息</span>
                                    </div>
                                    <div>
                                        <div class="text-center">
                                            <userAvatar :user="user" />
                                        </div>
                                        <ul class="list-group list-group-striped">
                                            <li class="list-group-item">
                                                <svg-icon icon-class="user" />
                                                业主姓名
                                                <div class="pull-right">{{ user.userName }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <svg-icon icon-class="phone" />
                                                手机号码
                                                <div class="pull-right">{{ user.phonenumber }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <i class="icon-item el-icon-postcard" style="font-size: 20px; margin-right: 10px" />
                                                身份证号
                                                <div class="pull-right">{{ user.idcard }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <svg-icon icon-class="email" />
                                                用户邮箱
                                                <div class="pull-right">{{ user.email }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <svg-icon icon-class="date" />
                                                创建日期
                                                <div class="pull-right">{{ parseTime(user.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
                                            </li>
                                        </ul>
                                    </div>
                                </el-card>
                            </el-col>
                            <el-col :span="18" :xs="24">
                                <el-card>
                                    <div slot="header" class="clearfix">
                                        <span>基本资料</span>
                                    </div>
                                    <el-tabs v-model="activeTab">
                                        <el-tab-pane label="商铺信息" name="houseInfo">
                                            <el-table v-loading="loading" :data="currentContracts">
                                                <el-table-column prop="block" label="商铺编号" align="left">
                                                    <template slot-scope="scope">{{ scope.row.resourceName }}</template>
                                                </el-table-column>
                                                <el-table-column label="类型" prop="state" algin="left">
                                                    <template slot-scope="scope">
                                                        <el-tag v-if="scope.row.contractType === 'selled'" type="success">拥有</el-tag>
                                                        <el-tag v-if="scope.row.contractType === 'rented'">在租</el-tag>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column label="开始时间" prop="beginDate" algin="left">
                                                    <template slot-scope="scope">
                                                        {{ scope.row.beginDate }}
                                                    </template>
                                                </el-table-column>
                                                <el-table-column label="结束时间" prop="endDate" algin="left">
                                                    <template slot-scope="scope">
                                                        {{ scope.row.endDate }}
                                                    </template>
                                                </el-table-column>
                                            </el-table>
                                        </el-tab-pane>
                                        <el-tab-pane label="历史商铺信息" name="houseHistory">
                                            <el-table v-loading="loading" :data="historyContracts">
                                                <el-table-column prop="block" label="商铺编号" align="left">
                                                    <template slot-scope="scope">{{ scope.row.resourceName }}</template>
                                                </el-table-column>
                                                <el-table-column label="类型" prop="state" algin="left">
                                                    <template slot-scope="scope">
                                                        <el-tag v-if="scope.row.contractType === 'selled'" type="success">拥有</el-tag>
                                                        <el-tag v-if="scope.row.contractType === 'rented'">在租</el-tag>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column label="开始时间" prop="beginDate" algin="left">
                                                    <template slot-scope="scope">
                                                        {{ scope.row.beginDate }}
                                                    </template>
                                                </el-table-column>
                                                <el-table-column label="结束时间" prop="deleteTime" algin="left">
                                                    <template slot-scope="scope">
                                                        {{ scope.row.deleteTime }}
                                                    </template>
                                                </el-table-column>
                                            </el-table>
                                        </el-tab-pane>
                                        <el-tab-pane label="待缴费信息" name="fee">
                                            <el-table
                                                v-loading="loading"
                                                :data="unpaiedBills"
                                                ref="showTable"
                                            >
                                                <el-table-column prop="feeItemName" label="收费项名称" align="left"></el-table-column>
                                                <el-table-column prop="beginDate" label="起收日期" align="left" />
                                                <el-table-column prop="endDate" label="到期日期" align="left" />
                                                <el-table-column prop="deadline" label="缴费限期" align="left" />
                                                <el-table-column prop="price" label="单价" align="left" />
                                                <el-table-column label="数量" prop="num" algin="left" />
                                                <el-table-column label="金额" prop="total" algin="left" />
                                            </el-table>
                                        </el-tab-pane>
                                        <el-tab-pane label="历史缴费信息" name="feeHistory">
                                            <el-table
                                                v-loading="loading"
                                                :data="paiedBills"
                                                ref="showTable"
                                            >
                                                <el-table-column prop="feeItemName" label="收费项名称" align="left"></el-table-column>
                                                <el-table-column prop="beginDate" label="起收日期" align="left" />
                                                <el-table-column prop="endDate" label="到期日期" align="left" />
                                                <el-table-column prop="receivable" label="实收金额" align="left" />
                                                <el-table-column label="缴费时间" prop="payTime" algin="left" />
                                                <el-table-column prop="refundAmount" label="退款金额" align="left" />
                                            </el-table>
                                        </el-tab-pane>
                                    </el-tabs>
                                </el-card>
                            </el-col>
                        </el-row>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </transition>
</template>

<script>
import userAvatar from './userAvatar';
import { getOwnerUserDetail } from '@/api/owner/ownerUser';

export default {
    name: 'Profile',
    components: { userAvatar },
    data() {
        return {
            user: {},
            activeTab: 'houseInfo',
            loading: false,
            currentContracts: [],
            historyContracts: [],
            unpaiedBills: [],
            histroyBills: [],
        };
    },
    created() {},
    methods: {
        init(ownerUser) {
            this.user = ownerUser;
            /** 查询业主信息列表 */
            this.loading = true;
            getOwnerUserDetail(this.user.id).then(response => {
                this.currentContracts = response.data.currentContracts;
                this.historyContracts = response.data.historyContracts;
                this.unpaiedBills = response.data.unpaiedBills;
                this.paiedBills = response.data.paiedBills;
                this.loading = false;
            });
        },
        goBack(isRefresh) {
            this.$emit('close', isRefresh);
        },
    },
};
</script>
