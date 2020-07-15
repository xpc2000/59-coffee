<template>
    <div>
        <div>
            <i style="color: #bd2c00;font-size: 20px"> 为您优选  </i>
        </div>

        <!-- 分割线 -->
        <el-divider>
        </el-divider>

    <el-carousel :interval="3000" type="card" height="500px">
        <el-carousel-item v-for="item in 6" :key="item">
            <h3>{{ item }}</h3>
        </el-carousel-item>
    </el-carousel>
    </div>
</template>

<script>
    export default
    {

    }
</script>

<style lang="less" scoped>
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
</style>