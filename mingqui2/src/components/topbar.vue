<template>
  <div class="forum-topbar">
    <p class="bold-logo" @click="goIndex()">MingQ论坛</p>
    <div>
      <el-dropdown @visible-change="getDailyTask($event)">
        <!-- 用户头像与集合 -->
        <div class="dropdown">
          <el-avatar :src="avatarUrl" :size="20"></el-avatar>&nbsp;&nbsp;<span>{{ nickname }}&nbsp;<i class="el-icon-arrow-down"></i></span>
        </div>

        <!-- 下拉框 -->
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <div>
                <span>每日任务</span>&nbsp;
                <i class="el-icon-star-off" v-if="dailyTasks >= 1"></i>
                <i class="el-icon-star-off" v-if="dailyTasks >= 2"></i>
                <i class="el-icon-star-off" v-if="dailyTasks >= 3"></i>
                <i class="el-icon-star-off" v-if="dailyTasks >= 4"></i>
                <i class="el-icon-star-off" v-if="dailyTasks == 5"></i>
                <i class="el-icon-close" v-if="dailyTasks == 0"></i>
                <i class="el-icon-close" v-if="dailyTasks <= 1"></i>
                <i class="el-icon-close" v-if="dailyTasks <= 2"></i>
                <i class="el-icon-close" v-if="dailyTasks <= 3"></i>
                <i class="el-icon-close" v-if="dailyTasks <= 4"></i>
              </div>
            </el-dropdown-item>
            <el-dropdown-item @click="$router.push('/userhome/index')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/userhome/attention')">
              我的消息
              <el-badge class="mark" :value="attentionCount" type="danger" />
            </el-dropdown-item>
            <el-dropdown-item @click="$router.push('/userhome/follow')">我的关注</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/userhome/favorite')">我的收藏</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/userhome/blacklist')">黑名单</el-dropdown-item>
            <el-dropdown-item @click="exit()">退 出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

import { clearToken } from "../jsmodule/token";

export default {
  data() {
    return {
      dailyTasks: 3,
    };
  },
  computed: {
    ...mapState(["uid", "avatarUrl", "nickname", "attentionCount"]),
  },
  methods: {
    goIndex() {
      this.$router.push("/index");
    },
    exit() {
      clearToken();
      this.$store.commit("clearLoginInfo");
      this.$router.push("/guide");
    },
    getDailyTask($event) {
      if ($event) {
        this.$axiosLogin
          .get("/users/self/daily-tasks")
          .then(({ data }) => {
            this.dailyTasks = data.data.todayReplies;
          })
          .catch((e) => {
            this.$solve(e, "获取每日任务失败");
            console.log(e);
          });
      }
    },
  },
};
</script>

<style scoped>
.bold-logo {
  font-weight: 900;
  color: #444;
}

.bold-logo:hover {
  cursor: pointer;
}

.dropdown {
  /* background-color: aquamarine; */
  position: relative;
  top: 6px;

  display: flex;
  align-items: center;
}

.forum-topbar {
  height: 100%;
  width: 100%;

  display: flex;
  justify-content: space-between;
  align-items: baseline;
}
</style>
