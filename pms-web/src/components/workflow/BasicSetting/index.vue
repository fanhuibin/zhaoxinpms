<template>
  <div class="setting-container">
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px"
      label-position="right">
      <el-form-item label="流程名称" prop="flowName">
        <el-input v-model="formData.flowName" placeholder="请输入流程名称" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="流程编码" prop="flowCode">
        <el-input v-model="formData.flowCode" placeholder="请输入流程编码" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
      <!--
      <el-form-item label="流程分组" prop="flowGroup">
        <el-select v-model="formData.flowGroup" placeholder="请选择流程分组" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in flowGroupOptions" :key="index" :label="item.label"
            :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      -->
      <el-form-item label="模板图标" prop="icon">
        <div :style="{backgroundColor: formData.flowIconBackground}" style="width:44px;height:44px;margin-left:30px;border-radius:5px;">
            <i :class="formData.flowIcon" style="font-size: 24px;color: white;padding-left:10px;padding-top:10px;" />
        </div>
        <div style="vertical-align: middle;">
            <el-button  plain size="mini" @click="dialogVisible = true" style="margin-left: 10px;">选择图标</el-button>
            <el-color-picker v-model="formData.flowIconBackground" title="图标背景色" style="vertical-align: middle;"
                        :predefine="['#008cff', '#35b8e0', '#00cc88','#ff9d00','#ff4d4d', '#5b69bc', '#ff8acc', '#3b3e47','#282828']" />
        </div>
      </el-form-item>
      <!--
      <el-form-item label="流程说明" prop="flowRemark">
        <el-input v-model="formData.flowRemark" type="textarea" placeholder="请输入流程说明" :maxlength="100"
          show-word-limit :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
      -->
    </el-form>
    <el-dialog
      title="选择图标"
      :visible.sync="dialogVisible"
      width="612px" height="500px">
      <div style="height:350px;overflow-y:scroll;margin-left:10px;">
        <i 
        v-for="(icon, index) in elementIcons" 
        :key="index" :src="icon.src" 
        style="font-size:24px;"
        class="icon-item" 
        :class="[{active: selectedIcon === icon}, icon]"
        @click="selectedIcon = icon"/>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false; selectedIcon = formData.flowIcon" size="small">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false; formData.flowIcon = selectedIcon" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import elementIconsJSON from './element-icons.json'
export default {
  components: {},
  props: ['tabName', 'initiator', 'conf'],
  data() {
    const elementIcons = elementIconsJSON.map(name => `el-icon-${name}`)
    return {
      dialogVisible: false,
      selectedIcon: elementIcons[0],
      formData: {
        flowName: '',
        flowIcon: elementIcons[0],
        flowIconBackground: 'rgb(211,82,70)',
        flowGroup: undefined,
        flowRemark: undefined,
        initiator: null
      },
      rules: {
        flowName: [{
          required: true,
          message: '请输入流程名称',
          trigger: 'blur'
        }],
        flowCode: [{
          required: true,
          message: '请输入流程编码',
          trigger: 'blur'
        }],
        // flowGroup: [{
        //   required: true,
        //   message: '请选择流程分组',
        //   trigger: 'blur'
        // }],
      },
      elementIcons,
    }
  },
  created() {
    if (typeof this.conf === 'object' && this.conf !== null) {
      Object.assign(this.formData, this.conf)
    }
  },
  methods: {
    emitInitiator(){
      this.$nextTick(()=>{
        this.$emit('initiatorChange', this.formData.initiator, this.$refs['org-select'].selectedLabels)
      })
    },
    // 给父级页面提供得获取本页数据得方法
    getData() {
      return new Promise((resolve, reject) => {
        this.$refs['elForm'].validate(valid => {
          if (!valid) {
            reject({ target: this.tabName})
            return
          }
          resolve({ formData: this.formData, target: this.tabName})  // TODO 提交表单
        })
      })
    },
  },
  watch:{
    initiator:{
      handler (val) {
        this.formData.initiator = val
      },
      immediate: true
    }
  }
}
</script>
<style lang="stylus" scoped>
.icon-item{
  width 40px
  height 40px 
  margin: 6px;
  position relative
  cursor pointer
  opacity .5

  &.active{
    opacity 1
  }
  &:hover{
    opacity 1
  }
}

.setting-container{
  width: 600px;
  height: 100%;
  margin: auto;
  background: white;
  padding: 16px;

  >>>.el-form--label-top .el-form-item__label{
    padding-bottom: 0;
  }
}
</style>>

