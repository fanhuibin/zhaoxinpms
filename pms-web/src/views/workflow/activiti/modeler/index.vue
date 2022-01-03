<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="KEY" prop="key">
        <el-input
          v-model="queryParams.key"
          placeholder="请输入KEY"
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
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['activiti:modeler:add']"
        >创建新模型</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="modelerList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="120" />
      <el-table-column label="KEY" align="center" prop="key" width="120" />
      <el-table-column label="名称" align="center" prop="name" width="150" />
      <el-table-column label="版本" align="center" prop="version" width="150" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['activiti:modeler:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload2"
            @click="handleDeploy(scope.row)"
            v-hasPermi="['activiti:modeler:deploy']"
          >部署</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleExport(scope.row)"
            v-hasPermi="['activiti:modeler:export']"
          >导出</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['activiti:modeler:remove']"
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

    <!-- 添加或修改模型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="KEY" prop="key">
          <el-input v-model="form.key" placeholder="请输入KEY" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
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
  import { listModeler, delModeler, addModeler, deployModeler, exportModeler } from "@/api/activiti/modeler.js"
  import { format } from "@/utils/activiti/myUtil.js"

  export default {
    name: "Modeler",
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
        // 模型表格数据
        modelerList: [],
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
        },
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 表单校验
        rules: {
          key: [
            { required: true, message: "KEY不能为空", trigger: "blur" }
          ],
          name: [
            { required: true, message: "名称不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询模型列表 */
      getList() {
        this.loading = true;
        listModeler(this.queryParams).then(
          response => {
            this.modelerList = response.rows;
            this.modelerList.forEach(row => {
              row.createTime = format(row.createTime, 'yyyy-MM-dd HH:mm:ss');
              row.lastUpdateTime = format(row.lastUpdateTime, 'yyyy-MM-dd HH:mm:ss');
            });
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          name: undefined,
          key: undefined,
          description: undefined,
        };
        this.resetForm("form");
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
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加模型";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const modelId = row.id;
        this.designModeler(modelId);
      },
      /** 提交按钮 */
      submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            addModeler(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();

                this.designModeler(response.data)
              }
            });
          }
        });
      },
      /** 模型在线设计 */
      designModeler(id) {
        // 打开新标签页
        const routeUrl = this.$router.resolve({
          path: process.env.VUE_APP_BASE_API + "/modeler/modeler.html?modelId=" + id,
        });
        window.open(routeUrl.href, '_blank');
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const modelIds = row.id;
        this.$confirm('是否确认删除ID为"' + modelIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delModeler(modelIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport(row) {
        const modelId = row.id;
        this.$confirm('是否确认导出ID为"' + modelId + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportModeler(modelId);
        }).then(response => {
          //创建一个隐藏的a连接，
          const link = document.createElement('a');
          let blob = new Blob([response]/*, {type: 'application/xml'}*/);
          link.style.display = 'none';
          //设置连接
          link.href = URL.createObjectURL(blob);
          link.download = '导出_' + new Date().getTime() + '.bpmn';
          document.body.appendChild(link);
          //模拟点击事件
          link.click();
        }).catch(function () {
        });
      },
      /** 部署按钮操作 */
      handleDeploy(row) {
        const modelId = row.id;
        this.$confirm('是否确认部署ID为"' + modelId + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return deployModeler(modelId);
        }).then(response => {
          this.msgSuccess("部署成功");
        }).catch(function () {
        });
      },
    },
  }
</script>

<style scoped>

</style>
