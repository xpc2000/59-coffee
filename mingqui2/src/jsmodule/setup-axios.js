import axios from "axios";
import { getToken } from "./token";
import { ElMessage } from "element-plus";

const axiosConfig = {
  baseURL: "http://localhost:8888",
  timeout: 1000,
};

const axiosBase = axios.create(axiosConfig);

const axiosLogin = axios.create(axiosConfig);

/**
 * e.response 的重要字段:
		config:
			baseURL: "http://localhost:8888"
			data: "{'username':'','password':''}"
			headers: {Accept: "...", Content-Type: "application/json;charset=utf-8"}
			method: "post"
			timeout: 1000
			url: "/users/signin"
			...
		data: {errno: 503, errmsg: "必须输入用户名;必须输入密码;"}
		headers: {content-type: "application/json;charset=UTF-8"}
		request: {...}
		status: 403
		statusText: ""
 */
const solve = (e, msg) => {
  if (!(e.response && e.response.data)) {
    // 不是后端返回的错误
    ElMessage.error("JS错误，请查看Console");
    console.log(e);
    return;
  }
  if (e.response.data.errmsg && e.response.data.errmsg.length < 50) {
    ElMessage.warning(e.response.data.errmsg);
  } else if (msg) {
    ElMessage.warning(msg);
  } else {
    ElMessage.warning("操作失败");
  }
};

const solveAuthorization = () => {
  //取消token更新
  // if (response.headers.authorization) {
  //   console.log("setup-axios: 成功获取了新Token", response.headers);
  //   saveToken(response.headers.authorization);
  // }
};

// 错误处理函数，也要检查token
const errorHandler = (e) => {
  if (e.response) {
    solveAuthorization(e.response);
  }
  return Promise.reject(e);
};

// 每个登录请求需要使用token
axiosLogin.interceptors.request.use((request) => {
  request.headers["Authorization"] = getToken();
  return request;
}, errorHandler);

// 每个响应需要检查新token
axiosLogin.interceptors.response.use((response) => {
  solveAuthorization(response);
  return response;
}, errorHandler);

// 导出为vue插件
export default {
  install: (Vue) => {
    Vue.mixin({
      beforeCreate() {
        this.$axios = axios;
        this.$axiosBase = axiosBase;
        this.$axiosLogin = axiosLogin;
        this.$solve = solve;
      },
    });
  },
};
