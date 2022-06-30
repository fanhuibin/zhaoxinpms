<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <el-row class="Jcommon-search-box" :gutter="16">
                <el-form @submit.native.prevent>
                    <el-col :span="6">
                        <el-form-item label="请选择楼栋" prop="block">
                            <el-cascader v-model="dataForm.buildingSelect" :options="buildings" clearable :style="{ width: '100%' }"></el-cascader>
                        </el-form-item>
                    </el-col>

                    <el-col :span="6">
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <div class="tips">
                        <div class="tips-item">
                            <span class="icon empty">●</span>
                            空置
                        </div>
                        <div class="tips-item">
                            <span class="icon rented">●</span>
                            已出租
                        </div>
                        <div class="tips-item">
                            <span class="icon selled">●</span>
                            已出售
                        </div>
                        <div class="tips-item">
                            <span class="icon warn">●</span>
                            即将到期
                        </div>
                        <div class="tips-item">
                            <span class="icon error">●</span>
                            租约逾期
                        </div>
                    </div>
                </div>

                <div style="overflow-y: scroll">
                    <div>
                        <div v-if="data.key.length == 0" style="padding-left:30px;">
                            商铺信息为空，请选择其他楼栋进行查询
                        </div>
                        <div v-else class="floorDiv" v-for="(item, index) in data.key" :key="index">
                            <div class="floorInfo">{{ item }}层</div>
                            <div style="flex: 1; display: flex; flex-wrap: wrap">
                                <div class="houseDiv" v-for="(house, houseindex) in data.list[item]" :key="houseindex">
                                    <div class="header" :class="house.showState + '-background'">
                                        <div class="title-box">{{ house.name }}（{{ house.buildingsquare }}㎡）</div>
                                    </div>
                                    <div class="body">
                                        <div v-if="house.showState == 'empty'" class="text">{{house.remark}}</div>
                                        <div v-else-if="house.showState == 'selled'" class="text">
                                            {{house.stateCompany}}
                                            <br/>
                                            已出售
                                        </div>
                                        <div v-else class="text">
                                            {{house.stateCompany}}
                                            <br/>
                                            {{ parseTime(house.stateEndTime, '{y}-{m}-{d}') }}到期
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
import { selectBuilding } from '@/api/pms/building';
export default {
    data() {
        return {
            activeName: 'first',
            data: {
                key:[],
            },
            buildings: [],
            dataForm: {
                buildingSelect: [],
            },
        };
    },
    created() {
        this.init();
        //为了演示，这是设置一个固定的楼栋
        this.dataForm.buildingSelect=['01','1540273162296537089'];
        this.search();
    },
    methods: {
        init() {
            selectBuilding().then(res => {
                this.buildings = res.data;
            });
        },
        handleClick() {},
        search() {
            request({
                url: `/baseconfig/House/rentControl/`+this.dataForm.buildingSelect[1],
                method: 'GET',
            }).then(res => {
                this.data = res.data;
                console.log(this.data);
            });
        },
    },
};
</script>
<style scoped lang="scss">
.empty {
    color: #909399;
}
.rented {
    color: #1890ff;
}
.selled {
    color: #20b2aa;
}
.warn {
    color: #e6a23c;
}
.error {
    color: #f56c6c;
}
.empty-background {
    background-color: #909399 !important;
}
.rented-background {
    background-color: #1890ff !important;
}
.selled-background {
    background-color: #20b2aa !important;
}
.warn-background {
    background-color: #e6a23c !important;
}
.error-background {
    background-color: #f56c6c !important;
}
.tips {
    margin-left: 20px;
    margin-bottom: 20px;
    text-align: left;
    .tips-item {
        line-height: 20px;
        font-size: 16px;
        display: inline-block;
        margin-right: 15px;
        .icon {
            font-size: 20px;
        }
    }
}

.floorDiv {
    display: flex;

    .floorInfo {
        height: 70px;
        width: 70px;
        line-height: 70px;
        text-align: center;
        border: 1px solid #ccc;
        box-shadow: 5px 5px 2px #ccc;
        margin-right: 35px;
        margin-left: 20px;
    }

    .houseDiv {
        width: 210px;
        min-height: 82px;
        font-size: 12px;
        border-radius: 4px;
        text-align: left;
        cursor: pointer;
        overflow: hidden;
        position: relative;
        box-sizing: border-box;
        box-shadow: 0 0 6px 0 rgba(0, 0, 0, 0.3);
        background-color: #fff;
        margin-bottom: 15px;
        margin-right: 15px;

        .header {
            background-color: #1890ff;
        }

        .header {
            padding-left: 16px;
            padding-right: 30px;
            width: 100%;
            height: 24px;
            line-height: 24px;
            color: white;
            position: relative;
            box-sizing: border-box;

            .title-box {
                position: relative;
                display: inline-block;
            }
        }

        .body {
            position: relative;
            padding: 12px;
            padding-right: 30px;
            box-sizing: border-box;

            .text {
                margin: 0;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 4;
                -webkit-box-orient: vertical;
            }
        }
    }
}
</style>
