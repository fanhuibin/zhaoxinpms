<template>
    <JTable :data="list" style="width:100%">
        <el-table-column v-if="false" label="活动ID" align="center" prop="activityId" width="60" />
        <el-table-column label="活动名称" align="center" prop="activityName" width="140" />
        <el-table-column label="办理人ID" align="center" prop="assignee" width="90" />
        <el-table-column label="办理人" align="center" prop="assigneeName" width="80" />
        <el-table-column label="备注" align="center" prop="comment" />
        <el-table-column label="开始时间" align="center" prop="startTime" width="160" />
        <el-table-column label="结束时间" align="center" prop="endTime" width="160" />
        <el-table-column label="耗时" align="center" prop="durationInMillis" width="150" />
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
