/**
 * 该模块核心功能：
 * 		设置与获取token
 * 		通过已有的token初始化vuex
 */

// import { ElMessage } from "element-plus";
import { decode } from "js-base64";

export function saveToken(token) {
  localStorage.setItem("user-token", token);
}

export function getToken() {
  let token = localStorage.getItem("user-token");
  return token ? token : "";
}

export function clearToken() {
  localStorage.setItem("user-token", "");
}

export function getPayload() {
  let token = getToken();
  if (!token) {
    return undefined;
  }
  let s = token
    .split(".")[1]
    .replaceAll("-", "+")
    .replaceAll("_", "/");
  let payload = JSON.parse(decode(s));
  if (!payload) {
    return undefined;
  }
  if (new Date(payload.exp * 1000) <= new Date()) {
    clearToken();
    return undefined;
  }

  return payload;
}

// 该函数——初始化登录状态
// 如果未登录则返回false
// 如果登录的是普通用户，设置vuex基本信息和黑名单列表
// 如果是管理员，设置vuex.userType='admin'
export async function initLoginStatus(vue) {
  return new Promise((resolve, reject) => {
    let payload = getPayload();
    if (!payload || !payload.roleId) {
      reject(false);
      return;
    }

    if (payload.roleId == 1 || payload.roleId == 2) {
      vue.$store.commit("setAdmin");
      resolve("adminUser");
      return;
    }

    // 普通用户
    vue
      .$axiosLogin("/users/self")
      .then((res) => {
        vue.$store.commit("setByUserRetVo", res.data.data);
      })
      .catch((e) => vue.$solve(e, "获取用户信息失败"));
    vue
      .$axiosLogin("/users/self/blacklist")
      .then((res) => {
        vue.$store.commit("setBlackMap", res.data.data);
      })
      .catch((e) => vue.$solve(e, "获取用户黑名单失败"));
    resolve("normalUser");
  });
}
