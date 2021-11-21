<template>
  <!-- 其他用户主页 -->

  <div v-if="ban">
    <el-result icon="error" title="该用户已经被封禁" subTitle="暂时无法访问，请点击按钮返回上一页面">
      <template #extra>
        <el-button type="primary" size="medium" @click="goBack()">返回</el-button>
      </template>
    </el-result>
  </div>

  <div v-if="inBlacklist">
    <el-result icon="error" title="该用户已经进入您的黑名单" subTitle="暂时无法访问，请点击按钮返回上一页面">
      <template #extra>
        <el-button type="primary" size="medium" @click="goBack()">返回</el-button>
      </template>
    </el-result>
  </div>

  <el-dialog title="填写举报理由" v-model="accuseDialog">
    <el-input v-model="accuseReason"></el-input>
    <template #footer>
      <el-button @click="accuseUser(accuseUserId, accuseReason)">提交</el-button>
    </template>
  </el-dialog>

  <div v-if="!ban && !inBlacklist">
    <el-card>
      <el-space :size="40">
        <el-avatar shape="square" :size="170" :src="user.avatarURL"></el-avatar>
        <el-space direction="vertical" alignment="left">
          <el-space size="large">
            <h1>{{ user.nickname }}</h1>
            <el-button v-if="!following" icon="el-icon-star-off" size="small" @click="follow(user.id)">关注</el-button>
            <el-button v-if="following" icon="el-icon-close" size="small" @click="disFollow(user.id)">取关</el-button>
            <el-button icon="el-icon-warning-outline" size="small" @click="openUserAccuseDialog(user.id)">举报</el-button>
            <el-button icon="el-icon-delete" size="small" @click="black(user.id)">黑名单</el-button>
          </el-space>
          <div class="little-gray">个性签名：{{ user.message }}</div>
          <div class="little-gray">
            等级 {{ user.levelGrade }} | 经验 {{ user.levelExp }} | 注册日期 {{ formateDate(user.createdTime, "yyyy-MM-dd") }}
          </div>
          <div class="little-gray">
            发帖数 {{ user.postCount }} | 回帖数 {{ user.repliesCount }} | 赞 {{ user.praiseCount }} | 粉丝 {{ user.followercount }}
          </div>
        </el-space>
      </el-space>
    </el-card>

    <!-- 空行  -->
    <p></p>

    <el-tabs type="border-card" v-model="activeTag">
      <!-- 用户的帖子 -->
      <el-tab-pane label="TA的帖子" name="posts">
        <div v-for="post in posts" :key="post.pid">
          <el-card>
            <div class="space-between-flex">
              <el-space>
                <span>{{ post.postSection }}</span>
                <el-tag size="small">{{ post.postTag }}</el-tag>
                <el-tag v-if="post.good" size="small" type="danger">加精</el-tag>
              </el-space>
              <div class="little-gray">
                <!-- <span class="accuse-button" @click="accuse(post.pid, 2)">举报</span> |  -->
                {{ formateDate(post.postTime, "yyyy-MM-dd") }} | 回复数 {{ post.postReplies }} | 赞 {{ post.postPraise }}
              </div>
            </div>
            <h2>{{ post.postTitle }}</h2>
          </el-card>

          <!-- 空行 -->
          <p></p>
        </div>
      </el-tab-pane>

      <!-- 用户的跟帖 -->
      <el-tab-pane label="TA的跟帖" name="layers">
        <div v-for="reply in layers" :key="reply.pid">
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
      <el-tab-pane label="TA的回复" name="interlayers">
        <div v-for="reply in interlayers" :key="reply.pid">
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
  </div>
</template>

<script>
import formateDate from "../../jsmodule/format-date";
import { fixNullToZero } from "../../jsmodule/fixvo";

export default {
  data() {
    return {
      ban: false,
      inBlacklist: false,
      following: false,
      activeTag: "posts",

      accuseDialog: false,
      accuseReason: "",
      accuseUserId: 0,

      user: { createdTime: new Date() },

      posts: [],

      layers: [],

      interlayers: [],
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
      this.$router.push(`./${id}`);
    },
    accuse(id, type) {
      //TODO axios
      this.$message.error(`TODO solve accusation id=${id} type=${type}`);
    },
    openUserAccuseDialog(uid) {
      this.accuseUserId = uid;
      this.accuseDialog = true;
    },
    accuseUser(uid, reason) {
      this.accuseDialog = false;
      this.$axiosLogin
        .post("/accusations/users/" + uid, { reason: reason })
        .then(() => {
          this.$message.success("举报成功");
        })
        .catch((e) => this.$solve(e));
    },
    follow(uid) {
      this.$axiosLogin
        .post("/users/self/follows/" + uid)
        .then(() => {
          this.$message.success("关注成功");
          this.following = true;
        })
        .catch((e) => this.$solve(e));
    },
    disFollow(uid) {
      this.$axiosLogin
        .delete("/users/self/follows/" + uid)
        .then(() => {
          this.$message.success("取关成功");
          this.following = false;
        })
        .catch((e) => this.$solve(e));
    },
    black(uid) {
      this.$axiosLogin
        .post("/users/self/blacklist/" + uid)
        .then(() => {
          this.$router.go(0);
        })
        .catch((e) => this.$solve(e));
    },
  },
  mounted() {
    let loadVisitInfo = () => {
      let visitUid = this.$route.params.id;

      if (this.$store.state.blackMap.get(parseInt(visitUid))) {
        this.inBlacklist = true;
        return;
      }

      // 获取关注信息
      this.$axiosLogin
        .get("/users/self/follows/" + visitUid)
        .then(() => (this.following = true))
        .catch(() => {this.following = false}); //屏蔽错误

      this.$axiosLogin
        .get("users/" + visitUid)
        .then((res) => {
          let user = res.data.data;
          console.log(user);
          user.createdTime = new Date(user.createdTime);
          user = fixNullToZero(user);
          this.user = user;
        })
        .catch((e) => this.$solve(e, "获取个人信息错误"));

      this.$axiosLogin
        .get("/users/" + visitUid + "/posts")
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
        .get("/users/" + visitUid + "/layers")
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
        .get("/users/" + visitUid + "/interlayers")
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
    };

    let waitUidLoaded = () => {
      if (!this.$store.state.uid) {
        setTimeout(() => {
          waitUidLoaded();
        }, 100);
        return;
      }
      loadVisitInfo();
    };
    waitUidLoaded();
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

.accuse-button {
  color: rgb(255, 116, 116);
}

.accuse-button:hover {
  cursor: pointer;
}
</style>
