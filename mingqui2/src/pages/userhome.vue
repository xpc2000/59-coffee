<template>
  <main-layout>
    <router-view></router-view>
  </main-layout>
</template>

<script>
import mainLayout from "../components/main-layout";
import userIndex from "../components/user/index";
import userVisit from "../components/user/visit";
import userAvatar from "../components/user/avatar";
import userAttention from "../components/user/attention";
import userFollow from "../components/user/follow";
import userFavorite from "../components/user/favorite";
import userBlacklist from "../components/user/blacklist";

export default {
  routeChildren: [
    { path: "", redirect: "/userhome/index" },
    { path: "index", component: userIndex },
    { path: "visit/:id", component: userVisit },
    { path: "avatar", component: userAvatar },
    { path: "attention", component: userAttention },
    { path: "follow", component: userFollow },
    { path: "favorite", component: userFavorite },
    { path: "blacklist", component: userBlacklist },
  ],
  computed: {
    path() {
      return this.$route.path;
    },
  },
  components: {
    mainLayout,
  },
};
</script>
