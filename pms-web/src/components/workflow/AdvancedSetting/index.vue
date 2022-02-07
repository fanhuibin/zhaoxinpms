<template>
  <div class="setting-container">
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px"
      label-position="top">
      <el-form-item label="审批意见">
        <el-checkbox v-model="formData.remarkRequired">必填</el-checkbox>
        <el-checkbox v-model="formData.notVisibleForSponsor">对发起人不可见</el-checkbox>
      </el-form-item>
      <el-form-item label="消息提醒" prop="remarkTip">
         <el-checkbox v-model="formData.sms">短信消息提醒</el-checkbox>
          <el-checkbox v-model="formData.wechat">微信消息提醒</el-checkbox>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  components: {},
  props: ['conf'],
  data() {
    return {
      formData: {
        autoRepeat: false, //审批人去重
        myAuditAutoPass: false, //发起人审批时自动通过
        remarkTip: '', //审批意见填写提示
        remarkRequired: false,
        notVisibleForSponsor: false
      },
      rules: {
        autoRepeat: [{
          required: true,
          message: '请选择选择分组',
          trigger: 'change'
        }],
      },
      autoRepeatOptions: [{
        "label": "启用自动去重",
        "value": true
      }, {
        "label": "不启用自动去重",
        "value": false
      }],
    }
  },
  created() {
    if (typeof this.conf === 'object' && this.conf !== null) {
      Object.assign(this.formData, this.conf)
    }
  },
  methods: {
    getData() {
      return this.formData
    },
  }
}

</script>
<style lang="stylus" scoped>
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

