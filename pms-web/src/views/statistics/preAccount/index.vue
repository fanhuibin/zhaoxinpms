<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="编号">
                            <HouseInput  v-model="query.resourceName"/>
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
                    <el-table-column prop="resourceName" label="编号" align="left" />
                    <el-table-column label="账户对应收费项目" prop="feeItemId" algin="left" />
                    <el-table-column prop="amt" label="账户余额" align="left" />
                </JTable>
                <pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="initData" />
            </div>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
import HouseInput from '@/components/HouseInput';

export default {
    components: {HouseInput},
    data() {
        return {
            showAll: false,
            query: {
                resourceName: undefined,
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
            withdrawFormVisible: false,
            printVisible: false,
        };
    },
    computed: {},
    created() {
        this.initData();
    },
    methods: {
        initData() {
            this.listLoading = true;
            let query = {
                ...this.listQuery,
                ...this.query,
            };
            request({
                url: `/statistics/preAccount`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data.list;
                this.total = res.data.pagination.total;

                this.listLoading = false;
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
