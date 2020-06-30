<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>餐品管理</el-breadcrumb-item>
            <el-breadcrumb-item>检索餐品</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <!-- 搜索栏 新建按钮-->
        <el-form class="user-search" :inline="true" label-width="90px">
            <el-form-item prop="store" label="餐品名称: ">
                <el-input v-model="value" placeholder="请输入餐品名称" clearable></el-input>
            </el-form-item>
            <el-form-item label="">
                <el-button size="small" type="primary"  icon="iconfont icon-chazhao" class="title" round @click="Search()"> 查询餐品 </el-button>
            </el-form-item>
            <el-form-item label="">
                <el-button size="small" type="primary" icon="iconfont icon-tianjiaxindeyangbenhe" round @click="handleEdit()"> 添加餐品 </el-button>
            </el-form-item>
        </el-form>



        <!-- 循环卡片列表 -->
        <div style="margin-left:0.5%;margin-right:1%">
            <el-row>
                <el-col :span="4" v-for="(item) in tempList" :key="item.foodID" :offset="1">
                    <div style="margin-top:15px">
                        <el-card :body-style="{ padding: '0px'}"  shadow="hover">
                            <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
                            <div style="padding: 14px;">
                                <span>{{item.foodName}}</span>
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span>  ¥ {{item.foodMoney}}</span>
                                    <div style="padding-top: 14px">
                                        <el-row type="flex">
                                        <el-button type="success" size="mini" round @click="handleEdit(item)">编辑</el-button>
                                        <el-button type="danger" size="mini" round @click="deleteUser(item)">删除</el-button>
                                        </el-row>
                                    </div>
                                </div>
                            </div>
                        </el-card>
                    </div>
                </el-col>
            </el-row>

            <br/>
            <br/>

            <!-- 分页 -->
            <div class="block" align="center">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[4,8, 12, 16]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="totalSize">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑界面 -->
        <el-dialog :title="title" :visible.sync="editFormVisible" width="30%">
            <el-form label-width="80px" :model="editForm" :rules="rules" >
                <el-form-item label="餐品ID" prop="foodID"  required>
                    <el-input size="small" v-model="editForm.foodID" auto-complete="off" prefix-icon="iconfont icon-id" placeholder="请输入餐品ID"></el-input>
                </el-form-item>
                <el-form-item label="餐品名称" prop="foodName"  required>
                    <el-input size="small" v-model="editForm.foodName" auto-complete="off" prefix-icon="iconfont icon-mingcheng" placeholder="请输入餐品名称"></el-input>
                </el-form-item>
                <el-form-item label="餐品类型" prop="foodType"  required>
                    <el-input size="small" v-model="editForm.foodType" prefix-icon="iconfont icon-17" placeholder="请输入餐品类型"></el-input>
                </el-form-item>
                <el-form-item label="单价" prop="foodMoney"  required>
                    <el-input size="small" v-model="editForm.foodMoney" prefix-icon="iconfont icon-RectangleCopy" placeholder="请输入餐品单价"></el-input>
                </el-form-item>

                <!-- 照片上传 -->
                <el-form-item label="商品图片" prop=""  required>
                <el-upload
                        class="avatar-uploader"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="small" @click='closeDialog()'>取消</el-button>
                <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default
    {
        data() {
            return {

                /* 死数据 循环卡片列表的数据源 */
                tabledata: [
                    {
                        foodID: '1',
                        foodName: '餐品1',
                        foodType: '类型',
                        foodMoney: '单价',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品2',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品3',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品4',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品5',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品6',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品7',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品8',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品9',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品10',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品11',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品12',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品13',
                        foodType: '3',
                        foodMoney: '4',
                    },
                    {
                        foodID: '1',
                        foodName: '餐品14',
                        foodType: '3',
                        foodMoney: '4',
                    },
                ],

                /* 分页 */
                pageSize: 4,
                currentPage: 1,
                totalSize: 20,   // 总条数，用于死数据
                tempList: [],   // 用于死数据

                /* 编辑页面样式 */
                editForm: {
                    foodID: '',
                    foodName: '',
                    foodType: '',
                    foodMoney: '',
                },

                /* 图片路径 */
                imageUrl:'',

                editFormVisible: false,   // 控制编辑页面显示与隐藏
                title: "添加餐品",

                /* rules表单验证 */
                rules: {
                    foodID: [
                        {required: true, message: '请输入餐品ID', trigger: 'blur'},
                        {pattern: /^F[A-Za-z0-9]+/, required: true, message: '请输入正确餐品ID(以开头)', trigger: 'blur'},
                        {required: true, message: '请输入餐品ID', trigger: 'change'},
                        {pattern: /^F[A-Za-z0-9]+/, required: true, message: '请输入正确餐品ID(以F开头)', trigger: 'change'},
                    ],
                    foodName: [
                        {required: true, message: '请输入餐品名称', trigger: 'blur'},
                        {required: true, message: '请输入餐品名称', trigger: 'change'},
                    ],
                    foodType: [
                        {required: true, message: '请输入餐品类型', trigger: 'blur'},
                        {required: true, message: '请输入餐品类型', trigger: 'change'},
                    ],
                    foodMoney: [
                        {required: true, message: '请输入餐品单价', trigger: 'blur'},
                        {required: true, message: '请输入餐品单价', trigger: 'change'},
                    ],
                },
            }
        },

        methods: {
            /* TO DO:查询 */
            Search()
            {

            },

            /* 分页更新功能 */
            handleSizeChange(val)
            {
                this.pageSize = val;
                this.handleCurrentChange(this.currentPage);
            },

            /* TO-DO: 死数据 */
            handleCurrentChange(val)
            {
                this.totalSize = this.tabledata.length;   // 更新总条数，用于死数据
                this.currentPage = val;
                let from = (this.currentPage - 1) * this.pageSize;
                let to = this.currentPage * this.pageSize;
                this.tempList = [];
                for (; from < to; from++)
                {
                    if (this.tabledata[from])
                    {
                        this.tempList.push(this.tabledata[from]);
                    }
                }
            },

            /* 显示编辑界面 */
            handleEdit: function(item)
            {
                this.editFormVisible = true
                if (item !== undefined && item !== 'undefined')
                {
                    this.title = '修改餐品'
                    this.editForm.foodID = item.foodID;
                    this.editForm.foodName = item.foodName;
                    this.editForm.foodType = item.foodType;
                    this.editForm.foodMoney = item.foodMoney;
                }
                else
                {
                    this.title = '添加餐品'
                    this.editForm.foodID = '';
                    this.editForm.foodName = '';
                    this.editForm.foodType = '';
                    this.editForm.foodMoney = '';
                }

            },

            /* 关闭编辑栏 */
            closeDialog()
            {
                this.editFormVisible = false
            },



            /* 图片上传控制 */
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },


            // 编辑、添加提交方法
            submitForm(editData) {
                this.$refs[editData].validate(valid => {
                    if (valid) {
                        // 请求方法
                        userSave(this.editForm)
                            .then(res => {
                                this.editFormVisible = false
                                this.loading = false
                                if (res.success)
                                {
                                    this.getdata(this.formInline)
                                    this.$message({
                                        type: 'success',
                                        message: '数据保存成功！'
                                    })
                                }
                                else
                                {
                                    this.$message({
                                        type: 'info',
                                        message: res.msg
                                    })
                                }
                            })
                            .catch(err => {
                                this.editFormVisible = false
                                this.loading = false
                                this.$message.error('保存失败，请稍后再试！')
                            })
                    }
                    else
                    {
                        return false
                    }
                })
            },

            // 删除用户
            deleteUser(item) {
                this.$confirm('确定要删除吗?', '信息', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(() => {
                        // 删除
                        userDelete(item.id)
                            .then(res => {
                                if (res.success)
                                {
                                    this.$message({
                                        type: 'success',
                                        message: '数据已删除!'
                                    })
                                    this.getdata(this.formInline)
                                }
                                else
                                {
                                    this.$message({
                                        type: 'info',
                                        message: res.msg
                                    })
                                }
                            })
                            .catch(err => {
                                this.loading = false
                                this.$message.error('数据删除失败，请稍后再试！')
                            })
                    })
                    .catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除！'
                        })
                    })
            },
        }
    }
</script>

<style lang="less">
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 100px;
        line-height: 100px;
        text-align: center;

    }
    .avatar {
        width: 100px;
        height: 100px;
        display: block;
    }
</style>