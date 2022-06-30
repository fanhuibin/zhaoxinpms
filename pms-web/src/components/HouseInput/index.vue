<template>
    <el-autocomplete
        popper-class="my-autocomplete"
        v-model="content"
        :fetch-suggestions="querySearch"
        placeholder="商铺完整编号"
        :trigger-on-focus="false"
        @change="onEditorChange($event)"
        @select="handleSelect"
        :style="customStyle"
    >
        <i class="el-icon-edit el-input__icon" slot="suffix" @click="handleIconClick"></i>
        <template slot-scope="{ item }">
            <div v-if="type === 'contract'">
                <div class="name">编号：{{ item.resourceName }}</div>
                <div class="addr">单位：山西肇新科技有限公司</div>
                <div class="addr">联系人：{{ item.userName }} {{ item.userPhone }}</div>
            </div>
            <div v-if="type === 'house'">
                <div class="name">编号：{{ item.name }}</div>
                <div class="addr">状态：{{ item.stateName }}</div>
                <div v-if="item.state != 'empty'" class="addr">单位：山西肇新科技有限公司</div>
            </div>
        </template>
    </el-autocomplete>
</template>
<style lang="scss">
.my-autocomplete {
    li {
        line-height: normal;
        padding: 7px;

        .name {
            text-overflow: ellipsis;
            overflow: hidden;
        }
        .addr {
            font-size: 12px;
            color: #b4b4b4;
        }

        .highlighted .addr {
            color: #ddd;
        }
    }
}
</style>

<script>
import request from '@/utils/request';
export default {
    props: {
        value: String,
        type: {
            type: String,
            default: 'house',
        },
        customStyle: Object,
    },
    data() {
        return {
            content: this.value,
            searchResult: [],
            state: '',
        };
    },
    watch: {
        value: {
            immediate: true,    // 这句重要
            handler (val) {
                this.content = val;
            }
        }
    },
    methods: {
        querySearch(queryString, cb) {
            if (this.type === 'contract') {
                //异步查询数据
                request({
                    url: `/payment/PaymentContract/resourceNameTips/` + queryString,
                    method: 'get',
                }).then(res => {
                    // 调用 callback 返回建议列表的数据
                    cb(res.data);
                });
            } else if (this.type === 'house') {
                //异步查询数据
                request({
                    url: `/baseconfig/House/tips/` + queryString,
                    method: 'get',
                }).then(res => {
                    // 调用 callback 返回建议列表的数据
                    res.data.forEach(function (value, index, array) {
                        if (value.state == 'selled') {
                            value.stateName = '出售';
                        }
                        if (value.state == 'empty') {
                            value.stateName = '空置';
                        }
                        if (value.state == 'rented') {
                            value.stateName = '出租';
                        }
                    });

                    cb(res.data);
                });
            }
        },
        handleSelect(item) {
            if(this.type === 'house'){
                this.content = item.name;
            }
            if(this.type === 'contract'){
                this.content = item.resourceName;
            }
            
            this.$emit('input', this.content);
        },
        handleIconClick(ev) {
            //console.log(ev);
        },
        onEditorChange(editor) {
            //内容改变事件
            this.$emit('input', this.content);
        },
    },
    mounted() {},
};
</script>
