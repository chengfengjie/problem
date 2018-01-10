<template>
    <div id="problem-tag" :style="style" @click="handleClick">
        <slot></slot>
    </div>
</template>
<script>
    export default {
        props: {
            color: String,
            isActive: {
                type: Boolean,
                default: false,
            },
            value: {
                type: [Number, String],
            },
            selected: {
                type: Boolean,
                default: false,
            }
        },
        data() {
            return {
                style: {
                    color: this.color,
                    border: '0.8px solid ' + this.color,
                    backgroundColor: 'transparent',
                },
                isSelect: this.selected,
            }
        },
        created() {
            if (this.selected) {
                this.style.color = 'white'
                this.style.backgroundColor = this.color
            } else {
                this.style.color = this.color
                this.style.backgroundColor = 'transparent'
            }
        },
        methods: {
            handleClick() {
                if (this.isActive === false) {
                    return
                }
                this.isSelect = !this.isSelect
                if (this.isSelect) {
                    this.style.color = 'white'
                    this.style.backgroundColor = this.color
                } else {
                    this.style.color = this.color
                    this.style.backgroundColor = 'transparent'
                }
                this.$emit('change', this.value, this.isSelect)
            },
        },
    }
</script>
<style>
    #problem-tag {
        display: inline-block;
        height: 24px;
        line-height: 24px;
        padding-left: 8px;
        padding-right: 8px;
        border-radius: 12px;
        font-weight: 100;
        font-size: 14px;
        cursor: pointer;
        user-select: none;
        margin-right: 10px;
    }
</style>
