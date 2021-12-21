(function($){
    $.extend({
        excelSave:function(data,token,callback,url){
            if(!url){
                url=`${baseFull}/jmreport/save`;
            }
            const saveFn=(obj)=>{
                //判断报表是否有分组字段
                const rows = data.rows;
                let groupField="";
                for(let key in rows){
                    if(groupField) break;
                    const cells = rows[key].cells;
                    for(let cellKey in cells){
                        const text = cells[cellKey].text;
                        if(!text) continue;
                        if(text.includes(".group(")){
                            groupField = text.replace("group(","")
                                             .replace(")}","")
                                             .replace("#{","")
                            break;

                        }
                    }
                }
                if(groupField){
                    data.isGroup=true;
                    data.groupField=groupField;
                }
                if (data.chartList && data.chartList.length > 0){
                    data.chartList.forEach(item=>{
                        if (item.width === 0 || item.height === 0){
                            item.width = "650";
                            item.height = "350";
                        }
                    })
                }
                var dataStr = JSON.stringify({designerObj:obj,...data});
                if ( obj.name != null && obj.name != "" ){
                    $.ajax({
                        type : "POST",
                        contentType: "application/json;charset=UTF-8",
                        url : url,
                        headers:{"X-Access-Token":token},
                        data : dataStr,
                        //请求成功
                        success : function(result) {
                            onbeforeunload="return true";
                            //window.location.reload();
                            callback(result);
                        },
                        //请求失败，包含具体的错误信息
                        error : function(e){
                            console.log(e.status);
                            console.log(e.responseText);
                        }
                    });
                }
            }
            vm.handleCreate(saveFn,data);
        },
        excelGet:function(code,token,callback,url){
            if (code != null && code != ""){
                if(!url){
                    url=`${baseFull}/jmreport/get/${code}`;
                }
                $.ajax({
                    type : "GET",
                    url : url,
                    headers:{"X-Access-Token":token},
                    //请求成功
                    success : function(result) {
                        if (result.result != "" && result.result != null){
                            callback(result);
                        }
                    },
                    //请求失败，包含具体的错误信息
                    error : function(e){
                        console.log(e.status);
                        console.log(e.responseText);
                    }
                });
            }
        },
        excelView:function(id,requestParam, callback, api = '', url){
            if(!url){
                url=`${view.origin}${baseFull}/jmreport/show`;
            }
            $.ajax({
                type : "GET",
                url : url,
                header:"Content-Type: application/json",
                data:{"id":id,"apiUrl":api,"params":JSON.stringify(requestParam)},
                //请求成功
                success : function(result) {
                    callback(result);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    console.log(e.status);
                    console.log(e.responseText);
                }
            });
        },
        addViewCount:function (id) {
            var url=`${view.origin}${baseFull}/jmreport/addViewCount/${id}`;
            $.ajax({
                type : "POST",
                url : url,
                header:"Content-Type: application/json",
                //请求成功
                success : function() {
                    console.log('浏览新增1');
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    console.log(e.status);
                    console.log(e.responseText);
                }
            });
        },
        checkParam: function (id, callback) {
            var url=`${view.origin}${baseFull}/jmreport/checkParam/${id}`;
            $.ajax({
                type : "GET",
                url : url,
                header:"Content-Type: application/json",
                //请求成功
                success : function(result) {
                    callback(result);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    console.log(e.status);
                    console.log(e.responseText);
                }
            });
        },
        qurestSql:function (id,token,callback) {
            let url = `${baseFull}/jmreport/qurestSql`;
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                headers: {"X-Access-Token": token},
                data: {"apiSelectId": id},
                success: (result) => {
                    callback(result);
                }
            });
        },
        qurestApi:function(id,token,callback){
            let url = `${baseFull}/jmreport/qurestApi`;
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                headers: {"X-Access-Token": token},
                data: {"apiSelectId": id},
                success: (result) => {
                    callback(result);
                }
            });
        },
        qureyByApiUrl:function(url,callback){
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                success: (result) => {
                    callback(result);
                }
            });
        },
        //校验唯一性
        dataCodeExist: function (id, code, token, callback) {
            let url = `${baseFull}/jmreport/dataCodeExist`;
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                headers: {"X-Access-Token": token},
                data: {"reportId": id, "code": code},
                success: (result) => {
                    callback(result);
                }
            });
        },
        //获取查询信息
        getQueryInfo:function (id, token, callback) {
            let url = `${baseFull}/jmreport/getQueryInfo`;
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                headers: {"X-Access-Token": token},
                data: {"reportId": id},
                success: (result) => {
                    callback(result);
                }
            });
        }


    });
})(jQuery);

function dataURLtoFile (dataurl, filename) {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
}
