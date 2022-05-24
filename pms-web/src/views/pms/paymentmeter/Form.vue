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
                label-width="120px"
                label-position="right"
                :disabled="!!isDetail"
                v-loading="loading"
            >
                <el-col :span="24">
                    <el-form-item label="资源名" prop="resourceName">
                        <el-input v-model="dataForm.resourceName" placeholder="请输入" clearable :disabled="true" :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="资源类型" prop="feeItemName">
                        <el-input v-model="dataForm.feeItemName" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="上期度数" prop="lastIndex">
                        <el-input v-model="dataForm.lastIndex" placeholder="请输入" clearable v-on:input="calcResult" :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="上期读表时间" prop="lastIndexDate">
                        <el-date-picker
                            v-model="dataForm.lastIndexDate"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="本期度数" prop="currentIndex">
                        <el-input v-model="dataForm.currentIndex" placeholder="请输入" clearable v-on:input="calcResult" :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="本期读表时间" prop="currentIndexDate">
                        <el-date-picker
                            v-model="dataForm.currentIndexDate"
                            placeholder="请选择"
                            clearable
                            :style="{ width: '100%' }"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="timestamp"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="倍率" prop="multiple">
                        <el-input v-model="dataForm.multiple" placeholder="请输入" clearable v-on:input="calcResult" :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="损耗" prop="loss">
                        <el-input v-model="dataForm.loss" placeholder="请输入" clearable v-on:input="calcResult" :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="最终数量" prop="result">
                        <el-input v-model="dataForm.result" placeholder="请输入" clearable :style="{ width: '100%' }" :disabled="true"></el-input>
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
                resourceId: undefined,
                resourceType: undefined,
                resourceName: undefined,
                feeItemId: undefined,
                lastIndex: undefined,
                lastIndexDate: undefined,
                index: undefined,
                indexDate: undefined,
            },
            rules: {
                lastIndex: [
                    {
                        required: true,
                        message: '上期度数不能为空',
                        trigger: 'blur',
                    },
                ],
                currentIndex: [
                    {
                        required: true,
                        message: '本期度数不能为空',
                        trigger: 'blur',
                    },
                ],
                currentIndexDate: [
                    {
                        required: true,
                        message: '本期读表时间不能为空',
                        trigger: 'blur',
                    },
                ],
                multiple: [
                    {
                        required: true,
                        message: '倍率不能为空',
                        trigger: 'blur',
                    },
                ],
                loss: [
                    {
                        required: true,
                        message: '损耗不能为空',
                        trigger: 'blur',
                    },
                ],
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
                        url: '/payment/PaymentMeter/' + this.dataForm.id,
                        method: 'get',
                    }).then(res => {
                        this.dataForm = res.data;
                        this.loading = false;
                    });
                }
            });
        },
        calcResult() {
            if (this.dataForm.lastIndex != '' && this.dataForm.currentIndex != '') {
                var num = (parseFloat(this.dataForm.currentIndex) - parseFloat(this.dataForm.lastIndex)).toFixed(2);
                if (this.dataForm.multiple != '') {
                    num = (parseFloat(num) * parseFloat(this.dataForm.multiple)).toFixed(2);
                }
                if (this.dataForm.loss != '') {
                    num = (parseFloat(num) + parseFloat(this.dataForm.loss)).toFixed(2);
                }
                this.dataForm.result = num;
            }
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['elForm'].validate(valid => {
                if (valid) {
                    if (!this.dataForm.id) {
                        request({
                            url: `/payment/PaymentMeter`,
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
                            url: '/payment/PaymentMeter/' + this.dataForm.id,
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
