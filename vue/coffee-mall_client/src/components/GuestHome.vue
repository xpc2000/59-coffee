<template>
    <el-container class="home-container">
        <el-main>
            <router-view></router-view>
        </el-main>

        <!-- 底部导航栏 -->
        <el-footer>
            <el-menu
                    default-active="/Message"
                    class="el-menu-demo"
                    mode="horizontal"
                    background-color="#545c64"
                    text-color="#fff"
                    active-text-color="#ffd04b">
                <el-menu-item index="1" >精选</el-menu-item>
                <el-menu-item index="2" >分类</el-menu-item>
                <el-menu-item index="3">购物车</el-menu-item>
                <el-menu-item index="/Message">我的信息</el-menu-item>
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
        background-color: #999999;
        display: flex;
        justify-content: space-around;
        align-items: center;

        /* 导航栏样式 */
        .el-menu-demo
        {
            border-top: none;
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