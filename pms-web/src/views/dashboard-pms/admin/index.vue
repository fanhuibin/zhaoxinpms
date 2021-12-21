<template>
  <div class="dashboard-editor-container">
    <el-row style="margin-bottom:10px;border-radius: 4px;height:140px;margin-left: -5px; margin-right: -5px;">
        <dataList ref="dataList"/>
    </el-row>

    <el-row :gutter="10" style="height:500px">
      <el-col :span="12" style="height:500px">
        <pieChart ref="pieChart"/>
      </el-col>
      <el-col :span="12" style="height:450px">
        <barChart ref="barChart"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import echarts from 'echarts' 
import LineChart from './components/LineChart'
import CountTo from 'vue-count-to'
import dataList from './components/dataList'
import barChart from './components/barChart'
import pieChart from './components/pieChart'
import request from "@/utils/request";
export default {
  name: 'DashboardAdmin',
  components: {
    LineChart,
    CountTo,
    dataList,
    barChart,
    pieChart
  },
  data() {
    return {
      value1: '',
      charts: '',
    }
  },
  created() {
    this.init();
  },
  methods: {
      init() {
        request({
            url: `/statistics/dashboard`,
            method: "get",
        }).then((res) => {
            this.$nextTick(() => {
                this.$refs.dataList.initData(res.data.delayFee,res.data.needPay);
                this.$refs.pieChart.initChart(res.data.houseCount);
                this.$refs.barChart.initChart(res.data.monthPay);
            });
        });
      }
  }
}
</script>

<style lang="scss" scoped>
.componey {
  font-size: 14px;
  text-align: center;
  color: #999;
  padding-bottom: 10px;
}

.dashboard-editor-container {
  background-color: #ebeef5;
  position: relative;
  width: 100%;
  overflow: hidden;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 20px;
  }
}
.dateSelect {
  position: absolute;
  right: 67px;
  top: 38px;
  z-index: 100;
  .el-date-editor {
    width: 160px;
  }
}
@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>