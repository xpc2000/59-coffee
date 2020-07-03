import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../components/Login.vue"
import Home from '../components/Home.vue'
import Welcome from "../views/Welcome"
import Maintain from "../views/Maintain"
import Account from "../views/store/Account"
import Stream from "../views/store/Stream"
import Discount from "../views/food/Discount"
import Search from "../views/food/Search"

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

  const routes =
      [
          {
          path: "/",
          redirect: "/login"
          },
          {
          path: "/login",
          component: Login
          },
          {
              path: "/home",
              component: Home,
              redirect: "/welcome",
              children:[
                  {path: "/welcome", component: Welcome},
                  {path: "/store/account", component: Account},
                  {path: "/store/stream", component: Stream},
                  {path: "/food/discount", component: Discount},
                  {path: "/food/search", component: Search},
                  {path: "/maintain", component: Maintain},
              ]
          }
      ]

const router = new VueRouter({
  routes
})

// // 挂载路由导航守卫
// router.beforeEach((to, from, next)=>{
//
// })

export default router
