<template>
  <div class="guide-container">
    <div class="background">
      <!-- 背景层，独立div解决了白边问题 -->
      <div></div>
    </div>

    <div class="full-box" v-if="successType">
      <!-- <el-progress class="progress-bar" :percentage="100" :show-text="false" :indeterminate="true" :duration="3"></el-progress> -->

      <el-progress type="dashboard" :percentage="successPercentage" status="success" width="250" color="#67C23A">
        <template #default="">
          <el-result icon="success" :title="successTitle" :subTitle="subTitle"></el-result>
        </template>
      </el-progress>
    </div>

    <div class="center-box" v-if="!successType">
      <div class="form-div" v-if="!signupPage">
        <h1>MingQ 登录</h1>
        <el-form :model="loginForm" label-width="4em" :rules="loginRules" ref="loginForm">
          <el-form-item label="账 户" prop="signinName">
            <el-input v-model="loginForm.signinName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-radio-group v-model="loginForm.signinType" size="small" :disabled="admin">
              <el-radio-button :label="1">用户名</el-radio-button>
              <el-radio-button :label="2">手机号</el-radio-button>
              <el-radio-button :label="3">邮箱</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="密 码" prop="password">
            <el-input v-model="loginForm.password" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item label="管 理">
            <el-switch v-model="admin"></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button v-if="!admin" type="primary" size="small" @click="login()">登录</el-button>
            <el-button v-if="admin" type="primary" size="small" @click="adminLogin()">管理员登录</el-button>
            <el-button size="small" @click="signupPage = true">注册</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="form-div signup-form" v-if="signupPage">
        <h1>MingQ 注册</h1>
        <el-form :model="signupForm" label-width="4em" :rules="signupRules" ref="signupForm">
          <el-form-item label="账 号" prop="username">
            <el-input v-model="signupForm.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密 码" prop="password">
            <el-input v-model="signupForm.password" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item label="昵 称" prop="nickname">
            <el-input v-model="signupForm.nickname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮 箱" prop="email">
            <el-input v-model="signupForm.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="电 话" prop="phone">
            <el-input v-model="signupForm.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="signup">注册</el-button>
            <el-button size="small" @click="signupPage = false">返回</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import originaxios from "axios";
import { saveToken } from "../jsmodule/token";

function autoheight() {
  let winHeight = window.innerHeight;

  let el = document.getElementsByClassName("guide-container");
  if (el.length > 0) {
    el[0].style.height = winHeight + "px";
  }
}

const axios = originaxios.create({
  baseURL: "http://localhost:8888",
  timeout: 1000,
});

let loginRules = {
  signinName: [
    { required: true, message: "请输入登录名", trigger: "change" },
    { whitespace: false, message: "不允许有空格", trigger: "change" },
    { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "change" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "change" },
    { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "change" },
  ],
};

let signupRules = {
  username: [
    { required: true, message: "请输入用户名", trigger: "change" },
    { whitespace: false, message: "不允许有空格", trigger: "change" },
    { min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "change" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "change" },
    { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "change" },
  ],
  nickname: [
    { required: true, message: "请输入昵称", trigger: "change" },
    { whitespace: false, message: "不允许有空格", trigger: "change" },
    { min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "change" },
  ],
  email: [
    { required: true, message: "请输入邮箱", trigger: "change" },
    { type: "email", message: "请输入正确的邮箱", trigger: "change" },
  ],
  phone: [
    { required: true, message: "请输入电话", trigger: "change" },
    { min: 11, max: 11, message: "电话长度必须是11位", trigger: "change" },
  ],
};

//TODO 完成页面功能
//(Done By App.vue)刚进入本页面 检查是否已经登录，是的话直接跳转至相应的页面
//(√) 普通用户登录成功 清空表单，3秒后跳转主页
//(√) 管理员登录成功 清空表单，3秒后跳转后台管理页
//( ) 注册成功 清空表单，3秒后进入登录页，并填入用户名
export default {
  data() {
    return {
      admin: true,
      signupPage: false,
      successType: 0, //0 登录/注册页面，1 普通用户登录成功，2 管理员登录成功，3 注册成功
      successPercentage: 0,
      waitSeconds: 1,

      // 字段校验规则
      loginRules: loginRules,
      signupRules: signupRules,

      loginForm: {
        signinName: "",
        password: "",
        signinType: 1, //1用户名 2手机 3邮箱
      },

      signupForm: {
        username: "",
        password: "",
        nickname: "",
        email: "",
        phone: "",
      },
    };
  },
  computed: {
    successTitle() {
      switch (this.successType) {
        case 1:
          return "普通用户登录成功";
        case 2:
          return "管理员登录成功";
        case 3:
          return "注册成功";
        default:
          return "默认成功";
      }
    },
    subTitle() {
      return this.waitSeconds + "秒后跳转";
    },
  },
  watch: {
    successType() {
      if (this.successType) {
        this.successPercentage = 0;
      }
    },
  },
  methods: {
    goBack() {
      //回退并且刷新页面
      this.$router.go(-1);
    },

    login() {
      this.$refs.loginForm.validate((valid) => {
        if (!valid) {
          this.$message.warning("请完善表单");
          return;
        }
        //signin type 1用户名 2手机 3邮箱
        axios
          .post("/users/signin", { ...this.loginForm })
          .then((res) => {
            // 设置好vuex并准备跳转
            saveToken(res.data.data.token);
            this.$store.commit("setByLoginVo", res.data.data);
            this.successType = 1;

            // 延时跳转
            setTimeout(() => {
              this.$router.push("/index");
              this.successType = 0;
            }, this.waitSeconds * 1000);
          })
          .catch((e) => {
            this.$solve(e);
          });
      });
    },

    adminLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (!valid) {
          this.$message.warning("请完善表单");
          return;
        }
        axios
          .post("/admins/signin", { adminName: this.loginForm.signinName, password: this.loginForm.password })
          .then((res) => {
            // 设置好vuex并准备跳转
            saveToken(res.data.data.token);
            this.$store.commit("setAdmin");
            this.successType = 2;

            // 延时跳转
            setTimeout(() => {
              this.$router.push("/manage/admin");
              this.successType = 0;
            }, this.waitSeconds * 1000);
          })
          .catch((e) => {
            this.$solve(e, "用户名或密码错误");
          });
      });
    },

    signup() {
      this.$refs.signupForm.validate((valid) => {
        // 先本地校验一次
        if (!valid) {
          this.$message.warning("请完善表单");
          return;
        }

        // 远程校验
        let all = [];
        let args = [
          { url: "/users/signup/check/username", param: { username: this.signupForm.username }, message: "用户名重复或者不合法" },
          { url: "/users/signup/check/email", param: { email: this.signupForm.email }, message: "邮箱重复或者不合法" },
          { url: "/users/signup/check/phone", param: { phone: this.signupForm.phone }, message: "电话重复或者不合法" },
        ];
        for (let arg of args) {
          all.push(
            new Promise((resolve, reject) => {
              axios
                .post(arg.url, arg.param)
                .then((res) => {
                  console.log(arg.param, res);
                  if (res.data.data.available) {
                    resolve(1);
                  } else {
                    this.$message.warning(arg.message);
                    reject(2);
                  }
                })
                .catch((e) => {
                  this.$solve(e, arg.message);
                  reject(2);
                });
            })
          );
        }
        Promise.all(all)
          .then(() => {
            // 正式提交
            axios
              .post("/users/signup", { ...this.signupForm })
              .then(() => {
                this.successType = 3;
                this.loginForm.signinName = this.signupForm.username;

                // 延时跳转
                setTimeout(() => {
                  this.successType = 0;
                  this.signupPage = false;
                }, this.waitSeconds * 1000);
              })
              .catch((e) => {
                console.log(e.response);
                this.$solve(e);
              });
          })
          .catch(() => {});
      });
    },
  },
  mounted() {
    autoheight();
    window.addEventListener("resize", autoheight);

    setInterval(() => {
      if (this.successPercentage < 100) {
        this.successPercentage += 100;
      }
    }, 100);
  },
};
</script>

<style scoped>
.guide-container {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: auto;
}

.background {
  background-color: black;
  overflow: hidden;
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -2;
}

.background > div {
  background-image: url("../img/guide-bg.jpg");
  background-repeat: repeat;
  filter: blur(10px);
  height: 110%;
  width: 110%;
  position: relative;
  top: -5%;
  left: -5%;
  z-index: -1;
}

.full-box {
  width: 100%;
  height: 100%;
  background-color: white;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.full-box > * {
  max-width: 40em;
}

.progress-bar {
  min-height: 1em;
  min-width: 30em;
}

.center-box {
  background-color: white;
  background-image: url("../img/guide-bg.jpg");
  background-repeat: repeat;
  background-size: auto 100%;

  display: flex;
  justify-content: right;
  padding-left: 40em;

  border-radius: 0.3em;
  overflow: hidden;
}

@media (max-width: 1025px) {
  .center-box {
    padding-left: 0em;
    min-width: 400px;
  }
}

.form-div {
  min-height: 20em;
  background-color: white;
  padding: 1em 4em;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.form-div > h1 {
  margin-bottom: 1.5em;
  color: rgb(53, 53, 53);
}

.signup-form {
  padding: 1em 6em;
}
</style>
