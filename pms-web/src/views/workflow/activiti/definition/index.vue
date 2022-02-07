<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="KEY" prop="key">
        <el-input
          v-model="queryParams.key"
          placeholder="请输入流程KEY"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属分类" prop="name">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入所属分类"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-upload"
          size="mini"
          @click="handleUpload"
        >部署流程定义</el-button>
        <span style="color: red; font-size: 12px;">（查看流程图模糊时，可选择 .bpmn 和 .png 一同打成 zip 包部署）</span>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="definitionList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程ID" align="center" prop="id" width="120" />
      <el-table-column label="流程KEY" align="center" prop="key" width="120" />
      <el-table-column label="流程名称" align="center" prop="name" width="150" />
      <el-table-column label="版本" align="center" prop="version" width="90" />
      <el-table-column label="流程描述" align="center" prop="description" width="150" />
      <el-table-column label="所属分类" align="center" prop="category" width="120" />
      <el-table-column label="部署时间" align="center" prop="deploymentTime" width="100" />
      <!--<el-table-column label="流程定义" align="center" prop="resourceName" width="120">
        <template slot-scope="scope">
          <a
            :href="baseURL + '/activiti/definition/readResource?pdid=' + scope.row.id + '&resourceName=' + scope.row.resourceName "
            target="_blank"
            style="color: #409EFF;"
          >{{ scope.row.resourceName.substring(scope.row.resourceName.lastIndexOf('/') + 1) }}</a>
        </template>
      </el-table-column>
      <el-table-column label="流程图" align="center" prop="diagramResourceName" width="120">
        <template slot-scope="scope">
          <img
            style="width: 100px;"
            :src="baseURL + '/activiti/definition/readResource?pdid=' + scope.row.id + '&resourceName=' + scope.row.diagramResourceName"
          />
        </template>
      </el-table-column>-->
      <el-table-column label="状态" align="center" prop="suspendStateName" width="90" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-sort"
            @click="handleState(scope.row)"
          >{{ scope.row.suspendState === '2' ? '激活' : '挂起' }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh"
            @click="handleConvert(scope.row)"
          >转模型</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 部署流程定义对话框 -->
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="上传" prop="upload" required>
          <el-upload ref="upload" :file-list="uploadfileList" :action="uploadAction"
                     :before-upload="uploadBeforeUpload">
            <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listDefinition, delDefinition, suspendOrActiveDefinition, convert2Model } from "@/api/activiti/definition.js"
  import { format } from "@/utils/activiti/myUtil.js"

  export default {
    name: "Definition",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 流程定义表格数据
        definitionList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 日期范围
        dateRange: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          key: undefined,
          name: undefined,
          category: undefined,
        },
        // 表单参数
        form: {},
        rules: {},
        formData: {
          upload: null,
        },
        uploadAction: process.env.VUE_APP_BASE_API + '/activiti/definition/upload',
        uploadfileList: [],
        baseURL: process.env.VUE_APP_BASE_API,
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询流程定义列表 */
      getList() {
        this.loading = true;
        listDefinition(this.queryParams).then(
          response => {
            this.definitionList = response.rows;
            this.definitionList.forEach(row => {
              row.deploymentTime = format(row.deploymentTime, 'yyyy-MM-dd HH:mm:ss');
            });
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 新增按钮操作 */
      handleUpload() {
        this.open = true;
        this.title = "部署流程定义";
      },
      onOpen() {},
      onClose() {
        this.$refs['elForm'].resetFields()
      },
      close() {
        this.open = false;
        this.formData = {
          upload: null,
        };
        this.uploadfileList = [];
      },
      handelConfirm() {
        // this.$refs['elForm'].validate(valid => {
        //   if (!valid) return;
        //   this.close()
        // })
        this.$refs.upload.submit();
        this.close();
        this.getList();
      },
      uploadBeforeUpload(file) {
        let isRightSize = file.size / 1024 / 1024 < 2
        if (!isRightSize) {
          this.$message.error('文件大小超过 2MB')
        }
        return isRightSize
      },
      /** 激活挂起按钮操作 */
      handleState(row) {
        const pid = row.id;
        const suspendState = row.suspendState;
        const opt = row.suspendState === '2' ? '激活': '挂起';
        this.$confirm('是否确认' + opt + 'ID为"' + pid + '"的流程定义?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          const data = { "id": pid, "suspendState": suspendState };
          return suspendOrActiveDefinition(data);
        }).then(response => {
          this.msgSuccess("操作成功");
          this.getList();
        }).catch(function () {
        });
      },
      /** 转模型按钮操作 */
      handleConvert(row) {
        const pid = row.id;
        this.$confirm('是否确认将ID为"' + pid + '"的流程定义转换成流程模型?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          const data = { "processDefinitionId": pid };
          return convert2Model(data);
        }).then(response => {
          this.msgSuccess("操作成功");
          this.getList();
        }).catch(function () {
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const deploymentId = row.deploymentId;
        this.$confirm('是否确认删除ID为"' + deploymentId + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delDefinition(deploymentId);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
    },
  }
</script>

<style scoped>

</style>
