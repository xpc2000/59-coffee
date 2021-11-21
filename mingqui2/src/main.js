/* 外部依赖 */
import { createApp } from "vue";
import { createRouter, createWebHashHistory } from "vue-router";
import locale from "element-plus/lib/locale/lang/zh-cn";
import ElementPlus from "element-plus";
import "dayjs/locale/zh-cn";
import "element-plus/lib/theme-chalk/index.css";

/* 内部依赖 */
import App from "./App.vue";
import { store } from "./vuex/vuex";
import setupAxios from "./jsmodule/setup-axios";
import { getPayload } from "./jsmodule/token";

/* 页面 */
import index from "./pages/index";
import section from "./pages/section";
import post from "./pages/post";
import userhome from "./pages/userhome";
import manage from "./pages/manage";
import guide from "./pages/guide";

let router = createRouter({
  history: createWebHashHistory(),
  base: "/ui/",
  routes: [
    { path: "/", redirect: "/index" },
    { path: "/index", component: index },
    { path: "/section/:id", component: section },
    { path: "/section", redirect: "/section/1" },
    { path: "/post/:id", component: post },
    { path: "/post", redirect: "/post/1" },
    { path: "/userhome", component: userhome, children: userhome.routeChildren },
    { path: "/manage", component: manage, children: manage.routeChildren },
    { path: "/guide", component: guide },
  ],
});

router.beforeEach((to) => {
  let payload = getPayload();
  let path = to.fullPath;

  // 未登录
  if (!payload || !payload.roleId) {
    if (path.indexOf("/manage") == 0 || path.indexOf("/userhome") == 0) {
      // 未登录且处于游客禁入区域
      console.log("未登录的权限限制区域，跳转至登录页");
      return "/guide";
    }
    return true;
  }

  // 管理员
  if (payload.roleId == 1 || payload.roleId == 2) {
    // 已登录的管理员只能访问后台
    if (path.indexOf("/manage") != 0) {
      console.log("已登录的管理员只能访问后台，跳转至后台");
      return "/manage";
    }
    return true;
  }

  // 普通用户
  // 已登录的普通用户不能呆在登录页
  if (path.indexOf("/guide") == 0) {
    console.log("已登录的普通用户不能呆在登录页，跳转至主页");
    return "/index";
  }
  if (path.indexOf("/manage") == 0) {
    console.log("已登录的普通用户禁止进入后台，跳转至主页");
    return "/index";
  }

  return true;
});

const app = createApp(App);
app.use(router);
app.use(ElementPlus, { locale });
app.use(store);
app.use(setupAxios);
app.mount("#app");
