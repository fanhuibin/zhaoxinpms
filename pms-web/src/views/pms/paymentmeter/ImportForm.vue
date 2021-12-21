<template>
    <el-dialog
        title="批量导入"
        :show-close="false"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        lock-scroll
        width="1000px"
    >
        <el-steps :active="active" align-center>
            <el-step title="上传文件"></el-step>
            <el-step title="导入结果"></el-step>
        </el-steps>
        <div class="import-main" v-show="active == 1">
            <div class="upload">
                <div class="up_left">
                    <img src="@/assets/images/upload.png" />
                </div>
                <div class="up_right">
                    <p class="title">上传填好的数据表</p>
                    <p class="tip">文件后缀名必须是xls或xlsx，文件大小不超过500KB</p>
                    <el-upload
                        :action="uploadUrl"
                        :headers="{ Authorization: $store.getters.token }"
                        :on-success="handleSuccess"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        :on-change="handleChange"
                        :file-list="fileList"
                        accept=".xls,.xlsx"
                        :before-upload="beforeUpload"
                        class="upload-area"
                    >
                        <el-button type="text">上传文件</el-button>
                    </el-upload>
                </div>
            </div>
            <div class="upload">
                <div class="up_left">
                    <img src="@/assets/images/import.png" />
                </div>
                <div class="up_right">
                    <p class="title">填写导入数据信息</p>
                    <p class="tip">请按照数据模板的格式准备导入数据，模板中的表头名称不可更改，表头行不能删除</p>
                    <el-select v-model="feeItemId" placeholder="请选择收费项" size="mini" style="width: 200px">
                        <el-option v-for="item in feeItemList" :key="item.id" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                    <br />
                    <el-button type="text" @click="templateDownload">下载抄表数据导入模板</el-button>
                </div>
            </div>
        </div>
        <div class="import-main" v-show="active == 2">
            <div class="success">
                <img src="@/assets/images/success.png" alt="" />
                <p class="success-title">批量导入成功</p>
                <p class="success-tip">您已成功导入{{ result.num }}条数据</p>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="cancle()" v-if="active == 1">取 消</el-button>
            <el-button @click="next" type="primary" v-if="active == 1" :loading="btnLoading">下一步</el-button>
            <el-button @click="cancle(true)" type="primary" v-else>关 闭</el-button>
        </span>
    </el-dialog>
</template>

<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            visible: false,
            btnLoading: false,
            listLoading: false,
            fileName: '',
            fileList: [],
            active: 1,
            result: {},
            feeItemId: undefined,
            feeItemList: [],
            uploadUrl: process.env.VUE_APP_BASE_API + '/payment/PaymentMeterImport/Uploader',
        };
    },
    methods: {
        prev() {
            if (this.active == 1) return;
            this.active--;
        },
        next() {
            if (this.active == 1) {
                if (!this.fileList.length || !this.fileName) return this.$message({ message: '请先上传文件', type: 'warning' });
                this.btnLoading = true;
                request({
                    url: '/payment/PaymentMeterImport/Import',
                    method: 'get',
                    data: { fileName: this.fileName },
                })
                    .then(res => {
                        this.btnLoading = false;
                        this.result = res.data;
                        this.active++;
                    })
                    .catch(() => {
                        this.btnLoading = false;
                    });
            }
        },
        cancle(isRefresh) {
            this.visible = false;
            if (isRefresh) this.$emit('refresh', true);
        },
        init(feeItemList) {
            this.active = 1;
            this.fileList = [];
            this.fileName = '';
            this.feeItemList = feeItemList;
            this.visible = true;
        },
        handleDel(index) {
            this.list.splice(index, 1);
        },
        templateDownload() {
            if (this.feeItemId) {
                const url = process.env.VUE_APP_BASE_API + '/payment/PaymentMeterImport/Template?feeId=' + this.feeItemId;
                this.$download.customDownload(url);
            } else {
                this.$message({ message: '请先选择收费项', type: 'error', duration: 1000 });
            }
        },
        beforeUpload(file) {
            let isRightSize = file.size / 1024 < 500;
            if (!isRightSize) this.$message.error(`文件大小不能超过500KB`);
            return isRightSize;
        },
        handleRemove(file, fileList) {
            this.fileList = [];
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`).catch(() => {});
        },
        handleChange(file, fileList) {
            this.fileList = fileList.slice(-1);
        },
        handleSuccess(res, file, fileList) {
            if (res.code == 200) {
                this.fileList = fileList.slice(-1);
                this.fileName = res.data.name;
            } else {
                this.fileList = fileList.filter(o => o.uid != file.uid);
                this.$message({ message: res.msg, type: 'error', duration: 1000 });
            }
        },
    },
};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog__body {
    padding: 20px 40px 2px !important;
}
.import-main {
    height: 500px;
    margin-top: 20px;
    overflow: hidden;
    position: relative;
    .upload {
        display: flex;
        border: 1px solid #dcdfe6;
        margin-bottom: 25px;
        &.error-show {
            margin-top: 10px;
            margin-bottom: 15px;
            .up_left {
                height: 120px;
            }
            .up_right {
                padding-top: 20px;
                font-size: 16px;
                ::v-deep .el-link {
                    font-size: 16px;
                }
            }
        }
        .up_left {
            width: 126px;
            height: 140px;
            background: #f9f9f9;
            text-align: center;
            padding-top: 20px;
            flex-shrink: 0;
            img {
                width: 80px;
                height: 80px;
            }
        }

        .up_right {
            color: #333;
            margin-left: 30px;
            font-size: 14px;
            padding-top: 30px;
            flex: 1;

            .title {
                font-size: 18px;
                font-weight: bold;
            }
            .tip {
                margin: 15px 0;
            }
        }
        .upload-area {
            display: flex;
            padding-right: 200px;
            ::v-deep {
                .el-upload {
                    margin-right: 50px;
                    flex-shrink: 0;
                }
                .el-upload-list {
                    flex: 1;
                }
                .el-upload-list__item:first-child {
                    margin-top: 5px;
                }
            }
        }
    }
    .success {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-top: 110px;
        .success-title {
            margin: 20px 0;
            font-size: 18px;
            font-weight: bold;
        }
    }
    .contips {
        margin-bottom: 15px;
    }
}
</style>
