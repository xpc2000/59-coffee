<template>
  <!-- 自己的主页 -->

  <el-card>
    <el-space :size="40">
      <el-avatar shape="square" :size="190" :src="user.avatarUrl" @click="goAvatar()"></el-avatar>
      <el-space direction="vertical" alignment="left">
        <el-space size="large">
          <h1>{{ user.nickname }}</h1>
          <el-button icon="el-icon-edit" size="small" @click="edit = true">编辑</el-button>
          <el-button icon="el-icon-edit" size="small" @click="delself()">注销</el-button>
        </el-space>
        <div class="little-gray">个性签名：{{ user.message }}</div>
        <div class="little-gray">
          等级 {{ user.levelGrade }} | 经验 {{ user.levelExp }} | 注册日期 {{ formateDate(user.signupTime, "yyyy-MM-dd") }}
        </div>
        <div class="little-gray">
          发帖数 {{ user.postsCount }} | 回帖数 {{ user.repliesCount }} | 赞 {{ user.praiseCount }} | 粉丝 {{ user.followerCount }}
        </div>
        <div class="little-gray">邮箱 {{ user.email }} | 电话 {{ user.phone }} | 论坛币 {{ user.coin }} | 未读消息 {{ user.attentionCount }}</div>
      </el-space>
    </el-space>
  </el-card>

  <!-- 个人信息编辑 -->
  <edit-profile :user="user" :show="edit" @close="edit = false"></edit-profile>

  <!-- 空行  -->
  <p></p>

  <el-tabs type="border-card" v-model="activeTag">
    <!-- 用户的帖子 -->
    <el-tab-pane label="我的帖子" name="posts">
      <div v-for="post in posts" :key="post.pid">
        <el-card>
          <div class="space-between-flex">
            <el-space>
              <span>{{ post.postSection }}</span>
              <el-tag size="small">{{ post.postTag }}</el-tag>
              <el-tag v-if="post.good" size="small" type="danger">加精</el-tag>
            </el-space>
            <div class="little-gray">
              <span class="accuse-button" @click="fav(post.pid)">收藏</span> | {{ formateDate(post.postTime, "yyyy-MM-dd") }} | 回复数
              {{ post.postReplies }} | 赞 {{ post.postPraise }}
            </div>
          </div>
          <h2>{{ post.postTitle }}</h2>
        </el-card>

        <!-- 空行 -->
        <p></p>
      </div>
    </el-tab-pane>

    <!-- 用户的回复 -->
    <el-tab-pane label="我的跟帖" name="layers">
      <div v-for="reply in layers" :key="'L' + reply.pid">
        <el-card>
          <div class="space-between-flex">
            <div class="little-gray">帖子：{{ reply.postTitle }}</div>
            <div class="little-gray">
              {{ formateDate(reply.replyTime, "yyyy-MM-dd") }} | {{ reply.floor }}楼<span v-if="reply.replyUid">中楼</span>
            </div>
          </div>
          <div>
            <p></p>
            <el-button v-if="reply.replyUid" type="text" @click="goUserhome(reply.replyUid)">@{{ reply.replyUname }} </el-button>
            {{ reply.replyContent }}
          </div>
        </el-card>

        <!-- 空行 -->
        <p></p>
      </div>
    </el-tab-pane>

    <!-- 用户的回复 -->
    <el-tab-pane label="我的回复" name="interlayers">
      <div v-for="reply in interlayers" :key="'I' + reply.pid">
        <el-card>
          <div class="space-between-flex">
            <div class="little-gray">帖子：{{ reply.postTitle }}</div>
            <div class="little-gray">
              {{ formateDate(reply.replyTime, "yyyy-MM-dd") }} | {{ reply.floor }}楼<span v-if="reply.replyUid">中楼</span>
            </div>
          </div>
          <div>
            <p></p>
            <el-button v-if="reply.replyUid" type="text" @click="goUserhome(reply.replyUid)">@{{ reply.replyUname }} </el-button>
            {{ reply.replyContent }}
          </div>
        </el-card>

        <!-- 空行 -->
        <p></p>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import formateDate from "../../jsmodule/format-date";
import editProfile from "./subcomponent/edit-profile";
import { fixNullToZero } from "../../jsmodule/fixvo";
import { clearToken, getPayload } from "../../jsmodule/token";

export default {
  data() {
    return {
      edit: false,
      activeTag: "posts",

      user: {
        signupTime: new Date(),
      },

      posts: [],

      layers: [],

      interlayers: [],
    };
  },
  components: {
    editProfile,
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
      this.$router.push(`./${id}`);
    },
    goAvatar() {
      this.$router.push(`./avatar`);
    },
    delself() {
      this.$confirm("此操作将永久注销您的账户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$axiosLogin
          .delete("/users/self")
          .then(() => {
            clearToken();
            this.$router.push("/guide");
          })
          .catch((e) => {
            this.$solve(e, "注销失败");
          });
      });
    },
    fav(pid) {
      this.$axiosLogin
        .post("/users/self/favourate/" + pid)
        .then(() => {
          this.$message.success("收藏成功");
        })
        .catch((e) => {
          this.$solve(e);
        });
    },
  },
  mounted() {
    this.$axiosLogin
      .get("users/self")
      .then((res) => {
        let user = res.data.data;
        console.log(user);
        user.signupTime = new Date(user.signupTime);
        user = fixNullToZero(user);
        this.user = user;
      })
      .catch((e) => this.$solve(e, "获取个人信息错误"));

    this.$axiosLogin
      .get("/users/" + getPayload().userId + "/posts")
      .then((res) => {
        let posts = res.data.data;
        for (let post of posts) {
          post.postTime = new Date(post.postTime);
          if (!post.postTag) {
            post.postTag = "默认";
          }
        }
        this.posts = posts;
      })
      .catch((e) => {
        this.$solve(e);
      });

    this.$axiosLogin
      .get("/users/" + getPayload().userId + "/layers")
      .then((res) => {
        let ds = res.data.data.list;
        for (let d of ds) {
          d.replyTime = new Date(d.replyTime);
        }
        this.layers = ds;
      })
      .catch((e) => {
        this.$solve(e);
      });

    this.$axiosLogin
      .get("/users/" + getPayload().userId + "/interlayers")
      .then((res) => {
        let ds = res.data.data.list;
        for (let d of ds) {
          d.replyTime = new Date(d.replyTime);
        }
        this.interlayers = ds;
      })
      .catch((e) => {
        this.$solve(e);
      });
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
</style>
