<template>
  <el-page-header @back="goBack" content="我的关注"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <div v-for="follow in followArray" :key="follow.uid">
    <el-card>
      <div class="space-between-center-flex">
        <el-space direction="vertical" alignment="left">
          <el-space class="hover-pointer" alignment="baseline" size="large" @click="goUserhome(follow.uid)">
            <el-avatar shape="square" :size="30" :src="follow.avatarUrl" style="position:relative;top:6px"></el-avatar>
            <b style="font-size:26px;">{{ follow.nickname }}</b>
          </el-space>
          <div>{{ follow.message }}</div>
          <div class="little-gray">
            等级 {{ follow.levelGrade }} | 经验 {{ follow.levelExp }} | 注册日期 {{ formateDate(follow.signupTime, "yyyy-MM-dd") }} | MQ龄
            {{ follow.mingqAge }}年 | 发帖数 {{ follow.postsCount }} | 回帖数 {{ follow.repliesCount }} | 赞 {{ follow.praiseCount }} | 粉丝
            {{ follow.followerCount }}
          </div>
        </el-space>
        <el-button type="danger" @click="disFollow(follow.uid)">取关</el-button>
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
      followArray: [],
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
    disFollow(uid) {
      this.$axiosLogin
        .delete("/users/self/follows/" + uid)
        .then(() => {
          this.$message.success("取关成功");
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        })
        .catch((e) => this.$solve(e));
    },
  },
  mounted() {
    this.$axiosLogin
      .get("/users/self/follows")
      .then((res) => {
        let followArray = res.data.data;
        // console.log(followArray);
        for (let id in followArray) {
          let follow = followArray[id];
          follow.signupTime = new Date(follow.signupTime);
          follow.fid = parseInt(id);
        }
        this.followArray = followArray;
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
