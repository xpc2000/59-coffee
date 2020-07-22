<template>
    <el-container>
        <el-header>
            <div>
                <span style="color: #ffffff">我的订单</span>
            </div>
            <el-button  @click="back()" style="color: #ffffff" type="text">返回</el-button>
        </el-header>

        <el-main>

            <el-tabs type="border-card">

                <!--待发货-->
                <el-tab-pane label="待发货">
                    <el-table
                            :data="ShipData"
                            height="250"
                            border
                            style="width: 100%">
                        <el-table-column prop="date" label="日期" width="180"></el-table-column>
                        <el-table-column prop="name" label="姓名" width="180"></el-table-column>
                        <el-table-column prop="address" label="地址" width="180"></el-table-column>
                        <el-table-column prop="phone" label="手机号码" width="180"></el-table-column>
                        <el-table-column prop="totalPrice" label="总金额" width="180"></el-table-column>
                        <el-table-column fixed="right" label="操作">
                            <template slot-scope="scope">
                                <el-button @click="handleClick(scope.row,1)" type="text" size="small">查看详细</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

                <!--待收货-->
                <el-tab-pane label="待收货">
                    <el-table
                            :data="RecData"
                            height="250"
                            border
                            style="width: 100%">
                        <el-table-column prop="date" label="日期" width="180"></el-table-column>
                        <el-table-column prop="name" label="姓名" width="180"></el-table-column>
                        <el-table-column prop="address" label="地址" width="180"></el-table-column>
                        <el-table-column prop="phone" label="手机号码" width="180"></el-table-column>
                        <el-table-column prop="totalPrice" label="总金额" width="180"></el-table-column>
                        <el-table-column fixed="right" label="操作">
                            <template slot-scope="scope">
                                <el-button @click="handleClick(scope.row,1)" type="text" size="small">查看详细</el-button>
                                <el-button @click="handleRec(scope.row,1)" type="text" size="small">收货</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

                <!--已完成，待评价-->
                <el-tab-pane label="已完成">
                    <el-table
                            :data="CompleteData"
                            height="250"
                            border
                            style="width: 100%">
                        <el-table-column prop="date" label="日期" width="180"></el-table-column>
                        <el-table-column prop="name" label="姓名" width="180"></el-table-column>
                        <el-table-column prop="address" label="地址" width="180"></el-table-column>
                        <el-table-column prop="phone" label="手机号码" width="180"></el-table-column>
                        <el-table-column prop="totalPrice" label="总金额" width="180"></el-table-column>
                        <el-table-column fixed="right" label="操作">
                            <template slot-scope="scope">
                                <el-button @click="handleClick(scope.row,1)" type="text" size="small">查看详细</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

            </el-tabs>


            <!-- 订单详情页 -->
            <el-dialog title="订单详情" :visible.sync="detailDataVisible">
                <el-table :data="detailData" style="width: 100%">
                    <el-table-column prop="idFood" label="商品id" width="180"></el-table-column>
                    <el-table-column prop="num" label="商品数量" width="180"></el-table-column>
                    <el-table-column prop="singlePrice" label="单价" width="180"></el-table-column>
                    <el-table-column prop="totalPrice" label="总价"></el-table-column>
                </el-table>
            </el-dialog>

        </el-main>
    </el-container>
</template>

<script>
    export default
    {
        data()
        {
            return{
                temprowid: '',
                totalList: [],
                detailDataVisible: false,
                detailData: [],
                ShipData: [
                    {
                        date: "2019/06/06",
                        name: "alex",
                        address: "tiananmen",
                        phone: "10086",
                        totalPrice: "5000",
                    },
                    {
                        date:'1970/01/01',
                        name:'You',
                        address:'wuhu',
                        phone:'123',
                        totalPrice:'1.0',
                    },
                ],
                RecData:[
                    {
                        date: "2019/06/06",
                        name: "alex",
                        address: "tiananmen",
                        phone: "10086",
                        totalPrice: "5000",
                    },
                    {
                        date:'1970/01/01',
                        name:'You',
                        address:'wuhu',
                        phone:'123',
                        totalPrice:'1.0',
                    },
                ],
                CompleteData:[
                    {
                        date: "2019/06/06",
                        name: "alex",
                        address: "tiananmen",
                        phone: "10086",
                        totalPrice: "5000",
                    },
                    {
                        date:'1970/01/01',
                        name:'You',
                        address:'wuhu',
                        phone:'123',
                        totalPrice:'1.0',
                    },
                ],
            }
        },
        methods:
            {
                back()
                {
                    this.$router.push({path: "/setting"});
                },
                async getList() {
                    //const { data: res } = await this.$http.post("SearchShopOrder",window.sessionStorage.getItem("username"));
                    this.username=window.sessionStorage.getItem("username");
                    const { data: res } = await this.$http.post("SearchShopOrderClient",this.username);
                    console.log(res);
                    this.totalList = res;
                    //this.handleSizeChange(this.pageSize);
                    this.ShipData=[];
                    this.RecData = [];
                    this.CompleteData = [];
                    for (var i = 0; i < res.length; i++) {
                        if (res[i].beDeliver === 'n') {
                            this.ShipData.push(res[i]);
                        } 
                        else if(res[i].beOver ==='n'){
                        this.RecData.push(res[i]);
                        }
                        else{
                            this.CompleteData.push(res[i]);
                        }
                    }
                },
                /* 查看详情 */
                async handleClick(row, type) {
                    this.temprowid=row.orderID;
                    const { data: res } = await this.$http.post("OrderDetail", this.temprowid);
                    this.detailDataVisible = true;
                    this.detailData = res;
                },
                /* 收货 */
                async handleRec(row, type) {
                    console.log(row);

                    this.temprowid=row.orderID;
                    const {data: res} = await this.$http.post("Receipt", this.temprowid);
                    if (res === "ok") {
                        await this.getList();
                        this.$message({
                            type: "success",
                            message: "发货成功"
                        });
                    }else{
                        this.$message.error('发货失败');
                    }
                }
            },
        created() {
            this.getList();
        },
    }
</script>

<style lang="less" scoped>
    /* 布局样式 */
    .home-container
    {
        height: 100%;
    }
    /* 头部样式 */
    .el-header
    {
        background-color: #000000;
        display: flex;
        justify-content: space-between;
        padding-left: 0%;
        align-items: center;
        color: #fff;
        font-size: 20px;
        div
        {
            display: flex;
            align-items: center;
            span
            {
                margin-left: 15px;
            }
        }
    }
</style>