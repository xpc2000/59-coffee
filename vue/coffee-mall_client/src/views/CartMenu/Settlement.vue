<template>
    <el-container>
        <el-header>
            <div>
                <span style="color: #ffffff">结算</span>
            </div>
            <el-button  @click="back()" style="color: #ffffff" type="text">返回</el-button>
        </el-header>

        <el-main>
            <div v-show="show">
                <el-alert
                        title="错误"
                        type="error"
                        :description="text"
                        show-icon>
                </el-alert>
                <br/>
            </div>

            <van-cell-group>
                <van-cell title="门店"  :value="item.store" size="large" style="background-color: #ffffff"/>
            <div style="width: 200px;padding-left: 1160px">
                    <el-radio v-model="item.oType" label="1">到店</el-radio>
                    <el-radio v-model="item.oType" label="2">外送</el-radio>
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
                    <span style="color: red"> ¥ {{item.goodsPrice}} </span>
                </van-cell>

                <van-cell title="配送费"  size="large" style="background-color: #ffffff">
                    <span style="color: red"> ¥ {{item.deliveryPrice}} </span>
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
                            :disabled="SubmitDisable"
                            :price='100*(item.deliveryPrice+item.goodsPrice)'
                            :button-text="ButtonText"
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
                text:'"未选择门店，请到分类界面选择门店"',
                show: true,
                ButtonText: '未选择门店',
                SubmitDisable: true,

                param:{
                    username:'',
                    item:'',
                },

                item:
                    {
                        storeId:'',
                        store:'未选择门店',
                        oType: '1',
                        goodsPrice: 0.0,
                        deliveryPrice: 0.0,
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

                /* 提交订单 */
                async Submit() {
                    this.item.shopId=window.sessionStorage.getItem("StoreId");
                    this.param.username=window.sessionStorage.getItem("username");
                    this.param.item=this.item;
                    console.log(this.param);
                    if(this.item.address==null||this.item.address=='')
                    {
                        this.$message.error("请填写地址");
                        return;
                    }
                    const {data: res} = await this.$http.post("SubmitOrder", this.param);
                    if(res === 'ok') {
                        this.$message.success("提交订单成功");
                        await this.$router.push({path: "/cart"});
                    }
                    else
                    {
                        this.text = res.item.name + '库存不足';
                        this.show = true;
                    }
                }
            },

        created() {
            this.item.goodsPrice = window.sessionStorage.getItem("money")/100.0;
            if(window.sessionStorage.getItem("StoreName") !== null)
            {
                this.item.storeId=window.sessionStorage.getItem("StoreId");
                this.item.store = window.sessionStorage.getItem("StoreName");
                this.SubmitDisable = false;
                this.ButtonText = '提交订单';
                this.show = false;
            }
        },

        watch:
            {
                oType(val){
                    if(val === '1')
                    {
                        this.item.deliveryPrice = 0;
                    }
                    else
                    {
                        this.item.deliveryPrice = 2.0;
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