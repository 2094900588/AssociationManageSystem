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
  background: #7dc24e center top no-repeat;
  /* background-position:left top; */
}

th,
td {
  text-align: center !important;
}
</style>