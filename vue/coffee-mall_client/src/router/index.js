import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../components/Login.vue"
import ShopHome from '../components/ShopHome'
import GuestHome from '../components/GuestHome'

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
              path: "/ShopHome",
              component: ShopHome
          },
          {
              path: "/GuestHome",
              component: GuestHome
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
