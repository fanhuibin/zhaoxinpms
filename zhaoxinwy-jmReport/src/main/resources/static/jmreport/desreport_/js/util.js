const util = {

    /**
     * 实际配置转图表option 单个
     * @param setting
     * @returns {{}}
     */
    setting2Option(setting){
        let option = {}
        Object.keys(setting).map(key => {
            let str = setting[key]
            if (str && typeof str == 'object' && str.constructor == Array){
                option[key] = [...str]
            } else if (key.indexOf('_') > 0){
                let arr = key.split('_')
                this.handleMultiUnderline(str, option, arr, 0)
            } else {
                option[key] = str
            }
        })
        return option;
    },
    //处理多段下划线属性
    handleMultiUnderline(value, obj, arr, index){
        let key = arr[index]
        if (index == arr.length - 1){
            obj[key] = value;
            return;
        }
        if (!obj[key]){
            obj[key] = {}
        }
        this.handleMultiUnderline(value, obj[key], arr, ++index)
    },

//百分数格式化
    percentFormat(val){
        return val + '%';
    },
    /**
     * 实际配置转图表array 多个
     * @param setting
     * @returns []
     */
    setting2Array(setting){
        let array = [];
        setting.forEach(item => {
            let option = this.setting2Option(item);
            array.push(option)
        })
        return array;
    },
    //通过百分数转化 获取实际数字
    getNumberFromPercent(p, suffix = ''){
        if (!p){
            return 0;
        }
        return Number((p + '').replace('%', '').replace(suffix, ''))
    },

    //将数据列表按某个字段进行分组
    arrayGroupBy(list, field){
        let sorted = this.groupBy(list, function (item) {
            return [item[field]];
        });
        return sorted;
    },
    groupBy(array, f) {
        let groups = {};
        array.forEach(function (o) {
            let group = JSON.stringify(f(o));
            groups[group] = groups[group] || [];
            groups[group].push(o);
        });
        return Object.keys(groups).map(function (group) {
            return groups[group];
        });
    }
};