
<template>
  <el-dialog :title="title" :close-on-click-modal="false"
    class="Jdialog Jdialog_center transfer-dialog" lock-scroll append-to-body
    v-bind="$attrs" width="800px" :modal-append-to-body="false" v-on="$listeners" @open="onOpen">
    <transfer :loading="loading" :treeData="treeData" v-model="selectedData" :type="type"
      ref="transfer" />
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeTransfer">取消</el-button>
      <el-button type="primary" @click="confirm">确认</el-button>
    </span>
  </el-dialog>
</template>

<script>
import transfer from '@/components/Transfer'
import request from '@/utils/request';

export default {
  name: 'org-transfer',
  components: { transfer },
  props: {
    value: {
      type: Array,
      default: () => []
    },
    type: {
      type: String,
      default: 'user'
    },
    title: {
      type: String,
      default: '组织机构'
    },
  },
  data() {
    return {
      loading: false,
      treeData: [],
      selectedData: []
    }
  },
  methods: {
    onOpen() {
      this.dataInit()
    },
    closeTransfer() {
      this.$emit('update:visible', false)
    },
    confirm() {
      this.$emit('confirm', this.selectedData)
      this.closeTransfer()
    },
    async dataInit() {
      this.loading = true
      this.selectedData = []
      this.$nextTick(() => {
        this.$refs.transfer && (this.$refs.transfer.filterText = '')
      })
      let res = null
      if (this.type == 'role') {
        res = await request({
            url: '/workflow/treeSelect/roleSelect' ,
            method: 'get',
        });
      } else {
        res = await request({
            url: '/workflow/treeSelect/userSelect' ,
            method: 'get',
        });
      }
      this.treeData = res.data
      this.selectedData = this.value
      this.loading = false
    }
  }
}
</script>