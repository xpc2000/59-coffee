<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>库存管理</el-breadcrumb-item>
            <el-breadcrumb-item>上架菜品</el-breadcrumb-item>
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
        </el-form>



        <!-- 循环卡片列表 -->
        <div style="margin-left:0.5%;margin-right:1%">
            <el-row>
                <el-col :span="4" v-for="(item) in tempList" :key="item.idFood" :offset="1">
                    <div style="margin-top:15px">
                        <el-card :body-style="{ padding: '0px'}"  shadow="hover" >
                            <img :src="item.url" class="image">
                            <div style="padding: 14px;">
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span><i class="el-icon-star-on"> {{item.name}}</i></span>
                                </div>
                                <span><i class="el-icon-menu"> {{item.type}}</i> </span>
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span>  ¥ {{item.price}}</span>
                                    <span><p>库存数量:{{item.num}}</p></span>
                                    <span><p v-text="item.sellOut==0?'在售':'已下架'"></p></span>
                                    <div style="padding-top: 14px">
                                        <el-row type="flex">
                                        <el-button type="success" size="mini" round @click="upStock(item)">上架</el-button>
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

    </div>
</template>

<script>
    export default
    {
        data() {
            return {

                uploadURL: "https://api.uomg.com/api/image.ali?file=multipart",

                name:'',   // 搜索框 餐品名称
                EditFormVisible: false,   // 控制编辑页面显示与隐藏

                /* 分页 */
                pageSize: 4,
                currentPage: 1,
                totalSize: 20,   // 总条数，用于死数据
                tempList: [],   // 分页数据


                /* 死数据 循环卡片列表的数据源 */
                tableData: [
                    {
                        idFood: '1',
                        name: '餐品1',
                        type: '类型',
                        price: '单价',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'500',
                        sellOut:'1',
                    },
                    {
                        idFood: '2',
                        name: '餐品2',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'600',
                        sellOut:'1',
                    },
                    {
                        idFood: '3',
                        name: '餐品3',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'500',
                        sellOut:'1',
                    },
                    {
                        idFood: '4',
                        name: '餐品4',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'500',
                        sellOut:'1',
                    },
                    {
                        idFood: '5',
                        name: '餐品5',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'100',
                        sellOut:'1',
                    },
                    {
                        idFood: '6',
                        name: '餐品6',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'200',
                        sellOut:'1',
                    },
                    {
                        idFood: '7',
                        name: '餐品7',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'600',
                        sellOut:'1',
                    },
                    {
                        idFood: '8',
                        name: '餐品8',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'700',
                        sellOut:'1',
                    },
                    {
                        idFood: '9',
                        name: '餐品9',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'500',
                        sellOut:'1',
                    },
                    {
                        idFood: '10',
                        name: '餐品10',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'100',
                        sellOut:'1',
                    },
                    {
                        idFood: '11',
                        name: '餐品11',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'700',
                        sellOut:'1',
                    },
                    {
                        idFood: '12',
                        name: '餐品12',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'700',
                        sellOut:'1',
                    },
                    {
                        idFood: '13',
                        name: '餐品13',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'700',
                        sellOut:'1',
                    },
                    {
                        idFood: '14',
                        name: '餐品14',
                        type: '3',
                        price: '4',
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num:'700',
                        sellOut:'1',
                    },
                ],

                /* 编辑 页面样式 */
                EditForm: {
                    idFood: '',
                    name: '',
                    type: '',
                    price: '',
                    url:'',
                    num:'',
                    sellOut:'',
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
                    num:[
                        {required: true, message: '请输入餐品库存', trigger: 'blur'},
                        {required: true, message: '请输入餐品库存', trigger: 'change'},
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
                const {data: res} = await this.$http.post("SearchDownShelfStock", this.name);

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




            // 上架商品
            upStock(item) {
                this.$confirm('确定要上架吗?', '信息', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(async () => {
                        // 下架商品
                        const {data:res} = await this.$http.post("StockUpShelf", item.idFood);
                        if (res === "ok")
                        {
                            this.$message({
                                type: 'success',
                                message: '商品已上架',
                            })
                            
                            await this.getList();
                        }
                        else
                        {
                            this.$message({
                                type: 'info',
                                message: '上架失败'
                            })
                        }
                    })
                    .catch(err => {
                        this.$message.error('上架失败')
                    })
                    .catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消上架'
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