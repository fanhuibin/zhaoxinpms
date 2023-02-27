<template>
    <JTable :data="list" style="width:100%">
        <el-table-column v-if="false" label="活动ID" align="center" prop="activityId" width="60" />
        <el-table-column label="任务名称" align="center" prop="taskName" width="140" />
        <el-table-column label="办理人ID" align="center" prop="userId" width="90" />
        <el-table-column label="办理人" align="center" prop="userName" width="80" />
        <el-table-column label="备注" align="center" prop="fullMsg" />
        <el-table-column label="处理时间" align="center" prop="time" width="160">
            <template slot-scope="scope">
                 {{parseTime(scope.row.time)}}
            </template>
        </el-table-column>
    </JTable>
</template>

<script>
export default {
    props: {
        list: { type: Array, default: [] },
        endTime: { type: Number, default: 0 },
    },
    name: 'recordList',
};
</script>
