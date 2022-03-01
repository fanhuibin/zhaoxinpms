<template>
    <el-dialog
        title="选择收费项"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        lock-scroll
        append-to-body
        width="700px"
    >
        <el-row class="Jcommon-search-box" :gutter="16">
            <el-form @submit.native.prevent>
                <el-col :span="10">
                    <el-form-item label="关键词">
                        <el-input v-model="query.name" placeholder="请输入关键词查询" clearable />
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="init()">查询</el-button>
                        <el-button icon="el-icon-refresh-right" @click="refresh()">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-form>
            <div class="Jcommon-search-box-right">
                <el-tooltip effect="dark" :content="'刷新'" placement="top">
                    <el-link icon="icon-ym icon-ym-Refresh Jcommon-head-icon" :underline="false" @click="refresh()" />
                </el-tooltip>
            </div>
        </el-row>
        <el-table v-loading="listLoading" :data="list" hasC @selection-change="handleSelectionChange" :border="false">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column prop="name" label="收费项目名" align="left" />
            <el-table-column prop="price" label="单价" align="left" />
            <el-table-column label="数量" prop="numType" algin="left">
                <template slot-scope="scope">
                    {{ scope.row.numType | dynamicText(numTypeOptions) }}
                </template>
            </el-table-column>
            <el-table-column label="计算公式" prop="formula" algin="left">
                <template slot-scope="scope">
                    {{ scope.row.formula | dynamicText(formulaOptions) }}
                </template>
            </el-table-column>
            <el-table-column label="计算周期" prop="period" algin="left">
                <template slot-scope="scope">
                    {{ scope.row.period }}
                </template>
            </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="select()">确认</el-button>
        </span>
    </el-dialog>
</template>

<script>
import request from '@/utils/request';
export default {
    data() {
        return {
            query: {
                name: undefined,
                type: 'house',
            },
            typeOptions: [
                { fullName: '常规收费项（商铺）', id: 'house' },
                { fullName: '临时收费项', id: 'temp' },
                { fullName: '押金类收费项', id: 'deposit' },
            ],
            formulaOptions: [
                { fullName: '单价*数量', id: 'base' },
                { fullName: '自定义', id: 'expression' },
            ],
            numTypeOptions: [
                { fullName: '按户数收费', id: 'flat' },
                { fullName: '按人口数收费', id: 'people' },
                { fullName: '按楼层收费', id: 'floor' },
                { fullName: '按占地面积收费', id: 'building_area' },
                { fullName: '按使用面积收费', id: 'use_area' },
                { fullName: '按走表数量', id: 'meter' },
            ],
            visible: false,
            listLoading: true,
            keyword: '',
            list: [],
            total: 0,
            checked: [],
        };
    },
    methods: {
        init() {
            this.visible = true;
            this.listLoading = true;
            let query = {
                ...this.query,
            };
            request({
                url: `/baseconfig/ConfigFeeItem/select`,
                method: 'get',
                data: query,
            }).then(res => {
                this.list = res.data;
                this.listLoading = false;
            });
        },
        refresh() {
            this.keyword = '';
            this.query.name = '';
            this.init();
        },
        select() {
            if (!this.checked.length) return;
            this.visible = false;
            this.$emit('refreshDataList', this.checked);
        },
        handleSelectionChange(val) {
            this.checked = val;
        },
    },
};
</script>
<style lang="scss" scoped>
>>> .el-dialog__body {
    height: 70vh;
    padding: 0 0 10px !important;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    .Jcommon-search-box {
        margin-bottom: 0;
        .Jcommon-search-box-right {
            padding: 10px 10px 0 0;
        }
    }
}
</style>
