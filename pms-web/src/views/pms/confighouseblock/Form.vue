<template>
    <el-dialog
        :title="!dataForm.id ? '新建' : isDetail ? '详情' : '编辑'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        lock-scroll
        width="600px"
    >
        <el-row :gutter="15" class="">
            <el-form
                ref="elForm"
                :model="dataForm"
                :rules="rules"
                size="medium"
                label-width="100px"
                label-position="right"
                :disabled="!!isDetail"
                v-loading="loading"
            >
                <el-col :span="24">
                    <el-form-item label="商业区编号" prop="code">
                        <el-input :disabled="dataForm.id" v-model="dataForm.code" placeholder="请输入" clearable :style="{ width: '100%' }" show-word-limit :maxlength="17"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="商业区名" prop="name">
                        <el-input v-model="dataForm.name" placeholder="请输入" clearable :style="{ width: '100%' }" show-word-limit :maxlength="17"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="商业区地址" prop="address">
                        <el-input v-model="dataForm.address" type="textarea" :autosize="{ minRows: 4, maxRows: 4 }" placeholder="请输入" clearable show-word-limit :style="{ width: '100%' }" :maxlength="200"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="备注" prop="remark">
                        <el-input
                            v-model="dataForm.remark"
                            placeholder="请输入"
                            :style="{ width: '100%' }"
                            true
                            type="textarea"
                            show-word-limit
                            :autosize="{ minRows: 4, maxRows: 4 }"
                            :maxlength="200"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-form>
        </el-row>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="dataFormSubmit()" v-if="!isDetail">确 定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import request from '@/utils/request';

export default {
    components: {},
    props: [],
    data() {
        return {
            loading: false,
            visible: false,
            isDetail: false,
            dataForm: {
                code: undefined,
                name: undefined,
                remark: undefined,
            },
            rules: {
                code: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^\d+$/,
                        message: '请输入正确的数字',
                        trigger: 'blur',
                    },
                ],
                name: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur',
                    },
                ],
                address: [
                    {
                        required: true,
                        message: '请输入地址',
                        trigger: 'blur',
                    }
                ]
            },
        };
    },
    computed: {},
    watch: {},
    created() {},
    mounted() {},
    methods: {
        init(id, isDetail) {
            this.dataForm.id = id || 0;
            this.visible = true;
            this.isDetail = isDetail || false;
            this.$nextTick(() => {
                this.$refs['elForm'].resetFields();
                if (this.dataForm.id) {
                    this.loading = true;
                    request({
                        url: '/baseconfig/ConfigHouseBlock/' + this.dataForm.id,
                        method: 'get',
                    }).then(res => {
                        this.dataForm = res.data;
                        this.loading = false;
                    });
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    if (!this.dataForm.id) {
                        request({
                            url: `/baseconfig/ConfigHouseBlock`,
                            method: 'post',
                            data: this.dataForm,
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
                    } else {
                        request({
                            url: '/baseconfig/ConfigHouseBlock/' + this.dataForm.id,
                            method: 'PUT',
                            data: this.dataForm,
                        }).then(res => {
                            this.$message({
                                message: res.msg,
                                type: 'success',
                                duration: 1000,
                                onClose: () => {
                                    this.visible = false;
                                    this.$emit('refresh', true);
                                },
                            });
                        });
                    }
                }
            });
        },
    },
};
</script>
