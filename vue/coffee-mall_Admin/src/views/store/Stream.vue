<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>门店管理</el-breadcrumb-item>
            <el-breadcrumb-item>账目管理</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <!-- 搜索栏-->
        <el-form class="user-search" :inline="true" label-width="90px">
            <el-form-item prop="date1" label="起始日期: ">
                <el-date-picker type="date"  v-model="sizeForm.date1" placeholder="选择起始日期"  style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item prop="date2" label="终止日期: ">
                <el-date-picker type="date"  v-model="sizeForm.date2" placeholder="选择终止日期"  style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item prop="store" label="门店: ">
                <el-input v-model="value" placeholder="请选择门店" clearable></el-input>
            </el-form-item>
            <el-form-item label=" ">
                <el-button size="medium" type="primary" :loading="loading" class="title"  icon="iconfont icon-chazhao" round @click="Search()"> 查询账目 </el-button>
            </el-form-item>
        </el-form>

        <!-- 列表 -->
        <el-table :data="tempList" :fit="true" :show-header="true" border="true" max-height="430" show-summary>
            <el-table-column prop="OrderID" label="订单ID" align="center">
            </el-table-column>
            <el-table-column prop="storeID" label="门店ID" align="center">
            </el-table-column>
            <el-table-column prop="name" label="门店名称" align="center">
            </el-table-column>
            <el-table-column prop="money" label="订单金额(元)" align="center">
            </el-table-column>
        </el-table>

    </div>
</template>

<script>
    export default
    {
        data() {
            return {

                // 死数据
                tempList:[
                    {
                    OrderID: 'O41361',
                    storeID: 'S53251',
                    name: 'xmu59',
                    money: '123.1',
                    },
                    {
                        OrderID: 'O41361',
                        storeID: 'S53251',
                        name: 'xmu59',
                        money: '250.1',
                    },
                ],


                /* 时间选择器 */
                sizeForm: {
                    date1: '',
                    date2: '',
                },

                /* 门店选择器 */
                option: [{
                    value: '选项1',
                    label: '店1'
                }, {
                    value: '选项2',
                    label: '店2',
                    disabled: true
                }, {
                    value: '选项3',
                    label: '店3'
                }, {
                    value: '选项4',
                    label: '店4'
                }, {
                    value: '选项5',
                    label: '店5'
                }],
                value: '',   // 输入框值

            }
        },
        methods:{

            /* 查询 */
            Search()
            {

            }
        }
    }
</script>

<style lang="less" scoped>

</style>