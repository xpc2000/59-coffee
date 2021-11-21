<template>
  <el-container class="major-container">
    <el-aside class="major-side-container" width="15em">
      <el-menu class="side-menu" @select="handleSelect" :default-active="defaultActiveTagName" :unique-opened="true" :router="true">
        <el-menu-item index="admin" class="first-option"><i class="el-icon-s-custom"></i>管理团队</el-menu-item>
        <el-menu-item index="user"><i class="el-icon-user-solid"></i>用户管理</el-menu-item>
        <el-submenu index="forum-manage">
          <template #title>
            <i class="el-icon-s-management"></i>
            <span>帖子管理</span>
          </template>
          <el-menu-item index="section"><i class="el-icon-menu"></i>板块管理</el-menu-item>
          <el-menu-item index="post"><i class="el-icon-s-order"></i>帖子管理</el-menu-item>
          <el-menu-item index="reply"><i class="el-icon-s-comment"></i>回复管理</el-menu-item>
        </el-submenu>
        <el-submenu index="ban-manage">
          <template #title>
            <i class="el-icon-delete-solid"></i>
            <span>举报与封禁</span>
          </template>
          <el-menu-item index="accusation"><i class="el-icon-warning"></i>举报管理</el-menu-item>
          <el-menu-item index="ban"><i class="el-icon-error"></i>封禁管理</el-menu-item>
        </el-submenu>
        <el-menu-item index="analyse"><i class="el-icon-s-data"></i>数据分析</el-menu-item>
        <el-menu-item index="quit"><i class="el-icon-switch-button"></i>退出</el-menu-item>
      </el-menu>
    </el-aside>
    <el-main class="major-main-container">
      <div class="main-box">
        <router-view></router-view>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import manageAdmin from "../components/manage/admin";
import manageUser from "../components/manage/user";
import manageSection from "../components/manage/section";
import managePost from "../components/manage/post";
import manageReply from "../components/manage/reply";
import manageAccusation from "../components/manage/accusation";
import manageBan from "../components/manage/ban";
import manageAnalyse from "../components/manage/analyse";

import { clearToken } from "../jsmodule/token";

function autoheight() {
  let winHeight = 0;

  //IE 兼容性写法
  if (window.innerHeight) {
    winHeight = window.innerHeight;
  } else if (document.body && document.body.clientHeight) {
    winHeight = document.body.clientHeight;
  }
  if (document.documentElement && document.documentElement.clientHeight) {
    winHeight = document.documentElement.clientHeight;
  }

  let el = document.getElementsByClassName("major-container");
  if (el.length > 0) {
    el[0].style.height = winHeight + "px";
  }
}

export default {
  routeChildren: [
    { path: "", redirect: "/manage/admin" },
    { path: "index", redirect: "/manage/admin" },
    { path: "admin", component: manageAdmin },
    { path: "user", component: manageUser },
    { path: "section", component: manageSection },
    { path: "post", component: managePost },
    { path: "reply", component: manageReply },
    { path: "accusation", component: manageAccusation },
    { path: "ban", component: manageBan },
    { path: "analyse", component: manageAnalyse },
    { path: "quit", redirect: "/guide" },
  ],
  data() {
    return {};
  },
  mounted() {
    autoheight();
    window.addEventListener("resize", autoheight);
  },
  computed: {
    defaultActiveTagName() {
      return this.$route.path.split("/").slice(-1)[0];
    },
  },
  methods: {
    handleSelect(key) {
      //退出登录
      if (key == "quit") {
        clearToken();
        this.$store.commit("clearLoginInfo");
        console.log("用户已退出");
      }
    },
  },
};
</script>

<style scoped>
.major-container {
  height: 100%;
  padding: 0;
  margin: 0;
}

.side-menu {
  height: 100%;
}

.major-main-container {
  padding: 0;
  margin: 0;
  overflow: auto;
}

.side-menu .first-option {
  margin-top: 5em;
}

.main-box {
  margin: 3em 2em;
}
</style>
