// 该组件定义了容器的基本布局以及顶部悬浮框

<template>
  <el-container>
    <!-- 顶部悬浮窗 -->
    <el-header height="auto" style="box-shadow: 0 0 7px rgb(190, 190, 190);">
      <el-row>
        <el-col :xs="0" :sm="0" :md="0" :lg="4" :xl="4"></el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="16" :xl="16" class="col-topbar"> <topbar></topbar></el-col>
        <el-col :xs="0" :sm="0" :md="0" :lg="4" :xl="4"> </el-col>
      </el-row>
    </el-header>

    <!-- 主要内容框 -->
    <el-main>
      <el-row>
        <el-col :xs="0" :sm="0" :md="0" :lg="4" :xl="4"></el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="16" :xl="16" class="col-slot"> <slot></slot></el-col>
        <el-col :xs="0" :sm="0" :md="0" :lg="4" :xl="4"> </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import topbar from "./topbar";

export default {
  components: { topbar },
};
</script>

<style scoped>
.el-main,
.el-header {
  padding: 0;
}

.col-topbar {
  padding: 0 1em;
}

.col-slot {
  padding: 2em 1em;
}
</style>
