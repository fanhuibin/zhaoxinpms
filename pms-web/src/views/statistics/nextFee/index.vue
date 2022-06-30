<template>
    <div style="box-sizing:border-box;overflow-x: hidden;height:100%">
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
                                <el-col :span="6">
                                    <el-form-item label="下次缴费时间">
                                        <el-date-picker
                                            v-model="pickerVal"
                                            type="daterange"
                                            start-placeholder="开始日期"
                                            end-placeholder="结束日期"
                                            :picker-options="pickerOptions"
                                            value-format="timestamp"
                                            clearable
                                            :editable="false"
                                        ></el-date-picker>
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
                            <JTable v-loading="listLoading" :data="list">
                                <el-table-column prop="resourceName" label="编号" align="left" width="100" />
                                <el-table-column prop="feeItemName" label="收费项名" align="left" width="150" />
                                <el-table-column prop="userName" label="客户姓名" align="left" width="100" />
                                <el-table-column prop="nextBillDate" label="下次缴费时间" align="left" width="120">
                                    <template slot-scope="scope">
                                        <span style="color: red">{{ scope.row.nextBillDate }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="beginDate" label="开始计费日期" align="left" width="150" />
                                <el-table-column prop="endDate" label="结束计费日期" align="left" width="150" />
                            </JTable>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import request from '@/utils/request';
import HouseInput from '@/components/HouseInput';
import { selectBuilding } from '@/api/pms/building';

export default {
    components: { HouseInput },
    data() {
        return {
            showAll: false,
            query: {
                feeItemId: undefined,
                resourceName: undefined,
                beginTime: undefined,
                endTime: undefined,
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
            pickerVal: [],
            pickerOptions: {
                shortcuts: [
                    {
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                ],
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

            //初始化时间设置
            if (this.pickerVal && this.pickerVal.length) {
            } else {
                const end = new Date();
                const start = new Date();
                end.setTime(end.getTime() + 3600 * 1000 * 24 * 60);
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 180);
                this.pickerVal = [start.getTime(), end.getTime()];
            }
            this.query.beginTime = this.pickerVal[0];
            this.query.endTime = this.pickerVal[1];

            request({
                url: `/statistics/nextFee`,
                method: 'get',
                data: this.query,
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
