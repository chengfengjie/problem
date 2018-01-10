<template>
    <div>
        <el-tag type="danger" v-if="problemType===1">缺陷</el-tag>
        <el-tag type="success" v-if="problemType===2">任务</el-tag>
        <el-tag type="primary" v-if="problemType===3">需求</el-tag>
    </div>
</template>
<script>
    export default {
        props: {
            problemType: [Number],
        }
    }
</script>
