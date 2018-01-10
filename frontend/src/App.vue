<template>
  <div :style="style">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'app',
    data() {
      return {
        style: {
          height: '0'
        },
      }
    },
    created() {
      this.style.height = window.innerHeight + 'px'
      window.onresize = function () {
        this.style.height = window.innerHeight + 'px'
      }.bind(this)
    },
  }
</script>
