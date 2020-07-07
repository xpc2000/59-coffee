<template>
    <div class = "login_container">
        <div class = "login_box">

            <h3 class="title">个人信息</h3>

            <!-- 表单区域 -->
            <el-form ref="editFormRef" label-width="80px" :model="editForm" :rules="rules" class="login_form">
                <el-form-item label="密码" prop="password"  required>
                    <el-input size="small" v-model="editForm.password" auto-complete="off" prefix-icon="iconfont icon-mima" type="password" placeholder="请输入门店密码"  show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="passwordConfirm"  required>
                    <el-input size="small" v-model="editForm.passwordConfirm" auto-complete="off" prefix-icon="iconfont icon-mima" type="password" placeholder="请输入门店密码"  show-password></el-input>
                </el-form-item>
                <el-form-item label="客户名称" prop="name"  required>
                    <el-input size="small" v-model="editForm.name" prefix-icon="iconfont icon-denglu" placeholder="请输入门店名称" clearable></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="tel"  required>
                    <el-input size="small" v-model="editForm.tel" prefix-icon="iconfont icon-lianxifangshi" placeholder="请输入联系方式" clearable></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address"  required>
                    <el-input size="small" v-model="editForm.address" prefix-icon="iconfont icon-dizhi" placeholder="请输入地址" clearable></el-input>
                </el-form-item>
                <el-form-item label="配送地址" prop="delivery_address"  required>
                    <el-input size="small" v-model="editForm.delivery_address" prefix-icon="iconfont icon-dizhi" placeholder="请输入配送地址" clearable></el-input>
                </el-form-item>

                <!-- 按钮 -->
                <el-form-item class ="btns">
                    <el-button size="medium" type="primary" @click="Edit()">保存修改</el-button>
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
                /* 编辑页面样式 */
                editForm: {
                    password: '',
                    passwordConfirm: '',
                    name: '',
                    tel: '',
                    address: '',
                    delivery_address: '',
                },

                /* rules表单验证 */
                rules: {
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
                    tel: [
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

        /* 初始化 */
        created()
        {
          this.getMessage();
        },

        methods:
            {
                async getMessage() {
                    let id = window.sessionStorage.getItem("username");
                    const {data: res} = await this.$http.post("getMessage", id);
                    this.editForm = res;
                },

                Edit()
                {
                    this.$refs.editFormRef.validate(async valid => {
                        if (valid) {
                            const {data:res} = await this.$http.post("EditMessage", this.editForm);
                            if (res === "ok")
                            {
                                this.$message({
                                    type: 'success',
                                    message: '修改成功',
                                })
                            }
                            else
                            {
                                this.$message.error('修改失败');
                            }

                        }
                        else
                        {
                            return false
                        }
                    })
                },
            }
    }
</script>

<style lang="less" scoped>
    .login_container
    {
        background-color: #2b4b6b;
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
        top: 0%;
        transform: translate(-50%, -50%);
    }
    .login_box
    {
        width: 450px;
        height: 550px;
        background-color: #fff;
        border-radius: 10px;
        position: absolute;   // 绝对坐标
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }
    .login_form
    {
        position: absolute;
        bottom: 7%;
        width: 100%;
        padding-left: 30px;
        padding-right: 60px;
        box-sizing: border-box;
    }
    .btns
    {
        display: flex;   // 流式布局
        justify-content: center;
        padding-right: 50px;
    }
</style>