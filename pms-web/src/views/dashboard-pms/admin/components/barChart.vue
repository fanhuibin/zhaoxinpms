<template>
  <el-card shadow="never" class="portal-eChart-box">
    <div slot="header" class="portal-common-title">
      <span>{{title}}</span>
    </div>
    <div class="eChart-box-body">
      <div ref="chart" id="chart" v-show="!isEmpty" style="width:100%;height:400px;margin-top:10px;"></div>
    </div>
  </el-card>
</template>
<script>
import echarts from 'echarts'
import resize from './mixins/resize'
export default {
  mixins: [resize],
  data() {
    return {
      chart: null,
      currOption: {},
      isEmpty: false,
      title:'月度收费',
      option:{}
    }
  },
  mounted() {
  },
  watch: {
    option: {
      handler(val) {
        this.currOption = val
        this.isEmpty = JSON.stringify(val) === "{}"
        if (!this.isEmpty) this.initChart()
      },
      deep: true
    }
  },
  methods: {
    initChart(data) {
      this.chart = echarts.init(this.$refs.chart)
      var option = {
			"color": ["#1890ff"],
			"title": {
				"text": "",
				"subtext": ""
			},
			"tooltip": {
				"trigger": "axis",
				"axisPointer": {
					"type": "shadow"
				}
			},
			"grid": {
				"left": "3%",
				"right": "4%",
				"bottom": "3%",
				"containLabel": true
			},
			"xAxis": [{
				"type": "category",
				"data": data.month,
				"axisTick": {
					"alignWithLabel": true
				}
			}],
			"yAxis": [{
				"type": "value"
			}],
			"series": [{
				"name": "收款金额",
				"type": "bar",
				"barWidth": "60%",
				"data": data.value
			}]
		}
      this.chart.setOption(option)
      setTimeout(() => {
        this.$nextTick(() => {
          this.chart.resize();
        })
      }, 50);
    }
  },
  beforeDestroy() {
    if (!this.chart) return
    this.chart.dispose()
    this.chart = null
  }
}
</script>