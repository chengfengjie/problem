<template>
    <div id="problemEditItem">

        <div class="content-div-box" v-if="item.editType === 0">
            <div v-if="item.solveUserName">
                <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
                <span class="name-span-box">{{item.inUserName}}</span>
                创建,指派给
                <span class="name-span-box">{{item.solveUserName}}</span>
            </div>
            <div v-else>
                <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
                <span class="name-span-box">{{item.inUserName}}</span>
                创建,未指派
            </div>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 1">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>
            评论:
            <span>{{item.content}}</span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 2">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>
            指派给
            <span class="name-span-box">{{item.solveUserName}}</span>
            <span v-if="item.content">
                ,&nbsp;&nbsp;备注: {{item.content}}
            </span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 3">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>修复
            <span v-if="item.solveUserName">,&nbsp;指派给 <span class="name-span-box">{{item.solveUserName}}</span></span>
            <span v-if="item.content">,&nbsp;&nbsp;备注:&nbsp;{{item.content}}</span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 4">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>&nbsp;&nbsp;拒绝
            <span v-if="item.rejectTypeName">({{item.rejectTypeName}})</span>
            <span v-if="item.solveUserName">,指派给: <span class="name-span-box">{{item.solveUserName}}</span></span>
            <span v-if="item.content">,备注: {{item.content}}</span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 5">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>&nbsp;&nbsp;关闭问题
            <span v-if="item.content">,&nbsp;&nbsp;备注: {{item.content}}</span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 6">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>&nbsp;&nbsp;延期问题
            <span v-if="item.content">, &nbsp;&nbsp;备注: {{item.content}}</span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 7">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>&nbsp;:&nbsp;审核通过
            <span v-if="item.auditVersionName">&nbsp;&nbsp;(&nbsp;&nbsp;审核版本: {{item.auditVersionName}}&nbsp;&nbsp;)&nbsp;&nbsp;</span>
            <span v-if="item.content">,备注:{{item.content}}</span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 8">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;:&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>审核不通过
            <span v-if="item.auditVersionName">(审核版本: {{item.auditVersionName}})</span>
            <span v-if="item.content">,备注:{{item.content}}</span>
        </div>

        <div class="content-div-box" v-else-if="item.editType === 9">
            <span class="date-span-box">{{formatDate(item.inDate)}}&nbsp;&nbsp;</span>
            <span class="name-span-box">{{item.inUserName}}</span>&nbsp;&nbsp;打开
            <span v-if="item.auditVersionName">(发现版本: {{item.auditVersionName}})</span>
            <span v-if="item.solveUserName">,指派给 <span class="name-span-box">{{item.solveUserName}}</span></span>
            <span v-if="item.content">, 备注: {{item.content}}</span>
        </div>

        <div v-if="item.imageList && item.imageList.length > 0 && showImage" style="padding-top: 10px">
            <img class="editItemImage"
                 v-for="imageItem in item.imageList"
                 :src="imageItem.imageName"
                 @click="clickImage(imageItem.imageName)" alt="">
        </div>

    </div>
</template>

<script>
    export default {
        props: {
            item: {
                type: Object
            },
            showImage: {
                type: Boolean,
                default: false,
            },
        },
        methods: {
            formatDate(timestamp) {
                if (timestamp !== null && timestamp !== undefined) {
                    return this.$moment(timestamp).format('YYYY-MM-DD HH:mm')
                } else {
                    return ''
                }
            },
            clickImage(src) {
                this.$emit('imageClick', src)
            },
        }
    }
</script>

<style>
    #problemEditItem .name-span-box {
        color: #02a0ff;
    }
    #problemEditItem .content-div-box {
        display: inline-block;
        word-break: break-all;
    }
    #problemEditItem .date-span-box {
        font-weight: bold;
        word-break: break-all;
    }
    #problemEditItem .editItemImage {
        height: 140px;
        display: block;
        margin-right: 10px;
        float: left;
        margin-bottom: 10px;
        border: 1px dotted #efefef;
        cursor: zoom-in;
    }
</style>
