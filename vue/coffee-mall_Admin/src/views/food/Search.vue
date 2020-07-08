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
                <el-input v-model="name" placeholder="请输入餐品名称" clearable></el-input>
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
                <el-col :span="4" v-for="(item) in tempList" :key="item.id" :offset="1">
                    <div style="margin-top:15px">
                        <el-card :body-style="{ padding: '0px'}"  shadow="hover">
                            <img :src="item.url" class="image">
                            <div style="padding: 14px;">
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span><i class="el-icon-star-on"> {{item.name}}</i></span>
                                </div>
                                <span><i class="el-icon-menu"> {{item.type}}</i> </span>
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span>  ¥ {{item.price}}</span>
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
            <el-form label-width="80px" :model="NewForm" :rules="NewRules" ref="NewFormRef">
                <el-form-item label="餐品ID" prop="id"  required>
                    <el-input size="small" v-model="NewForm.id" auto-complete="off" prefix-icon="iconfont icon-id" placeholder="请输入餐品ID"></el-input>
                </el-form-item>
                <el-form-item label="餐品名称" prop="name"  required>
                    <el-input size="small" v-model="NewForm.name" auto-complete="off" prefix-icon="iconfont icon-mingcheng" placeholder="请输入餐品名称"></el-input>
                </el-form-item>
                <el-form-item label="餐品类型" prop="type"  required>
                    <el-input size="small" v-model="NewForm.type" prefix-icon="iconfont icon-17" placeholder="请输入餐品类型"></el-input>
                </el-form-item>
                <el-form-item label="单价" prop="price"  required>
                    <el-input size="small" v-model="NewForm.price" prefix-icon="iconfont icon-RectangleCopy" placeholder="请输入餐品单价"></el-input>
                </el-form-item>

                <!-- 照片上传 -->
                <el-form-item label="商品图片" prop=""  required>
                    <el-upload
                            class="avatar-uploader"
                            :action=uploadURL
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                        <img v-if="NewForm.url" :src="NewForm.url" class="avatar">
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
        <el-dialog title="编辑餐品" :visible.sync="EditFormVisible" width="30%">
            <el-form label-width="80px" :model="EditForm" :rules="editRules" ref="EditFormRef" >
                <el-form-item label="餐品名称" prop="name"  required>
                    <el-input size="small" v-model="EditForm.name" auto-complete="off" prefix-icon="iconfont icon-mingcheng" placeholder="请输入餐品名称"></el-input>
                </el-form-item>
                <el-form-item label="餐品类型" prop="type"  required>
                    <el-input size="small" v-model="EditForm.type" prefix-icon="iconfont icon-17" placeholder="请输入餐品类型"></el-input>
                </el-form-item>
                <el-form-item label="单价" prop="price"  required>
                    <el-input size="small" v-model="EditForm.price" prefix-icon="iconfont icon-RectangleCopy" placeholder="请输入餐品单价"></el-input>
                </el-form-item>

                <!-- 照片上传 -->
                <el-form-item label="商品图片" prop=""  required>
                <el-upload
                        class="avatar-uploader"
                        :action=uploadURL
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                        :headers="header">
                    <img v-if="EditForm.url" :src="EditForm.url" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="small" @click='EditFormVisible = false'>取消</el-button>
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

                uploadURL: "https://api.superbed.cn/upload?token=d9add37aec764da091cf31b9e3e5cf93",

                name:'',   // 搜索框 餐品名称
                NewFormVisible: false,   // 控制新建页面显示与隐藏
                EditFormVisible: false,   // 控制编辑页面显示与隐藏

                /* 分页 */
                pageSize: 4,
                currentPage: 1,
                totalSize: 20,   // 总条数，用于死数据
                tempList: [],   // 分页数据


                /* 死数据 循环卡片列表的数据源 */
                tableData: [
                    {
                        id: '1',
                        name: '餐品1',
                        type: '类型',
                        price: '单价',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '2',
                        name: '餐品2',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '3',
                        name: '餐品3',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '4',
                        name: '餐品4',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '5',
                        name: '餐品5',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '6',
                        name: '餐品6',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '7',
                        name: '餐品7',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '8',
                        name: '餐品8',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '9',
                        name: '餐品9',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '10',
                        name: '餐品10',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '11',
                        name: '餐品11',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '12',
                        name: '餐品12',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '13',
                        name: '餐品13',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                    {
                        id: '14',
                        name: '餐品14',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    },
                ],

                /* 新建 页面样式 */
                NewForm: {
                    id: '',
                    name: '',
                    type: '',
                    price: '',
                    url:'',
                },

                /* 编辑 表单验证 */
                NewRules: {
                    id: [
                        {required: true, message: '请输入餐品ID', trigger: 'blur'},
                        {required: true, message: '请输入餐品ID', trigger: 'change'},
                    ],
                    name: [
                        {required: true, message: '请输入餐品名称', trigger: 'blur'},
                        {required: true, message: '请输入餐品名称', trigger: 'change'},
                    ],
                    type: [
                        {required: true, message: '请输入餐品类型', trigger: 'blur'},
                        {required: true, message: '请输入餐品类型', trigger: 'change'},
                    ],
                    price: [
                        {required: true, message: '请输入餐品单价', trigger: 'blur'},
                        {required: true, message: '请输入餐品单价', trigger: 'change'},
                    ],
                },

                /* 编辑 页面样式 */
                EditForm: {
                    id: '',
                    name: '',
                    type: '',
                    price: '',
                    url:'',
                },

                /* 编辑 表单验证 */
                editRules: {
                    name: [
                        {required: true, message: '请输入餐品名称', trigger: 'blur'},
                        {required: true, message: '请输入餐品名称', trigger: 'change'},
                    ],
                    type: [
                        {required: true, message: '请输入餐品类型', trigger: 'blur'},
                        {required: true, message: '请输入餐品类型', trigger: 'change'},
                    ],
                    price: [
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

                if(this.name === '')
                {
                    this.name = "null";
                }
                const {data: res} = await this.$http.post("SearchFood", this.name);

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
                    this.EditFormVisible = true
                    this.EditForm.id = item.id;
                    this.EditForm.name = item.name;
                    this.EditForm.type = item.type;
                    this.EditForm.price = item.price;
                    this.EditForm.url = item.url;
                }
                else
                {
                    this.NewFormVisible = true
                    this.NewForm.id = '';
                    this.NewForm.name = '';
                    this.NewForm.type = '';
                    this.NewForm.price = '';
                    this.NewForm.url = '';
                }

            },

            /* 图片上传控制 */
            handleAvatarSuccess(res, file) {
                console.log(res.url);
                this.NewForm.url = res.url;
                this.EditForm.url = res.url;
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

            // 新建 方法
            newForm() {
                this.$refs.NewFormRef.validate(async valid => {
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
                this.$refs.EditFormRef.validate(async valid => {
                    if (valid) {
                        const {data:res} = await this.$http.post("foodEdit", this.EditForm);
                        if (res === "ok")
                        {
                            this.EditFormVisible = false;
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

            // 删除餐品
            deleteUser(item) {
                this.$confirm('确定要删除吗?', '信息', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(async () => {
                        // 删除
                        const {data:res} = await this.$http.post("foodDelete", item.id);
                        if (res === "ok")
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
        font-size: 100px;
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
    .image{
        width: 210px;
        height: 235px;
        align-content: center;
    }
</style>