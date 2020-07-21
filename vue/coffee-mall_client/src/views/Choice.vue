<template>
    <div>
        <div>
            <i style="color: #bd2c00;font-size: 20px"> 为您优选  </i>
        </div>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <van-notice-bar
                left-icon="volume-o"
                scrollable
                text="欢迎使用59coffee点餐系统"
        />
        <br/>
        <br/>

    <el-carousel :interval="3000" type="card" height="500px">
        <el-carousel-item v-for="item in 6" :key="item">
            <h3>{{ item }}</h3>
        </el-carousel-item>
    </el-carousel>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <div>
            <i style="color: #bd2c00;font-size: 18px" class="iconfont icon-caipinpaihangbang"> 销量排行  </i>
            <br/>
            <br/>
        </div>
        <el-table
                :data="Data"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                :default-sort = "{prop: 'Sales', order: 'descending'}">
                <el-table-column width="70">
                    <template slot-scope="scope">
                        <i v-if="scope.$index === 0" class="iconfont icon-first" style="color: #FFD700"></i>
                        <i v-else-if="scope.$index === 1" class="iconfont icon-second" style="color: #C0C0C0"></i>
                        <i v-else-if="scope.$index === 2" class="iconfont icon-third" style="color: #F0E68C"></i>
                        <i v-else>{{scope.$index+1}}</i>
                    </template>
                </el-table-column>
            <el-table-column
                    prop="foodId"
                    label="菜品ID"
                    width="180"
                    v-show=true>
            </el-table-column>
            <el-table-column
                    prop="foodName"
                    label="菜品名称"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="Sales"
                    label="销量"
                    width="180"
                    :render-header="renderHeader">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row,1)" type="text" size="small">加入购物车</el-button>
                </template>
            </el-table-column>

        </el-table>

    </div>
</template>

<script>
    export default
    {
        data()
        {
            return{
                show: 'iconfont icon-yajun',

                /* 排行榜 */
                Data:[],

                /* 排行榜,死数据 */
                tableData:[
                    {
                        foodId:'1',
                        foodName:'wwhu',
                        Sales:12,
                    },
                    {
                        foodId:'2',
                        foodName:'wu',
                        Sales:11,
                    },
                    {
                        foodId:'3',
                        foodName:'whu',
                        Sales:7,
                    },
                    {
                        foodId:'4',
                        foodName:'wuhu',
                        Sales:8,
                    },
                    {
                        foodId:'5',
                        foodName:'wuhu',
                        Sales:8,
                    },
                    {
                        foodId:'6',
                        foodName:'wuhu',
                        Sales:8,
                    },
                    {
                        foodId:'7',
                        foodName:'wuhu',
                        Sales:8,
                    },
                ]
            }
        },

        methods:{

            /* 排行榜颜色 */
            tableRowClassName({row, rowIndex}) {
                if (rowIndex === 0) {
                        return 'warning-row';
                } else if (rowIndex === 1) {
                    return 'success-row';
                }
                else if (rowIndex === 2) {
                    return 'error-row';
                }
                return '';
            },

            /* 获取排行榜 */
            async getLeaderboard() {
                const {data: res} = await this.$http.get("GetLeadBoard");
                this.Data = res;
            },


            /* 加入购物车 */
            async handleClick(row, type)
            {
                const { data: res } = await this.$http.post("OrderDetail", row.foodId);
                if(res === 'ok')
                {
                    this.$message.success('加入购物车成功');
                }
            },

            /* render 事件 */
            renderHeader (h,{column}) { // h即为cerateElement的简写，具体可看vue官方文档
                return h(
                    'div',
                    [
                        h('span', column.label),
                        h('i', {
                            class:'iconfont icon-xiaoliangyuce',
                            style:'color:#409eff;margin-left:5px;'
                        })
                    ],
                );
            }
        },

        created() {
            this.getLeaderboard();
        }
    }
</script>

<style>
    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
    .el-table .warning-row {
        background: oldlace;
        font-size: 20px;
    }
    .el-table .success-row {
        background: #f0f9eb;
        font-size: 18px;
    }
    .el-table .error-row{
        background: #F7F7F7;
        font-size: 16px;
    }
</style>