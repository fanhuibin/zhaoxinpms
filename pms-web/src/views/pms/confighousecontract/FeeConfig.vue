<template>
    <el-dialog
        :title="'默认收费项'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        width="900px"
        height="50%"
        lock-scroll
    >
        <div class="Jflex-main" style="overflow: hidden; height: 100%; padding-bottom: 18px;">
            <JTable v-loading="listLoading" :data="feeItemList">
                <el-table-column prop="feeItemName" label="收费项名" align="left" />
                <el-table-column prop="price" label="单价" align="left" />

                <el-table-column label="操作" width="50">
                    <template slot-scope="scope">
                        <el-button size="mini" type="text" class="JTable-delBtn" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </JTable>
            <div class="table-actions" @click="choice">
                <el-button type="text" icon="el-icon-plus">新增收费项</el-button>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dataFormSubmit()">保存</el-button>
        </span>
        <Box v-if="boxVisible" ref="form" @refreshDataList="initData" />
    </el-dialog>
</template>
<script>
import request from '@/utils/request';
import Box from '../configfeeitem/SelectBox';

export default {
    components: { Box },
    props: [],
    data() {
        return {
            loading: false,
            visible: false,
            isDetail: false,
            feeItemList: [],
            listLoading: true,
            boxVisible: false,
            type: '',
        };
    },
    computed: {},
    watch: {},
    created() {},
    mounted() {},
    methods: {
        init(type) {
            this.visible = true;
            this.type = type;
            this.$nextTick(() => {
                if (type) {
                    this.search(type);
                }
            });
        },
        search(type) {
            this.listLoading = true;
            request({
                url: `/baseconfig/ConfigFeeSetting`,
                method: 'get',
                data: { type: type },
            }).then(res => {
                this.feeItemList = res.data;
                this.listLoading = false;
            });
        },
        handleDel(index, row) {
            this.feeItemList.splice(index, 1);
        },
        choice() {
            this.boxVisible = true;
            this.$nextTick(() => {
                this.$refs.form.init();
            });
        },
        initData(list) {
            for (let i = 0; i < list.length; i++) {
                const e = list[i];
                let item = {
                    feeItemId: e.id,
                    feeItemName: e.name,
                    price: e.price,
                    numType: e.numType,
                    formula: e.formula,
                };
                this.feeItemList.push(item);
            }
        },
        dataFormSubmit() {
            request({
                url: `/baseconfig/ConfigFeeSetting`,
                method: 'post',
                data: { type: this.type, feeList: this.feeItemList },
            }).then(res => {
                this.$message({
                    message: res.msg,
                    type: 'success',
                    duration: 1000,
                    onClose: () => {
                        (this.visible = false), this.$emit('refresh', true);
                    },
                });
            });
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
