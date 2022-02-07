<script>
import FlowCard from './FlowCard/preview.vue';
import PropPanel from './PropPanel/index.vue';
import { NodeUtils, getMockData } from './FlowCard/util.js';

export default {
    name: 'Process',
    props: ['tabName', 'conf'],
    data() {
        let data = getMockData();
        if (typeof this.conf === 'object' && this.conf !== null) {
            Object.assign(data, this.conf);
        }
        return {
            data, // 流程图数据
            scaleVal: 100, // 流程图缩放比例 100%
            step: 5, // 缩放步长
            updateId: 0, // key值 用于模拟$forceUpdate
            activeData: null, // 被激活的流程卡片数据，用于属性面板编辑
            isProcessCmp: true,
            verifyMode: true,
        };
    },
    methods: {
        // 给父级组件提供的获取流程数据得方法
        getData() {
            this.verifyMode = true;
            if (NodeUtils.checkAllNode(this.data)) {
                return Promise.resolve({ formData: this.data });
            } else {
                return Promise.reject({ target: this.tabName });
            }
        },
        /**
         * 接收所有FlowCard事件触发
         * @param { Object } data - 含有event(事件名称)/args(参数)两个属性
         */
        eventReciver({ event, args }) {
            if (event === 'edit') {
                this.activeData = args[0]; // 打开属性面板
                return;
            }
            // 本实例只监听了第一层数据（startNode）变动
            // 为了实时更新  采用$forceUpdate刷新 但是由于某些条件下触发失效（未排除清除原因）
            // 使用key + 监听父组件updateId方式强制刷新
            NodeUtils[event](...args);
            this.forceUpdate();
        },

        forceUpdate() {
            this.updateId = this.updateId + 1;
        },
        /**
         * 控制流程图缩放
         * @param { Object } val - 缩放增量 是step的倍数 可正可负
         */
        changeScale(val) {
            let v = this.scaleVal + val;
            if (v > 0 && v <= 200) {
                // 缩放介于0%~200%
                this.scaleVal = v;
            }
        },

        // 传formIds 查询指定组件 未传时  判断所有组件
        isFilledPCon(formIds) {
            let res = false;
            const loopChild = (parent, callback) => parent.childNode && loop(parent.childNode, callback);
            const loop = (data, callback) => {
                if (res || !data) return; // 查找到就退出
                if (Array.isArray(data.conditionNodes)) {
                    const uesd = data.conditionNodes.some(c => {
                        const cons = c.properties.conditions || [];
                        return Array.isArray(formIds)
                            ? cons.some(item => formIds.includes(item.formId)) // 查询特定组件
                            : cons.length > 0; // 只要有节点设置了条件 说明就有组件作为条件被使用
                    });
                    uesd ? callback() : data.conditionNodes.forEach(t => loopChild(t, callback));
                }
                loopChild(data, callback);
            };
            loop(this.data, () => (res = true));
            return res;
        },
    },
    render: function (h) {
        return (
            <div class="flow-container flow-container-preview">
                <div class="scale-slider">
                    <i class="btn  el-icon-minus" onClick={this.changeScale.bind(this, -this.step)}></i>
                    <span style="font-size:14px;">{this.scaleVal}%</span>
                    <i class="btn  el-icon-plus " onClick={this.changeScale.bind(this, this.step)}></i>
                </div>
                <div class="tips">
                    <div class="tips-item">
                        <span class="icon complete">●</span>已完成
                    </div>
                    <div class="tips-item">
                        <span class="icon current">●</span>进行中
                    </div>
                    <div class="tips-item">
                        <span class="icon">●</span>未进行
                    </div>
                </div>
                <FlowCard
                    verifyMode={this.verifyMode}
                    key={this.updateId}
                    data={this.data}
                    onEmits={this.eventReciver}
                    style={{ transform: `scale(${this.scaleVal / 100})` }}
                />
            </div>
        );
    },
};
</script>

<style scoped lang="scss">
$bg-color: #fff;

.flow-container {
    display: inline-block;
    background: $bg-color;
    padding: 20px;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    text-align: center;
    overflow: auto;
    &.flow-container-preview {
        ::v-deep .branch-wrap {
            .branch-box {
                background: $bg-color;

                > .col-box {
                    &:first-of-type {
                        &::before,
                        &::after {
                            background: $bg-color !important;
                        }
                    }
                    &:last-of-type {
                        &::before,
                        &::after {
                            background: $bg-color;
                        }
                    }
                }
            }
        }
        ::v-deep .node-wrap-box.approver::before {
            background: #fff;
        }
        ::v-deep .flow-path-card {
            &:hover {
                .title-text {
                    border-bottom: none;
                }
            }
            &.condition:hover,
            &.timer:hover {
                box-shadow: 0 0 0 2px #b6b6b6, 0 0 5px 4px rgb(0 0 0 / 20%);
            }
            &.approver {
                &:hover {
                    box-shadow: 0 0 0 2px #909399, 0 0 5px 4px rgb(0 0 0 / 20%);
                }
                .header {
                    background-color: #909399;
                }
            }
            &.active {
                .header {
                    background-color: #1890ff;
                }
                &:hover {
                    box-shadow: 0 0 0 2px #1890ff, 0 0 5px 4px rgb(24 144 255 / 20%);
                }
            }
            &.start-node,
            &.complete {
                .header {
                    background-color: #20b2aa;
                }
                &:hover {
                    box-shadow: 0 0 0 2px #20b2aa, 0 0 5px 4px rgb(32 178 170 / 20%);
                }
            }
        }
    }
}

.scale-slider {
    position: fixed;
    right: 0;
    z-index: 99;

    .btn {
        display: inline-block;
        padding: 4px;
        border: 1px solid #cacaca;
        border-radius: 3px;
        background: #fff;
        margin-left: 10px;
        margin-right: 10px;
        cursor: pointer;
    }
}
.tips {
    position: absolute;
    left: 20px;
    top: 0px;
    z-index: 199;
    text-align: left;
    .tips-item {
        line-height: 20px;
        font-size: 16px;
        display: inline-block;
        margin-right: 15px;
        .icon {
            font-size: 20px;
            margin-right: 5px;
            color: #909399;
            &.current {
                color: #1890ff;
            }
            &.complete {
                color: #20b2aa;
            }
        }
    }
}
</style>
