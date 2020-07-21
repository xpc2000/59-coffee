<template>
    <div class = "login_container">
        <el-dialog
                title="客户账号注册"
                :visible.sync="dialogVisible"
                width="30%">
            <el-form ref="editFormRef" label-width="80px" :model="editForm" :rules="rules">
                <el-form-item label="账号" prop="id"  required>
                    <el-input size="small" v-model="editForm.id" auto-complete="off" prefix-icon="iconfont icon-denglu" placeholder="请输入客户账号" clearable></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password"  required>
                    <el-input size="small" v-model="editForm.password" auto-complete="off" prefix-icon="iconfont icon-mima" type="password" placeholder="请输入门店密码"  show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="passwordConfirm"  required>
                    <el-input size="small" v-model="editForm.passwordConfirm" auto-complete="off" prefix-icon="iconfont icon-mima" type="password" placeholder="请输入门店密码"  show-password></el-input>
                </el-form-item>
                <el-form-item label="客户名称" prop="name"  required>
                    <el-input size="small" v-model="editForm.name" prefix-icon="iconfont icon-denglu" placeholder="请输入门店名称" clearable></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="phone"  required>
                    <el-input size="small" v-model="editForm.phone" prefix-icon="iconfont icon-lianxifangshi" placeholder="请输入联系方式" clearable></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address"  required>
                    <el-input size="small" v-model="editForm.address" prefix-icon="iconfont icon-dizhi" placeholder="请输入地址" clearable></el-input>
                </el-form-item>
                <el-form-item label="配送地址" prop="delivery_address"  required>
                    <el-input size="small" v-model="editForm.delivery_address" prefix-icon="iconfont icon-dizhi" placeholder="请输入配送地址" clearable></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm()">确 定</el-button>
            </div>
        </el-dialog>
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
                    <el-input v-model="loginForm.id" prefix-icon="iconfont icon-denglu" placeholder="账号" clearable></el-input>
                </el-form-item>

                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="iconfont icon-mima" type="password" placeholder="密码" show-password ></el-input>
                </el-form-item>

                <!-- 选择器 客户注册界面 -->
                <el-row prop="value" class="MyAdd">
                    <el-col :span="8">
                    <el-button ref="btnRegister" type="text" @click="dialogVisible = true">客户账号注册</el-button>
                    </el-col>

                    <el-col :span="5">
                        <div></div>
                    </el-col>

                    <el-col :span="15">
                    <el-select  v-model="loginForm.value" placeholder="请选择用户类型">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                    </el-col>
                </el-row>

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
            /* 注册界面 */
            RegisterName: '客户账号注册',
            dialogVisible: false,

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

            /* 编辑页面样式 */
            editForm: {
                id: '',
                password: '',
                passwordConfirm: '',
                name: '',
                phone: '',
                address: '',
                delivery_address: '',
            },

            /* rules表单验证 */
            rules: {
                id: [
                    { required: true, message: '请输入客户账号', trigger: 'blur' },
                    { required: true, message: '请输入客户账号', trigger: 'change' },
                ],
                password: [
                    { required: true, message: '请输入客户密码', trigger: 'blur' },
                    { required: true, message: '请输入客户密码', trigger: 'change' },
                ],
                passwordConfirm: [
                    { required: true, message: '请确认密码', trigger: 'blur'},
                    { required: true, message: '请确认密码', trigger: 'change'},
                    {
                        validator:(rule,value,callback)=>{
                            if(value!==this.editForm.password){
                                callback(new Error('两次输入密码不一致'))
                            }else{
                                callback( )
                            }
                        },
                        trigger:'blur'
                    }

                ],
                name: [
                    { required: true, message: '请输入客户名称', trigger: 'blur' },
                    { required: true, message: '请输入客户名称', trigger: 'change' },
                ],
                phone: [
                    { required: true, message: '请输入联系方式', trigger: 'blur' },
                    { required: true, message: '请输入联系方式', trigger: 'change' },
                ],
                address: [
                    { required: true, message: '请输入地址', trigger: 'blur' },
                    { required: true, message: '请输入地址', trigger: 'change' },
                ],
                delivery_address: [
                    { required: true, message: '请输入配送地址', trigger: 'blur' },
                    { required: true, message: '请输入配送地址', trigger: 'change' },
                ],
            },
        };
    },
    methods:
    {
        /* 重置表单内容 按钮*/
        resetLoginForm()
        {
            this.$refs.loginFormRef.resetFields();
            this.loginForm.value='';
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
                    if(window.sessionStorage.getItem("username") === null) {
                        window.sessionStorage.setItem("username", this.loginForm.id);
                    }
                    if(this.loginForm.value === "客户") {
                        await this.$router.push({path: "/GuestHome"});   // 页面路由跳转
                    }
                    else if(this.loginForm.value === "门店") {
                        await this.$router.push({path: "/ShopHome"});   // 页面路由跳转
                    }
                }
                else
                {
                    this.$message.error("账号或者密码错误");   // 错误提示
                }
            })
        },

        // 注册 客户账号
        submitForm() {
            this.$refs.editFormRef.validate(async valid => {
                if (valid) {
                    const {data:res} = await this.$http.post("GuestRegister", this.editForm);
                    if (res === "ok")
                    {
                        this.dialogVisible = false
                        this.$message({
                            type: 'success',
                            message: '注册成功！'
                        })
                    }
                    else
                    {
                        this.$message.error('注册失败, 用户名已存在')
                    }

                }
                else
                {
                    return false
                }
            })
        },
    },

    /* 监听选择器 */
    computed:
        {
          newValue()
          {
              return this.loginForm.value;
          }
        },
    watch:
        {
            newValue(val)
            {
                if(val !== '门店')
                {
                    this.$refs.btnRegister.$el.innerText = this.RegisterName;
                }
                else
                {
                    this.$refs.btnRegister.$el.innerText = '';
                }
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
    .MyAdd
    {
        display: flex;
        justify-content: space-between;
        padding-left: 20px;
        padding-bottom: 15px;
    }
    .btns
    {
        display: flex;   // 流式布局
        justify-content: flex-end;
    }
</style>