<template>
    <el-container>
        <el-header>
            <div>
                <span style="color: #ffffff">结算</span>
            </div>
            <el-button  @click="back()" style="color: #ffffff" type="text">返回</el-button>
        </el-header>

        <el-main>

            <van-cell-group>
                <van-cell title="门店"  :value="store" size="large" style="background-color: #ffffff"/>
            <div style="width: 200px;padding-left: 1160px">
                    <el-radio v-model="DType" label="1">到店</el-radio>
                    <el-radio v-model="DType" label="2">外送</el-radio>
            </div>

                <van-field
                        v-model="item.address"
                        label="收货地址"
                        placeholder="请输入地址"
                        autosize
                        :border="false"
                        input-align="right"
                />

                <van-cell title="商品金额"  size="large" style="background-color: #ffffff">
                    <span style="color: red"> ¥ {{goodsPrice}} </span>
                </van-cell>

                <van-cell title="配送费"  size="large" style="background-color: #ffffff">
                    <span style="color: red"> ¥ {{deliveryPrice}} </span>
                </van-cell>

                <van-field
                        v-model="item.message"
                        label="订单备注"
                        placeholder="请输入备注"
                        autosize
                        :border="false"
                        input-align="right"

                />
            </van-cell-group>

            <div class="msg_box">
                    <van-submit-bar
                            :price='100*(deliveryPrice+goodsPrice)'
                            button-text="提交订单"
                            @submit="Submit"
                    />
            </div>
        </el-main>
    </el-container>
</template>

<script>
    export default
    {
        data()
        {
            return{
                store:'门店1',
                totalPrice: '',
                goodsPrice: 0.0,
                DType: '1',
                deliveryPrice: 0.0,
                item:
                    {
                        message:'',
                        address:'',
                    },
            }
        },

        methods:
            {
                back()
                {
                    this.$message.error('结算失败');
                    this.$router.push({path: "/cart"});
                },

                Submit()
                {
                    this.$message.success("提交订单成功");
                    this.$router.push({path: "/cart"});
                }
            },

        created() {
            this.goodsPrice = window.sessionStorage.getItem("money")/100.0;
            this.store = window.sessionStorage.getItem("StoreName");
        },

        watch:
            {
                DType(val){
                    if(val === '1')
                    {
                        this.deliveryPrice = 0;
                    }
                    else
                    {
                        this.deliveryPrice = 2.0;
                    }
                }
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
    .el-main
    {
        background-color: #eaedf1;
        width: 90%;
        height: 85%;
        align-self: center;
        position: absolute;
        top: 10%;
    }
    .msg_box
    {
        width: 1000px;
        height: 520px;
        //background-color: #eaedf1;
        //border-radius: 10px;
        position: absolute;   // 绝对坐标
        left: 60%;
        top: 55%;
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
        top: 95%;
        left: 85%;
        transform: translate(-50%, -50%);
    }
</style>