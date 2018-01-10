<template>
    <div style="padding-top: 10px;padding-bottom: 10px">
        <span style="color: deepskyblue;" v-if="data.productName">{{data.productName}}</span>
        <span>{{data.problemTitle}}</span>
        <br />
        <div style="padding-top: 5px" v-if="data.tagList && data.tagList.length > 0">
            <problem-tag v-for="item in data.tagList" :key="item.tagName" :color="item.tagColor">
                {{item.tagName}}
            </problem-tag>
        </div>
    </div>
</template>
<script>
    export default {
        props: {
            data: [Object]
        },
    }
</script>
