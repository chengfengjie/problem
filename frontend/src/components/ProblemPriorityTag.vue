<template>
    <div>
        <el-tag type="danger" v-if="priority === 1">急</el-tag>
        <el-tag type="warning" v-if="priority === 2">高</el-tag>
        <el-tag type="primary" v-if="priority === 3">中</el-tag>
        <el-tag type="success" v-if="priority === 4">低</el-tag>
    </div>
</template>
<script>
    export default {
        props: {
            priority: [Number]
        },
    }
</script>
