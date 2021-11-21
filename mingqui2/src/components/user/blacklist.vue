<template>
  <el-page-header @back="goBack" content="黑名单"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <div v-for="ban in banArray" :key="ban.uid">
    <el-card>
      <div class="space-between-center-flex">
        <el-space direction="vertical" alignment="left">
          <el-space class="hover-pointer" alignment="baseline" size="large" @click="goUserhome(ban.uid)">
            <el-avatar shape="square" :size="30" :src="ban.avatarUrl" style="position:relative;top:6px"></el-avatar>
            <b style="font-size:26px;">{{ ban.nickname }}</b>
          </el-space>
          <div class="little-gray">
            等级 {{ ban.levelGrade }} | 经验 {{ ban.levelExp }} | 注册日期 {{ formateDate(ban.signupTime, "yyyy-MM-dd") }} | MQ龄 {{ ban.mingqAge }}年
            | 发帖数 {{ ban.postsCount }} | 回帖数 {{ ban.repliesCount }} | 赞 {{ ban.praiseCount }} | 粉丝
            {{ ban.followerCount }}
          </div>
        </el-space>
        <el-button type="danger" @click="removeFromBlacklist(ban.uid)">移除黑名单</el-button>
      </div>
    </el-card>

    <p></p>
  </div>
</template>

<script>
import formateDate from "../../jsmodule/format-date";

export default {
  data() {
    return {
      banArray: [],
    };
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },
    formateDate(date, format) {
      return formateDate(date, format);
    },
    goUserhome(id) {
      this.$router.push(`/userhome/visit/${id}`);
    },
    removeFromBlacklist(uid) {
      this.$axiosLogin
        .delete("/users/self/blacklist/" + uid)
        .then(() => {
          this.$message.success("移除黑名单成功");
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        })
        .catch((e) => this.$solve(e));
    },
  },
  mounted() {
    this.$axiosLogin
      .get("/users/self/blacklist")
      .then((res) => {
        let banArray = res.data.data;
        for (let id in banArray) {
          let ban = banArray[id];
          ban.signupTime = new Date(ban.signupTime);
          ban.bid = parseInt(id);
        }
        this.banArray = banArray;
      })
      .catch((e) => this.$solve(e));
  },
};
</script>

<style scoped>
.little-gray {
  font-size: 0.8em;
  color: rgb(134, 134, 134);
}

.space-between-center-flex {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.hover-pointer:hover {
  cursor: pointer;
}
</style>
