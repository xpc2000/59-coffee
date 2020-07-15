<template>
    <el-container class="home-container">
        <el-main>
            <router-view></router-view>
        </el-main>

        <!-- 底部导航栏 -->
        <el-footer>
            <el-menu
                    mode="horizontal"
                    background-color="#F5F5F5"
                    text-color="#999999"
                    active-text-color="#ff0000"
                    :default-active="this.$route.path"
                    :router="true">
                <el-menu-item index="/choice" > 精选 <i class="iconfont icon-Discount"></i></el-menu-item>
                <el-menu-item index="/classification" > 分类 <i class="iconfont icon-type"></i></el-menu-item>
                <el-menu-item index="/cart"> 购物车 <i class="iconfont icon-gouwuche"></i></el-menu-item>
                <el-menu-item index="/setting"> 我的 <i class="iconfont icon-denglu"></i></el-menu-item>
            </el-menu>
        </el-footer>

    </el-container>
</template>

<script>

</script>

<style lang="less" scoped>

    /* 布局样式 */
    .home-container
    {
        height: 100%;
    }

    /* 底部样式 */
    .el-footer
    {
        background-color: #F7F7F7;
        display: flex;
        justify-content: space-around;
        align-items: center;

        /* 导航栏样式 */
        .el-menu
        {
            border-top: none;
            .el-menu-item
            {
                width: 369px;
                text-align: center;
                font-size: 16px;
            }
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


</style>