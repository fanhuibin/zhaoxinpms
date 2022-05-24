<template>
    <div class="Jcommon-layout">
        <div class="page Jcommon-layout-center">
            <header class="page__header">
                <div class="page-actions">
                    <div>{{ title }}</div>
                </div>
                <div class="step-tab">
                    <div v-for="(item, index) in steps" :key="index" class="step" :class="[activeStep == item.key ? 'active' : '']" @click="changeSteps(item)">
                        <span class="step-index">{{ index + 1 }}</span>
                        {{ item.label }}
                    </div>
                    <div class="ghost-step step" :style="{ transform: translateX }"></div>
                </div>
                <div class="designer-actions">
                    <el-button size="small" class="publish-btn" @click="close">返回</el-button>
                    <el-button size="small" class="publish-btn" @click="publish">提交</el-button>
                </div>
            </header>
            <section class="Jcommon-layout-main Jflex-main" v-if="mockData">
                <BasicSetting
                    ref="basicSetting"
                    :conf="mockData.basicSetting"
                    v-show="activeStep === 'basicSetting'"
                    tabName="basicSetting"
                    @initiatorChange="onInitiatorChange"
                />
                <DynamicForm ref="formDesign" :conf="mockData.formData" v-show="activeStep === 'formDesign'" tabName="formDesign" />
                <Process
                    ref="processDesign"
                    :conf="mockData.processData"
                    tabName="processDesign"
                    v-show="activeStep === 'processDesign'"
                    @startNodeChange="onStartChange"
                />
            </section>
        </div>
    </div>
</template>

<script>
// @ is an alias to /src
import Process from '@/components/workflow/Process';
import BasicSetting from '@/components/workflow/BasicSetting';
import DynamicForm from "@/views/tool/build";
import request from '@/utils/request';
const beforeUnload = function (e) {
    var confirmationMessage = '离开网站可能会丢失您编辑得内容';
    (e || window.event).returnValue = confirmationMessage; // Gecko and Trident
    return confirmationMessage; // Gecko and WebKit
};

export default {
    name: 'Home',
    props: {
        title: {
            type: String,
            default: '流程设计器',
        },
    },
    data() {
        return {
            id: null,
            mockData: null, // 可选择诸如 $route.param，Ajax获取数据等方式自行注入
            activeStep: 'basicSetting', // 激活的步骤面板
            steps: [
                { label: '基础设置', key: 'basicSetting' },
                { label: "表单设计", key: "formDesign" },
                { label: '流程设计', key: 'processDesign' },
            ],
        };
    },
    beforeRouteEnter(to, from, next) {
        window.addEventListener('beforeunload', beforeUnload);
        next();
    },
    beforeRouteLeave(to, from, next) {
        window.removeEventListener('beforeunload', beforeUnload);
        next();
    },
    computed: {
        translateX() {
            return `translateX(${this.steps.findIndex(t => t.key === this.activeStep) * 100}%)`;
        },
    },
    mounted() {},
    methods: {
        init(id) {
            this.id = id;
            if (id == null) {
                this.mockData = {
                    basicSetting: {},
                    processData: {},
                    formData: {},
                };
            } else {
                request({
                    url: `/workflow/designer/${id}`,
                    method: 'get',
                }).then(res => {
                    this.mockData = JSON.parse(res.data.json);
                });
            }
        },
        changeSteps(item) {
            this.activeStep = item.key;
        },
        publish() {
            const getCmpData = name => this.$refs[name].getData();
            // basicSetting  formDesign processDesign 返回的是Promise 因为要做校验
            const p1 = getCmpData('basicSetting');
            const p2 = getCmpData('formDesign');
            const p3 = getCmpData('processDesign');
            Promise.all([p1, p2, p3])
                .then(res => {
                    const param = {
                        basicSetting: res[0].formData,
                        processData: res[2].formData,
                        formData: res[1].formData,
                    };
                    this.sendToServer(param);
                })
                .catch(err => {
                    err.target && (this.activeStep = err.target);
                    err.msg && this.$message.warning(err.msg);
                });
        },
        sendToServer(param) {
            if (this.id) {
                request({
                    url: `/workflow/designer/${this.id}`,
                    method: 'put',
                    data: param,
                }).then(res => {
                    this.$message({
                        message: res.msg,
                        type: 'success',
                        duration: 1000,
                        onClose: () => {
                            this.$emit('refresh', true);
                        },
                    });
                });
            } else {
                request({
                    url: `/workflow/designer`,
                    method: 'post',
                    data: param,
                }).then(res => {
                    this.$message({
                        message: res.msg,
                        type: 'success',
                        duration: 1000,
                        onClose: () => {
                            this.$emit('refresh', true);
                        },
                    });
                });
            }
            // console.log('配置数据', param);
        },
        close() {
            this.$emit('refresh', true);
        },
        exit() {
            this.$confirm('离开此页面您得修改将会丢失, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            })
                .then(() => {
                    this.$message({
                        type: 'success',
                        message: '模拟返回!',
                    });
                })
                .catch(() => {});
        },
        /**
         * 同步基础设置发起人和流程节点发起人
         */
        onInitiatorChange(val, labels) {
            const processCmp = this.$refs.processDesign;
            const startNode = processCmp.data;
            startNode.properties.initiator = val['dep&user'];
            startNode.content = labels || '所有人';
            processCmp.forceUpdate();
        },
        /**
         * 监听 流程节点发起人改变 并同步到基础设置 发起人数据
         */
        onStartChange(node) {
            const basicSetting = this.$refs.basicSetting;
            basicSetting.formData.initiator = { 'dep&user': node.properties.initiator };
        },
    },
    components: {
        Process,
        BasicSetting,
        DynamicForm,
    },
};
</script>
<style lang="stylus" scoped>
$header-height = 54px;
.page {
  width: 100%;
  margin-bottom: 60px;
  box-sizing: border-box;

  .page__header {
    width: 100%;
    height: $header-height;
    display flex;
    align-items center;
    justify-content center;
    justify-content: space-between;
    box-sizing: border-box;
    color: white;
    background: #3296fa;
    font-size: 14px;
    top: 0;

    .page-actions {
      height: 100%;
      text-align: center;
      line-height: $header-height;

      > div {
        height: 100%;
        padding-left: 20px;
        padding-right: 20px;
        display: inline-block;
      }

      i {
        font-size: 22px;
        vertical-align: middle;
      }
    }

    .step-tab {
      display: flex;
      justify-content: center;
      height: 100%;
      position: relative;

      >.step {
        width: 140px;
        line-height: $header-height;
        padding-left: 30px;
        padding-right: 30px;
        cursor: pointer;
        position: relative;

        &.ghost-step{
          position: absolute;
          height: $header-height;
          left: 0;
          z-index: -1;
          background: #4483f2;
          transition: transform .5s;

          &::after {
            content: '';
            border-width: 6px 6px 6px;
            border-style: solid;
            border-color: transparent transparent white;
            position: absolute;
            bottom: 0;
            left: 50%;
            margin-left: -6px;
          }
        }

        &.active >.step-index  {
          background: white;
          color: #4483f2;
        }

        >.step-index {
          display: inline-block;
          width: 18px;
          height: 18px;
          border: 1px solid #fff;
          border-radius: 8px;
          line-height: 18px;
          text-align: center;
          box-sizing: border-box;
        }
      }
    }
  }

  .page__content {
    width: 100%;
    height: 100%;
    overflow: hidden;
    box-sizing: border-box;
    background #f5f5f7
  }
}

.publish-btn {
  margin-right: 20px;
  color: #3296fa;
  padding: 7px 20px;
  border-radius: 4px;
  font-size: 14px;
}

.github{
  position fixed
  bottom 10px
  left 20px
}
</style>
