<template>
    <el-dialog title="选择业主" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll append-to-body width="800px">
        <el-row class="Jcommon-search-box" :gutter="16">
            <el-form @submit.native.prevent>
                <el-col :span="8">
                    <el-form-item label="公司名" prop="company">
                        <el-input v-model="queryParams.company" placeholder="公司名" clearable size="small" @keyup.enter.native="handleQuery" />
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="业主名" prop="userName">
                        <el-input v-model="queryParams.userName" placeholder="请输入业主名" clearable size="small" @keyup.enter.native="handleQuery" />
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="手机号码" prop="phonenumber">
                        <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号码" clearable size="small" @keyup.enter.native="handleQuery" />
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
        <el-table v-loading="listLoading" :data="ownerUserList" @selection-change="handleSelectionChange">
            <el-table-column label="公司名" align="center" prop="company" />
            <el-table-column label="业主姓名" align="center" prop="userName" />
            <el-table-column label="身份证号" align="center" prop="idcard" width="150px" />
            <el-table-column label="手机号码" align="center" prop="phonenumber" />
            <el-table-column label="性别" align="center" prop="sex">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex" />
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="120px" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                   <el-button type="text" @click="select(scope.row)" size="mini">选择业主</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <!--<el-button type="primary" @click="select()">确认</el-button>-->
        </span>
    </el-dialog>
</template>

<script>
import request from '@/utils/request';
import { listOwnerUser, getOwnerUser, delOwnerUser, addOwnerUser, updateOwnerUser, exportOwnerUser } from '@/api/owner/ownerUser';
export default {
    dicts: ['sys_user_sex', 'sys_normal_disable'],
    data() {
        return {
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                userName: null,
                idcard: null,
                phonenumber: null,
                ownCount: null,
            },
            visible: false,
            listLoading: true,
            list: [],
            ownerUserList: [],
            total: 0,
            checked: [],
        };
    },
    methods: {
        /** 查询业主信息列表 */
        init() {
            this.visible = true;
            this.listLoading = true;
            this.getList();
        },
        getList() {
            listOwnerUser(this.queryParams).then(response => {
                this.ownerUserList = response.data.list;
                this.total = response.data.pagination.total;
                this.listLoading = false;
            });
        },
        refresh() {
            this.query.userName = '';
            this.query.phonenumber = "";
            this.init();
        },
        select(data) {
            this.visible = false;
            this.$emit('select', data);
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
