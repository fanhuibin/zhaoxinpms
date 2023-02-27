<template>
    <el-drawer size="550px" class="drawer" :visible.sync="visible" :show-close="false" style="text-align: left" @close="cancel" v-if="properties">
        <!-- 标题 -->
        <header slot="title" class="header" v-if="value && value.type == 'start' && properties.title">{{ properties.title }}</header>
        <header slot="title" class="header" v-else>
            <span @click="titleInputVisible = true" v-show="!titleInputVisible" style="cursor: pointer">
                {{ properties.title }}
                <i class="el-icon-edit"></i>
            </span>
            <el-input
                size="mini"
                v-model="properties.title"
                v-show="titleInputVisible"
                v-clickoutside="_ => (titleInputVisible = false)"
                style="z-index: 9; max-width: 200px"
            ></el-input>
            <el-select v-if="isConditionNode()" v-model="properties.priority" size="mini" class="priority-select">
                <el-option v-for="item in priorityLength" :key="item" :value="item - 1" :label="'优先级' + item"></el-option>
            </el-select>
        </header>

        <!-- 条件  -->
        <section class="condition-pane pd-10" v-if="value && isConditionNode()">
            <el-form size="medium" label-width="100px" label-position="top">
                <el-form-item label="条件说明" prop="condition">
                    <el-input v-model="conditionLabel" placeholder="条件说明" :maxlength="100" :style="{ width: '100%' }"></el-input>
                </el-form-item>
                <el-form-item label="条件表达式" prop="condition">
                    <el-input
                        v-model="condition"
                        type="textarea"
                        placeholder="请输入条件表达式"
                        :maxlength="100"
                        show-word-limit
                        :autosize="{ minRows: 4, maxRows: 4 }"
                        :style="{ width: '100%' }"
                    ></el-input>
                </el-form-item>
            </el-form>
        </section>

        <!-- 发起人 -->
        <section class="approver-pane" style="height: 100%" v-if="value && isStartNode()">
            <el-tabs v-model="activeName" style="height: 100%" class="pane-tab">
                <el-tab-pane label="设置发起人">
                    <h3 style="padding-left: 20px">所有人都可以发起流程</h3>
                </el-tab-pane>
                <el-tab-pane label="表单权限" name="formAuth">
                    <div class="form-auth-table">
                        <el-table :data="getFormOperates()" size="mini">
                            <el-table-column prop="vModel" label="字段名" align="left"></el-table-column>
                            <el-table-column prop="label" label="字段标题" align="left"></el-table-column>
                            <el-table-column prop="operate" label="操作" align="center" width="300px">
                                <template slot-scope="scope">
                                    <el-checkbox v-model="scope.row.read">查看权限</el-checkbox>
                                    <el-checkbox v-model="scope.row.write">修改权限</el-checkbox>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </section>

        <!-- 审批人 -->
        <section class="approver-pane" style="height: 100%" v-if="value && isApproverNode()">
            <el-tabs v-model="activeName" class="pane-tab">
                <el-tab-pane :label="'设置' + (value.type === 'approver' ? '审批人' : '发起人')" name="config">
                    <!-- 开始节点 -->
                    <el-row style="padding: 10px" v-if="value.type === 'start'">
                        <el-col :span="4" style="font-size: 12px">发起人</el-col>
                        <el-col :span="18" style="padding-left: 12px"></el-col>
                    </el-row>

                    <div v-else-if="value.type === 'approver'">
                        <div style="padding: 12px">
                            <el-radio-group v-model="approverForm.assigneeType" style="line-height: 32px" @change="resetOrgColl">
                                <el-radio v-for="item in assigneeTypeOptions" :label="item.value" :key="item.value" class="radio-item">
                                    {{ item.label }}
                                </el-radio>
                            </el-radio-group>
                        </div>
                        <div style="border-bottom: 1px solid #e5e5e5; padding-bottom: 1rem">
                            <div v-if="approverForm.assigneeType === 'myself'" class="option-box" style="color: #a5a5a5">发起人自己将作为审批人处理审批单</div>
                            <div v-else class="option-box">
                                <org-select
                                    v-if="approverForm.assigneeType === 'role'"
                                    ref="approver-role-org"
                                    buttonType="button"
                                    v-model="approverForm.approverRoles"
                                    title="添加角色"
                                    type="role"
                                    @change="onOrgChange"
                                    class="mb-10"
                                />
                                <org-select
                                    v-if="approverForm.assigneeType === 'user'"
                                    ref="approver-user-org"
                                    buttonType="button"
                                    v-model="approverForm.approvers"
                                    title="添加用户"
                                    @change="onOrgChange"
                                />
                                <el-form size="medium" label-width="100px" label-position="top">
                                    <el-form-item label="activiti表达式" prop="expression" v-if="approverForm.assigneeType === 'input'">
                                        <el-input
                                            v-model="approverForm.expression"
                                            type="textarea"
                                            placeholder="请输入表达式"
                                            :maxlength="100"
                                            show-word-limit
                                            :autosize="{ minRows: 4, maxRows: 4 }"
                                            :style="{ width: '100%' }"
                                        ></el-input>
                                    </el-form-item>
                                    <el-form-item label="多人审批时采取的审批方式" prop="counterSign">
                                        <el-radio v-model="approverForm.optionalMultiUser" label="orSign">或签</el-radio>
                                        <el-radio v-model="approverForm.optionalMultiUser" label="counterSign">会签</el-radio>
                                    </el-form-item>
                                    <el-form-item label="是否可驳回" prop="counterSign">
                                        <el-radio v-model="approverForm.rejectConfig" label="0">不可驳回</el-radio>
                                        <el-radio v-model="approverForm.rejectConfig" label="1">可驳回</el-radio>
                                        <el-input v-model="approverForm.rejectNodeName" 
                                            v-if="approverForm.rejectConfig == '1'" 
                                            placeholder="请输入要回到到的节点名"
                                            style="width:50%"
                                        />
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="表单权限" name="formAuth">
                    <div class="form-auth-table">
                        <el-table :data="getFormOperates()" size="mini">
                            <el-table-column prop="vModel" label="字段名" align="left"></el-table-column>
                            <el-table-column prop="label" label="字段标题" align="left"></el-table-column>
                            <el-table-column prop="operate" label="操作" align="center" width="300px">
                                <template slot-scope="scope">
                                    <el-checkbox v-model="scope.row.read">查看权限</el-checkbox>
                                    <el-checkbox v-model="scope.row.write">修改权限</el-checkbox>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </section>

        <section v-if="value && isCopyNode()" style="padding-left: 1rem">
            <el-tabs v-model="activeName" class="pane-tab">
                <el-tab-pane :label="'设置抄送人'" name="config">
                    <div>
                        <div style="padding: 12px">
                            <el-radio-group v-model="approverForm.assigneeType" style="line-height: 32px" @change="resetOrgColl">
                                <el-radio v-for="item in copyAssigneeTypeOptions" :label="item.value" :key="item.value" class="radio-item">
                                    {{ item.label }}
                                </el-radio>
                            </el-radio-group>
                        </div>
                        <div style="border-bottom: 1px solid #e5e5e5; padding-bottom: 1rem">
                            <div class="option-box">
                                <org-select
                                    v-if="approverForm.assigneeType === 'role'"
                                    ref="approver-role-org"
                                    buttonType="button"
                                    v-model="approverForm.approverRoles"
                                    title="添加角色"
                                    type="role"
                                    @change="onOrgChange"
                                    class="mb-10"
                                />
                                <org-select
                                    v-if="approverForm.assigneeType === 'user'"
                                    ref="approver-user-org"
                                    buttonType="button"
                                    v-model="approverForm.approvers"
                                    title="添加用户"
                                    @change="onOrgChange"
                                />
                                <el-form size="medium" label-width="100px" label-position="top">
                                    <el-form-item label="activiti表达式" prop="expression" v-if="approverForm.assigneeType === 'input'">
                                        <el-input
                                            v-model="approverForm.expression"
                                            type="textarea"
                                            placeholder="请输入表达式"
                                            :maxlength="100"
                                            show-word-limit
                                            :autosize="{ minRows: 4, maxRows: 4 }"
                                            :style="{ width: '100%' }"
                                        ></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </section>

        <div class="actions">
            <el-button size="small" @click="cancel">取消</el-button>
            <el-button size="small" type="primary" @click="confirm">确定</el-button>
        </div>
    </el-drawer>
</template>
<script>
import Clickoutside from 'element-ui/src/utils/clickoutside';
import { NodeUtils } from '../FlowCard/util.js';
import OrgSelect from '../../FormControls/OrgSelect';

const defaultApproverForm = {
    approvers: [], // 审批人集合
    approverRoles: [], // 审批角色集合
    assigneeType: 'user', // 指定审批人
    formOperates: [], // 表单操作权限集合
    optionalMultiUser: 'orSign', //会签 counterSign,或签名 orSign
    expression: '',
    rejectConfig: '0', // 驳回设置
    rejectNodeName: '',
};
export default {
    props: [/*当前节点数据*/ 'value', /*整个节点数据*/ 'processData'],
    data() {
        return {
            visible: false, // 控制面板显隐
            globalFormOperate: null, // 统一设置节点表单权限
            titleInputVisible: false, // 是否显示标题输入框  startNode 不显示
            activeName: 'config', // or formAuth  Tab面板key
            showingPCons: [], // 用户选择了得条件  被选中的才会被展示在面板上编辑
            pconditions: [], // 从vuex中获取的可以作为流程图条件的集合
            conditionLabel: '', //条件显示的内容
            condition: null, //条件选择
            dialogVisible: false, // 控制流程条件选项Dialog显隐
            // 当前节点数据
            properties: {},
            // 发起人  start节点和condition节点需要
            initiator: {},
            priorityLength: 0, // 当为条件节点时  显示节点优先级选项的数据
            startForm: {
                formOperates: [],
            },
            approverForm: JSON.parse(JSON.stringify(defaultApproverForm)),
            assigneeTypeOptions: [
                {
                    label: '指定成员',
                    value: 'user',
                },
                {
                    label: '指定角色',
                    value: 'role',
                },
                {
                    label: '发起人',
                    value: 'myself',
                },
                {
                    label: 'activiti表达式',
                    value: 'input',
                },
            ],
            copyAssigneeTypeOptions: [
                {
                    label: '指定成员',
                    value: 'user',
                },
                {
                    label: '指定角色',
                    value: 'role',
                },
                {
                    label: 'activiti表达式',
                    value: 'input',
                },
            ],
        };
    },
    computed: {
        usedFormItems() {
            return this.$store.state.designer.formItemList;
        },
    },
    directives: {
        Clickoutside,
    },
    methods: {
        getFormOperates() {
            let res = [];
            this.isApproverNode() && (res = this.approverForm.formOperates);
            this.isStartNode() && (res = this.startForm.formOperates);
            return res;
        },
        resetOrgColl() {
            this.approverForm.approvers = [];
            this.approverForm.approverRoles = [];
        },
        onOrgChange(data) {},
        timeTangeLabel(item) {
            const index = ['fc-time-duration', 'fc-date-duration'].findIndex(t => t === item.tag);
            if (index > -1) {
                return '时长' + ['(小时)', '(天)'][index];
            } else {
                return item.label;
            }
        },
        getAssignTypeLabel() {
            const res = this.assigneeTypeOptions.find(t => t.value === this.approverForm.assigneeType);
            return res ? res.label : '';
        },
        changeAllFormOperate(val) {
            const target = this.isStartNode() ? this.startForm : this.approverForm;
            target.formOperates.forEach(t => (t.formOperate = val));
        },
        // 是否可以显示当前条件组件
        couldShowIt(item, ...tag) {
            return tag.includes(item.tag) && this.showingPCons.includes(item.formId);
        },

        initFormOperates(target) {
            const formOperates = (target.properties && target.properties.formOperates) || [];
            // 自定义组件不加入权限控制
            const res = [];
            const defaultRead = true;
            const defaultWrite = false;
            const getReadPermissionById = id => {
                const permission = formOperates.find(t => t.formId === id);
                return permission !== undefined ? permission.read : defaultRead;
            };
            const getWritePermissionById = id => {
                const permission = formOperates.find(t => t.formId === id);
                return permission !== undefined ? permission.write : defaultWrite;
            };
            const format = (list, parentName = '') =>
                list.map(t => {
                    const data = {
                        formId: t.formId,
                        required: t.required,
                        vModel: t.vModel,
                        label: parentName ? [parentName, t.label].join('.') : t.label,
                        read: getReadPermissionById(t.formId),
                        write: getWritePermissionById(t.formId),
                    };
                    res.push(data);
                    Array.isArray(t.children) && format(t.children, t.label);
                });
            const formItems = this.$store.state.designer.formItemList.filter(t => t.cmpType !== 'custom');
            format(formItems);
            return res;
        },
        initCopyNodeData() {
            Object.assign(this.approverForm, this.value.properties);
        },

        initStartNodeData() {
            this.initInitiator();
            this.activeName = 'formAuth';
            this.startForm.formOperates = this.initFormOperates(this.value);
        },

        copyNodeConfirm() {
            if (!this.properties.title) {
                this.$message({
                    message: '请输入节点名称',
                    type: 'error',
                });
                return;
            }
            const assigneeType = this.approverForm.assigneeType;
            let content = '';
            if (assigneeType === 'user') {
                this.approverForm.approverRoles = [];
                this.approverForm.expression = '';
                content = this.getOrgSelectLabel('approver-' + assigneeType);
                if (!this.approverForm.approvers.length) {
                    this.$message({
                        message: '请设置审批人',
                        type: 'error',
                    });
                    return;
                }
            } else if (assigneeType === 'role') {
                this.approverForm.approvers = [];
                this.approverForm.expression = '';
                content = this.getOrgSelectLabel('approver-' + assigneeType);
                if (!this.approverForm.approverRoles.length) {
                    this.$message({
                        message: '请设置审批人',
                        type: 'error',
                    });
                    return;
                }
            } else if (assigneeType === 'input') {
                this.approverForm.approvers = [];
                this.approverForm.approverRoles = [];
                content = '自定义';
                if (!this.approverForm.expression) {
                    this.$message({
                        message: '请设置activiti表达式',
                        type: 'error',
                    });
                    return;
                }
            }

            //const formOperates = this.approverForm.formOperates.map(t => ({ formId: t.formId, formOperate: t.formOperate }));
            //Object.assign(this.properties, this.approverForm, { formOperates });
            Object.assign(this.properties, this.approverForm);
            this.$emit('confirm', this.properties, content || '请设置抄送人');
            this.visible = false;
        },

        /**
         * 条件节点确认保存得回调
         */
        conditionNodeComfirm() {
            if (!this.condition) {
                this.$message({
                    message: '请输入条件说明',
                    type: 'error',
                });
                return;
            }
            if (!this.conditionLabel) {
                this.$message({
                    message: '请输入条件说明',
                    type: 'error',
                });
                return;
            }

            let nodeContent = this.conditionLabel;
            this.properties.condition = this.condition;
            this.properties.conditionLabel = this.conditionLabel;
            this.properties.conditions = [{ condition: this.condition, conditonLabel: this.conditionLabel }];

            this.$emit('confirm', this.properties, nodeContent || '请设置条件');
            this.visible = false;
        },
        getOrgSelectLabel(type) {
            return this.$refs[type + '-org']['selectedLabels'];
        },
        /**
         * 开始节点确认保存
         */
        startNodeComfirm() {
            //TODO 支持选择发起人
            this.properties.initiator = 'all';
            const formOperates = this.startForm.formOperates;
            Object.assign(this.properties, { formOperates });
            this.$emit('confirm', this.properties, '所有人');
            this.visible = false;
        },
        /**
         * 审批节点确认保存
         */
        approverNodeComfirm() {
            if (!this.properties.title) {
                this.$message({
                    message: '请输入节点名称',
                    type: 'error',
                });
                return;
            }
            const assigneeType = this.approverForm.assigneeType;
            let content = '';
            if ('myself' === assigneeType) {
                content = this.assigneeTypeOptions.find(t => t.value === assigneeType).label;
                this.approverForm.approvers = [];
                this.approverForm.approverRoles = [];
                this.approverForm.expression = '';
            } else if (assigneeType === 'user') {
                this.approverForm.approverRoles = [];
                this.approverForm.expression = '';
                content = this.getOrgSelectLabel('approver-' + assigneeType);
                if (!this.approverForm.approvers.length) {
                    this.$message({
                        message: '请设置审批人',
                        type: 'error',
                    });
                    return;
                }
            } else if (assigneeType === 'role') {
                this.approverForm.approvers = [];
                this.approverForm.expression = '';
                content = this.getOrgSelectLabel('approver-' + assigneeType);
                if (!this.approverForm.approverRoles.length) {
                    this.$message({
                        message: '请设置审批人',
                        type: 'error',
                    });
                    return;
                }
            } else if (assigneeType === 'input') {
                this.approverForm.approvers = [];
                this.approverForm.approverRoles = [];
                content = '自定义';
                if (!this.approverForm.expression) {
                    this.$message({
                        message: '请设置activiti表达式',
                        type: 'error',
                    });
                    return;
                }
            }

            this.approverForm.title = this.properties.title;
            //const formOperates = this.approverForm.formOperates.map(t => ({ formId: t.formId, formOperate: t.formOperate }));
            //Object.assign(this.properties, this.approverForm, { formOperates });
            Object.assign(this.properties, this.approverForm);
            this.$emit('confirm', this.properties, content || '请设置审批人');
            this.visible = false;
        },
        // 确认修改
        confirm() {
            this.isCopyNode() && this.copyNodeConfirm();
            this.isStartNode() && this.startNodeComfirm();
            this.isApproverNode() && this.approverNodeComfirm();
            this.isConditionNode() && this.conditionNodeComfirm();
        },
        // 关闭抽屉
        cancel() {
            setTimeout(() => {
                this.$emit('cancel');
                this.visible = false;
            }, 0);
        },
        /**
         * 删除流程条件
         */
        onDelCondition(condition) {
            this.pconditions.splice(index, 1);
        },
        // 配合getPriorityLength 获取前一个节点条件数组长度 用于设置优先级
        getPrevData() {
            return NodeUtils.getPreviousNode(this.value.prevId, this.processData);
        },
        // 用于获取节点优先级范围
        getPriorityLength() {
            this.priorityLength = this.getPrevData().conditionNodes.length;
        },
        // 判断是否是条件节点
        isConditionNode() {
            return this.value ? NodeUtils.isConditionNode(this.value) : false;
        },
        /** 判断是否是审批节点*/
        isApproverNode() {
            return this.value ? NodeUtils.isApproverNode(this.value) : false;
        },

        isStartNode() {
            return this.value ? NodeUtils.isStartNode(this.value) : false;
        },

        isCopyNode() {
            return this.value ? NodeUtils.isCopyNode(this.value) : false;
        },

        initInitiator() {
            const initiator = this.value.properties && this.value.properties.initiator;
        },
        /**
         * 初始化审批节点所需数据
         */
        initApproverNodeData() {
            Object.assign(this.approverForm, this.value.properties);
            this.approverForm.formOperates = this.initFormOperates(this.value);
            this.activeName = 'config';
        },
        /**
         * 初始化条件节点数据
         */
        initConditionNodeData() {
            // 初始化条件表单数据
            this.condition = this.value.properties && this.value.properties.condition;
            this.conditionLabel = this.value.properties && this.value.properties.conditionLabel;
            this.activeName = 'config';
        },
    },
    watch: {
        visible(val) {
            if (!val) {
                this.approverForm = JSON.parse(JSON.stringify(defaultApproverForm)); // 重置数据为默认状态
                return;
            }
            this.isStartNode() && this.initStartNodeData();
            this.isApproverNode() && this.initApproverNodeData();
            this.isConditionNode() && this.initConditionNodeData();
            this.isCopyNode() && this.initCopyNodeData();
        },

        value(newVal) {
            if (newVal && newVal.properties) {
                this.visible = true;
                this.properties = JSON.parse(JSON.stringify(newVal.properties));
                if (this.properties) {
                    NodeUtils.isConditionNode(newVal) && this.getPriorityLength();
                }
            }
        },
    },
};
</script>
<style lang="stylus">
.condition-dialog {
  .el-dialog__header{
    border-bottom : 1px solid #eee;
  }
}
</style>
<style lang="stylus" scoped>
.drawer {
  >>> .el-drawer__header {
    margin-bottom: 0;
    border-bottom: 1px solid #c5c5c5;
    padding-bottom: 8px;
  }

  >>> .el-drawer__body {
    padding-bottom: 44px;
    overflow: hidden;
  }

  .pane-tab{
    height: 100%;
  }

  .pane-tab >>>  .el-tabs__item.is-top:nth-child(2) {
    padding-left: 20px;
  }

  >>> .el-tabs__item:focus{
    box-shadow: none !important;
  }

  >>> .el-tabs__header {
    margin-bottom: 0;
  }
}

.header {
  line-height: 28px;
}

.actions {
  position: absolute;
  bottom: 0;
  left: 0;
  padding: 6px 12px;
  width: 100%;
  box-sizing: border-box;
  text-align: right;
}

.radio-item {
  width: 110px;
  padding: 6px;
}

.priority-select {
  width: 118px;
  position: absolute;
  right: 26px;
}

.form-auth-table{
  font-size: 14px;
  padding-left: 10px;
  padding-right: 10px;
  .auth-table-header{
    background: #fafafa
    line-height: 40px;
  }
  .row{
    display: flex;
    align-items: center;
    line-height: 32px;
    padding: 8px 12px;
    border-bottom: 1px solid #efefef;
    &:hover{
      background: #f5f7fa;
    }
    .label{
      flex:1;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;

      .required{
        color: #f2725e;
      }
    }
    .radio-group{
      flex: 2;
      display: flex;
      justify-content: space-between;
    }
  }
}

.approver-pane{
  overflow-y: scroll;
  overflow-x: hidden;
  .option-box {
    font-size 14px
    padding-left 1rem
  }
}

.condition-pane{
  height 100%
  overflow scroll
}
</style>
