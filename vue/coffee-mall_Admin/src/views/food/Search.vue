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
        <el-form ref="editFormRef" class="user-search" :inline="true" label-width="90px">
            <el-form-item prop="store" label="餐品名称: ">
                <el-input v-model="foodName" placeholder="请输入餐品名称" clearable></el-input>
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
                            <img :src="item.foodImage" class="image">
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

        <!-- 新建界面 -->
        <el-dialog title="添加餐品" :visible.sync="NewFormVisible" width="30%">
            <el-form label-width="80px" :model="NewForm" :rules="NewRules" >
                <el-form-item label="餐品ID" prop="foodID"  required>
                    <el-input size="small" v-model="NewForm.foodID" auto-complete="off" prefix-icon="iconfont icon-id" placeholder="请输入餐品ID"></el-input>
                </el-form-item>
                <el-form-item label="餐品名称" prop="foodName"  required>
                    <el-input size="small" v-model="NewForm.foodName" auto-complete="off" prefix-icon="iconfont icon-mingcheng" placeholder="请输入餐品名称"></el-input>
                </el-form-item>
                <el-form-item label="餐品类型" prop="foodType"  required>
                    <el-input size="small" v-model="NewForm.foodType" prefix-icon="iconfont icon-17" placeholder="请输入餐品类型"></el-input>
                </el-form-item>
                <el-form-item label="单价" prop="foodMoney"  required>
                    <el-input size="small" v-model="NewForm.foodMoney" prefix-icon="iconfont icon-RectangleCopy" placeholder="请输入餐品单价"></el-input>
                </el-form-item>

                <!-- 照片上传 -->
                <el-form-item label="商品图片" prop=""  required>
                    <el-upload
                            class="avatar-uploader"
                            action="https://jsonplaceholder.typicode.com/posts/"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                        <img v-if="NewForm.foodImage" :src="NewForm.foodImage" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="small" @click='NewFormVisible = false'>取消</el-button>
                <el-button size="small" type="primary"  class="title" @click="newForm()">保存</el-button>
            </div>
        </el-dialog>

        <!-- 编辑界面 -->
        <el-dialog title="编辑餐品" :visible.sync="editFormVisible" width="30%">
            <el-form label-width="80px" :model="editForm" :rules="editRules" >
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
                    <img v-if="editForm.foodImage" :src="editForm.foodImage" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="small" @click='editFormVisible = false'>取消</el-button>
                <el-button size="small" type="primary"  class="title" @click="editForm()">保存</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default
    {
        data() {
            return {

                imageUrl:'',   // 图片路径
                foodName:'',   // 搜索框 餐品名称
                NewFormVisible: false,   // 控制新建页面显示与隐藏
                editFormVisible: false,   // 控制编辑页面显示与隐藏

                /* 分页 */
                pageSize: 4,
                currentPage: 1,
                totalSize: 20,   // 总条数，用于死数据
                tempList: [],   // 分页数据


                /* 死数据 循环卡片列表的数据源 */
                tableData: [
                    {
                        foodID: '1',
                        foodName: '餐品1',
                        foodType: '类型',
                        foodMoney: '单价',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '2',
                        foodName: '餐品2',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '3',
                        foodName: '餐品3',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '4',
                        foodName: '餐品4',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '5',
                        foodName: '餐品5',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '6',
                        foodName: '餐品6',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '7',
                        foodName: '餐品7',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '8',
                        foodName: '餐品8',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '9',
                        foodName: '餐品9',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '10',
                        foodName: '餐品10',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '11',
                        foodName: '餐品11',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '12',
                        foodName: '餐品12',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '13',
                        foodName: '餐品13',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        foodID: '14',
                        foodName: '餐品14',
                        foodType: '3',
                        foodMoney: '4',
                        foodImage:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                ],

                /* 新建 页面样式 */
                NewForm: {
                    foodID: '',
                    foodName: '',
                    foodType: '',
                    foodMoney: '',
                    foodImage:'',
                },

                /* 编辑 表单验证 */
                NewRules: {
                    foodID: [
                        {required: true, message: '请输入餐品ID', trigger: 'blur'},
                        {required: true, message: '请输入餐品ID', trigger: 'change'},
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

                /* 编辑 页面样式 */
                editForm: {
                    foodName: '',
                    foodType: '',
                    foodMoney: '',
                    foodImage:'',
                },

                /* 编辑 表单验证 */
                editRules: {
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

        /* 初始化 */
        created() {
            this.getList();
        },

        methods: {

            /* 获得列表 */
            async getList() {

                const {data: res} = await this.$http.post("SearchFood", this.foodName);

                //设置列表数据
                this.tableData = res;
                this.handleSizeChange(this.pageSize);   // 更新分页 界面
            },

            /* 查询 */
            Search()
            {
                this.getList();
                this.totalSize = this.tableData.length;   // 更新总条数
                this.handleSizeChange(this.pageSize);   // 更新分页 界面
            },

            /* 分页更新功能 */
            handleSizeChange(val)
            {
                this.pageSize = val;
                this.handleCurrentChange(this.currentPage);
            },

            /* 更新 */
            handleCurrentChange(val)
            {
             //   this.totalSize = this.tableData.length;   // 更新总条数，用于死数据
                this.currentPage = val;
                let from = (this.currentPage - 1) * this.pageSize;
                let to = this.currentPage * this.pageSize;
                this.tempList = [];
                for (; from < to; from++)
                {
                    if (this.tableData[from])
                    {
                        this.tempList.push(this.tableData[from]);
                    }
                }
            },

            /* 显示编辑界面 */
            handleEdit: function(item)
            {
                if (item !== undefined && item !== 'undefined')
                {
                    this.editFormVisible = true
                    this.editForm.foodID = item.foodID;
                    this.editForm.foodName = item.foodName;
                    this.editForm.foodType = item.foodType;
                    this.editForm.foodMoney = item.foodMoney;
                    this.editForm.foodImage = item.foodImage;
                }
                else
                {
                    this.NewFormVisible = true
                    this.NewForm.foodID = '';
                    this.NewForm.foodName = '';
                    this.NewForm.foodType = '';
                    this.NewForm.foodMoney = '';
                    this.NewForm.foodImage = '';
                }

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

            // 编辑 方法
            newForm() {
                this.$refs.editFormRef.validate(async valid => {
                    if (valid) {
                        const {data:res} = await this.$http.post("foodNew", this.NewForm);
                        if (res === "ok")
                        {
                            this.NewFormVisible = false;
                            await this.getList();
                            this.$message({
                                type: 'success',
                                message: '新建餐品成功'
                            })
                        }
                        else
                        {
                            this.$message.error('新建餐品失败');
                        }

                    }
                    else
                    {
                        return false
                    }
                })
            },

            // 编辑 方法
            editForm() {
                this.$refs.editFormRef.validate(async valid => {
                    if (valid) {
                        const {data:res} = await this.$http.post("foodEdit", this.editForm);
                        if (res === "ok")
                        {
                            this.editFormVisible = false;
                            await this.getList();
                            this.$message({
                                type: 'success',
                                message: '编辑餐品成功'
                            })
                        }
                        else
                        {
                            this.$message.error('编辑餐品失败');
                        }

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
                    .then(async () => {
                        // 删除
                        const {data:res} = await this.$http.post("foodDelete", item.foodID);
                        if (res.success)
                        {
                            this.$message({
                                type: 'success',
                                message: '数据已删除'
                            })
                            await this.getList();
                        }
                        else
                        {
                            this.$message({
                                type: 'info',
                                message: '删除失败'
                            })
                        }
                    })
                    .catch(err => {
                        this.$message.error('数据删除失败')
                    })
                    .catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
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