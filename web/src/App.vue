<template>
  <div id="app" class="index_background">
    <router-view />
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  mounted() {
    this.getUserInfo()
  },
  methods: {
    ...mapActions(['getUserInfo'])
  }
}
</script>

<style>
.index_background {
  background: url('./assets/BG.jpg') center top no-repeat;
  /* background-position:left top; */
}
</style>