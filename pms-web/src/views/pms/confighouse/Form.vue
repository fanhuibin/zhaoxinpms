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
                ref="dataForm"
                :model="dataForm"
                :rules="rules"
                size="medium"
                label-width="100px"
                label-position="right"
                :disabled="!!isDetail"
                v-loading="loading"
            >
                <el-col :span="24">
                    <el-form-item label="楼栋" prop="block">
                         <el-cascader
                            v-model="dataForm.buildingSelect"
                            :options="buildings"
                            clearable
                            :style="{ width: '100%' }"
                            @change="handleChange">
                        </el-cascader>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="房号" prop="code">
                        <el-input v-model="dataForm.code" placeholder="请输入房号" :maxlength="49" clearable :style="{ width: '100%' }"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="楼层" prop="floor">
                        <el-input v-model="dataForm.floor" placeholder="请输入楼层" clearable :style="{ width: '100%' }" :maxlength="7"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="占地面积" prop="buildingsquare">
                        <el-input v-model="dataForm.buildingsquare" placeholder="建筑面积" clearable :style="{ width: '100%' }" :maxlength="10"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="使用面积" prop="usesquare">
                        <el-input v-model="dataForm.usesquare" placeholder="使用面积" clearable :style="{ width: '100%' }" :maxlength="10"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="租金" prop="rentFee">
                        <el-input v-model="dataForm.rentFee" placeholder="请输入租金" clearable :style="{ width: '100%' }" :maxlength="10"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item label="使用状态" prop="state">
                        <el-select v-model="dataForm.state" placeholder="请选择" :disabled="true" clearable :style="{ width: '100%' }" :multiple="false">
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

                <el-col :span="24">
                    <el-form-item label="备注" prop="remark">
                        <el-input
                            v-model="dataForm.remark"
                            placeholder="请输入"
                            :maxlength="200"
                            :style="{ width: '100%' }"
                            true
                            type="textarea"
                            :autosize="{ minRows: 4, maxRows: 4 }"
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
import { selectBuilding } from '@/api/pms/building';

export default {
    components: {},
    props: [],
    data() {
        return {
            loading: false,
            visible: false,
            isDetail: false,
            buildings: [],
            dataForm: {
                id: '',
                block: '',
                code: undefined,
                buildingsquare: undefined,
                usesquare: undefined,
                state: 'empty',
                floor: '',
                rentFee: '0',
                remark: undefined,
                buildingSelect: [],
            },
            rules: {
                block: [
                    {
                        required: true,
                        message: '请选择楼栋',
                        trigger: 'blur',
                    },
                ],
                code: [
                    {
                        required: true,
                        message: '请输入房号',
                        trigger: 'blur',
                    },
                ],
                floor: [
                    {
                        required: true,
                        message: '请输入楼层',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^[1-9]{1}[0-9]*$/,
                        message: '格式不正确',
                        trigger: 'blur',
                    },
                ],
                rentFee: [
                    {
                        pattern: /^(([1-9]{1}\d*)|(0{1}))(\.\d{0,2})?$/,
                        message: '请输入正确的数字，最多两位小数',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur',
                    },
                ],
                buildingsquare: [
                    {
                        required: true,
                        message: '建筑面积',
                        trigger: 'blur',
                    },
                    {
                        min: 0,
                        max: 99999999,
                        message: '建筑面积要大于0小于99999999',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur',
                    },
                ],
                usesquare: [
                    {
                        required: true,
                        message: '使用面积',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d*)|(0{1}))(\.\d{0,2})?$/,
                        message: '请输入正确的数字，最多俩位小数',
                        trigger: 'blur',
                    },
                    {
                        pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{0,2})?$/,
                        message: '小数点前最多8位数字',
                        trigger: 'blur',
                    },
                ],
                state: [
                    {
                        required: true,
                        message: '请至少选择一个',
                        trigger: 'blur',
                    },
                ],
            },
            areaOptions: [],
            stateOptions: [{ fullName: '空置', id: 'empty' }],
        };
    },
    computed: {},
    watch: {},
    created() {
        //this.getareaOptions();
        this.getBuildings();
        this.getstateOptions();
    },
    mounted() {},
    methods: {
        getBuildings() {
            selectBuilding().then(res => {
                this.buildings = res.data;
            });
        },
        handleChange(value) {
            this.dataForm.block = value[0];
            this.dataForm.building = value[1];
        },
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
        init(id, isDetail) {
            this.dataForm.id = id || 0;
            this.visible = true;
            this.isDetail = isDetail || false;
            this.$nextTick(() => {
                this.$refs['dataForm'].resetFields();

                if (this.dataForm.id) {
                    this.loading = true;
                    request({
                        url: '/baseconfig/House/' + this.dataForm.id,
                        method: 'get',
                    }).then(res => {
                        res.data.buildingSelect = [res.data.block,res.data.building];
                        this.dataForm = res.data;
                        this.loading = false;
                    });
                }
            });
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['dataForm'].validate(valid => {
                if (valid) {
                    if (!this.dataForm.id) {
                        request({
                            url: `/baseconfig/House`,
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
                            url: '/baseconfig/House/' + this.dataForm.id,
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
