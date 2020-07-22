<template>
    <div>
        <van-notice-bar
                left-icon="volume-o"
                text="欢迎使用59coffee管理系统"
                scrollable
        />
        <br/>
        <br/>

        <el-carousel :interval="3000" type="card" height="500px">
            <el-carousel-item v-for="item in src" :key="item">
                <img :src="item" class="image">
            </el-carousel-item>
        </el-carousel>


    </div>
</template>

<script>
    export default
    {
        data() {
            return {
                src: [
                    'https://pic.downk.cc/item/5f16dfa014195aa594de9ed2.jpg',
                    'https://pic.downk.cc/item/5f16d86114195aa594db8441.jpg',
                    'https://pic.downk.cc/item/5f16df4a14195aa594de871f.jpg',
                    'https://pic.downk.cc/item/5f16d9ea14195aa594dc0fae.jpg',
                    'https://pic.downk.cc/item/5f16dfed14195aa594deb37c.jpg',
                ],
            }
        }
    }
</script>

<style lang="less">
    .el-carousel__item{
        .h3 {
            color: #475669;
            font-size: 14px;
            opacity: 0.75;
            line-height: 200px;
            margin: 0;
        }
        .image{
            width: 800px;
            height: 500px;
            align-content: center;
        }
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
</style>