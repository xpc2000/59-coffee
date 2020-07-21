<template>
    <div>
        <!-- 搜索栏 -->
        <el-form class="user-search" :inline="true" label-width="90px">
            <el-form-item prop="store" label="小吃名称: ">
                <el-input v-model="name" placeholder="请输入小吃名称" clearable></el-input>
            </el-form-item>
            <el-form-item label="">
                <el-button size="small" type="primary" icon="iconfont icon-chazhao" class="title" round
                           @click="SearchSnack()"> 查询小吃
                </el-button>
            </el-form-item>
        </el-form>


        <!-- 循环卡片列表 -->
        <div style="margin-left:0.5%;margin-right:1%">
            <el-row>
                <el-col :span="4" v-for="(item) in tempList" :key="item.id" :offset="1">
                    <div style="margin-top:15px">
                        <el-card :body-style="{ padding: '0px'}" shadow="hover">
                            <img :src="item.url" class="image">
                            <div style="padding: 14px;">
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span><i class="el-icon-star-on"> {{item.idFood}}</i></span>
                                </div>
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span><i class="el-icon-star-on"> {{item.name}}</i></span>
                                </div>
                                <span><i class="el-icon-menu"> {{item.type}}</i> </span>
                                <div style="padding-bottom: 14px; padding-top: 14px">
                                    <span><i style="color: red">  ¥ {{item.price}}</i></span>
                                    <div style="padding-top: 14px">
                                        <span><i style="font-size: 13px;font-style: normal">剩余 {{item.num}} </i> </span>
                                    </div>
                                    <div style="padding-top: 14px">
                                        <el-row type="flex">
                                            <el-button type="success" size="mini" round @click="AddCart(item)">加入购物车</el-button>
                                        </el-row>
                                    </div>
                                </div>
                            </div>
                        </el-card>
                    </div>
                </el-col>
            </el-row>

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
        data()
        {
            return{

                /* 分页 */
                name: '',   // 查询名称
                shopid:'',
                param:{
                    name:'',
                    shopid:''
                },
                addtocart:{
                    username:'',
                    item:''
                },
                pageSize: 4,
                currentPage: 1,
                totalSize: 20,   // 总条数，用于死数据
                tempList: [],   // 分页数据


                tableData:
                    [
                        {
                            id: '1',
                            name: '餐品1',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                            num: 1,
                        },
                        {
                            id: '2',
                            name: '餐品2',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                            num: 2,
                        },
                        {
                            id: '3',
                            name: '餐品3',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        },
                        {
                            id: '4',
                            name: '餐品4',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        },
                        {
                            id: '5',
                            name: '餐品5',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        },
                        {
                            id: '6',
                            name: '餐品6',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        },
                        {
                            id: '7',
                            name: '餐品7',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        },
                        {
                            id: '8',
                            name: '餐品8',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        },
                        {
                            id: '9',
                            name: '餐品9',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        },
                        {
                            id: '10',
                            name: '餐品10',
                            type: '类型',
                            price: '单价',
                            url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        }
                    ]
            }
        },

        /* 初始化 */
        created() {
            this.getList();
        },

        methods:
            {
                /* 获得列表 */
                async getList() {

                    if (this.name === '') {
                        this.name = "null";
                    }
                    this.param.name=this.name;
                    if(window.sessionStorage.getItem("StoreId")===null){
                        this.param.shopid="null";
                        console.log("1");
                    }
                    else{
                        this.param.shopid=window.sessionStorage.getItem("StoreId");
                        console.log(this.param.shopid);
                        console.log("2");
                    }
                    const {data: res} = await this.$http.post("SearchSnack", this.param);

                    //设置列表数据
                    this.tableData = res;
                    this.handleSizeChange(this.pageSize);   // 更新分页 界面
                },

                /* 查询饮料 */
                SearchSnack()
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

                /* 加入购物车 */
                async AddCart(item) {
                    this.addtocart.username=window.sessionStorage.getItem("username");
                    this.addtocart.item=item;
                    const {data: res} = await this.$http.post("AddShoppingCart", this.addtocart);
                    if(res === 'ok')
                    {
                        this.$message.success("成功加入购物车");
                    }
                    else
                    {
                        this.$message.error("请稍后再试");
                    }
                }
            }
    }
</script>

<style lang="less" scoped>
    .image{
        width: 210px;
        height: 235px;
        align-content: center;
    }
</style>