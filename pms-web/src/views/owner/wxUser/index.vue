<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form :model="queryParams" ref="queryForm" v-show="showSearch">
                    <el-col :span="6">
                        <el-form-item label="用户标识" prop="openId">
                            <el-input v-model="queryParams.openId" placeholder="请输入用户标识" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="昵称" prop="nickName">
                            <el-input v-model="queryParams.nickName" placeholder="请输入昵称" clearable size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                    </el-col>

                    <el-col :span="6">
                        <el-form-item label="手机号码" prop="phone">
                            <el-input v-model="queryParams.phone" placeholder="请输入手机号码" clearable size="small" @keyup.enter.native="handleQuery" />
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
                   
                </div>

                <JTable v-loading="loading" :data="wxUserList" @selection-change="handleSelectionChange">
                    <el-table-column label="头像" align="center" prop="headimgUrl">
                        <template slot-scope="scope">
                            <img :src="scope.row.headimgUrl" width="60px"/>
                        </template>
                    </el-table-column>
                    <el-table-column label="用户标识" align="center" prop="openId" />
                    <el-table-column label="昵称" align="center" prop="nickName" />
                    <el-table-column label="性别" align="center" prop="sex" />
                    <el-table-column label="所在城市" align="center" prop="city" />
                    <el-table-column label="所在国家" align="center" prop="country" />
                    <el-table-column label="所在省份" align="center" prop="province" />
                    <el-table-column label="手机号码" align="center" prop="phone" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100px;">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['owner:wxUser:remove']">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>

                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </div>
        </div>

        <!-- 添加或修改微信用户对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="用户备注" prop="remark">
                    <el-input v-model="form.remark" placeholder="请输入用户备注" />
                </el-form-item>
                <el-form-item label="逻辑删除标记" prop="delFlag">
                    <el-input v-model="form.delFlag" placeholder="请输入逻辑删除标记" />
                </el-form-item>
                <el-form-item label="应用类型(1:小程序，2:公众号)" prop="appType">
                    <el-select v-model="form.appType" placeholder="请选择应用类型(1:小程序，2:公众号)">
                        <el-option label="请选择字典生成" value="" />
                    </el-select>
                </el-form-item>
                <el-form-item label="是否订阅" prop="subscribe">
                    <el-input v-model="form.subscribe" placeholder="请输入是否订阅" />
                </el-form-item>
                <el-form-item
                    label="返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他"
                    prop="subscribeScene"
                >
                    <el-input
                        v-model="form.subscribeScene"
                        placeholder="请输入返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他"
                    />
                </el-form-item>
                <el-form-item label="关注时间" prop="subscribeTime">
                    <el-date-picker
                        clearable
                        size="small"
                        v-model="form.subscribeTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择关注时间"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="关注次数" prop="subscribeNum">
                    <el-input v-model="form.subscribeNum" placeholder="请输入关注次数" />
                </el-form-item>
                <el-form-item label="取消关注时间" prop="cancelSubscribeTime">
                    <el-date-picker
                        clearable
                        size="small"
                        v-model="form.cancelSubscribeTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择取消关注时间"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="用户标识" prop="openId">
                    <el-input v-model="form.openId" placeholder="请输入用户标识" />
                </el-form-item>
                <el-form-item label="昵称" prop="nickName">
                    <el-input v-model="form.nickName" placeholder="请输入昵称" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-select v-model="form.sex" placeholder="请选择性别">
                        <el-option label="请选择字典生成" value="" />
                    </el-select>
                </el-form-item>
                <el-form-item label="所在城市" prop="city">
                    <el-input v-model="form.city" placeholder="请输入所在城市" />
                </el-form-item>
                <el-form-item label="所在国家" prop="country">
                    <el-input v-model="form.country" placeholder="请输入所在国家" />
                </el-form-item>
                <el-form-item label="所在省份" prop="province">
                    <el-input v-model="form.province" placeholder="请输入所在省份" />
                </el-form-item>
                <el-form-item label="手机号码" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入手机号码" />
                </el-form-item>
                <el-form-item label="用户语言" prop="language">
                    <el-input v-model="form.language" placeholder="请输入用户语言" />
                </el-form-item>
                <el-form-item label="头像" prop="headimgUrl">
                    <el-input v-model="form.headimgUrl" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="union_id" prop="unionId">
                    <el-input v-model="form.unionId" placeholder="请输入union_id" />
                </el-form-item>
                <el-form-item label="用户组" prop="groupId">
                    <el-input v-model="form.groupId" placeholder="请输入用户组" />
                </el-form-item>
                <el-form-item label="标签列表" prop="tagidList">
                    <el-input v-model="form.tagidList" placeholder="请输入标签列表" />
                </el-form-item>
                <el-form-item label="二维码扫码场景" prop="qrSceneStr">
                    <el-input v-model="form.qrSceneStr" placeholder="请输入二维码扫码场景" />
                </el-form-item>
                <el-form-item label="地理位置纬度" prop="latitude">
                    <el-input v-model="form.latitude" placeholder="请输入地理位置纬度" />
                </el-form-item>
                <el-form-item label="地理位置经度" prop="longitude">
                    <el-input v-model="form.longitude" placeholder="请输入地理位置经度" />
                </el-form-item>
                <el-form-item label="地理位置精度" prop="precision">
                    <el-input v-model="form.precision" placeholder="请输入地理位置精度" />
                </el-form-item>
                <el-form-item label="会话密钥" prop="sessionKey">
                    <el-input v-model="form.sessionKey" placeholder="请输入会话密钥" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { listWxUser, getWxUser, delWxUser, addWxUser, updateWxUser } from '@/api/owner/wxUser';

export default {
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
            // 微信用户表格数据
            wxUserList: [],
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                appType: null,
                subscribe: null,
                subscribeScene: null,
                subscribeTime: null,
                subscribeNum: null,
                cancelSubscribeTime: null,
                openId: null,
                nickName: null,
                sex: null,
                city: null,
                country: null,
                province: null,
                phone: null,
                language: null,
                headimgUrl: null,
                unionId: null,
                groupId: null,
                tagidList: null,
                qrSceneStr: null,
                latitude: null,
                longitude: null,
                precision: null,
                sessionKey: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                openId: [{ required: true, message: '用户标识不能为空', trigger: 'blur' }],
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询微信用户列表 */
        getList() {
            this.loading = true;
            listWxUser(this.queryParams).then(response => {
                this.wxUserList = response.data.records;
                this.total = response.data.total;
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
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
                remark: null,
                delFlag: null,
                appType: null,
                subscribe: null,
                subscribeScene: null,
                subscribeTime: null,
                subscribeNum: null,
                cancelSubscribeTime: null,
                openId: null,
                nickName: null,
                sex: null,
                city: null,
                country: null,
                province: null,
                phone: null,
                language: null,
                headimgUrl: null,
                unionId: null,
                groupId: null,
                tagidList: null,
                qrSceneStr: null,
                latitude: null,
                longitude: null,
                precision: null,
                sessionKey: null,
            };
            this.resetForm('form');
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
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
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = '添加微信用户';
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids;
            getWxUser(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = '修改微信用户';
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs['form'].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateWxUser(this.form).then(response => {
                            this.$modal.msgSuccess('修改成功');
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addWxUser(this.form).then(response => {
                            this.$modal.msgSuccess('新增成功');
                            this.open = false;
                            this.getList();
                        });
                    }
                }
            });
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            const ids = row.id || this.ids;
            this.$modal
                .confirm('是否确认删除微信用户编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delWxUser(ids);
                })
                .then(() => {
                    this.getList();
                    this.$modal.msgSuccess('删除成功');
                })
                .catch(() => {});
        },
        /** 导出按钮操作 */
        handleExport() {
            const queryParams = this.queryParams;
            this.$modal
                .confirm('是否确认导出所有微信用户数据项？')
                .then(() => {
                    this.exportLoading = true;
                    return exportWxUser(queryParams);
                })
                .then(response => {
                    this.$download.name(response.msg);
                    this.exportLoading = false;
                })
                .catch(() => {});
        },
    },
};
</script>
