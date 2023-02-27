<template>
   <el-dialog :title="'选择流程'" :close-on-click-modal="false" :visible.sync="visible" class="Jdialog Jdialog_center" lock-scroll width="600px">
       <div class="main" style="margin-bottom:40px;">
        <div class="myFlow">
          <div class="flowBox" v-for="(item,i) in list" :key="i" @click="jump(item)">
            <div class="box-icon" :style="{backgroundColor: item.iconBackground}" >
                <i :class="item.icon"  />
            </div>
            <p class="box-title">{{item.fullName}}</p>
          </div>
          <div style="clear:both;"></div>
        </div>
      </div>
   </el-dialog>
</template>

<script>
import request from '@/utils/request';
export default {
    components: {},
    data() {
        return {
            loading: true,
            visible: false,
            list: [],
        };
    },

    methods: {
        /** 查询所有流程 */
        init() {
            this.visible = true;
             request({
                url: `/workflow/designer`,
                method: 'get',
            }).then(res => {
                this.list = res.data.list;
                this.loading = false;
                
            });
        },
        jump(item) {
            if (!item.enCode) {
                this.$message({
                type: 'error',
                message: '流程不存在'
                });
                return
            }
            console.log(123);
            this.$emit('chioceFlow', item)
        }
    },
};
</script>
<style scoped lang="scss">
.flowBox {
      cursor: pointer;
      width: 90px;
      height: 90px;
      overflow: hidden;
      float: left;
      margin: 10px;
      margin-left: 0px;
      margin-bottom: 20px;
      &:hover {
        opacity: 0.8;
        .flowBox:hover .box-icon {
          box-shadow: 0 0 6px 1px rgba(0, 0, 0, 0.1);
        }
      }
      .box-icon {
        width: 50px;
        height: 50px;
        border-radius: 12px;
        text-align: center;
        margin: 0 auto;
        margin-top: 10px;
        margin-bottom: 10px;
        background-color: #ccc;
        i {
          text-align: center;
          font-size: 38px;
          color: #fff;
          line-height: 50px;
        }
      }
      .box-title {
        text-align: center;
        font-size: 12px;
      }
    }
</style>
