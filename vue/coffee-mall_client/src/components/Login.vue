<template>
    <div class = "login_container">
        <div class = "login_box">

            <!-- logo区 -->
            <div class = "avatar_box">
                <img src="../assets/logo.jpg" alt/>
            </div>

            <h3 class="title">用户登录</h3>

            <!-- 表单区域 -->
            <el-form ref="loginFormRef"  :model="loginForm" :rules="loginRules" class="login_form"  label-width="0px">

                <!-- 用户名 -->
                <el-form-item prop="id">
                    <el-input v-model="loginForm.id" prefix-icon="iconfont icon-denglu" placeholder="账号"></el-input>
                </el-form-item>

                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="iconfont icon-mima" type="password" placeholder="密码"></el-input>
                </el-form-item>

                <!-- 选择器 -->
                <el-form-item prop="value">
                    <el-select  v-model="loginForm.value" placeholder="请选择用户类型">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>

                <!-- 按钮 -->
                <el-form-item class ="btns">
                    <el-button type="primary" @click="login">登录</el-button>
                    <el-button type="info" @click="resetLoginForm">重置</el-button>
                </el-form-item>

            </el-form>
        </div>
    </div>
</template>
<script>
export default
{
    data()
    {
        return{
            /* 表单对象 */
            loginForm:
            {
                id:'',
                password:'',
                value:'',
            },

            /* 验证对象 */
            loginRules:
                {
                    id: [
                        {required: true, message: '请输入账号', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ],
                    value:[
                        {required: true, message: '请选择类型', trigger: 'blur'},
                    ]
                },

            /* 选择器选项 */
            options: [{
                value: '客户',
                label: '客户'
            }, {
                value: '门店',
                label: '门店'
            }],
        };
    },
    methods:
    {
        /* 重置表单内容 按钮*/
        resetLoginForm()
        {
            this.$refs.loginFormRef.resetFields();
        },

        /* 登录 按钮 */
        login()
        {
            this.$refs.loginFormRef.validate(async valid =>
            {
                let url;
                if(!valid)
                     return;
                if(this.loginForm.value === "客户") {
                    url = "GuestLogin";
                }
                else {
                    url = "ShopAdminLogin"
                }
                const {data:res} = await this.$http.post(url, this.loginForm);
                if(res === "ok")
                {
                    this.$message.success("登录成功");   // 信息提示
                    if(this.loginForm.value === "客户")
                        await this.$router.push({path: "/GuestHome"});   // 页面路由跳转
                    else if(this.loginForm.value === "门店")
                        await this.$router.push({path: "/ShopHome"});   // 页面路由跳转
                }
                else
                {
                    this.$message.error("账号或者密码错误");   // 错误提示
                }
            })
        }
    },
}
</script>

<style lang ="less" scoped>
    .login_container
    {
        background-image: url(../assets/background.jpg);
        //background-color: #2b4b6b;
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
        top: 20%;
        transform: translate(-50%, -50%);
    }
    .login_box
    {
        width: 460px;
        height: 380px;
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
    .login_form
    {
        position: absolute;
        bottom: 0%;
        width: 100%;
        padding: 0 10px;
        box-sizing: border-box;
    }
    .btns
    {
        display: flex;   // 流式布局
        justify-content: flex-end;
    }
</style>