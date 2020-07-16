<template>
  <el-container>
    <el-header>
      <div>
        <span style="color: #ffffff">客户订单</span>
      </div>
    </el-header>

    <el-main>
      <!--未发送 -->
      <el-tabs type="border-card">
        <el-tab-pane label="未发送">
          <el-table :data="undeliverList" border style="width: 100%">
            <el-table-column prop="date" label="日期" width="180"></el-table-column>
            <el-table-column prop="name" label="姓名" width="180"></el-table-column>
            <el-table-column prop="address" label="地址" width="180"></el-table-column>
            <el-table-column prop="phone" label="手机号码" width="180"></el-table-column>
            <el-table-column prop="totalPrice" label="总金额" width="180"></el-table-column>
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button @click="handleClick(scope.row,0)" type="text" size="small">查看详细</el-button>
                <el-button type="text" size="small" @click="deliver(scope.row)">配送</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!--待发货-->
        <el-tab-pane label="已发送">
          <el-table :data="deliverList" height="100%" border style="width: 100%">
            <el-table-column prop="date" label="日期" width="180"></el-table-column>
            <el-table-column prop="name" label="姓名" width="180"></el-table-column>
            <el-table-column prop="address" label="地址" width="180"></el-table-column>
            <el-table-column prop="phone" label="手机号码" width="180"></el-table-column>
            <el-table-column prop="totalPrice" label="总金额" width="180"></el-table-column>
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button @click="handleClick(scope.row,1)" type="text" size="small">查看详细</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>

      <!-- 订单详情页 -->
      <el-dialog title="订单详情" :visible.sync="detailDataVisible">
        <el-table :data="detailData" style="width: 100%">
          <el-table-column prop="idFood" label="商品id" width="180"></el-table-column>
          <el-table-column prop="num" label="商品数量" width="180"></el-table-column>
          <el-table-column prop="singlePrice" label="单价" width="180"></el-table-column>
          <el-table-column prop="totalPrice" label="总价"></el-table-column>
        </el-table>
      </el-dialog>
    </el-main>
    <!-- <div class="block" align="center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 15, 20, 25]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalSize"
      ></el-pagination>
    </div>-->
  </el-container>
</template>

<script>
export default {
  created() {
    this.getList();
  },
  data() {
    return {
      undeliverList: [
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000",
          orderDetails: [
            {
              idFood: "000",
              num: "5",
              singlePrice: "5",
              totalPrice: "20"
            }
          ]
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        },
        {
          date: "2019/06/06",
          name: "alex",
          address: "tiananmen",
          phone: "10086",
          totalPrice: "5000"
        }
      ],
      totalList: [],
      pageSize: 10,
      currentPage: 1,
      totalSize: 20, // 总条数，用于死数据
      tempList: [], // 分页数据
      deliverList: [],
      detailDataVisible: false,
      detailData: []
    };
  },

  methods: {
    async getList() {
      const { data: res } = await this.$http.post("SearchShopOrder");
      this.totalList = res;
      //this.handleSizeChange(this.pageSize);

      for (var i = 0; i < res.length; i++) {
        if (res[i].beDeliver == 0) {
          undeliverList.push(res[i]);
        } else {
          deliverList.push(res[i]);
        }
      }
    },
    handleClick(row, type) {
      this.detailDataVisible = true;
      this.detailData = row.orderDetails;
    },
    async deliver(row) {
      const { data: res } = await this.$http.post("Deliver", row.id);
      if (res === "ok") {
        await this.getList();
        this.$message({
          type: "success",
          message: "发货成功"
        });
      }else{
          this.$message.error('发货失败');
      }
    }
    // handleSizeChange(val) {
    //   this.pageSize = val;
    //   this.handleCurrentChange(this.currentPage);
    // },

    // /* 分页与排序不可兼得 */
    // handleCurrentChange(val) {
    //   //   this.totalSize = this.UserList.length;   // 更新总条数，用于死数据
    //   this.currentPage = val;
    //   let from = (this.currentPage - 1) * this.pageSize;
    //   let to = this.currentPage * this.pageSize;
    //   this.tempList = [];
    //   for (; from < to; from++) {
    //     if (this.totalList[from]) {
    //       this.tempList.push(this.totalList[from]);
    //     }
    //   }
    // }
  }
};
</script>

<style lang="less" scoped>
/* 布局样式 */
.el-container {
  height: 100%;
}

/* 头部样式 */
.el-header {
  background-color: #000000;
  display: flex;
  justify-content: space-between;
  padding-left: 0%;
  align-items: center;
  color: #fff;
  font-size: 20px;
  div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}
</style>