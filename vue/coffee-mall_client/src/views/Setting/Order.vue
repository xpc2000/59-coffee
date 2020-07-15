<template>
    <el-container>
        <el-header>
            <div>
                <span style="color: #ffffff">我的订单</span>
            </div>
            <el-button  @click="back()" style="color: #ffffff" type="text">返回</el-button>
        </el-header>

        <el-main>

            <!--待付款 -->
            <el-tabs type="border-card">
                <el-tab-pane label="待付款">
                    <el-table
                            :data="tableData3"
                            height="250"
                            border
                            style="width: 100%">
                        <el-table-column
                                prop="date"
                                label="日期"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="姓名"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="address"
                                label="地址">
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

                <!--待发货-->
                <el-tab-pane label="待发货">
                    <el-table
                            :data="tableData3"
                            height="250"
                            border
                            style="width: 100%">
                        <el-table-column
                                prop="date"
                                label="日期"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="姓名"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="address"
                                label="地址">
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

                <!--待收货-->
                <el-tab-pane label="待收货">
                    <el-table
                            :data="tableData3"
                            height="250"
                            border
                            style="width: 100%">
                        <el-table-column
                                prop="date"
                                label="日期"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="姓名"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="address"
                                label="地址">
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

                <!--待评价-->
                <el-tab-pane label="待评价">
                    <el-table
                            :data="tableData3"
                            height="250"
                            border
                            style="width: 100%">
                        <el-table-column
                                prop="date"
                                label="日期"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="姓名"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="address"
                                label="地址">
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

            </el-tabs>
        </el-main>
    </el-container>
</template>

<script>
    export default
    {
        data()
        {
            return{

            }
        },

        methods:
            {
                back()
                {
                    this.$router.push({path: "/setting"});
                }
            }
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