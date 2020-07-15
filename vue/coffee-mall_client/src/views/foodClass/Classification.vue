<template>
    <div>
        <el-row>
            <el-col :span="2">
                <i style="color: #bd2c00;font-size: 20px"> 分类 </i>
            </el-col>


            <el-col :span="3">
                <el-select v-model="name" placeholder="请选择门店">
                    <el-option
                            v-for="item in options"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-col>
        </el-row>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

        <el-container class="home-container">
            <el-aside width="200px">
                <el-menu
                        background-color="#F5F5F5"
                        text-color="#999999"
                        active-text-color="#ff0000"
                        :default-active="this.$route.path"
                        :router="true">
                    <el-menu-item index="/classification/drink"><i class="iconfont icon-yinliao"></i> 饮 料 类 </el-menu-item>
                    <el-menu-item index="/classification/snack"><i class="iconfont icon-xiaochi"></i> 小 吃 类 </el-menu-item>
                </el-menu>
            </el-aside>

            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </div>
</template>

<script>
 export default {
     data()
     {
         return{
             name: '',   // 选中的门店名称
             options:[
                 {
                     id: 1,
                     name: '门店1',
                 },
                 {
                     id: 2,
                     name: '门店2',
                 },
                 {
                     id: 3,
                     name: '门店3',
                 },
             ],
         }
     }

 }


</script>

<style lang="less" scoped>
    .el-menu
    {
        width: 110px;
        height: 200px;
    }
</style>