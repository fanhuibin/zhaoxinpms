<script type="text/x-template" id="yAxis-setting-template">
    <div>
        <Submenu :name="'yAxis'+index" style="border-bottom: inset 1px;" v-for="(item,index) in yAxisOptions">
            <template slot="title">
                <span class="rightFontSize">{{getTitle(index)}}Y轴设置</span>
            </template>
            <div class="blockDiv" style="padding-bottom: 10px">
                <Row class="ivurow">
                    <p>显示&nbsp;&nbsp;</p>
                    <i-switch size="small" style="margin-left: 141px;" v-model="item.show" @on-change="onYAxisChange"/>
                </Row>
                <Row class="ivurow">
                    <p>Y轴名称&nbsp;&nbsp;</p>
                    <i-input class="rightFontSize iSelect" size="small" v-model="item.name" @on-blur="onYAxisChange" style="margin-left: 4px"></i-input>
                </Row>
                <Row class="ivurow">
                    <p>分隔线&nbsp;&nbsp;</p>
                    <i-switch size="small" style="margin-left: 128px;" v-model="item.splitLine_show" @on-change="onYAxisChange"/>
                </Row>
                <Row class="ivurow" v-if="typeof item.splitLine_show !== 'undefined'">
                    <p>颜色设置&nbsp;&nbsp;</p>
                    <Col>
                    <i-input class="rightFontSize iSelect" v-model="item.splitLine_lineStyle_color" size="small" @on-change="onYAxisChange">
                    <span slot="append">
                        <color-picker class="colorPicker" v-model="item.splitLine_lineStyle_color" :editable="false"  :transfer="true" size="small" @on-change="onYAxisChange"/>
                    </span>
                    </i-input>
                    </Col>
                </Row>
                <Row class="ivurow">
                    <p style="margin-bottom: 10px;">字体大小&nbsp;&nbsp;</p>
                    <i-input class="rightFontSize iSelect" size="small" type="number" v-model="item.axisLabel_textStyle_fontSize" @on-blur="onYAxisChange"></i-input>
                </Row>
                <Row class="ivurow" v-if="typeof item.axisLabel_textStyle_color !== 'undefined'">
                    <p>字体颜色&nbsp;&nbsp;</p>
                    <Col>
                    <i-input class="rightFontSize iSelect" v-model="item.axisLabel_textStyle_color" size="small" @on-change="onYAxisChange">
                    <span slot="append">
                        <color-picker class="colorPicker" v-model="item.axisLabel_textStyle_color" :editable="false"  :transfer="true" size="small" @on-change="onYAxisChange"/>
                    </span>
                    </i-input>
                    </Col>
                </Row>
                <Row class="ivurow" v-if="typeof item.axisLine_lineStyle_color !== 'undefined'">
                    <p>轴线颜色&nbsp;&nbsp;</p>
                    <Col>
                    <i-input class="rightFontSize iSelect" v-model="item.axisLine_lineStyle_color" size="small" @on-change="onYAxisChange">
                    <span slot="append">
                        <color-picker class="colorPicker" v-model="item.axisLine_lineStyle_color" :editable="false"  :transfer="true" size="small" @on-change="onYAxisChange"/>
                    </span>
                    </i-input>

                    </Col>
                </Row>
            </div>
        </Submenu>
    </div>
</script>
<script>
    Vue.component('j-yaxis-setting', {
        template: '#yAxis-setting-template',
        props: {
            settings: {
                type: [Object, Array],
                required: true,
                default: null
            },
            dataIndex: 0
        },
        data(){
            return {
                yAxisOption: {},
                yAxisOptions: []
            }
        },
        watch: {
            settings: {
                deep: true,
                immediate: true,
                handler: function (){
                    this.initData()
                }
            }
        },
        methods: {
            getTitle: function(index) {
                if (this.settings instanceof Array){
                    if (index == 0){
                        return '左'
                    }
                    if (index == 1){
                        return '右'
                    }
                }else{
                    return ''
                }
            },
            initData: function (){
                if (this.settings){
                    if (this.settings instanceof Array){
                        this.yAxisOptions = this.settings;
                        this.yAxisOption = Object.assign(this.yAxisOption, this.settings[this.dataIndex])
                    } else {
                        this.yAxisOptions=[];
                        this.yAxisOptions.push(this.settings);
                        this.yAxisOption = Object.assign(this.yAxisOption, this.settings)
                    }

                }
            },
            onYAxisChange(){
                if (this.settings instanceof Array){
                    this.$emit('change', 'yAxis', this.yAxisOptions)
                } else {
                    this.$emit('change', 'yAxis', this.yAxisOptions[0])
                }
            }
        }
    })
</script>