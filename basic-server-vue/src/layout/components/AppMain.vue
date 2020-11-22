<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <div id="content-div">
        <router-view :key="key" />
      </div>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  margin-top: 5px;
  padding-top: 5px;
  padding-left: 15px;
  border-top-left-radius: 5px;
  background-color: #fdfcfd;
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header + .app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}

//#app-main {
//  margin-top: 10px;
//  margin-left: 10px;
//  background-color: #20a0ff;
//  width: 100%;
//  height: 100%;
//}
</style>
