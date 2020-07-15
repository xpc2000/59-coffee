<template>
    <div>
        <div class="OutBtn">
            <el-button  @click="logout()" style="color: #ffffff" type="primary"><b>安全退出</b></el-button>
        </div>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <div class = "msg_container">
            <div class = "msg_box">

                <!-- logo区 -->
                <div class = "avatar_box">
                    <img src=".././assets/logo.jpg" alt/>
                </div>

                <h3 class="title" v-text="username"></h3>

                <!-- 菜单区域 -->
                <div>
                <el-menu
                        class="msg_menu"
                        background-color="#F5F5F5"
                        text-color="#999999"
                        active-text-color="#ff0000"
                        :default-active="this.$route.path"
                        :router="true">
                    <el-menu-item index="/message" ><i class="iconfont icon-denglu"></i> 信息修改 </el-menu-item>
                    <el-menu-item index="order" ><i class="iconfont icon-dingdan"></i> 我的订单 </el-menu-item>
                    <el-menu-item index="/favorites"><i class="iconfont icon-shoucangjia"> 我的收藏夹 </i></el-menu-item>
                    <el-menu-item index="/cart"><i class="iconfont icon-gouwuche"> 购物车 </i></el-menu-item>
                </el-menu>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default
    {
        data()
        {
            return{
                username:'',
            }
        },
        created() {
            if(window.sessionStorage.getItem("username") === null)
                this.username = '(测试阶段)未命名用户';
            else {
                this.username = window.sessionStorage.getItem("username");   // 获取 用户名
            }
        },

        methods:
            {
                /* 安全退出 */
                logout()
                {
                    this.$router.push({path: "/login"});
                    window.sessionStorage.clear();   // 清空session
                    this.$message.success("已安全退出");
                }
            }
    }
</script>

<style lang="less" scoped>
    .msg_container
    {
        width: 100%;
        height: 100%;
        background-repeat: no-repeat;
        background-position: center right;
        background-size: 100%;
    }
    .title
    {
        color: #505458;
        position: absolute;
        left: 50%;
        top: 15%;
        transform: translate(-50%, -50%);
    }
    .msg_box
    {
        width: 650px;
        height: 520px;
        background-color: #fff;
        border-radius: 10px;
        position: absolute;   // 绝对坐标
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        .avatar_box
        {
            width: 130px;
            height: 130px;
            border: 1px solid #eee;
            border-radius: 50%;
            padding: 5px;
            box-shadow: 0 0 1px #ddd;
            position: absolute;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #eee;
            img
            {
                width: 100%;
                height: 100%;
                border-radius: 50%;
                background-color: #eee;
            }
        }
    }
    .msg_menu
    {
        position: absolute;
        bottom: 13%;
        width: 100%;
        padding: 0 10px;
        box-sizing: border-box;
        .el-menu-item
        {
            height: 75px;
            text-align: center;
        }
    }
    .OutBtn
    {
        display: flex;
        justify-content: flex-end;
    }
</style>