/**
 * 处理预览时 数据需要循环 循环行之后的数据行 按列依次往后移动
 */
class RowCycleTrans {
    /**
     *
     * @param excelJsonData excel json数据
     * @param begin 开始行索引  从0开始
     * @param end 结束行号
     * @param cycleCells 循环的列索引数组 从0开始
     * @param cycleData 循环的数据 已经处理成row格式的
     */
    constructor(excelJsonData, begin, end, cycleCells, cycleData) {
        this.excelJsonData = excelJsonData;
        this.begin = Number(begin);
        this.end = Number(end);
        this.moveRow = this.end - this.begin
        this.rowExpress = new RowExpress(this.moveRow, this.begin);
        this.handleCycleDataExpress(cycleData)
        this.cycleData = cycleData;
        this.cycleCells = cycleCells;
        this.cellList = [...this.transRows2Array(excelJsonData['rows'])]
        this.moveList = []
        this.len = 100

    }

    /**
     * 处理带表达式的
     */
    handleCycleDataExpress(rows){
        //rows:{2:{cells:{2: {text: "顺序1", style: 2}}}}
        let moveNum = 0
        let rowNumList = Object.keys(rows)
        for(let x of rowNumList){
            if(rows[x]){
                let cells = rows[x].cells
                if(!cells || '{}'==JSON.stringify(cells)){
                    continue
                }
                Object.keys(cells).map(y=>{
                    let cellData = cells[y]
                    if(cellData && cellData.text){
                        cellData.text = this.rowExpress.getMixedExpressContent(cellData.text, moveNum)
                    }
                })
            }
            moveNum++;
        }
    }

    getTransRows(){
        this.handleMoveList();
        this.handleUnionData();
        let rows = this.transArray2Rows();
        this.excelJsonData['rows'] = rows;
        rows['len']=Number(this.len)+5+this.handleLayerHeight()
        //this.handleLayerRange();
        // virtualCellRange  imgList  chartList
    }

    // 处理图层高度
    handleLayerHeight(){
        let h = this.getLayerMaxHeight(0,'imgList')
        h = this.getLayerMaxHeight(h,'chartList')
        return parseInt(h/25)
    }

    //获取图层最大高度
    getLayerMaxHeight(h, arg){
        let maxHeight = h
        if(this.excelJsonData[arg] && this.excelJsonData[arg].length>0){
            for(let item of this.excelJsonData[arg]){
                let temp = Number(item['height'])
                if(temp>maxHeight){
                    maxHeight = temp
                }
            }
        }
        return maxHeight
    }


    /**
     * 将 rows 转成数组便于处理
     * @param rows
     */
    transRows2Array(rows){
        //rows:{2:{cells:{2: {text: "顺序1", style: 2}}}}
        //console.log('transRows2Array',rows)
        let arr = []
        Object.keys(rows).map(x=>{
            if(rows[x]){
                let cells = rows[x].cells
                let otherProp = {}
                if(rows[x].isDrag == true){
                    otherProp['isDrag'] = true
                }
                if(rows[x].cycle == 1){
                    otherProp['cycle'] = 1
                }
                if(rows[x].height){
                    otherProp['height'] = rows[x].height

                    //如果没有设置任何信息 但是有高度 需要设置一下空白行
                    if(!cells || '{}'==JSON.stringify(cells)){
                        cells = {}
                        cells['0'] = {text:' '}
                    }
                }

                if(cells){
                    Object.keys(cells).map(y=>{
                        let cellData = cells[y]
                        //如果没有设置任何信息 但是有高度 需要设置一下空白行
                        if(otherProp['height'] && !cellData['text']){
                            cellData['text'] = ' '
                        }
                       /* if(cellData && cellData['virtual']==1){
                            cellData['style'] = undefined
                        }*/
                        //console.log('1row:'+x+",1col"+y)
                        arr.push({
                            r: Number(x),
                            c: Number(y),
                            d: cellData,
                            otherProp: otherProp
                        })
                    })
                }

            }
        })
       return arr
    }

    transArray2Rows(){
        //rows:{2:{cells:{2: {text: "顺序1", style: 2}}}}
        let rows = {}
        let len = 0
        for(let i=0;i<this.cellList.length;i++){
            let cell = this.cellList[i]
            let row = cell['r']
            if(Number(row)>len){
                len = row
            }
            let col = cell['c']
            if(!rows[row]){
                rows[row] = {cells:{}}
            }
            rows[row]['cells'][col] = cell['d']
            if(cell['cycle'] == 1){
                rows[row]['cycle'] = 1
            }
            if(cell['otherProp']){
                if(cell['otherProp']['isDrag'] == true){
                    rows[row]['isDrag'] = true
                }
                if(cell['otherProp']['cycle'] == 1){
                    rows[row]['cycle'] = 1
                }
                if(cell['otherProp']['height']){
                    rows[row]['height'] = cell['otherProp']['height']
                }
            }
        }
        this.len = len
        return rows;
    }

    /**
     * 处理数据后移
     */
    handleMoveList(){
        //console.log('this.cellList', [...this.cellList])

        for(let i=0;i<this.cellList.length;i++){
            let cell = this.cellList[i]
            let row = cell['r']
            let col = cell['c']
           // console.log('row:'+row+",col"+col)
            if(this.isChangeCell(row, col, false)){
                let newRow = row + this.moveRow
                let text = cell['d']['text']
                text = this.rowExpress.getRealText(text)
                this.moveList.push({
                    r: newRow,
                    c: col,
                    d: Object.assign({},cell['d'],{text}),
                    otherProp: cell.otherProp||''
                })
                cell['d']['delete'] = 1
              /*  if(cell['d'].virtual){
                    cell['d']['delete'] = 1
                   //this.cellList.splice(i,1)
                }*/
            }
        }
        let arr = this.cellList.filter(item=>{
            return item['d']['delete']!=1
        })
        this.cellList = [...arr]
    }

    /**
     * 合并 取并集 以moveList为准
     */
    handleUnionData(){
        let arr = []
        // 循环的数据先添加进去
        let cycleList = [...this.transRows2Array(this.cycleData)]
        for(let cell of cycleList){
            cell['cycle'] = 1
            arr.push(cell)
        }
        //改变位置的数据
        for(let cell of this.moveList){
            arr.push(cell)
        }
        //原数据 需要剔除哪些被占用位置的数据
        for(let cell of this.cellList){
            if(this.isChangeCell(cell.r, cell.c, true)){
                let flag = true
                for(let i=0;i<arr.length;i++){
                    if(this.compareCell(cell, arr[i])){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    arr.push(cell)
                }
            }else{
                arr.push(cell)
            }
        }
        this.cellList = [...arr]
    }

    compareCell(a, b){
        return a.r == b.r && a.c == b.c
    }

    /**
     * 只有当 行号大于循环开始行  然后列号在循环列之内的需要移动
     * @param cell
     * @returns {boolean}
     */
    isChangeCell(row, col, eq){
        let flag = false
        if(row>this.begin || (eq && row==this.begin)){
            for(let colNum of this.cycleCells){
                if(colNum==col){
                    flag = true
                    break;
                }
            }
        }
        return flag;
    }

    handleLayerRange(){
        let map = {}
        for(let i=0;i<this.cellList.length;i++){
            let cell = this.cellList[i]
            let layerId = cell['d'].virtual
            if(!layerId){
                continue;
            }else{
                if(!map[layerId]){
                    map[layerId] = []
                }
                map[layerId].push([cell.r, cell.c])
            }
        }
        console.log('map',map)

        let imgList = this.excelJsonData['imgList']
        this.setLayerCellRange(imgList, map);

        let chartList = this.excelJsonData['chartList']
        this.setLayerCellRange(chartList, map);

    }

    setLayerCellRange(ls, map){
        if(ls && ls.length>0){
            for(let item of ls){
                let id = item['layer_id']
                item['virtualCellRange'] = map[id]
            }
        }
    }

}

