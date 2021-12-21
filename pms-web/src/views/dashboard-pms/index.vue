<template>
  <el-scrollbar class="dashboard-container layout-area">
    <component :is="currentRole" />
  </el-scrollbar>
</template>

<script>
import adminDashboard from './admin'

export default {
  name: 'Dashboard',
  components: { adminDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  }
}
</script>
<style lang="scss" scoped>
.layout-area {
  height: 100%;
  overflow: hidden;
  >>> .el-scrollbar__wrap {
    margin-bottom: 0 !important;
    overflow-x: auto;
  }
  >>> .el-scrollbar__bar.is-horizontal > div {
    display: none;
  }
  >>> .el-card {
    width: 100%;
    height: 100%;
    .el-card__body {
      padding: 0;
    }
  }
  .vue-grid-item {
    position: relative;
    .mask {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 1;
    }
  }
}
</style>