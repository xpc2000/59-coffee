<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>餐品管理</el-breadcrumb-item>
            <el-breadcrumb-item>折扣管理</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <el-form label-width="80px" :model="item" inline>

            <div style="width: 800px">
            <el-form-item label="优惠一"></el-form-item>
            <el-form-item label="满" style="width: 300px">
                <el-input v-model="item.payments_1"></el-input>
            </el-form-item>
            <el-form-item label="减" style="width: 300px">
                <el-input v-model="item.minus_1"></el-input>
            </el-form-item>
            </div>

            <div style="width: 800px">
                <el-form-item label="优惠二"></el-form-item>
                <el-form-item label="满" style="width: 300px">
                    <el-input v-model="item.payments_2"></el-input>
                </el-form-item>
                <el-form-item label="减" style="width: 300px">
                    <el-input v-model="item.minus_2"></el-input>
                </el-form-item>
            </div>

            <div style="width: 800px">
                <el-form-item label="优惠三"></el-form-item>
                <el-form-item label="满" style="width: 300px">
                    <el-input v-model="item.payments_3"></el-input>
                </el-form-item>
                <el-form-item label="减" style="width: 300px">
                    <el-input v-model="item.minus_3"></el-input>
                </el-form-item>
            </div>

        </el-form>
        <br/>
        <br/>

        <el-button type="primary" @click="submitForm">保存修改</el-button>


    </div>
</template>

<script>
    export default
    {
        data() {
            return {
                // item:{
                //     payments_1:'',
                //     minus_1:'',
                //     payments_2:'',
                //     minus_2:'',
                //     payments_3:'',
                //     minus_3:'',
                // }
                item:{
                    payments_1:'',
                    minus_1:'',
                    payments_2:'',
                    minus_2:'',
                    payments_3:'',
                    minus_3:'',
                }
            };
        },
        created() {
            this.getDiscount();
        },
        methods:{
            async submitForm() {
                console.log(this.item);
                if(this.item.payments_1 > this.item.minus_1 && this.item.payments_2 > this.item.minus_2 && this.item.payments_3 > this.item.minus_3
                && this.item.payments_1 !== null && this.item.payments_2 !== null && this.item.payments_3 !== null
                && this.item.minus_1 !== null && this.item.minus_2 !== null && this.item.minus_3 !== null)
                {
                    const {data: res} = await this.$http.post("editdiscount", this.item);
                    if (res === 'ok') {
                        this.$message.success('保存成功');
                    } else {
                        this.$message.error('保存失败');
                    }
                }
                else
                {
                    this.$message.error('输入有误');
                }
            },
            async getDiscount() {
                const {data: res} = await this.$http.get("showdiscount");
                this.item = res;
            }
        }
    }
</script>

<style lang="less" scoped>
</style>