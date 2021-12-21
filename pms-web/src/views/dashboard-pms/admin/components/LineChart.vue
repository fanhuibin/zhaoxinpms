<template>
  <div id="chart" style="width:100%;height:400px;margin-top:30px;"></div>
</template>

<script>
import echarts from 'echarts'
import resize from './mixins/resize'
export default {
  name: 'chart',
  mixins: [resize],
  data() {
    return {
      chart: null,
      option: {
        title: {
          text: '实时数据',
          textStyle: {
            fontSize: 16,
            fontWeight: 900,
            color: '#333'
          }
        },
        grid: {
          x: 80,
          y: 50,
          x2: 50,
          y2: 60,
          borderWidth: 0
        },
        // legend: {
        //   data: ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎'],
        //   top : '55%',
        //   orient: 'vertical',
        //   textStyle:{//图例文字的样式
        //     color:'#dbdbdb' 
        //   }
        // },
        tooltip: {
          trigger: 'axis',
          // formatter:function(){
          //   return '整天数据'+"<br>"+'交易'
          // }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['2AM', '4AM', '6AM', '8AM', '10AM',
            '12AM', '2PM', '4PM', '6PM', '8PM', '10PM', '12PM'],
          axisLine: {
            show: false, //是否显示x轴
            lineStyle: {
              // color: '#dbdbdb', // y坐标轴的轴线颜色
              // width: 1, //这里是坐标轴的宽度,可以去掉
            }
          },
          axisTick: {
            show: false, //是否显示刻度
          },
          // X轴的字体样式
          axisLabel: {
            show: true, //这行代码控制着坐标轴y轴的文字是否显示
            textStyle: {
              color: '#666', //X轴上的字体颜色
              fontSize: '12', // X轴字体大小

            }
          },
        },
        yAxis: {
          type: 'value',
          // y轴的颜色和宽度
          axisLine: {
            show: false, //是否显示y轴
            lineStyle: {
              // color: '#000', // y坐标轴的轴线颜色
              // width: 1, //这里是坐标轴的宽度,可以去掉
            }
          },
          axisTick: {
            show: false, //是否显示刻度
          },
          // y轴的字体样式
          axisLabel: {
            show: true, //这行代码控制着坐标轴y轴的文字是否显示
            textStyle: {
              color: '#666', //y轴上的字体颜色
              fontSize: '12' // y轴字体大小
            }
          },
        },
        series: [{
          data: [0, 2820, 8932, 5700, 7901, 4934, 5000, 3000, 4090, 2330, 3820, 0],
          type: 'line',
          radius: '100%',
          center: ['100%', '50%'],
          areaStyle: {},
          smooth: true,
          areaStyle: {
            color: '#d0e2f3'
          },
          itemStyle: {
            normal: {
              color: '#1890FF', //改变折线点的颜色
              lineStyle: {
                color: '#1890FF' //改变折线颜色
              }
            }
          },
          stack: '总量',
        }]
      }
    }
  },
  mounted() {
    this.initChart()
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(document.getElementById('chart'))
      this.chart.setOption(this.option);
      window.onresize = this.chart.resize()
    }
  }
}
</script>
