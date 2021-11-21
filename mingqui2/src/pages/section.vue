<template>
  <main-layout>
    <el-container>
      <div class="el-header">
        <img src="../img/论坛-2.jpg" alt="" /><span style="font-size: 22px">{{
          blockname
        }}</span>
        <el-button v-if="!isLogin" class="loginbutton" @click="jumpToLogin"
          >登录</el-button
        >
      </div>
      <el-button @click="showFavorate" type="danger" plain
        >{{isOnlyShowFavor?'显示所有帖子':'显示所有精品帖子'}}</el-button
      >
      <div class="navandsearch clearfix">
        <div class="nav">
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
          >
            <el-menu-item
              v-for="(tag, index) in tags"
              :key="index"
              :index="index"
              >{{ tag.tname }}</el-menu-item
            >
          </el-menu>
        </div>
        <div class="search">
          <label for="content">按标题查询&nbsp;&nbsp;</label
          ><input type="text" id="content" v-model="searchcondition.title" />
          <br />
          <label for="author">按发布者查询&nbsp;</label>
          <input type="text" id="author" v-model="searchcondition.poster" />
          <br />
          <label for="time">按开始时间查询</label>
          <el-date-picker
            id="time"
            v-model="searchcondition.timeBegin"
            type="date"
            placeholder="选择日期"
          >
          </el-date-picker>
          <br />
          <label for="timeEnd">按结束时间查询</label
          ><el-date-picker
            id="timeEnd"
            v-model="searchcondition.timeEnd"
            type="date"
            placeholder="选择日期"
          >
          </el-date-picker>
          <br />
          <el-button size="mini" @click="searchConditional">查询</el-button>
        </div>
      </div>

      <!-- 发布帖子和置顶帖子 -->
      <el-container style="line-height: 2.5em" v-if="isShowIssue">
        标题
        <el-input v-model="issuePost.title" placeholder="请输入内容"></el-input>
        <br />内容<br />
        <el-input
          v-model="issuePost.content"
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
        >
        </el-input>
        <br />文本类型
        <el-select v-model="issuePost.contentType" placeholder="请选择">
          <el-option
            v-for="(contentItem, index) in contentTypeSelected"
            :key="index"
            :label="contentItem"
            :value="index + 1"
          >
          </el-option>
        </el-select>
        标签
        <el-select clearable v-model="issuePost.tid" placeholder="请选择">
          <el-option
            v-for="tagItem in tags"
            :key="tagItem.tid"
            :label="tagItem.tname"
            :value="tagItem.tid"
          >
          </el-option>
        </el-select>
        <br />
        是否需要付费
        <el-switch
          v-model="isNeedPayed"
          active-color="#13ce66"
          inactive-color="#ff4949"
        ></el-switch>
        <span v-if="isNeedPayed">付费多少</span>
        <el-input
          v-if="isNeedPayed"
          v-model.number="issuePost.payment"
          placeholder="请输入内容"
        ></el-input>
      </el-container>
      <el-button style="margin-bottom: 1em" @click="toggleIsIssue">{{
        isShowIssue ? "取消发布帖子" : "发布帖子"
      }}</el-button>
      <el-button v-if="isShowIssue" @click="issue">发布</el-button>
      <span v-if="issuePostErrInfo != ''">{{ issuePostErrInfo }}</span>
      <!-- 某个标签下的所有帖子 -->
      <el-container v-if="!isOnlyShowFavor"
        ><el-container
          class="content"
          v-for="(posts, index) in blockdata"
          :key="index"
        >
          <el-card>
            <el-container>
              <el-container class="user">
                <span
                  ><a
                    style="text-decoration: none; color: black"
                    @click="jumpToUser(posts.uid)"
                    href="javascript:;"
                    >{{ posts.uname }}</a
                  ></span
                >
                <span style="margin-left: 3em">{{ posts.postTitle }}</span>
                <el-tag class="tag" v-if="isDefaultBlock">{{
                  posts.postTag
                }}</el-tag>
                <el-tag class="tag" type="danger" v-if="posts.good"
                  >精品</el-tag
                >
              </el-container>
              <el-container
                ><a
                  href="javascript:;"
                  class="more"
                  @click="getBlockDetail(posts.pid)"
                  >查看详情</a
                ></el-container
              >
              <el-container style="margin-bottom: 1em">{{
                posts.postContent
              }}</el-container>
              <el-container>
                <el-tag type="primary"
                  >赞同{{posts.postPraise&lt;=10000?posts.postPraise:Math.round(posts.postPraise/10000)+'万'}}</el-tag
                >
                <span style="margin-left: 2.5em"
                  >回复{{ posts.postReplies }}</span
                >
                <span
                  style="
                    width: 20%;
                    margin-right: 0;
                    display: inline-block;
                    float: right;
                  "
                  >{{ dateTime(posts.postTime) }}</span
                >
              </el-container>
            </el-container>
          </el-card>
        </el-container>
      </el-container>
      <el-container v-else
        ><el-container
          class="content"
          v-for="(posts, index) in blockdataFavor"
          :key="index"
        >
          <el-card>
            <el-container>
              <el-container class="user">
                <span
                  ><a
                    style="text-decoration: none; color: black"
                    @click="jumpToUser(posts.uid)"
                    href="javascript:;"
                    >{{ posts.uname }}</a
                  ></span
                >
                <span style="margin-left: 3em">{{ posts.postTitle }}</span>
                <el-tag class="tag" v-if="isDefaultBlock">{{
                  posts.postTag
                }}</el-tag>
                <el-tag class="tag" type="danger" v-if="posts.good"
                  >精品</el-tag
                >
              </el-container>
              <el-container
                ><a
                  href="javascript:;"
                  class="more"
                  @click="getBlockDetail(posts.pid)"
                  >查看详情</a
                ></el-container
              >
              <el-container style="margin-bottom: 1em">{{
                posts.postContent
              }}</el-container>
              <el-container>
                <el-tag type="primary"
                  >赞同{{posts.postPraise&lt;=10000?posts.postPraise:Math.round(posts.postPraise/10000)+'万'}}</el-tag
                >
                <span style="margin-left: 2.5em"
                  >回复{{ posts.postReplies }}</span
                >
                <span
                  style="
                    width: 20%;
                    margin-right: 0;
                    display: inline-block;
                    float: right;
                  "
                  >{{ dateTime(posts.postTime) }}</span
                >
              </el-container>
            </el-container>
          </el-card>
        </el-container>
      </el-container>
    </el-container>
  </main-layout>
</template>
<style src="../css/font/iconfont.css">
</style>
<style scoped>
@font-face {
  font-family: "iconfont logo";
  src: url("https://at.alicdn.com/t/font_985780_km7mi63cihi.eot?t=1545807318834");
  src: url("https://at.alicdn.com/t/font_985780_km7mi63cihi.eot?t=1545807318834#iefix")
      format("embedded-opentype"),
    url("https://at.alicdn.com/t/font_985780_km7mi63cihi.woff?t=1545807318834")
      format("woff"),
    url("https://at.alicdn.com/t/font_985780_km7mi63cihi.ttf?t=1545807318834")
      format("truetype"),
    url("https://at.alicdn.com/t/font_985780_km7mi63cihi.svg?t=1545807318834#iconfont")
      format("svg");
}
.clearfix:after {
  content: "";
  display: block;
  clear: both;
  visibility: hidden;
}
.el-container {
  display: block;
}
.el-header {
  height: 150px;
  background-image: url("../img/论坛背景.jpg");
  background-size: 100%;
}
.el-header img {
  width: 110px;
  height: 80%;
}
/* .el-main {
  display: block;
} */
.el-menu-item {
  margin-top: 15px;
  font-size: 18px;
}
.contents {
  margin-left: 3%;
  font-size: 18px;
}
.content {
  margin-bottom: 25px;
}
.more {
  width: 100%;
  display: inline-block;
  text-decoration: none;
  text-align: right;
}
.nav {
  float: left;
  width: 45%;
}
.search {
  float: right;
  line-height: 2em;
  margin-top: -5em;
}
.search input {
  width: 10em;
}
.el-input {
  width: 30%;
}
.el-textarea {
  width: 75%;
}
.el-tag {
  margin-left: 3em;
}
</style>
<script>
import mainLayout from "../components/main-layout";
import { getPayload } from "../jsmodule/token";

export default {
  data() {
    return {
      //识别是否登录
      isLogin: false,
      // 以下是跟纯界面相关的数据
      activeIndex: 0,
      isShowIssue: false,
      // 以下是从后台拿来的数据
      sid: 0, //section id
      contentTypeSelected: ["text", "html5", "markdown"],
      blockname: "娱乐板块",
      isNeedPayed: false,
      issuePost: {
        title: "",
        content: "",
        contentType: "",
        sid: 0,
        tid: 0,
        payment: 0,
      },
      tags: [
        {
          tid: 1,
          tname: "全部",
        },
      ],
      issuePostErrInfo: "",
      isDefaultBlock: true, //是否是“全部”（显示所有标签的帖子）
      blockdata: [
        {
          pid: 1,
          postSection: "",
          postTag: "",
          postTitle: "",
          postReplies: 300,
          postPraise: 500,
          postTime: "",
          good: false,
          uid: 1,
          uname: "flash",
        },
        {
          user: {
            id: 2,
            name: "asdf",
            img: "/ui/favicon.ico",
          },
          post: {
            name: "帖子",
            content: "这是一条帖子",
            praise: 345,
          },
        },
      ],
      blockdataFavor: null,
      searchcondition: {
        title: "",
        poster: "",
        timeBegin: null,
        timeEnd: null,
      },
      //是否只显示精品帖子
      isOnlyShowFavor: false,
    };
  },
  computed: {
    path() {
      return this.$route.path;
    },
    dateTime() {
      return function (time) {
        if (time == undefined) {
          return time;
        } else {
          return time.slice(0, 10);
        }
      };
    },
  },
  components: {
    mainLayout,
  },
  mounted() {
    if (getPayload() == undefined) {
      console.log("nnn");
    } else {
      console.log(getPayload(), getPayload().userId);
    }

    if (localStorage.getItem("user-token")) {
      this.isLogin = true;
    }
    let idGet = this.$route.params.id;
    //用于测试
    // idGet = 1;
    this.sid = idGet;
    this.issuePost.sid = this.sid;
    console.log(idGet);
    console.log("这里用的是测试的id");
    this.$axiosBase.get(`/sections/${idGet}`).then((response) => {
      // console.log(response.data);
      let dataget = response.data.data;
      console.log(dataget);
      this.blockname = dataget.sname;
      this.tags = dataget.tags;
      this.tags.splice(0, 0, { tid: -1, tname: "全部" });
      console.log(this.tags);
      this.blockdata = dataget.posts;
      console.log(this.blockdata);
    });
  },
  methods: {
    searchConditional() {
      this.$axiosBase
        .post(`/sections/${this.sid}/posts/query`, this.searchcondition)
        .then((res) => {
          console.log(res);
          this.blockdata = res.data.data;
          console.log("hh");
          console.log(this.blockdata);
        });
    },
    loadVisitInfo() {
      console.log(this.$store.state.uid + "uuuu");
    },
    jumpToLogin() {
      this.$router.push("/guide");
    },
    handleSelect(key, keypath) {
      console.log(key, keypath);
      console.log(this.activeIndex);
      if (this.activeIndex == key) return;
      //请求全部帖子
      else if (this.activeIndex != key && key == 0) {
        this.isDefaultBlock = true;
        this.$axiosBase.get(`/sections/${this.sid}`).then((response) => {
          // console.log(response.data);
          let dataget = response.data.data;
          this.blockdata = dataget.posts;
        });
      }
      //请求某个标签的帖子
      else {
        this.isDefaultBlock = false;
        console.log("yes");
        console.log(this.tags);
        console.log(`/sections/${this.sid}/tags/${this.tags[key].tid}/posts`);
        this.$axiosBase
          .get(`/sections/${this.sid}/tags/${this.tags[key].tid}/posts`)
          .then((res) => {
            let dataget = res.data.data;
            this.blockdata = dataget;
            console.log(dataget);
          });
      }
      this.activeIndex = key;
      console.log("ac" + this.activeIndex);
    },
    getBlockDetail(id) {
      this.$router.push(`/post/${id}`);
    },
    sh(n) {
      console.log("n" + n);
    },
    toggleIsIssue() {
      this.isShowIssue = !this.isShowIssue;
    },
    issue() {
      console.log(this.issuePost);
      if (this.issuePost.title == "") {
        this.issuePostErrInfo = "请输入标题";
        return;
      }
      if (this.issuePost.content == "") {
        this.issuePostErrInfo = "请输入内容";
        return;
      }
      this.$axiosLogin
        .post("/sections/1/posts/self", this.issuePost)
        .then((res) => {
          if (res.data.errno == 504) {
            this.issuePostErrInfo = "请选择标签";
          } else {
            this.$message("发布成功");
            this.issuePostErrInfo = "";
            this.isShowIssue = false;
          }
        })
        .catch((err) => {
          if (err.response.data.errno == 704) {
            this.issuePostErrInfo = "请先登录";
          }
          console.log("bbb");
        });
    },
    // showtops(){
    //   this.$axiosBase.get(`/sections/${this.sid}/posts/top`).then((response) => {
    //   // console.log(response.data);
    //   let dataget = response.data.data;
    //   console.log(dataget);
    //   this.blockname = dataget.sname;
    //   this.tags = dataget.tags;
    //   this.tags.splice(0, 0, { tid: -1, tname: "全部" });
    //   console.log(this.tags);
    //   this.blockdata = dataget.posts;
    //   console.log(this.blockdata);
    // }
    jumpToUser(uid) {
      this.$router.push(`/userhome/visit/${uid}`);
    },
    showFavorate() {
      if (!this.isOnlyShowFavor) {
        if (this.blockdataFavor == null) {
          this.blockdataFavor = new Array();
          for (let i = 0; i < this.blockdata.length; i++) {
            if (this.blockdata[i].good) {
              this.blockdataFavor.push(this.blockdata[i]);
            }
          }
        }
      }
      this.isOnlyShowFavor=!this.isOnlyShowFavor;
    },
  },
};
</script>
