<script type="text/x-template" id="jurisdiction">
  <div>
  <Modal
          :loading="loading"
          v-model="shareModal"
          :title="moduleTitle"
          @on-cancel="handleShareCancel"
          @on-ok="handleShareOk"
          ok-text="创建连接"
  >
    <i-form>
      <form-item label="过期时间:">
        <RadioGroup v-model="jurisdictionData.termOfValidity">
          <Radio v-for="item in termValidity" :label="item.value" :key="item.value">
            <span>{{item.label}}</span>
          </Radio>
        </RadioGroup>
      </form-item>
      </template>
    </i-form>
  </Modal>
  <Modal
          :loading="loading"
          v-model="shareUrlModal"
          title="分享链接"
          @on-cancel="handleShareUrlCancel"
  >
    <p slot="footer">
      <Button type="primary"  @click="handleShareUrlOk">取消分享</Button>
    </p>
    <i-form>
      <form-item label="预览链接:" style="display: flex">
         <i-input v-model="jurisdictionData.previewUrl" readonly style="width: 400px"/>
      </form-item>
      <form-item label="预览密码" style="display: flex">
        <i-input v-model="jurisdictionData.previewLock" readonly  style="width: 100px"/>
        <Button type="primary" style="position: relative;left: 242px;" @click="copyClick">复制</Button>
      </form-item>
      </template>
    </i-form>
  </Modal>
  </div>
</script>
<script>
  Vue.component('j-jurisdiction', {
    template: '#jurisdiction',
    data() {
      return {
        id: "",
        loading: false,
        moduleTitle: "创建分享链接",
        shareModal: false,
        shareUrlModal:false,
        jurisdictionData: {
          "id": "",
          "reportId": "",
          "previewUrl": "",
          "previewLock": "",
          "status": "",
          "termOfValidity": "1"
        },
        termValidity: [
          {
            label: '永久有效',
            value: '1'
          },
          {
            label: '7天',
            value: '2'
          },
          {
            label: '1天',
            value: '3'
          }
        ],//过期时间
      }
    },
    created() {
    },
    methods: {
      //创建分享连接关闭事件
      handleShareCancel() {
        this.shareModal = false
        this.close();
      },
      close(){
        this.jurisdictionData.id="";
        this.jurisdictionData.reportId="";
        this.jurisdictionData.previewUrl="";
        this.jurisdictionData.previewLock="";
        this.jurisdictionData.status="";
        this.jurisdictionData.termOfValidity="1";
      },
      //创建分享连接确定事件
      handleShareOk() {
        this.jurisdictionData.status="0"
        $http.post({
          contentType:'json',
          url: api.addAndEdit,
          data: this.jurisdictionData,
          success: (res) => {
            if (res) {
              let protocol = window.location.protocol;
              let host = window.location.host;
              let url = protocol+"//"+host+base;
              res.previewUrl = url+res.previewUrl;
              this.jurisdictionData = res
              this.shareModal = false
              this.shareUrlModal=true
            }
          },
          error:()=>{
            this.shareModal = true
            this.$Message.warning("创建连接失败！")
          }
        })
      },
      //取消分享
      handleShareUrlOk(){
        let jurisdictionData={}
        jurisdictionData.id= this.jurisdictionData.id
        jurisdictionData.status= '1'
        jurisdictionData.reportId= this.jurisdictionData.reportId
        console.log("我进来了")
        $http.post({
          contentType:'json',
          url: api.addAndEdit,
          data: jurisdictionData,
          success: (res) => {
            if (res) {
              this.$Message.success("取消分享成功！")
              localStorage.removeItem(this.jurisdictionData.reportId);
              this.shareUrlModal=false
              this.close()
            }
          }
        }) 
      },
      handleShareUrlCancel(){
        this.close()
      },
      //复制预览地址和密码
      copyClick(){
        let previewUrl = this.jurisdictionData.previewUrl;
        let previewLock = this.jurisdictionData.previewLock;
        let textarea = document.createElement('textarea');
        textarea.value = "预览地址:  "+previewUrl+" \n"+"预览密码:  "+previewLock+"\n"+"复制预览地址在浏览器打开";
        document.body.appendChild(textarea);
        textarea.select(); // 选择对象;
        document.execCommand("Copy"); // 执行浏览器复制命令
        document.body.removeChild(textarea)
        this.$Message.success('复制成功');
      }
    }
  })
</script>