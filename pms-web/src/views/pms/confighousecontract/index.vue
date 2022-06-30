<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="编号">
                            <HouseInput v-model="query.name" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="使用状态">
                            <el-select v-model="query.state" placeholder="请选择" clearable>
                                <el-option
                                    v-for="(item, index) in stateOptions"
                                    :key="index"
                                    :label="item.fullName"
                                    :value="item.id"
                                    :disabled="item.disabled"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="客户姓名">
                            <el-input v-model="query.userName" placeholder="" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
                            <el-button icon="el-icon-refresh-right" @click="reset()">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <div>
                        <el-button type="primary" icon="el-icon-setting" @click="showRentedConfig()">默认收费项(出租)</el-button>
                        <el-button type="primary" icon="el-icon-setting" @click="showSelledConfig()">默认收费项(出售)</el-button>
                    </div>
                    <div class="Jcommon-head-right">
                        <el-tooltip effect="dark" content="刷新" placement="top">
                            <el-link icon="icon-ym icon-ym-Refresh Jcommon-head-icon" :underline="false" @click="reset()" />
                        </el-tooltip>
                        
                    </div>
                </div>
                <JTable v-loading="listLoading" :data="list">
                    <el-table-column prop="block" label="商铺编号" align="left">
                        <template slot-scope="scope">{{ scope.row.name }}</template>
                    </el-table-column>
                    <el-table-column label="使用状态" prop="state" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.state | dynamicText(stateOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="公司名" prop="company" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.company }}
                        </template>
                    </el-table-column>
                    <el-table-column label="姓名" prop="userName" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.userName }}
                        </template>
                    </el-table-column>
                    <el-table-column label="电话" prop="userPhone" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.userPhone }}
                        </template>
                    </el-table-column>
                    <el-table-column label="租金" prop="rentFee" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.rentFee }}
                        </template>
                    </el-table-column>
                    <el-table-column label="行业" prop="userTrade" algin="left">
                        <template slot-scope="scope">
                            {{ scope.row.userTrade }}
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

                    <el-table-column label="操作" fixed="right" width="200">
                        <template slot-scope="scope">
                            <el-button
                                type="text"
                                @click="selled('', scope.row.resourceId, scope.row.block, scope.row.name, scope.row.contractId, scope.row.rentFee)"
                                :disabled="scope.row.state !== 'empty'"
                            >
                                发起合同
                            </el-button>
                            
                            <el-button
                                type="text"
                                :disabled="scope.row.state === 'empty'"
                                @click="addOrUpdateHandle(scope.row.company, scope.row.resourceId, scope.row.block, scope.row.name, scope.row.contractId, scope.row.rentFee)"
                            >
                                编辑合同
                            </el-button>
                            <el-button type="text" @click="cancelContract(scope.row.resourceId)" :disabled="scope.row.state === 'empty'">撤销合同</el-button>
                            
                        </template>
                    </el-table-column>
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
        <FeeConfig v-if="FeeConfigVisible" ref="FeeConfig" @refresh="refresh" />
        <Contract v-if="ContractVisible" ref="ContractForm" @refresh="refresh" />
        <CancelContractForm v-if="cancelContractFormVisible" ref="CancelContractForm" @refresh="refresh" />
    </div>
</template>

<script>
import request from '@/utils/request';
import Contract from './ContractForm';
import CancelContractForm from './CancelContractForm';
import FeeConfig from './FeeConfig';
import HouseInput from '@/components/HouseInput';

export default {
    components: { Contract, CancelContractForm, FeeConfig, HouseInput },
    data() {
        return {
            query: {
                name: undefined,
                area: undefined,
                code: undefined,
                state: undefined,
            },
            defaultProps: {
                children: 'children',
                label: 'fullName',
            },
            list: [],
            listLoading: true,
            total: 0,
            listQuery: {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            },
            formVisible: false,
            exportBoxVisible: false,
            ContractVisible: false,
            FeeConfigVisible: false,
            cancelContractFormVisible: false,
            stateOptions: [{ fullName: '空置', id: 'empty' }],
        };
    },
    computed: {},
    created() {
        this.initData();
        this.getareaOptions();
        this.getstateOptions();
    },
    methods: {
        getareaOptions() {
            request({
                url: `/baseconfig/ConfigHouseBlock/selectList`,
                method: 'GET',
            }).then(res => {
                this.areaOptions = res.data;
            });
        },
        getstateOptions() {
            request({
                url: `/baseconfig/House/stateOptions`,
                method: 'GET',
            }).then(res => {
                this.stateOptions = res.data;
            });
        },

        initData() {
            this.listLoading = true;
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/baseconfig/House/houseContract`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;
                this.listLoading = false;
            });
        },
        cancelContract(id) {
            this.cancelContractFormVisible = true;
            this.$nextTick(() => {
                this.$refs.CancelContractForm.init(id);
            });
        },
        addOrUpdateHandle(company, resourceId, block, name, contractId, rentFee, isDetail) {
            this.ContractVisible = true;
            this.$nextTick(() => {
                this.$refs.ContractForm.init(company, resourceId, block, name, contractId, rentFee, '', isDetail);
            });
        },
        rented(resourceId, block, name, contractId, rentFee, isDetail) {
            this.ContractVisible = true;
            this.$nextTick(() => {
                this.$refs.ContractForm.init(resourceId, block, name, contractId, rentFee, 'rented', isDetail);
            });
        },
        selled(company, resourceId, block, name, contractId, rentFee, isDetail) {
            this.ContractVisible = true;
            this.$nextTick(() => {
                this.$refs.ContractForm.init(company, resourceId, block, name, contractId, rentFee, 'selled', isDetail);
            });
        },
        showRentedConfig() {
            this.FeeConfigVisible = true;
            this.$nextTick(() => {
                this.$refs.FeeConfig.init('rented');
            });
        },
        showSelledConfig() {
            this.FeeConfigVisible = true;
            this.$nextTick(() => {
                this.$refs.FeeConfig.init('selled');
            });
        },
        search() {
            this.listQuery = {
                currentPage: 1,
                pageSize: 20,
                sort: 'desc',
                sidx: '',
            };
            this.initData();
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
    },
};
</script>
