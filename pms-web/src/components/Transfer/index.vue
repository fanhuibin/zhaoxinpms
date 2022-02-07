<template>
  <div class="transfer__body" v-loading="loading" :element-loading-text=" '加载中'"
    :style="{height}">
    <div class="transfer-pane">
      <div class="transfer-pane__tools">
        <el-input placeholder="输入关键词进行搜索" v-model="filterText" />
      </div>
      <div class="transfer-pane__body">
        <el-tree :data="treeData" :props="props" default-expand-all highlight-current ref="treeBox"
          :expand-on-click-node="false" check-on-click-node class="Jcommon-el-tree"
          node-key="id" show-checkbox v-loading="loading" :filter-node-method="filterNode"
          @check="onCheck">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <i :class="data.icon"></i>
            <span class="text">{{node.label}}</span>
          </span>
        </el-tree>
      </div>
    </div>
    <div class="transfer-pane">
      <div class="transfer-pane__tools">
        <span>已选</span>
        <el-button @click="removeAll" type="text">清空列表</el-button>
      </div>
      <div class="transfer-pane__body shadow right-pane">
        <template>
          <div v-for="(item, index) in selectedTextData" :key=" index" class="selected-item">
            <span>{{ item.name}}</span>
            <i class="el-icon-delete" @click="removeData(item,index)"></i>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'transfer',
  data() {
    return {
      selectedData: [],
      selectedTextData: [],
      filterText: ''
    }
  },
  props: {
    height: {
      type: String,
      default: "380px"
    },
    loading: {
      type: Boolean,
      default: false
    },
    treeData: {
      type: Array,
      default: () => []
    },
    value: {
      type: Array,
      default: () => []
    },
    props: {
      type: Object,
      default: () => {
        return { value: "id", label: "name", children: "children" };
      }
    },
    type: {
      type: String,
      default: ''
    }
  },
  watch: {
    filterText(val) {
      this.$refs.treeBox.filter(val);
    },
    value(val) {
      this.initHandle()
    },
  },
  methods: {
    initHandle() {
      if (this.value.length) {
        const selectKey = this.value.map(item=>item.id);
        this.$refs.treeBox.setCheckedKeys(selectKey)
        this.$nextTick(_ => {
          this.selectedTextData = this.$refs.treeBox.getCheckedNodes(true)
          this.selectedData = this.$refs.treeBox.getCheckedKeys(true)
        })
      } else {
        this.$refs.treeBox.setCheckedKeys([])
        this.selectedTextData = []
        this.selectedData = []
      }
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    onCheck(data, checked) {
      if (this.type) {
        let list = this.$refs.treeBox.getCheckedNodes(true)
        this.selectedTextData = list.filter(o => o.type == this.type)
      } else {
        this.selectedTextData = this.$refs.treeBox.getCheckedNodes(true)
      }
      this.selectedData = this.selectedTextData.map(o => o.id)
      this.$emit('input', this.selectedTextData)
      this.$emit('getValue', this.selectedData, this.selectedTextData)
    },
    removeData(item, index) {
      this.$refs.treeBox.setChecked(item.id, false)
      this.selectedData.splice(index, 1)
      this.selectedTextData.splice(index, 1)
      this.$emit('input', this.selectedTextData)
      this.$emit('getValue', this.selectedData, this.selectedTextData)
    },
    removeAll() {
      this.$refs.treeBox.setCheckedKeys([])
      this.selectedData = []
      this.selectedTextData = []
      this.$emit('input', this.selectedTextData)
      this.$emit('getValue', this.selectedData, this.selectedTextData)
    },
  }
};
</script>
<style lang="scss" scoped>
.transfer__body {
  line-height: 32px;
  display: flex;
  justify-content: space-around;
  padding-top: 10px;
  .transfer-pane {
    width: 360px;
  }
  .transfer-pane__tools {
    margin-bottom: 8px;
    height: 32px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .transfer-pane__body {
    position: relative;
    width: 100%;
    height: calc(100% - 40px);
    overflow: auto;
    overflow-x: hidden;
    font-size: 14px;
    border: 1px solid #dcdfe6;
  }

  .right-pane {
    box-sizing: border-box;
    overflow: auto;
    border: 1px solid #dcdfe6;

    .selected-item {
      padding: 0px 12px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      &:hover {
        background-color: #f5f7fa;
      }

      span {
        max-width: 90%;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      i:hover {
        color: #f56c6c;
        cursor: pointer;
      }
    }
  }
}
</style>