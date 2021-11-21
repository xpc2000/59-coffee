<template>
  <div><router-view></router-view></div>
</template>

<script>
import { initLoginStatus } from "./jsmodule/token";

export default {
  data() {
    return {};
  },
  mounted() {
    //准备加载登录信息
    initLoginStatus(this).catch(()=>{});
  },
};
</script>
