/**
 * 表达式处理类 将页面配置的表达式 转化成实际的excel需要的表达式
 */
class RowExpress {

    /**
     * 构造器
     * @param moveRow  移动行数
     * @param beginIndex  开始行索引
     */
    constructor(moveRow, beginIndex) {
        this.moveRow = Number(moveRow);
        this.beginRowNum = beginIndex+1
        this.expressions = ['SUM','AVG','AVERAGE','MAX','MIN']
        //只处理一个单元格的表达式 如=SUM(A1)  不能处理=SUM(A1,A2)
        //^=(SUM)\(([A-Z]{1})([0-9]+)\)$ 这个匹配之后会获取到一个数组 3个元素 后2个分别是字母、 数字
        this.regex = '^=${expression}\\(([A-Z]{1})([0-9]+)\\)$'
        this.mixedRegex = '^=${expression}\\(([A-Z]{1})([0-9]+)([,:]){1}([A-Z]{1})([0-9]+)\\)$'
    }

    /**
     * 获取实际的文本
     */
    getRealText(text){
        if(!text || (text+'').trim().length==0){
            return text;
        }
        text = text+''
        let word = '',num='',exp=''
        for(let e of this.expressions){
            let expString = this.regex.replace("${expression}", e)
            let temp = new RegExp(expString);
            let arr = text.match(temp)
            if(arr && arr.length>0){
                word = arr[1]
                num = Number(arr[2])
                exp = e
                break;
            }
        }
        if(exp==''){
            return text
        }
        let end = word+(num+this.moveRow)
        if(this.beginRowNum==num){
            let begin = word+num
            let realText = '='+exp+'('+begin+':'+end+')'
            return realText
        }else{
            let realText = '='+exp+'('+end+')'
            return realText
        }
    }

    //计算表达式 包含多个单元格的需要重新计算单元格的位置
    getMixedExpressContent(text, moveRow){
        if(!text || (text+'').trim().length==0){
            return text;
        }
        text = text+''
        let startWord,startNum,endWord,endNum,exp='',linkChar = '';
        for(let e of this.expressions){
            let expString = this.mixedRegex.replace("${expression}", e)
            /*let text = '=SUM(A1,A2)';
            let temp = new RegExp("^=SUM\\(([A-Z]{1})([0-9]+)([,:]{1})([A-Z]{1})([0-9]+)\\)$");
            let arr = text.match(temp)
            console.log(arr)*/
            let temp = new RegExp(expString);
            let arr = text.match(temp)
            if(arr && arr.length>0){
                startWord = arr[1]
                startNum = Number(arr[2])
                linkChar = arr[3]
                endWord = arr[4]
                endNum = Number(arr[5])
                exp = e
                break;
            }
        }
        if(exp==''){
            return text
        }
        startNum +=moveRow
        endNum +=moveRow
        let content = '='+exp+'('+startWord+startNum+linkChar+endWord+endNum+')'
        console.log(content)
        return content;
    }
}