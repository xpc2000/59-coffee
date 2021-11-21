<template>
  <el-page-header @back="goBack" content="我的收藏"> </el-page-header>

  <!-- 空行 -->
  <p style="margin: 5em;"></p>

  <div v-for="post in posts" :key="post.pid">
    <el-card>
      <div class="space-between-flex">
        <el-space>
          <span>{{ post.postSection }}</span>
          <el-tag size="small">{{ post.postTag }}</el-tag>
          <el-tag v-if="post.good" size="small" type="danger">加精</el-tag>
        </el-space>
        <div class="little-gray">
          <span class="remove-favorite-button" @click="cancelFav(post.pid)">取消收藏</span>
          | {{ formateDate(post.postTime, "yyyy-MM-dd") }} | 回复数 {{ post.postReplies }} | 赞 {{ post.postPraise }}
        </div>
      </div>
      <h2>{{ post.postTitle }}</h2>
    </el-card>

    <!-- 空行 -->
    <p></p>
  </div>
</template>

<script>
import formateDate from "../../jsmodule/format-date";

export default {
  data() {
    return {
      posts: [
        {
          fid: 1777,
          pid: 1,
          postSection: "娱乐",
          postTag: "瓜",
          postTitle: "我的收藏，这是第一条收藏",
          postReplies: 34,
          postPraise: 111,
          postTime: new Date(),
          good: true,
        },
        {
          fid: 1779,
          pid: 1,
          postSection: "娱乐",
          postTag: "瓜",
          postTitle: "我的收藏，这已经是第1779条收藏了",
          postReplies: 34,
          postPraise: 111,
          postTime: new Date(),
          good: true,
        },
      ],
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
    cancelFav(pid) {
      this.$axiosLogin
        .delete("/users/self/favourate/" + pid)
        .then(() => {
          this.$message.success("取消收藏成功");
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        })
        .catch((e) => {
          this.$solve(e);
        });
    },
  },
  mounted() {
    this.$axiosLogin
      .get("/users/self/favourate")
      .then((res) => {
        let array = res.data.data;
        console.log(array);
        for (let id in array) {
          let item = array[id];
          item.postTime = new Date(item.postTime);
          item.fid = parseInt(id);
          if (!item.postTag) {
            item.postTag = "默认";
          }
        }
        this.posts = array;
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

.space-between-flex {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
}

.remove-favorite-button {
  color: rgb(255, 116, 116);
}

.remove-favorite-button:hover {
  cursor: pointer;
}
</style>
