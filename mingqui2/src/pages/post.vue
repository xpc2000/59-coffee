<template>
  <main-layout>
    <el-container id="layer">
      <el-card>
        <!-- 这是主帖子 -->
        <el-container>
          <el-container class="topBackground"
            >{{ postData.post.postSection }}
            <el-button @click="jumpToLogin" v-if="!isLogin" class="loginButton"
              >请登录</el-button
            >
            <el-button @click="jumpToSection" class="loginButton"
              >返回板块页</el-button
            >
          </el-container>
          <!-- 这是分页 -->
          <el-pagination
            style="margin-left: 35%"
            layout="prev, pager, next"
            :total="pages * 10"
            @current-change="handleCurrentChange"
            v-model:currentPage="nowpage"
          >
          </el-pagination>
          <!-- 这是用户和帖子名称 -->
          <el-container>
            <el-container class="title">
              <span style="margin-right: 3em"
                ><a
                  style="color: black"
                  href="javascript:;"
                  @click="jumpToUser(postData.post.uid)"
                  >{{ postData.post.uname }}</a
                ></span
              >
              <span style="margin-right: 3em">{{
                postData.post.postTitle
              }}</span
              ><el-tag>{{ postData.post.postTag }}</el-tag></el-container
            >
          </el-container>
          <el-container>
            <el-container>
              {{ postData.post.postContent }}
            </el-container>
            <el-container class="clearfix">
              <el-row style="margin-right: 30%"
                ><el-tag style="margin: 0 3em"
                  >点赞数{{ postData.post.postPraise }}</el-tag
                >
                <el-tag>回复数{{ postData.post.postReplies }}</el-tag>
              </el-row>
            </el-container>
          </el-container>
          <el-container class="clearfix">
            <el-row>
              <el-button type="danger" @click="praise">{{
                isPraised ? "已经点赞" : "点赞"
              }}</el-button>
              <el-button @click="dialogVisible = true" type="warning"
                >举报</el-button
              >
              <el-button @click="collect" type="primary">收藏</el-button>
              <el-dialog
                title="提示"
                v-model="dialogVisible"
                width="30%"
                :before-close="handleClose"
              >
                <span>请输入举报内容</span>
                <el-input
                  v-model="accusationContent"
                  placeholder="请输入内容"
                ></el-input>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="accusation"
                      >确 定</el-button
                    >
                  </span>
                </template>
              </el-dialog>
            </el-row>
          </el-container>
        </el-container>
        <el-container @mouseover="mainfocus" @mouseleave="mainblur">
          <el-input
            class="maininput"
            type="textarea"
            :rows="2"
            placeholder="请输入回复内容"
            v-model="yourReply"
          >
          </el-input>
          <el-button v-if="isMainInputFocused" @click="replyToMainBlock"
            >发布</el-button
          >
        </el-container>

        <!-- 这是回复的帖子 -->
        <el-container class="replyposts">
          <!-- 这是回复的每一条帖子 -->
          <el-container
            class="replypost"
            v-for="(post, index) in postData.postLayer"
            :key="index"
          >
            <!-- 头像和回复内容、回复和举报图标 -->

            <el-container
              class="replypost-reply"
              @mouseover="showIcon(index)"
              @mouseleave="hideIcon(index)"
            >
              <!-- 头像和回复内容 -->
              <el-container>
                <el-tag v-if="post.uid == uid" style="margin-right: 1em"
                  >我的回复</el-tag
                >
                <a
                  href="javascript:;"
                  v-if="post.uid == uid"
                  style="float: right"
                  @click="deletePost(post.lid)"
                  >删除该回复</a
                >
                <span style="margin-right: 1em; background-color: skyblue"
                  >{{ post.floor }}楼</span
                >
                <span>{{ post.uname }}</span>
              </el-container>
              <el-container>
                <span style="float: right; width: 25%">{{
                  timeShow(post.replyTime)
                }}</span>
              </el-container>
              <el-container>{{ post.content }}</el-container>
              <!-- 回复、举报、赞图标 -->
              <el-container class="icons-box" v-if="isReplyFocused[index]">
                <a
                  href="javascript:;"
                  @click="showReplySection(index)"
                  class="button"
                  ><span>{{
                    isReplySectionShowing[index] == false
                      ? "回复"
                      : "取消回复aaa"
                  }}</span
                  ><i class="iconfont">&#xe609;</i>
                </a>
                <a
                  href="javascript:;"
                  class="button"
                  @click="layerAccusationDialogVisible = true"
                  ><span>举报s</span><i class="iconfont">&#xe609;</i>
                </a>
                <el-dialog
                  title="提示"
                  v-model="layerAccusationDialogVisible"
                  width="30%"
                  :before-close="handleClose"
                >
                  <template #footer>
                    <span class="dialog-footer">
                      <el-input
                        v-model="layerDialogAccusationContent"
                        placeholder="请输入内容"
                      ></el-input>
                      <el-button
                        type="primary"
                        @click="accusationLayerCommit(post.lid)"
                        >确 定</el-button
                      >
                    </span>
                  </template>
                </el-dialog>
              </el-container>
              <!-- 回复框，平时隐藏 -->

              <el-container v-if="isReplySectionShowing[index]">
                <el-input
                  type="textarea"
                  :rows="2"
                  placeholder="请输入内容dddd"
                  v-model="replyReply[index]"
                  style="width: 80%"
                >
                </el-input>
                <el-button @click="replyReplyFunc(post.lid, index, post.uid)"
                  >回复</el-button
                >
              </el-container>
            </el-container>

            <div class="innerpost" v-if="post.interlayers != []">
              <el-container
                v-for="(interPost, indexi) in post.interlayers"
                :key="indexi"
              >
                <el-container
                  @mouseover="
                    interlayersAccusationIconShow[index][indexi] = true
                  "
                  @mouseleave="
                    interlayersAccusationIconShow[index][indexi] = false
                  "
                >
                  <!-- 显示谁回复的谁 -->
                  <el-container>
                    <el-tag v-if="uid == interPost.uid">我的回复</el-tag>
                    {{ uid }}
                    <span>{{ interPost.uname }}</span>
                    <span style="padding: 0 2em">回复</span>
                    <span>{{ interPost.replyUname }}</span>
                    <a
                      href="javascript:;"
                      v-if="uid == interPost.uid"
                      style="float: right"
                      @click="deleteInterReply(interPost.iid)"
                      >删除该回复</a
                    >
                    <el-container class="interPost"
                      >{{ interPost.content }}
                      <br />
                      <a
                        v-if="interlayersAccusationIconShow[index][indexi]"
                        href="javascript:;"
                        class="button"
                        @click="interlayerAccusationDialogVisible = true"
                        ><span>举报ss</span><i class="iconfont">&#xe609;</i>
                      </a>
                    </el-container>
                    <el-container>
                      <el-dialog
                        title="提示"
                        v-model="interlayerAccusationDialogVisible"
                        width="30%"
                        :before-close="handleClose"
                      >
                        <el-input
                          v-model="interlayerAccusationContent"
                          placeholder="请输入内容"
                        ></el-input>
                        <template #footer>
                          <span class="dialog-footer">
                            <el-button
                              type="primary"
                              @click="
                                accusationInterlayerCommit(
                                  index,
                                  indexi,
                                  interPost.iid
                                )
                              "
                              >确 定</el-button
                            >
                          </span>
                        </template>
                      </el-dialog>
                    </el-container>
                  </el-container>
                </el-container>
              </el-container>
              <el-row v-if="post.interlayerCount > 3"
                ><a
                  v-if="postmoreIndex(postmore, index)"
                  class="more"
                  href="javascript:;"
                  @click="searchmore(post.lid, index)"
                  >查看更多</a
                ></el-row
              >
            </div>
          </el-container>
        </el-container>
      </el-card>
    </el-container>
  </main-layout>
</template>
<style src="../css/font/iconfont.css"></style>
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
a[class="button"] {
  color: #555;
  text-decoration: none;
  padding: 0 10px;
}
.topBackground {
  background-image: url("../img/论坛背景.jpg");
  background-size: 100% 100%;
  height: 10em;
  width: 100%;
}
img.common {
  width: 70px;
  height: 70px;
}
.el-container {
  display: block;
}
.el-row {
  display: block;
  float: right;
}
.clearfix:after {
  content: "";
  display: block;
  clear: both;
  visibility: hidden;
}

.user {
  font-size: 25px;
  color: blue;
  margin-bottom: 15px;
}
.user .icon {
  background-color: rgb(228, 91, 91);
  color: black;
  padding: 4px;
  margin-left: 12px;
}

.replyposts {
  margin-left: 7%;
  font-size: 18px;
  margin-bottom: 15px;
}
.replypost {
  margin-bottom: 15px;
  padding-bottom: 2em;
  border-bottom: 1px solid #aaa;
}
.replypost > .el-container {
  margin-bottom: 15px;
}
.replypost-reply {
  padding-bottom: 30px;
}
.innerpost {
  margin-left: 5%;
  margin-bottom: 25px;
  /* border-top: 2px solid black; */
}
/* 以下是输入框样式 */
.maininput {
  width: 75%;
}
.replyinput {
  width: 80%;
}
.icons-box {
  margin-top: 5px;
}
.more {
  text-decoration: none;
}
.loginButton {
  margin-top: 2em;
  float: right;
  margin-right: 4em;
}
.interPost {
  margin-top: 1em;
  margin-bottom: 3em;
}
a {
  text-decoration: none;
}
a.button > span {
  color: "#aaa";
  font-size: 24px;
}
</style>
<script>
import mainLayout from "../components/main-layout";
import { getPayload } from "../jsmodule/token";
export default {
  data() {
    return {
      isLogin: false,
      pid: 0,
      pages: 0,
      nowpage: 1,
      postData: {
        post: {
          good: false,
          pid: 1,
          postPraise: 3,
          postReplies: "",
          postSection: "",
          postTag: "",
          postTime: "",
          postTitle: "",
          postContent: "",
          uname: "",
        },
        postLayer: [
          {
            uid: 1,
            uname: "ss",
            floor: 3,
            content: "",
            replyTime: "",
            interLayerCount: 0,
            interlayers: [
              {
                content: "",
                replyTime: "",
                replyUid: 1,
                replyUname: "",
                uid: 1,
                uname: "",
              },
              {
                user: {
                  name: "flashxiaomang111",
                  img: "/ui/favicon.ico",
                },
                replyContent: "信息1的楼中楼2",
              },
              {
                user: {
                  name: "flashxiaomang111",
                  img: "/ui/favicon.ico",
                },
                replyContent: "信息1的楼中楼2",
              },
            ],
          },
          {
            replyContent: "回复的信息1",
            user: {
              name: "flashxiaomang111",
              img: "/ui/favicon.ico",
            },
            interLayer: [
              {
                user: {
                  name: "flashxiaomang111",
                  img: "/ui/favicon.ico",
                },
                replyContent: "信息1的楼中楼1",
              },
              {
                user: {
                  name: "flashxiaomang111",
                  img: "/ui/favicon.ico",
                },
                replyContent: "信息1的楼中楼2",
              },
            ],
          },
        ],
      },
      yourReply: "",
      isMainInputFocused: false,
      isReplyFocused: [true, false],
      replyReply: [],
      isReplyIconsOpen: [],
      isReplySectionShowing: [],
      //举报
      dialogVisible: false,
      accusationContent: "",
      //查看更多
      postmore: [],
      //是否已经点赞
      isPraised: false,
      //该用户id
      uid: 0,
      //举报回帖
      layerAccusationDialogVisible: false,
      layerDialogAccusationContent: "",
      //记录楼中楼的“举报”是否打开的数组
      interlayersAccusationIconShow: [[]],
      interlayerAccusationDialogVisible: false,
      interlayerAccusationContent: "",
      pidGet: 0,
      //当前是否是只显示置顶帖子
      isShowTop: false,
    };
  },
  computed: {
    path() {
      return this.$route.path;
    },
    timeShow() {
      return function (timeRaw) {
        if (timeRaw == undefined) {
          return timeRaw;
        } else {
          return timeRaw.slice(0, 10);
        }
      };
    },
    postmoreIndex() {
      return function (postmore, index) {
        if (postmore == undefined) {
          return postmore;
        } else {
          return postmore[index];
        }
      };
    },
  },
  mounted() {
    if (getPayload() == undefined) {
      console.log("nnn");
    } else {
      console.log(getPayload(), getPayload().userId);
      console.log("uuuuuu");
    }
    if (localStorage.getItem("user-token") != null) {
      this.isLogin = true;
    }
    console.log(this.$route.params.id);
    let tidGet = this.$route.query.id;
    // let sidGet=this.$route.query.sid;
    // let tidGet = 1;

    // console.log('ceshi',tidGet,sidGet);
    this.loadData(tidGet);
    if (getPayload() != undefined) {
      // this.uid=getPayload(), getPayload().userId;
      this.uid = getPayload().userId;
    }
  },
  methods: {
    test() {
      this.interlayersAccusationIconShow[0][0] = true;
    },
    jumpToSection() {
      history.back();
      // history.go(-1);
    },
    jumpToLogin() {
      this.$router.push("/guide");
    },
    loadData(pidGet) {
      //用于测试
      pidGet = 1;
      this.pidGet = pidGet;
      this.$axiosBase.get(`/posts/${pidGet}?page=1&pageSize=10`).then((res) => {
        let dataGet = res.data.data;
        this.pid = dataGet.simpleRetVo.pid;
        this.postData.post = dataGet.simpleRetVo;
        this.postData.postLayer = dataGet.layerRetVoList;
        console.log(this.postData);
        this.isReplySectionShowing = new Array(this.postData.postLayer.length);
        this.isReplySectionShowing.fill(false);
        this.isReplyIconsOpen = new Array(this.postData.postLayer.length);
        this.isReplyIconsOpen.fill(this.postData.postLayer.length);
        this.replyReply = new Array(this.postData.postLayer.length);
        this.replyReply.fill("");
        this.praisecheck();
        this.postmore = new Array(this.postData.postLayer.length);
        this.postmore.fill(true);
        this.interlayersAccusationIconShow = new Array(
          this.postData.postLayer.length
        );
        for (let i = 0; i < this.postData.postLayer.length; i++) {
          this.interlayersAccusationIconShow[i] = new Array(
            this.postData.postLayer[i].interlayers.length
          );
          this.interlayersAccusationIconShow[i].fill(false);
        }
      });
      // this.isReplyFocused = new Array(5);
      // this.isReplyFocused.fill(false);
      // this.replyReply = new Array(5);
      // this.replyReply.fill("");
      this.$axiosBase.get(`/posts/${pidGet}/pages`).then((res) => {
        console.log(res.data.data);
        this.pages = res.data.data;
        console.log("pages", this.pages);
      });
    },
    handleCurrentChange(val) {
      console.log("val", val);
      this.$axiosBase
        .get(`/posts/${this.pidGet}?page=${val}&pageSize=10`)
        .then((res) => {
          let dataGet = res.data.data;
          this.pid = dataGet.simpleRetVo.pid;
          this.postData.post = dataGet.simpleRetVo;
          this.postData.postLayer = dataGet.layerRetVoList;
          console.log(this.postData);
          this.isReplySectionShowing = new Array(
            this.postData.postLayer.length
          );
          this.isReplySectionShowing.fill(false);
          this.isReplyIconsOpen = new Array(this.postData.postLayer.length);
          this.isReplyIconsOpen.fill(this.postData.postLayer.length);
          this.replyReply = new Array(this.postData.postLayer.length);
          this.replyReply.fill("");
          this.praisecheck();
          this.postmore = new Array(this.postData.postLayer.length);
          this.postmore.fill(true);
          this.interlayersAccusationIconShow = new Array(
            this.postData.postLayer.length
          );
          for (let i = 0; i < this.postData.postLayer.length; i++) {
            this.interlayersAccusationIconShow[i] = new Array(
              this.postData.postLayer[i].interlayers.length
            );
            this.interlayersAccusationIconShow[i].fill(false);
          }
        });
    },
    mainfocus() {
      this.isMainInputFocused = true;
    },
    mainblur() {
      this.isMainInputFocused = false;
    },
    showIcon(index) {
      this.isReplyFocused[index] = true;
    },
    hideIcon(index) {
      this.isReplyFocused[index] = false;
    },
    showReplySection(index) {
      // 如果输入框还没有显示
      this.isReplySectionShowing[index] = !this.isReplySectionShowing[index];
    },
    replyReplyFunc(lid, index, uid) {
      if (this.replyReply[index] == "") {
        this.$message("请输入内容");
        return;
      }

      this.$axiosLogin
        .post(`/posts/${this.pid}/layers/${lid}/interlayers`, {
          replyTo: uid,
          content: this.replyReply[index],
        })
        .then((res) => {
          this.isReplySectionShowing[index] = false;

          location.reload();
          return res;
        })
        .catch((err) => {
          this.$message(err.response.data.errmsg);
        });
    },
    replyToMainBlock() {
      this.$axiosLogin
        .post(`/posts/${this.pid}/layers`, {
          content: this.yourReply,
        })
        .then((res) => {
          location.reload();
          return res;
        })
        .catch((err) => {
          this.$message(err.response.data.errmsg);
        });
    },
    accusation() {
      this.dialogVisible = false;
      this.$axiosLogin
        .post(`accusations/posts/${this.pid}`, {
          reason: this.accusationContent,
        })
        .then((res) => {
          this.$message("举报成功");
          return res;
        })
        .catch((err) => {
          if (err.response.data.errors == undefined) {
            this.$message("请先登录");
          } else {
            this.$message(err.response.data.errors[0].defaultMessage);
          }
        });
    },
    praisecheck() {
      console.log("che");
      this.$axiosLogin.get(`posts/${this.pid}/praise/self`).then((res) => {
        if (res.data.data) {
          console.log("falseff");
        }
        this.isPraised = res.data.data;
        return res;
      });
    },
    praise() {
      this.$axiosLogin.put(`posts/${this.pid}/praise`).then((res) => {
        location.reload();
        return res;
      });
    },
    searchmore(lid, index) {
      this.$axiosBase.get(`posts/${this.pid}/layers/${lid}`).then((res) => {
        console.log(res);
        let dataGet = res.data.data;
        this.postData.postLayer[index].interlayers = dataGet;
        this.postmore[index] = false;
      });
    },
    deleteInterReply(iid) {
      this.$axiosLogin.delete(`/users/self/interlayers/${iid}`).then((res) => {
        location.reload();
        return res;
      });
    },
    deletePost(lid) {
      this.$axiosLogin.delete(`/users/self/layers/${lid}`).then((res) => {
        console.log(res);
        location.reload();
      });
    },
    accusationLayerCommit(lid) {
      if (this.layerDialogAccusationContent == "") {
        this.$message("举报内容不能为空");
        return;
      }
      this.$axiosLogin
        .post(`/accusations/layers/${lid}`, {
          reason: this.layerDialogAccusationContent,
        })
        .then((res) => {
          this.layerAccusationDialogVisible = false;
          return res;
        });
    },
    accusationInterlayerCommit(indexOuter, indexInter, iid) {
      if (this.interlayerAccusationContent == "") {
        this.$message("请填写举报信息");
        return;
      }
      this.$axiosLogin
        .post(`/accusations/interlayers/${iid}`, {
          reason: this.interlayerAccusationContent,
        })
        .then((res) => {
          console.log(res);
          this.$message("举报成功");
          location.reload();
        })
        .catch((err) => {
          console.log(this.$message(err.response.data.errmsg));
        });
      this.interlayerAccusationDialogVisible = false;
      this.interlayersAccusationIconShow[indexOuter][indexInter] = false;
    },
    collect() {
      this.$axiosLogin
        .get(`/users/self/favourate/${this.pid}`)
        .then((res) => {
          this.$message("收藏成功");
          console.log(res);
          return res;
        })
        .catch((err) => {
          this.$message("请先登录");
          return err;
        });
    },
    jumpToUser(uid) {
      this.$router.push(`/userhome/visit/${uid}`);
    },
  },
  components: {
    mainLayout,
  },
};
</script>
