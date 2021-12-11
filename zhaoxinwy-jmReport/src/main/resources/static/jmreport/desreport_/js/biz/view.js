const view = {
    origin:window.location.origin
 }
let xs = null;
// 预览页面 vue实例
 let rpViewInst = new Vue({
     el: "#app",
     data: function () {
         return {
             enhanceFunction: '',
             loading: false,
             visible: false,
             //报表参数选项列表
             reportParamList:[],
             //查询参数对象
             reportParamObj:{

             },
             callback:'',
             configQueryList:[],
             //查询对象
             tokenKey:'X-Access-Token',
             queryInfo:{

             },
             timerArr:[],
             lock:"",//浏览密码
             lockVisible:false,
             lockLoading:false,
             viewData:{},
             queryResultData: {},
             returnPreviousPage:false, //是否显示上一页
             rpBar: true
         }
     },
     created(){
         this.resetQueryInfo()
         //获取url路径上的是否显示上一页
         let previousPage = getLocalRequestUrl().previousPage;
         if(previousPage){
           if(previousPage == "true"){
             this.returnPreviousPage = true
           }else{
             this.returnPreviousPage = false
           }
         }else{
           this.returnPreviousPage = false
         }
         //提示信息停留时间设置
         Vue.prototype.$Message.config({
           duration:5
         });
     },
     methods:{
         // 执行js增强
         executeEnhanceJs(){
             if(this.enhanceFunction){
                 this.enhanceFunction.call(this)
             }
         },
         // 查询区域表单值改变事件
         onSearchFormChange(dbCode, fieldName, callback){
             const getRealFormKey = (dbCode, fieldName)=>{
                 let key = ''
                 for(let i=0;i<this.configQueryList.length;i++){
                     let item = this.configQueryList[i]
                     if(item.dbCode == dbCode && item.name == fieldName){
                         key = getSearchFormKey(item);
                         break;
                     }
                 }
                 return key;
             }
             this.$nextTick(()=>{
                 let key = getRealFormKey(dbCode, fieldName);
                 let ref = this.$refs[key];
                 if(ref){
                     if(ref instanceof Array){
                         ref[0].$on('on-change', callback)
                     }else{
                         ref.$on('on-change', callback)
                     }
                 }
             })
         },
         // 修改下拉框的options
         updateSelectOptions(dbCode, fieldName, options){
             for(let i=0;i<this.configQueryList.length;i++){
                 let item = this.configQueryList[i]
                 if(item.dbCode == dbCode && item.name == fieldName && item.mode == 7){
                     item.dictList = options;
                     break;
                 }
             }
         },
         // 修改查询控件的值
         updateSearchFormValue(dbCode, fieldName, value){
             updateSearchForm(dbCode, fieldName, value)
         },
         //获取查询控件描述
         getQueryItemLabel(item){
             if(item.duplicate===true){
                 return item.title+'('+item.dbText+'):'
             }else{
                 return item.title+':'
             }
         },
         resetQueryInfo(){
             // 查询条件重置操作 需要将没有默认值的设置为空 有默认值的还原默认值
             Object.keys(this.queryInfo).map(k=>{
                 if(k!=this.tokenKey){
                     let queryConfigArr = this.configQueryList.filter(i=>{
                         return i.key == k
                     });
                     let defVal = ''
                     if(queryConfigArr && queryConfigArr.length>0){
                       if(queryConfigArr[0].defaultValue){
                         defVal = queryConfigArr[0].defaultValue.toString()
                       }else{
                         defVal = ''
                       }
                     }
                     this.queryInfo[k] = defVal
                 }
             })
         },
         //重置
         resetReportQuery(){
             this.resetQueryInfo()
             this.doReportQuery()
             /*初始化参数，在查询数据之后
              *不在上面写是因为如果在上面拼接参数的话，也会传到后台，导致查询不准确
              */
             this.initQueryParams(this.token)
         },
         //重置初始化查询参数
         initQueryParams(token){
           this.queryInfo = {}
           //后台查询条件
           //获取浏览器中的参数并传递给后台
           let requestParam = getRequestUrlParam();
           $jm.getQueryInfo(configId, requestParam, token, (result)=> {
             let ls = result.list
             if (ls) {
                 let { list, queryInfo } = getFirstQueryInfo(ls)
                 rpViewInst.queryInfo = queryInfo
                 rpViewInst.configQueryList = [...list]
             }
           })
         },
         //获取查询条件
         getRpQueryParam(){
             let requestParam = getRequestUrlParam();
             if(!requestParam['pageNo']){
                 requestParam['pageNo'] = 1
             }
             query2RequestParam(this.queryInfo, requestParam);
             return requestParam
         },
         //查询
         doReportQuery(){
             console.log('查询条件', this.queryInfo)
             Vue.prototype.$Spin.show();
             let requestParam = this.getRpQueryParam();
             $jm.excelView(view.excelConfigId, requestParam, (result)=> {
                 Vue.prototype.$Spin.hide();
                 this.saveQueryResultData(result.dataList)
                 var str = result.jsonStr;
                 if(!str){
                     xs.loadData({});
                     return;
                 }

                 let json = JSON.parse(str)
                 let dataMap = result.dataList
                 let expData = {...dataMap['expData']}
                 delete dataMap['expData']
                 str = handleChartData(dataMap, str)
                 const dealDataList = view.dealDataList(dataMap);
                 const dataList = dealDataList.dataList;
                 const count = dealDataList.count;
                 const pageObj = dealDataList.pageObj;
                 const excelDataList = view.getExcelData(dataList);
                 let json1 = JSON.parse(str)
                 const viewData = view.parseData(str,excelDataList);
                 let json2 = JSON.parse(str)
                 //update-begin-author:wangshuai date:20210609 for:使用多数据对比柱状图时，如果查询条件后图表重叠 github 305
                 let charList = json2['chartList'];
                 //循环charList数据，更新图表数据
                 if(charList){
                   for (let i = 0; i <charList.length; i++) {
                     let layerId = charList[i].layer_id;
                     let config = charList[i].config;
                     xs.updateChart(layerId ,JSON.parse(config));
                   }
                   //删除cahrtList避免重复加载图表
                   delete viewData['chartList'];
                 }
                 //update-end-author:wangshuai date:20210609 for:使用多数据对比柱状图时，如果查询条件后图表重叠 github 305
                 if(expData && "{}"!=JSON.stringify(expData)){
                     xs.setExpData(expData);
                 }
                 if(Object.keys(pageObj).length===1){
                     xs.data.settings.page = 1;
                     xs.data.settings.total = pageObj[Object.keys(pageObj)[0]];
                     if(xs.data.settings.total==1 && this.rpBar == true){
                         //下一页和最后一页禁用
                         xs.sheet.rpbar.btn_next.btn.el.disabled=true;
                         xs.sheet.rpbar.btn_end.btn.el.disabled=true;
                     }
                 }
                 //TODO 多个数据源是否还为0
                 if(this.rpBar == true){
                     if(Object.keys(pageObj).length===0){
                         xs.sheet.rpbar.btn_input.countSpan.el.innerHTML=1;
                     }else{
                         xs.sheet.rpbar.btn_input.countSpan.el.innerHTML=xs.data.settings.total;
                     }
                 }
                 viewData['currentPageSize'] = view.pageSize;
                 xs.loadData(viewData);

             },(res)=>{
                 Vue.prototype.$Message.warning(!res.message?'查无数据':res.message);
                 xs.loadData({});
             },(error)=>{
               Vue.prototype.$Spin.hide();
             });
         },
         //日期组件值改变事件
         handleQueryDateChange(str, key){
             this.queryInfo[key] = str
         },
         // 模糊查询事件
         handleLikeQueryChange(e, key){
             let str = e.target.value;
             if(str===0 || str==='0'){
                 this.queryInfo[key] = '*'+str+'*'
             }else if(str){
                 this.queryInfo[key] = '*'+str+'*'
             }
         },
         //下拉多选改变
         handleQueryMultiSelectChange(arr, key){
             console.log(key, arr)
             if(!arr || arr.length==0){
                 this.queryInfo[key] = ''
             }else{
                 this.queryInfo[key] = arr.join(',')
             }
         },
         show(reportParamList, callback){
             this.reportParamList = [...reportParamList];
             this.visible=true;
             let obj = {};
             for(let item of reportParamList){
                 if(!item.paramValue || item.paramValue.length==0){
                     obj[item.paramName] = '';
                 }else{
                     obj[item.paramName] = item.paramValue;
                 }
             }
             this.reportParamObj = obj;
             this.callback = callback;
             //console.log('show', reportParamList)
         },
         onSave() {
             //console.log('this.paramobj', this.reportParamObj);
             this.callback(this.reportParamObj);
             this.visible=false;
             Vue.prototype.$Spin.show();
         },
         //查询区域切换需要重新渲染表格
         onQueryAreaSwitch(){
            setTimeout(()=>{
                xs.render();
            },200)
         },
         lockClick(configId){
             localStorage.setItem(configId,this.lock);
             this.lockVisible=false
             view.load(configId)
         },
         // 将查询结果存下来  联动的时候 取数据用到
         saveQueryResultData(data){
             if(data){
                 Object.keys(data).map(dbCode=>{
                     let list = data[dbCode]['list']
                     // TODO 数据存到对象里 数据量大是否卡顿
                     this.queryResultData[dbCode] = list
                 })
             }
         },
         // 点击单元格的时候需要获取单元格所在数据集的数据 传递参数到下一个联动图表
         getLinkageArgument(linkData, param){
             let text = param.text
             let params = []
             if(linkData.parameter){
                 let arr = JSON.parse(linkData.parameter)
                 for(let p of arr){
                     let dbCode = p.dbCode
                     let list = this.queryResultData[dbCode]
                     let paramValue = p.paramValue
                     if(paramValue){
                         if(paramValue.startsWith('=')){
                             let obj = {}
                             let columnChar = paramValue.replace('=', '')
                             obj[p.paramName] = xs.cellText(param.ri, columnChar)
                             params.push(obj)
                         }else{
                             let fieldName = p.fieldName
                             for(let i=0;i<list.length;i++){
                                 if(list[i][fieldName] == text){
                                     let obj = {}
                                     obj[p.paramName] = list[i][paramValue]
                                     params.push(obj)
                                     break;
                                 }
                             }
                         }

                     }
                 }
             }
             return params
         },
         //下拉搜索选中的时候触发，当前仅限单选
         handleQuerySingle(val,key){
           let configQueryList = this.configQueryList;
             for (let queryData of configQueryList) {
               if(queryData['key'] == key){
                 queryData.select=true
                 //queryType 1是默认值 2不是默认值
                 queryData.queryType=2
               }
             }
         },
         //按钮清除时触发，仅限单选
         handleQueryClear(key){
           this.dictCodeSearch("",key,false,1)
         },
         //搜索词改变时触发，当前只有单选
         handleQueryChange(query,key){
           if(!query){
             this.dictCodeSearch("",key,false,1)
           }
         },
       
         /**
          *远程搜索字典
          * @param query 当前搜索值
          * @param key 当前数据源编码
          * @param select 是否为下拉选中 true是 false否
          * @param queryType 是否存在默认值 1存在默认值 2不存在默认值
          */
         dictCodeSearch(query,key,select,queryType){
           if(queryType && queryType == 1){
             query = "";
           }
           if(!select || select==false){
             let dataStr={"reportId":configId,"text":query,"key":key}
             $http.get({
               url:api.dictCodeSearch,
               data : dataStr,
               success:(result)=>{
                 // 查询条件重置操作 需要将没有默认值的设置为空 有默认值的还原默认值
                 let configQueryList = this.configQueryList;
                 if(result && result.length>0){
                   for (const queryList of configQueryList) {
                     if(queryList['key'] == key){
                       queryList['dictList'] = result
                     }
                   }
                 }
               }
             })
           }else{
             let configQueryList = this.configQueryList;
             for (const queryData of configQueryList) {
               if(queryData['key'] == key){
                 queryData.select=false
                 queryData.queryType=2
               }
             }
           }
         }
     }
 })
 //方便前端调试
 if(getRequestUrlParam().develop=='true'){
     view.origin=getRequestUrlParam().origin;
 }
 const reg = /{([a-zA-Z0-9_\u4e00-\u9fa5]+).(\S+)}/
 window.onresize=()=>{
     //窗口大小改变后，设置图片上边距
     setTimeout(() => {
         view.dealPicTop();
         changeScrollBottom();
     }, 1);

 }
 view.load = function(excelConfigId){
     var that = this
     Vue.prototype.$Spin.show();
     //拿到token
     let token = getRequestUrlParam().token;
     if(token && token!=null&& token!=""){
         window.localStorage.setItem('JmReport-Access-Token',token);
     }
     view.token = window.localStorage.getItem('JmReport-Access-Token');
     console.log("view_view--------------",view.token);
     view.excelConfigId = excelConfigId;
     let reportConfigJson = getReportConfigJson()
     let pageSizeList = []
     if(reportConfigJson && reportConfigJson.pageSize){
         pageSizeList = reportConfigJson.pageSize
     }
     let exportDisabled = false;
     if(reportConfigJson && reportConfigJson.exportDisabled == true){
         exportDisabled = true;
     }
     view.pageSize = pageSizeList.length>0?pageSizeList[0]:10;
     const options = {
         "viewLocalImage":"/jmreport/img",//预览本地图片方法
         domain:baseFull,
         rpBar: true,
         showToolbar:false ,     //头部操作按钮
         showGrid: false,        //excel表格
         showContextmenu: false, //右键操作按钮
         readOnly:true,
         view: {
             height: () => {
                 let queryArea = document.querySelector('.jm-query-collapse')
                 let restHeight = 40
                 if(queryArea){
                     restHeight+=queryArea.scrollHeight+2;
                 }
                 return document.documentElement.clientHeight - restHeight;
             },
             width: () => document.documentElement.clientWidth,
         },
         row: {
             len: 100,
             height: 25,
         },
         col: {
             len: 50,
             width: 100,
             minWidth: 60,
             height: 0,
             indexWidth: 0,
         },
         style: {
             bgcolor: '#ffffff',
             align: 'left',
             valign: 'middle',
             textwrap: false,
             strike: false,
             underline: false,
             color: '#0a0a0a',
             font: {
                 name: 'Helvetica',
                 size: 10,
                 bold: false,
                 italic: false,
             },
         },
     };
     options.exportDisabled = exportDisabled;
     //x.spreadsheet.locale('zh-cn');
     const requestParam = getRequestUrlParam();
     let jmdata=requestParam.jmdata;
     // 下载oss图片到本地
     options.downLoadImage = (imageUrl)=>{
         return $jm.downLoadImage(options.domain, imageUrl)
     }
     if(jmdata){
         options.total = 1;
         xs = x.spreadsheet('#jm-sheet-wrapper', options)
         const excelData = JSON.parse(decodeURIComponent(jmdata));
        xs.data.rows._={}
        xs.data.cols._={};
        xs.data.styles=[];
        xs.loadData(excelData);
     }else{
         handleReportQueryInfo(token).then(()=>{
             let page=1;
             let total = 1;
             options.pageInfo={
                 url:`${baseFull}/jmreport/show`,
                 data:{
                     "id":excelConfigId,
                     "params":requestParam
                 }
             }
             //导出参数默认值
             let exportDefaultValue={}
             var loadExcelView = function(inputparam){
                 if(inputparam){
                     Object.keys(inputparam).map(key=>{
                         requestParam[key] = inputparam[key]
                         exportDefaultValue[key] = inputparam[key]
                         if(key=='pageNo'){
                             page = Number(inputparam[key])
                         }
                     })
                 }
                 //update-begin-author:taoyan date:20210506 for:1、浏览器输入pageNo不好使 JMREP-1926
                 if(!requestParam.pageNo){
                     requestParam.pageNo = page;
                 }
                 //update-end-author:taoyan date:20210506 for:1、浏览器输入pageNo不好使 JMREP-1926
                 view.dictInfo = []
                 query2RequestParam(rpViewInst.queryInfo, requestParam);
                 requestParam['jmViewFirstLoad'] = '1';
                 console.log('requestParam', requestParam)
                 getShareViewUrl(excelConfigId, (result)=> {
                     if(result=="0"){
                         $jm.excelView(excelConfigId,requestParam,(result)=> {
                             rpViewInst.saveQueryResultData(result.dataList)
                             //设置网页标题
                             document.title = result.name+'';
                             var str = result.jsonStr;
                             if(!str){
                                 xs.loadData({});
                                 return;
                             }
                             // 设置样式
                             addCssToHead(result.cssStr);
                             // getDataById 查询出来的 MAP MAP MAP 不是list
                             let dataMap = result.dataList
                             console.info("test",dataMap);
                             let expData = {...dataMap['expData']}
                             delete dataMap['expData']
                             //预览运行图表数据
                             str = handleChartData(dataMap, str)
                             //获取分页参数
                             const dealDataList = view.dealDataList(dataMap);
                             const dataList = dealDataList.dataList;
                             const count = dealDataList.count;
                             const pageObj = dealDataList.pageObj;
                             //获取数据集
                             const excelDataList = view.getExcelData(dataList);
                             view.dictInfo = result.dictInfo
                             if(Object.keys(pageObj).length===1){
                                 total = pageObj[Object.keys(pageObj)[0]];
                                 options.getPageResult = getPageResult
                             }
                             options.count = count;
                             options.pdfName = result.name;
                             options.page = page;
                             options.pageSize = requestParam.pageSize
                             options.total = total;
                             if(pageSizeList && pageSizeList.length>0){
                                 options.pageSizeList = pageSizeList
                             }
                             const printAllFlag=encodeURIComponent(JSON.stringify({'printAll':true}));
                             options.getAll=`${baseFull}/jmreport/show?id=${excelConfigId}&params=${printAllFlag}`;
                             //查找全部数据接口
                             options.getAllFn=function (p) {
                                 let arg = {'printAll':true}
                                 if(p){
                                     Object.keys(p).map(k=>{
                                         if(p[k]){
                                             arg[k] = p[k]
                                         }
                                     })
                                 }
                                 // 如果没有pageSize 取固定1W条
                                 if(!arg['pageSize']){
                                     arg['pageSize'] = 10000;
                                 }
                                 let paramString = encodeURIComponent(JSON.stringify(arg))
                                 return $jm.getAllData(excelConfigId, paramString);
                             };

                             //多页打印回调函数
                             options.parseDataFn=view.parseData;
                             options.dealManySourceFn=dealManySource;
                             const requestParamObj = getRequestUrlParam();
                             let previousPage = requestParamObj.previousPage;
                             if(previousPage == "true"){
                               options.previousPage = true
                             }
                             let requestStr=``;
                             if(requestParamObj){
                                 for(let key in requestParamObj){
                                     requestStr+=(requestStr.includes("?")?`&`:`?`)+`${key}=${requestParamObj[key]}`
                                 }
                                 requestStr+=(requestStr.includes("?")?`&`:`?`)+`token=${token}`
                             }
                             var base64Arry = [];
                             //console.log('--options',JSON.stringify(options))
                             xs = x.spreadsheet('#jm-sheet-wrapper', options)
                                 .onSettingEvent(function (e,param) {
                                     if(e=='pdf'){
                                         //导出pdf需要获取参数
                                         let exportParam = rpViewInst.getRpQueryParam();
                                         xs.exportPdf(exportParam)
                                     }else if(e=='iText_pdf'){
                                         xs.getLayerBase64().then(values=>{
                                             base64Arry = values;
                                             var dataStr = '';
                                             let queryParam  = rpViewInst.getRpQueryParam();
                                             Object.keys(exportDefaultValue).map(key=>{
                                               if(!queryParam[key]){
                                                 queryParam[key] = exportDefaultValue[key]
                                               }
                                             })
                                             if (base64Arry != null && base64Arry.length > 0){
                                                 dataStr = JSON.stringify({excelConfigId:excelConfigId,base64Arry:base64Arry,queryParam: queryParam});
                                             }else {
                                                 dataStr = JSON.stringify({excelConfigId:excelConfigId, queryParam:queryParam});
                                             }
                                             Vue.prototype.$Spin.show();
                                             $http.post({
                                                 contentType:'json',
                                                 url:api.exportPdf,
                                                 data : dataStr,
                                                 success:(result)=>{
                                                   Vue.prototype.$Spin.hide();
                                                   //如果宋体不存在，则提示用户安装宋体
                                                   if(result.error){
                                                     xs.tip(result.error);
                                                   }else{
                                                     ajaxFileDownload(result.file, result.name);
                                                   }
                                                 },
                                                 error:(e)=>{
                                                     Vue.prototype.$Spin.hide();
                                                     xs.tip(e.error);
                                                 }
                                             })
                                         })
                                     }else if(e=='print_all'){
                                         let exportParam = rpViewInst.getRpQueryParam();
                                         xs.printByQuery(exportParam)
                                     }else if(e=='print_table'){
                                         let exportParam = rpViewInst.getRpQueryParam();
                                         xs.printByQuery(exportParam, 'table');
                                     }else if(e=='export_img'){
                                         let exportParam = rpViewInst.getRpQueryParam();
                                         xs.printByQuery(exportParam, 'export_img');
                                     }else if(e==='clickcell'){
                                         if(param.linkIds){
                                             let linkIds = param.linkIds;
                                             let text = param.text;
                                             $http.get(
                                                 {
                                                     url: api.linkQueryByIds,
                                                     data: {ids: linkIds},
                                                     success: (result) => {
                                                         //先循环一遍取判断是否存在表达式
                                                         //处理钻取
                                                         let reportHref = jimuReportHyperlinks(result,param,"2");
                                                         //处理联动
                                                         reportHref = jimuReportLinkage(reportHref,param,"2")
                                                         jimuReportHref(reportHref,param, '2');
                                                     }
                                                 });
                                         }
                                     }else if(e==='back_previous'){
                                         returnPreviousPageClick();
                                     }
                                 })
                                 .onClickChart(function(data){
                                     console.log(data)
                                     if(data.extData.linkIds){
                                         let linkIds = data.extData.linkIds;
                                         $http.get(
                                             {
                                                 url: api.linkQueryByIds,
                                                 data: {ids: linkIds},
                                                 success: (result) => {
                                                     let reportHref = jimuReportLinkage(result,data,"1");
                                                     //1、代表图表和图表之间的联动
                                                     jimuReportHref(reportHref,data,"1");
                                                 }
                                             });
                                     }
                                 })
                                 .onExportExcelAll(function(){
                                     //导出全部excel
                                     //window.open(baseFull+`/jmreport/exportAllExcel/${excelConfigId}?token=${token}`);
                                     xs.getLayerBase64().then(values=>{
                                         base64Arry = values;
                                         var dataStr = '';
                                         let queryParam  = rpViewInst.getRpQueryParam();
                                         let pageInfo = xs.getPageInfo();
                                         queryParam.pageNo = pageInfo.pageNo
                                         queryParam.pageSize = pageInfo.pageSize
                                         Object.keys(exportDefaultValue).map(key=>{
                                           if(!queryParam[key]){
                                             queryParam[key] = exportDefaultValue[key]
                                           }
                                         })
                                         if (base64Arry != null && base64Arry.length > 0){
                                             dataStr = JSON.stringify({excelConfigId:excelConfigId,base64Arry:base64Arry,queryParam: queryParam});
                                         }else {
                                             dataStr = JSON.stringify({excelConfigId:excelConfigId, queryParam:queryParam});
                                         }
                                         Vue.prototype.$Spin.show();
                                         $http.post({
                                             contentType:'json',
                                             url:api.exportAllExcel,
                                             data : dataStr,
                                             success:(result)=>{
                                                 ajaxFileDownload(result.file, result.name);
                                             },
                                             error:(e)=>{
                                                 Vue.prototype.$Spin.hide();
                                                 xs.tip(e.error);
                                             }
                                         })
                                     })
                                 })
                             if(expData && "{}"!=JSON.stringify(expData)){
                                 xs.setExpData(expData);
                             }
                             let finalJsonObj = JSON.parse(str);
                             if(finalJsonObj.chartList && finalJsonObj.chartList.length >0){
                                 finalJsonObj.chartList.forEach(function(item){
                                     let config = JSON.parse(item.config);
                                     //处理地图信息
                                     if (config.geo){
                                         let map = config.geo.map;
                                         //如果是china，则换成后台可以解析的code,china 为 100000
                                         if(map=="china"){
                                           map=100000;
                                         }
                                         $http.post({
                                             contentType:'json',
                                             url:api.queryMapByCodeUseOrigin,
                                             data:JSON.stringify({name:map,reportId: view.excelConfigId}),
                                             success:(result)=>{
                                                 let data=JSON.parse(result.data);
                                                 if(item.extData && item.extData.dbCode){
                                                     item.extData.mapData = data;
                                                     //图表刷新
                                                     let isTiming = item.extData.isTiming;//是否开启定时刷新
                                                     if(isTiming){
                                                         let intervalTime = item.extData.intervalTime; //刷新时间
                                                         let timer=setInterval(function(){
                                                             //数据刷新
                                                             refreshData(item,dataMap)
                                                         },intervalTime * 1000);
                                                     }
                                                 }
                                                 xs.registerMap(result.name,data);
                                                 xs.updateChart(item.layer_id ,config);
                                             }
                                         })
                                     }
                                     //判断图表设置定时刷新
                                     else if(item.extData && item.extData.dbCode){
                                         //图表刷新
                                         let isTiming = item.extData.isTiming;//是否开启定时刷新
                                         if(isTiming){
                                             let intervalTime = item.extData.intervalTime; //刷新时间
                                             let timer=setInterval(function(){
                                                 //数据刷新
                                                 refreshData(item,dataMap)
                                             },intervalTime * 1000);
                                             rpViewInst.timerArr.push(timer)
                                         }
                                     }
                                 })
                             }
                             view.viewReport(str,excelDataList, 'firstLoad');
                             Vue.prototype.$Spin.hide();

                         },(res)=>{
                             Vue.prototype.$Spin.hide();
                         })
                     }else if(result=="1"){
                         Vue.prototype.$Spin.hide();
                         Vue.prototype.$Message.error({
                             content:"该链接已失效！",
                             duration:3
                         });
                     }else if(result=="2"){
                         if(rpViewInst.lock){
                             Vue.prototype.$Message.error({
                                 content:"密码错误！",
                                 duration:3
                             });
                         }
                         rpViewInst.lockVisible=true
                         //需要输入密码，进行弹窗
                         Vue.prototype.$Spin.hide();
                     }else if(result=="3"){
                         Vue.prototype.$Message.error({
                             content:"该链接不存在！",
                             duration:3
                         });
                     }
                 })
             }
             //先校验 参数 再
             $jm.checkParam(excelConfigId,(result)=>{
                 //Vue.prototype.$Spin.hide();
                 let requestUrlParam = getRequestUrlParam();
                 if(reportMode=='dev'){
                     // 判断地址参数是否和数据库参数匹配
                     var match = dbParamMatchUrlParam(result, requestUrlParam)
                     if(match){
                         //如果匹配 则直接显示报表
                         loadExcelView(requestUrlParam)
                     }else{
                         //如果不匹配 则弹框 需要用户填写相关参数
                         rpViewInst.show(result, loadExcelView);
                         //  loadExcelView()
                     }
                 }else{
                     let dbParam = {}
                     for(let item of result){
                         dbParam[item.paramName] = item.paramValue
                     }
                     let inputparam = Object.assign(dbParam, requestUrlParam)
                     loadExcelView(inputparam)
                 }
             },(res)=>{
                 //无参数
                 loadExcelView();
             })
         });
     }
     //增加访问次数
     $jm.addViewCount(excelConfigId);
 }
 view.getExcelData=function(dataList){
     const excelDataList = {};
     for(let key in dataList){
         const dataObj = dataList[key];
         if(!dataObj){
             continue;
         }
         if(!dataObj.list){
             excelDataList[key] = []
             excelDataList[`${key}_isPage`] =dataObj['isPage'];
         }else{
             excelDataList[key] = JSON.parse(JSON.stringify(dataObj.list));
             excelDataList[`${key}_isPage`] =dataObj['isPage'];
         }

     }
     return excelDataList;
 }
 /**
  * 获得分页信息
  * @param {} resultList
  */
 view.dealDataList=function(resultList){
     //处理之前老数据,只有一个数据源，
     if(Object.keys(resultList).length==1){
          let dataKey = Object.keys(resultList)[0];
          let dataObj = resultList[dataKey];
          if(!dataObj.isPage && dataObj.total>1){
              dataObj['isPage']='1';
          }
     }
     const dataList = JSON.parse(JSON.stringify(resultList));
     let count = 1;
     const pageObj = {}
     Object.keys(dataList).forEach(key=>{
         const dataObj = dataList[key];
         Object.keys(dataObj).forEach(resultKey=>{
             if(resultKey.endsWith("total")){
                 //是否是分页数据集
                 if(dataObj[`isPage`]=='1' &&dataObj['list'].length>0){
                     count = dataObj[`count`];
                     pageObj[key] = dataObj['total'];
                 }
                 delete dataObj['total'];
                 delete dataObj['count'];
             }
         })
     });
     return {
         dataList,
         count,
         pageObj
     }
 }

 // 将表达式的json数据转化成真实数据
 view.parseData = function(str,dataList,isPrint=false){
     //console.log('+++++++++++++++++++++++++++++++++')
     //console.log(str)
     //console.log('数据集',dataList)
     let strObj = JSON.parse(str);
     if(!dataList || JSON.stringify(dataList) == '{}') {
         return strObj
     }
     //console.log('----------------------------')
     if(isPrint){
         dataList = view.dealDataList(dataList);
         dataList = view.getExcelData(dataList.dataList);
     }
     Object.keys(dataList).forEach(key=>{
         delete dataList[`${key}_count`]
         if(key.endsWith("_total")){
             delete  dataList[key]
         }
     });
     //将数据里的key转成小写
     Object.values(dataList).forEach(item=>{
        item instanceof Array && item.forEach(row=>{
             Object.keys(row).forEach(key=>{
                 const lowerKey = key.toLowerCase();
                 if (lowerKey != key) {
                     row[lowerKey] = row[key];
                     delete row[key];
                 }
             })
         })
     });
     const dataListLen = Object.keys(dataList)
          .filter(item=>!item.endsWith('_isPage'))
          .length
     if(dataListLen==1){
         // 有一个数据集，并且数据集中只有一条记录
         let resultArray = Object.values(dataList);
         if(!resultArray) return
         const dataArryLen = resultArray[0].length
         if(dataArryLen==1){
            // strObj = dealOneData(strObj,dataList)
         }else if(dataArryLen>1){
             //strObj =  dealManySource(strObj,dataList)
         }else {
             //strObj = dealNoData(strObj)
         }
     }else if(dataListLen>1){
         //strObj = dealManySource(strObj,dataList)
     }
     // 字典数据处理
     //dictDataHandler(strObj);
     //console.log('----------------------------')
     //console.log('数据对象',strObj)
     //console.log('解析后内容',JSON.stringify(strObj.rows));
     //console.log('----------------------------')
     return strObj;
 }
 view.dealPicTop=function(){
     if(!xs) return;
     const imageData = xs.data.imgList;
     if(!imageData) return;
     //判断是否有套打图片,处理套打图片对不上问题
     const isBackend = imageData.some(item=>item.isBackend)
     if(isBackend){
      //   const $content = $(".x-spreadsheet-overlayer-content")[0];
        // $content.style.top='0px';
     }
 }
 view.viewReport = function(str,dataList,type){
     const viewData = view.parseData(str,dataList);
     if(type==='rpchange'){
         // 分页工具条改变触发事件
         viewData.imgList = []
         viewData.chartList = []
     }else if(type == 'firstLoad'){
        // 页面第一次加载事件
         if(viewData.rpbar){
             let pageSize = viewData.rpbar.pageSize;
             if(pageSize){
                 view.pageSize = pageSize;
             }
         }
     }
     viewData['currentPageSize'] = view.pageSize;
     xs.loadData(viewData);
     if(viewData.rpbar){
         rpViewInst.rpBar = viewData.rpbar.show;
     }
     view.dealPicTop();
 }


function ajaxFileDownload(data,filename) {
    var a = document.createElement('a');
    var bstr = atob(data), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    var blob =  new Blob([u8arr], { type: "application/octet-stream,charset=UTF-8" });
    var url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
    Vue.prototype.$Spin.hide();
}

 //图表预览替换查询SQL数据
function querySqlData(echartJson,sqlxAxis,sqlseries,sqlgroup,sqltype,result){
    let charType = echartJson.series[0].type;
    let resultArr=JSON.parse(JSON.stringify(result));//记录结果集数据
    if (charType === 'bar' || charType === 'line'){
        let xAxisData = [];
        let seriesData = [];
        let legendData = [];//图例数据
        result.forEach(item=>{
            for(var d in item) {
                if (xAxisData.indexOf(item[d]) != -1){
                    let index = xAxisData.indexOf(item[d]);
                    seriesData[index] = seriesData[index] + item[sqlseries];
                    delete item[sqlseries];
                }else {
                    if (d === sqlxAxis){
                        xAxisData.push(item[d]);
                    }
                    if (d === sqlseries){
                        seriesData.push(item[d]);
                    }
                    if (d === sqlgroup && legendData.indexOf(item[d]) == -1){
                        legendData.push(item[d]);
                    }
                }
            }
        });
        echartJson.xAxis.data = xAxisData;
        //图例数据大于0，多组分类数据
        if(legendData.length>1){
            //处理分组数据
            let series=[];
            legendData.forEach((name,index)=>{
                //获取series公共样式
                let seriesStyle = echartJson.series.filter(item=>item.name==name);
                //let commonObj=Object.assign(echartJson.series[0],{name:name,data:[]});
                let commonObj=Object.assign(seriesStyle[0],{name:name,data:[]});
                let seriesObj=JSON.parse(JSON.stringify(commonObj));
                //获取series的data数据集
                for(let item of resultArr){
                    if(seriesObj.data.length==xAxisData.length){
                        break;
                    }
                    if(item[sqlgroup] == name){
                        seriesObj.data.push(item[sqlseries]);
                    }
                }
                series[index]=seriesObj;
            });
            console.log("series===>",series);
            echartJson.series=series;
        }else{
            echartJson.series[0].data = seriesData;
        }
        return echartJson;
    }else if (charType === 'pie'){
        let objpie = [];
        result.forEach(item=>{
            var objres = {};
            for(var d in item) {
                if (d === sqlxAxis){
                    objres['name'] = item[d];
                }
                if (d === sqlseries){
                    objres['value'] = item[d];
                }
            }
            objpie.push(objres);
        });
        let colorList=echartJson.series[0].data.map(item=>{return item.itemStyle;});
        echartJson.series[0].data = mergeObject(objpie);
        console.log('colorList',colorList);
        echartJson.series[0].data.forEach((item,index)=>{
            item.itemStyle=colorList[index];
        });
        return echartJson;
    }else if(charType === 'scatter'){
        echartJson.series[0].data = result.map(item=>{
            return [item[sqlxAxis],item[sqlseries]]
        });
        return obj;
    }
}
//图表预览替换查询api数据
function queryApiData(echartJson,result){
    let charType = echartJson.series[0].type;
    if (charType === 'bar' || charType === 'line'){
        let xAxisData = [];
        let seriesData = [];
        result.data.forEach(item=>{
            for(var d in item) {
                if (d === 'name'){
                    xAxisData.push(item[d]);
                }
                if (d === 'value'){
                    seriesData.push(item[d]);
                }
            }
        });
        echartJson.xAxis.data = xAxisData;
        //包含分类多组数据处理
        if(result.category){
            let series=[];
            //设置图例数据
            let obj=JSON.parse(JSON.stringify(echartJson.series));
            result.category.forEach((name,index)=>{
                //获取series默认样式
                let commonObj=Object.assign(echartJson.series[0],{name:name,data:[]});
                //判断原有样式是否存在
                let hasSeries = obj.filter(item=>item.name === name);
                if(hasSeries!=null && hasSeries.length>0) {
                    commonObj=Object.assign(hasSeries[0],{name:name,data:[]});
                }
                //多种图表的series公共样式获取
                 /*if(result.type && result.type.length>0){
                     let filter = obj.filter(serie=>serie.type == result.type[index]);
                     if(filter&&filter.length>0){
                         //设置一个默认样式
                         commonObj=Object.assign(filter[0],{name:name,data:[]});
                         //设置已经存在的样式
                         let hasSeries = filter.filter(item=>item.name === name);
                         if(hasSeries!=null && hasSeries.length>0) {
                             commonObj=Object.assign(hasSeries[0],{name:name,data:[]});
                         }
                     }
                 }*/
                let seriesObj=JSON.parse(JSON.stringify(commonObj));
                //获取series的data数据集
                seriesObj.data=seriesData.map(item=>{
                    return item[index]
                });
                series[index]=seriesObj;
            });
            echartJson.series=series;
        }else{
            echartJson.series[0].data = seriesData;
        }
        return echartJson;
    }else if (charType === 'pie'){
        //饼图数据替换、自定义颜色替换
        let colorList=echartJson.series[0].data.map(item=>{return item.itemStyle;});
        console.log('colorList',colorList);
        echartJson.series[0].data = result.data;
        if(echartJson.series[0].data.length>0){
            echartJson.series[0].data.forEach((item,index)=>{
                item.itemStyle=colorList[index];
            });
        }
        return echartJson;
    }else if(charType === 'scatter'){
        echartJson.series[0].data = result.data.map(item=>{return [item.name,item.value]});
        return obj;
    }else if(charType === 'map'){
        echartJson.data=result.data;
        return echartJson;
    }
}

//数组对象去重
function mergeObject(array) {
    var arrayFilted = [];
    array.forEach(value=>{
        if ( arrayFilted.length == 0 ) {
            arrayFilted.push(value);
        }else{
            let flag = true;
            arrayFilted.forEach(valueIndex=>{
                if (valueIndex.name && valueIndex.name === value.name) {
                    valueIndex.value = valueIndex.value + value.value;
                    flag = false;
                }
            });
            if (flag){
                arrayFilted.push(value);
            }
        }
    });
    return arrayFilted;
}

 /**
  * 单条数据
  * @param strObj
  * @param dataList
  * @returns {*}
  */
 function dealOneData(strObj,dataList) {
     // 有一个数据集，并且数据集中只有一条记录，详情表单
     let resultArray = Object.values(dataList);
     let dbsArray = Object.keys(dataList)
     let  resultList = resultArray[0][0];
     let excelRows = strObj.rows;
     for(let rowKey in excelRows){
         let cells = excelRows[rowKey].cells;
         if(!cells) continue;
         for(let cellKey in cells){
             let cell = cells[cellKey];
             let text  = cell.text;
             if(reg.test(text)){
                 let dbs = text.match(reg)[1]
                 if(dbsArray.indexOf(dbs)>=0){
                     const cellText = resultList[text.match(reg)[2]] || "";
                     cell.text = ''+cellText;
                 }
             }
         }
     }
     return strObj;
 }

/**
 * 解析 数据
 * @param height
 * @param startRowNum
 * @param dataCells
 * @param resultDataList
 * @returns {{}|[]}
 */
 function getRowData(height,startRowNum,dataCells,resultDataList){
    if(!resultDataList) return {};
    let rowData = {};

    //判断有无分组
    let dataCellsFlag = 0;
    let dataRightFlag = 0;
    for (let cellIndex in dataCells){
        //合计
        if (dataCells[cellIndex].aggregate === "group"){
            dataCellsFlag++;
        }
        //方向
        if (dataCells[cellIndex].direction === "right"){
            dataRightFlag++;
        }
    }
    //处理数据
    let newresultList = [];
    const groupObj=[];
    const mergesArr=[];
    let maxRowNum = startRowNum;
    let groupStartNum = maxRowNum;
    if (dataCellsFlag === 0 && dataCellsFlag === 0 && dataRightFlag === 0){
        //列表数据
        rowData = backSelectData(resultDataList,dataCells,maxRowNum,rowData,height, mergesArr)
    }else if (dataCellsFlag > 0 && dataRightFlag == 0){
        //分组数据 maxRowNum 行数
        newresultList = backGroupData(dataCellsFlag,dataCells,resultDataList,newresultList,groupObj)
        backSelectData(newresultList,dataCells,maxRowNum,rowData,height)
        //设置合并单元格
        let maxendRowNum = startRowNum + newresultList.length;
        for(let key in groupObj){
            if (maxRowNum >= maxendRowNum){
                maxRowNum = groupStartNum;
            }
            let cellIndex = groupObj[key].cellIndex;
            const count = groupObj[key].count;
            const cell = rowData[maxRowNum].cells[cellIndex];
            if (count>1){
                cell.merge = [count-1,0];
            }
            maxRowNum =  maxRowNum+1;

            //将合并的单元格merge添加到数组里
            if (count>1){
                let letter = String.fromCharCode(64 + parseInt(cellIndex+1));
                let endsite = count+maxRowNum-1;
                let mergelocat = letter+maxRowNum+':'+letter+endsite;
                mergesArr.push(mergelocat);
            }
            //将分组后的多余数据删除
            for (let m = 1; m < count; m++) {
                rowData[maxRowNum] && delete  rowData[maxRowNum].cells[cellIndex];
                maxRowNum++;
            }
        }
    }else if (dataRightFlag > 0){
        //分组数据 maxRowNum 列数
        newresultList = backGroupData(dataRightFlag,dataCells,resultDataList,newresultList,groupObj)
        backSelectRightData(newresultList,dataCells,maxRowNum,rowData,height);
        //设置合并单元格
        let maxendRowNum = startRowNum + newresultList.length;
        for(let key in groupObj){
            if (maxRowNum >= maxendRowNum){
                maxRowNum = groupStartNum;
            }
            let cellIndex = groupObj[key].cellIndex;
            const count = groupObj[key].count;
            const cell = rowData[cellIndex].cells[maxRowNum];
            if (count>1){
                cell.merge = [0,count-1];
            }
            maxRowNum =  maxRowNum+1;
            for (let m = 1; m < count; m++) {
                rowData[cellIndex] && delete  rowData[cellIndex].cells[maxRowNum];
                maxRowNum++;
            }
        }
    }
    //将分组的数据和合并的数组返回
    let arrmap = [];
    if (mergesArr.length > 0){
        arrmap["mergesArr"] = mergesArr;
    }
     arrmap["rowData"] = rowData;
    return arrmap;
 }

 //处理分组数据
function backGroupData(dataCellsFlag,dataCells,resultDataList,newresultList,groupObj){
    var listgroup = [];
    let isProcessed = true;
    let listgroupSize = 0;
    for (let cellIndex in dataCells){
        if(!dataCells[cellIndex].text) continue;
        if (dataCells[cellIndex].text.indexOf("group(") != -1 || dataCells[cellIndex].text.indexOf("groupRight(") != -1 ) {
            let cellMe = subStringStr(dataCells[cellIndex].text, "#{", "}").split(".")[1];
            let field = "";
            if (dataCells[cellIndex].text.indexOf("group(") != -1 ){
                field = subStringStr(cellMe, "group(", ")");
            }else if(dataCells[cellIndex].text.indexOf("groupRight(") != -1 ){
                field = subStringStr(cellMe, "groupRight(", ")");
            }
            //一个字段分组
            if (dataCellsFlag === 1){
                listgroup = util.arrayGroupBy(resultDataList,field);
                listGroupFe(listgroup,groupObj,field,cellIndex);
                for (let i = 0; i < listgroup.length; i++) {
                    newresultList.push.apply(newresultList,listgroup[i]);
                }
            }else if (dataCellsFlag > 1){
                //多个字段分组
                if(isProcessed){
                    //第一次分组
                    listgroup = util.arrayGroupBy(resultDataList,field);
                    listGroupFe(listgroup,groupObj,field,cellIndex);
                    listgroupSize = listgroup.length;
                }else {
                    //其余字段分组
                    var listgroupThree = [];
                    newresultList = [];
                    for (let i = 0; i < listgroupSize; i++) {
                        var listgroupTwo = [];
                        listgroupTwo = util.arrayGroupBy(listgroup[i],field);
                        listGroupFe(listgroupTwo,groupObj,field,cellIndex);
                        for (let j = 0; j < listgroupTwo.length; j++) {
                            listgroupThree.push(listgroupTwo[j]);
                        }
                    }
                    //将分组数据合并为集合
                    listgroup = listgroupThree;
                    listgroupSize = listgroup.length;
                    for (let i = 0; i < listgroupThree.length; i++) {
                        newresultList.push.apply(newresultList,listgroupThree[i]);
                    }
                }
            }
            isProcessed = false;
        }
    }
    return newresultList;
}

//将需要合并的个数和列位置记录
function listGroupFe(listgroup,groupObj,field,cellIndex){
    listgroup.forEach(listItem=>{
        const groupFieldObj = {};
        groupFieldObj.count = listItem.length;
        groupFieldObj.cellIndex = Number(cellIndex);
        groupObj.push(groupFieldObj);
    })
    return groupObj;
}

//将数据提取为rows中的格式
function backSelectData(resultDataList,dataCells,maxRowNum,rowData,height, mergesArr){
    for (let i = 0; i < resultDataList.length; i++) {
        let resultObj = resultDataList[i];
        let newCellObj={};
        for (let cellIndex in dataCells){
            let tempCell = {...dataCells[cellIndex]}
            if(tempCell.virtual){
                if(i>0){
                    delete tempCell['virtual']
                }
            }
            let text = tempCell.text;
            newCellObj[cellIndex] = tempCell
            if(reg.test(text)){
                if (text.match(reg)[2].indexOf("group(") != -1){
                    let field2 = subStringStr(text.match(reg)[2],"group(",")");
                    text = resultObj[field2] || "";
                    newCellObj[cellIndex].text = text;
                }else {
                    text = resultObj[text.match(reg)[2]] || "";
                    newCellObj[cellIndex].text = text;
                }
            }

            if(mergesArr){
                if(dataCells[cellIndex].merge){
                    let arr = dataCells[cellIndex].merge
                    if(arr && arr.length>0){
                        let charCode = parseInt(cellIndex)+ 64 + 1
                        let letter = String.fromCharCode(charCode);
                        let letter2 = String.fromCharCode(charCode+parseInt(arr[1]));
                        let mergeCode = letter+(maxRowNum+1)+':'+letter2+(maxRowNum+1)
                        mergesArr.push(mergeCode)
                    }
                }
            }

        }
        rowData[maxRowNum] = {};
        rowData[maxRowNum].height = height;
        rowData[maxRowNum].cells=newCellObj;
        maxRowNum++;
    }
    return rowData;
}

function backSelectRightData(resultDataList,dataCells,maxRowNum,rowData,height){
    for (let cellIndex in dataCells){
        var dataRight = [];
        rowData[cellIndex] = {};
        rowData[cellIndex].height = height;
        let startMaxRowNum = maxRowNum;
        for (let i = 0; i < resultDataList.length; i++) {
            let newCellObj={};
            newCellObj[startMaxRowNum] = {...dataCells[cellIndex]};
            let text = newCellObj[startMaxRowNum].text;
            let resultObj = resultDataList[i];
            if(reg.test(text)){
                if (text.match(reg)[2].indexOf("groupRight(") != -1){
                    let field2 = subStringStr(text.match(reg)[2],"groupRight(",")");
                    let textfield = resultObj[field2] || "";
                    newCellObj[startMaxRowNum].text = textfield;
                }else {
                    let textfield = resultObj[text.match(reg)[2]] || "";
                    newCellObj[startMaxRowNum].text = textfield;
                }
            }
            rowData[cellIndex].cells =  newCellObj;
            dataRight.push(rowData[cellIndex].cells);
            startMaxRowNum++;
        }
        for (let dataRightindex in dataRight){
            rowData[cellIndex].cells = { ...rowData[cellIndex].cells,...dataRight[dataRightindex]};
        }
    }
    return rowData;
}



//截取字符串方法
function subStringStr(str,strStart,strEnd){
    let strStartIndex = str.indexOf(strStart);
    let strEndIndex = str.indexOf(strEnd);
    if (strStartIndex < 0) {
        return "";
    }
    if (strEndIndex < 0) {
        return "";
    }
    let result = str.substring(strStartIndex, strEndIndex).substring(strStart.length);
    return result;
}

 /**
  * 没有数据情况
  * @param strObj
  */
 function dealNoData(strObj={}) {
     const rows = strObj.rows
     for(const rowIndex in rows){
         const cells = rows[rowIndex].cells
         for(const cellIndex in cells){
            const cell = cells[cellIndex]
             if(reg.test(cell.text)){
                 cell.text = ''
             }
         }
     }
     return strObj;
 }

 function getMaxIndexInRows(strObj){
     if(!strObj) return 0;
     return  Math.max(...Object.keys(strObj).filter(item=>item!='len').map(item=>Number(item)))
 }

/**
 * 组装自定义的RowObj
 * @param strObj
 * @param dataList
 * @returns {{}}
 */
 function getRowObj(strObj={},dataList={}){
     let rowObj = {};
     //保留数据集行
     let strRows = JSON.parse(JSON.stringify(strObj.rows));
     let strRowObj = {};
     //循环行
     for(let rowIndex in strRows){
         if(Number(rowIndex)<0) continue;
         strRowObj = strRows[rowIndex];
         //获取一行中的全部单元格对象
         let cellObj = strRowObj.cells;
         if(!cellObj){
             continue;
         }
         //数据集名称
         let dbName='';
         //循环一行中的所单元格
         for(let cellIndex in cellObj ){
             //单元格内容
             let cellText = cellObj[cellIndex].text || "";
             //当单元格内容是表达式时
             if(reg.test(cellText)){
                 //匹配数据集数据(返回一个匹配后的数组对象)
                 let cellTextArr = cellText.match(reg);
                 dbName = cellTextArr[1];
                 //字段名称
                 let field = cellTextArr[2];
                 //当该数据集不存在时初始化该数据集
                 if(!rowObj[dbName]){
                     rowObj[dbName] = {};
                     //记录字段的行位置
                     rowObj[dbName].rowNums = [];
                     //记录字段的列位置
                     rowObj[dbName].cellNums = [];
                     //记录字段
                     rowObj[dbName].tableFields = [];
                     //行高
                     rowObj[dbName].height = strRowObj.height;
                     //记录数据集样式(一行内的原始(解析前)数据配置)
                     rowObj[dbName].dataCells = strRows[rowIndex].cells;
                     //数据总条数
                     const total = (dataList[dbName] && dataList[dbName].length) || 0;
                     if(total>1){
                         //当数据集有多条记录时记录数据集上一行记录
                         rowObj[dbName].prevRow=strRows[(rowIndex-1<0)?0:(rowIndex-1)];
                     }else{
                         //当数据集有1条或者没有时取当前行
                         rowObj[dbName].prevRow =strRows[rowIndex];
                     }
                     //当前数据集行索引位置
                     rowObj[dbName].dataSetNumber=Number(rowIndex);
                     //是否分页
                     rowObj[dbName].isPage = dataList[`${dbName}_isPage`]
                 }
                 //保存所有字段的的行列索引
                 rowObj[dbName].rowNums.push(parseInt(rowIndex));
                 rowObj[dbName].cellNums.push(parseInt(cellIndex));
                 rowObj[dbName].tableFields.push(field);
             }else{
                 if(rowObj[dbName]){
                     rowObj[dbName].rowNums.push(parseInt(rowIndex));
                     rowObj[dbName].cellNums.push(parseInt(cellIndex));
                     rowObj[dbName].tableFields.push("");
                 }
             }

         }
     };
     //数据集高度
     if(strObj.dataSetNumber){
       strObj.dataSetHeight = strObj.rows[strObj.dataSetNumber].height;
     }
    return rowObj;
 }

/**
 * 经查询没有数据的时候需要 将表达式置为空
 */
function handleNoDataRow(strObj, rowObj) {
    let arr =[]
    Object.keys(rowObj).map(db=>{
        let tempCells = rowObj[db]['dataCells']
        if(tempCells){
            Object.keys(tempCells).map(colIndex=>{
                let cell = tempCells[colIndex]
                if(cell){
                    let exp = cell.text
                    if(exp){
                        arr.push(exp)
                    }
                }
            })
        }
    })
    const rows = strObj.rows
    for(const rowIndex in rows){
        const cells = rows[rowIndex].cells
        for(const cellIndex in cells){
            const cell = cells[cellIndex]
            if(cell && cell.text && arr.indexOf(cell.text)>=0){
                cell.text = ''
            }
        }
    }
}


 /**
  * 多条数据
  * @param strObj:表格配置
  * @param dataList:数据集合
  * @returns {*}
  * 调用方法:parseData
  */
 function dealManySource(strObj={},dataList={}){
    let rowObj = getRowObj(strObj,dataList);
    if(Object.values(rowObj) && Object.values(rowObj).length>0){
         const dataSetNumberArr = Object.values(rowObj).filter(item=>item['isPage']=='1').map(item=>item.dataSetNumber);
         if(dataSetNumberArr && dataSetNumberArr.length>0){
             //保留数据集位置，打印全部时，第二页到最后只打印列表数据
             strObj.dataSetNumber = Math.min(...dataSetNumberArr);
         }
    }
     const rowNumObj = {};
     //行数
     const initRowLen = Object.keys(strObj.rows).length;
     for(let key in rowObj){
       //遍历数据
       let tempObj = rowObj[key];
       if(!tempObj) continue;
       const dataCells = tempObj.dataCells;
       const currentCell = {};
       for (let cellIndex in dataCells){
           let cellObj = dataCells[cellIndex];
           const cellText = String(cellObj.text);
           if(!cellText) continue;
           if(cellText.startsWith('#{')){
               if(cellText.includes(key)){
                currentCell[cellIndex] = dataCells[cellIndex];
               }
           }else{
            currentCell[cellIndex] = dataCells[cellIndex];
           }
       }
       const rowNumSize = new Set(tempObj.rowNums).size;
       const cellNumSize = new Set(tempObj.cellNums).size;
       if(rowNumSize>1 && cellNumSize != 1){
          strObj.rows = {...strObj.rows,...dealOneData(strObj,{[key]:dataList[key]}).rows};
       }else{
         let currentDataRow = {};
         if (rowNumSize>1){
             //横向分组处理数据
             //取含有表达式的
             const currentCellNum = Math.min(...tempObj.cellNums);
             let strRows = JSON.parse(JSON.stringify(strObj.rows));
             const currentRow = {};
             for(let rowIndex in strRows) {
                 if (Number(rowIndex) < 0) continue;
                 let strrCells = strRows[rowIndex].cells;
                 for(let cellsIndex in strrCells) {
                     if (Number(cellsIndex) === currentCellNum){
                         currentRow[rowIndex] = strrCells[cellsIndex];
                     }
                 }
             }
             //currentCellNum 列数
             let arrmap = getRowData(tempObj.height,currentCellNum,currentRow,dataList[key]);
             currentDataRow = arrmap["rowData"];
             //处理设置单元格宽度除了第一行不起作用
             let strCol = JSON.parse(JSON.stringify(strObj.cols));
             let colSty = strCol[currentCellNum];
             for (let i = 0; i < dataList[key].length; i++) {
                 strCol[currentCellNum+i] = colSty
             }
             strObj.cols = strCol;
         }else {
             //获得当前行位置 currentRowNum 行数
             const currentRowNum = Math.min(...tempObj.rowNums);
             let arrmap = getRowData(tempObj.height,currentRowNum,currentCell,dataList[key]);
             currentDataRow = arrmap["rowData"];
             let  huamergesArr = arrmap["mergesArr"];
             strObj.merges.push.apply(strObj.merges,huamergesArr);
         }
       const newDataLen = Object.keys({...strObj.rows,...currentDataRow}).length;
         if(!currentDataRow || JSON.stringify(currentDataRow) == "{}"){
            //找到表达式对应的单元格  改成空
             handleNoDataRow(strObj, rowObj)
             continue;
         }
           let tempRepeatRange = getRepeatRange(currentDataRow)
           if(!strObj.repeatRange){
               strObj.repeatRange = []
           }
           strObj.repeatRange.push(tempRepeatRange)
           //取出新数据的位置
           const dataKeys = Object.keys(currentDataRow);
           const dataStartIndex = Math.min(...dataKeys);
           const dataEndIndex =  Math.max(...dataKeys);

       if(newDataLen<=initRowLen && cellNumSize != 1){
           //没有新数据生成
           //strObj.rows = {...strObj.rows,...currentDataRow};
           //update-begin-author:taoyan date:20201016 for:全体数据平移
           let rowCycleTrans = new RowCycleTrans(strObj, dataStartIndex, dataEndIndex, tempObj['cellNums'], currentDataRow);
           rowCycleTrans.getTransRows();
           //update-end-author:taoyan date:20201016 for:全体数据平移
           rowObj = getRowObj(strObj,dataList);
       }else{
           let rightFlag = false;
           tempObj.tableFields.forEach(item=>{
               if (item.indexOf("groupRight") != -1){
                   rightFlag = true;
               }
           })
           if (cellNumSize === 1 && rightFlag){
               //横向分组提取数据
               for(let rowIndex in currentDataRow) {
                   for(let cellIndex in currentDataRow[rowIndex].cells) {
                       strObj.rows[rowIndex].cells[cellIndex] = currentDataRow[rowIndex].cells[cellIndex];
                   }
               }
               rowObj = getRowObj(strObj,dataList);
           }else {
               //数据有新增
               maxRowNum =  (
                   Object.keys({...strObj.rows,...currentDataRow})
                       .filter(item=>item!='len')
               ).length;
               let rowsKeys = Object.keys( strObj.rows);
               let rowInterval=0;
               for (let i =0 ; i <rowsKeys.length;i++ ){
                   if(rowsKeys[i+1]-rowsKeys[i]>1){
                       rowInterval = rowsKeys[i+1]-rowsKeys[i]-1
                       break;
                   };
               };
               //取出数据剩余的部分
               const rowDataOthers  = {};
               for(let rowIndex in strObj.rows){
                   //update-begin-author:taoyan date:20201202 for:行索引可能为其他特殊字符 此处过滤掉
                   if(rowIndex=='len' || !parseInt(rowIndex) || Number(rowIndex)<=dataStartIndex) continue;
                   //update-end-author:taoyan date:20201202 for:行索引可能为其他特殊字符 此处过滤掉
                   const hasText = Object.values(strObj.rows[rowIndex].cells).some(item=>item.text);
                   if(hasText){
                       rowDataOthers[rowIndex] = {...strObj.rows[rowIndex]};
                   }
               }
               //update-begin-author:taoyan date:20201016 for:全体数据平移
               let rowCycleTrans = new RowCycleTrans(strObj, dataStartIndex, dataEndIndex, tempObj['cellNums'], currentDataRow);
               rowCycleTrans.getTransRows();
               //update-end-author:taoyan date:20201016 for:全体数据平移
               let maxRowIndex = getMaxIndexInRows(strObj.rows)+1;
               //if(rowInterval>0) maxRowIndex+=rowInterval;   //最大行值加间隔行数 key - dataStartIndex
               let newOthers = {};
               for(let key in rowDataOthers){
                   newOthers[maxRowIndex++] ={...rowDataOthers[key]};
               }
              // strObj.rows = {...strObj.rows,...newOthers}
               rowObj = getRowObj(strObj,dataList);
           }

       }
       }
     }
     const minRowNum = Math.min(...Object.values(rowNumObj));
     strObj.styles.push({"border":{"bottom":["thin","#000"],
             "top":["thin","#000"],"left":["thin","#000"],"right":["thin","#000"]}});
     const borderStyleLen = strObj.styles.length-1;
     for(let key in strObj.rows){
         if(key<minRowNum) continue;
         const cells = strObj.rows[key].cells;
         if(!cells) continue;
         Object.values(cells).forEach(cell=>{
             if(!cell.style){
                 cell.style=borderStyleLen
             }
         })
     }
     return strObj;
 }

 /**
  * 获取url参数
  */
 function getRequestUrlParam() {
     var url = location.search;
     var theRequest = new Object();
     if (url.indexOf("?") != -1) {
         var str = url.substr(1);
         strs = str.split("&");
         for(var i = 0; i < strs.length; i++) {
             theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
         }
     }
     return theRequest;
 }

/**
 * 参数取值 type:
 * last上一页  next下一页  first首页  end尾页  pageSize每页条数
 * @param pageNo 当前页
 * @param pageSize 每页显示条数
 */
 function getPageResult(pageNo,pageSize=10,type){
     if(type){
       Vue.prototype.$Spin.show();
       //判断是否为pageSize
       if(type == 'pageSize'){
         view.pageSize = pageSize;
         //当时pageSize的时候此时当前页数为1
         pageNo = 1;
         //获取文本框中的pageNo并赋值
         let btnInput = document.getElementsByClassName("btn-input");
         btnInput[0].querySelector('input').value = 1;
       }
     }
     const requestParam = getRequestUrlParam();
     requestParam.pageNo = pageNo;
     requestParam.pageSize = pageSize;
    // requestParam['onlyPageData'] = '1'
     query2RequestParam(rpViewInst.queryInfo, requestParam);
     $jm.excelView(view.excelConfigId,requestParam,(result)=> {
         var str = result.jsonStr;
         if(!str){
            Vue.prototype.$Spin.hide();
            return;
         }
         const dealDataList  = view.dealDataList(result.dataList);
         const dataList = dealDataList.dataList;
         //获取分页参数
         const pageObj = dealDataList.pageObj;
         if(Object.keys(pageObj).length===1){
             xs.data.settings.page = pageNo;
             xs.data.settings.total = pageObj[Object.keys(pageObj)[0]];
             if(xs.data.settings.total==1 && rpViewInst.rpBar == true){
                 //下一页和最后一页禁用
                 xs.sheet.rpbar.btn_next.btn.el.disabled=true;
                 xs.sheet.rpbar.btn_end.btn.el.disabled=true;
             }
         }
         if(rpViewInst.rpBar == true){
             xs.sheet.rpbar.btn_input.countSpan.el.innerHTML=xs.data.settings.total;
         }
         view.viewReport(str,view.getExcelData(dataList),'rpchange');
         Vue.prototype.$Spin.hide();  
     },(res)=>{
       Vue.prototype.$Message.warning(!res.message?'查无数据':res.message);
       xs.loadData({});
     },(error)=>{
       Vue.prototype.$Spin.hide();
     })
 }

/**
 * 改变滚动条位置
 */
function changeScrollBottom() {
   // console.log('changeScrollBottom')
    let overlayer = document.getElementsByClassName('x-spreadsheet-overlayer');
    if(overlayer && overlayer.length>0){
        var height = overlayer[0].style.height;
        var heinum = Number(height.split('px')[0]) - 40;
        document.getElementsByClassName('x-spreadsheet-scrollbar vertical')[0].style.bottom = 'calc(100% - '+heinum+'px)';
    }
}

/**
 * 获取请求地址中的参数
 */
function getRequestUrlParam() {
    var url = window.location.href;
    var index = url.indexOf("?");
    var param = {};
    if(index>0){
        var strs = url.substring(index+1);
        var array = strs.split("&");
        for(let a of array){
            var temp = a.split('=');
            param[temp[0]] = decodeURIComponent(temp[1])
        }
    }
    return param;
}

/**
 * 判断数据库的报表参数是否匹配请求地址参数
 */
function dbParamMatchUrlParam(dbList, urlParam) {
    let res = false;
    let array = Object.keys(urlParam);
    if(!array){
        return res;
    }
    for(let item of dbList){
        if(array.indexOf(item.paramName)>=0){
            res = true;
        }
    }
    return res;
}

/**
 * 字典数据处理
 * @param json
 */
function dictDataHandler(json) {
    let dictInfo = view.dictInfo
    if(!dictInfo){
        return;
    }
    if(json){
        let rows = json.rows;
        if(rows){
            //遍历行
            Object.keys(rows).map(rowIndex=>{
                let row = rows[rowIndex].cells;
                if(row){
                    //遍历列
                    Object.keys(row).map(colIndex=>{
                        //获取单元格
                        let cell = row[colIndex]
                        if(cell.isDict===1){
                            let tempText = cell.text;
                            let tempDictList = dictInfo[cell.dictCode];
                            if(tempDictList){
                                console.log('tempText',tempDictList)
                                cell.text = getDictTextByValue(tempDictList, tempText)
                            }
                        }
                    })
                }
            })
        }
    }
}

/**
 * 获取字典 文本
 * @param ls
 * @param Value
 * @returns {string|*}
 */
function getDictTextByValue(ls, value) {
    if(!ls||ls.length==0){
        return value;
    }
    let text = ''
    for(let item of ls){
        if(item.value == value){
            text = item.text;
            break;
        }
    }
    return text;
}

/**
 * 获取数据 所在范围
 * @param rows
 * @returns {{sci: string, eci: string, sri: string, eri: string}}
 */
function getRepeatRange(rows){
    //currentDataRow
    let sri,sci,eri,eci;
    let rowsIndex = Object.keys(rows)
    sri = rowsIndex[0]
    let beginCells = rows[sri].cells
    let beginColArr = Object.keys(beginCells)
    sci = beginColArr[0]

    eri = rowsIndex[rowsIndex.length-1]
    let endCells = rows[eri].cells
    let endColArr = Object.keys(endCells)
    eci = endColArr[endColArr.length-1]
    return {sri:Number(sri), sci:Number(sci), eri:Number(eri), eci:Number(eci)}
}

function getQueryDefaultValue(defaultValue, type) {
    if(defaultValue === 0){
        if(type=='number'){
            defaultValue = 0;
        }else{
            defaultValue = '0';
        }
    } else if(!defaultValue){
        defaultValue = '';
    }
    return defaultValue;
}

function updateSearchForm(dbCode, name, value) {
    let queryInfo = rpViewInst.queryInfo
    let list = rpViewInst.configQueryList
    if(list){
        for(let vo of list){
            if(vo.dbCode==dbCode && vo.name==name){
                handleConfigToQueryInfo(vo, queryInfo, value);
            }
        }
    }
    rpViewInst.queryInfo = {...queryInfo}
}

function handleConfigToQueryInfo(vo, queryInfo, defaultValue) {
    // 数值类型 number，字符类型 string， 日期类型 date，时间类型 datetime
    let type = vo.type;
    // 1输入框单查询  2范围查询 3下拉多选 4下拉框查询
    let mode = vo.mode;
    let key = vo.key;
    if(mode==1){
        // 输入框
        if(type=='date' || type=='time'){
            if(defaultValue){
                queryInfo['onlyshow_'+key] = getDateByStr(defaultValue, vo.format)
            }
        }
        queryInfo[key] = defaultValue;
    }else if(mode == 2){
        // 范围
        let arr = (defaultValue+'').split('\|')
        if(arr){
            if(arr.length>0){
                queryInfo[key+'_begin'] = arr[0].trim();
            }
            if(arr.length>1){
                queryInfo[key+'_end'] = arr[1].trim();
            }
            // 如果是时间的 还需要初始化展示的值 然后添加change事件 改变原值
            if(type=='date' || type=='time'){
                if(arr.length>0 && arr[0]){
                    queryInfo['onlyshow_'+key+'_begin'] = getDateByStr(arr[0], vo.format);
                }
                if(arr.length>1 && arr[1]){
                    queryInfo['onlyshow_'+key+'_end'] = getDateByStr(arr[1], vo.format);
                }
            }
        }
    }else if(mode == 3){
        // 多选 因为组件问题  只能处理数组  onlyshow_前缀的需要通过 添加change事件 改变原值
        let arr = (defaultValue+'').split(',');
        queryInfo['onlyshow_'+key] = arr;
        queryInfo[key] = defaultValue;
    }else if(mode == 4){
        // 4下拉框查询
        queryInfo[key] = defaultValue;
        //select 是否为下拉选中 queryType是否存在默认值 1存在默认值 2不存在默认值
        if(defaultValue){
          vo.select=false
          vo.queryType=1
        }
    }else if(mode == 5){
        // 5 模糊查询
        queryInfo[key] = defaultValue;
        queryInfo['onlyshow_'+key] = defaultValue;
    }else{
        // 6 下拉树
        queryInfo[key] = defaultValue;
        if((type=='date' || type=='time') && defaultValue){
            queryInfo['onlyshow_'+key] = getDateByStr(defaultValue, vo.format)
        }
    }
}

/**
 * 获取查询表单的 key
 */
function getSearchFormKey(vo) {
    let key = '';
    if(vo.paramSearch==true){
        // 参数查询 不需要区分是否重复
        key = vo.name;
    }else{
        // 字段查询
        key = vo.dbCode+'__'+vo.name;
    }
    return key;
}

/**
 * 获取初始化的 查询对象
 */
function getFirstQueryInfo(list) {
    let queryInfo = {};
    if(list){
        for(let vo of list){
            vo['key'] = getSearchFormKey(vo);
            let defaultValue = getQueryDefaultValue(vo.defaultValue, vo.type);
            handleConfigToQueryInfo(vo, queryInfo, defaultValue);
        }
    }
    return {
        list: list,
        queryInfo: queryInfo
    }
}

/**
 * 时间格式的 转成date对象
 * @param str
 * @param format
 * @returns {string|Date}
 */
function getDateByStr(str, format) {
    if(!str){
        return '';
    }
    str = str.trim();
    // 年月日的默认值 可以直接new Date 传字符串获取
    // 时分秒的需要特殊设置
    let date = new Date();
    if("HH:mm" == format){
        let hour = str.substring(0, 2);
        date.setHours(hour);
        let mill = str.substring(3);
        date.setMinutes(mill);
    }else if("HH:mm:ss" == format){
        let hour = str.substring(0, 2);
        date.setHours(hour);
        let mill = str.substring(3, 5);
        date.setMinutes(mill);
        let sec = str.substring(6);
        date.setSeconds(sec);
    }else if("mm:ss" == format){
        let mill = str.substring(0, 2);
        date.setMinutes(mill);
        let sec = str.substring(3);
        date.setSeconds(sec);
    }else if("HH"==format){
        date.setHours(str);
    }else if("mm"==format){
        date.setMinutes(str);
    }else if("ss"==format){
        date.setSeconds(str);
    }else{
        let d = str.replace(/-/g,'/');
        date = new Date(d);
    }
    return date;


}

/**
 * 查询报表查询信息
 * @param token
 */
function handleReportQueryInfo(token) {
    return new Promise(resolve => {
        rpViewInst.configQueryList = []
        rpViewInst.queryInfo = {}
        //获取浏览器中的参数并传递给后台
        let requestParam = getRequestUrlParam();
        $jm.getQueryInfo(configId, requestParam, token, (result)=>{
            console.log('查询配置信息', result)
            let ls = result.list
            if(ls){
                let { list, queryInfo } = getFirstQueryInfo(ls)
                rpViewInst.queryInfo = queryInfo
                rpViewInst.configQueryList = [...list]
            }
            let jsStr = result.jsStr;
            if(jsStr){
                rpViewInst.enhanceFunction = eval("("+jsStr+")");
                rpViewInst.executeEnhanceJs();
            }else{
                rpViewInst.enhanceFunction = ''
            }
            resolve();
        })
    })
}
//处理是范围的情况下
function modeTwo(key,type,obj,defaultValue){
  if(type && type!='String'){
    let value = eval("["+defaultValue+"]");
    //数字的情况
    if(type == "number"){
      let begin = key +"_begin";
      let end = key +"_end";
      obj[begin] = value[0]
      obj[end] = value[1]
    }else{
      //日期的情况
      let begin = key +"_begin";
      let end = key +"_end";
      let jdate__begin = "jdate__"+key +"_begin";
      let jdate__end = "jdate__"+key +"_end";
      obj[begin] = value[0]
      obj[jdate__begin] = value[0]
      obj[end] = value[1]
      obj[jdate__end] = value[1]
    }
  }
}
// 手动获取图表最新的数据 组装json
function handleChartData(dataMap, jsonStr) {
    let excelOptions = JSON.parse(jsonStr);
    if (!excelOptions){
        return jsonStr;
    }
    let chartList = excelOptions.chartList;
    if(chartList && chartList.length>0){
        for(let item of chartList){
            if(!item.extData || !item.extData.dbCode){
                continue;
            }else{
                let dbCode = item.extData.dbCode
                let dataList = dataMap[dbCode]?dataMap[dbCode]['list']:[];
                for (let data of dataList) {
                    for (let key in data) {
                        let lowerkey = key.toLowerCase();
                        data[lowerkey] = data[key];
                    }
                }
                let chartOption = JSON.parse(item.config);
                //图表刷新
                preRefreshChart(item,chartOption,dataList,dataMap)
                item.config = JSON.stringify(chartOption)
            }
        }
    }
    return JSON.stringify(excelOptions);
}

/***
 * 刷新数据
 */
function refreshData(item,dataMap){
    let settings=item.extData;
    let chartOption = JSON.parse(item.config);
    //sql数据集
    if (settings.dataType === 'sql') {
        $jm.qurestSql(settings.dataId, (result)=> {
            //关系图多数据集
            if(item.extData['chartType']==='graph.simple'){
                $jm.qurestSql(item.extData.dataId1, (res)=> {
                    let { source,target }= item.extData;
                    let links = res.map((item,index)=>{
                        return {'source':item[source],'target':item[target]}
                    })
                    refreshChart(rpViewInst, chartOption, item.extData,{data:result,links:links})
                    //只刷新数据，目前有问题。xs.updateChart(item.layer_id ,chartOption);
                    //update-begin---author:王帅   Date:20210412  for：换成新接口刷新图表，注释掉原来旧接口------------  
                    // xs.refreshChartData(item.layer_id , chartOption['series']);
                    xs.updateChart(item.layer_id , chartOption);
                    //update-end---author:王帅   Date:20210412  for：换成新接口刷新图表，注释掉原来旧接口------------
                })
            }else{
                //单数据集问题
                preRefreshChart(item,chartOption,result,dataMap)
                //只刷新数据，目前有问题。xs.updateChart(item.layer_id ,chartOption);
                //update-begin---author:王帅   Date:20210412  for：换成新接口刷新图表，注释掉原来旧接口------------
                // xs.refreshChartData(item.layer_id , chartOption['series']);
                xs.updateChart(item.layer_id , chartOption);
                //update-end---author:王帅   Date:20210412  for：换成新接口刷新图表，注释掉原来旧接口------------
            }
        })
    }else if(settings.dataType === 'api'){
        //api数据集
        Object.assign(settings,{axisX: 'name', axisY: 'value', series: 'type'})
        if(settings.apiStatus == '1'){
            $jm.qurestApi(settings.dataId,function (res) {
                preRefreshChart(item,chartOption,res.data,dataMap)
                //只刷新数据，目前有问题。xs.updateChart(item.layer_id ,chartOption);
                //update-begin---author:王帅   Date:20210412  for：换成新接口刷新图表，注释掉原来旧接口------------
                // xs.updateChart(item.layer_id , chartOption['series']);
                xs.updateChart(item.layer_id , chartOption);
                //update-end---author:王帅   Date:20210412  for：换成新接口刷新图表，注释掉原来旧接口------------
            })
        }
    }
}
/***
 * 图表刷新前处理
 */
function preRefreshChart(item,chartOption,dataList,dataMap){
    if(item.extData['chartType']==='graph.simple'){
        if(item.extData['dataType']==='sql'){
            $jm.qurestSql(item.extData.dataId1, (res)=> {
                let { source,target }= item.extData;
                let links = res.map((item,index)=>{
                    return {'source':item[source],'target':item[target]}
                })
                refreshChart(rpViewInst, chartOption, item.extData,{data:dataList,links:links})
            })
        }else{
            let dbCode = item.extData.dbCode
            refreshChart(rpViewInst, chartOption, item.extData,{data:dataList,links:dataMap[dbCode]?dataMap[dbCode]['linkList']:null})
        }
    }else{
      if(item.extData.dataType!="api" && item.extData.apiStatus!='0'){
        refreshChart(rpViewInst, chartOption, item.extData, dataList)
      }
      if(item.extData.dataType=="api" && item.extData.apiStatus=='1'){
        refreshChart(rpViewInst, chartOption, item.extData, dataList)
      }
    }
}
/**
 * 将查询条件放到requestParam
 * @param queryInfo
 * @param requestParam
 */
function query2RequestParam(queryInfo, requestParam) {
    Object.keys(queryInfo).map(key=>{
        if(queryInfo[key] && !key.startsWith('onlyshow_')){
            requestParam[key] = queryInfo[key]
        }
        //update-begin-author:taoyan date:20210728 for 参数作为查询条件 多选下拉报错
/*        if(key.startsWith('onlyshow_')){
            let newKey = key.replace('onlyshow_', '')
            if(!queryInfo[newKey]){
                let arr = queryInfo[key]
                if(arr instanceof Array){
                    requestParam[newKey] = arr.join(',')
                }
            }
        }*/
        //update-end-author:taoyan date:20210728 for 参数作为查询条件 多选下拉报错
    })
    // 设置查询的分页条数
    requestParam.pageSize = view.pageSize
    //requestParam['X-Access-Token'] = view.token
}

/**
 * 处理数据更新图表
 * @param list
 */
function refreshChart(vm, chartOptions, dataSettings, dataList){
    let seriesConfig = chartOptions['series']
    let chartType = dataSettings['chartType']
    if(!chartType){
        vm.$Message.warning('老数据不兼容，请删除该图表重新添加即可!');
    }
    if( chartType === 'bar.simple' || (chartType.indexOf('line.') !== -1 && chartType!=='line.multi')){
        let { axisX, axisY } = dataSettings
        let xarray = []
        let yarray = []
        for(let item of dataList){
            xarray.push(item[axisX])
            yarray.push(item[axisY])
        }
        chartOptions['xAxis']['data'] = xarray
        seriesConfig[0].data = yarray
    }
    if(chartType === 'scatter.simple'){
        let { axisX, axisY } = dataSettings
        let yarray = []
        for(let item of dataList){
            yarray.push([item[axisX],item[axisY]])
        }
        seriesConfig[0].data = yarray
    }
    if(chartType === 'scatter.bubble'){
        let { axisX, axisY, series } = dataSettings
        let seriesMap = {}
        for(let item of dataList){
            //获取series数据
            seriesMap[item[series]] = 1
        }
        let realSeries = []
        let legendData = Object.keys(seriesMap)
        let singleSeries = seriesConfig[0]
        for(let i=0;i<legendData.length;i++){
            let name = legendData[i]
            let seriesData = []
            let temparr = dataList.filter(item=>{
                return item[series] == name
            })
            temparr.forEach(function(e){
                seriesData.push([e[axisX],e[axisY]])
            })
            let itemstyle = getSeriesItemStyle(seriesConfig, i, name)
            let obj = Object.assign({},
                singleSeries,
                itemstyle,{
                    name: name,
                    data: seriesData,
                })
            realSeries.push(obj)
        }

        chartOptions['legend']['data'] = legendData
        chartOptions['series'] = realSeries
    }
    if(chartType.indexOf('pie')!=-1|| chartType === 'funnel.simple' ){
        let { axisX, axisY } = dataSettings
        let ls = [], xarray = []
        let i = 0;
        for(let item of dataList){
            let tempName = item[axisX]
            //update-begin--Author:wangshuai -- Date:20210402 -- for：data可能为空需要判断一下---
            let itemStyle = {color:""}
            if(seriesConfig[0].data[i]){
                itemStyle =  seriesConfig[0].data[i]['itemStyle']
            }
            //update-end--Author:wangshuai -- Date:20210402 -- for：data可能为空需要判断一下---
            // getSeriesDataItemStyle(seriesConfig[0].data, tempName)
            ls.push({
                name: tempName,
                value: item[axisY],
                itemStyle: itemStyle
            })
            xarray.push(item[axisX])
            i++;
        }

        chartOptions['legend']['data'] = xarray
        seriesConfig[0].data = ls
    }

    if( chartType === 'pictorial.spirits'){
        let { axisX, axisY } = dataSettings
        let xarray = []
        let yarray = []
        for(let item of dataList){
            xarray.push(item[axisX])
            yarray.push(item[axisY])
        }
        chartOptions['yAxis']['data'] = xarray
        for(let item of seriesConfig){
            item['data'] = yarray
        }
    }

    if(chartType === 'gauge.simple'){
        let { axisX, axisY } = dataSettings
        let array = []
        for(let item of dataList){
            array.push({
                name: item[axisX],
                value: item[axisY],
            })
        }
        seriesConfig[0].data = array
    }


    if( chartType.indexOf('bar.multi')!==-1 || chartType === 'line.multi'|| chartType.indexOf('bar.stack') !== -1 ){
        let { axisX, axisY, series } = dataSettings
        let xarray = []
        let seriesMap = {}
        for(let item of dataList){
            let tempX = item[axisX]
            //获取x轴数据
            if(xarray.indexOf(tempX)<0){
                xarray.push(tempX)
            }
            //获取series数据
            seriesMap[item[series]] = 1
        }
        let realSeries = []
        let singleSeries = seriesConfig[0]
        let legendData = Object.keys(seriesMap)
        for(let i=0;i<legendData.length;i++){
            let name = legendData[i]
            let seriesData = []
            for(let x of xarray){
                let temparr = dataList.filter(item=>{
                    return item[axisX] == x && item[series] == name
                })
                if(temparr && temparr.length>0){
                    seriesData.push(temparr[0][axisY])
                }else{
                    seriesData.push(0)
                }
            }
            let itemstyle = getSeriesItemStyle(seriesConfig, i, name)
            let obj = Object.assign({},
                singleSeries,
                itemstyle,{
                    name: name,
                    data: seriesData
                })
            //处理堆叠情况
            if(chartType === 'bar.stack'){
                let tempStack=chartOptions['series'][0]['typeData'].filter(item=>{ return item.name===name });
                if(tempStack[0] && tempStack[0].stack){
                    obj['stack']=tempStack[0].stack
                }else{
                    obj['stack']=''
                }
            }
            realSeries.push(obj)
        }
        if(chartType==='bar.stack.horizontal'||chartType==='bar.multi.horizontal'){
            chartOptions['yAxis']['data'] = xarray
        }else{
            chartOptions['xAxis']['data'] = xarray
        }
        chartOptions['legend']['data'] = legendData
        chartOptions['series'] = realSeries
    }
    if(chartType === 'mixed.linebar'){
        let { axisX, axisY, series } = dataSettings
        let xarray = []
        let seriesMap = {}
        for(let item of dataList){
            let tempX = item[axisX]
            //获取x轴数据
            if(xarray.indexOf(tempX)<0){
                xarray.push(tempX)
            }
            //获取series数据
            seriesMap[item[series]] = 1
        }
        let realSeries = []
        let legendData = Object.keys(seriesMap)
        legendData.map(name=>{
            let seriesData = []
            for(let x of xarray){
                let temparr = dataList.filter(item=>{
                    return item[axisX] == x && item[series] == name
                })
                if(temparr && temparr.length>0){
                    seriesData.push(temparr[0][axisY])
                }else{
                    seriesData.push(0)
                }
            }
            let singleSeries=chartOptions['series'].filter(item=>{
                return  item['name'] == name
            })
            let obj = Object.assign({},
                singleSeries[0],{
                    name: name,
                    data: seriesData
                })
            realSeries.push(obj)
        })

        chartOptions['xAxis']['data'] = xarray
        chartOptions['legend']['data'] = legendData
        chartOptions['series'] = realSeries
    }
    if( chartType === 'map.scatter'){
        let { axisX, axisY } = dataSettings
        let ls = [];
        for(let item of dataList){
            let v=[];
            if(dataSettings.mapData){
                let data=dataSettings.mapData;
                let mapFeature = data.features.filter(district=> district.properties.name.indexOf(item[axisX])!=-1);
                if(mapFeature && mapFeature.length>0){
                    let coordinate=mapFeature[0].properties.center;
                    let lng=coordinate[0]?coordinate[0]:coordinate.lng;
                    let lat=coordinate[1]?coordinate[1]:coordinate.lat;
                    v=[lng,lat,item[axisY]];
                    ls.push({
                        name:item[axisX],
                        value: v
                    })
                }
            }
        }
        if(ls&&ls.length>0)
        seriesConfig[0].data = ls
    }
    //雷达图
     if( chartType === 'radar.basic'||chartType==='radar.custom'){
        handleRadarChart(dataSettings,dataList,chartOptions)
    }
    console.info("---chartOptions---",JSON.stringify(chartOptions))
    //关系图
    if( chartType === 'graph.simple'){
        let { axisX, axisY, series } = dataSettings;
        let seriesConfig = chartOptions['series']
        let data = dataList.data;
        let links = dataList.links;
        let seriesMap = {};
        let categories=[];
        for(let i=0,len=data.length;i<len;i++){
            //系列已存在
            if(seriesMap[data[i][series]]){
                data[i].category = seriesMap[data[i][series]];
                continue;
            }else{
                //获取series数据
                seriesMap[data[i][series]] = i;
                //获取categories数据
                let singleSeries=seriesConfig[0].categories.filter(item=>item.name === data[i][series]);
                let itemStyle=singleSeries&&singleSeries.length>0?singleSeries[0]['itemStyle']:{"color": ""};
                categories.push({name:data[i][series],itemStyle:itemStyle});
                //获取data.categories为坐标
                data[i].category = i;
            }
        }

        let legendData = Object.keys(seriesMap);
        seriesConfig[0].data=data.map(item=>{return { name:item[axisX],value:item[axisY],category:item.category }});
        seriesConfig[0].links=links;
        seriesConfig[0].categories=categories;
        chartOptions['legend']['data'] = legendData
        chartOptions['series'] = seriesConfig
    }
}
//处理雷达图数据
function handleRadarChart(dataSettings,dataList,chartOptions){
    let { axisX, axisY, series } = dataSettings
    let array = []   //分类数组
    let seriesMap = {}
    let seriesConfig = chartOptions['series']
    for(let item of dataList){
        let temp = item[axisX]
        //获取x轴数据
        if(array.indexOf(temp)<0){
            array.push(temp)
        }
        //获取系列
        seriesMap[item[series]] = 1
    }
    let realSeries = []
    let realData= []
    let legendData = Object.keys(seriesMap) //新的系列data
    let indicatorData=[];
    //雷达数据
    for(let i=0;i<legendData.length;i++){
        let name = legendData[i] //系列值
        let seriesData = []
        for(let x of array){
            //获取当前的雷达图的名称，附初始最大值0
            let indicator={name:x,max:0}
            let temparr = dataList.filter(item=>{
                return item[axisX] == x && item[series] == name
            })
            if(temparr && temparr.length>0){
                seriesData.push(temparr[0][axisY])
                indicator['max']=temparr[0][axisY];
            }else{
                seriesData.push(0)
            }
            //update-begin-author:wangshuai date:20210526 for：预览页面雷达图取最大值
            //判断取出设置数据的最大值
            let tempIndicator =indicatorData.filter(item=>{return item['name'] == x})
            if(tempIndicator && tempIndicator.length>0){
              //存在就判断大小并赋值
              if(tempIndicator[0]['max'] < indicator['max']){
                tempIndicator[0]['max'] = indicator['max'];
              }
            }else{
              //不存在就直接push
              indicatorData.push(indicator)
            }
        }
        //最大值的配置获取
        let indicators=chartOptions['radar'][0]['indicator']
        //修改最大值的大小
        indicatorData.forEach(item=>{
          let ogn=indicators.filter(indicator=>indicator.name===item.name)
          item.max = ogn&&ogn.length>0?ogn[0].max:calcuMaxVal(item.max);
        })
        //如果不存在最大值，则赋值
        chartOptions['radar'][0]['indicator'] = indicatorData
        //update-end-author:wangshuai date:20210526 for：预览页面雷达图取最大值
        let singleSeries=seriesConfig[0].data.filter(item=>item.name === name);
        singleSeries=singleSeries.length>0?singleSeries[0]:{}
        let obj = Object.assign({},singleSeries,{
            name: name,
            value: seriesData
        })
        realData.push(obj)
    }

    let obj = Object.assign({},{
        type: 'radar',
        data: realData
    });
    realSeries.push(obj)
    chartOptions['legend']['data'] = legendData
    chartOptions['series'] = realSeries
}

//计算雷达图边界数据
function calcuMaxVal(val){
    let first=val.toString().substr(0,1);
    first=parseInt(first)+1;
    val=first + val.toString().substr(1);
    return parseInt(val);
}

/**
 * 获取当前Series index对应的itemstyle 适用于多柱体多折线
 * @param seriesConfig
 * @param index
 * @param name
 * @returns {{itemStyle: any}}
 */
function getSeriesItemStyle(seriesConfig, index, name) {
    let itemStyle = JSON.parse(JSON.stringify(seriesConfig[0]['itemStyle']))
    itemStyle['color'] = ''

    if(seriesConfig[index] && seriesConfig[index].name == name){
        itemStyle['color'] = seriesConfig[index]['itemStyle']['color']
    }
    return {itemStyle}
}

/**
 * 获取当前Series data中的itemstyle 适用于饼状图 漏斗图
 * @param seriesData
 * @param name
 * @returns {{color: string}}
 */
function getSeriesDataItemStyle(seriesData, name) {
    let itemStyle = {color:''}
    for(let item of seriesData){
        if(name === item.name){
            itemStyle = item['itemStyle']
            break;
        }
    }
    return itemStyle;
}

/**
 * 是否为分享的链接，并且秘钥是否正确
 * 0:说明可以直接预览
 * 1:说明分享链接已过期
 * 2:说明提取码错误或缓存提取码不存在，需要用户输入
 * 3:说明分享链接不存在
 */
async function getShareViewUrl(excelConfigId,callback) {
    //是否为分享的链接
    if(shareView==='1'){
        //在请求数据之间先请求后台查看是否存在密码情况
        let config = localStorage.getItem(excelConfigId);
        let param={};
        param.reportId=excelConfigId;
        if(config){
            param.lock=config;
        }
        await $http.get({
            url:api.verification,
            data:param,
            success:(result)=>{
                callback(result);
            }
        })
    }else{
        callback("0");
    }
}

/**
 * 处理图表联动 参数
 * @param linkData jm_rp_link表数据
 * @param param 点击图表回调参数
 */
function handleLinkageChartParam(linkData, param, type) {
    if(linkData.parameter){
        let arr = JSON.parse(linkData.parameter);
        if(arr.length>0){
            let params = []
            if(type=='1'){
                //  图表 -- 图表
                for(let p of arr){
                    if(p.paramValue && param[p.paramValue]){
                        let obj = {}
                        obj[p.paramName] = param[p.paramValue]
                        params.push(obj)
                    }
                }
            }else{
                // 单元格 -- 图表
                params = rpViewInst.getLinkageArgument(linkData, param)
            }
            console.log("params",params)
            let { reportId, linkChartId } = linkData
            $http.get({
                url: api.getCharData,
                data: {params: JSON.stringify(params), charId: linkChartId, reportId: reportId},
                success: (result) => {
                    let chartOptions = JSON.parse(result.config)
                    let dataList = result.dataList
                    if(dataList){
                        dataList=JSON.parse(dataList)
                        refreshChart(rpViewInst, chartOptions, result.extData, dataList)
                        xs.updateChart(linkChartId ,chartOptions);
                    }else{
                        Vue.prototype.$Message.warning("数据为空")
                        xs.updateChart(linkChartId ,chartOptions);
                    }
                }
            })
        }
    }else{
      Vue.prototype.$Message.warning("联动参数未配置!")
    }
}

/**
 *
 * @param result jm_link表的数据
 * @param param 点击图表回调参数
 * @param type 1图表联动图表 2单元格联动图表
 */
function jimuReportHref(result,param,type) {
    for (const resultElement of result) {
    let parameter = JSON.parse(resultElement.parameter);
    let protocol = window.location.protocol;
    let host = window.location.host;
    let reportId = resultElement.reportId;
    let url = protocol + "//" + host + api.view + reportId;
    let linkType = resultElement.linkType;
    let text = "";
    if(type === '1'){
        text = param.value;
    }else{
        text = param.text;
    }
    let ri = param.ri;
    if (linkType == '0') {
        if (parameter.length > 0) {
          let linkageArgument = rpViewInst.getLinkageArgument(resultElement, param);
          url = getUrl(url, linkageArgument);
        }
        let ejectType = resultElement.ejectType;
        let type = "";
        let previousPage = false
        if (ejectType == '0') {
            type = "_blank";
        } else {
            type = "_self";
          //当时钻取并且为当前窗口的时候才会显示返回上一页
          previousPage = true
        }
        //拼接是否显示上一页
        if (url.indexOf("?") != -1) {
          url = url+"&"+"previousPage="+previousPage
        }else{
          url = url+"?"+"previousPage="+previousPage
        }
        window.open(url, type)
    }
    if (linkType == '1') {
      let apiUrl = resultElement.apiUrl;
      if (parameter.length > 0) {
        let linkageArgument = rpViewInst.getLinkageArgument(resultElement, param);
        apiUrl = getUrl(apiUrl, linkageArgument);
      }
      let ejectType = resultElement.ejectType;
      let type = "";
      if (ejectType == '0') {
        type = "_blank";
      } else {
        type = "_self";
      }
      window.open(apiUrl, type)
    }
    if (linkType == '2') {
        handleLinkageChartParam(resultElement, param, type)
    }
  }
}

//获取完整的带参数的url
function getUrl(url,parameter){
  for (const params of parameter) {
    for (const i in params) {
      let paramValue = params[i];
      if (url.indexOf("?") == -1) {
        url = url + "?" + i + "=" + paramValue
      } else {
        url = url + "&" + i + "=" + paramValue
      }
    }
  }
  return url;
}

//返回上一页
function returnPreviousPageClick(){
  window.history.back();
}

//处理钻取和网络
function jimuReportHyperlinks(result,param,type){
  //获取text
  let params = {}
  let resultExpression = []
  for (const resultElement of result) {
    //获取text
    let linkType = resultElement.linkType
    let initExpression = resultElement.requirement
    if(linkType!=2){
      if(initExpression){
        params = getTextByDbCode(resultElement,param,type);
        let expression = params.expression;
        let text = params.text;
        //替换并判断expression是否符合规则
        var reg = /^[0-9]+.?[0-9]*$/;
        if (reg.test(text)) {
         text = Number(text)
        }else{
          if(text != "''"){
            text = "'" + text + "'"
          }
        }
        initExpression = initExpression.replace(expression,text)
        let b = eval(initExpression)
        if(b){
          resultExpression.push(resultElement)
          return  resultExpression;
        }
      }
    }else{
      return  result;
    }
  }
  if(result.length>0){
     let expression = []
     expression.push(result[0])
     return expression;
  }
}

//处理联动
function jimuReportLinkage(result,param,type){
  //获取text
  let params = {}
  let resultExpression = []
  for (const resultElement of result) {
    //获取text
    let linkType = resultElement.linkType
    let initExpression = resultElement.requirement
    if(linkType==2){
      if(initExpression){
        params = getTextByDbCode(resultElement,param,type);
        let expression = params.expression;
        let text = params.text;
        //验证是否位数字,不是数字直接加上单引号
        var reg = /^[0-9]+.?[0-9]*$/;
        if (reg.test(text)) {
          text = Number(text)
        }else{
          if(text != "''"){
            text = "'" + text + "'"
          }
        }
        //替换并判断expression是否符合规则
        initExpression = initExpression.replace(expression,text)
        let b = eval(initExpression)
        //表达式通过
        if(b){
          resultExpression.push(resultElement)
        }
      }else{
        //不存在表达式
        resultExpression.push(resultElement)
      }
    }else{
      return  result;
    }
  }
  return  resultExpression;
}

//截取表达式
function subStrExpression(expression){
  // let isExceprion = false
  if(expression.indexOf("==")!=-1){
    expression = expression.substr(0,expression.indexOf("=="))
  }else if(expression.indexOf("!=")!=-1){
    expression = expression.substr(0,expression.indexOf("!="))
  }else if(expression.indexOf(">=")!=-1){
    expression = expression.substr(0,expression.indexOf(">="))
  }else if(expression.indexOf("<=")!=-1){
    expression = expression.substr(0,expression.indexOf("<="))
  }else if(expression.indexOf("<")!=-1){
    expression = expression.substr(0,expression.indexOf("<"))
  }else if(expression.indexOf(">")!=-1){
    expression = expression.substr(0,expression.indexOf(">"))
  }else{
    expression = ""
  }
  return  expression.trim();
}

function getTextByDbCode(linkData, param,type) {
  let text = param.text
  if(!text){
    text = "''"
  }
  let expression = linkData.requirement;
  let subStr = subStrExpression(expression,1);
  let params = {}
  //说明是图表则只包含 name type value
  if(type == '1'){
    if(subStr == 'name'){
      params.expression = subStr
      params.text =param.name
    }else if(subStr == 'value'){
      params.expression = subStr
      params.text =param.value
    }else if(subStr == 'type'){
      params.expression = subStr
      params.text =param.type
    }else{
      params.expression = expression
      params.text =text
    }
    return params;
  }else{
    if (linkData.parameter) {
      let arr = JSON.parse(linkData.parameter)
      if (arr.length > 0 && subStr) {
        let p = arr[0];
        let dbCode = p.dbCode
        let fieldName = p.fieldName
        let list = rpViewInst.queryResultData[dbCode]
        for(let i=0;i<list.length;i++){
          //判断是否存在集合中是否存在对应表达式的name
          if(list[i][fieldName] == text){
            params.expression = subStr
            params.text = list[i][subStr]
            return params;
          }
        }
      }
    }
    if(subStr == 'text'){
      params.expression="text"
      params.text=text
    }
  }
  params.expression=expression
  params.text=text
  return params
}


/**
 * 增强css添加到 head
 */
function addCssToHead(str) {
    if(str){
        let style = document.createElement('style');
        style.type = 'text/css';
        style.rel = 'stylesheet';
        try {
            style.appendChild(document.createTextNode(str))
        }catch (e) {
            console.log('您的浏览器不支持,是不是IE?')
            style.styleSheet.cssText = str;
        }
        let head = document.getElementsByTagName('head')[0]
        head.appendChild(style);
    }
}