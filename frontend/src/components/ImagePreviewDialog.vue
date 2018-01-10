<template>
    <div>
        <el-dialog @close="handleClose" title="图片" :visible.sync="imageVisible" top="30px" :size="imageSize">
            <img :src="src"
                 @click="changeImageSize"
                 style="display: block;margin: auto;border: 1px dotted #e9e9e9;max-width: 100%;cursor: pointer"
                 :style="imageStyle" />
        </el-dialog>
    </div>
</template>
<script>
    export default {
        props: {
            src: {
                type: String,
                default: '',
            },
            visible: {
                type: Boolean,
                default: false,
            }
        },
        data() {
            return {
                imageVisible: false,
                imageStyle: {
                    width: 'auto',
                },
                imageSize: 'big',
            }
        },
        watch: {
            visible: function (newValue) {
                if (newValue) {
                    this.imageVisible = true
                }
            }
        },
        methods: {
            changeImageSize() {
                if (this.imageSize === 'big') {
                    this.imageSize = 'large'
                    this.imageStyle.width = '100%'
                } else {
                    this.imageSize = 'big'
                    this.imageStyle.width = 'auto'
                }
            },
            handleClose() {
                this.$emit('update:visible', false)
            }
        },
    }
</script>
