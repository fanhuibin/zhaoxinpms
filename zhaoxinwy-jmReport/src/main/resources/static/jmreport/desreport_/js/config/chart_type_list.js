/***
 * 图表选择modal数据
 * @type {*[]}
 */
var chartTypeList= [
        {
            "label": "柱形图",
            "name": "bar",
            "typeList": [{
                "id": "bar.simple",
                "name": "普通柱形图",
                "type": "bar.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/bar-1.jpg`,
                "allowed":true
            },{
                "id": "bar.background",
                "name": "带背景柱形图",
                "type": "bar.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/bar-2.jpg`,
                "allowed":true
            }, {
                "id": "bar.multi",
                "name": "多数据对比柱形图",
                "type": "bar.multi",
                "imgUrl": `/jmreport/desreport_/chartsImg/bar-3.jpg`,
                "allowed":true
            }, {
                "id": "bar.negative",
                "name": "正负条形图",
                "type": "bar.stack.horizontal",
                "imgUrl": `/jmreport/desreport_/chartsImg/bar-4.jpg`,
                "allowed":true
            }, {
                "id": "bar.stack",
                "name": "堆叠柱形图",
                "type": "bar.stack",
                "imgUrl": `/jmreport/desreport_/chartsImg/bar-5.png`,
                "allowed":true
            } ,{
                "id": "bar.stack.horizontal",
                "name": "堆叠条形图",
                "type": "bar.stack.horizontal",
                "imgUrl": `/jmreport/desreport_/chartsImg/bar-6.png`,
                "allowed":true
            },{
                "id": "bar.multi.horizontal",
                "name": "多数据条形柱状图",
                "type": "bar.multi.horizontal",
                "imgUrl": `/jmreport/desreport_/chartsImg/bar-7.png`,
                "allowed":true
            }]
        },
        {
            "label": "折线图",
            "name": "line",
            "typeList": [{
                "id": "line.simple",
                "name": "普通折线图",
                "type": "line.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/line-1.jpg`,
                "allowed":true
            }, {
                "id": "line.area",
                "name": "面积堆积折线图",
                "type": "line.area",
                "imgUrl": `/jmreport/desreport_/chartsImg/line-2.jpg`,
                "allowed":true
            }, {
                "id": "line.smooth",
                "name": "平滑曲线折线图",
                "type": "line.smooth",
                "imgUrl": `/jmreport/desreport_/chartsImg/line-3.jpg`,
                "allowed":true
            }, {
                "id": "line.multi",
                "name": "多数据对比折线图",
                "type": "line.multi",
                "imgUrl": `/jmreport/desreport_/chartsImg/line-4.png`,
                "allowed":true
            }, {
                "id": "line.step",
                "name": "阶梯折线图",
                "type": "line.step",
                "imgUrl": `/jmreport/desreport_/chartsImg/line-6.png`,
                "allowed":true
            }]
        },
        {
            "label": "饼图",
            "name": "pie",
            "typeList": [{
                "id": "pie.simple",
                "name": "普通饼图",
                "type": "pie.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/pie-1.png`,
                "allowed":true
            }, {
                "id": "pie.doughnut",
                "name": "环状饼图",
                "type": "pie.doughnut",
                "imgUrl": `/jmreport/desreport_/chartsImg/pie-2.png`,
                "allowed":true
            }, {
                "id": "pie.rose",
                "name": "南丁格尔玫瑰饼图",
                "type": "pie.rose",
                "imgUrl": `/jmreport/desreport_/chartsImg/pie-3.png`,
                "allowed":true
            }]
        },
        {
            "label": "折柱图",
            "name": "mixed.linebar",
            "typeList": [{
                "id": "mixed.linebar",
                "name": "普通折柱图",
                "type": "mixed.linebar",
                "imgUrl": `/jmreport/desreport_/chartsImg/mix-line-bar.jpg`,
                "allowed":true
            }]
        },
        {
            "label": "散点图",
            "name": "scatter",
            "typeList": [{
                "id": "scatter.simple",
                "name": "普通散点图",
                "type": "scatter.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/scatter-simple.jpg`,
                "allowed":true
            },{
                "id": "scatter.bubble",
                "name": "气泡散点图",
                "type": "scatter.bubble",
                "imgUrl": `/jmreport/desreport_/chartsImg/scatter_bubble.png`,
                "allowed":true
            }]
        },
        {
            "label": "漏斗图",
            "name": "funnel",
            "typeList": [{
                "id": "funnel.simple",
                "name": "普通漏斗图",
                "type": "funnel.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/funnel.png`,
                "allowed": true
            },{
                "id": "funnel.pyramid",
                "name": "金字塔漏斗图",
                "type": "funnel.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/funnel.pyramid.png`,
                "allowed": true
            }]
        },
        {
            "label": "雷达图",
            "name": "radar",
            "typeList": [{
                "id": "radar.basic",
                "name": "普通雷达图",
                "type": "radar.basic",
                "imgUrl": `/jmreport/desreport_/chartsImg/radar.png`,
                "allowed":true
            },{
                "id": "radar.custom",
                "name": "圆形雷达图",
                "type": "radar.basic",
                "imgUrl": `/jmreport/desreport_/chartsImg/radar-2.png`,
                "allowed":true
            }]
        },
        {
            "label": "象形图",
            "name": "pictorial",
            "typeList": [{
                "id": "pictorial.spirits",
                "name": "普通象形图",
                "type": "pictorial.spirits",
                "imgUrl": `/jmreport/desreport_/chartsImg/pictorialBar-spirit.png`,
                "allowed": true
            }]
        },
        {
            "label": "地图",
            "name": "map",
            "typeList": [{
                "id": "map.simple",
                "name": "区域地图",
                "type": "map.simple",
                "imgUrl": "/jmreport/desreport_/chartsImg/map.png",
                "allowed": true
               },{
                "id": "map.scatter",
                "name": "点地图",
                "type": "map.scatter",
                "imgUrl": `/jmreport/desreport_/chartsImg/map_scatter.png`,
                "allowed":true
            }]
        },
        {
            "label": "仪表盘",
            "name": "gauge",
            "typeList": [{
                "id": "gauge.simple",
                "name": "360°仪表盘",
                "type": "gauge.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/gauge.png`,
                "allowed": true
            },{
                "id": "gauge.simple180",
                "name": "180°仪表盘",
                "type": "gauge.simple180",
                "imgUrl": `/jmreport/desreport_/chartsImg/gauge180.png`,
                "allowed": true
            }]
        },
        {
            "label": "关系图",
            "name": "graph",
            "typeList": [{
                "id": "graph.simple",
                "name": "普通关系图",
                "type": "graph.simple",
                "imgUrl": `/jmreport/desreport_/chartsImg/graph.png`,
                "allowed": true
            }]
        }
    ]