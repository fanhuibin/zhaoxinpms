<template>
    <div class="Jcommon-layout">
        <div class="Jcommon-layout-center">
            <div class="Jcommon-layout-main Jflex-main">
                <div class="Jcommon-head">
                    <el-row :gutter="10" class="mb8">
                        <el-col :span="1.5">
                            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="designTemplate" >
                                设计模板
                            </el-button>
                        </el-col>
                    </el-row>
                   
                </div>

                <JTable :data="printTemplateList">
                    <el-table-column label="模板名" align="center" prop="name" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="editTempalte(scope.row)">
                                预览模板
                            </el-button>
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="editTempalte(scope.row)">
                                模板维护
                            </el-button>
                        </template>
                    </el-table-column>
                </JTable>
            </div>
        </div>
    </div>
</template>

<script>
import { getLodop } from '@/utils/lodop/LodopFuncs';
export default {
    data() {
        return {
            printTemplateList: [
                {
                    name: '常规收费交费单',
                    method: '',
                },
                {
                    name: '常规收费退费单',
                    method: '',
                },
            ],
        };
    },
    created() {},
    methods: {
        designTemplate() {
            LODOP=getLodop(); 
            LODOP.PRINT_INIT("emptyDesign");		       
		    LODOP.PRINT_DESIGN();
        },
        editTempalte() {

        },
    },
};
</script>
