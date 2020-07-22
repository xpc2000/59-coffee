<template>
    <el-container class="home-container">

        <!-- 头部 -->
        <el-header>
            <div>
                <img src="../assets/logo.jpg" alt/>
                <span style="color: #ffffff">门店管理界面</span>
            </div>
            <el-button  @click="logout" style="color: #ffffff" type="primary"><b>安全退出</b></el-button>
        </el-header>

        <!-- 主体 -->
        <el-container>

            <!-- 侧边栏 -->
            <el-aside class="el-color-alpha-slider__bar" :width="isCollapse?'64px':'200px'" >
                <div class="toggle-button" @click="toggleCollapse" style="color: #000000">|||</div>
                <el-menu  background-color="#F5F5F5" text-color="#000000" active-text-color="#409eff"
                         :collapse="isCollapse" :collapse-transition="false" :router="true" :default-active="$route.path">
                    <el-menu-item index="/welcome">
                        <i class="iconfont icon-shouye"></i>
                        <span slot="title"> 首页</span>
                    </el-menu-item>
                    <el-submenu index="stock">
                        <template slot="title">
                            <i class="iconfont icon-mendian"></i>
                            <span> 库存管理</span>
                        </template>
                            <el-menu-item index="/Stock/Stock"><i class="iconfont icon-denglu"></i> 菜单库存管理</el-menu-item>
                            <el-menu-item index="/Stock/UpStock"><i class="iconfont icon-denglu"></i> 上架菜品</el-menu-item>
                            <el-menu-item index="/Stock/BuyFood"><i class="iconfont icon-denglu"></i> 进货</el-menu-item>
                    </el-submenu>
                    
                    <el-submenu index="order">
                        <template slot="title">
                            <i class="iconfont icon-icon-"></i>
                            <span> 订单管理</span>
                        </template>
                        <el-menu-item index="/ShopOrder/ShopOrder"><i class="iconfont icon-chazhao"></i> 查看订单详情</el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>

            <!-- 主体 -->
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    export default {
        data()
        {
            return{
                isCollapse: false,
            }
        },
        methods:
            {
                /* 安全退出 */
                logout()
                {
                    window.sessionStorage.clear();   // 清除session
                    this.$router.push("/login");   // 回到首页
                },

                /* 控制伸缩 */
                toggleCollapse()
                {
                    this.isCollapse = !this.isCollapse;
                },
            }
    }
</script>

<style lang="less" scoped>

    /* 布局样式 */
    .home-container
    {
        height: 100%;
    }

    /* 头部样式 */
    .el-header
    {
        background-color: #000000;
        display: flex;
        justify-content: space-between;
        padding-left: 0%;
        align-items: center;
        color: #fff;
        font-size: 20px;
        div
        {
            display: flex;
            align-items: center;
            span
            {
                margin-left: 15px;
            }
        }
    }

    /* 侧边样式 */
    .el-aside
    {
        background-color: #DCDCDC;
        .el-menu
        {
            border-right: none;
        }
    }

    /* 主体样式 */
    .el-main
    {
        background-color: #eaedf1;
    }

    img {
        width: 55px;
        height: 55px;
    }
    // 伸缩按钮样式
    .toggle-button
    {
        background-color: #DCDCDC;
        font-size: 10px;
        line-height: 24px;
        color: #fff;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer;
    }
</style>