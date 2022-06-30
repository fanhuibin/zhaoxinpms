<template>
    <div>
        <div class="car-number">
            <div class="pop">
                <div class="title">
                    <span v-for="(item, index) in textList" :key="index">{{ item }}</span>
                    <i @click="colse" class="iconfont icon-quxiao"></i>
                </div>
                <div class="pro flx-w">
                    <span v-for="(item, index) in items" :key="index" @click="add(item.text)">{{ item.text }}</span>
                </div>
                <div class="pro flx-w">
                    <span v-for="(item, index) in nums" :key="index" @click="add(item.num)">{{ item.num }}</span>
                </div>
                <div class="pro flx-w">
                    <span v-for="(item, index) in letters" :key="index" @click="add(item.let)">{{ item.let }}</span>
                </div>
                <div class="pro flx-w del">
                    <span v-for="(item, index) in letters02" :key="index" @click="add(item.let)">{{ item.let }}</span>
                </div>
                <p @click="del()">
                    删除
                    <i class="el-icon-circle-close"></i>
                </p>
                <div class="confirm" @click="btnCar"><span>确定</span></div>
            </div>
        </div>
    </div>
</template>
<script>
import { isVehicleNumber } from '@/utils/validate.js';
export default {
    data() {
        return {
            items: [
                { text: '京' },
                { text: '津' },
                { text: '冀' },
                { text: '晋' },
                { text: '蒙' },
                { text: '辽' },
                { text: '吉' },
                { text: '黑' },
                { text: '沪' },
                { text: '苏' },
                { text: '浙' },
                { text: '皖' },
                { text: '闽' },
                { text: '赣' },
                { text: '鲁' },
                { text: '豫' },
                { text: '鄂' },
                { text: '湘' },
                { text: '粤' },
                { text: '桂' },
                { text: '琼' },
                { text: '渝' },
                { text: '川' },
                { text: '贵' },
                { text: '云' },
                { text: '藏' },
                { text: '陕' },
                { text: '甘' },
                { text: '青' },
                { text: '宁' },
                { text: '新' },
                { text: '港' },
                { text: '澳' },
                { text: '警' },
                { text: '学' },
                { text: '挂' },
                { text: '领' },
                { text: '使' },
            ],
            nums: [{ num: 1 }, { num: 2 }, { num: 3 }, { num: 4 }, { num: 5 }, { num: 6 }, { num: 7 }, { num: 8 }, { num: 9 }, { num: 0 }],
            letters: [
                { let: 'A' },
                { let: 'B' },
                { let: 'C' },
                { let: 'D' },
                { let: 'E' },
                { let: 'F' },
                { let: 'G' },
                { let: 'H' },
                { let: 'J' },
                { let: 'K' },
            ],
            letters02: [
                { let: 'L' },
                { let: 'M' },
                { let: 'N' },
                { let: 'P' },
                { let: 'Q' },
                { let: 'R' },
                { let: 'S' },
                { let: 'T' },
                { let: 'U' },
                { let: 'V' },
                { let: 'W' },
                { let: 'X' },
                { let: 'Y' },
                { let: 'Z' },
            ],
            textList: [],
        };
    },
    methods: {
        add(td) {
            this.textList.push(td);
        },
        del() {
            //删除
            const index = this.textList.length - 1;
            this.textList.splice(index, 1);
        },
        btnCar() {
            //把车牌号传递给父组件
            let lists = this.textList.join(''); //把数组转换成字符串
            let vefy = isVehicleNumber(lists);
            if (vefy === false) {
                this.$message({
                    message: '车牌号错误',
                    type: 'error'
                });
            } else {
                this.$emit('confirm', lists);
                this.colse();
            }
        },
        colse() {
            //关闭键盘
            this.$emit('close', false);
        },
    },
};
</script>
<style lang="less" scoped>
.car-number {
    width: 346px;
    z-index: 100;
    background: rgba(0, 0, 0, 0.6);
    .title {
        letter-spacing: 0.03rem;
        margin-bottom: 0.2rem;
        height: 50px;
        border-bottom: 0.02rem solid #01be6e;
        span {
            font-weight: bold;
            font-size: 20px;
            padding-bottom: 0.18rem;
            display: inline-block;
        }
        i {
            font-size: 40px;
            color: #a8a8a8;
            display: block;
            position: absolute;
            top: 0.1rem;
            right: 0.15rem;
        }
    }
    .pop {
        background: #fff;
        border-radius: 0.16rem;
        padding: 0.3rem 0.2rem 0.3rem 0.2rem;
        text-align: center;
        .pro > span:active {
            background: #01be6e;
            color: #fff;
        }
        .pro {
            display: flex;
            justify-content: flex-start;
            flex-wrap: wrap;
            align-items: center;
            margin: 0.1rem 0;
            span {
                display: inline-block;
                height: 32px;
                line-height: 32px;
                width: 32px;
                background: #e4e4e4;
                border-radius: 0.06rem;
                text-align: center;
                margin: 0.1rem 0.06rem;
                font-size: 20px;
                cursor: pointer;
                box-shadow: 0 0 0.03rem #7a7a7a;
            }
        }
        .del {
            margin-right: 25%;
            margin-top: -0.1rem;
        }
        p:active {
            background: #ff2b2bef !important;
            color: #fff !important;
            i:active {
                color: #ffffff !important;
            }
        }
        p {
            float: right;
            background: #dbdbdb;
            border-radius: 0.08rem;
            text-align: center;
            width: 29%;
            margin-top: -70px;
            margin-bottom: 0;
            margin-right: 0.12rem;
            height: 70px;
            line-height: 70px;
            vertical-align: middle;
            cursor: pointer;
            i {
                font-size: 20px;
                color: #8a8a8a;
            }
        }
        .confirm {
            height: 40px;
            line-height: 40px;
            width: 100%;
            background: #01be6e;
            border-radius: 0.12rem;
            box-shadow: 0 0 0.08rem rgb(163, 163, 163);
            cursor: pointer;
            color: #fff;
            letter-spacing: 0.1rem;
            margin: 0.3rem 0 0.2rem;
        }
    }
}
</style>
