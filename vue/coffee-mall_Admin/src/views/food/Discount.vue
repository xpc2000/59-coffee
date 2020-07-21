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
                <el-input v-model="item.Payments1"></el-input>
            </el-form-item>
            <el-form-item label="减" style="width: 300px">
                <el-input v-model="item.Minus1"></el-input>
            </el-form-item>
            </div>

            <div style="width: 800px">
                <el-form-item label="优惠二"></el-form-item>
                <el-form-item label="满" style="width: 300px">
                    <el-input v-model="item.Payments2"></el-input>
                </el-form-item>
                <el-form-item label="减" style="width: 300px">
                    <el-input v-model="item.Minus2"></el-input>
                </el-form-item>
            </div>

            <div style="width: 800px">
                <el-form-item label="优惠三"></el-form-item>
                <el-form-item label="满" style="width: 300px">
                    <el-input v-model="item.Payments3"></el-input>
                </el-form-item>
                <el-form-item label="减" style="width: 300px">
                    <el-input v-model="item.Minus3"></el-input>
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
                item:{
                    Payments1:'',
                    Minus1:'',
                    Payments2:'',
                    Minus2:'',
                    Payments3:'',
                    Minus3:'',
                }
            };
        },

        created() {
            this.getDiscount();
        },

        methods:{
            async submitForm() {
                if(this.item.Payments1 > this.item.Minus1 && this.item.Payments2 > this.item.Minus2 && this.item.Payments3 > this.item.Minus3
                && this.item.Payments1 !== null && this.item.Payments2 !== null && this.item.Payments3 !== null
                && this.item.Minus1 !== null && this.item.Minus2 !== null && this.item.Minus3 !== null)
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