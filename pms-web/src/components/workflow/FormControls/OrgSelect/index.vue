<template>
  <div class="org-select" :class="{'org-select__button':buttonType === 'button'}">
    <div class="tags">
      <el-button v-if="buttonType === 'button'" icon="el-icon-plus" @click="show = true"
        style="margin-bottom: 6px;">
        {{title}}
      </el-button>
      <div class="input-box" :class="{'as-input': buttonType === 'input'}" @click="show = true">
        <el-tag v-bind="tagConfig" class="org-tag" v-for="(item,i) in selectedData" :key="item.key"
          @close="onClose(item,i)">
          {{item.name}}
        </el-tag>
      </div>
    </div>
    <org-transfer ref="transfer" :value="innerValue" :title="title" :visible.sync="show"
      @confirm="onConfirm" :type="type" />
  </div>
</template>
<script>
import OrgTransfer from '../OrgTransfer'
export default {
  model: {
    prop: 'value',
    event: 'change'
  },
  components: { OrgTransfer },
  name: 'org-select',
  props: {
    value: {
      type: Array,
      default: () => ([]),
      required: true
    },
    title: {
      type: String,
      default: '组织机构'
    },
    type: {
      type: String,
      default: 'user'
    },
    buttonType: {
      type: String,
      default: 'input'
    }, // or input
    tagConfig: {
      type: Object,
      default: () => ({
        type: 'info',
        closable: true,
        'disable-transitions': false,
        hit: false,
        color: undefined,
        size: 'small',
        effect: 'light'
      })
    },
  },
  data() {
    return {
      show: false,
      innerValue: null,
      selectedData: []
    }
  },
  watch: {
    value: {
      handler: function (val) {
        if (!val) return
        this.reloadCmpData()
      },
      immediate: true,
      deep: true
    },
  },
  computed: {
    selectedLabels() {
      return this.selectedData.map(t => t.name).join(',')
    }
  },
  methods: {
    reloadCmpData() {
      this.innerValue = this.value
      // transfer 可能还未加载成功
      this.$nextTick(_ => {
        this.initSelectedData()
      })
    },
    async initSelectedData() {
      this.selectedData = this.innerValue;
    },
    onClose(item, i) {
      this.innerValue.splice(i, 1)
      this.selectedData.splice(i, 1)
      this.$emit('change', this.innerValue)
    },

    onConfirm(data) {
      this.innerValue = data
      this.$emit('change', this.innerValue)
    }
  }
}
</script>
<style lang="scss" scoped>
.tags {
  .input-box.as-input {
    border: 1px solid #dcdfe6;
    padding-left: 6px;
    font-size: 12px;
    min-height: 32px;
    line-height: 30px;
    border-radius: 4px;
    background: white;
    color: #606266;
    cursor: pointer;
  }

  .org-tag {
    margin-right: 6px;
    // max-width: 6rem;
    overflow: hidden;
    text-overflow: ellipsis;
    position: relative;
    padding-right: 1rem;
    vertical-align: middle;

    >>> .el-tag__close {
      position: absolute;
      right: 2px;
      top: 50%;
      margin-top: -7px;
    }
  }
}
.org-select__button {
  .org-tag {
    margin-bottom: 3px;
  }
}
</style>