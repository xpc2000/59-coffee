import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../components/Login.vue"
import ShopHome from '../components/ShopHome'
import GuestHome from '../components/GuestHome'
import Message from '../views/Setting/Message'
import Choice from '../views/Choice'
import Cart from '../views/CartMenu/Cart'
import Classification from '../views/foodClass/Classification'
import Setting from "../views/Setting"
import Favorites from "../views/Setting/Favorites"
import Order from "../views/Setting/Order"
import Drink from "../views/foodClass/Drink"
import Snack from "../views/foodClass/Snack"
import Settlement from "../views/CartMenu/Settlement"
import Stock from '../views/Stock/Stock.vue';
import UpStock from '../views/Stock/UpStock.vue';
import ShopOrder from '../views/ShopOrder/ShopOrder.vue'
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
          /* 客户 */
          {
              path: "/GuestHome",
              component: GuestHome,
              redirect: "/choice",
              children:[
                  {path: "/choice", component: Choice},
                  {path: "/classification", component: Classification, redirect: "/classification/drink",
                      children:[
                          {path: "/classification/drink", component: Drink},
                          {path: "/classification/snack", component: Snack},
                      ]
                  },
                  {path: "/cart", component: Cart},
                  {path: "/setting", component: Setting},
              ]
          },
          {
            path: "/settlement",
            component: Settlement,
          },
          {
            path: "/message",
            component: Message,
          },
          {
              path: "/order",
              component: Order,
          },
          {
              path: "/favorites",
              component: Favorites,
          },

          /* 门店 */
          {
              path: "/ShopHome",
              component: ShopHome,
              children:[
                  {path:"/Stock/Stock",component:Stock},
                  {path:"/Stock/UpStock",component:UpStock},
                  {path:"/ShopOrder/ShopOrder",component:ShopOrder}
              ]
          },
      ]

const router = new VueRouter({
  routes
})

// // 挂载路由导航守卫
// router.beforeEach((to, from, next)=>{
//
// })

export default router
