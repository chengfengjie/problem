<template>
    <div>
        <el-tag v-if="status === 0" type="success">已关闭</el-tag>
        <el-tag style="background-color: orangered;color: white" v-if="status === 1" type="danger">未解决</el-tag>
        <el-tag v-if="status === 2" type="success">已延期</el-tag>
        <el-tag v-if="status === 3" type="warning">待审核</el-tag>
        <el-tag v-if="status === 4" type="success">已解决</el-tag>

        <el-tag v-if="status === 11" type="warning">不是错误</el-tag>
        <el-tag v-if="status === 12" type="warning">重复问题</el-tag>
        <el-tag v-if="status === 13" type="warning">延期解决</el-tag>
        <el-tag v-if="status === 14" type="warning">设计如此</el-tag>
        <el-tag v-if="status === 15" type="warning">不能复现</el-tag>
        <el-tag v-if="status === 16" type="warning">不同意意见</el-tag>
        <el-tag v-if="status === 17" type="warning">忽略</el-tag>
    </div>
</template>
<script>
    export default {
        props: {
            status: [Number]
        }
    }
</script>
