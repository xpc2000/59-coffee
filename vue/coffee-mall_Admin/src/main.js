import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'   // 全局样式
import  './assets/font/iconfont.css'   // 引入iconfont

import axios from 'axios'// 导入axios跨域

Vue.prototype.$http = axios   // 挂在axios
axios.defaults.baseURL = "http://localhost:9000"   // 设置访问根路径

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
