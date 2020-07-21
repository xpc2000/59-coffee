<template>
    <div>
        <el-row>
            <el-col :span="4">
            <i style="color: #bd2c00;font-size: 20px"> 购物车 </i>
            </el-col>
        </el-row>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <div class="msg_box">
            <div style="margin-left:0.5%;margin-right:1%">
                <el-row>
                    <van-checkbox-group v-model="checkedGoods">
                        <el-col :span="24" v-for="(item) in goods" :key="item.id">
                            <div style="margin-top:15px">
                                <el-card :body-style="{ padding: '0px'}" shadow="hover">
                                    <el-row>
                                        <el-col :span="2">
                                            <div style="padding-top: 110px; padding-left: 20px">
                                                <van-checkbox v-model="item.checked" icon-size="25px"
                                                              :name="item.id"></van-checkbox>
                                            </div>
                                        </el-col>
                                        <el-col :span="6"><img :src="item.url" class="image"></el-col>
                                        <el-col :span="8">
                                            <div style="padding-bottom: 14px; padding-top: 14px"><i
                                                class="el-icon-star-on"> {{item.id}}</i></div>
                                            <div style="padding-bottom: 14px; padding-top: 14px"><i
                                                    class="el-icon-star-on"> {{item.name}}</i></div>
                                            <div style="padding-bottom: 14px; padding-top: 14px"><i
                                                    class="el-icon-menu"> {{item.type}}</i></div>
                                            <div style="padding-bottom: 14px; padding-top: 14px"><i style="color: red">
                                                ¥ {{item.price}}</i></div>
                                        </el-col>
                                        <el-col :span="8">
                                            <div style="padding-top: 28px; padding-left: 100px">
                                                <van-stepper :value="item.num" integer input-width="65px"
                                                             button-size="37px" @change="stepperEvent(item, arguments)"
                                                             disableInput/>
                                            </div>
                                            <div style="padding-top: 30px; padding-left: 112px">
                                                <van-button type="danger" style="width: 120px; height: 50px" hairline @click="delCart(item.id)">删除</van-button>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </el-card>
                            </div>
                        </el-col>
                    </van-checkbox-group>
                </el-row>
            </div>
        </div>
        <van-submit-bar
                :price="totalPrice"
                :disabled="!checkedGoods.length"
                button-text="结算"
                @submit="cartSubmit"
        />
    </div>
</template>

<script>
    export default
    {
        data()
        {
            return{
                username:'',
                param:{
                    username:'',
                    foodid:''
                },
                checkedAll: false,
                checkedGoods: [],
                allGoods: [],
                goods: [],

                tempList:[
                    {
                        id: '1',
                        name: '餐品1',
                        type: '类型',
                        price: 1,
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num: 1,
                        checked: true,
                    },
                    {
                        id: '2',
                        name: '餐品2',
                        type: '类型',
                        price: 2,
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num: 2,
                        checked: false,
                    },
                    {
                        id: '3',
                        name: '餐品3',
                        type: '类型',
                        price: 2.5,
                        url:'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                        num: 3,
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
                ],

            }
        },

        created() {
            this.getCartList();
        },

        computed:{
            totalPrice(){
                return this.goods.reduce(
                    (total, item) =>
                        total +
                        (this.checkedGoods.indexOf(item.id) !== -1
                        ? item.price * item.num * 100 : 0
                        ), 0
                );
            }
        },

        methods:{

            async getCartList() {
                this.username=window.sessionStorage.getItem("username");
                
                const {data: res} = await this.$http.post("getShoppingCart",this.username);
                console.log(res);
                this.goods = res;
                //this.goods = this.tempList;   // 用于死数据
                this.allGoods = this.goods;
                this.checkedGoods = this.getCheckedList(this.goods);
            },

            getCheckedList() {
                let result = [];
                this.goods.forEach(function(item, index){
                    if(item.checked){
                        result.push(item.id);
                    }
                });
                return result;
            },

            stepperEvent(item, arg){
                this.goods.forEach(function (item1, index) {
                    if(item.id === item1.id)
                    {
                        item1.num = arg[0];
                    }
                })
            },

            /* 删除 */
            delCart(id) {
                this.$confirm('确定删除此商品吗?', '信息', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(async () => {
                        this.param.username=this.username;
                        this.param.foodid=id;
                        // 删除
                        const {data: res} = await this.$http.post("DelShoppingCart", this.param);
                        if (res === "ok") {
                            this.$message({
                                type: 'success',
                                message: '数据已删除'
                            })
                            await this.getCartList();
                        } else {
                            this.$message({
                                type: 'info',
                                message: '删除失败'
                            })
                        }
                    })
                    .catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        })
                    })
                    .catch(err => {
                        this.$message.error('数据删除失败')
                    })

            },

            /* 结算 */
            async cartSubmit() {
                //const {data: res} = await this.$http.post("CartSubmit", this.goods);
                if(true)   // res === 'ok'
                {
                    this.totalPrice=90;
                    window.sessionStorage.setItem("money", this.totalPrice);
                    this.$message.info('进入结算界面');
                    await this.$router.push({path: "/settlement"});   // 页面路由跳转
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .msg_box
    {
        width: 1000px;
        height: 520px;
        background-color: #fff;
        border-radius: 10px;
        position: absolute;   // 绝对坐标
        left: 50%;
        top: 47%;
        transform: translate(-50%, -50%);
        overflow-y: auto;
        overflow-x: hidden;
    }
    .image{
        width: 210px;
        height: 235px;
        align-content: center;
    }
    .el-row {
        margin-bottom: 20px;
        &:last-child {
            margin-bottom: 0;
        }
    }
    .el-col {
        border-radius: 4px;
    }
    .van-submit-bar{
        background-color: #eaedf1;
        position: absolute;
        width: 300px;
        height: 50px;
        top: 87%;
        left: 87%;
        transform: translate(-50%, -50%);
    }
</style>